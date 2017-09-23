package services.tiles;


//Objeto usado quando faz uma requisição para o servidor para obter as meta informações de uma musica para exibir no dashboard. Não contém a musicaTiles propriamente dita pois ela é feita um I/O na hora de começar
public class MetaInfoMusica {
    public long id;

    //Esses valores são preenchidos na requisição às musicas estáticas
    public String titulo, compositores;
    public CategoriaMusica categoria;
    public int valorMoedas;

    public int versaoAtualizacaoBanco;

    public MetaInfoMusica(long id, String titulo, String compositores, CategoriaMusica categoria, int versaoAtualizacaoBanco, int valorMoedas) {
        this.id = id;
        this.titulo = titulo;
        this.compositores = compositores;
        this.categoria = categoria;
        this.versaoAtualizacaoBanco = versaoAtualizacaoBanco;
        this.valorMoedas = valorMoedas;
    }

    public MetaInfoMusica() {
    }

    @Override
    public String toString() {
        return "MetaInfoMusica{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", compositores='" + compositores + '\'' +
                ", categoria=" + categoria +
                ", valorMoedas=" + valorMoedas +
                ", versaoAtualizacaoBanco=" + versaoAtualizacaoBanco +
                '}';
    }

  /*    @Override
    public String toString() {
        return "MetaInfoMusica{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", compositores='" + compositores + '\'' +
                ", categoria=" + categoria +
                ", valorMoedas=" + valorMoedas +
                '}';
    }*/
}
