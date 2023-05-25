package gui.mrd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Scene1 extends Scene {
    public static BorderPane root = new BorderPane();

    public Scene1() throws FileNotFoundException {
        super(root, 1200, 800, Color.rgb(34, 56, 78));

        // get text to be displayed and add its settings
        Text text = getText();

        // add its action listener
        text.setOnMouseClicked(e -> {
            Main.currentStage.setScene(Main.howToPlayScene);
        });

        root.setCenter(text);

        this.getStylesheets().add(Scene1.class.getResource("styles/scene1.css").toExternalForm());
    }

    private static Text getText() throws FileNotFoundException {
        Text text = new Text();
        text.setText("Start the Game");
        text.setFont(Font.loadFont(new FileInputStream(new File("src/resources/fonts/DemonSker-zyzD.ttf")), 80));
        text.setFill(Color.rgb(139, 00, 00));
        text.setOpacity(0.85);
        text.setTranslateY(115);
        text.setTranslateX(-15);
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setSpread(50);
        text.setEffect(shadow);

        return text;
    }
}
