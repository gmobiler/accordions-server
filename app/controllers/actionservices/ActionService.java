package controllers.actionservices;

import com.fasterxml.jackson.databind.JsonNode;

import db.BancoUsuarios;
import play.mvc.Http;
import play.mvc.Result;
import services.crypto.CryptoManager;
import services.util.Util;

//Interface usada para o ServicesController delegar os metodos do endpoint services. O ServicesController já entrega o corpo da requisição descriptografado
public abstract class ActionService {

    //o corpo da requisição  e o cookieSv já decryptados e validados
    public Result action(Http.Context ctx, JsonNode jsonBody, String cookieSv, CryptoManager cm, BancoUsuarios bancoUsuarios) {
        Util.log("action() jsonBody: " + jsonBody + ", cookieSv: "+ cookieSv);

        return play.mvc.Results.TODO;
    }

   /* public Result action(Http.Context ctx, JsonNode jsonBody, CryptoManager cm, Object bancoUsuarios) {
        Util.log("action() jsonBody: " + jsonBody);

        return play.mvc.Results.TODO;
    }*/
}