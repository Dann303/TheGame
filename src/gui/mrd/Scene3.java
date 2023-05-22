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
    public static StackPane root = new StackPane();
    public static BorderPane theGameLayout = new BorderPane();
    public static GridPane grid = new GridPane();

    public static Character currentHero;
    public static Character currentTarget;

    public static VBox middleContainer = new VBox();

    public static HBox topPane = new HBox();
    public static HBox bottomPane = new HBox();
    public static VBox leftSideBar = new VBox();
    public static VBox rightSideBar = new VBox();

    public Scene3(){
        super(root, 1200, 800, Color.rgb(34,56,78));

        root.getChildren().addAll(theGameLayout);

        middleContainer.getChildren().addAll(topPane, grid, bottomPane); // make middle container the parent of top, grid and bottom contaienrs

        this.getStylesheets().add(Scene3.class.getResource("styles/scene3.css").toExternalForm()); // link to css file

        // add the containers to the main root parent container
        theGameLayout.setLeft(leftSideBar);
        theGameLayout.setCenter(middleContainer);
        theGameLayout.setRight(rightSideBar);

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

        setAlertBoxContainer("Hey, you can't go there!");

    }

    private static void setGridElements() {
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                // create the element to be added (eg: label)
                Label label = new Label("X");

                // set that element's settings
                label.setAlignment(Pos.CENTER);
                label.setMinWidth(40);
                label.setMinHeight(40);
                label.getStyleClass().add("cell");

                //add it to the grid and set its coordinates
                grid.getChildren().add(label);
                grid.setConstraints(label,i,j);
            }
        }
    }

    private void setContainerSettings() {
        middleContainer.setAlignment(Pos.CENTER);
        middleContainer.getStyleClass().add("middleContainer");

//        grid.setGridLinesVisible(true);
//        grid.setPadding(new Insets(10,10,10,10));


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
        GridPane moveKeysButtons = new GridPane();

        // create 4 buttons and add them to a container moveKeysButtons
        Button up = new Button();
        Button down = new Button();
        Button left = new Button();
        Button right = new Button();
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
        Button specialAbilityButton = new Button("<Special Ability>");
        Button cureButton = new Button("Cure");
        GridPane moveKeysButtons = getDirectionsButtons();

        // for each one add the class bottomPaneButton
        attackButton.getStyleClass().add("bottomPaneButton");
        specialAbilityButton.getStyleClass().add("bottomPaneButton");
        cureButton.getStyleClass().add("bottomPaneButton");
        moveKeysButtons.getStyleClass().add("bottomPaneButton");

        // add them to the bottomPane
        bottomPane.getChildren().addAll(attackButton,specialAbilityButton,cureButton,moveKeysButtons);
    }


    private void setRightSideBar() {
        VBox targetContainer = new VBox();

        targetContainer.setMinWidth(204);
        targetContainer.setMaxWidth(204);
        targetContainer.setMinHeight(700);
        targetContainer.getStyleClass().add("targetContainer");

        Label targetImage = new Label();
        targetImage.setMinWidth(200);
        targetImage.setMinHeight(300);
        targetImage.getStyleClass().add("targetImage");

        Text targetName = new Text("Name : Danny 7ayawan");
        Text targetType = new Text("Fighter");
        Text health = new Text("Health : 50/70 HP");
        Text attackDamage = new Text("Attack Damage : 4 Points");
        Text remainingActionPoints = new Text("Moves left : 3");
        Text supplies = new Text("Supplies in inventory : 2");
        Text vaccines = new Text("Vaccines in inventory : 1");

        VBox targetText = new VBox();
        targetText.getStyleClass().add("targetText");
        targetText.setAlignment(Pos.CENTER);
        targetText.setSpacing(20);
        targetText.setPadding(new Insets(50, 0, 0, 0));
        targetText.getChildren().addAll(targetName, targetType, health, attackDamage, remainingActionPoints, supplies, vaccines);


        targetContainer.getChildren().addAll(targetImage, targetText);
        rightSideBar.getChildren().addAll(targetContainer);

    }


    private void setLeftSideBar() {
        VBox heroContainer = new VBox();

        heroContainer.setMinWidth(204);
        heroContainer.setMaxWidth(204);
        heroContainer.setMinHeight(700);
        heroContainer.getStyleClass().add("heroContainer");

        Label heroImage = new Label();
        heroImage.setMinWidth(200);
        heroImage.setMinHeight(300);
        heroImage.getStyleClass().add("heroImage");

        Text heroName = new Text("Name : Rubina Gamadan");
        Text heroType = new Text("Medic");
        Text health = new Text("Health : 50/50 HP");
        Text attackDamage = new Text("Attack Damage : 4 Points");
        Text remainingActionPoints = new Text("Moves left : 3");
        Text supplies = new Text("Supplies in inventory : 2");
        Text vaccines = new Text("Vaccines in inventory : 1");

        VBox heroText = new VBox();
        heroText.getStyleClass().add("heroText");
        heroText.setAlignment(Pos.CENTER);
        heroText.setSpacing(20);
        heroText.setPadding(new Insets(50, 0, 0, 0));
        heroText.getChildren().addAll(heroName, heroType, health, attackDamage, remainingActionPoints, supplies, vaccines);


        heroContainer.getChildren().addAll(heroImage, heroText);
        leftSideBar.getChildren().addAll(heroContainer);
    }


    private void setTopPane() {
        HBox topPanelContainer = new HBox();
        topPanelContainer.getStyleClass().add("topPanelContainer");
        topPanelContainer.setMinWidth(700);
        topPanelContainer.setSpacing(100);
        topPanelContainer.setAlignment(Pos.CENTER);

        Button endTurnButton = new Button("End Turn");
        endTurnButton.setMinHeight(40);
        endTurnButton.setMinWidth(100);

        Label timer = new Label("Time: 01:39");
        timer.setMinHeight(40);
        timer.setMinWidth(100);

        Label roundCounter = new Label("Round 1");
        roundCounter.setMinHeight(40);
        roundCounter.setMinWidth(100);


        topPanelContainer.getChildren().addAll(roundCounter, timer, endTurnButton);
        topPane.getChildren().add(topPanelContainer);
    }

    public static void setAlertBoxContainer(String message) { //TO DO : E3MEL TEXT WRAP
        HBox alertBox = new HBox();
        alertBox.setMinWidth(300);
        alertBox.setMaxWidth(300);
        alertBox.setMinHeight(100);
        alertBox.setMaxHeight(100);
        alertBox.getStyleClass().add("alertBox");

        alertBox.setTranslateX(-200);
        alertBox.setTranslateY(-280);
        alertBox.setAlignment(Pos.CENTER);

        Button xButton = new Button();
        xButton.getStyleClass().add("alertXButton");
        xButton.setMinWidth(20);
        xButton.setMaxWidth(20);
        xButton.setMinHeight(20);
        xButton.setMaxHeight(20);

        Label labelMessage = new Label(message);
        labelMessage.setAlignment(Pos.CENTER);

        alertBox.setSpacing(15);
        alertBox.getChildren().addAll(labelMessage, xButton);

        root.getChildren().add(alertBox);
    }
}
