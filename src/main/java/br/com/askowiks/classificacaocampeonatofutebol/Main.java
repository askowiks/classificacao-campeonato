package br.com.askowiks.classificacaocampeonatofutebol;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File("C:\\partidasfutebol\\partidasfutebol.csv");
            lines = FileUtils.readLines(file, "UTF-8");
        } catch (Exception e) {
            System.out.println("Erro");
        }

        Map<String, Integer> placar = new LinkedHashMap<>();

        List<Clube> clubes = new ArrayList<>();

        for (int indice = 1; indice < lines.size(); indice++) {
            String linha = lines.get(indice);
            String[] conteudoLinha = linha.split(";");
            Partida partida = new Partida();
            partida.setMandante(conteudoLinha[0]);
            partida.setVisitante(conteudoLinha[1]);

            //Criando LocalDate a partir de um padrão de data específico
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            //LocalDate localDate = LocalDate.parse(conteudoLinha[4], formatter);
            LocalDate localDate = LocalDate.parse(conteudoLinha[4]);
            partida.setData(localDate);
            partida.setGolsMandante(conteudoLinha[2]);
            partida.setGolsVisitante(conteudoLinha[3]);

            int pontuacaoFinalMandante = partida.getResultadoMandante().getValor();
            int pontuacaoFinalVisitante = partida.getResultadoVisitante().getValor();
            //O placar atual já contém o mandante atual?
            if (placar.containsKey(partida.getMandante())) {
                pontuacaoFinalMandante += placar.get(partida.getMandante());
                placar.put(partida.getMandante(), pontuacaoFinalMandante);
                for (Clube clube : clubes) {
                    if (clube.getNome().equals(partida.getMandante())) {
                        switch (partida.getResultadoMandante()) {
                            case VITORIA:
                                clube.setVitorias(clube.getVitorias() + 1);
                                break;
                            case EMPATE:
                                clube.setEmpates(clube.getEmpates() + 1);
                                break;
                            case DERROTA:
                                clube.setDerrotas(clube.getDerrotas() + 1);
                                break;
                        }
                        clube.setPontuacaoFinal(pontuacaoFinalMandante);
                        clube.getPartidas().add(partida);
                        break;
                    }
                }
            } else {
                placar.put(partida.getMandante(), pontuacaoFinalMandante);
                Clube clube = new Clube();
                clube.setNome(partida.getMandante());
                switch (partida.getResultadoMandante()) {
                    case VITORIA:
                        clube.setVitorias(1);
                        break;
                    case EMPATE:
                        clube.setEmpates(1);
                        break;
                    case DERROTA:
                        clube.setDerrotas(1);
                        break;
                }
                clube.setPontuacaoFinal(pontuacaoFinalMandante);
                clube.getPartidas().add(partida);
                clubes.add(clube);
            }
            //O placar atual já contém o visitante atual?
            if (placar.containsKey(partida.getVisitante())) {
                pontuacaoFinalVisitante += placar.get(partida.getVisitante());
                placar.put(partida.getVisitante(), pontuacaoFinalVisitante);
                for (Clube clube : clubes) {
                    if (clube.getNome().equals(partida.getVisitante())) {
                        switch (partida.getResultadoVisitante()) {
                            case VITORIA:
                                clube.setVitorias(clube.getVitorias() + 1);
                                break;
                            case EMPATE:
                                clube.setEmpates(clube.getEmpates() + 1);
                                break;
                            case DERROTA:
                                clube.setDerrotas(clube.getDerrotas() + 1);
                                break;
                        }
                        clube.setPontuacaoFinal(pontuacaoFinalVisitante);
                        break;
                    }
                }
            } else {
                placar.put(partida.getVisitante(), pontuacaoFinalVisitante);
                Clube clube = new Clube();
                clube.setNome(partida.getVisitante());
                switch (partida.getResultadoVisitante()) {
                    case VITORIA:
                        clube.setVitorias(1);
                        break;
                    case EMPATE:
                        clube.setEmpates(1);
                        break;
                    case DERROTA:
                        clube.setDerrotas(1);
                        break;
                }
                clube.setPontuacaoFinal(pontuacaoFinalVisitante);
                clubes.add(clube);

            }
        }

//        placar.forEach((chave, pontuacao) -> {
//            System.out.println("time: " + chave + "- pontuação: " + pontuacao);
//        });

//        clubes.forEach(clube -> {
//            System.out.println(clube);
//        });


        clubes.forEach(clube -> {
            File file = new File("C:\\partidasfutebol\\" + clube.getNome() + ".txt");

            StringBuilder texto = new StringBuilder();
            clube.getPartidas().forEach(partida -> {
                texto.append(partida.getData().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
                texto.append(": ");
                texto.append(partida.getMandante());
                texto.append(" ");
                texto.append(partida.getGolsMandante());
                texto.append(" x ");
                texto.append(partida.getGolsVisitante());
                texto.append(" ");
                texto.append(partida.getVisitante());
                texto.append("\n");
            });
            System.out.println("\nArquivo " + clube.getNome() + " criado com sucesso!");
            try {
                FileUtils.write(file, texto.toString(), "UTF8");
            } catch (Exception e) {
                System.out.println("Erro ao criar arquivo.");
            }

        });

        Collections.sort(clubes);
        File file = new File("C:\\partidasfutebol\\classificacaofinal.csv");
        StringBuilder texto = new StringBuilder();
        texto.append("Time;Vitorias;Empates;Derrotas;Pontos\n");
        clubes.forEach(clube -> {
            texto.append(clube.getNome());
            texto.append(";");
            texto.append(clube.getVitorias());
            texto.append(";");
            texto.append(clube.getEmpates());
            texto.append(";");
            texto.append(clube.getDerrotas());
            texto.append(";");
            texto.append(clube.getPontuacaoFinal());
            texto.append(";");
            texto.append("\n");

        });
        System.out.println("\nArquivo classificaçãofinal criado com sucesso!");

        try {
            FileUtils.write(file, texto.toString(), "UTF8");
        } catch (Exception e) {
            System.out.println("Erro ao criar arquivo.");
        }
//

    }
}
