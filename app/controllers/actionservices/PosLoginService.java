package controllers.actionservices;

import com.fasterxml.jackson.databind.JsonNode;

import org.h2.api.ErrorCode;

import db.BancoUsuarios;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import services.crypto.CryptoManager;
import services.util.Util;

import static controllers.actionservices.LoginService.USUARIO_SESSAO_KEY;
import static play.mvc.Results.ok;
import static services.ErrorCodes.*;

//Action service abstrata para que todas as ActionServices que precisem estar logado extendam ela e executem a validação de CryptoManager e checagem de usuario logado
//Quando chama essa classe, o ServicesController já validou a requisição e todas as questões de string de validação e cookies no nivel da request
public abstract class PosLoginService extends ActionService{
    @Override
    public final Result action(Context ctx, JsonNode jsonBody, String cookieSv, CryptoManager cm, BancoUsuarios bancoUsuarios) {
        Util.log("action() PosLoginService, validando requsição CryptoManager e checando usuario logado");
        Util.log("jsonBody(): " + jsonBody + ", cookieSv: " +cookieSv);

        //Verifica se existe usuario na session
        String idUsuarioSession = ctx.session().get(USUARIO_SESSAO_KEY);
        Util.log("idUsuarioSession: " + idUsuarioSession);

        if (idUsuarioSession != null){

            //Verifica se o usuario enviado na session existe no banco
            boolean usuarioExisteNoBanco = bancoUsuarios.containsUsuario(idUsuarioSession);
            Util.log("usuarioExisteNoBanco: " + usuarioExisteNoBanco);

            if (usuarioExisteNoBanco) {
                JsonNode usuarioLogado = bancoUsuarios.userTable.get(idUsuarioSession);
                return actionParsed(ctx, jsonBody, idUsuarioSession, cookieSv, cm, bancoUsuarios);
            }
            else{
                return ok(ERROR_CODE_USUARIO_INEXISTENTE_POS_LOGIN + "");
            }
        }
        else{
            return ok(ERROR_CODE_SESSION_SEM_USUARIO_POS_LOGIN + "");
        }
    }


    public abstract Result actionParsed(Context ctx, JsonNode jsonBody, String idUsuario, String cookieSv, CryptoManager cm, BancoUsuarios bancoUsuarios);
}
