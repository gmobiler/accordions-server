package services.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import services.tiles.NotaMusical.GeneroAcorde;
import services.tiles.NotaMusical.TipoSetima;
import services.util.Util;

public class GeradorTeste {

    //Eu estou sem amor
    public static MusicaTiles getFakeGravacaoEuEstouSemAmor() {
        //GentTile g = new GentTile("Eu estou sem amor", "Dorgival Dantas", 353241, 1, 800, Util.aa().teclado.tecladoConfiguration);
        //GentTile g = new GentTile("Eu estou sem amor", "Dorgival Dantas", 353241, 0.2f, 100, Util.aa().teclado.tecladoConfiguration);
        GentTile g = new GentTile("Eu estou sem amor", "Dorgival Dantas", 353241, 1f, 400,1, ThreadLocalRandom.current().nextInt(25, 250 + 1));

        g.addDelta(1000, 19);
        g.addDelta(100, 22);
        g.addDelta(100, 24);    //E
        //g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.C, GeneroAcorde.MAIOR));      //adiciona a cifra sem duração, pois após isso, ajusta a duração para casar com o proximo acorde
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.C, GeneroAcorde.MENOR, TipoSetima.SET_MENOR));
        g.addDelta(100, 22);
        g.addDelta(100, 24);
        g.addDelta(100, 22);
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.G, GeneroAcorde.MAIOR));

        g.addDelta(100, 19);
        g.addDelta(100, 15);
        g.addDelta(100, 17);
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.D, GeneroAcorde.MAIOR));
        g.addDelta(100, 15);
        g.addDelta(100, 17);
        g.addDelta(100, 19);
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.E, GeneroAcorde.MENOR));

        g.addDelta(100, 19);     //B
        g.addDelta(100, 17);
        g.addDelta(100, 15);
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.C, GeneroAcorde.MAIOR));
        g.addDelta(100, 24);    //E
        g.addDelta(100, 24);    //E
        g.addDelta(100, 22);
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.G, GeneroAcorde.MAIOR));

        g.addDelta(100, 19);     //B
        g.addDelta(100, 15);     //G
        g.addDelta(100, 17);     //A
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.D, GeneroAcorde.MAIOR));
        g.addDelta(100, 15);     //G
        g.addDelta(100, 14);     //F#
        g.addDelta(100, 12);     //E
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.E, GeneroAcorde.MENOR));

        //dueto
        g.addDelta(100, 10);     //E
        g.add(g.getLastTileTeclado().tsInicio, 14);         //F#
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.D, GeneroAcorde.MAIOR));
        //dueto
        g.addDelta(100, 12);     //E
        g.add(g.getLastTileTeclado().tsInicio, 15);     //G
        g.addCifra(g.getLastTileTeclado().tsInicio, -1, 1, new Acorde(NotaMusical.E, GeneroAcorde.MENOR));
        //posProcessarListaTiles(g.musicaTiles);


        //Ao final Ajusta a duração dos acordes da cifra para que fique um atras do outro (já que antes fica ruim de prever a duracao)
        g.posProcessar();
       /* List<CifraTile> tc = g.musicaTiles.trackCifras;
        for (int i = 0; i < tc.size(); i++) {
            CifraTile ctAtual = tc.get(i);

            long duracao;
            if (i == tc.size() - 1) {     //caso seja a ultima, a duração vai até a ultima nota tile teclado
                Tile ultTile = g.musicaTiles.trackTeclado.get(g.musicaTiles.trackTeclado.size() - 1);
                duracao = (ultTile.tsInicio + ultTile.duracao) - ctAtual.tsInicio;
            } else {
                CifraTile ctProx = tc.get(i + 1);
                duracao = ctProx.tsInicio - ctAtual.tsInicio;
            }
            ctAtual.duracao = duracao;
        }*/

        return g.musicaTiles;
    }












    //Debug para criar rapidamente fake musicas tiles
    static class GentTile {
        float mult;
        long duracaoDefault;
        //LayoutConfiguration lc;
        MusicaTiles musicaTiles;

        public GentTile(String tit, String compositores, int id, float mult, long duracaoDefault, int versaoAtualizacaoBanco, int valorMoedas) {
            this.mult = mult;
            this.duracaoDefault = duracaoDefault;
            //this.lc = lc;

            musicaTiles = new MusicaTiles(new ArrayList<Tile>(), new ArrayList<Tile>(), new ArrayList<CifraTile>());
            musicaTiles.id = id;
            musicaTiles.titulo = tit;
            musicaTiles.compositores = compositores;
            musicaTiles.versaoAtualizacaoBanco = versaoAtualizacaoBanco;
            musicaTiles.valorMoedas = valorMoedas;
        }


        //Teclado
        Tile addDelta(long deltaLastTile, int numBotao) {
            return addDelta(deltaLastTile, duracaoDefault, numBotao);
        }

        Tile addDelta(long deltaLastTile, long duracao, int numBotao) {
            List<Tile> tt = musicaTiles.trackTeclado;

            long tsLast = tt.isEmpty() ? 0 : tt.get(tt.size() - 1).tsInicio;
            long duracaoLast = tt.isEmpty() ? 0 : tt.get(tt.size() - 1).duracao;
            return add(tsLast + duracaoLast + deltaLastTile, duracao, numBotao);
        }

        //Cifra
        CifraTile addCifra(long tsInicio, long duracao, int numRepeticoes, Acorde acorde) {
            CifraTile cf = new CifraTile(acorde, tsInicio, duracao, numRepeticoes);
            musicaTiles.trackCifras.add(cf);
            return cf;
        }


        Tile add(long tsInicio, int numBotao) {
            return add(tsInicio, duracaoDefault, numBotao);
        }

        Tile add(long tsInicio, long duracao, int numBotao) {
            //Tile t = new Tile((long) (tsInicio * mult), (long) (duracao * mult), lc.getBotaoByNumero(numBotao));
            Tile t = new Tile((long) (tsInicio * mult), (long) (duracao * mult), new Botao(numBotao));
            musicaTiles.trackTeclado.add(t);
            return t;
        }

        Tile getLastTileTeclado() {
            return musicaTiles.trackTeclado.get(musicaTiles.trackTeclado.size() - 1);
        }

        //Ao final Ajusta a duração dos acordes da cifra para que fique um atras do outro (já que antes fica ruim de prever a duracao)
        public void posProcessar() {
            List<CifraTile> tc = musicaTiles.trackCifras;
            for (int i = 0; i < tc.size(); i++) {
                CifraTile ctAtual = tc.get(i);

                long duracao;
                if (i == tc.size() - 1) {     //caso seja a ultima, a duração vai até a ultima nota tile teclado
                    Tile ultTile = musicaTiles.trackTeclado.get(musicaTiles.trackTeclado.size() - 1);
                    duracao = (ultTile.tsInicio + ultTile.duracao) - ctAtual.tsInicio;
                } else {
                    CifraTile ctProx = tc.get(i + 1);
                    duracao = ctProx.tsInicio - ctAtual.tsInicio;
                }
                ctAtual.duracao = duracao;
            }
        }
    }




    public static final long DUCARAO_PONTUACAO_BASE = 400;
    public static void posProcessarListaTiles(MusicaTiles musicaTiles, long silencioFim) {
        Util.hideLog = true;
        List<Tile> trackTeclado = musicaTiles.trackTeclado;

        //Ao final, aplica um pos processamento para corrigir acordes e duração minima. Cada tile terá agora uma lista contendo seus outros tiles que formam acorde, para facilitar e otimizar posteriormente
        //Usado para corrigir os acordes e aumentar duacao. Isto é, notas que estão próximas por menos de 50 ms (ou um limiar qualquer), serao editadas e consideradas com mesmo tsInicio
        final long DURACAO_MINIMA = DUCARAO_PONTUACAO_BASE + convertTamanhoToDuracao(30);       //os 30 dp é para poder ver caso encoste na linha do tempo, ainda fica a cabecinha

        if (!trackTeclado.isEmpty()) {
            List<List<Tile>> notasProximas = new ArrayList<>();
            List<Tile> ultimaLista = null;
            final long LIMIAR_DIFF_ACORDE = 50;     //50 ms de diferença entre um tile e outro, é considerado acorde e todos os tiles terão o tsInicio do primeiro
            //long ultimoTileMs = -1;

            //for (Tile t : res) {
            //Agoora começa de 0 pois processa a duração minima, mas pula pro idx para processar os acordes
            for (int i = 0; i < trackTeclado.size(); i++) {
                Tile t = trackTeclado.get(i);

                //corrige a duração minima
                if (t.duracao < DURACAO_MINIMA) {
                    t.duracao = DURACAO_MINIMA;
                }


                //para processar os acordes, o indice 0 pula.
                if (i == 0) {
                    continue;
                }
                long diffEntreNotas = t.tsInicio - trackTeclado.get(i - 1).tsInicio;
                Util.log("tAtual: " + t.toShortString() + ", res.get(i-1): " + trackTeclado.get(i - 1).toShortString() + ", diffEntreNotas: " + diffEntreNotas);
                if (diffEntreNotas <= LIMIAR_DIFF_ACORDE) {
                    Util.log("OPA, diff entre o tile idx: " + i + " e o idx: " + (i - 1) + ", é menor que: " + LIMIAR_DIFF_ACORDE +
                            " ms. ultimaLista.size(): " + (ultimaLista != null ? ultimaLista.size() : "null"));
                    //se a ultimaLista == null então está formando um par de notas (dueto) agora (podem vir mais depois formando uma triade... etc)
                    if (ultimaLista == null) {
                        Util.log("ultimaLista == null, formou o primeiro dueto");
                        ultimaLista = new ArrayList<>();                 //adiciona o segundo tile
                        ultimaLista.add(trackTeclado.get(i - 1));        //adiciona o primeiro tile somente quando cria a lista
                    }
                    ultimaLista.add(t);
                } else {
                    //Caso o tile atual não forme acorde com o anterior. Isso demarca um fim de acorde caso ultimaLista seja != null (estava processando um acorde). Então faz commit dessa lista
                    if (ultimaLista != null) {
                        //Faz commit da lista
                        notasProximas.add(ultimaLista);

                        Util.log("Finalizou o processamento de um acorde. notasProximas: " + notasProximas.size() + ", " + Arrays.toString(notasProximas.toArray()));
                        ultimaLista = null;         //libera para proxima lista
                    } else {       //caso a ultima lista seja null é porque não estava formando acorde. Não faz nada. Aqui é onde vai cair na maioria das vezes
                        Util.log("Nota atual nao forma acorde com o anterior, diff entre notas: " + diffEntreNotas);
                    }
                }
            }

            //Faz o ultimo commit caso o acorde esteja no final
            if (ultimaLista != null) {
                //Faz commit da lista
                notasProximas.add(ultimaLista);
                Util.log("Fazendo o comit após o laço pois o acorde estava na ultima posicao. notasProximas: " + notasProximas.size() + ", " + Arrays.toString(notasProximas.toArray()));
                ultimaLista = null;         //libera para proxima lista
            }

            //Ao final, iguala os tsInicio de todas as notas que estão tão próximas que formam acordes. Aqui também é gerada a lista de tiles que compõe cada acorde (lista de tiles dentro do objeto Tile)
            Util.log("Imprimindo os acordes notasProximas.size(): " + notasProximas.size());
            //Agora a lista é ordenada pelo numero do botão, para facilitar mais tarde, uma vez que seus vizios são os do idx-1 e idx+1
            for (List<Tile> lAcorde : notasProximas) {
                //Primeiramente ordena a lista de acodes baseado no numero do botão
                //Ordena as views na ordem canonica do enum dos registros
                Collections.sort(lAcorde, new Comparator<Tile>() {
                    public int compare(Tile lhs, Tile rhs) {
                        int x = lhs.botao.numero, y = rhs.botao.numero;        //fiz isso pois o codigo Integer.compare() só está disponivel na API 19+
                        return (x < y) ? -1 : ((x == y) ? 0 : 1);
                    }
                });

                long tsInicioAcorde = lAcorde.get(0).tsInicio;

                //itera pelo acorde
                Util.log("#ACORDE, tsInicioAcorde: " + tsInicioAcorde);
                //for (int i = 1; i < lAcorde.size(); i++) {      //note que começa do idx 1 pois seu tsInicio será herdado por todos os outros tiles no acorde
                for (int i = 0; i < lAcorde.size(); i++) {      //note que começa do idx 1 pois seu tsInicio será herdado por todos os outros tiles no acorde
                    //adiciona todos os outros tiles do acorde na sua lista
                    for (int j = 0; j < lAcorde.size(); j++) {
                        //agora adiciona ele mesmo também na sua lista. uma vez que ela é ordenada e isso facilitará depois
                        //if (i != j) {        //Não adiciona ele mesmo na sua lista
                        if (lAcorde.get(i).tilesAcorde == null) {            //instancia caso nao tenha instanciado
                            lAcorde.get(i).tilesAcorde = new ArrayList<>();
                            //Util.log("instanciando lAcorde.get(i).tilesAcorde, i: " + i);
                        }
                        //Util.log("lAcorde.get(i).tilesAcorde: "+  lAcorde.get(i).tilesAcorde + " i: " + i);
                        lAcorde.get(i).tilesAcorde.add(lAcorde.get(j));
                        //}
                    }

                    //nem vou fazer o teste de i != 0 pois não faz mal
                    lAcorde.get(i).tsInicio = tsInicioAcorde;       //ajusta o tsInicio
                }
            }

        }

        //apos corrigir durações minimas e etc. calcula a duração total da musica
        //duracaoTotalMusica = (res.get(res.size()-1).tsInicio + res.get(res.size()-1).duracao) - res.get(0).tsInicio ;

        //Agora como a duração eu sempre calculo através da lista de tiles pois é mais seguro (e nao através do midi pois pode vir um midi bugado).
        //Eu normalizo o final com um silencio padrão de 1 segundo.
        //long SILENCIO_FINAL_MUSICA = 2000;
        //duracaoTotalMusica = (res.get(res.size() - 1).tsInicio + res.get(res.size() - 1).duracao) + SILENCIO_FINAL_MUSICA;        //nao pega o tempo do primeiro tile, e sim 0 q é o inicio de fato
        long duracaoTotalMusica = (trackTeclado.get(trackTeclado.size() - 1).tsInicio + trackTeclado.get(trackTeclado.size() - 1).duracao) + silencioFim;        //nao pega o tempo do primeiro tile, e sim 0 q é o inicio de fato
        Util.log("pos processamento, duracaoTotalMusica: " + duracaoTotalMusica);
        Util.log("Tiles musica ed itadas para normalizar acordes");
        for (Tile t : trackTeclado) {
            Util.log(t.toString());
        }

        //calcula as estatisticas para esta musica
        //EstatisticasPontuacao.calcularMaximoPontos(res);

        //DEBUG GERA O JSON
       /* List<TileJSon> lJson = new ArrayList<>();
        for (Tile t : trackTeclado) {
            lJson.add(t.toTileJSon());
        }
        Util.log(Util.toJson(lJson));
        Util.saveToFile(Environment.getExternalStorageDirectory().toString() + "/testejson.txt", Util.toJson(lJson));*/

        //musicaTiles.duracaoTotal = duracaoTotalMusica;


        //DEBUG - gera a cifra e a track dos baixos correspondente a cifra
        if (musicaTiles.trackCifras == null) {
            //musicaTiles.trackCifras = criarFakeListaCifraTiles();
        }

        //Obrigatoriamente gera a track dos baixos conforme a track das cifras
       // musicaTiles.trackBaixos = gerarListaTilesBaixos(musicaTiles.trackCifras);     //gera  a track dos baixos a partir da track de cifras

        Util.hideLog = false;   //ocultar logs
    }


    public static final float PROPORCAO_TEMPO_TAMANHODP = 500f / 100f;       //"leva 1000 ms para andar 100 dp"
    //Converte um tamanho em px para tempo em ms, aplicando a proporção de tempo/tamanho. Basicamente é manipulação da formula de convertDuracaoToTamanho()
    public static long convertTamanhoToDuracao(float tamanho) {
        return (long) (tamanho * PROPORCAO_TEMPO_TAMANHODP);
    }
}
