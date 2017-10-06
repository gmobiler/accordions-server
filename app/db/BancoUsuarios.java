package db;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import services.util.Pair;
import services.util.RawValueGM;
import services.util.Util;

@Singleton
public class BancoUsuarios {
    public volatile HashMap<String, JsonNode> userTable;                 //map onde key -> id do usuario, value -> json do usuario

    //esses são os nomes usados no json do banco. Os jsons enviados pela rede são diferentes pois podem ser abreviados para consumir menos banda
    public static final String JSON_DB_KEY_ID = "id";
    public static final String JSON_DB_KEY_NOME = "nome";
    public static final String JSON_DB_KEY_MOEDAS = "moedas";
    public BancoUsuarios() {
        Util.log("Inicializando o bando de usuarios");
        userTable = new HashMap<>();

        long idGen = 1;
        for (int i = 0; i < 10; i++) {
            addUsuario(Long.toString(idGen),"Usuario " + idGen, Util.randomInt(0,100));
            idGen++;
        }

        addUsuario("gilianp@gmail.com","Gilian Pablo " + idGen,45);
    }

    public boolean containsUsuario(String id){
        return userTable.containsKey(id);
    }


    void addUsuario(String id, String nome, int numMoedas){
        userTable.put(id, criarUsuario(id, nome,numMoedas));
    }

    JsonNode criarUsuario(String id, String nome, int numMoedas){
        ObjectNode json = new ObjectMapper().createObjectNode();
        json.put(JSON_DB_KEY_ID, id);
        json.put(JSON_DB_KEY_NOME, nome);
        json.put(JSON_DB_KEY_MOEDAS, numMoedas);

        return json;
    }
}
