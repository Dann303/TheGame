package views;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class HowToPlayScene extends Scene {
    public static StackPane root = new StackPane();
    public static VBox instructions = new VBox();
    private static FadeTransition fadeEffect = new FadeTransition();
    public static Timer timer = new Timer();

    private static Label title = new Label("How To Play");
    private static Label instructionsText = new Label();

    private static Label pressAnyKeyToContinueLabel = new Label("Press any key to continue ...");


    public HowToPlayScene() {
        super(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34, 56, 78));

        this.getStylesheets().add(Scene1.class.getResource("styles/howToPlay.css").toExternalForm());

        root.setAlignment(Pos.CENTER_LEFT);

        instructions.getStyleClass().add("instructions");

        instructions.setPadding(new Insets(0,0,0,Main.WINDOW_WIDTH/14.118));
        instructions.setAlignment(Pos.CENTER);
        instructions.setMaxWidth(Main.WINDOW_WIDTH/1.2);
        instructions.setSpacing(Main.WINDOW_HEIGHT/26.667);
        instructions.setTranslateY(-Main.WINDOW_HEIGHT/16);

        title.getStyleClass().add("instructions-title");
        title.setTranslateX(-Main.WINDOW_WIDTH/8);

        instructionsText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
        instructionsText.setTranslateY(Main.WINDOW_HEIGHT/16);
        instructionsText.getStyleClass().add("instructions-text");
        instructionsText.setLineSpacing(Main.WINDOW_HEIGHT/80);
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

        setWindowResizeableListener();

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
        pressAnyKeyToContinueLabel.getStyleClass().add("press-any-key");
        pressAnyKeyToContinueLabel.setTranslateX(Main.WINDOW_WIDTH/3.158);
        pressAnyKeyToContinueLabel.setTranslateY(Main.WINDOW_HEIGHT/2.540);

        this.setOnMouseClicked(e -> {
            try {
                Main.s2 = new Scene2();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            Main.setUpSceneWindowResizeDetector(Main.howToPlayScene, null);
            boolean wasFullScreen = Main.currentStage.isFullScreen();
            Main.currentStage.setScene(Main.s2);
            if (wasFullScreen)
                Main.currentStage.setFullScreen(true);
            Main.setUpSceneWindowResizeDetector(null, Main.s2);
        });

        this.setOnKeyPressed(e -> {
            try {
                Main.s2 = new Scene2();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            Main.setUpSceneWindowResizeDetector(Main.howToPlayScene, null);
            boolean wasFullScreen = Main.currentStage.isFullScreen();
            Main.currentStage.setScene(Main.s2);
            if (wasFullScreen)
                Main.currentStage.setFullScreen(true);
            Main.setUpSceneWindowResizeDetector(null, Main.s2);
        });

        fadeEffect.setDuration(Duration.millis(2000));
        fadeEffect.setFromValue(0);
        fadeEffect.setToValue(0.8);
        fadeEffect.setCycleCount(1000); // rakam kebeeer
        fadeEffect.setAutoReverse(true);
        fadeEffect.setNode(pressAnyKeyToContinueLabel);

        root.getChildren().add(pressAnyKeyToContinueLabel);
        fadeEffect.play();
    }

    private void setWindowResizeableListener() {
        Main.height.bind(this.heightProperty());
        Main.width.bind(this.widthProperty());
        title.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));
        instructionsText.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/60 + "px;"));
        pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/33.333 + "px;"));

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Main.WINDOW_HEIGHT = Main.howToPlayScene.getHeight();
                Main.WINDOW_WIDTH = Main.howToPlayScene.getWidth();

                instructions.setPadding(new Insets(0,0,0,Main.WINDOW_WIDTH/14.118));
                instructions.setMaxWidth(Main.WINDOW_WIDTH/1.2);
                instructions.setSpacing(Main.WINDOW_HEIGHT/26.667);
                instructions.setTranslateY(-Main.WINDOW_HEIGHT/16);

                title.setTranslateX(-Main.WINDOW_WIDTH/8);
                title.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));

                instructionsText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
                instructionsText.setTranslateY(Main.WINDOW_HEIGHT/16);
                instructionsText.setLineSpacing(Main.WINDOW_HEIGHT/80);
                instructionsText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width, 60), "px;"));

                pressAnyKeyToContinueLabel.setTranslateX(Main.WINDOW_WIDTH/3.158);
                pressAnyKeyToContinueLabel.setTranslateY(Main.WINDOW_HEIGHT/2.540);
                pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/33.333 + "px;"));

            }
        };

        this.widthProperty().addListener(changeListener);
        this.heightProperty().addListener(changeListener);
    }

}