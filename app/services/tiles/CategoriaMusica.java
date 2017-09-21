package services.tiles;


import services.util.Util;

public class CategoriaMusica {
    public static final int MINHAS_MUSICAS = 1;
    public static final int POPULARES = 2;
    public static final int SERTANEJO = 3;
    public static final int FORRÓ = 4;
    public static final int INSTRUMENTAIS = 5;
    public static final int TRADICIONAIS = 6;
    public static final int NORDESTINAS = 7;
    public static final int GAÚCHAS = 8;
    public static final int TEMAS = 9;
    public static final int ESCOLHAS_DO_EDITOR = 10;

    //MINHAS_MUSICAS(1), POPULARES(2), SERTANEJO(3), FORRÓ(4), INSTRUMENTAIS(5), TRADICIONAIS(6), NORDESTINAS(7), GAÚCHAS(8), TEMAS(9), ESCOLHAS_DO_EDITOR(10);
    public final int id;       //usado para cada enum ter um id proprio na hora de salvar/recuperar

    private String name;        //APENAS DEBUG, NAO EXISTEM EM PRODUÇÃO

    CategoriaMusica(int id) {
        this.id = id;

        computarInfosTempoCompilacao();         //como nao é um enum, simula
    }

    private void computarInfosTempoCompilacao() {
        switch (id) {
            case MINHAS_MUSICAS:
                name = "MINHAS_MUSICAS";
                break;
            case POPULARES:
                name = "POPULARES";
                break;
            case SERTANEJO:
                name = "SERTANEJO";
                break;
            case FORRÓ:
                name = "FORRÓ";
                break;
            case INSTRUMENTAIS:
                name = "INSTRUMENTAIS";
                break;
            case TRADICIONAIS:
                name = "TRADICIONAIS";
                break;
            case NORDESTINAS:
                name = "NORDESTINAS";
                break;
            case GAÚCHAS:
                name = "GAÚCHAS";
                break;
            case TEMAS:
                name = "TEMAS";
                break;
            case ESCOLHAS_DO_EDITOR:
                name = "ESCOLHAS_DO_EDITOR";
                break;
        }
    }


    //gambiarra, depois criar estaticos
    public static CategoriaMusica[] values() {
        return new CategoriaMusica[]{
                new CategoriaMusica(MINHAS_MUSICAS),
                new CategoriaMusica(POPULARES),
                new CategoriaMusica(SERTANEJO),
                new CategoriaMusica(FORRÓ),
                new CategoriaMusica(INSTRUMENTAIS),
                new CategoriaMusica(TRADICIONAIS),
                new CategoriaMusica(NORDESTINAS),
                new CategoriaMusica(GAÚCHAS),
                new CategoriaMusica(TEMAS),
                new CategoriaMusica(ESCOLHAS_DO_EDITOR),

        };
    }

    public String name() {
        Util.log("name: " + name + " id: " + id);
        return name;
    }
}
/*
    public static CategoriaMusica createFromId(int id){
        CategoriaMusica res = null;
        switch (id){
            case 1:
                res = MINHAS_MUSICAS;
                break;
            case 2:
                res = MINHAS_MUSICAS;
                break;
            case 3:
                res = MINHAS_MUSICAS;
                break;
            case 4:
                res = MINHAS_MUSICAS;
                break;
            case 5:
                res = MINHAS_MUSICAS;
                break;
            case 6:
                res = MINHAS_MUSICAS;
                break;
            case 7:
                res = MINHAS_MUSICAS;
                break;
            case 8:
                res = MINHAS_MUSICAS;
                break;
            case 9:
                res = MINHAS_MUSICAS;
                break;
            case 10:
                res = MINHAS_MUSICAS;
                break;
        }
        return res;
    }*/
