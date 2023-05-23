package gui.mrd;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Scene3 extends Scene {
    public static StackPane root = new StackPane();    // root howwa el asas, han7ot feeh el pain borderPane bas also we can now put over it el exception absolutely! (absolutely ya3ny using any coords we like)
    public static BorderPane theGameLayout = new BorderPane(); // el beta3a el shayla kol 7aga
    public static GridPane grid = new GridPane(); // container of the grid

    // Figure a way out to be able to store currentHero when you click on a hero
    // and also figure a way for healing to work, youll click on the hero to use (medic) then youll pick the other hero to heal (possibly same hero)
    // in a way lazem tefarra2
    // maybe hate3melha enak lama te3mel el superAbility fel medic hayprompt you to click on someone to heal <<==============
    public static Character currentHero;
    public static Character currentTarget;

    public static VBox middleContainer = new VBox(); // contains top, middle and bottom containers

    public static HBox topPane = new HBox();
    public static HBox bottomPane = new HBox();
    public static VBox leftSideBar = new VBox();
    public static VBox rightSideBar = new VBox();

    public Scene3(){
        super(root, 1200, 800, Color.rgb(34,56,78));

        // css link
        this.getStylesheets().add(Scene3.class.getResource("styles/scene3.css").toExternalForm()); // link to css file

        // take care of all parent-child relations ya3ny middle container gowah top w middle w bottom, etc.
        parentChildRelations();

        // setup the grid elements
        setGridElements();

        // setup each of the containers settings
        setContainerSettings();

        // TO BE CREATED, TOP, LEFT, RIGHT SIDEBARS ... and finish the bottom one bardo
        // setup the bottom pane and its children
        setTopPane();
        setLeftSideBar();
        setBottomPane();
        setRightSideBar();

        //bengarab ne3mel setAlertBoxContainer (remove by using index 1)
//        setAlertBoxContainer("Hey, you can't go there!");

    }

    private void parentChildRelations() {
        // fel awel ben add el beta3a nafsaha (no alerts yet)
        root.getChildren().add(theGameLayout);

        // link el middle container bel 3 containers el feeh
        middleContainer.getChildren().addAll(topPane, grid, bottomPane); // make middle container the parent of top, grid and bottom contaienrs

        // add the containers to the main root parent container
        theGameLayout.setLeft(leftSideBar);
        theGameLayout.setCenter(middleContainer);
        theGameLayout.setRight(rightSideBar);

    }

    private static void setGridElements() {
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                // create the element to be added (eg: label)
                // for now tub3an dah hayet8ayar lamma neegy ne7ot makano background icon maslan
                // and to link it with some character we'll use el cells in Game.map
                // basically we're gonna add a property to Cell class which is the iconPath that will hold path to some icon to be displayed

                Label label = new Label("X");

                // set that element's settings
                label.setAlignment(Pos.CENTER);
                label.setMinWidth(40);
                label.setMinHeight(40);
                label.getStyleClass().add("cell");

                // add it to the grid and set its coordinates
                grid.getChildren().add(label);
                // constraints dy basically specifies the coordinates of the node that was added to the grid
                grid.setConstraints(label,i,j);
            }
        }
    }

    private void setContainerSettings() {
        middleContainer.setAlignment(Pos.CENTER);
        middleContainer.getStyleClass().add("middleContainer");

        grid.setAlignment(Pos.CENTER);
        grid.setMaxWidth(0);
        grid.setMaxHeight(0);
        grid.getStyleClass().add("grid");

        topPane.setAlignment(Pos.CENTER);
        topPane.setMinHeight(80);
        topPane.getStyleClass().add("topPane");

        rightSideBar.setAlignment(Pos.CENTER);
        rightSideBar.setMinWidth(200);
        rightSideBar.setPadding(new Insets(0, 25, 0, 25));
        rightSideBar.getStyleClass().add("rightSideBar");

        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setMinHeight(80);
        bottomPane.setSpacing(80);
        bottomPane.setPadding(new Insets(10, 0, 10, 0));
        bottomPane.getStyleClass().add("bottomPane");

        leftSideBar.setAlignment(Pos.CENTER);
        leftSideBar.setMinWidth(200);
        leftSideBar.setPadding(new Insets(0, 25, 0, 25));
        leftSideBar.getStyleClass().add("leftSideBar");

    }

    private GridPane getDirectionsButtons() {
        // gets us the 4 move buttons we have in the bottomPane

        GridPane moveKeysButtons = new GridPane();

        // create 4 buttons and add them to a container moveKeysButtons
        Button up = new Button();
        Button down = new Button();
        Button left = new Button();
        Button right = new Button();

        // add them to the small grid container moveKeysButtons
        moveKeysButtons.getChildren().addAll(up, down, left, right);

        // add to each one a seperate class to style their backgrounds
        up.getStyleClass().addAll("key-button", "up-key");
        down.getStyleClass().addAll("key-button", "down-key");
        left.getStyleClass().addAll("key-button", "left-key");
        right.getStyleClass().addAll("key-button", "right-key");

        // set the coordinates of each in their container
        moveKeysButtons.setConstraints(up, 1,0);
        moveKeysButtons.setConstraints(down, 1,2);
        moveKeysButtons.setConstraints(left, 0,1);
        moveKeysButtons.setConstraints(right, 2,1);

        // some setting
        moveKeysButtons.setHgap(3);
        moveKeysButtons.setVgap(3);

        return moveKeysButtons;
    }

    private void setBottomPane() {
        // create the 4 buttons to be added to the bottom pane
        Button attackButton = new Button("Attack");
        Button specialAbilityButton = new Button("<Special Ability>"); // button text will be set according to currentHero instance of what
        Button cureButton = new Button("Cure");
        GridPane moveKeysButtons = getDirectionsButtons(); // the 4 movement button keys

        // for each one add the class bottomPaneButton
        attackButton.getStyleClass().add("bottomPaneButton");
        specialAbilityButton.getStyleClass().add("bottomPaneButton");
        cureButton.getStyleClass().add("bottomPaneButton");
        moveKeysButtons.getStyleClass().add("bottomPaneButton");

        // FOR LATER **** thanks to mahmoud for the idea, instead of text for the buttons, we will use small images to cover the whole button area
        // we will do this by giving each button a unique class
        // and in this class add an image that refers to what the button does

        // add them to the bottomPane
        bottomPane.getChildren().addAll(attackButton,specialAbilityButton,cureButton,moveKeysButtons);
    }


    private void setRightSideBar() {
        // basically the right side bar contains data of the target
        // note: we didn't set the vbox that we create directly to the right of the borderpane rather 7atenah gowa el vbox that already exists in the right of the border pane
        // that is because we wanna specify en what we setRight fel borderPane hay take some area mo3ayana and the target container nafso hayeb2a gowa this area so that it wont
        // stick to the other areas around

        VBox targetContainer = new VBox();

        // whole sidebar panel settings
        targetContainer.setMinWidth(204);
        targetContainer.setMaxWidth(204);
        targetContainer.setMinHeight(700);
        targetContainer.getStyleClass().add("targetContainer");

        // image settings
        Label targetImage = new Label();
        targetImage.setMinWidth(200);
        targetImage.setMinHeight(300);
        targetImage.getStyleClass().add("targetImage");

        // text to be added as details **** TO BE CHANGED TO BE DYNAMIC (according to currentTarget)
        Text targetName = new Text("Name : Danny 7ayawan");
        Text targetType = new Text("Fighter");
        Text health = new Text("Health : 50/70 HP");
        Text attackDamage = new Text("Attack Damage : 4 Points");
        Text remainingActionPoints = new Text("Moves left : 3");
        Text supplies = new Text("Supplies in inventory : 2");
        Text vaccines = new Text("Vaccines in inventory : 1");

        // ba2eit el text that will be added as details and their settings
        VBox targetText = new VBox();
        targetText.getStyleClass().add("targetText");
        targetText.setAlignment(Pos.CENTER);
        targetText.setSpacing(20);
        targetText.setPadding(new Insets(50, 0, 0, 0));
        targetText.getChildren().addAll(targetName, targetType, health, attackDamage, remainingActionPoints, supplies, vaccines);

        // add el targetImage wel text details to el container that we then add to the rightSideBar (not replacing it ha)
        targetContainer.getChildren().addAll(targetImage, targetText);
        rightSideBar.getChildren().add(targetContainer);

    }


    private void setLeftSideBar() {
        // nafs fekret el right sidebar, besra7a mesh ader akteb tany fa see el notes el maktooba fel mathod beta3et setRightSideBar

        VBox heroContainer = new VBox();

        // container settings
        heroContainer.setMinWidth(204);
        heroContainer.setMaxWidth(204);
        heroContainer.setMinHeight(700);
        heroContainer.getStyleClass().add("heroContainer");

        // image settings
        Label heroImage = new Label();
        heroImage.setMinWidth(200);
        heroImage.setMinHeight(300);
        heroImage.getStyleClass().add("heroImage");

        // text details
        Text heroName = new Text("Name : Rubina Gamadan");
        Text heroType = new Text("Medic");
        Text health = new Text("Health : 50/50 HP");
        Text attackDamage = new Text("Attack Damage : 4 Points");
        Text remainingActionPoints = new Text("Moves left : 3");
        Text supplies = new Text("Supplies in inventory : 2");
        Text vaccines = new Text("Vaccines in inventory : 1");

        // text details settings
        VBox heroText = new VBox();
        heroText.getStyleClass().add("heroText");
        heroText.setAlignment(Pos.CENTER);
        heroText.setSpacing(20);
        heroText.setPadding(new Insets(50, 0, 0, 0));
        heroText.getChildren().addAll(heroName, heroType, health, attackDamage, remainingActionPoints, supplies, vaccines);

        // add all to container and then add that container to the leftSideBar
        heroContainer.getChildren().addAll(heroImage, heroText);
        leftSideBar.getChildren().addAll(heroContainer);
    }


    private void setTopPane() {
        // nafs fekret el left wel rightsidebars
        HBox topPanelContainer = new HBox();

        // container settings
        topPanelContainer.getStyleClass().add("topPanelContainer");
        topPanelContainer.setMinWidth(700);
        topPanelContainer.setSpacing(100);
        topPanelContainer.setAlignment(Pos.CENTER);

        // element 1 and settings
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setMinHeight(40);
        endTurnButton.setMinWidth(100);

        // element 2 and settings **** EB2A KHALIH TIMER BEGAD
        Label timer = new Label("Time: 01:39");
        timer.setMinHeight(40);
        timer.setMinWidth(100);

        // element 3 and settings **** INCREMENTS WITH GAME.ENDTURN()
        Label roundCounter = new Label("Round 1");
        roundCounter.setMinHeight(40);
        roundCounter.setMinWidth(100);

        // add kolo lel container then add container to topPane
        topPanelContainer.getChildren().addAll(roundCounter, timer, endTurnButton);
        topPane.getChildren().add(topPanelContainer);
    }

    public static void setAlertBoxContainer(String message) { //TO DO : E3MEL TEXT WRAP
        // create alert box
        HBox alertBox = new HBox();

        // the close button inside the alert box to force close it (remove it from the root.getChildren().get(1))
        Button xButton = new Button();
        xButton.getStyleClass().add("alertXButton");
        xButton.setMinWidth(20);
        xButton.setMaxWidth(20);
        xButton.setMinHeight(20);
        xButton.setMaxHeight(20);

        // add xButton actionlistener here (pressed yeb2a remove it from the root

        // create the message to be added aw inserted as error
        Label labelMessage = new Label(message);
        labelMessage.setAlignment(Pos.CENTER);

        // settings
        alertBox.setMinWidth(300);
        alertBox.setMaxWidth(300);
        alertBox.setMinHeight(100);
        alertBox.setMaxHeight(100);
        alertBox.setAlignment(Pos.CENTER);
        alertBox.setSpacing(15);
        alertBox.getStyleClass().add("alertBox");

        // coordinates
        alertBox.setTranslateX(-200);
        alertBox.setTranslateY(-280);

        // add kollo lel alertBox w add el alertBox lel root (sheelo bel action listener or be timeout) easy ya3ny isa
        alertBox.getChildren().addAll(labelMessage, xButton);
        root.getChildren().add(alertBox);
    }
}
