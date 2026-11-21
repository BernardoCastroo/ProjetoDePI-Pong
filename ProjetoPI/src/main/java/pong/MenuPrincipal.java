package pong;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuPrincipal extends FXGLMenu {

    public static String modoSelecionado = "PVP"; 

    public MenuPrincipal() {
        super(MenuType.MAIN_MENU);

        Rectangle fundo = new Rectangle(900, 600, Color.web("#111111"));

        Line linhaFundo = new Line(450, 0, 450, 600);
        linhaFundo.setStroke(Color.web("#222222"));
        linhaFundo.setStrokeWidth(4);
        linhaFundo.getStrokeDashArray().addAll(15.0, 15.0);

        Rectangle raqueteEsq = new Rectangle(40, 220, 16, 110);
        raqueteEsq.setFill(Color.web("#2A2A2A"));

        Rectangle raqueteDir = new Rectangle(844, 270, 16, 110);
        raqueteDir.setFill(Color.web("#2A2A2A"));

        Circle bolaDecorativa = new Circle(620, 140, 10, Color.web("#333333"));

        Text titulo = new Text("PONG");
        titulo.setFont(Font.font("Consolas", FontWeight.BOLD, 84));
        titulo.setFill(Color.WHITE);

        Text subtitulo = new Text("SELECIONE O MODO DE JOGO");
        subtitulo.setFont(Font.font("Consolas", FontWeight.NORMAL, 16));
        subtitulo.setFill(Color.web("#888888"));

        Button btnPVP = criarBotaoArcade("CLASSICO (JOGADOR VS JOGADOR)");
        Button btnBot = criarBotaoArcade("JOGADOR VS BOT");
        Button btnArcade = criarBotaoArcade("MODO ARCADE");
        Button btnSair = criarBotaoArcade("SAIR");

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

        btnSair.setOnAction(e -> fireExit());

        VBox layoutBotoes = new VBox(14, btnPVP, btnBot, btnArcade, btnSair);
        layoutBotoes.setAlignment(Pos.CENTER);

        VBox layoutGeral = new VBox(15, titulo, subtitulo, layoutBotoes);
        layoutGeral.setAlignment(Pos.CENTER);
        layoutGeral.setPrefSize(900, 600); 
        getContentRoot().getChildren().addAll(
                fundo, linhaFundo, raqueteEsq, raqueteDir, bolaDecorativa, layoutGeral
        );
    }

    private Button criarBotaoArcade(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(280);
        btn.setPrefHeight(44);

        String estiloNormal =
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Consolas';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;";

        String estiloHover =
                "-fx-background-color: white;" +
                "-fx-text-fill: #111111;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-font-family: 'Consolas';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;";

        btn.setStyle(estiloNormal);

        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloNormal));

        return btn;
    }
}