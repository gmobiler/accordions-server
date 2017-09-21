package services.tiles;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.inject.Singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import controllers.HomeController;
import play.Environment;
import play.libs.Json;
import services.util.Pair;
import services.util.RawValueGM;
import services.util.Util;

@Singleton
public class BancoMusicasMemoria {

    //Todas as estruturas de representam diferentes "visões" do banco de dados (musicas). Cada estrutura visa otimizar a performance em diferentes tipos de requisições.
    //public volatile List<String> listaMetaInfoOrdenadaVersaoAtualizacao;        //lista de musicas contendo apenas os campos de meta-infos ordenadas pela versao de atualizacao no banco. (Usado para o metodo de atualizar lista de musicas)
    //public volatile List<Pair<Integer, RawValue>> listaMetaInfoOrdenadaVersao = new ArrayList<>();      //lista de musicas contendo apenas os campos de meta-infos ordenadas pela versao de atualizacao no banco. (Usado para o metodo de atualizar lista de musicas)
    public volatile List<Pair<Integer, RawValueGM>> listaMetaInfoOrdenadaVersao = new ArrayList<>();      //lista de musicas contendo apenas os campos de meta-infos ordenadas pela versao de atualizacao no banco. (Usado para o metodo de atualizar lista de musicas)
    public volatile HashMap<Long, String> mapTracksMusicas;                   //map onde key -> id da musica, value -> json contendo apenas as tracks da musica (sem meta infos). Usado para no metodo de obter tracks da musica. (Usado para o metodo de obter musica antes de iniciar o jogo e também o preview)
    public volatile HashMap<Long, Integer> mapNumMoedasMusicas;                 //map onde key -> id da musica, value -> numero de moedas para comprar a musica. Usado quando o usuario for confirmar a compra de uma musica, ele faz uma requisição para saber a quantidade de moedas. Usa isso pois o numero de moedas vai nas meta-infos, forém como são armazenadas nas shared-prefs, não confia neste valor (usa apenas para a UI).

    //algum dia pode ser necessario usar um map onde se queira obter as meta-infos de uma dada musica (ate agora nou houve necessidade por isso nao criei)
    //HashMap<Long, String> mapMetaInfosMusicas;

    public BancoMusicasMemoria() {
        Util.log("Inicializando o banco!");

        //listaMetaInfoOrdenadaVersaoAtualizacao = new ArrayList<>();
        listaMetaInfoOrdenadaVersao = new ArrayList<>();
        mapTracksMusicas = new HashMap<>();
        mapNumMoedasMusicas = new HashMap<>();

        //Cria uma lista de musicas jsons txt no disco para simular o banco de musicas no projeto. TODO Levar esses arquivos para dentro dos arquivos do projeto, como será no final
        //Adiciona de maneira contraria de proposito para testar o algoritmo de ordenacao por versaoAtualizacaoBanco
        //Deleta todos os arquivos da pasta de saida
        boolean recriarJsonsBancoDev = false;
        Util.log("isProd: " + Environment.simple().isProd());
        if (recriarJsonsBancoDev) {
            Util.deletarTodosArquivosPasta(HomeController.PASTA_SAIDA_JSON);

            int idGen = 1;
            int num = 3;
            CategoriaMusica[] categorias = CategoriaMusica.values();
            Random r = new Random();
            for (int i = 0; i < num; i++) {
                String jSon = debugGerarJson(idGen++,Util.getRandomElement(categorias), num - i);
            }
            num = 5;
            for (int i = 0; i < num; i++) {
                String jSon = debugGerarJson(idGen++, Util.getRandomElement(categorias),1);
            }
            num = 4;
            for (int i = 0; i < num; i++) {
                String jSon = debugGerarJson(idGen++, Util.getRandomElement(categorias),4);
            }
        }
        //Fim Cria

        //Daqui pra baixo é como será feito em produção. (por isso nao aproveita o laço anterior de criação de jsons, pois ele nao estara em producao)
        //Lê todos os arquivos json do disco para a memória e preenche as estruturas de dados
        //File pastaJSons = new File(HomeController.PASTA_SAIDA_JSON);
        File pastaJSons = new File(Environment.simple().resource("private_resources/bancomusicas").getFile());
        File[] jSonsFiles = pastaJSons.listFiles();
        for (File f : jSonsFiles) {
            Util.log("fileName:  " + f.getAbsolutePath());

            testarLerJSonStream(f);

            String jSon = Util.readFroFile(f.getAbsolutePath());

            adicionarMusicaAoBancoMemoria(jSon);
            //Obtém o id e versaoAtualizacaoBanco do json lido
            /*mapMusicasJson.put(id, jSon);
            listaOrdenadaMusicasJson.add(jSon);*/
        }

        //Ordena a lista de meta-infos pela versaoAtualizacaoBanco
        //       Collections.sort(listaMetaInfoOrdenadaVersaoAtualizacao, Comparator.comparingInt(o -> Json.parse(o).get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt()));


        Util.log("ANTES SORT");
        Util.log(listaMetaInfoOrdenadaVersao.toString());
        Collections.sort(listaMetaInfoOrdenadaVersao, new Comparator<Pair<Integer, RawValueGM>>() {
            public int compare(Pair<Integer, RawValueGM> o1, Pair<Integer, RawValueGM> o2) {
                return Integer.compare(o1.first, o2.first);
            }
        });
        Util.log("DEPOIS SORT");
        Util.log(listaMetaInfoOrdenadaVersao.toString());

        /*Util.log(listaMetaInfoOrdenadaVersaoAtualizacao.toString());
        Collections.sort(listaMetaInfoOrdenadaVersaoAtualizacao, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return Integer.compare(Json.parse(o1).get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt(),
                        Json.parse(o2).get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt());
            }
        });*/

          /*      Util.log("DEPOIS SORT");
        //Util.log(listaMetaInfoOrdenadaVersaoAtualizacao.toString());*/

    }

