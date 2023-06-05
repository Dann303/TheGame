package views;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

public class GameOverScene extends Scene {

    public static StackPane root = new StackPane();
    private Timer timer = new Timer();


    public GameOverScene() throws FileNotFoundException {
//        super(root, 1200, 800, Color.rgb(34, 56, 78));
        super(new BorderPane(), 1200, 800, Color.rgb(34, 56, 78));
        this.getStylesheets().add(GameOverScene.class.getResource("styles/gameOverScene.css").toExternalForm());

        Main.clearListeners();

        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        root.getStyleClass().add("root6");

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
        Main.myScene.setOnMouseClicked(e -> {
            Main.currentStage.close();
        });

        // dost ay zorar close the window bas e3melhom be time delay 3shan maye2felsh bel 8alat awel mal screen tigy
        Main.myScene.setOnKeyPressed(e -> {
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
}
