package services.tiles;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import services.util.Util;
import services.util.Util.Predicado;
import services.util.Util.TipoNotacao;

/**
 * Created by Gilian on 04/02/2017.
 */

public class NotaMusical {
    public static String SEP_NOTAS_AFINACAO = "/";

    public NotaSimples notaSimples;
    public Acidente acidente;
    public int idxArrayNotasMusicais;    //o indice no array de notas musicais, se for um D#, o idx eh 3 (C, C#, D, D#...)

    public static NotaMusical fromNotacaoString(String notacaoString) {
        Util.log("fromNotacaoString(), notacaoString: " + notacaoString);
        NotaMusical res = null;
        //metodo 2
        Acidente acidente = Acidente.fromNotacaoString(notacaoString);
        Util.log("acidente: " + acidente);
        if (!acidente.equals(Acidente.NENHUM)){
            notacaoString = Util.removeLastChar(notacaoString);
        }

        NotaSimples notaSimples = NotaSimples.fromNotacaoString(notacaoString);
        Util.log("notaSimples: " + notaSimples);

        for (NotaMusical notaMusical : arrayNotasMusicaisAcidentesRepetidos){
            if (notaMusical.notaSimples.equals(notaSimples) && notaMusical.acidente.equals(acidente)){
                Util.log("OPA, ENCONTROU A NOTAMUSICAL: " + notaMusical);
                res = notaMusical;
                break;
            }
        }

        return res;
    }

    public enum NotaSimples {
        C, D, E, F, G, A, B;

        public String toNotacaoString(TipoNotacao tipoNotacao) {
            String res = null;
            switch (tipoNotacao) {
                case C:
                    res = toString();
                    break;
                case DO:
                    res = Util.TECLAS_EXIBIR_NOTAS_NOTACAO_DO_NATURAIS[ordinal()];
                    break;
            }
            return res;
        }

        public static NotaSimples fromNotacaoString(String notacaoString) {
            return NotaSimples.valueOf(notacaoString);
        }
    }

    public enum Acidente {
        SUSTENIDO, BEMOL, NENHUM;

        public String toNotacaoString() {
            String res = "";
            switch (this) {
                case BEMOL:
                    res = "b";
                    break;
                case SUSTENIDO:
                    res = "#";
                    break;
            }
            return res;
        }

        public static Acidente fromNotacaoString(String notacaoString) {
            return notacaoString.endsWith("#") ? SUSTENIDO : (notacaoString.endsWith("b") ? BEMOL : NENHUM);
        }
    }

    public enum GeneroAcorde {
        MAIOR, MENOR;

        public String toNotacaoString() {
            return equals(MENOR) ? "m" : "";
        }

        public static GeneroAcorde fromNotacaoString(String notacaoString) {
            return  notacaoString.endsWith("m") ?MENOR : MAIOR;
        }
    }

    public enum TipoSetima {
        NENHUM, SET_MAIOR, SET_MENOR;

        public String toNotacaoString() {
            return equals(NENHUM) ? "" : "7";
        }
        public static TipoSetima fromNotacaoString(String notacaoString){
            return notacaoString.endsWith("7") ? SET_MENOR : NENHUM;
        }
    }

    static int idxGenerator = 0;
    public static final NotaMusical C = new NotaMusical(NotaSimples.C, idxGenerator++);
    public static final NotaMusical CSus = new NotaMusical(NotaSimples.C, Acidente.SUSTENIDO, idxGenerator++);
    public static final NotaMusical D = new NotaMusical(NotaSimples.D, idxGenerator++);
    public static final NotaMusical DSus = new NotaMusical(NotaSimples.D, Acidente.SUSTENIDO, idxGenerator++);
    public static final NotaMusical Db = new NotaMusical(NotaSimples.D, Acidente.BEMOL, CSus.idxArrayNotasMusicais);
    public static final NotaMusical E = new NotaMusical(NotaSimples.E, idxGenerator++);
    public static final NotaMusical Eb = new NotaMusical(NotaSimples.E, Acidente.BEMOL, DSus.idxArrayNotasMusicais);
    public static final NotaMusical F = new NotaMusical(NotaSimples.F, idxGenerator++);
    public static final NotaMusical FSus = new NotaMusical(NotaSimples.F, Acidente.SUSTENIDO, idxGenerator++);
    public static final NotaMusical Fb = new NotaMusical(NotaSimples.F, Acidente.BEMOL, E.idxArrayNotasMusicais);
    public static final NotaMusical ESus = new NotaMusical(NotaSimples.E, Acidente.SUSTENIDO, F.idxArrayNotasMusicais);
    public static final NotaMusical G = new NotaMusical(NotaSimples.G, idxGenerator++);
    public static final NotaMusical GSus = new NotaMusical(NotaSimples.G, Acidente.SUSTENIDO, idxGenerator++);
    public static final NotaMusical Gb = new NotaMusical(NotaSimples.G, Acidente.BEMOL, FSus.idxArrayNotasMusicais);
    public static final NotaMusical A = new NotaMusical(NotaSimples.A, idxGenerator++);
    public static final NotaMusical ASus = new NotaMusical(NotaSimples.A, Acidente.SUSTENIDO, idxGenerator++);
    public static final NotaMusical Ab = new NotaMusical(NotaSimples.A, Acidente.BEMOL, GSus.idxArrayNotasMusicais);
    public static final NotaMusical B = new NotaMusical(NotaSimples.B, idxGenerator++);
    public static final NotaMusical BSus = new NotaMusical(NotaSimples.B, Acidente.SUSTENIDO, C.idxArrayNotasMusicais);    //B# Eh a mesma coisa que C, por isso possui o mesmo idx
    public static final NotaMusical Bb = new NotaMusical(NotaSimples.B, Acidente.BEMOL, ASus.idxArrayNotasMusicais);    //B# Eh a mesma coisa que C, por isso possui o mesmo idx
    public static final NotaMusical Cb = new NotaMusical(NotaSimples.C, Acidente.BEMOL, B.idxArrayNotasMusicais);

