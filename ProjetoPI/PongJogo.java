package pong;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class PongJogo extends GameApplication {

    private Entity bola;
    private Entity raquete1;
    private Entity raquete2;
    private int pontosJogador1 = 0;
    private int pontosJogador2 = 0;
    private Text textoPontos1;
    private Text textoPontos2;
    private boolean jogoAcabado = false;
    private Text textoVitoria;
    private Text textoInstrucao;

    // Variáveis exclusivas para o Modo Arcade
    private double tempoArcade = 0.0;
    private Text textoTempo;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Pong");
        settings.setWidth(900);
        settings.setHeight(600);

        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MenuPrincipal();
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new PongSettings());

        pontosJogador1 = 0;
        pontosJogador2 = 0;
        tempoArcade = 0.0;
        jogoAcabado = false;

        bola = FXGL.spawn("bola", 450, 300);

        if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
            // ARCADE: Apenas a raquete 2 na direita (paredão fica na esquerda)
            raquete2 = FXGL.spawn("raquete", 860, 250);
        } else {
            // PVP e BOT: Raquete 1 na esquerda e Raquete 2 na direita
            raquete1 = FXGL.spawn("raquete", 20, 250);
            raquete2 = FXGL.spawn("raquete", 860, 250);
        }
    }

    @Override
    protected void initInput() {
        // Controles para mover para cima (W ou Seta para Cima)
        FXGL.onKey(KeyCode.W, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete1 != null && raquete1.getY() > 10) {
                raquete1.translateY(-6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6);
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() > 10){
                raquete1.translateY(-6);
            }
        });

        // Controles para mover para baixo (S ou Seta para Baixo)
        FXGL.onKey(KeyCode.S, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete1 != null && raquete1.getY() < 490) {
                raquete1.translateY(6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() < 490) {
                raquete2.translateY(6);
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() < 490){
                raquete1.translateY(6);
            }
        });

        // Controles da Raquete 2 via Setas (Apenas para PVP)
        FXGL.onKey(KeyCode.UP, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6); // Permite usar as setas no Arcade também
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() > 10){
                raquete1.translateY(-6);
            }
        });

        FXGL.onKey(KeyCode.DOWN, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete2 != null && raquete2.getY() < 490) {
                raquete2.translateY(6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() < 490) {
                raquete2.translateY(6);
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() < 490){
                raquete1.translateY(6);
            }
        });

        // Reiniciar partida com ESPAÇO após o fim de jogo
        FXGL.onKeyDown(KeyCode.SPACE, () -> {
            if (jogoAcabado) {
                reiniciarPartida();
            }
        });

        // Voltar ao Menu Principal pressionando M
        FXGL.onKeyDown(KeyCode.M, () -> {
            FXGL.getGameController().gotoMainMenu();
        });
    }

    @Override
    protected void initUI() {
        // Linhas da quadra padrão
        Line linhaCentral = new Line(450, 0, 450, 600);
        linhaCentral.setStroke(Color.valueOf("#222222"));
        linhaCentral.setStrokeWidth(3);
        linhaCentral.getStrokeDashArray().addAll(10.0, 10.0);

        Line linhaSuperior = new Line(0, 0, 900, 0);
        linhaSuperior.setStroke(Color.valueOf("#222222"));
        linhaSuperior.setStrokeWidth(10);

        Line linhaInferior = new Line(0, 600, 900, 600);
        linhaInferior.setStroke(Color.valueOf("#222222"));
        linhaInferior.setStrokeWidth(10);

        FXGL.addUINode(linhaSuperior);
        FXGL.addUINode(linhaInferior);

        textoPontos1 = FXGL.getUIFactoryService().newText("0", Color.BLACK, 36);
        textoPontos2 = FXGL.getUIFactoryService().newText("0", Color.BLACK, 36);

        if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
            // Paredão colorido na ESQUERDA para o Arcade
            Line paredeArcade = new Line(3, 0, 3, 600);
            paredeArcade.setStroke(Color.CRIMSON);
            paredeArcade.setStrokeWidth(8);
            FXGL.addUINode(paredeArcade);

            // Placar único centralizado
            textoPontos1.setTranslateX(435);
            textoPontos1.setTranslateY(60);
            textoPontos2.setVisible(false);

            // Cronômetro do Modo Arcade
            textoTempo = FXGL.getUIFactoryService().newText("Tempo: 0.0s", Color.DARKSLATEGRAY, 20);
            textoTempo.setTranslateX(395);
            textoTempo.setTranslateY(95);
            FXGL.addUINode(textoTempo);
        } else {
            FXGL.addUINode(linhaCentral);
            textoPontos1.setTranslateX(380);
            textoPontos1.setTranslateY(60);

            textoPontos2.setTranslateX(510);
            textoPontos2.setTranslateY(60);
        }

        FXGL.addUINode(textoPontos1);
        FXGL.addUINode(textoPontos2);

        // Atalho discreto para voltar ao menu
        Text textoDicaMenu = FXGL.getUIFactoryService().newText("[M] Menu Inicial", Color.GRAY, 14);
        textoDicaMenu.setTranslateX(770);
        textoDicaMenu.setTranslateY(25);
        FXGL.addUINode(textoDicaMenu);

        // Textos de Fim de Jogo
        textoVitoria = FXGL.getUIFactoryService().newText("", Color.BLACK, 34);
        textoVitoria.setTranslateX(200);
        textoVitoria.setTranslateY(260);
        textoVitoria.setVisible(false);

        textoInstrucao = FXGL.getUIFactoryService().newText("ESPAÇO: Jogar novamente  |  M: Voltar ao Menu", Color.DARKGRAY, 16);
        textoInstrucao.setTranslateX(265);
        textoInstrucao.setTranslateY(310);
        textoInstrucao.setVisible(false);

        FXGL.addUINode(textoVitoria);
        FXGL.addUINode(textoInstrucao);
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(TipoEntidade.BOLA, TipoEntidade.RAQUETE, (b, r) -> {
            double velocidadeX = b.getDouble("velocidadeX");
            double velocidadeY = b.getDouble("velocidadeY");

            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
                // Incrementa score e aumenta velocidade em 10% a cada rebatida no paddle da direita
                pontosJogador1++;
                textoPontos1.setText(String.valueOf(pontosJogador1));

                b.setProperty("velocidadeX", -velocidadeX * 1.1);
                b.setProperty("velocidadeY", velocidadeY * 1.1);
            } else {
                b.setProperty("velocidadeX", -velocidadeX * 1.05);
            }
        });
    }

    private void verificarVitoria() {
        int pontosParaVencer = 5;

        if (pontosJogador1 >= pontosParaVencer) {
            mostrarVitoria("JOGADOR 1 VENCEU!");
        } else if (pontosJogador2 >= pontosParaVencer) {
            String msg = MenuPrincipal.modoSelecionado.equals("BOT") ? "O BOT VENCEU!" : "JOGADOR 2 VENCEU!";
            mostrarVitoria(msg);
        }
    }

    private void mostrarVitoria(String mensagem) {
        jogoAcabado = true;
        textoVitoria.setText(mensagem);
        textoVitoria.setVisible(true);
        textoInstrucao.setVisible(true);
    }

    private void reiniciarPartida() {
        pontosJogador1 = 0;
        pontosJogador2 = 0;
        tempoArcade = 0.0;

        textoPontos1.setText("0");
        textoPontos2.setText("0");
        if (textoTempo != null) {
            textoTempo.setText("Tempo: 0.0s");
        }

        textoVitoria.setVisible(false);
        textoInstrucao.setVisible(false);

        bola.setPosition(450, 300);
        bola.setProperty("velocidadeX", 300.0);
        bola.setProperty("velocidadeY", 300.0);

        jogoAcabado = false;
    }

    @Override
    protected void onUpdate(double tpf) {
        if (jogoAcabado) {
            return;
        }

        // Atualiza o cronômetro no Modo Arcade
        if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
            tempoArcade += tpf;
            textoTempo.setText(String.format("Tempo: %.1fs", tempoArcade));
        }

        // Lógica do Bot (apenas no modo BOT)
        if (MenuPrincipal.modoSelecionado.equals("BOT")) {
            double velocidadeBot = 270.0 * tpf;
            if (raquete2.getY() + 50 < bola.getY() && raquete2.getY() < 490) {
                raquete2.translateY(velocidadeBot);
            } else if (raquete2.getY() + 50 > bola.getY() && raquete2.getY() > 10) {
                raquete2.translateY(-velocidadeBot);
            }
        }

        double velocidadeX = bola.getDouble("velocidadeX");
        double velocidadeY = bola.getDouble("velocidadeY");

        bola.translate(velocidadeX * tpf, velocidadeY * tpf);

        // Colisão com teto e chão
        if (bola.getY() <= 0 || bola.getY() >= 580) {
            bola.setProperty("velocidadeY", -velocidadeY);
        }

        // Lado esquerdo da tela (Onde fica a parede vermelha no Arcade)
        if (bola.getX() <= 5) {
            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
                // Bateu na parede vermelha da esquerda: rebate de volta para a direita!
                bola.setProperty("velocidadeX", Math.abs(velocidadeX));
            } else {
                pontosJogador2++;
                textoPontos2.setText(String.valueOf(pontosJogador2));
                bola.setPosition(450, 300);
                bola.setProperty("velocidadeX", 300.0);
                verificarVitoria();
            }
        }

        // Lado direito da tela (Onde fica sua raquete no Arcade)
        if (bola.getX() >= 890) {
            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
                // Deixou passar da raquete na direita: Fim de Jogo!
                mostrarVitoria(String.format("FIM DE JOGO! Score: %d | Tempo: %.1fs", pontosJogador1, tempoArcade));
            } else {
                pontosJogador1++;
                textoPontos1.setText(String.valueOf(pontosJogador1));
                bola.setPosition(450, 300);
                bola.setProperty("velocidadeX", -300.0);
                verificarVitoria();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}