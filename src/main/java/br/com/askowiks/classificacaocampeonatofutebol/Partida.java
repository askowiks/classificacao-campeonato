package br.com.askowiks.classificacaocampeonatofutebol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Partida {
    private String mandante;
    private String visitante;
    private int golsMandante;
    private int golsVisitante;
    private LocalDate data;

    public void setGolsMandante(String gols){
        try {
            golsMandante = Integer.parseInt(gols);
        }catch (Exception e){
            System.out.println(this);
            golsMandante = Integer.MIN_VALUE;
        }
    }

    public void setGolsVisitante(String gols){
        try {
            golsVisitante = Integer.parseInt(gols);
        }catch (Exception e){
            System.out.println(this);
            golsVisitante = Integer.MIN_VALUE;
        }
    }

    public ResultadoPartida getResultadoMandante(){
        if (golsMandante > golsVisitante) {
            return ResultadoPartida.VITORIA;
        }else if (golsMandante == golsVisitante){
            return ResultadoPartida.EMPATE;
        }else return ResultadoPartida.DERROTA;
    }
    public ResultadoPartida getResultadoVisitante(){
        if (golsMandante < golsVisitante) {
            return ResultadoPartida.VITORIA;
        }else if (golsMandante == golsVisitante){
            return ResultadoPartida.EMPATE;
        }else return ResultadoPartida.DERROTA;
    }

}
