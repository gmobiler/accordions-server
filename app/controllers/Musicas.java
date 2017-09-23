package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RawValue;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.tiles.BancoMusicasMemoria;
import services.tiles.MusicaTiles;
import services.util.Pair;
import services.util.RawValueGM;
import services.util.Util;

public class Musicas extends Controller {
    private final BancoMusicasMemoria bancoMusicas;

    //injeta o banco singleton no controller via construtor
    @Inject
    public Musicas(BancoMusicasMemoria bancoMusicas) {
        this.bancoMusicas = bancoMusicas;
    }



    //Debug - Obtém a musica completa, o que inclui meta-info e tracks dado o id da musica. Por enquanto como não há nenhum caso de uso que necessite de ambas as informações de uma só vez, será feito na concatenação)
    public Result get(long id) {
        Util.log("get() id: " + id);
        //Faz na gambiarra pois não tem uma "visão" para o arquivo completo. então concatena o meta e as tracks
        JsonNode jsMeta = null;
        for (Pair<Integer,RawValueGM> meta : bancoMusicas.listaMetaInfoOrdenadaVersao){
            JsonNode js = Json.parse(meta.second.toString());
            if (js.get(MusicaTiles.KEY_JSON_ID).asLong() == id){
                jsMeta = js;
                break;
            }
        }
       /* for (String meta : bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao){
            JsonNode js = Json.parse(meta);
            if (js.get(MusicaTiles.KEY_JSON_ID).asLong() == id){
                jsMeta = js;
                break;
            }
        }*/

        if (jsMeta == null){
            Util.log("opa, jsMeta == null");
            return badRequest("Musica com id: " + id + ", não encontrado!");
        }
        else{
            Util.log("jsMeta existe : " + jsMeta);
            ObjectNode jsTracks = (ObjectNode)Json.parse(bancoMusicas.mapTracksMusicas.get(id));
            return ok(((ObjectNode)jsMeta).setAll(jsTracks));
        }
    }

    public Result tracks(long id) {
        return ok(bancoMusicas.mapTracksMusicas.get(id)).as("application/json");
    }
    public Result valorMoedas(long id) {
        return ok(Integer.toString(bancoMusicas.mapNumMoedasMusicas.get(id))).as("application/json");
    }
    public Result meta(long id) {
        JsonNode jsMeta = null;
        /*for (String meta : bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao){
            JsonNode js = Json.parse(meta);
            if (js.get(MusicaTiles.KEY_JSON_ID).asLong() == id){
                jsMeta = js;
                break;
            }
        }*/
        for (Pair<Integer,RawValueGM> meta : bancoMusicas.listaMetaInfoOrdenadaVersao){
            JsonNode js = Json.parse(meta.second.toString());       //FIXME lento!
            //JsonNode js = (JsonNode)meta.second.rawValue();
            if (js.get(MusicaTiles.KEY_JSON_ID).asLong() == id){
                jsMeta = js;
                break;
            }
        }

        if (jsMeta == null){
            return badRequest("Musica com id: " + id + ", não encontrado!");
        }
        else{
            return ok(jsMeta);
        }
    }

    //Executa o algoritmo da atualizacao descrito na pasta de resources. Recebe um inteiro indicando a versaoAtualizacaoBanco mais recente que o usuario tem baixado. Retorna uma lista de musicas contendo as novas musicas que foram adicionadas ao banco a partir da versao enviada pelo usuario
    public Result checkForUpdates(int versaoAtualizacaoBancoCliente) {
        Util.log("checkForUpdates() versaoAtualizacaoBancoCliente: " + versaoAtualizacaoBancoCliente + ", ultima versao no banco: " + bancoMusicas.listaMetaInfoOrdenadaVersao.get(bancoMusicas.listaMetaInfoOrdenadaVersao.size()-1).first);
        Util.log("bancoMusicas.listaMetaInfoOrdenadaVersao: " + bancoMusicas.listaMetaInfoOrdenadaVersao);

        //OTIMIZAÇÃO FAST SKIP - Como é muito comum o usuario estar atualizado, e essa ser a requisição mais chamada, faz logo uma comparação com a ultima versão no banco, evitando de alocar todos os objetos e laços da resposta generica)
        if (bancoMusicas.listaMetaInfoOrdenadaVersao.get(bancoMusicas.listaMetaInfoOrdenadaVersao.size()-1).first == versaoAtualizacaoBancoCliente){
            Util.log("Opa, caiu no fast skip, sem atualização. (Versao do usuario igual a ultima versão do banco");
            return ok("[]").as("application/json");
        }
        //Itera do final para o inicio adicionando a lista de resposta, todas as musicas com versao de atualização maior que a enviada pelo cliente
        List<RawValueGM> res = new ArrayList<>();
        ListIterator<Pair<Integer, RawValueGM>> li = bancoMusicas.listaMetaInfoOrdenadaVersao.listIterator(bancoMusicas.listaMetaInfoOrdenadaVersao.size());
        Pair<Integer, RawValueGM> m;
        while(li.hasPrevious()){
            m = li.previous();
            if (versaoAtualizacaoBancoCliente >= m.first) {
                break;
            }
            res.add(m.second);
        }

        Util.log("res.size(): " + res.size());
        return ok(Json.toJson(res));
    }

