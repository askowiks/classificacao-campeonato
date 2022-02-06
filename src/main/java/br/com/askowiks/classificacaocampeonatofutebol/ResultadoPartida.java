package br.com.askowiks.classificacaocampeonatofutebol;

public enum ResultadoPartida {
    VITORIA(3), EMPATE(1), DERROTA(0);

    private final int valor;

    ResultadoPartida(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return valor;
    }
}
