package br.com.askowiks.classificacaocampeonatofutebol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
public class Clube {
    private String nome;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int pontuacaoFinal;
    private List<Partida> partidas = new ArrayList<>();

}