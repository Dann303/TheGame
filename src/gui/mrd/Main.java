package gui.mrd;

import engine.Game;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.characters.Hero;

import java.util.ArrayList;

public class Main extends Application {

    public static Stage currentStage = new Stage();
    public static HowToPlayScene howToPlayScene;
    public static Scene1 s1;
    public static Scene2 s2;
    public static Scene3 s3;
    public static GameWinScene gameWin;
    public static GameOverScene gameOver;

    public static ArrayList<Hero> allHeroes = new ArrayList<Hero>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        assignImageToHeroes();
        createAllHeroesArray();

        howToPlayScene = new HowToPlayScene();

        s1 = new Scene1();
        s2 = new Scene2();
        s3 = new Scene3();
        gameWin = new GameWinScene();
        gameOver = new GameOverScene();

        currentStage.setResizable(true);

        Image icon = new Image("gui/mrd/images/the_last_of_us_icon.jpg");

        currentStage.setTitle("The Last of MRD");
        currentStage.getIcons().add(icon);
        currentStage.setScene(s1);
        currentStage.show();

//        currentStage.setOnCloseRequest(e -> {
//            Platform.exit();
//            System.out.println("e2fel el zeft dah!");
//        });

    }

    private void assignImageToHeroes() throws Exception {
        Game.loadHeroes("src/shared files/Heros.csv");

        Game.availableHeroes.get(0).setImagePath("src/gui/mrd/images/scene3/fighters/1.jpeg");
        Game.availableHeroes.get(1).setImagePath("src/gui/mrd/images/scene3/medic/1.jpeg");
        Game.availableHeroes.get(2).setImagePath("src/gui/mrd/images/scene3/explorer/5.jpeg");
        Game.availableHeroes.get(3).setImagePath("src/gui/mrd/images/scene3/explorer/4.jpeg");
        Game.availableHeroes.get(4).setImagePath("src/gui/mrd/images/scene3/explorer/1.jpeg");
        Game.availableHeroes.get(5).setImagePath("src/gui/mrd/images/scene3/medic/5.jpeg");
        Game.availableHeroes.get(6).setImagePath("src/gui/mrd/images/scene3/fighters/2.jpeg");
        Game.availableHeroes.get(7).setImagePath("src/gui/mrd/images/scene3/medic/4.jpeg");

    }

    private void createAllHeroesArray() {
        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            allHeroes.add(Game.availableHeroes.get(i));
        }
    }

}
