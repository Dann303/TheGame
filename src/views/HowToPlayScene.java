package views;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class HowToPlayScene extends Scene {
    public static StackPane root = new StackPane();
    public static VBox instructions = new VBox();
    private static FadeTransition fadeEffect = new FadeTransition();
    public static Timer timer = new Timer();


    public HowToPlayScene() {
        super(root, 1200, 800, Color.rgb(34, 56, 78));

        this.getStylesheets().add(Scene1.class.getResource("styles/howToPlay.css").toExternalForm());

        root.setAlignment(Pos.CENTER_LEFT);

        instructions.getStyleClass().add("instructions");

        instructions.setPadding(new Insets(0,0,0,85));
        instructions.setAlignment(Pos.CENTER);
        instructions.setMaxWidth(1000);
        instructions.setSpacing(30);
        instructions.setTranslateY(-50);

        Label title = new Label("How To Play");
        title.getStyleClass().add("instructions-title");
        title.setTranslateX(-150);

        Label instructionsText = new Label();
        instructionsText.setMaxWidth(1000);
        instructionsText.setTranslateY(50);
        instructionsText.getStyleClass().add("instructions-text");
        instructionsText.setLineSpacing(10);
        instructionsText.setWrapText(true);
        instructionsText.setText("After picking the hero you are to play with, you'll be \npresented to a grid. " +
                "To start playing, you will select the \nhero you want to use by clicking on his corresponding hero \nicon on the grid. " +
                "Your goal is to find all 5 vaccines in the map and \nuse them to cure 5 heroes and add them into your team. " +
                "Hover over a \ncell to see what lies there if it is visible to you, similarly you can preview \nall your " +
                "available heroes that you can play with by hovering over them and \ndisplaying their stats on the right sidebar. " +
                "you can move by either clicking on an \nappropriate cell after selecting the hero to move, or you can click on the " +
                "arrow key buttons \npresented on the screen or even using your keyboard (wasd & arrow keys)   ...                 Enjoy! :)");

//        instructionsText.setText("After picking the hero you are to play with, you'll be \npresented to a grid. " +
//                "To start playing, you will select the \nhero you want to use by clicking on his corresponding hero \nicon on the grid. " +
//                "Your goal is to find all 5 vaccines in the map and \nuse them to cure 5 heroes and add them into your team. " +
//                "You can hover \nover the cell to see what lies there if it is visible to you, similarly you can \npreview all your " +
//                "available heroes that you can play with by hovering over them \nand displaying their stats on the right sidebar. " +
//                "you can move by either clicking on an \nappropriate cell after selecting the hero to move, or you can click on the " +
//                "arrow key buttons \npresented on the screen or even using your keyboard (wasd & arrow keys)... Enjoy! :)");

        instructions.getChildren().addAll(title, instructionsText);
        root.getChildren().add(instructions);

    }

    public void startAllowContinue() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    addPressAnyKeyToContinue();
                    timer.cancel();
                });
            }
        };

        timer.schedule(task, 5000);
    }

    private void addPressAnyKeyToContinue() {
        Label label = new Label("Press any key to continue ...");
        label.getStyleClass().add("press-any-key");
        label.setTranslateX(380);
        label.setTranslateY(315);

        this.setOnMouseClicked(e -> {
            Main.currentStage.setScene(Main.s2);
        });

        this.setOnKeyPressed(e -> {
            Main.currentStage.setScene(Main.s2);
        });

        fadeEffect.setDuration(Duration.millis(2000));
        fadeEffect.setFromValue(0);
        fadeEffect.setToValue(0.8);
        fadeEffect.setCycleCount(1000); // rakam kebeeer
        fadeEffect.setAutoReverse(true);
        fadeEffect.setNode(label);

        root.getChildren().add(label);
        fadeEffect.play();
    }

}