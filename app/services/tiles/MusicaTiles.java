package services.tiles;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import controllers.HomeController;
import services.util.Util;

/**
 * Created by Gilian on 30/07/2017.
 */

public class MusicaTiles {

    public List<Tile> trackTeclado;
    public List<Tile> trackBaixos;
    public List<CifraTile> trackCifras;
    //public long duracaoTotal;

    //atributos para ui e json. DEPRECATED, AGORA A MÚSICA E SUAS META INFOS SÃO DOIS OBJETOS DIFERENTES POIS A MUSICA EM SÍ SÓ É CARREGADA NA HORA DE TOCAR E FAZ UMA REQUISIÇÃO ESPECIFICA.
    public long id;
    public String titulo, compositores;
    public int valorMoedas;

    //EXPERIMENTAL - A versão da musica no banco. Usada para atualização da lista de musicas no cliente, usando o algoritmo descrito na pasta AndroidAccordionResources
    public int versaoAtualizacaoBanco;

    //construtor completo para json
    public MusicaTiles(List<Tile> trackTeclado, List<Tile> trackBaixos, List<CifraTile> trackCifras, long id, String titulo, String compositores, int valorMoedas) {
        this(trackTeclado, trackBaixos, trackCifras);
        this.id = id;
        this.titulo = titulo;
        this.compositores = compositores;
        this.valorMoedas = valorMoedas;
    }

    public MusicaTiles(List<Tile> trackTeclado, List<Tile> trackBaixos, List<CifraTile> trackCifras) {
        this.trackTeclado = trackTeclado;
        this.trackBaixos = trackBaixos;
        this.trackCifras = trackCifras;
    }

    //TODO FALTA LEVAR EM CONTA A TRACK DOS BAIXOS E CIFRA
    public long getDuracaoTotal(){
        long SILENCIO_FINAL_MUSICA = 2000;
        //duracaoTotalMusica = (res.get(res.size() - 1).tsInicio + res.get(res.size() - 1).duracao) + SILENCIO_FINAL_MUSICA;        //nao pega o tempo do primeiro tile, e sim 0 q é o inicio de fato
        Util.log("getDuracaoTotal() trackTeclado.size(): " + trackTeclado.size());
        long duracaoTotalMusica = (trackTeclado.get(trackTeclado.size() - 1).tsInicio + trackTeclado.get(trackTeclado.size() - 1).duracao) + SILENCIO_FINAL_MUSICA;        //nao pega o tempo do primeiro tile, e sim 0 q é o inicio de fato
        return duracaoTotalMusica;
    }


    //O JSon gerado contém a track do teclado, a track das cifras, e a track de improvido dos baixos (a track do ritmo dos baixos é gerada em realtime com base na track das cifras)
    public static final String FILENAME_DEBUG_JSON = "musicaJSonNovo.txt";

    public static final String KEY_JSON_ID = "id";
    public static final String KEY_JSON_TITULO = "tit";
    public static final String KEY_JSON_COMPOSITORES = "comp";
    public static final String KEY_JSON_TRACK_TECLADO = "tt";
    public static final String KEY_JSON_TRACK_CIFRAS = "tc";
    public static final String KEY_VERSAO_ATUALIZACAO_BANCO = "v";
    public static final String KEY_VAL_MOEDAS= "vm";

    static final JsonSerializer<MusicaTiles> musicaTilesJsonSerializer = new JsonSerializer<MusicaTiles>() {
        @Override
        public JsonElement serialize(MusicaTiles src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject res = new JsonObject();

            //DEBUG LIMITA O TAMANHO DAS LISTAS PARA EXIBIR NO LOGCAT
            //boolean dbg = true;
            boolean dbg = false;
            if (dbg) {
                int tam = 5;
                while (src.trackTeclado.size() > tam) {
                    src.trackTeclado.remove(src.trackTeclado.size() - 1);
                }

                while (src.trackCifras.size() > tam) {
                    src.trackCifras.remove(src.trackCifras.size() - 1);
                }
            }
            //FIM DEBUG LIMITA O TAMANHO DAS LISTAS PARA EXIBIR NO LOGCAT

            //Serializa os metadados
            res.addProperty(KEY_JSON_ID, src.id);
            res.addProperty(KEY_JSON_TITULO, src.titulo);
            res.addProperty(KEY_JSON_COMPOSITORES, src.compositores);
            res.addProperty(KEY_VERSAO_ATUALIZACAO_BANCO,src.versaoAtualizacaoBanco);

            //DEBUG - Adiciona o numero de moedas
            res.addProperty(KEY_VAL_MOEDAS,src.valorMoedas);


            //..res.addProperty("score",src.compositores);

            //Serializa as listas
            JsonElement trackTecladoJson = context.serialize(src.trackTeclado);
            JsonElement trackCifrasJson = context.serialize(src.trackCifras);

            res.add(KEY_JSON_TRACK_TECLADO, trackTecladoJson);
            res.add(KEY_JSON_TRACK_CIFRAS, trackCifrasJson);

            return res;
        }
    };

