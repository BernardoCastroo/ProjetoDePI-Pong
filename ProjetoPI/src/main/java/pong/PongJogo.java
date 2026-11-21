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
    private double tempoContagem = 0.0;
    private Text textoContagem;

    private double tempoArcade = 0.0;
    private Text textoTempo;

    protected void initSettings(GameSettings settings) {
        settings.setTitle("Pong");
        settings.setWidth(900);
        settings.setHeight(600);
        settings.setTicksPerSecond(60);

        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            public FXGLMenu newMainMenu() {
                return new MenuPrincipal();
            }
        });
    }

    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new PongSettings());
        FXGL.getGameScene().setBackgroundColor(Color.web("#9b9797"));

        pontosJogador1 = 0;
        pontosJogador2 = 0;
        tempoArcade = 0.0;
        jogoAcabado = false;

        bola = FXGL.spawn("bola", 450, 300);

        if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
            raquete2 = FXGL.spawn("raquete", 860, 250);
        } else {
            raquete1 = FXGL.spawn("raquete", 20, 250);
            raquete2 = FXGL.spawn("raquete", 860, 250);
        }
        tempoContagem = 3.0;
    }

    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete1 != null && raquete1.getY() > 10) {
                raquete1.translateY(-6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6);
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() > 10){
                raquete1.translateY(-6);
            }
        });

        FXGL.onKey(KeyCode.S, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete1 != null && raquete1.getY() < 490) {
                raquete1.translateY(6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() < 490) {
                raquete2.translateY(6);
            } else if(MenuPrincipal.modoSelecionado.equals("BOT") && raquete1 != null && raquete1.getY() < 490){
                raquete1.translateY(6);
            }
        });

        FXGL.onKey(KeyCode.UP, () -> {
            if (MenuPrincipal.modoSelecionado.equals("PVP") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6);
            } else if (MenuPrincipal.modoSelecionado.equals("ARCADE") && raquete2 != null && raquete2.getY() > 10) {
                raquete2.translateY(-6); 
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

        FXGL.onKeyDown(KeyCode.SPACE, () -> {
            if (jogoAcabado) {
                reiniciarPartida();
            }
        });

        FXGL.onKeyDown(KeyCode.M, () -> {
            FXGL.getGameController().gotoMainMenu();
        });
    }

    protected void initUI() {
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
            Line paredeArcade = new Line(3, 0, 3, 600);
            paredeArcade.setStroke(Color.CRIMSON);
            paredeArcade.setStrokeWidth(8);
            FXGL.addUINode(paredeArcade);

            textoPontos1.setTranslateX(435);
            textoPontos1.setTranslateY(60);
            textoPontos2.setVisible(false);

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

        Text textoDicaMenu = FXGL.getUIFactoryService().newText("[M] Menu Inicial", Color.GRAY, 14);
        textoDicaMenu.setTranslateX(770);
        textoDicaMenu.setTranslateY(25);
        FXGL.addUINode(textoDicaMenu);

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

        textoContagem = FXGL.getUIFactoryService().newText("3", Color.BLACK, 72);
        textoContagem.setTranslateX(430);
        textoContagem.setTranslateY(230);
        textoContagem.setVisible(false);
        FXGL.addUINode(textoContagem);
    }

    protected void initPhysics() {
        FXGL.onCollisionBegin(TipoEntidade.BOLA, TipoEntidade.RAQUETE, (b, r) -> {
            double velocidadeX = b.getDouble("velocidadeX");
            double velocidadeY = b.getDouble("velocidadeY");

            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
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

        tempoContagem = 3.0;
        jogoAcabado = false;
    }
    private long ultimoNanos = 0; 

protected void onUpdate(double tpfFxgl) { 
    long agora = System.nanoTime();
    double tpf = (ultimoNanos == 0) ? 1.0 / 60 : (agora - ultimoNanos) / 1_000_000_000.0;
    ultimoNanos = agora;
        if (jogoAcabado) {
            return;
        }

        if (tpf > 0.05) {
            return;
        }

        if (tempoContagem > 0) {
            tempoContagem -= tpf;
            textoContagem.setVisible(true);
            
            int segundosRestantes = (int) Math.ceil(tempoContagem);
            textoContagem.setText(String.valueOf(Math.max(1, segundosRestantes)));  
            return; 
        } else {
            textoContagem.setVisible(false);
        }

        if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
            tempoArcade += tpf;
            textoTempo.setText(String.format("Tempo: %.1fs", tempoArcade));
        }

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

        if (bola.getY() <= 0 || bola.getY() >= 580) {
            bola.setProperty("velocidadeY", -velocidadeY);
        }

        if (bola.getX() <= 5) {
            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
                bola.setProperty("velocidadeX", Math.abs(velocidadeX));
            } else {
                pontosJogador2++;
                textoPontos2.setText(String.valueOf(pontosJogador2));
                bola.setPosition(450, 300);
                bola.setProperty("velocidadeX", 300.0);
                verificarVitoria();
            }
        }

        if (bola.getX() >= 890) {
            if (MenuPrincipal.modoSelecionado.equals("ARCADE")) {
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