package gui.mrd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import engine.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

public class Scene2 extends Scene {

    public static BorderPane root = new BorderPane();
    public static Button temp = new Button("Play");
    public static Hero currentHero;

    public static String typeChosen = "";

    public static HBox availableHeroes = new HBox();
    public static HBox typesOfHeroes;
    public static VBox heroPanel = new VBox();

    public Scene2() throws FileNotFoundException {
        super(root, 1200, 800, Color.rgb(34, 56, 78));

        // add link to css file
        this.getStylesheets().add(Scene2.class.getResource("styles/scene2.css").toExternalForm());

        // create a container to be put in the center of the root, inside that container han7ot awel element el heroes w el second hayeb2a el text
        VBox middleContainer = new VBox();
        middleContainer.setTranslateY(75);

        middleContainer.getChildren().addAll(getTypesOfHeroes(),getHeroType());
        root.setCenter(middleContainer);


        // after clicking one of the 3 hero types, preview all heroes in the availableHeroes array of that type
        // show these heroes instead of the hero types
        heroPanel.setTranslateY(75);
//        heroPanel.getChildren().addAll(getHeroes(), getHeroStats());
//        heroes.setAlignment(Pos.CENTER);

        //create action listeners
        createActionListeners();

        root.setBottom(temp);
        temp.setOnMouseClicked(e-> {
            System.out.println("bawsal");
            Main.currentStage.setScene(Main.s3);
        });
//        root.setCenter(typesOfHeroes);
//        root.setBottom(heroDescription);

    }

    private static HBox getTypesOfHeroes() throws FileNotFoundException {
        // container el hero to be put inside the middleContainer inside the center of the root
        HBox typesOfHeroes = new HBox();
        typesOfHeroes.setSpacing(100);
        typesOfHeroes.setAlignment(Pos.TOP_CENTER);

        // add all hero types (fighter, medic, explorer) to the container of the heroe types.
        typesOfHeroes.getChildren().addAll(
                getImageViewOfCharacter("src/gui/mrd/images/ruby_the_medic_edited.jpg"),
                getImageViewOfCharacter("src/gui/mrd/images/danny_the_fighter_edited.jpg"),
                getImageViewOfCharacter("src/gui/mrd/images/mahmoud_explorer_edited.jpg")
        );

        Scene2.typesOfHeroes = typesOfHeroes;
        return typesOfHeroes;
    }

    private static VBox getHeroType() {
        // create text description about hero type, 1- Hero Name, 2- Ability, 3- background-story

        VBox heroType = new VBox();
        Text heroTypeName = new Text("Medic");
        Text heroTypeAbility = new Text("Heals other heroes including himself");
        Text heroTypeDescription = new Text("Raised among titans and warriors, medics found their way supporting those around them excelling as team players.");

        heroType.setAlignment(Pos.CENTER);
        heroType.getStyleClass().add("hero-description"); // add css class
        heroType.setTranslateY(100); // translate it 100px downwards relative to its place in its parent container

        // add the 3 text to the container
        heroType.getChildren().addAll(heroTypeName,heroTypeAbility,heroTypeDescription);

        // apply the following to all text 1- font color, 2- center it, 3- starts wrapping into new line after 800 px
        for (int i = 0; i < heroType.getChildren().size(); i++) {
            ((Text)heroType.getChildren().get(i)).setFill(Color.WHITE);
            ((Text)heroType.getChildren().get(i)).setTextAlignment(TextAlignment.CENTER);
            ((Text)heroType.getChildren().get(i)).setWrappingWidth(800);
        }

        return heroType;
    }

    private static void getHeroes() throws FileNotFoundException {
        // get available heroes and put them in a box just like typesOfHeroes
        availableHeroes.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            Hero currentHeroFromHeroes = Game.availableHeroes.get(i);
            if (isHeroCorrectType(currentHeroFromHeroes)) {
                ImageView currentChild = getImageViewOfCharacter(currentHeroFromHeroes.getImagePath());
                availableHeroes.getChildren().add(currentChild);

                currentChild.setOnMouseEntered(e -> {
                    String name = currentHeroFromHeroes.getName();
                    int health = currentHeroFromHeroes.getMaxHp();
                    int damage = currentHeroFromHeroes.getAttackDmg();
                    int moves = currentHeroFromHeroes.getMaxActions();

                    if (typeChosen == "Fighter")
                        currentHero = new Fighter(name, health, damage, moves);
                    else if (typeChosen == "Medic")
                        currentHero = new Medic(name, health, damage, moves);
                    else
                        currentHero = new Explorer(name, health, damage, moves);

                    System.out.println(currentHero.getName());
                    System.out.println(currentHero.getMaxHp());
                    System.out.println(currentHero.getAttackDmg());
                    System.out.println(currentHero.getMaxActions());

                    heroPanel.getChildren().add(getHeroStats());
                });

                currentChild.setOnMouseExited(e->{
                    heroPanel.getChildren().remove(1);
                });

                currentChild.setOnMouseClicked(e-> {
                    Main.currentStage.setScene(Main.s3);
                });

            }
        }

        availableHeroes.setSpacing(90);

        heroPanel.getChildren().addAll(availableHeroes);
    }

    private static boolean isHeroCorrectType(Hero h) {
        if (typeChosen == "Fighter")
            return h instanceof Fighter;
        else if (typeChosen == "Medic")
            return h instanceof Medic;
        else
            return h instanceof Explorer;
    }

    private static VBox getHeroStats() {
        VBox heroStats = new VBox();
        Text name = new Text();
        Text health = new Text();
        Text attackDmg = new Text();
        Text moves = new Text();


        name.setText("Name : " + currentHero.getName());
        health.setText("Health : " + currentHero.getCurrentHp());
        attackDmg.setText("Attack Damage : " + currentHero.getAttackDmg());
        moves.setText("Number of moves per turn : " + currentHero.getMaxActions() + " moves");

        heroStats.setSpacing(20);
        heroStats.getChildren().addAll(name, health, attackDmg, moves);

        heroStats.setAlignment(Pos.CENTER);
        heroStats.getStyleClass().add("hero-stats"); // add css class
        heroStats.setTranslateY(50); // translate it 100px downwards relative to its place in its parent container

        // apply the following to all text 1- font color, 2- center it, 3- starts wrapping into new line after 800 px
        for (int i = 0; i < heroStats.getChildren().size(); i++) {
            ((Text)heroStats.getChildren().get(i)).setFill(Color.RED);
            ((Text)heroStats.getChildren().get(i)).setTextAlignment(TextAlignment.CENTER);
            ((Text)heroStats.getChildren().get(i)).setWrappingWidth(800);
        }

        return heroStats;
    }

    private static void createActionListeners() {
        typesOfHeroes.getChildren().get(0).setOnMouseClicked(e -> {
            typeChosen = "Medic";

            try {
                getHeroes();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
            temp.toFront();
        });

        typesOfHeroes.getChildren().get(1).setOnMouseClicked(e -> {
            typeChosen = "Fighter";

            try {
                getHeroes();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
            temp.toFront();
        });

        typesOfHeroes.getChildren().get(2).setOnMouseClicked(e -> {
            typeChosen = "Explorer";

            try {
                getHeroes();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
            temp.toFront();
        });

    }

    private static ImageView getImageViewOfCharacter(String path) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(path);
        Image image = new Image(file);
        ImageView view = new ImageView(image);
        view.setFitHeight(300);
        view.setFitWidth(150);
        return view;
    }

}
