package views;

import engine.Game;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

        assignImageToHeroes();
        createAllHeroesArray();

//        howToPlayScene = new HowToPlayScene();

        s1 = new Scene1();
        s2 = new Scene2();
//        s3 = new Scene3();
        gameWin = new GameWinScene();
        gameOver = new GameOverScene();

        currentStage.setResizable(true);

        Image icon = new Image("views/images/the_last_of_us_icon.jpg");

        currentStage.setTitle("The Last of MRD");
        currentStage.getIcons().add(icon);
        setUpSceneWindowResizeDetector(null, s2); //should be s1
        currentStage.setScene(s2); //should be s1
        currentStage.show();
//        currentStage.setFullScreen(true);
//        currentStage.widthProperty().addListener((obs, oldVal, newVal) -> {
//            WINDOW_WIDTH = currentStage.getScene().getWidth();
//            WINDOW_HEIGHT = currentStage.getScene().getHeight();
//        });

//        currentStage.setOnCloseRequest(e -> {
//            Platform.exit();
//            System.out.println("e2fel el zeft dah!");
//        });

    }

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

}
