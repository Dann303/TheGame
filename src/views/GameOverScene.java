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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class GameOverScene extends Scene {

    public static StackPane root = new StackPane();
    private static Timer timer = new Timer();
    private static Label loseMessage1 = new Label();
    private static Label loseMessage2 = new Label();

    private static Label pressAnyKeyToContinueLabel = new Label("Press any key to exit ...");
    private static FadeTransition fadeEffect = new FadeTransition();

    public GameOverScene() throws FileNotFoundException {
//        super(root, 1200, 800, Color.rgb(34, 56, 78));
        super(new BorderPane(), 1200, 800, Color.rgb(34, 56, 78));
        this.getStylesheets().add(GameOverScene.class.getResource("styles/gameOverScene.css").toExternalForm());

        Main.clearListeners();

//        startTimer();
//        startAnimationCycle();

        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        root.getStyleClass().add("root6");

        loseMessage1.getStyleClass().add("loseMessage");
        loseMessage1.setAlignment(Pos.CENTER);
//        message1.setTranslateY(-75);
        loseMessage1.translateYProperty().bind(Bindings.divide(Main.height,-10.667));
//        message1.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),18.75), "px;"));
        loseMessage1.setText("You have lost!");
        loseMessage1.setOpacity(0);
        try {
            loseMessage1.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/12.5));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        loseMessage2.getStyleClass().add("loseMessage");
        loseMessage2.setAlignment(Pos.CENTER);
//        message1.setTranslateY(-75);
        loseMessage2.translateYProperty().bind(Bindings.divide(Main.height,-10.667));
//        message1.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.myScene.widthProperty(),18.75), "px;"));
        loseMessage2.setText("Better luck next time!");
        loseMessage2.setOpacity(0);
        try {
            loseMessage2.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/12.5));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        root.getChildren().addAll(loseMessage1,loseMessage2);

//        ImageView tree_guy= getImageViewOfCharacter("src/views/images/tree_guy.gif");
        StackPane tree_guy = new StackPane();
//        tree_guy.setTranslateY(200);
        tree_guy.translateYProperty().bind(Bindings.divide(Main.height, 4));
//        tree_guy.setTranslateX(350);
        tree_guy.translateXProperty().bind(Bindings.divide(Main.width, 3.429));
//        tree_guy.setMaxWidth(300);
        tree_guy.maxWidthProperty().bind(Bindings.divide(Main.width, 4));
//        tree_guy.setMaxHeight(300);
        tree_guy.maxHeightProperty().bind(Bindings.divide(Main.height, 2.667));

        Label gif = new Label();
        gif.getStyleClass().add("media");
//        gif.setMinWidth(300);
        gif.minWidthProperty().bind(Bindings.divide(Main.width, 4));
//        gif.setMinHeight(300);
        gif.minHeightProperty().bind(Bindings.divide(Main.height, 2.667));

//        Label clip = new Label();
//        clip.setMinHeight(300);
//        clip.setMinWidth(300);
//        clip.getStyleClass().add("gif");
//        tree_guy.setClip(clip);

        tree_guy.getChildren().add(gif);
        tree_guy.getStyleClass().add("gif");
        root.getChildren().add(tree_guy);

//        Media media = new Media(new File("C:\\Users\\danie\\eclipse-workspace\\The Game\\TheGame\\src\\views\\images\\guy_throws_tree_short.mp4").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
//        MediaView mediaView = new MediaView(mediaPlayer);
//        mediaView.setFitWidth(500);
//        mediaView.setFitHeight(500);
//
//        root.setAlignment(Pos.CENTER);
//        root.getChildren().add(mediaView);

        setWindowResizeableListener();
    }

    public void startTimer() {
        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                   createActionListeners();
                   addPressAnyKeyToContinue();
                });
            }
        };
        timer.schedule(t1, 10000);
    }

    private static FadeTransition createFadeTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2500));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(false);

        return fadeTransition;
    }

    public static void startAnimationCycle() {
        FadeTransition fadeTransition1 = createFadeTransition();
        fadeTransition1.setNode(loseMessage1);
        fadeTransition1.play();

        TimerTask fadeOutTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    FadeTransition fadeTransition2 = createFadeTransition();
                    fadeTransition2.setNode(loseMessage1);
                    fadeTransition2.setFromValue(loseMessage1.getOpacity());
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
                    fadeTransition3.setNode(loseMessage2);
                    fadeTransition3.setFromValue(0);
                    fadeTransition3.setToValue(1);
                    fadeTransition3.play();
                });
            }
        };

        timer.schedule(fadeOutTask, 3000);
        timer.schedule(fadeInStats, 5750);
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

//    private static ImageView getImageViewOfCharacter(String path) throws FileNotFoundException {
//        FileInputStream file = new FileInputStream(path);
//        Image image = new Image(file);
//        ImageView view = new ImageView(image);
//        view.setFitHeight(300);
//        view.setFitWidth(300);
//        return view;
//    }

    private void setWindowResizeableListener() {
        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                Main.WINDOW_WIDTH = Main.myScene.getWidth();
                Main.WINDOW_HEIGHT = Main.myScene.getHeight();

                loseMessage1.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/12.5);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));

                loseMessage2.fontProperty().bind(Main.width.map(size -> {
                    try {
                        return Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), size.floatValue()/12.5);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }));
            }
        };

        Main.myScene.widthProperty().addListener(changeListener);
        Main.myScene.heightProperty().addListener(changeListener);
    }

    private static void addPressAnyKeyToContinue() {
        pressAnyKeyToContinueLabel.getStyleClass().add("press-any-key");

//        pressAnyKeyToContinueLabel.translateXProperty().bind(Bindings.divide(Main.myScene.widthProperty(), 3.158));
        pressAnyKeyToContinueLabel.translateYProperty().bind(Bindings.divide(Main.myScene.heightProperty(),2.540));
        pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width ,33.333), "px;"));

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
