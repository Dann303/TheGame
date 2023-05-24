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
    public static Hero startingHero;

    public static String typeChosen = "";
    public static int typeHovered = -1;

    // type, ability description
//    private static String[][] typeDetails = {{"Fighter", "Attacks without consuming action points for 1 turn", "Ancient warriors born among monsters of the ", "underworld, fighters' specialty lies at slicing", "their enemies into pieces over and over with no remorse."},
//            {"Medic", "Heals a hero around him including himself", "Raised among titans and beasts in the depth of", "mystical islands, medics found their way supporting", "those around them excelling as team players."},
//            {"Explorer", "Lights up the map's visibility for 1 turn", "Gifted heroes with the ability to differentiate streets", "of Maadi apart with ease. If you're ever stuck", "somewhere and feel a bit lost get yourself an explorer", "to enlighten you. They're basically Heimdall of the game."}};
    private static String[][] typeDetails = {{"Medic", "Heals a hero around him including himself", "Raised among titans and beasts in the depth of mystical islands, medics found their way supporting those around them excelling as team players."},
            {"Fighter", "Attacks without consuming action points for 1 turn", "Ancient warriors born among monsters of the underworld, fighters' specialty lies at slicing their enemies into pieces over and over with no remorse."},
            {"Explorer", "Lights up the map's visibility for 1 turn", "Gifted heroes with the ability to differentiate streets of Maadi apart with ease. If you're ever stuck somewhere and feel a bit lost get yourself an explorer to enlighten you. They're basically Heimdall of the game."}};


    public static HBox availableHeroes = new HBox();
    public static HBox typesOfHeroes;
    public static VBox middleContainer = new VBox();
    public static VBox heroPanel = new VBox();

    public Scene2() throws FileNotFoundException {
        super(root, 1200, 800, Color.rgb(34, 56, 78));

        // add link to css file
        this.getStylesheets().add(Scene2.class.getResource("styles/scene2.css").toExternalForm());

        // create a container to be put in the center of the root, inside that container han7ot awel element el heroes w el second hayeb2a el text
        middleContainer.setTranslateY(75);

        middleContainer.getChildren().addAll(getTypesOfHeroes());
        root.setCenter(middleContainer);


        // after clicking one of the 3 hero types, preview all heroes in the availableHeroes array of that type
        // show these heroes instead of the hero types inside heroPanel

        //create action listeners for hero types
        createActionListenersOfHeroTypes();

        // settings
        heroPanel.setTranslateY(75);

    }

    private static HBox getTypesOfHeroes() throws FileNotFoundException {
        // container el hero to be put inside the middleContainer inside the center of the root
        // contains the 3 classes medic, fighter and explorer

        HBox typesOfHeroes = new HBox();

        // settings
        typesOfHeroes.setSpacing(100);
        typesOfHeroes.setAlignment(Pos.TOP_CENTER);

        // add all hero types (fighter, medic, explorer) to the container of the heroe types.
        typesOfHeroes.getChildren().addAll(
                getImageViewOfCharacter("src/gui/mrd/images/scene2/ruby_the_medic.jpg"),
                getImageViewOfCharacter("src/gui/mrd/images/scene2/danny_the_fighter.jpg"),
                getImageViewOfCharacter("src/gui/mrd/images/scene2/mahmoud_the_explorer.jpg")
        );

        for (int i = 0; i < typesOfHeroes.getChildren().size(); i++){
            ImageView currentChild = (ImageView) typesOfHeroes.getChildren().get(i);
            final int finalI = i;
            currentChild.setOnMouseEntered(e -> {
                typeHovered = finalI;
                middleContainer.getChildren().add(getHeroType());
            });

            currentChild.setOnMouseExited(e -> {
                middleContainer.getChildren().remove(1);
            });
        }

        Scene2.typesOfHeroes = typesOfHeroes;
        return typesOfHeroes;
    }

    private static VBox getHeroType() {
        // create text description about hero type, 1- Hero Name, 2- Ability, 3- background-story

        // to be changed dynamically
        VBox heroType = new VBox();
        Text heroTypeName = new Text();
        Text heroTypeAbility = new Text();
        Text heroTypeDescription = new Text();

        if (typeHovered != -1) {
            heroTypeName.setText(typeDetails[typeHovered][0]);
            heroTypeAbility.setText(typeDetails[typeHovered][1]);
            heroTypeDescription.setText(typeDetails[typeHovered][2]);
        }

        // settings
        heroType.setAlignment(Pos.CENTER);
        heroType.setSpacing(15);
        heroType.getStyleClass().add("heroDescription"); // add css class
        heroType.setTranslateY(30); // translate it 30px downwards relative to its place in its parent container

        // add the 3 texts to the container
        heroType.getChildren().addAll(heroTypeName,heroTypeAbility,heroTypeDescription);

        // apply the following to all text 1- font color, 2- center it, 3- starts wrapping into new line after 800 px
        for (int i = 0; i < heroType.getChildren().size(); i++) {
            // instead of setting color here, you can add a class and use custom colors with rgb
            heroType.getChildren().get(i).getStyleClass().add("heroDescriptionText");
            ((Text)heroType.getChildren().get(i)).setFill(Color.rgb(12,21,20));
            ((Text)heroType.getChildren().get(i)).setTextAlignment(TextAlignment.CENTER);
            ((Text)heroType.getChildren().get(i)).setWrappingWidth(900);
        }

        return heroType;
    }

    private static void getHeroesOfParticularType() throws FileNotFoundException {
        // get available heroes and put them in a box just like typesOfHeroes
        // according to the typeChosen from the previous part, we're constructing that array with the corresponding heroes

        // settings
        availableHeroes.setAlignment(Pos.TOP_CENTER);

        // loop through all heroes available to pick the heroes that are of the same type as the type chosen
        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            Hero currentHero = Game.availableHeroes.get(i);

            // condition using helper method to make sure that the currentHero is the same as the typeChosen
            if (isHeroCorrectType(currentHero)) {

                // getting ImageView to add next to each other and add action events for each image
                ImageView currentChild = getImageViewOfCharacter(currentHero.getImagePath());
                availableHeroes.getChildren().add(currentChild);

                currentChild.setOnMouseEntered(e -> {
                    // when you hover over a certain image, you get that hero's properties stored and then linked to the startingHero global variable
                    String name = currentHero.getName();
                    int health = currentHero.getMaxHp();
                    int damage = currentHero.getAttackDmg();
                    int moves = currentHero.getMaxActions();

                    if (typeChosen == "Fighter")
                        startingHero = new Fighter(name, health, damage, moves);
                    else if (typeChosen == "Medic")
                        startingHero = new Medic(name, health, damage, moves);
                    else
                        startingHero = new Explorer(name, health, damage, moves);

                    // right after you store the data of the hero of the image, you show its text with the corresponding data
                    heroPanel.getChildren().add(getHeroStats());
                });

                currentChild.setOnMouseExited(e->{
                    // now if you leave the image area you remove the text with it
                    heroPanel.getChildren().remove(1);
                });

                currentChild.setOnMouseClicked(e-> {
                    // and if you click then you are sent to scene 3 with the starting hero equal to startingHero (currentHero)
                    Main.currentStage.setScene(Main.s3);

                    // fetch hero from array of availableHeroes using name
                    Hero heroToStart = null;

                    for (int j=0; j<Game.availableHeroes.size(); j++) {
                        if (Game.availableHeroes.get(j).getName() == startingHero.getName()) {
                            heroToStart = Game.availableHeroes.get(j);
                        }
                    }

                    Game.startGame(heroToStart);
                });

            }
        }

        // settings
        availableHeroes.setSpacing(90);

        heroPanel.getChildren().addAll(availableHeroes);
    }

    private static boolean isHeroCorrectType(Hero h) {
        // helper method used in previous method to decide if hero h is the same type as the typeChosen
        if (typeChosen == "Fighter")
            return h instanceof Fighter;
        else if (typeChosen == "Medic")
            return h instanceof Medic;
        else if (typeChosen == "Explorer")
            return h instanceof Explorer;
        else {
            System.out.println("Something wrong happened!");
            return false;
        }
    }

    private static VBox getHeroStats() {
        // responsible for the text area in the second part of the scene (hero details)

        VBox heroStats = new VBox();
        Text name = new Text();
        Text health = new Text();
        Text attackDmg = new Text();
        Text moves = new Text();

        // setting text for each text container
        name.setText("Name : " + startingHero.getName());
        health.setText("Health : " + startingHero.getCurrentHp());
        attackDmg.setText("Attack Damage : " + startingHero.getAttackDmg());
        moves.setText("Number of moves per turn : " + startingHero.getMaxActions() + " moves");

        // settings
        heroStats.setSpacing(20);
        heroStats.setAlignment(Pos.CENTER);
        heroStats.getStyleClass().add("heroStats"); // add css class
        heroStats.setTranslateY(50); // translate it 100px downwards relative to its place in its parent container

        // adding children to the container of type VBox (result)
        heroStats.getChildren().addAll(name, health, attackDmg, moves);

        // apply the following to all text 1- font color, 2- center it, 3- starts wrapping into new line after 800 px
        for (int i = 0; i < heroStats.getChildren().size(); i++) {
            // nafs el fekra fel color, add for each text individually a class to style their color
            heroStats.getChildren().get(i).getStyleClass().add("heroStatsText");
            ((Text)heroStats.getChildren().get(i)).setFill(Color.rgb(247, 208, 0));
            ((Text)heroStats.getChildren().get(i)).setTextAlignment(TextAlignment.CENTER);
            ((Text)heroStats.getChildren().get(i)).setWrappingWidth(900);
        }

        return heroStats;
    }

    private static void createActionListenersOfHeroTypes() {
        // when you click on one of the three types (medic, fighter, explorer) you begin to construct the second part (the heroes to display) according to what you pressed

        typesOfHeroes.getChildren().get(0).setOnMouseClicked(e -> {
            typeChosen = "Medic";

            try {
                getHeroesOfParticularType();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
        });

        typesOfHeroes.getChildren().get(1).setOnMouseClicked(e -> {
            typeChosen = "Fighter";

            try {
                getHeroesOfParticularType();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
        });

        typesOfHeroes.getChildren().get(2).setOnMouseClicked(e -> {
            typeChosen = "Explorer";

            try {
                getHeroesOfParticularType();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            root.setCenter(heroPanel);
        });

    }

    private static ImageView getImageViewOfCharacter(String path) throws FileNotFoundException {
        // helper method (might not longer be needed cuz we use the css file way now to do images and backgrounds etc.)
        // creates an ImageView of the given path as string and returns it
        FileInputStream file = new FileInputStream(path);
        Image image = new Image(file);
        ImageView view = new ImageView(image);
        view.setFitHeight(300);
        view.setFitWidth(180);
        return view;
    }

}
