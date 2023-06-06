package views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class Scene1 extends Scene {
    public static BorderPane root = new BorderPane();
    private static Text text = new Text();

    public Scene1() throws FileNotFoundException {
        super(new BorderPane());
//        super(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34, 56, 78));

        this.getStylesheets().add(Scene1.class.getResource("styles/scene1.css").toExternalForm());

        root.getStyleClass().add("root1");
        root.minWidthProperty().bind(Main.width);
        root.minHeightProperty().bind(Main.height);

        // get text to be displayed and add its settings
        setText();

        // add its action listener
        text.setOnMouseClicked(e -> {
            Main.howToPlayScene = new HowToPlayScene();
            Main.setUpSceneWindowResizeDetector(Main.s1, null);
            boolean wasFullScreen = Main.currentStage.isFullScreen();
            Main.myScene.setRoot(HowToPlayScene.root);
//            Main.currentStage.setScene(Main.howToPlayScene);
            if (wasFullScreen)
                Main.currentStage.setFullScreen(true);
//            Main.howToPlayScene.setRoot(new BorderPane());
//            Main.currentStage.getScene().setRoot(Main.howToPlayScene.root);
            Main.setUpSceneWindowResizeDetector(null, Main.howToPlayScene);
//            Main.howToPlayScene.startAllowContinue();
        });

        Main.myScene.setOnKeyPressed(e -> {
            Main.howToPlayScene = new HowToPlayScene();
            Main.setUpSceneWindowResizeDetector(Main.s1, null);
            boolean wasFullScreen = Main.currentStage.isFullScreen();
            Main.myScene.setRoot(HowToPlayScene.root);
//            Main.currentStage.setScene(Main.howToPlayScene);
            if (wasFullScreen)
                Main.currentStage.setFullScreen(true);
//            Main.howToPlayScene.setRoot(new BorderPane());
//            Main.currentStage.getScene().setRoot(Main.howToPlayScene.root);
            Main.setUpSceneWindowResizeDetector(null, Main.howToPlayScene);
//            Main.howToPlayScene.startAllowContinue();
        });

        root.setCenter(text);

        setWindowResizeableListener();

    }

    private static void setText() throws FileNotFoundException {
        text.setText("Start the Game");
        text.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
        text.setFill(Color.rgb(139, 00, 00));
        text.setOpacity(0.85);
        text.setTranslateY(Main.WINDOW_HEIGHT/10.435);
        text.setTranslateY(400);
        text.setTranslateX(Main.WINDOW_WIDTH/80);
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setSpread(50);
        text.setEffect(shadow);
    }

    private void setWindowResizeableListener() {
        Main.height.bind(this.heightProperty());
        Main.width.bind(this.widthProperty());
        text.translateYProperty().bind(Bindings.divide(Main.height,10.435));
        text.translateXProperty().bind(Bindings.divide(Main.width,80));

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                Main.WINDOW_WIDTH = Main.myScene.getWidth();
                Main.WINDOW_HEIGHT = Main.myScene.getHeight();

                try {
                    text.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), Main.WINDOW_WIDTH/15));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                text.setTranslateY(Main.WINDOW_WIDTH/10.435);
//                text.setTranslateX(Main.WINDOW_WIDTH/80);

            }
        };

        Main.myScene.widthProperty().addListener(changeListener);
        Main.myScene.heightProperty().addListener(changeListener);
    }

}