    //**Escalas Diatonicas maiores
    public static final NotaMusical[] escalaC = new NotaMusical[]{C, D, E, F, G, A, B};
    public static final NotaMusical[] escalaCSus = new NotaMusical[]{CSus, DSus, ESus, FSus, GSus, ASus, BSus};
    public static final NotaMusical[] escalaCb = new NotaMusical[]{Cb, Db, Eb, Fb, Gb, Ab, Bb};

    public static final NotaMusical[] escalaD = new NotaMusical[]{D, E, FSus, G, A, B, CSus};
    public static final NotaMusical[] escalaDSus = new NotaMusical[]{DSus, ESus, G, GSus, ASus, BSus, D};
    public static final NotaMusical[] escalaDb = new NotaMusical[]{Db, Eb, F, Gb, Ab, Bb, C};

    public static final NotaMusical[] escalaE = new NotaMusical[]{E, FSus, GSus, A, B, CSus, DSus};
    public static final NotaMusical[] escalaESus = new NotaMusical[]{ESus, G, A, ASus, BSus, D, E};
    public static final NotaMusical[] escalaEb = new NotaMusical[]{Eb, F, G, Ab, Bb, C, D};

    public static final NotaMusical[] escalaF = new NotaMusical[]{F, G, A, Bb, C, D, E};
    public static final NotaMusical[] escalaFSus = new NotaMusical[]{FSus, GSus, ASus, B, CSus, DSus, ESus};
    public static final NotaMusical[] escalaFb = new NotaMusical[]{Fb, Gb, Ab, A, Cb, Db, Eb};

    public static final NotaMusical[] escalaG = new NotaMusical[]{G, A, B, C, D, E, FSus};
    public static final NotaMusical[] escalaGSus = new NotaMusical[]{GSus, ASus, BSus, CSus, DSus, ESus, G};
    public static final NotaMusical[] escalaGb = new NotaMusical[]{Gb, Ab, Bb, Cb, Db, Eb, F};

    public static final NotaMusical[] escalaA = new NotaMusical[]{A, B, CSus, D, E, FSus, GSus};
    public static final NotaMusical[] escalaASus = new NotaMusical[]{ASus, BSus, D, DSus, ESus, G, A};
    public static final NotaMusical[] escalaAb = new NotaMusical[]{Ab, Bb, C, Db, Eb, F, G};

    public static final NotaMusical[] escalaB = new NotaMusical[]{B, CSus, DSus, E, FSus, GSus, ASus};
    public static final NotaMusical[] escalaBSus = new NotaMusical[]{BSus, D, E, ESus, G, A, B};
    public static final NotaMusical[] escalaBb = new NotaMusical[]{Bb, C, D, Eb, F, G, A};

    //Array ORDENADO (nao mexer na ordem) de notas musicais sem repeticao (aq uso o #, mas tanto faz)
    public static List<NotaMusical> arrayNotasMusicais = Arrays.asList(C, CSus, D, DSus, E, F, FSus, G, GSus, A, ASus, B);

    //Arrray de todas as notas, usado para buscar uma nota e seus similares (Cb == B)
    //public static NotaMusical[] arrayNotasMusicaisAcidentesRepetidos = new NotaMusical[]{C, CSus, Cb, D, DSus, Db, E, ESus, Eb, F, FSus, Fb, G, GSus, Gb, A, ASus, Ab, B, BSus, Bb};
    public static List<NotaMusical> arrayNotasMusicaisAcidentesRepetidos = Arrays.asList(C, CSus, Cb, D, DSus, Db, E, ESus, Eb, F, FSus, Fb, G, GSus, Gb, A, ASus, Ab, B, BSus, Bb);


    public static HashMap<NotaMusical, NotaMusical[]> escalasDiatonicasMaiores = new HashMap<>();

