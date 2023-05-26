package gui.mrd;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Timer;
import java.util.TimerTask;

public class GameWinScene extends Scene {

    public static BorderPane root = new BorderPane();
    private Timer timer = new Timer();
    public static String timeTaken = "00 : 00";
    public static int roundNumber = 0;

    public GameWinScene(){
        super(root, 1200, 800, Color.rgb(34, 56, 78));
        this.getStylesheets().add(GameOverScene.class.getResource("styles/gameWinScene.css").toExternalForm());

        Label message1 = new Label("You won the game!");
        message1.getStyleClass().add("message");
        message1.setAlignment(Pos.CENTER);
        message1.setTranslateY(-75);

        root.setCenter(message1);

        VBox stats = new VBox();
        Label message2 = new Label("Your stats");
        Label message3 = new Label("Time : " + timeTaken);
        Label message4 = new Label("Rounds played : " + roundNumber);

        message2.setAlignment(Pos.CENTER);
        message2.setTextAlignment(TextAlignment.CENTER);
        message3.setAlignment(Pos.CENTER);
        message3.setTextAlignment(TextAlignment.CENTER);
        message4.setAlignment(Pos.CENTER);
        message4.setTextAlignment(TextAlignment.CENTER);

        message2.getStyleClass().add("messages");
        message3.getStyleClass().add("messages");
        message4.getStyleClass().add("messages");

        stats.setSpacing(2);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(message2, message3, message4);

        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.getChildren().clear();
                    root.setCenter(stats);
                });
            }
        };

        timer.schedule(t2, 5000);
    }

    public void startTimer() {
        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    createActionListeners();
                });
            }
        };
        timer.schedule(t1, 5000);
    }

    private void createActionListeners() {
        // dost bel mouse close the window bas e3melhom be time delay 3shan maye2felsh bel 8alat awel mal screen tigy
        this.setOnMouseClicked(e -> {
            Main.currentStage.close();
        });

        // dost ay zorar close the window bas e3melhom be time delay 3shan maye2felsh bel 8alat awel mal screen tigy
        this.setOnKeyPressed(e -> {
            Main.currentStage.close();
        });
    }

}
