package pong;

import com.almasb.fxgl.app.GameApplication;// funcionalidades do engine 
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.input.KeyCode;

public class PongJogo extends GameApplication{

    private Entity bola;
    private Entity raquete1;
    private Entity raquete2;

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
    }
    
@Override
protected void onUpdate(double tpf) {
    // Usa getDouble em vez de getObject para ler valores numéricos decimais
    double velocidadeX = bola.getDouble("velocidadeX");
    double velocidadeY = bola.getDouble("velocidadeY");

    bola.translate(velocidadeX * tpf, velocidadeY * tpf);

    // Ressalto nas paredes superior e inferior
    if (bola.getY() <= 0 || bola.getY() >= 580) {
        bola.setProperty("velocidadeY", -velocidadeY);
    }

    // Reposicionamento temporário se sair pelas laterais
    if (bola.getX() <= 0 || bola.getX() >= 890) {
        bola.setPosition(450, 300);
    }
}
    public static void main(String[] args) {// inicia o jogo
        launch(args);
    }
}