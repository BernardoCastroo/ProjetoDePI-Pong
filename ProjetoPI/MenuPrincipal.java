package pong;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuPrincipal extends FXGLMenu {

    // Variável estática para o jogo saber qual modo foi escolhido
    public static String modoSelecionado = "PVP"; // "PVP", "BOT" ou "ARCADE"

    public MenuPrincipal() {
        super(MenuType.MAIN_MENU);

        Text titulo = FXGL.getUIFactoryService().newText("PONG", Color.BLACK, 50);
        titulo.setTranslateX(450 - 120);
        titulo.setTranslateY(150);

        Button btnPVP = new Button("1 Jogador vs 2 Jogadores");
        Button btnBot = new Button("Jogador vs Bot");
        Button btnArcade = new Button("Modo Arcade");

        btnPVP.setOnAction(e -> {
            modoSelecionado = "PVP";
            fireNewGame();
        });

        btnBot.setOnAction(e -> {
            modoSelecionado = "BOT";
            fireNewGame();
        });

        btnArcade.setOnAction(e -> {
            modoSelecionado = "ARCADE";
            fireNewGame();
        });

        VBox box = new VBox(15, btnPVP, btnBot, btnArcade);
        box.setAlignment(Pos.CENTER);
        box.setTranslateX(450 - 100);
        box.setTranslateY(250);

        getContentRoot().getChildren().addAll(titulo, box);
    }
}