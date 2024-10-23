package Jogo;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
    private ArrayList<ArrayList<String>> matriz;
    private String vezPlayer = "X";

    public Jogo() {
        matriz = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            matriz.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                matriz.get(i).add(" * ");
            }
        }
    }

    private void renderizar() {
        for (ArrayList<String> casa : matriz) {
            StringBuilder linha = new StringBuilder();
            for (String item : casa) {
                linha.append(item);
            }
            System.out.println(linha);
        }
    }

    public void jogar() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            limparTerminal();
            System.out.println("É a vez do jogador " + vezPlayer + "\n");
            this.renderizar();
            pausar(1000);

            int linha = obterEntrada(sc, "Insira a linha onde deseja jogar (1 a 3): ");
            int coluna = obterEntrada(sc, "Insira uma coluna onde deseja jogar (1 a 3): ");

            if (!isLugarDisponivel(linha, coluna)) {
                System.out.println("Casa já ocupada! Tente novamente.");
                pausar(1000);
                continue;
            }
            
            if (verificarVitoria(linha, coluna)) {
                limparTerminal();
                System.out.println("Jogador " + vezPlayer + " venceu!");
                this.renderizar();
                break;
            }

            if (isEmpate()) {
                System.out.println("Empate! Não há mais jogadas disponíveis.");
                this.renderizar();
                break;
            }

            mudarVez();
            pausar(800);
        }
    }

    private int obterEntrada(Scanner sc, String mensagem) {
        int entrada = 0;

        System.out.println(mensagem);
        while (true) {
            if (sc.hasNextInt()) {
                entrada = sc.nextInt() - 1;
                if (entrada >= 0 && entrada <= 2) {
                    break;
                } else {
                    System.out.println("Por favor, insira um número válido entre 1 e 3!");
                }
            } else {
                System.out.println("Por favor, insira um número válido!");
                sc.next();
            }
        }
        return entrada;
    }
    
    private boolean isLugarDisponivel(int linha, int coluna) {
        if (matriz.get(linha).get(coluna).equals(" * ")) {
            matriz.get(linha).set(coluna, " " + vezPlayer + " ");
            return true;
        }
        return false;
    }
    
    private boolean verificarVitoria(int linha, int coluna) {
        if (matriz.get(linha).get(0).trim().equals(vezPlayer) &&
            matriz.get(linha).get(1).trim().equals(vezPlayer) &&
            matriz.get(linha).get(2).trim().equals(vezPlayer)) {
            return true;
        }


        for (int i = 0; i < 3; i++) {
            if (!matriz.get(i).get(coluna).trim().equals(vezPlayer)) {
                break;
            }
            if (i == 2) return true;
        }

        if (linha == coluna) {
            for (int i = 0; i < 3; i++) {
                if (!matriz.get(i).get(i).trim().equals(vezPlayer)) {
                    break;
                }
                if (i == 2) return true;
            }
        }

        if (linha + coluna == 2) {
            for (int i = 0; i < 3; i++) {
                if (!matriz.get(i).get(2 - i).trim().equals(vezPlayer)) {
                    break;
                }
                if (i == 2) return true;
            }
        }

        return false;
    }

    private boolean isEmpate() {
        for (ArrayList<String> linha : matriz) {
            for (String casa : linha) {
                if (casa.equals(" * ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mudarVez() {
        this.vezPlayer = (this.vezPlayer.equals("X")) ? "O" : "X";
    }

    private void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar: " + e.getMessage());
        }
    }

    private void limparTerminal() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Erro ao limpar o terminal: " + e.getMessage());
        }
    }
}
