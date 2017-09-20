package services.tiles;


import services.tiles.NotaMusical.GeneroAcorde;
import services.tiles.NotaMusical.TipoSetima;
import services.util.Util;

/**
 * Created by Gilian on 07/07/2017.
 */

public class Acorde {
    public NotaMusical notaMusical;
    public GeneroAcorde generoAcorde;
    public TipoSetima tipoSetima;

    public Acorde(NotaMusical notaMusical, GeneroAcorde generoAcorde) {
        this(notaMusical, generoAcorde, TipoSetima.NENHUM);
    }

    public Acorde(NotaMusical notaMusical, GeneroAcorde generoAcorde, TipoSetima tipoSetima) {
        this.notaMusical = notaMusical;
        this.generoAcorde = generoAcorde;
        this.tipoSetima = tipoSetima;
    }


    public String toStringNotacao() {
        String res = notaMusical.toString() + generoAcorde.toNotacaoString() + tipoSetima.toNotacaoString();

        return res;
    }

    @Override
    public String toString() {
        String res = notaMusical.toString();
        switch (generoAcorde) {
            case MAIOR:
                res += "M";
                break;
            case MENOR:
                res += "m";
                break;
        }
        return res;
    }

    public static Acorde fromNotacaoString(String notacaoString) {
        Util.log("fromNotacaoString() notacaoString: " + notacaoString);
        //A SEQUENCIA DO PARSE É IMPORTANTE POIS VAI REMOVENDO OS ULTIMOS CARACTERES

        //define a setima (se tinha setima, remove o ultimo char)
        TipoSetima tipoSetima = TipoSetima.fromNotacaoString(notacaoString);
        Util.log("tipoSetima: " + tipoSetima);
        if (!tipoSetima.equals(TipoSetima.NENHUM)){
            notacaoString = notacaoString.substring(0, notacaoString.length() - 1);
        }

        //define o genero. remove o 'm' caso exista
        Util.log("agora notacaoString: " + notacaoString);
        GeneroAcorde generoAcorde = GeneroAcorde.fromNotacaoString(notacaoString);
        Util.log("generoAcorde: " + generoAcorde);
        if (generoAcorde.equals(GeneroAcorde.MENOR)){
            notacaoString = notacaoString.substring(0, notacaoString.length() - 1);
        }

        //neste ponto já removou os "7" e "m", então só restou "C#"
        NotaMusical notaMusical = NotaMusical.fromNotacaoString(notacaoString);

        Acorde res = new Acorde(notaMusical, generoAcorde, tipoSetima);
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        Acorde o = (Acorde) obj;
        return toStringNotacao().equals(o.toStringNotacao());
    }
}