    //Executa o algoritmo da atualizacao descrito na pasta de resources. Recebe um inteiro indicando a versaoAtualizacaoBanco mais recente que o usuario tem baixado. Retorna uma lista de musicas contendo as novas musicas que foram adicionadas ao banco a partir da versao enviada pelo usuario
    public Result checkForUpdatesOLD(int versaoAtualizacaoBancoCliente) {
        Util.log("checkForUpdates() versaoAtualizacaoBancoCliente: " + versaoAtualizacaoBancoCliente);
       /* List<Pair<Integer, String>> listaMetaOrdenada = new ArrayList<>();
        for (String meta : bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao){
            JsonNode js = Json.parse(meta);
            int versaoAtualizacao = js.get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt();
            listaMetaOrdenada.add(Pair.create(versaoAtualizacao, meta));
        }
        Util.log("listaMetaOrdenada: " + listaMetaOrdenada);*/
        Util.log("bancoMusicas.listaMetaInfoOrdenadaVersao: " + bancoMusicas.listaMetaInfoOrdenadaVersao);


        //Itera do final para o inicio adicionando a lista de resposta, todas as musicas com versao de atualização maior que a enviada pelo cliente
        //List<String> res = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
       // TypeFactory typeFactory = objectMapper.getTypeFactory();
        ObjectNode dataTable = objectMapper.createObjectNode();
        ArrayNode arrayNode =  dataTable.putArray("teste");

        //arrayNode.addRawValue(new RawValue(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0)));
        //JsonNode actualObj = mapper.readTree("{\"k1\":\"v1\"}");
        //arrayNode.add(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0));
        //arrayNode.add("aloka 2");

        //List<JsonNode> res = new ArrayList<>();
        List<RawValueGM> res = new ArrayList<>();
        //ListIterator<Pair<Integer, String>> li = listaMetaOrdenada.listIterator(listaMetaOrdenada.size());
        //ListIterator<Pair<Integer, RawValue>> li = bancoMusicas.listaMetaInfoOrdenadaVersao.listIterator(bancoMusicas.listaMetaInfoOrdenadaVersao.size());
        ListIterator<Pair<Integer, RawValueGM>> li = bancoMusicas.listaMetaInfoOrdenadaVersao.listIterator(bancoMusicas.listaMetaInfoOrdenadaVersao.size());
        Pair<Integer, RawValueGM> m;
        while(li.hasPrevious()){
            m = li.previous();
            //if (versaoAtualizacaoBancoCliente <= m.first) {
            if (versaoAtualizacaoBancoCliente >= m.first) {
                break;
            }
            res.add(m.second);
            //res.add(Json.parse(m.second));
            //res.add(m.second);
        }
        if (true){
            return ok(Json.toJson(res));
            //return ok((JsonNode)bancoMusicas.listaMetaInfoOrdenadaVersao.get(0).second.rawValue()).as("application/json");
            //return ok(arrayNode);
            /*List<String> l = new ArrayList<>();
            l.add(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0));
            return ok(Json.toJson(l)).as("application/json");*/
            //return ok(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0)).as("application/json");
            //return ok(Json.toJson(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0)));
            //return ok(Json.toJson(res));
            //return ok(Json.toJson(res)).as("application/json");
        }
        else{
            return ok("aloka");
        }
    }
}