    private void testarLerJSonStream(File f) {
        Util.log("testarLerJSonStream f: " + f + ", f.length(): " + f.length() + " bytes");

        int numite = 0;
        try {
            //InputStream stream =getClass().getResourceAsStream(FILENAME);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(f)));
            Gson gson = new GsonBuilder().create();

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                numite++;
                JsonToken nextToken = jsonReader.peek();
                Util.log("nextToken: " + nextToken.toString());

                if (JsonToken.NAME.equals(nextToken)) {
                    String name = jsonReader.nextName();
                    Util.log("name: " + name);
                } else if (JsonToken.STRING.equals(nextToken)) {
                    String string = jsonReader.nextString();
                    Util.log("string: " + string);

                } else if (JsonToken.NUMBER.equals(nextToken)) {
                    long number = jsonReader.nextLong();
                    Util.log("number: " + number);
                }
                else {
                    jsonReader.skipValue();
                }



                //apenas debug safe loop infimnot
                if (numite > 1000) {
                    break;
                }
            }
            jsonReader.close();
        } catch (Exception e) {

        }
    }

    //cria um json no disco fake
    private String debugGerarJson(long id, CategoriaMusica cat, int versaoAtualizacaoBanco) {
        Util.log("debugGerarJson() id: " + id + ", versaoAtualizacaoBanco: " + versaoAtualizacaoBanco);
        MusicaTiles musicaTiles = GeradorTeste.getFakeGravacaoEuEstouSemAmor();
        musicaTiles.id = id;        //FAKE APENAS TESTE
        musicaTiles.versaoAtualizacaoBanco = versaoAtualizacaoBanco;
        musicaTiles.categoria = cat;
        GeradorTeste.posProcessarListaTiles(musicaTiles, /*Util.dev ? 100 :*/ 2000);

        //Util.log("musicaTiles: " + musicaTiles);
        //String jSon = musicaTiles.gerarJSon(null,false);
        String jSon = musicaTiles.toJson();

        //cria o arquivo no disco
        Util.saveToFile(HomeController.PASTA_SAIDA_JSON + "/" + gerarFileName(musicaTiles) + ".json", jSon);
        return jSon;
    }

    //helper para gerar o nome do arquivo com base no id e nome da musica
    public static String gerarFileName(MusicaTiles musicaTiles) {
        String res = musicaTiles.id + "_" + musicaTiles.titulo
                .replace(" ", "_");
        res = Normalizer.normalize(res, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return res;
    }

    //Cria todas as entradas para as diferentes visões do banco em memoria
    public void adicionarMusicaAoBancoMemoria(String json) {
        Util.log("adicionarMusicaAoBancoMemoria()");
        ObjectNode jsonNodeMeta = (ObjectNode) Json.parse(json);
        ObjectNode jsonNodeTracks = (ObjectNode) Json.parse(json);     //clone

        long id = jsonNodeMeta.get(MusicaTiles.KEY_JSON_ID).asLong();

        //Remove as tracks, mantendo apenas as meta-infos
        jsonNodeMeta.remove(MusicaTiles.KEY_JSON_TRACK_TECLADO);
        jsonNodeMeta.remove(MusicaTiles.KEY_JSON_TRACK_CIFRAS);
        //listaMetaInfoOrdenadaVersao.add(Pair.create(jsonNodeMeta.get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt(), new RawValue(jsonNodeMeta)));
        //listaMetaInfoOrdenadaVersao.add(Pair.create(jsonNodeMeta.get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt(), new RawValueGM(jsonNodeMeta)));
        listaMetaInfoOrdenadaVersao.add(Pair.create(jsonNodeMeta.get(MusicaTiles.KEY_VERSAO_ATUALIZACAO_BANCO).asInt(), new RawValueGM(jsonNodeMeta.toString())));
        //listaMetaInfoOrdenadaVersaoAtualizacao.add(jsonNodeMeta.toString());

        //Adiciona o valor moedas
        mapNumMoedasMusicas.put(id, jsonNodeMeta.get(MusicaTiles.KEY_VAL_MOEDAS).asInt());

        //Remove as meta, mantendo apenas as tracks
        jsonNodeTracks.retain(MusicaTiles.KEY_JSON_TRACK_TECLADO, MusicaTiles.KEY_JSON_TRACK_CIFRAS);
        mapTracksMusicas.put(id, jsonNodeTracks.toString());

        /*for (JsonNode node : jsonNode) {
            Util.log("node: " + node);
            if (node instanceof ObjectNode) {
                ObjectNode object = (ObjectNode) node;
                object.remove("tt");
            }

        }*/

        //Util.log("adicionarMusicaAoBancoMemoria() jsonNode: " + jsonNode);

        /*Util.log("jsonNode.findValue(\"tt\"): "+ jsonNode.findValue("tt"));
        Util.log("jsonNode.findPath(\"tt\"): "+ jsonNode.findPath("tt"));
        JsonNode trackTeclado = jsonNode.findPath("tt");
        JsonNode trackBaixos = jsonNode.findPath("tb");
        JsonNode trackCifras = jsonNode.findPath("tc");
        Util.log("trackTeclado: " + trackTeclado);
        Util.log("trackBaixos: " + trackBaixos);
        Util.log("trackCifras: " + trackCifras);*/

      /*  Iterator<Entry<String, JsonNode>> nodes = jsonNode.fields();
        while(nodes.hasNext()){
            Map.Entry<String, JsonNode> entry = nodes.next();
            Util.log("entry: " + entry);

            //Util.log("key --> " + entry.getKey() + ", value-->" + entry.getValue());
        }

        if (true){
            return;
        }*/


        // Util.log("ao final o json ficou jsonNode: " + jsonNode);

       /* JsonNode trackTeclado = jsonNode.findPath("tt");
        Util.log("trackTeclado: " + trackTeclado);*/
    }

    /*//ordenarLista - true apenas na ultima chamada a addMusica, pois ordena a lista conforme o ts de atualização
    public void addMusica(long id, MusicaTiles musicaTiles, long tsAtualizacaoBanco, boolean ordenarLista) {
        Util.log("addMusica(), id: " + id + ", musicaTiles: " + musicaTiles);
        String jSon = musicaTiles.toJson();
        mapMusicasJson.put(id, jSon);
        listaOrdenadaMusicasJson.add(jSon);

        //Se ordenarLista, executa o sort na lista para ordenar por tempo de atualização do banco
        if (ordenarLista) {
            Collections.sort(listaOrdenadaMusicasJson, (o1, o2) -> {
                //tsAtualizacaoBanco
                int x = 3;
                if (true) {
                    x = 5;
                }
                return 0;
            });
        }
    }*/

}
