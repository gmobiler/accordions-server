package services.tiles;

public class Botao {
    public int numero;

    public String notaExibir;
    public String soundBankKey;


    public Botao(int numero) {
        this.numero = numero;
    }

    public String getNotaExibir() {
        return notaExibir;
    }

    public String getSoundBankKey() {
        return soundBankKey;
    }

    public void setSoundBankKey(String soundBankKey) {
        this.soundBankKey = soundBankKey;
    }
}
