package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import controllers.authentication.AuthenticationAction;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;
import services.crypto.CryptoManager;
import services.util.Util;

import static controllers.actionservices.LoginService.COOKIE_KEY_STRING_VALIDACAO;
import static controllers.actionservices.LoginService.JSON_KEY_ID;
import static controllers.actionservices.LoginService.USUARIO_SESSAO_KEY;
import static play.mvc.Controller.request;
import static play.mvc.Controller.session;
import static play.mvc.Results.ok;

public class Auth {


    private final CryptoManager cm;

    //injeta o CryptoManager singleton no controller via construtor
    @Inject
    public Auth(CryptoManager cm) {
        this.cm = cm;
    }

    //Teste onde só pode obter musiquinhas se estiver logado
    @With(AuthenticationAction.class)
    public Result obterMusiquinhas() {
        Util.log("obterMusiquinhas");
        return ok("Pronto, obteve as musiquinhas pois estava logado");
    }


    //Vem a string de solicitacao de método via POST para. host/services. Essa string vem codificada com o CryptoManager
    /*
    {
        metodo:login
        params:{
                login:gilianp@gmail.com
                }

    }
     */


    //A Requisição de login possui uma validação diferente dos outros métodos pois a string de validação é gerada pelo próprio id, por isso precisa validar varias coisas.
    public Result loginSecure() throws Exception {
        //JsonNode jsonBody = request().body().asJson();
        String requestCrypto = request().body().asText();
        Util.log("loginSecure() requestCrypto: " + requestCrypto);



        if (requestCrypto != null) {
                String requestDescrypto = cm.decode(requestCrypto);
            Util.log("requestDescrypto: " + requestDescrypto);
            JsonNode js = Json.parse(requestDescrypto);

            String idCrypto = js.get(JSON_KEY_ID).asText();
            String idDescryptoSV = cm.decode(idCrypto);
            Util.log("idCrypto: " + idCrypto);
            Util.log("idDescryptoSV: " + idDescryptoSV);

            //Como é a requisição de login, a string de validação é gerada pelo próprio id. Então separa a string de validação sem sequer valida-la para poder entao confrontar com uma correta que é gerada pelo id
            String SVExtraida = cm.extrairStringValidacao(idDescryptoSV);
            String idSemSV = cm.removerStringValidacao(idDescryptoSV);

            Util.log("idDescryptoSV: "+ idDescryptoSV + ", idSemSV: " + idSemSV + ", SVExtraida: " + SVExtraida);

            //Verifica se a string de validação extraida do id é válida
            boolean SVExtraidaLoginValida = cm.validarSintaxeStringValidacao(SVExtraida);
            Util.log("SVExtraidaLoginValida: " + SVExtraidaLoginValida);

            //Agora aplica o algoritmo de geração de string de validação a partir de um id para ver se a string de validação enviada é a mesma.
            String SVGeradaLogin = cm.gerarStringValidacaoFromString(idSemSV);
            Util.log("SVGeradaLogin: " + SVGeradaLogin);

            //Verifica se as strings de validação são iguais. Note que somente compara o char idx 0 e 1, pois o idx 2 é gerado aleatoriamente e já foi validado na sintaxe
            boolean SVIguais = (SVGeradaLogin.charAt(0) == SVExtraida.charAt(0)) && (SVGeradaLogin.charAt(1) == SVExtraida.charAt(1));
            Util.log("SVIguais: " + SVIguais);

            //Pronto, a partir desse ponto, a string de validação enviada no id está correta e o id foi extraido, então faz os procedimentos normais de um login

            //Verifica se o usuário existe no banco
            boolean existeNoBanco = true;
            //boolean existeNoBanco = false;

            if (existeNoBanco){

                /*//Teste lê cookies fora session
                Cookie cookieSV = request().cookie(COOKIE_KEY_STRING_VALIDACAO);
                Util.log("cookieSV: " + cookieSV);
                if (cookieSV !=null) {
                    String SV = cookieSV.value();
                    String SVSign = Crypto.cookieSigner().asJava().sign(SV);
                    Util.log("SV: " + SV + ", SVSign: "+ SVSign);
                }*/

                //Teste lê a string de validação do SESSION cookie
                String cookieSV = session(COOKIE_KEY_STRING_VALIDACAO);
                Util.log("cookieSV: " + cookieSV);
                if (cookieSV !=null) {
                    String cookieSVDecrypt = cm.decode(cookieSV);
                    Util.log("cookieSVDecrypt: " + cookieSVDecrypt);
                }


                //Cria uma nova string de validação para colocar nos cookies da session pois é assimetrico. Coloca ela com CryptoManager também!
                String SVRetorno = cm.gerarStringValidacaoAleatoria();
                String SVRetornoCrypto = cm.enconde(SVRetorno);
                Util.log("SVRetorno: " + SVRetorno + ", SVRetornoCrypto: "+ SVRetornoCrypto);
                session(USUARIO_SESSAO_KEY, idSemSV);
                session(COOKIE_KEY_STRING_VALIDACAO, SVRetornoCrypto);
                /*String cookieSign = Crypto.cookieSigner().asJava().sign("Oisouvalorsignet123aB");

                //TODO Coloca SVRetorno nos cookies
                Http.Cookie cookie =  Http.Cookie.builder(COOKIE_KEY_STRING_VALIDACAO, cookieSign)
                        .withSecure(false).build();*/
              // return ok("USUARIO EXISTE NO BANCO, LOGADO, SVRetorno: " + SVRetorno).withCookies(cookie);
                return ok("USUARIO EXISTE NO BANCO, LOGADO, SVRetorno: " + SVRetorno);
            }
            else{
                return ok("USUARIO NÃO EXISTE NO BANCO!");
            }


        } else {
            return ok("ERRO, BODY ESTA NULL");
        }
    }

