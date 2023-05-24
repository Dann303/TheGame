package gui.mrd;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class GameWinScene extends Scene {

    public static BorderPane root = new BorderPane();
    private Timer timer = new Timer();


    public GameWinScene(){
        super(root, 1200, 800, Color.rgb(34, 56, 78));
        this.getStylesheets().add(GameOverScene.class.getResource("styles/gameWinScene.css").toExternalForm());
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
