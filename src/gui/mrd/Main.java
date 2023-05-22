package gui.mrd;

import engine.Game;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage currentStage = new Stage();
    public static Scene1 s1;
    public static Scene2 s2;
    public static Scene3 s3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        assignImageToHeroes();

        s1 = new Scene1();
        s2 = new Scene2();
        s3 = new Scene3();

        currentStage.setResizable(true);

        // Timer timer = new Timer();
        // timer.

        Image icon = new Image("gui/mrd/images/the_last_of_us_icon.jpg");

        currentStage.setTitle("The Last of MRD");
        currentStage.getIcons().add(icon);
        currentStage.setScene(s1);
        currentStage.show();
    }

    private void assignImageToHeroes() throws Exception {
        Game.loadHeroes("src/shared files/Heros.csv");

        Game.availableHeroes.get(0).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(1).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(2).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(3).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(4).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(5).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(6).setImagePath("src/gui/mrd/images/medic_1.jpg");
        Game.availableHeroes.get(7).setImagePath("src/gui/mrd/images/medic_1.jpg");

    }

}
