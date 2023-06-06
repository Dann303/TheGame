package views;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class HowToPlayScene extends Scene {
    public static StackPane root = new StackPane();
    public static VBox instructions = new VBox();
    public static VBox controlsPage = new VBox();
    private static FadeTransition fadeEffect = new FadeTransition();
    public static Timer timer = new Timer();

    private static Label objectiveTitle = new Label("Objective");
    private static Label controlsTitle = new Label("How To Play");
    private static Label objectiveText = new Label();
    private static Label controlsText = new Label();

    private static Label pressAnyKeyToContinueLabel = new Label("Press any key to continue ...");
    private static Label toControlsButton = new Label();
    private static Label toObjectiveButton = new Label();

    private static boolean isControlsPage = false;
    private static boolean startedTimer = false;


    public HowToPlayScene() {
//        super(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34, 56, 78));
        super(new BorderPane(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34, 56, 78));

        Main.clearListeners();

        this.getStylesheets().add(Scene1.class.getResource("styles/howToPlay.css").toExternalForm());

        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("-fx-background-color:black;");

        instructions.getStyleClass().addAll("instructions", "root2");
        instructions.minWidthProperty().bind(Bindings.divide(Main.width,1));
        instructions.minHeightProperty().bind(Bindings.divide(Main.height,1));

        instructions.setPadding(new Insets(0,0,0,Main.WINDOW_WIDTH/14.118));
        instructions.setAlignment(Pos.CENTER);
//        instructions.setMaxWidth(Main.WINDOW_WIDTH/1.2);
        instructions.setSpacing(Main.WINDOW_HEIGHT/26.667);
//        instructions.setTranslateY(-Main.WINDOW_HEIGHT/16);

        objectiveTitle.getStyleClass().add("instructions-title");
        objectiveTitle.setTranslateX(-Main.WINDOW_WIDTH/4);

        objectiveText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
        objectiveText.setTranslateY(Main.WINDOW_HEIGHT/16);
        objectiveText.getStyleClass().add("instructions-text");
        objectiveText.setLineSpacing(Main.WINDOW_HEIGHT/80);
        objectiveText.setWrapText(true);
//        instructionsText.setText(
//                "After picking the hero you are to play with, you'll be \n" +
//                "presented to a grid. To start playing, you will select the \n" +
//                "hero you want to use by clicking on his corresponding hero \n" +
//                "icon on the grid. Your goal is to find all 5 vaccines in the map and \n" +
//                "use them to cure 5 heroes and add them into your team. Hover over a \n" +
//                "cell to see what lies there if it is visible to you, similarly you can preview \n" +
//                "all your available heroes that you can play with by hovering over them and \n" +
//                "displaying their stats on the right sidebar. you can move by either clicking on an \n" +
//                "appropriate cell after selecting the hero to move, or you can click on the arrow key buttons \n" +
//                "presented on the screen or even using your keyboard (wasd & arrow keys)   ...                 Enjoy! :)"
//        );

        objectiveText.setText(
                "With the gruesome zombie apocalypse breaking out \n" +
                "a couple days ago, your objective is to stay alive, grab as \n" +
                "many vaccines as you possibly can (5) and use them to get back \n" +
                "your friends and loved ones as an attempt of surviving on this dark \n" +
                "world. First you'll select a hero from the available hero types. The game \n" +
                "will start and you'll be left all on your own! Beware the traps that lies ahead \n" +
                "of you on your ways. If you or any of your friends die, you lose the game! So watch out \n" +
                "for your team's health and take appropriate action if necessary...\n"
        );

        instructions.getChildren().addAll(objectiveTitle, objectiveText);
        root.getChildren().add(instructions);

        setUpControlsPage();
        addArrowButton();

        setWindowResizeableListener();

    }

    public static void startAllowContinue() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    addPressAnyKeyToContinue();
                    timer.cancel();
                });
            }
        };

        timer.schedule(task, 3000);
    }

    private static boolean clickedOnButtons(double x, double y) {
        if (((x<Main.WINDOW_WIDTH && x>(Main.WINDOW_WIDTH/1.091)) || (x<(Main.WINDOW_WIDTH/12) && x>0)) && (y>(Main.WINDOW_HEIGHT/2.286) && y<(Main.WINDOW_HEIGHT/1.778))) {
            return true;
        }
        return false;
    }

    private static void addPressAnyKeyToContinue() {
        pressAnyKeyToContinueLabel.getStyleClass().add("press-any-key");
        pressAnyKeyToContinueLabel.setTranslateX(Main.WINDOW_WIDTH/3.158);
        pressAnyKeyToContinueLabel.setTranslateY(Main.WINDOW_HEIGHT/2.540);

        Main.myScene.setOnMouseClicked(e -> {
            if (!clickedOnButtons(e.getX(), e.getY())) {
                try {
                    Main.s2 = new Scene2();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                Main.setUpSceneWindowResizeDetector(Main.howToPlayScene, null);
                boolean wasFullScreen = Main.currentStage.isFullScreen();
//            Main.currentStage.setScene(Main.s2);
                Main.currentStage.getScene().setRoot(Scene2.root);
                if (wasFullScreen)
                    Main.currentStage.setFullScreen(true);
                Main.setUpSceneWindowResizeDetector(null, Main.s2);
            }
        });

        Main.myScene.setOnKeyPressed(e -> {
            if (e.getCode() != KeyCode.F11 && e.getCode() != KeyCode.LEFT && e.getCode() != KeyCode.RIGHT && e.getCode() != KeyCode.A && e.getCode() != KeyCode.D) {
                try {
                    Main.s2 = new Scene2();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                Main.setUpSceneWindowResizeDetector(Main.howToPlayScene, null);
                boolean wasFullScreen = Main.currentStage.isFullScreen();
//            Main.currentStage.setScene(Main.s2);
                Main.currentStage.getScene().setRoot(Scene2.root);
                if (wasFullScreen)
                    Main.currentStage.setFullScreen(true);
                Main.setUpSceneWindowResizeDetector(null, Main.s2);
            } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                slideLeftOrRight("Left");
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                slideLeftOrRight("Right");
            }
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
        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());
        objectiveTitle.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));
        controlsTitle.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));
        objectiveText.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/60 + "px;"));
        controlsText.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/60 + "px;"));
        pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/33.333 + "px;"));

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Main.WINDOW_HEIGHT = Main.myScene.getHeight();
                Main.WINDOW_WIDTH = Main.myScene.getWidth();

                if (!isControlsPage) {
                    controlsPage.setTranslateX(Main.WINDOW_WIDTH);
                    toObjectiveButton.setTranslateX(2*Main.WINDOW_WIDTH - toObjectiveButton.getMinWidth() - 10);
                } else {
                    toObjectiveButton.setTranslateX(Main.WINDOW_WIDTH - toObjectiveButton.getMinWidth() - 10);
                }

                instructions.setPadding(new Insets(0,0,0,Main.WINDOW_WIDTH/14.118));
