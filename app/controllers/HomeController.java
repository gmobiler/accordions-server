package controllers;

import com.google.inject.Inject;

import java.util.HashMap;

import play.libs.Json;
import play.mvc.*;

import services.tiles.BancoMusicasMemoria;
import services.tiles.GeradorTeste;
import services.tiles.MusicaTiles;
import services.util.Util;
import services.util.Util.OnObjectListener;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final BancoMusicasMemoria bancoMusicas;



    //injeta o banco singleton no controller via construtor
    @Inject
    public HomeController(BancoMusicasMemoria bancoMusicas) {
        this.bancoMusicas = bancoMusicas;
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("application");
    //HashMap<Integer, String> bancoMusicas = new HashMap<>();
    public Result index() {
        //Cria o "banco" de musicas para ficar na memoria
        Util.log("bancoMusicas: " + bancoMusicas);
        logger.info("PUNHETINHA DA VOVO");

        if (true){
            return ok(bancoMusicas.mapTracksMusicas.entrySet().iterator().next().getValue()).as("application/json");
            //return ok(bancoMusicas.listaMetaInfoOrdenadaVersaoAtualizacao.get(0)).as("application/json");
            //return ok(debugGerarJson(1)).as("application/json");
        }
        for (int i = 0; i < 10; i++) {
            MusicaTiles musicaTiles = carregarMusicaTile(1,null);
        }

        MusicaTiles musicaTiles = carregarMusicaTile(1,null);
       // String jSon = musicaTiles.gerarJSon(null,false);

       // return ok(jSon).as("application/json");
        //return ok(Json.toJson(jSon));
        //return ok(index.render("Your new application is ready. ALOKA"));
        return ok("");
    }

    public static final String FILENAME_DEBUG_JSON = "musicaJSonNovo.txt";
    public static final String PASTA_SAIDA_JSON = "D:/saidasJson";


    //NÃO É USADO NO SERVIDOR. APENAS PARA TESTE. Este método entrega uma MusicaTiles a partir do id da musica. Esse id é usado para ir no banco e buscar o json.
    private MusicaTiles carregarMusicaFromJsonId(long id){
        Util.log("carregarMusicaFromJsonId: " + id);
        MusicaTiles musicaTiles = GeradorTeste.getFakeGravacaoEuEstouSemAmor();
        musicaTiles.id = id;        //FAKE APENAS TESTE
        GeradorTeste.posProcessarListaTiles(musicaTiles, /*Util.dev ? 100 :*/ 2000);

        Util.log("musicaTiles: " + musicaTiles);
        //String jSon = musicaTiles.gerarJSon(FILENAME_DEBUG_JSON,false);

        //return MusicaTiles.fromJson(jSon);
        return null;
    }


    private MusicaTiles carregarMusicaTile(int id, final OnObjectListener<MusicaTiles>onFimCarregamento) {
        Util.log("carregarMusicaTile: " + id);
        //pos processa a lista de tiles
        MusicaTiles musicaTiles = GeradorTeste.getFakeGravacaoEuEstouSemAmor();
        musicaTiles.id = id;        //FAKE APENAS TESTE
        //MusicaTiles musicaTiles = getFakeGravacaRapida();
        GeradorTeste.posProcessarListaTiles(musicaTiles, /*Util.dev ? 100 :*/ 2000);


        Util.log("musicaTiles: " + musicaTiles);

       //String jSon = musicaTiles.gerarJSon(FILENAME_DEBUG_JSON,false);
        String jSon = musicaTiles.toJson();
       /*if (true){
           return jSon;
       }
*/
        MusicaTiles musicaTilesFromJson = MusicaTiles.fromJson(Util.readFroFile(PASTA_SAIDA_JSON + "/" + FILENAME_DEBUG_JSON));
        if (musicaTilesFromJson == null){
            Util.log("retornando musicaTilesFromJson == null" );
        }
        boolean compararFirst = musicaTiles.comparar(musicaTilesFromJson);
        //musicaTilesFromJson.gerarJSon(FILENAME_DEBUG_JSON + "_clone.txt",false);
        musicaTilesFromJson.toJson();
        MusicaTiles musicaTilesFromJsonClone = MusicaTiles.fromJson(Util.readFroFile(PASTA_SAIDA_JSON + "/" + FILENAME_DEBUG_JSON));
        boolean compararClone = musicaTilesFromJson.comparar(musicaTilesFromJsonClone);

        boolean compararString = Util.readFroFile(PASTA_SAIDA_JSON + "/" + FILENAME_DEBUG_JSON).
                equals(Util.readFroFile(PASTA_SAIDA_JSON + "/" + FILENAME_DEBUG_JSON + "_clone.txt"));
        Util.log("sao compararFirst: " + compararFirst + ", comprarClone: " + compararClone + ", compararString: " + compararString);


        if (onFimCarregamento != null){
            onFimCarregamento.onObject(musicaTiles);
        }

        return musicaTiles;

    }


    public Result getInfoMusica(long id) {
        return ok(index.render("Your new application is ready. ALOKA"));
    }
}
