package views;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class GameWinScene extends Scene {

    public static StackPane root = new StackPane();
    public static VBox stats = new VBox();

    private static Timer timer = new Timer();
    public static String timeTaken = "00 : 00";
    public static int roundNumber = 0;

    private static Label message1 = new Label();
    private Label message2 = new Label();
    private Label message3 = new Label();
    private Label message4 = new Label();

    private static FadeTransition fadeEffect = new FadeTransition();

    private static Label pressAnyKeyToContinueLabel = new Label("Press any key to exit ...");

    public GameWinScene(){
//        super(root, 1200, 800, Color.rgb(34, 56, 78));
        super(new BorderPane(), 1200, 800, Color.rgb(34, 56, 78));
        this.getStylesheets().add(GameOverScene.class.getResource("styles/gameWinScene.css").toExternalForm());

        Main.clearListeners();

        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root5");

        message1.getStyleClass().add("message");
        message1.setAlignment(Pos.CENTER);
//        message1.setTranslateY(-75);
        message1.translateYProperty().bind(Bindings.divide(Main.height,-10.667));
//        message1.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),18.75), "px;"));
        message1.setText("You won the game!");
        message1.setOpacity(0);

        try {
            message1.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/12.5));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        root.getChildren().add(message1);

        setWindowResizeableListener();
    }

    public void startTimer() {
        createStats();

        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.getChildren().clear();
                    root.getChildren().add(stats);
                    setClosingTimer();
                });
            }
        };

        timer.schedule(t1, 5000);
    }

    public void setClosingTimer(){
        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    createActionListeners();
                    addPressAnyKeyToContinue();
                    // upon allowing to close preview a message zay beta3et how to play
                });
            }
        };
        timer.schedule(t2, 4000);
    }

    public void createStats(){
        stats = new VBox();
        message2.setText("Your stats");
        message3.setText("Time  " + timeTaken.substring(0,3) + " " + timeTaken.substring(4,7));
        message4.setText("Rounds played  " + roundNumber);

        message2.setAlignment(Pos.CENTER);
        message2.setTextAlignment(TextAlignment.CENTER);
        message3.setAlignment(Pos.CENTER);
        message3.setTextAlignment(TextAlignment.CENTER);
        message4.setAlignment(Pos.CENTER);
        message4.setTextAlignment(TextAlignment.CENTER);

        message2.getStyleClass().add("messages");
        message3.getStyleClass().add("messages");
        message4.getStyleClass().add("messages");
//        message2.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),25), "px;"));
//        message3.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),25), "px;"));
//        message4.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),25), "px;"));
        try {
            message2.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
            message3.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
            message4.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        stats.setOpacity(0);
        stats.setSpacing(5);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(message2, message3, message4);
    }

    private void createActionListeners() {
        // dost bel mouse close the window bas e3melhom be time delay 3shan maye2felsh bel 8alat awel mal screen tigy
        Main.myScene.setOnMouseClicked(e -> {
            Main.currentStage.close();
        });

        // dost ay zorar close the window bas e3melhom be time delay 3shan maye2felsh bel 8alat awel mal screen tigy
        Main.myScene.setOnKeyPressed(e -> {
            if (e.getCode() != KeyCode.F11)
                Main.currentStage.close();
        });
    }

    private static FadeTransition createFadeTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(3000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(false);

        return fadeTransition;
    }

    public static void startAnimationCycle() {
        FadeTransition fadeTransition1 = createFadeTransition();
        fadeTransition1.setNode(message1);
        fadeTransition1.play();

        TimerTask fadeOutTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    FadeTransition fadeTransition2 = createFadeTransition();
                    fadeTransition2.setNode(message1);
                    fadeTransition2.setFromValue(message1.getOpacity());
                    fadeTransition2.setToValue(0);
                    fadeTransition2.play();
                });
            }
        };

        TimerTask fadeInStats = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    FadeTransition fadeTransition3 = createFadeTransition();
                    fadeTransition3.setNode(stats);
                    fadeTransition3.setFromValue(0);
                    fadeTransition3.setToValue(1);
                    fadeTransition3.play();
                });
            }
        };

        timer.schedule(fadeOutTask, 2500);
        timer.schedule(fadeInStats, 5000);
    }

    private void setWindowResizeableListener() {
        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                Main.WINDOW_WIDTH = Main.myScene.getWidth();
                Main.WINDOW_HEIGHT = Main.myScene.getHeight();

                message1.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/12.5);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                message2.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/15);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                message3.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/15);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
                message4.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/15);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));

//                try {
//                    message1.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/12.5));
//                    message2.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
//                    message3.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
//                    message4.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

//                text.setTranslateY(Main.WINDOW_WIDTH/10.435);
//                text.setTranslateX(Main.WINDOW_WIDTH/80);

            }
        };

        Main.myScene.widthProperty().addListener(changeListener);
        Main.myScene.heightProperty().addListener(changeListener);
    }

    private static void addPressAnyKeyToContinue() {
        pressAnyKeyToContinueLabel.getStyleClass().add("press-any-key");

//        pressAnyKeyToContinueLabel.translateXProperty().bind(Bindings.divide(Main.myScene.widthProperty(), 3.158));
        pressAnyKeyToContinueLabel.translateYProperty().bind(Bindings.divide(Main.myScene.heightProperty(),2.540));
        pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/33.333 + "px;"));

        fadeEffect.setDuration(Duration.millis(2000));
        fadeEffect.setFromValue(0);
        fadeEffect.setToValue(0.8);
        fadeEffect.setCycleCount(1000); // rakam kebeeer
        fadeEffect.setAutoReverse(true);
        fadeEffect.setNode(pressAnyKeyToContinueLabel);

        root.getChildren().add(pressAnyKeyToContinueLabel);
        fadeEffect.play();
    }



}