    public static MusicaTiles fromJson(String json) {
        Util.log("fromJson(), json: " + json);
        if (json == null){
            return null;
        }

        //debug, le go arquivo

        GsonBuilder builder = new GsonBuilder();

        JsonDeserializer<MusicaTiles> deserializer = new JsonDeserializer<MusicaTiles>() {
            public MusicaTiles deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject j = json.getAsJsonObject();

                long id = j.get(KEY_JSON_ID).getAsLong();
                String tit = j.get(KEY_JSON_TITULO).getAsString();
                String compositores = j.get(KEY_JSON_COMPOSITORES).getAsString();
                int versaoAtualizacao = j.get(KEY_VERSAO_ATUALIZACAO_BANCO).getAsInt();

                JsonArray ttJArray = j.get(KEY_JSON_TRACK_TECLADO).getAsJsonArray();
                JsonArray tcJArray = j.get(KEY_JSON_TRACK_CIFRAS).getAsJsonArray();
                Util.log("ttArray: " + ttJArray.toString());
                Util.log("tcJArray: " + tcJArray.toString());

                /*GsonBuilder builder = new GsonBuilder()
                        .registerTypeAdapter(new TypeToken<List<Tile>>(){}.getType(), new Tile(-1, -1, null))       //o tile é apenas pois ele implemneta deserializer... depois posso fazer um atirbuto estatico da classe Tile
                        .registerTypeAdapter(new TypeToken<List<CifraTile>>(){}.getType(), new CifraTile(null,-1, -1, -1));       //*/
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Tile.class, new Tile(-1, -1, null));
                builder.registerTypeAdapter(CifraTile.class, new CifraTile(null,-1, -1, -1));
                Gson gson = builder.create();

                List<Tile> trackTeclado = gson.fromJson(ttJArray, new TypeToken<List<Tile>>() {}.getType());
                List<CifraTile> trackCifras = gson.fromJson(tcJArray, new TypeToken<List<CifraTile>>() {}.getType());

                //Gera a track baixos
                //List<Tile> trackBaixos = GerenciadorTiles.gerarListaTilesBaixos(trackCifras);
                List<Tile> trackBaixos = new ArrayList<>();     //FIXME POR ENQUANTO NAO GERO OS BAIXOS POIS MEXE COM MIDI

                MusicaTiles res = new MusicaTiles(trackTeclado, trackBaixos, trackCifras, id, tit, compositores, -1);
                res.versaoAtualizacaoBanco = versaoAtualizacao;
                return res;
                //return new MusicaTiles(trackTeclado, trackBaixos,trackCifras, id, tit, compositores, -1);
            }
        };

        builder.registerTypeAdapter(MusicaTiles.class, deserializer);


        Gson gson = builder.create();
        MusicaTiles res = gson.fromJson(json, MusicaTiles.class);

        return res;
    }

    public String toJson() {
        return MusicaTiles.toJson(this);
    }

    public static String toJson(MusicaTiles mt) {
        GsonBuilder builder = new GsonBuilder();

        //custom serializer dos tiles teclado
        if (mt.trackTeclado != null && !mt.trackTeclado.isEmpty()) {
            builder.registerTypeAdapter(mt.trackTeclado.get(0).getClass(), mt.trackTeclado.get(0));
        }
        //custom serializer dos tiles cifra
        if (mt.trackCifras != null && !mt.trackCifras.isEmpty()) {
            builder.registerTypeAdapter(mt.trackCifras.get(0).getClass(), mt.trackCifras.get(0));
        }
        //custom serializer da musica tiles
        builder.registerTypeAdapter(mt.getClass(), musicaTilesJsonSerializer);


        //extras
        builder.setPrettyPrinting();
        builder.disableHtmlEscaping();

        Gson gson = builder.create();
        String res = gson.toJson(mt);

        return res;
    }

   /* //POJO para objeto usado para criar o json
    public static class MusicaJSon {
        public long id;
        public String titulo, compositores;
        //public int valorMoedas;           //valor moedas agora não vem direto do json, mas sim de outro lugar (acessado via id) para melhor segurança e facilidade em atualizar preços

        public List<TileJSon> trackTeclado;
        public List<CifraTile> trackCifras;

        //public String urlPreview;     //acho q o preview será usando samples ao vivo...


        public MusicaJSon(long id, String titulo, String compositores, List<TileJSon> trackTeclado, List<CifraTile> trackCifras) {
            this.id = id;
            this.titulo = titulo;
            this.compositores = compositores;
            this.trackTeclado = trackTeclado;
            this.trackCifras = trackCifras;
        }
    }*/

    @Override
    public String toString() {
        return "MusicaTiles{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", compositores='" + compositores + '\'' +
                ", valorMoedas=" + valorMoedas +
                ", trackTeclado=" + Util.listToString(trackTeclado) +
                ", trackBaixos=" + Util.listToString(trackBaixos) +
                ", trackCifras=" + Util.listToString(trackCifras) +
                '}';
    }


    public boolean comparar(Object obj) {
        MusicaTiles o = (MusicaTiles) obj;

        boolean res = true;
        if (id != o.id){
            res = false;
            Util.log("São diferentes pois tem id diferentes");
            return res;
        }
        if (!titulo.equals(o.titulo)){
            res = false;
            Util.log("São diferentes pois tem titulos diferentes");
            return res;
        }
        if (!compositores.equals(o.compositores)){
            res = false;
            Util.log("São diferentes pois tem compositores diferentes");
            return res;
        }

        /*if (valorMoedas != o.valorMoedas){
            res = false;
            Util.log("São diferentes pois tem valorMoedas diferentes");
            return res;
        }*/

        if (trackTeclado.size() != o.trackTeclado.size()){
            res = false;
            Util.log("São diferentes pois tem trackTeclado.size() diferentes");
            return res;
        }
        if (trackBaixos.size() != o.trackBaixos.size()){
            res = false;
            Util.log("São diferentes pois tem trackBaixos.size() diferentes");
            return res;
        }
        if (trackCifras.size() != o.trackCifras.size()){
            res = false;
            Util.log("São diferentes pois tem trackBaixos.size() diferentes");
            return res;
        }

        res = innerCompararListTile(trackTeclado, o.trackTeclado);
        if (!res){
            return res;
        }
        res = innerCompararListTile(trackBaixos, o.trackBaixos);
        if (!res){
            return res;
        }

        res = innerCompararListCifra(trackCifras, o.trackCifras);
        if (!res){
            return res;
        }

        /*for (int i = 0; i < trackTeclado.size(); i++) {
            Tile t1 = trackTeclado.get(i);
            Tile t2 = o.trackTeclado.get(i);
            if (!t1.comparar(t2)){
                res = false;
                Util.log("São diferentes pois tem tiles diferentes, t1: " + t1 + ", t2: " + t2);
                return res;
            }
        }*/

        return res;
    }

    static boolean innerCompararListCifra(List<CifraTile> l1, List<CifraTile> l2){
        List<AbstractTile> l1Abs = (List<AbstractTile>)(List<?>) l1;
        List<AbstractTile> l2Abs= (List<AbstractTile>)(List<?>) l2;
        return innerCompararList(l1Abs, l2Abs);
    }
    static boolean innerCompararListTile(List<Tile> l1, List<Tile> l2){
        List<AbstractTile> l1Abs = (List<AbstractTile>)(List<?>) l1;
        List<AbstractTile> l2Abs= (List<AbstractTile>)(List<?>) l2;
        return innerCompararList(l1Abs, l2Abs);
    }
    static boolean innerCompararList(List<AbstractTile> l1, List<AbstractTile> l2){
        boolean res = true;
        if (l1.size() != l2.size()){
            res = false;
            Util.log("São diferentes pois tem l1.size() diferentes l2.size");
            return res;
        }

        for (int i = 0; i < l1.size(); i++) {
            AbstractTile t1 = l1.get(i);
            AbstractTile t2 = l2.get(i);
            if (!t1.comparar(t2)){
                res = false;
                Util.log("São diferentes pois tem tiles diferentes, t1: " + t1 + ", t2: " + t2);
                return res;
            }
        }
        return res;
    }


}