    void novoLogin(){
        //1 lê o login e senha da requisicao a services
        //2 verifica no banco se existe esse login. Se nao existir retorna login invalido
        //3 coloca o usuario (login) na session
        //4 gera a string de 2 caracteres para validação de login, e coloca ela nos cookies
        //5 retorna a resposta Ok para o usuario com o s cookies preenchidos (session e string de validacao)
    }

    //Basicamente toda requisição que modifica moedas, score e etc, é através de um POST para www.host/services.
    //No body do POST é que vai um json indicando o método (login, changeMoedas, saveScore, etc) e parametros usados. Esse body vem codificado com o CryptoManager
    //Nas outras requisições para adicionar ou remover moedas, o login é enviado através do sistema de session/cookie
    //A quantidade de moedas é criptografada usando o CryptoManager
    //host/services
    void securedService(){

    }


    public Result login() {
        /*String usuarioLogado = session(USUARIO_SESSAO_KEY);
        Util.log("usuarioLogado: " + usuarioLogado);*/

        JsonNode jsonBody = request().body().asJson();
        Util.log("jsonBody: " + jsonBody);
        if (jsonBody != null) {
            String login = jsonBody.get("login").asText();
            String senha = jsonBody.get("senha").asText();

            Util.log("login: " + login + ", senha: " + senha);


            //Busca o usuario no banco
            boolean usuarioExisteBanco = true;


            String msg;
            if (usuarioExisteBanco) {
                msg = "login com sucesso, usuario colocado na sessao";
                session(USUARIO_SESSAO_KEY, login);
            } else {
                msg = "Usuário: " + login + ", não encontrado no banco";
            }

            return ok(msg);
        } else {
            return ok("ERRO, BODY NULL");
        }
    }

    public Result logout() {

        String usuarioLogado = session(USUARIO_SESSAO_KEY);
        Util.log("logout() usuarioLogado: " + usuarioLogado);

        String msg;
        if (usuarioLogado != null){
            session().remove(USUARIO_SESSAO_KEY);
            msg = "Usuario deslogado com sucesso";
        } else {
            msg = "NÃO TINHA USUARIO NA SESSION";
        }

        Util.log("msg: " + msg);

        return ok(msg);
    }




}
