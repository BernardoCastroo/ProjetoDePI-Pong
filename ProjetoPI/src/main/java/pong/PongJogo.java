package pong;

import com.almasb.fxgl.app.GameApplication;// funcionalidades do engine 
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;



public class PongJogo extends GameApplication{

    private Entity bola;
    private Entity raquete1;
    private Entity raquete2;
    private int Jogador1 = 0; 
    private int Jogador2 = 0; 
    private Text textoPontos1;
    private Text textoPontos2;
    private boolean jogoAcabado = false;
    private Text textoVitoria;
    private Text textoInstrucao;

    protected void initSettings(GameSettings settings) {// Janela de escrita
        settings.setTitle("Pong");
        settings.setWidth(900);
        settings.setHeight(600);
    }

    protected void initGame(){
        FXGL.getGameWorld().addEntityFactory(new PongSettings());//diz ao programa para usar o PongSettings

        bola=FXGL.spawn("bola", 450, 300);

        raquete1 = FXGL.spawn("raquete", 5, 250);

        raquete2 = FXGL.spawn("raquete", 875, 250);
    }
    
    protected void initInput() {
        // teclas (W e S) 
        FXGL.onKey(KeyCode.W, () -> {
            if (raquete1.getY() > 0) {
                raquete1.translateY(-5);
            }
        });
        
        FXGL.onKey(KeyCode.S, () -> {
            if (raquete1.getY() < 500) { 
                raquete1.translateY(5);
            }
        });

        // Setas Cima e Baixo
        FXGL.onKey(KeyCode.UP, () -> {
            if (raquete2.getY() > 0) {
                raquete2.translateY(-5);
            }
        });

        FXGL.onKey(KeyCode.DOWN, () -> {
            if (raquete2.getY() < 500) {
                raquete2.translateY(5);
            }
        });

        FXGL.onKeyDown(KeyCode.SPACE, () -> {
        if (jogoAcabado) {
            reiniciarPartida();
        }
    });
    }
    protected void initUI() {
    Line linhaCentral = new Line(450, 0, 450, 600);
    linhaCentral.setStroke(Color.BLUE); 
    linhaCentral.setStrokeWidth(4);
    linhaCentral.getStrokeDashArray().addAll(15.0, 15.0); // tracejado 

    Line linhalateral= new Line(1, 0, 1, 600);
    linhalateral.setStroke(Color.BLUE); 
    linhalateral.setStrokeWidth(4);

    Line linhalateral2= new Line(899, 0, 899, 600);
    linhalateral2.setStroke(Color.BLUE); 
    linhalateral2.setStrokeWidth(4);

    Line linhadedebaixo= new Line(0, 599, 900, 599);
    linhadedebaixo.setStroke(Color.BLUE); 
    linhadedebaixo.setStrokeWidth(5);

    Line linhadecima= new Line(0, 1, 900, 1);
    linhadecima.setStroke(Color.BLUE); 
    linhadecima.setStrokeWidth(5);


    FXGL.addUINode(linhaCentral);
    FXGL.addUINode(linhalateral);
    FXGL.addUINode(linhalateral2);
    FXGL.addUINode(linhadedebaixo);
    FXGL.addUINode(linhadecima);

        textoPontos1 = FXGL.getUIFactoryService().newText("0", Color.BLACK, 28);
        textoPontos2 = FXGL.getUIFactoryService().newText("0", Color.BLACK, 28);

        textoPontos1.setTranslateX(300);
        textoPontos1.setTranslateY(50);

        textoPontos2.setTranslateX(600);
        textoPontos2.setTranslateY(50);

        FXGL.addUINode(textoPontos1);
        FXGL.addUINode(textoPontos2);

        // Texto de vitória (Grande no centro)
        textoVitoria = FXGL.getUIFactoryService().newText("", Color.BLACK, 42);
        textoVitoria.setTranslateX(220);
        textoVitoria.setTranslateY(260);
        textoVitoria.setVisible(false);

        // Subtítulo de instrução
        textoInstrucao = FXGL.getUIFactoryService().newText("Pressione ESPAÇO para jogar novamente", Color.DARKGRAY, 18);
        textoInstrucao.setTranslateX(230);
        textoInstrucao.setTranslateY(310);
        textoInstrucao.setVisible(false);

        FXGL.addUINode(textoVitoria);
        FXGL.addUINode(textoInstrucao);
    }
    
    protected void initPhysics() {
        FXGL.onCollisionBegin(TipoEntidade.BOLA, TipoEntidade.RAQUETE, (bola, raquete) -> {
            double velocidadeX = bola.getDouble("velocidadeX");
            bola.setProperty("velocidadeX", -velocidadeX * 1.05);
        });
    }

private void verificarVitoria() {
    int pontosParaVencer = 5;

    if (Jogador1 >= pontosParaVencer) {
        mostrarVitoria("JOGADOR 1 VENCEU!");
    } else if (Jogador2 >= pontosParaVencer) {
        mostrarVitoria("JOGADOR 2 VENCEU!");
    }
}

private void mostrarVitoria(String mensagem) {
    jogoAcabado = true;
    textoVitoria.setText(mensagem);
    textoVitoria.setVisible(true);
    textoInstrucao.setVisible(true);
}

private void reiniciarPartida() {
    Jogador1 = 0;
    Jogador2 = 0;
    textoPontos1.setText("0");
    textoPontos2.setText("0");

    textoVitoria.setVisible(false);
    textoInstrucao.setVisible(false);

    bola.setPosition(400, 300);
    bola.setProperty("velocidadeX", 300.0);
    bola.setProperty("velocidadeY", 300.0);

    jogoAcabado = false;
}

protected void onUpdate(double tpf) {
    double velocidadeX = bola.getDouble("velocidadeX");
    double velocidadeY = bola.getDouble("velocidadeY");
    if (jogoAcabado) {
        return; 
    }

    bola.translate(velocidadeX * tpf, velocidadeY * tpf);

    if (bola.getY() <= 0 || bola.getY() >= 580) {
        bola.setProperty("velocidadeY", -velocidadeY);
    }
    if (bola.getX() <= 0) {
            Jogador2++;
            textoPontos2.setText(String.valueOf(Jogador2));

            bola.setPosition(400, 300);
            bola.setProperty("velocidadeX", 300.0);
        }

    if (bola.getX() <= 0 || bola.getX() >= 890) {
        Jogador1++;
        textoPontos1.setText(String.valueOf(Jogador1));

        bola.setPosition(450, 300);
        bola.setProperty("velocidadeX", -300.0);

        verificarVitoria();
    }
}
    public static void main(String[] args) {// inicia o jogo
        launch(args);
    }
}