    static {
        escalasDiatonicasMaiores.put(C, escalaC);
        escalasDiatonicasMaiores.put(CSus, escalaCSus);
        escalasDiatonicasMaiores.put(Cb, escalaCb);

        escalasDiatonicasMaiores.put(D, escalaD);
        escalasDiatonicasMaiores.put(DSus, escalaDSus);
        escalasDiatonicasMaiores.put(Db, escalaDb);

        escalasDiatonicasMaiores.put(E, escalaE);
        escalasDiatonicasMaiores.put(ESus, escalaESus);
        escalasDiatonicasMaiores.put(Eb, escalaEb);

        escalasDiatonicasMaiores.put(F, escalaF);
        escalasDiatonicasMaiores.put(FSus, escalaFSus);
        escalasDiatonicasMaiores.put(Fb, escalaFb);

        escalasDiatonicasMaiores.put(G, escalaG);
        escalasDiatonicasMaiores.put(GSus, escalaGSus);
        escalasDiatonicasMaiores.put(Gb, escalaGb);

        escalasDiatonicasMaiores.put(A, escalaA);
        escalasDiatonicasMaiores.put(ASus, escalaASus);
        escalasDiatonicasMaiores.put(Ab, escalaAb);

        escalasDiatonicasMaiores.put(B, escalaB);
        escalasDiatonicasMaiores.put(BSus, escalaBSus);
        escalasDiatonicasMaiores.put(Bb, escalaBb);
    }


    private NotaMusical(NotaSimples notaSimples, int idxArray) {
        this(notaSimples, Acidente.NENHUM, idxArray);
    }

    private NotaMusical(NotaSimples notaSimples, Acidente acidente, int idxArrayNotasMusicais) {
        this.notaSimples = notaSimples;
        this.acidente = acidente;
        this.idxArrayNotasMusicais = idxArrayNotasMusicais;
    }

    @Override
    public boolean equals(Object obj) {
        return this.idxArrayNotasMusicais == ((NotaMusical) obj).idxArrayNotasMusicais;
    }

    //olha a penas para a nota em si e nao o idx(usado em getsustenido e getbemol)
    public boolean equalsIgnoreIdx(NotaMusical c) {
        return this.notaSimples.equals(c.notaSimples) && this.acidente == c.acidente;
    }

    public static NotaMusical getNota(NotaSimples notaSimples) {
        return getNota(notaSimples, Acidente.NENHUM);
    }

    public static NotaMusical getNota(final NotaSimples notaSimples, final Acidente acidente) {
        ////Debug.Log("getNota(), notaSimples: " + notaSimples + ", acidente: " + acidente);

        return Util.find(arrayNotasMusicaisAcidentesRepetidos, new Predicado<NotaMusical>() {
            public boolean apply(NotaMusical other) {
                return other.notaSimples.equals(notaSimples) && other.acidente.equals(acidente);
            }
        });

       /* return Array.Find<NotaMusicalS>(arrayNotasMusicaisAcidentesRepetidos, delegate(NotaMusicalS other) {
            //return Array.Find<NotaMusicalS>(arrayNotasMusicaisS,delegate(NotaMusicalS other) {
            ////Debug.Log("other.notaSimples: " + other.notaSimples + ", notaSimples: " + notaSimples + ", ")
            return other.notaSimples.Equals(notaSimples) && other.acidente.Equals(acidente);
        });*/
    }

    public String toNotacaoString(TipoNotacao tipoNotacao) {
        return notaSimples.toNotacaoString(tipoNotacao) + acidente.toNotacaoString();
    }

    public String toString() {
        return notaSimples.toString() + acidente.toNotacaoString();
    }


    public NotaMusical getBemol() {
        NotaMusical res = null;

        int proxIdx = Util.getIdxCircular(idxArrayNotasMusicais - 1, arrayNotasMusicais.size());
        final NotaMusical anterior = arrayNotasMusicais.get(proxIdx);

        //**Pega todas as notas iguais (ex: Eb, D#)
        List<NotaMusical> iguais = (List<NotaMusical>) Util.findAll(NotaMusical.arrayNotasMusicaisAcidentesRepetidos, new Predicado<NotaMusical>() {
            public boolean apply(NotaMusical other) {
                return anterior.idxArrayNotasMusicais == other.idxArrayNotasMusicais;
            }
        });

 /*       NotaMusical[] iguais = Array.FindAll<NotaMusicalS>(arrayNotasMusicaisAcidentesRepetidos, delegate(NotaMusical other) {
            return anterior.idxArrayNotasMusicais == other.idxArrayNotasMusicais;
        });*/

        res = anterior;        //caso nao acho bemol fica apenas com a anterior
        for (NotaMusical n : iguais) {
            if (n.acidente.equals(Acidente.BEMOL)) {
                res = n;
                break;
            }
            //**Se chegar aqui é pq não achou a nota anterior com notação bemol. ex é um F#, so tem de bemol o F e o E#, nesse caso pega o F

        }

        ////Debug.Log("getBemol(), this: " + ToString() + ", iguais: " + Util.printArray<NotaMusicalS>(iguais) + ", res: " + res);

        return res;
    }

    public static int getIdxCircular(int idx) {
        return Util.getIdxCircular(idx, NotaMusical.arrayNotasMusicais.size());
    }

}
