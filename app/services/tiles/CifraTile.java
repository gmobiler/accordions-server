package services.tiles;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import services.util.Util;

/**
 * Created by Gilian on 07/07/2017.
 */

public class CifraTile extends AbstractTile {
    public Acorde acorde;
    public int numRepeticoes;       //indica quantas vezes será executado o loop do ritmo dentro da "duracao"

    public CifraTile(Acorde acorde, long tsInicio, long duracao, int numRepeticoes) {
        super(tsInicio, duracao);
        this.acorde = acorde;
        this.numRepeticoes = numRepeticoes;

        /*this.tsInicio = tsInicio;
        this.duracao = duracao;*/
    }

    @Override
    public boolean comparar(AbstractTile obj) {
        boolean sup = super.comparar(obj);
        CifraTile o = (CifraTile) obj;
        return sup && (acorde.equals(o.acorde)) && (numRepeticoes == o.numRepeticoes);
    }

    static final String KEY_JSON_ACORDE = "a";
    static final String KEY_JSON_NUM_REPETICOES = "nr";

    @Override
    public JsonElement serialize(AbstractTile src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject res = (JsonObject) super.serialize(src, typeOfSrc, context);

        //uso abreviações para o json ficar menor (a - acordes, nr - numRepeticoes)
        res.addProperty(KEY_JSON_ACORDE, ((CifraTile) src).acorde.toStringNotacao());

        //Caso o numero de repetições seja == 1 (marioria das vezes), não adiciona ele ao json pois vira o valor default, economizando no tamanho do json (otimização)
        if (((CifraTile) src).numRepeticoes > 1) {
            res.addProperty(KEY_JSON_NUM_REPETICOES, ((CifraTile) src).numRepeticoes);
        }
        return res;
    }

    @Override
    public AbstractTile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AbstractTile abs = super.deserialize(json, typeOfT, context);

        JsonObject obj = json.getAsJsonObject();
        String acordeStrNotacao = obj.get(KEY_JSON_ACORDE).getAsString();
        Acorde acorde = Acorde.fromNotacaoString(acordeStrNotacao);

        JsonElement jNumRep = obj.get(KEY_JSON_NUM_REPETICOES);
        int numRepeticoes = 1;
        if (jNumRep != null) {      //devido quando numRepeticoes ser igual 1 ele não é inserido no json, testa a nulidade
            numRepeticoes = obj.get(KEY_JSON_NUM_REPETICOES).getAsInt();
        }
        Util.log("acordeStrNotacao: " + acordeStrNotacao + ", acorde: " + acorde + ", numRepeticoes: " + numRepeticoes);

        CifraTile res = new CifraTile(acorde, abs.tsInicio, abs.duracao, numRepeticoes);
        return res;
    }


    @Override
    public String toString() {
        return "CifraTile{" +
                "acorde=" + acorde +
                ", tsInicio=" + tsInicio +
                ", duracao=" + duracao +
                ", numRepeticoes=" + numRepeticoes +
                '}';
    }
}