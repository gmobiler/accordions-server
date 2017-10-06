package controllers.actionservices;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import db.BancoUsuarios;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import services.ErrorCodes;
import services.crypto.CryptoManager;
import services.util.Util;

import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static services.ErrorCodes.ERROR_CODE_CRYPTO_MANAGER_DECODE_EXCEPTION;
import static services.ErrorCodes.ERROR_CODE_CRYPTO_MANAGER_ENCODE_EXCEPTION;

public class CadastroService extends ActionService {
    public static final String JSON_KEY_ID = "id";

    public static final String USUARIO_SESSAO_KEY = "usersession";          //chave do cookie da sessao para a string de validação nas PosLoginActions
    public static final String COOKIE_KEY_STRING_VALIDACAO = "SV";


    @Override
    public Result action(Context ctx, JsonNode jsonBody, String cookieSv, CryptoManager cm, BancoUsuarios bancoUsuarios) {
        Util.log("########### cadastro action ############");
        Result res = null;

        JsonNode jsNodeId = jsonBody.get(JSON_KEY_ID);
        if (jsNodeId != null) {

            String idCrypto = jsonBody.get(JSON_KEY_ID).asText();
            //String idDescryptoSV = cm.decode(idCrypto);
            String idDescryptoSV;
            try {
                idDescryptoSV = cm.decode(idCrypto);
            } catch (Exception e) {
                e.printStackTrace();
                return ok(ERROR_CODE_CRYPTO_MANAGER_DECODE_EXCEPTION + "");
            }
            Util.log("idCrypto: " + idCrypto);
            Util.log("idDescryptoSV: " + idDescryptoSV);

            //Como é a requisição de login, a string de validação é gerada pelo próprio id. Então separa a string de validação sem sequer valida-la para poder entao confrontar com uma correta que é gerada pelo id
            String SVExtraida = cm.extrairStringValidacao(idDescryptoSV);
            String idSemSV = cm.removerStringValidacao(idDescryptoSV);
            Util.log("idDescryptoSV: " + idDescryptoSV + ", idSemSV: " + idSemSV + ", SVExtraida: " + SVExtraida);

            //Verifica se a string de validação extraida do id é válida
            boolean SVExtraidaLoginValida = cm.validarSintaxeStringValidacao(SVExtraida);
            Util.log("SVExtraidaLoginValida: " + SVExtraidaLoginValida);

            //Agora aplica o algoritmo de geração de string de validação a partir de um id para ver se a string de validação enviada é a mesma.
            String SVGeradaLogin = cm.gerarStringValidacaoFromString(idSemSV);
            Util.log("SVGeradaLogin: " + SVGeradaLogin);

            //Verifica se as strings de validação são iguais. Note que somente compara o char idx 0 e 1, pois o idx 2 é gerado aleatoriamente e já foi validado na sintaxe
            boolean SVIguais = (SVGeradaLogin.charAt(0) == SVExtraida.charAt(0)) && (SVGeradaLogin.charAt(1) == SVExtraida.charAt(1));
            Util.log("SVIguais: " + SVIguais);

            if (SVIguais) {          //Pronto, a partir desse ponto, a string de validação enviada no id está correta e o id foi extraido, então faz os procedimentos normais de um login
                //Verifica se o usuário existe no banco
                boolean existeNoBanco = bancoUsuarios.containsUsuario(idSemSV);
                Util.log("existeNoBanco:" + existeNoBanco + ", idSemSV: " + idSemSV);
                //boolean existeNoBanco = false;
                if (existeNoBanco) {
                    //Cria uma nova string de validação para colocar nos cookies da session pois é assimetrico, esse é o coockie de string de validação
                    //que é enviada em todas as PosLoginActions. Coloca ela com CryptoManager também como descrito em resources
                    String SVSession = cm.gerarStringValidacaoAleatoria();
                    String SVSessionCrypto;
                    try {
                        SVSessionCrypto = cm.enconde(SVSession);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ok(ERROR_CODE_CRYPTO_MANAGER_ENCODE_EXCEPTION + "");
                    }
                    Util.log("SVSession: " + SVSession + ", SVSessionCrypto: " + SVSessionCrypto);
                    session(USUARIO_SESSAO_KEY, idSemSV);
                    //session(COOKIE_KEY_STRING_VALIDACAO, SVSessionCrypto);            //DEPRECATED - Não uso mais o cookie na session pois chega no cliente especificamente não posso descriptografar

                    Http.Cookie cookieSV =  Http.Cookie.builder(COOKIE_KEY_STRING_VALIDACAO, SVSessionCrypto).withSecure(true).build();     //secure força o cliente só aceitar o cookie se vier de um HTTPS
                    //Http.Cookie cookieSV =  Http.Cookie.builder(COOKIE_KEY_STRING_VALIDACAO,  "*+-=?_{|}.1234567890").withSecure(true).build();
                    res = ok().withCookies(cookieSV);
                    //res = ok();
                } else {
                    //Eu evito enviar um unauthorized() pois se alguém estiver tentando descobrir a API, um unauthorized deixa claro que tem que fazer um login aintes
                    Util.logProd("Não encontrou usuario no banco, id: " + idSemSV);
                    res = badRequest(ErrorCodes.ERROR_CODE_USUARIO_INEXISTENTE + "");
                    //res = unauthorized();
                }
            } else {
                Util.logProd("ERROR_CODE_STRING_VALIDACAO_LOGIN_ID_INVALIDO");
                res = badRequest(ErrorCodes.ERROR_CODE_STRING_VALIDACAO_LOGIN_ID_INVALIDO + "");
            }
        } else {
            Util.logProd("ERROR_CODE_CAMPO_ID_NAO_ENCOTRANDO");
            res = badRequest(ErrorCodes.ERROR_CODE_CAMPO_ID_NAO_ENCOTRANDO + "");
        }


        return res;
    }


    Result testarCharsPossiveisParaCookie(){
        Result res;
        /////TESTA SE MEUS CHARS DE SAIDA SÃO ACEITOS PELO COOKIE
        List<String> charsEncoded = new ArrayList<>();
        charsEncoded.addAll(CryptoManager.LATIN_LOWER);
        charsEncoded.addAll(CryptoManager.LATIN_UPPER);
        charsEncoded.addAll(CryptoManager.NUM);
        charsEncoded.addAll(CryptoManager.SPECIAL_ACEITAVEIS_SAIDA);
        StringBuilder sb = new StringBuilder();
        for (String s : charsEncoded){
            sb.append(s);
            //cria um cookie para cada sequencia
            Http.Cookie cookieSV =  Http.Cookie.builder(COOKIE_KEY_STRING_VALIDACAO, s).withSecure(true).build();
            res = ok().withCookies(cookieSV);
        }
        //cria um cookie master com toda a sequencia
        Http.Cookie cookieSV2 =  Http.Cookie.builder(COOKIE_KEY_STRING_VALIDACAO, sb.toString()).withSecure(true).build();
        res = ok().withCookies(cookieSV2);
        //FIM TESTA CHARS DE SAIDA

        return res;
    }

}
