package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import controllers.actionservices.ActionService;
import controllers.actionservices.AlterarMoedasService;
import controllers.actionservices.LoginService;
import db.BancoUsuarios;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ErrorCodes;
import services.crypto.CryptoManager;
import services.tiles.BancoMusicasMemoria;
import services.util.Util;

import static services.ErrorCodes.*;

//Recebe todas as requisições que mexem em atributos do usuario (via POST) e delega para os tratadores
//Toda requsição sempre vai vir criptografada com CryptoManager e aplicada uma string de validação
public class ServicesController extends Controller {

    private final Map<String, ActionService> metodos;           //padrão strategy com as implementações dos métodos

    private final BancoUsuarios bancoUsuarios;
    private final CryptoManager cm;

    //keys dos métodos possiveis de services
    public static final String METODO_KEY = "metodo";

    //values para os métodos disponiveis em service
    public static final String METODO_VALUE_LOGIN = "login";
    public static final String METODO_VALUE_CADASTRO = "cadastro";
    public static final String METODO_ALTERAR_MOEDAS = "alterarMoedas";


    //injeta o CryptoManager e o banco usuario singleton no controller via construtor
    @Inject
    public ServicesController(CryptoManager cm, BancoUsuarios bancoUsuarios) {
        this.cm = cm;
        this.bancoUsuarios = bancoUsuarios;
        metodos = new HashMap<>();
        metodos.put(METODO_VALUE_LOGIN, new LoginService());
        metodos.put(METODO_ALTERAR_MOEDAS, new AlterarMoedasService());
    }

    public Result services() {
        Util.log("###################### services() ######################");

        //Caso o body for nulo ou vazio, retorna erro
        String requestCrypto = request().body().asText();
        if (requestCrypto == null || requestCrypto.isEmpty()) {
            Util.logProd("ERRO: body vazio");
            return ok(ERROR_CODE_BODY_EMPTY + "");
        }

        //1) Extrai a string de validação da requisição e valida sua sintaxe.
        String svExtraidaRequest = cm.extrairStringValidacao(requestCrypto);
        boolean svRequestSintaxeValida = cm.validarSintaxeStringValidacao(svExtraidaRequest);
        Util.log("svExtraidaRequest: " + svExtraidaRequest + ", svRequestSintaxeValida: " + svRequestSintaxeValida);
        if (!svRequestSintaxeValida) {
            Util.logProd("ERROR_CODE_SV_REQUEST_SINTAXE_INVALIDA");
            return ok(ERROR_CODE_SV_REQUEST_SINTAXE_INVALIDA + "");
        }

        //2) Valida a requisição usando a string de validação. Para isso, depende se é um método de login/cadastro ou pós login.
        //2.1) Caso seja login/cadastro, valida a SV aplicando o algoritmo de verificação com base na string, no caso, a propria requisição.
        //2.2) Caso seja pos login, verifica se a SV é igual a SV enviada nos cookies

        //Descobre qual é o método solicitado, extraindo o json body que veio encriptado
        String requestCryptSemSV = cm.removerStringValidacao(requestCrypto);
        String requestDecrypt;
        try {
            requestDecrypt = cm.decode(requestCryptSemSV);
        } catch (Exception e) {
            e.printStackTrace();
            return ok(ERROR_CODE_CRYPTO_MANAGER_DECODE_EXCEPTION + "");
        }
        Util.log("requestCrypto: " + requestCrypto);
        Util.log("requestDecrypt: " + requestDecrypt);
        JsonNode json = Json.parse(requestDecrypt);
        JsonNode metodo = json.get(METODO_KEY);
        Util.log("metodo: " + metodo);
        if (metodo != null) {
            String metodoStr = metodo.asText();
            String cookieSvDecrypt = null;          //usado em requisições pos login para ja passar para o action. Em Login será null
            boolean validouRequisicaoSv = false;
            if (metodoStr.equals(METODO_VALUE_LOGIN) || metodoStr.equals(METODO_VALUE_CADASTRO)) {
                validouRequisicaoSv = cm.validarSVGerada(requestCryptSemSV, svExtraidaRequest);
            } else {
                //Obtém a SV dos cookies
                Http.Cookie cookieSv = request().cookie(LoginService.COOKIE_KEY_STRING_VALIDACAO);
                Util.log("cookieSv: " + (cookieSv!=null?cookieSv.value():"null"));
                if (cookieSv != null) {
                    try {
                        cookieSvDecrypt = cm.decode(cookieSv.value());
                        validouRequisicaoSv = cookieSvDecrypt.equals(svExtraidaRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ok(ERROR_CODE_CRYPTO_MANAGER_DECODE_EXCEPTION + "");
                    }
                    Util.log("cookieSvDecrypt: " + cookieSvDecrypt);
                } else {
                    Util.logProd("ERROR_CODE_SV_NAO_ENCONTRADO_COOKIE");
                    return ok(ERROR_CODE_SV_NAO_ENCONTRADO_COOKIE + "");
                }
            }

            Util.log("validouRequisicaoSv: " + validouRequisicaoSv);


            //Usa o padrão strategy para delegar o servico

            ActionService actionService = metodos.get(metodo.asText());
            Util.log("actionService: " + actionService);

            //Object bancoUsuarios = new Object();        //envia o banco de usuarios para o action service
            if (actionService != null) {
                return actionService.action(Http.Context.current(), json,cookieSvDecrypt, cm, bancoUsuarios);
            } else {
                return badRequest(ErrorCodes.ERROR_CODE_METODO_NAO_ENCONTRADO + "");
            }
        } else {
            return badRequest(ErrorCodes.ERROR_CODE_METODO_NAO_ENCONTRADO + "");
        }

    }
}
