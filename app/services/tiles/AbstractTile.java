package services.tiles;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import services.util.Util;

/**
 * Created by Gilian on 07/07/2017.
 */

public class AbstractTile implements JsonSerializer<AbstractTile>, JsonDeserializer<AbstractTile> {
    public long tsInicio;       //Enxergue o tsInicio como sendo o bottom do tile. Timestamp em ms do inicio da nota (tempo absoluto em relação ao inicio da musica).
    public long duracao;        //Duração em ms, é usado para gerar a altura da tileView, calculo pontuação etc


    public boolean comparar(AbstractTile o) {
        return (tsInicio == o.tsInicio) && (duracao == o.duracao);
    }
    public AbstractTile(long tsInicio, long duracao) {
        this.tsInicio = tsInicio;
        this.duracao = duracao;
    }

    //Gera o json com base no seu metodo "serialize"
    public String toJson(){
        String res = Util.toJson(this,getClass(),this);
        return res;
    }

    static final String KEY_JSON_TSINICIO = "ts";
    static final String KEY_JSON_DURACAO = "d";

    @Override
    public JsonElement serialize(AbstractTile src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty(KEY_JSON_TSINICIO, src.tsInicio);
        obj.addProperty(KEY_JSON_DURACAO, src.duracao);
        return obj;
    }

    @Override
    public AbstractTile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //Util.log("deserialize");
        JsonObject obj = json.getAsJsonObject();

        long tsInicio = obj.get(KEY_JSON_TSINICIO).getAsLong();
        long duracao = obj.get(KEY_JSON_DURACAO).getAsLong();

        return new AbstractTile(tsInicio, duracao);
    }
}
