package services.tiles;



import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;


public class Tile extends AbstractTile {

    @Override
    public boolean comparar(AbstractTile obj) {
        boolean res = super.comparar(obj);
        Tile o = (Tile) obj;

        if (botao != null && o.botao != null){
            res = res && (botao.numero == o.botao.numero);
        }
        else {
            res = res && (botao == null && o.botao == null);
        }
        return res;
    }

    public Botao botao;     //referencia para o botão o qual o tile corresponde, usado para ajustar posição X, verificar se acertou o tile, etc

    public List<Tile> tilesAcorde;      //caso este tile forme um acorde, essa lista conterá os outros tiles que compõe o acorde

    //public boolean pausadoEsperandoTocar;        //usado para quando estiver no modo "modoEsperarTile" (q pausa quando toca na linha do tempo esperando o usario tocar). Para liberar ao tocar qndo esta esperando
    public ESTADO_TILE estadoTile = ESTADO_TILE.NORMAL;

    public float posYUIPontuacao = -1f;      //-1f nao computada
    boolean jaComputouPosYUIPontuacao = false;


    public enum ESTADO_TILE {NORMAL, PAUSADO_ESPERANDO_TOCAR, TOCADO_APOS_PAUSAR_ESPERAR}

    public Tile(long tsInicio, long duracao, Botao botao) {
        super(tsInicio, duracao);
        this.botao = botao;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tsInicio: " + tsInicio +
                ", duracao: " + duracao +
                ", botao: " + (botao != null ? botao.getNotaExibir() : "null") +
                ", botao.numero: " + (botao != null ? botao.numero : -1) +
                ", estado: " + estadoTile +
                ", tilesAcorde: " + (tilesAcorde != null ? ("size: " + tilesAcorde.size() + ", " + getTilesAcordersStr()/*Arrays.toString(tilesAcorde.toArray())*/) : "null") +
                '}';
    }

    String getTilesAcordersStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (tilesAcorde != null) {
            for (Tile t : tilesAcorde) {
                sb.append(t.botao.numero + ", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public String toShortString() {
        return "Tile{ nota: " + botao.getNotaExibir() + ", .tsInicio: " + tsInicio + " ms }";
    }

    static final String KEY_JSON_NUMBOTAO = "nb";
    @Override
    public JsonElement serialize(AbstractTile src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject res = (JsonObject)super.serialize(src, typeOfSrc, context);
        res.addProperty(KEY_JSON_NUMBOTAO,((Tile)src).botao.numero);
        return res;
    }

    @Override
    public AbstractTile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //Util.log("deserialize");
        AbstractTile abs = super.deserialize(json, typeOfT, context);

        JsonObject obj = json.getAsJsonObject();
        //Util.log("obj: " + obj.toString());
        int numBotao = obj.get(KEY_JSON_NUMBOTAO).getAsInt();

        //Botao b = Util.aa().teclado.getLayoutConfiguration().getBotaoByNumero(numBotao);
        Botao b = getBotaoByNumero(numBotao);
        Tile res = new Tile(abs.tsInicio, abs.duracao, b);
        return res;
    }

    public Botao getBotaoByNumero(int numBotao) {
        Botao res = new Botao(numBotao);
        return res;
    }
}
