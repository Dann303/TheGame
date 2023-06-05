package views;

import engine.Game;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.characters.Hero;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main extends Application {

    public static double WINDOW_WIDTH = 1200;
    public static double WINDOW_HEIGHT = 800;

    public static DoubleProperty width = new SimpleDoubleProperty(1200);
    public static DoubleProperty height = new SimpleDoubleProperty(800);

    public static Stage currentStage = new Stage();
    public static Scene myScene = new Scene(new BorderPane(), WINDOW_WIDTH, WINDOW_HEIGHT);
    public static HowToPlayScene howToPlayScene;
    public static Scene1 s1;
    public static Scene2 s2;
    public static Scene3 s3;
    public static GameWinScene gameWin;
    public static GameOverScene gameOver;

    public static ArrayList<Hero> allHeroes = new ArrayList<Hero>();

    public static ChangeListener<Number> changeListenerWidth = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
            WINDOW_WIDTH = newWidth.doubleValue();
        }
    };

    public static ChangeListener<Number> changeListenerHeight = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
            WINDOW_HEIGHT = newHeight.doubleValue();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        myScene.getStylesheets().addAll(
                Scene1.class.getResource("styles/scene1.css").toExternalForm(),
                HowToPlayScene.class.getResource("styles/howToPlay.css").toExternalForm(),
                Scene2.class.getResource("styles/scene2.css").toExternalForm(),
                Scene3.class.getResource("styles/scene3.css").toExternalForm(),
                GameOverScene.class.getResource("styles/gameOverScene.css").toExternalForm(),
                GameWinScene.class.getResource("styles/gameWinScene.css").toExternalForm()
        );

        assignImageToHeroes();
        createAllHeroesArray();

//        howToPlayScene = new HowToPlayScene();

        s1 = new Scene1();
//        s2 = new Scene2();
//        s3 = new Scene3();
        gameWin = new GameWinScene();
        gameOver = new GameOverScene();

        currentStage.setResizable(true);

        Image icon = new Image("views/images/the_last_of_us_icon.jpg");

        currentStage.setTitle("The Last of MRD");
        currentStage.getIcons().add(icon);
        setUpSceneWindowResizeDetector(null, s1); //should be s1
        currentStage.setScene(myScene); //should be s1
        currentStage.getScene().setRoot(Scene1.root);

        currentStage.setFullScreenExitHint("Press F11 to toggle fullscreen!");
        currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        currentStage.setFullScreen(true);

        currentStage.show();


        WINDOW_WIDTH = myScene.widthProperty().getValue();
        WINDOW_HEIGHT = myScene.heightProperty().getValue();

        height.bind(myScene.heightProperty());
        width.bind(myScene.widthProperty());

        myScene.addEventFilter(KeyEvent.KEY_PRESSED, keyListener);
    }

    private EventHandler<KeyEvent> keyListener = new javafx.event.EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.F11) {
                currentStage.setFullScreen(!currentStage.isFullScreen());
                WINDOW_WIDTH = myScene.widthProperty().getValue();
                WINDOW_HEIGHT = myScene.heightProperty().getValue();
                height.bind(myScene.heightProperty());
                width.bind(myScene.widthProperty());
            }
        }
    };

            private void assignImageToHeroes() throws Exception {
        Game.loadHeroes("src/shared files/Heros.csv");

        Game.availableHeroes.get(0).setImagePath("src/views/images/scene3/fighters/1.jpeg");
        Game.availableHeroes.get(1).setImagePath("src/views/images/scene3/medic/1.jpeg");
        Game.availableHeroes.get(2).setImagePath("src/views/images/scene3/explorer/5.jpeg");
        Game.availableHeroes.get(3).setImagePath("src/views/images/scene3/explorer/4.jpeg");
        Game.availableHeroes.get(4).setImagePath("src/views/images/scene3/explorer/1.jpeg");
        Game.availableHeroes.get(5).setImagePath("src/views/images/scene3/medic/5.jpeg");
        Game.availableHeroes.get(6).setImagePath("src/views/images/scene3/fighters/2.jpeg");
        Game.availableHeroes.get(7).setImagePath("src/views/images/scene3/medic/4.jpeg");

    }

    private void createAllHeroesArray() {
        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            allHeroes.add(Game.availableHeroes.get(i));
        }
    }

    public static void setUpSceneWindowResizeDetector(Scene oldScene, Scene newScene) {
        if (oldScene != null) {
            oldScene.widthProperty().removeListener(changeListenerWidth);
            oldScene.heightProperty().removeListener(changeListenerHeight);
        }
        if (newScene != null) {
            newScene.widthProperty().addListener(changeListenerWidth);
            newScene.heightProperty().addListener(changeListenerHeight);
        }
    }

    public static void clearListeners() {
        myScene.setOnKeyPressed(null);
        myScene.setOnMouseClicked(null);
    }
}
