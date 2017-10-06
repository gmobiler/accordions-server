package controllers.actionservices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import db.BancoUsuarios;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;
import services.crypto.CryptoManager;
import services.util.Util;

public class AlterarMoedasService extends PosLoginService {

    //parametros dos metodos
    public static final String JSON_KEY_DIFF_MOEDAS = "diffMoedas";

    @Override
    public Result actionParsed(Context ctx, JsonNode jsonBody, String idUsuario, String cookieSv, CryptoManager cm, BancoUsuarios bancoUsuarios) {
        Util.log("############### AlterarMoedasService ###############");
        Util.log("jsonBody: " + jsonBody);
        int diffMoedas = jsonBody.get(JSON_KEY_DIFF_MOEDAS).asInt();

        ObjectNode user = (ObjectNode) bancoUsuarios.userTable.get(idUsuario);
        int moedasUsuario = user.get(BancoUsuarios.JSON_DB_KEY_MOEDAS).asInt();

        int novasMoedas = moedasUsuario + diffMoedas;
        Util.log("idUsuario: " + idUsuario + ", diffMoedas : "+ diffMoedas + ", moedasUsuario: "+ moedasUsuario + ", novasMoedas: "+ novasMoedas);

        /*user.set(BancoUsuarios.JSON_DB_KEY_MOEDAS, novasMoedas);
        bancoUsuarios.userTable.get(idUsuario).set*/

        return Results.TODO;
    }
}
