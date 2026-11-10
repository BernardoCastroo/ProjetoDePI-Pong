package pong;

import com.almasb.fxgl.app.GameApplication;// funcionalidades do engine 
import com.almasb.fxgl.app.GameSettings;

public class PongJogo extends GameApplication{

    protected void initSettings(GameSettings settings) {// Janela de escrita
        settings.setTitle("Pong");
        settings.setWidth(800);
        settings.setHeight(600);
    }

    public static void main(String[] args) {// inicia o jogo
        launch(args);
    }
}