//                instructions.setMaxWidth(Main.WINDOW_WIDTH/1.2);
                instructions.setSpacing(Main.WINDOW_HEIGHT/26.667);
//                instructions.setTranslateY(-Main.WINDOW_HEIGHT/16);

                objectiveTitle.setTranslateX(-Main.WINDOW_WIDTH/4);
                objectiveTitle.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));

                controlsTitle.setTranslateX(Main.WINDOW_WIDTH/5.5);
                controlsTitle.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/25 + "px;"));

                objectiveText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
                objectiveText.setTranslateY(Main.WINDOW_HEIGHT/16);
                objectiveText.setLineSpacing(Main.WINDOW_HEIGHT/80);
                objectiveText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width, 60), "px;"));

                controlsText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
                controlsText.setLineSpacing(Main.WINDOW_HEIGHT/80);
                controlsText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width, 60), "px;"));

                pressAnyKeyToContinueLabel.setTranslateX(Main.WINDOW_WIDTH/3.158);
                pressAnyKeyToContinueLabel.setTranslateY(Main.WINDOW_HEIGHT/2.540);
                pressAnyKeyToContinueLabel.styleProperty().bind(Bindings.concat("-fx-font-size: " + Main.WINDOW_WIDTH/33.333 + "px;"));

            }
        };

        Main.myScene.widthProperty().addListener(changeListener);
        Main.myScene.heightProperty().addListener(changeListener);
    }

    private static void setUpControlsPage() {
        controlsPage.minWidthProperty().bind(Bindings.divide(Main.width,1));
        controlsPage.minHeightProperty().bind(Bindings.divide(Main.height,1));

        controlsPage.getStyleClass().add("controls-page-background");
        controlsPage.setTranslateX(Main.WINDOW_WIDTH);
//        controlsPage.translateYProperty().bind(Bindings.divide(Main.height,1));
//        controlsPage.translateXProperty().bind(Bindings.divide(Main.width,1));

        controlsPage.setAlignment(Pos.TOP_CENTER);
        controlsPage.spacingProperty().bind(Bindings.divide(Main.height, 20));

        controlsTitle.getStyleClass().add("instructions-title");
        controlsTitle.setTranslateX(Main.WINDOW_WIDTH/5.5);

        controlsTitle.translateYProperty().bind(Bindings.divide(Main.height,10));

        controlsText.setMaxWidth(Main.WINDOW_WIDTH/1.2);
        controlsText.getStyleClass().add("instructions-text");
        controlsText.setLineSpacing(Main.WINDOW_HEIGHT/80);
        controlsText.setWrapText(true);
        controlsText.setStyle("-fx-background-color:red;");
        controlsText.setTextAlignment(TextAlignment.CENTER);
        controlsText.setAlignment(Pos.CENTER);

        controlsText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width, 60), "px;"));
        controlsText.translateXProperty().bind(Bindings.divide(Main.width, 5.5));
        controlsText.translateYProperty().bind(Bindings.divide(Main.height,10));

        // wasd - arrows : movement
        // e: end turn - tab: swap selected hero - esc: remove alertbox - c: cure - q: activate special ability
        // t: select target mode - enter: select the currently hovered cell to be the target
        // k: attacks the selected target

        controlsText.setText(
                "               There are controls available with your              \n" +
                "                    mouse, but if you're like                      \n" +
                "               me and your mouse is broken then I'm                \n" +
                "                  sure you'll love these hotkeys.                  \n" +
                "                                                                   \n" +
                "                                                                   \n" +
                "                  Move : WASD keys or Arrow Keys                   \n" +
                " T : Select target     &&    Enter : to enter the cell as a target \n" +
                "     ESC: Exit alert message          K : Attack selected target   \n" +
                "   Q: Activate special ability         Tab : Swap Selected Hero    \n" +
                "           C : Cure                          E : End turn          \n"
        );



        controlsPage.getChildren().addAll(controlsTitle, controlsText);

        root.getChildren().add(controlsPage);
    }

    private static void addArrowButton() {
        toControlsButton.setTranslateX(10);
        toControlsButton.minWidthProperty().bind(Bindings.divide(Main.width,24));
        toControlsButton.minHeightProperty().bind(Bindings.divide(Main.height,16));
        toControlsButton.getStyleClass().addAll("arrow", "toControls");
        toControlsButton.setOnMouseClicked(e -> slideLeftOrRight(null));

        toObjectiveButton.minWidthProperty().bind(Bindings.divide(Main.width,24));
        toObjectiveButton.minHeightProperty().bind(Bindings.divide(Main.height,16));
        toObjectiveButton.setTranslateX(2*Main.WINDOW_WIDTH - toObjectiveButton.getMinWidth() - 10);
        toObjectiveButton.getStyleClass().addAll("arrow", "toObjective");
        toObjectiveButton.setOnMouseClicked(e -> slideLeftOrRight(null));
        Main.myScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                slideLeftOrRight("Left");
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                slideLeftOrRight("Right");
            }
        });
        root.getChildren().addAll(toControlsButton, toObjectiveButton);
    }

    private static void slideLeftOrRight(String d) {
        Optional optionalDirection = Optional.ofNullable(d);
        String direction = optionalDirection.isPresent() ? (String) optionalDirection.get() : "null";

        if (!direction.equals("null")) {
            if (direction.equals("Left") && !isControlsPage)
                return;
            else if (direction.equals("Right") && isControlsPage)
                return;
        }

        if (!startedTimer) {
            startAllowContinue();
            startedTimer = true;
        }

        isControlsPage = !isControlsPage;
        TranslateTransition controlsTransition = new TranslateTransition();
        controlsTransition.setDuration(Duration.millis(1000));
        controlsTransition.setAutoReverse(false);
        controlsTransition.setNode(controlsPage);

        TranslateTransition objectiveTransition = new TranslateTransition();
        objectiveTransition.setDuration(Duration.millis(1000));
        objectiveTransition.setAutoReverse(false);
        objectiveTransition.setNode(instructions);

        TranslateTransition toObjectiveButtonTransition = new TranslateTransition();
        toObjectiveButtonTransition.setDuration(Duration.millis(1000));
        toObjectiveButtonTransition.setAutoReverse(false);
        toObjectiveButtonTransition.setNode(toObjectiveButton);

        TranslateTransition toControlsButtonTransition = new TranslateTransition();
        toControlsButtonTransition.setDuration(Duration.millis(1000));
        toControlsButtonTransition.setAutoReverse(false);
        toControlsButtonTransition.setNode(toControlsButton);

        if (isControlsPage) {
            // roo7 controls page
            controlsTransition.setFromX(controlsPage.getTranslateX());
            controlsTransition.setToX(0);
            controlsTransition.play();

            objectiveTransition.setFromX(instructions.getTranslateX());
            objectiveTransition.setToX(-Main.WINDOW_WIDTH);
            objectiveTransition.play();

            toObjectiveButtonTransition.setFromX(toObjectiveButton.getTranslateX());
            toObjectiveButtonTransition.setToX(Main.WINDOW_WIDTH - toObjectiveButton.getMinWidth() - 10);
            toObjectiveButtonTransition.play();

            toControlsButtonTransition.setFromX(toControlsButton.getTranslateX());
            toControlsButtonTransition.setToX(-Main.WINDOW_WIDTH);
            toControlsButtonTransition.play();
//                controlsPage.translateXProperty().bind(Bindings.divide(new SimpleDoubleProperty(0),1));
        } else {
            // erga3 lel objective page
            objectiveTransition.setFromX(instructions.getTranslateX());
            objectiveTransition.setToX(0);
            objectiveTransition.play();

            controlsTransition.setFromX(controlsPage.getTranslateX());
            controlsTransition.setToX(Main.WINDOW_WIDTH);
            controlsTransition.play();

            toObjectiveButtonTransition.setFromX(toObjectiveButton.getTranslateX());
            toObjectiveButtonTransition.setToX(2*Main.WINDOW_WIDTH - toObjectiveButton.getMinWidth() - 10);
            toObjectiveButtonTransition.play();

            toControlsButtonTransition.setFromX(toControlsButton.getTranslateX());
            toControlsButtonTransition.setToX(10);
            toControlsButtonTransition.play();
        }
    }
}