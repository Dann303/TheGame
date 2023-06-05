package views;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Scene3 extends Scene {
    public static StackPane root = new StackPane();    // root howwa el asas, han7ot feeh el pain borderPane bas also we can now put over it el exception absolutely! (absolutely ya3ny using any coords we like)
    public static BorderPane theGameLayout = new BorderPane(); // el beta3a el shayla kol 7aga
    public static GridPane grid = new GridPane(); // container of the grid

    // Figure a way out to be able to store currentHero when you click on a hero
    // and also figure a way for healing to work, youll click on the hero to use (medic) then youll pick the other hero to heal (possibly same hero)
    // in a way lazem tefarra2
    // maybe hate3melha enak lama te3mel el superAbility fel medic hayprompt you to click on someone to heal <<==============
    public static Hero currentHero;
    public static Character currentTarget;
    public static Cell currentTargetCell;
    public static Cell currentHeroCell;
//    public static ArrayList<Hero> heroesAlive = new ArrayList<>();
    public static VBox heroesAlive = new VBox();

    public static Character newTarget;

    public static VBox middleContainer = new VBox(); // contains top, middle and bottom containers

    public static HBox topPane = new HBox();
    public static HBox bottomPane = new HBox();
    public static VBox leftSideBar = new VBox();
    public static VBox rightSideBar = new VBox();

    public static int currentRound = 1;
    public static boolean isHealing = false;
    public static boolean isHovering = false;
    public static Cell hoveredOverCell = null;
    public static boolean gameDone = false;

    public static int seconds = 0;
    public static String timeFormat = "00 : 00";

    public static boolean selectingTarget = false;

    // buttons
    private static Button up;
    private static Button down;
    private static Button left;
    private static Button right;
    private static Button attackButton;
    private static Button specialAbilityButton;
    private static Button cureButton;
    private static Button showHeroes;
    private static Button endTurnButton;

    public Scene3(){
//        super(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34,56,78));
        super(new BorderPane(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(34,56,78));

        Main.clearListeners();

        root.getStyleClass().add("root4");

        setWindowResizeableListener();

        // css link
        this.getStylesheets().add(Scene3.class.getResource("styles/scene3.css").toExternalForm()); // link to css file

        createKeysActionsListeners();

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

        createMoveKeysActionListeners();
        //bengarab ne3mel setAlertBoxContainer (remove by using index 1)
//        setAlertBoxContainer("Hey, you can't go there!");
        setButtonsFocusable(false);


    }

    private static void createMoveKeysActionListeners() {
//        Scene3.root.setOnKeyPressed(e -> {
//            if (currentHero == null) {
//                // erza3 exception
//            } else {
//                // move
//                switch (e.getCode() == KeyCode.DOWN)
//            }
//        });
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

    public static void setGridElements() {
        grid.getChildren().clear();
        grid.getChildren().removeAll();

        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                // create the element to be added (eg: label)
                // for now tub3an dah hayet8ayar lamma neegy ne7ot makano background icon maslan
                // and to link it with some character we'll use el cells in Game.map
                // basically we're gonna add a property to Cell class which is the iconPath that will hold path to some icon to be displayed

                // get current cell and its path
                Cell currentCell = Game.map[i][j];

                // create container (label)
//                Label label = new Label();
                StackPane cell = new StackPane();
                Label iconImage = new Label();
                String icon = "nothing";

                if (currentCell != null)
                    icon = currentCell.getIcon();

                // according to iconPath add a css class with the corresponding class
                switch (icon) {
                    case "nothing":
                        iconImage.getStyleClass().add("emptyIcon");
                        break;
                    case "hero":
                        iconImage.getStyleClass().add("heroIcon");
                        break;
                    case "zombie":
                        iconImage.getStyleClass().add("zombieIcon");
                        break;
                    case "vaccine":
                        iconImage.getStyleClass().add("vaccineIcon");
                        break;
                    case "supply":
                        iconImage.getStyleClass().add("supplyIcon");
                        break;
                    case "invisible":
                        iconImage.getStyleClass().add("invisibleIcon");
                        break;
                    default:
                        iconImage.getStyleClass().add("emptyIcon");
                        break;
                }

                // set that container's settings
                cell.setAlignment(Pos.CENTER);
                cell.minWidthProperty().bind(Bindings.divide(Main.width, 30));
//                cell.setMinWidth(40);
                cell.minHeightProperty().bind(Bindings.divide(Main.height, 26.6667));
//                cell.setMinHeight(40);
                cell.getStyleClass().add("cell");

                // set the image settings
                iconImage.setAlignment(Pos.CENTER);
                iconImage.minWidthProperty().bind(Bindings.divide(Main.width, 48));
//                iconImage.setMinWidth(25);
                iconImage.minHeightProperty().bind(Bindings.divide(Main.height, 32));
//                iconImage.setMinHeight(25);
                iconImage.getStyleClass().add("icon");

                // add it to the grid and set its coordinates
                if(currentCell == currentHeroCell) {
                    cell.getStyleClass().add("currentHeroCell");
                } else if (currentCell == hoveredOverCell) {
                    cell.getStyleClass().add("hoveredOverCell");
                } else if (currentCell == currentTargetCell) {
                    cell.getStyleClass().add("currentTargetCell");
                } else {

                }
                cell.getChildren().add(iconImage);
                grid.getChildren().add(cell);
                // constraints dy basically specifies the coordinates of the node that was added to the grid
                grid.setConstraints(cell, i,14-j);

                final int finalI = i;
                final int finalJ = j;

                // create event listener for el grid cells (to extract el coordinates)
                // hover
                cell.setOnMouseEntered(e -> {
                    if (!selectingTarget) {
                        isHovering = true;
                        selectingTarget = false;
                        hoveredOverCell = Game.map[finalI][finalJ];
                        cell.getStyleClass().add("hoveredOverCell");
                        if (hoveredOverCell instanceof CharacterCell && ((CharacterCell) hoveredOverCell).getCharacter() instanceof Zombie){
                            Zombie zombie = ((Zombie) ((CharacterCell) hoveredOverCell).getCharacter());
                            if (zombie.getZombieImageIndex() == -1) {
                                int randomZombieIndex = (int) (Math.random() * 3) + 1;
                                zombie.setZombieImageIndex(randomZombieIndex);
                            }
                        }
                        setRightSideBar();
                    }
                });

                cell.setOnMouseExited(e -> {
                    if (!selectingTarget) {
                        hoveredOverCell = null;
                        selectingTarget = false;
                        isHovering = false;
                        cell.getStyleClass().remove("hoveredOverCell");
                        setRightSideBar();
                    }
                });

                // click
                cell.setOnMouseClicked(e -> {
                    isHovering = false;
                    if (isHealing) {
                        // medic's isSpecial to select target to heal
                        Cell cellClicked = Game.map[finalI][finalJ];
                        if (cellClicked instanceof CharacterCell && cellClicked.isVisible() && ((CharacterCell)cellClicked).getCharacter() instanceof Hero) {
                            currentTarget = (Hero) ((CharacterCell)cellClicked).getCharacter();
                            currentHero.setTarget(currentTarget);

                            try {
                                currentHero.useSpecial();
                                addEffectOnScreen("heal");

                            } catch (InvalidTargetException e2) {
                                e2.printStackTrace();
                            } catch (NoAvailableResourcesException e2) {
                                e2.printStackTrace();
                            }
                        }
                        updateScene();
                        isHealing = false;
                    } else {
                        // normal click
                        Cell cellClicked = Game.map[finalI][finalJ];
                        if (cellClicked instanceof CharacterCell && ((CharacterCell)cellClicked).getCharacter() != null) {
                            Character characterClicked = ((CharacterCell)cellClicked).getCharacter();
                            if (characterClicked instanceof Hero) {
                                // hero cell
                                // select hero w nekhaleeh yeb2a el currentHero
                                if (currentHero == (Hero) characterClicked) {
                                    currentHero = null;
                                    currentHeroCell = null;
                                }
                                else {
                                    currentHero = (Hero) characterClicked;
                                    currentHeroCell = cellClicked;
                                }
//                            currentTarget = characterClicked;
                                updateLeftSideBar();
                            } else {
                                // zombie
                                if((Zombie)((CharacterCell) cellClicked).getCharacter() != characterClicked && ((Zombie)((CharacterCell) cellClicked).getCharacter()).getZombieImageIndex() == -1) {
                                    int randomZombieIndex = (int) (Math.random() * 3) + 1;
                                    ((Zombie) currentTarget).setZombieImageIndex(randomZombieIndex);
                                }
                                if(currentTarget != characterClicked){
                                    currentTarget = characterClicked;
                                    currentTargetCell = cellClicked;
                                } else {
                                    currentTarget = null;
                                    currentTargetCell = null;
                                }

                            }
                            updateScene();
                        } else {
                            // law mesh charactercell aw character cell bas null yeb2a heya
                            // 1- empty cell, 2- vaccine cell, 3- supply cell, 4- trap cell
                            // once geit hena ana kda nawy amove fa check first if hero is selected
                            if (currentTargetCell == cellClicked){
                                currentTargetCell = null;
                            } else {
                                currentTargetCell = cellClicked;
                            }
                            updateScene();

                            if (currentHero == null) {
                                // erza3 exception men 3andena
//                                setAlertBoxContainer("Please select a hero before moving!");
                            } else {
                                // fee hero selected fa you can move
                                int heroX = currentHero.getLocation().x;
                                int heroY = currentHero.getLocation().y;
                                int targetX = finalI;
                                int targetY = finalJ;

                                int diffX = targetX - heroX;
                                int diffY = targetY - heroY;


                                Direction directionToMove = null;
                                if (diffX == 0 && diffY == 1)
                                    directionToMove = Direction.RIGHT;
                                else if (diffX == 0 && diffY == -1)
                                    directionToMove = Direction.LEFT;
                                else if (diffX == 1 && diffY == 0)
                                    directionToMove = Direction.UP;
                                else if (diffX == -1 && diffY == 0)
                                    directionToMove = Direction.DOWN;
                                else {
                                    // aw erza3 exception
                                    return;
                                }
                                try {
                                    currentHero.move(directionToMove);
                                    updateLeftSideBar();
                                } catch (MovementException ex) {
                                    ex.printStackTrace();
                                } catch (NotEnoughActionsException ex) {
                                    ex.printStackTrace();
                                }
//                            updateGridCells();
                            }
                            updateScene();
                        }

                    }

                    cell.setOnMouseClicked(null);
                });

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
//        topPane.setMinHeight(80);
        topPane.minHeightProperty().bind(Bindings.divide(Main.height, 10));
//        topPane.getStyleClass().add("topPane");

        rightSideBar.setAlignment(Pos.CENTER);
//        rightSideBar.setMinWidth(200);
        rightSideBar.minWidthProperty().bind(Bindings.divide(Main.width, 6));
//        rightSideBar.setPadding(new Insets(0, 25, 0, 25));
        rightSideBar.styleProperty().bind(Bindings.concat("-fx-padding: 0 ", Bindings.divide(Main.width,48), " 0 ", Bindings.divide(Main.width,48), ";"));
//        rightSideBar.getStyleClass().add("rightSideBar");

        bottomPane.setAlignment(Pos.CENTER);
//        bottomPane.setMinHeight(80);
        bottomPane.minHeightProperty().bind(Bindings.divide(Main.height, 10));
//        bottomPane.setSpacing(80);
        bottomPane.spacingProperty().bind(Bindings.divide(Main.width, 15));
//        bottomPane.setPadding(new Insets(10, 0, 10, 0));
        bottomPane.styleProperty().bind(Bindings.concat("-fx-padding: ", Bindings.divide(Main.height,80), " 0 ", Bindings.divide(Main.height,80), " 0;"));
//        bottomPane.getStyleClass().add("bottomPane");

        leftSideBar.setAlignment(Pos.CENTER);
//        leftSideBar.setMinWidth(200);
        leftSideBar.minWidthProperty().bind(Bindings.divide(Main.width, 6));
//        leftSideBar.setPadding(new Insets(0, 25, 0, 25));
        leftSideBar.styleProperty().bind(Bindings.concat("-fx-padding: 0 ", Bindings.divide(Main.width,48), " 0 ", Bindings.divide(Main.width,48), ";"));
//        leftSideBar.getStyleClass().add("leftSideBar");

    }

    private static GridPane getDirectionsButtons() {
        // gets us the 4 move buttons we have in the bottomPane

        GridPane moveKeysButtons = new GridPane();

        // create 4 buttons and add them to a container moveKeysButtons
        up = new Button();
        down = new Button();
        left = new Button();
        right = new Button();



        up.setOnMouseClicked(e -> {
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero first!");
            } else {
                //move
                try {
                    currentHero.move(Direction.RIGHT);
                } catch (MovementException ex) {
                    ex.printStackTrace();
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                }

                //refresh scene
                updateScene();
            }
        });

        down.setOnMouseClicked(e -> {
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero first!");
            } else {
                //move
                try {
                    currentHero.move(Direction.LEFT);
                } catch (MovementException ex) {
                    ex.printStackTrace();
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                }

                //refresh scene
                updateScene();            }
        });

        left.setOnMouseClicked(e -> {
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero first!");
            } else {
                //move
                try {
                    currentHero.move(Direction.DOWN);
                } catch (MovementException ex) {
                    ex.printStackTrace();
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                }

                //refresh scene
                updateScene();
            }
        });

        right.setOnMouseClicked(e -> {
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero first!");
            } else {
                //move
                try {
                    currentHero.move(Direction.UP);
                } catch (MovementException ex) {
                    ex.printStackTrace();
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                }

                //refresh scene
                updateScene();
            }
        });

        // add them to the small grid container moveKeysButtons
        moveKeysButtons.getChildren().addAll(up, down, left, right);

        // add to each one a seperate class to style their backgrounds
//        up.getStyleClass().addAll("key-button", "up-key");
        up.getStyleClass().add("up-key");
//        down.getStyleClass().addAll("key-button", "down-key");
        down.getStyleClass().add("down-key");
//        left.getStyleClass().addAll("key-button", "left-key");
        left.getStyleClass().add("left-key");
//        right.getStyleClass().addAll("key-button", "right-key");
        right.getStyleClass().add("right-key");

        up.minWidthProperty().bind(Bindings.divide(Main.width, 40));
        up.minHeightProperty().bind(Bindings.divide(Main.height, 26.667));
        down.minWidthProperty().bind(Bindings.divide(Main.width, 40));
        down.minHeightProperty().bind(Bindings.divide(Main.height, 26.667));
        left.minWidthProperty().bind(Bindings.divide(Main.width, 40));
        left.minHeightProperty().bind(Bindings.divide(Main.height, 26.667));
        right.minWidthProperty().bind(Bindings.divide(Main.width, 40));
        right.minHeightProperty().bind(Bindings.divide(Main.height, 26.667));

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

    private static void setBottomPane() {
        // create the 4 buttons to be added to the bottom pane
        attackButton = new Button("Attack");
        specialAbilityButton = new Button("<Special Ability>"); // button text will be set according to currentHero instance of what
        cureButton = new Button("Cure");
        GridPane moveKeysButtons = getDirectionsButtons(); // the 4 movement button keys

        if (currentHero instanceof Fighter)
            specialAbilityButton.setText("Berserk Mode");
        else if (currentHero instanceof Medic)
            specialAbilityButton.setText("Heal");
        else if (currentHero instanceof Explorer)
            specialAbilityButton.setText("Illuminate"); //show map

        // for each one add the class bottomPaneButton
//        attackButton.getStyleClass().add("bottomPaneButton");
//        specialAbilityButton.getStyleClass().add("bottomPaneButton");
//        cureButton.getStyleClass().add("bottomPaneButton");
//        moveKeysButtons.getStyleClass().add("bottomPaneButton");

        attackButton.minWidthProperty().bind(Bindings.divide(Main.width, 17.143));
        attackButton.minHeightProperty().bind(Bindings.divide(Main.height, 20));
        specialAbilityButton.minWidthProperty().bind(Bindings.divide(Main.width, 17.143));
        specialAbilityButton.minHeightProperty().bind(Bindings.divide(Main.height, 20));
        cureButton.minWidthProperty().bind(Bindings.divide(Main.width, 17.143));
        cureButton.minHeightProperty().bind(Bindings.divide(Main.height, 20));

        // action actionlistener
        attackButton.setOnMouseClicked(e -> {
            // check if hero is selected walla la2
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero before attacking!");
            } else {
                try {
                    //before attacking set the currentTarget as the target for the currentHero
                    currentHero.setTarget(currentTarget);
                    currentHero.attack();
                    updateScene();
                    if (currentTarget.getCurrentHp() == 0) {

                        // timer 3 seconds
                        Timer t = new Timer();
                        TimerTask t1 = new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    currentTarget = null;
                                    updateScene();
                                    t.cancel();
                                });
                            }
                        };

                        t.schedule(t1, 3000);

                    }
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                } catch (InvalidTargetException ex) {
                    ex.printStackTrace();
                }

                updateScene();
            }
        });

        specialAbilityButton.setOnMouseClicked(e1 -> {
            if (currentHero == null) {
                //erza3 exception
                setAlertBoxContainer("Please select a hero first before invoking a special Ability!");
            } else {
                if (currentHero instanceof Medic){
                    if (currentHero.getSupplyInventory().size()<=0) { // empty
                        // erza3 exception
                        setAlertBoxContainer("No enough supplies!");
                    } else {
                        isHealing = true;
                    }
                } else {
                    // not medic ya3ny fighter aw explorer
                    try {
                        currentHero.useSpecial();

                        if (currentHero instanceof Fighter)
                            addEffectOnScreen("berserk");
                        else
                            addEffectOnScreen("light");

                    } catch (InvalidTargetException e) {
                        e.printStackTrace();
                    } catch (NoAvailableResourcesException e) {
                        e.printStackTrace();
                    }
                }

                // refresh scene
                updateScene();
            }
        });

        cureButton.setOnMouseClicked(e -> {
            if (currentHero == null) {
                // erza3 exception
                setAlertBoxContainer("Please select a hero to cure a zombie!");
            } else {
                if (currentTarget instanceof Zombie) {
                    currentHero.setTarget(currentTarget);
                    try {
                        currentHero.cure();
                    } catch (InvalidTargetException ex) {
                        ex.printStackTrace();
                    } catch (NoAvailableResourcesException ex) {
                        ex.printStackTrace();
                    } catch (NotEnoughActionsException ex) {
                        ex.printStackTrace();
                    }
                    currentTarget = null;
                    currentTargetCell = null;
                } else {
                    // erza3 exception
                    setAlertBoxContainer("Invalid target to cure!");
                }
            }
            updateScene();
        });
        // FOR LATER **** thanks to mahmoud for the idea, instead of text for the buttons, we will use small images to cover the whole button area
        // we will do this by giving each button a unique class
        // and in this class add an image that refers to what the button does

        // add them to the bottomPane
        bottomPane.getChildren().clear();
        bottomPane.getChildren().removeAll();
        bottomPane.getChildren().addAll(attackButton,specialAbilityButton,cureButton,moveKeysButtons);
    }

    private static void setRightSideBar() {
        // basically the right side bar contains data of the target
        // note: we didn't set the vbox that we create directly to the right of the borderpane rather 7atenah gowa el vbox that already exists in the right of the border pane
        // that is because we wanna specify en what we setRight fel borderPane hay take some area mo3ayana and the target container nafso hayeb2a gowa this area so that it wont
        // stick to the other areas around

        VBox targetContainer = new VBox();

        // whole sidebar panel settings
//        targetContainer.setMinWidth(204);
        targetContainer.minWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        targetContainer.setMaxWidth(204);
        targetContainer.maxWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        targetContainer.setMinHeight(700);
        targetContainer.minHeightProperty().bind(Bindings.divide(Main.height,1.143));
        targetContainer.getStyleClass().add("targetContainer");

        // image settings
        Label targetImage = new Label();
//        targetImage.setMinWidth(200);
        targetImage.minWidthProperty().bind(Bindings.divide(Main.width,6));
//        targetImage.setMinHeight(300);
        targetImage.minHeightProperty().bind(Bindings.divide(Main.height,2.667));
        targetImage.getStyleClass().add("targetImage");

        Text targetName = new Text();
        Text targetType = new Text();
        Text health = new Text();
        Text attackDamage = new Text();
        Text remainingActionPoints = new Text();
        Text supplies = new Text();
        Text vaccines = new Text();

        // ba2eit el text that will be added as details and their settings
        VBox targetText = new VBox();
        targetText.getStyleClass().add("targetText");
        targetText.setAlignment(Pos.CENTER);
//        targetText.setSpacing(20);
        targetText.spacingProperty().bind(Bindings.divide(Main.height,40));
//        targetText.setPadding(new Insets(50, 0, 0, 0));
        targetText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,75), "px; -fx-padding: ", Bindings.divide(Main.height,16), " 0 0 0;"));


        if (isHovering) {
            // hovering so display the hovered thing

            //targetImage
            if ((hoveredOverCell != null && !hoveredOverCell.isVisible())) { // || (currentTargetCell instanceof CharacterCell && currentTarget)
                targetImage.getStyleClass().add("targetImageInvisible");
            } else {
                if (hoveredOverCell instanceof TrapCell){
                    targetImage.getStyleClass().add("targetImageEmpty");
                } else if (hoveredOverCell instanceof CharacterCell && ((CharacterCell) hoveredOverCell).getCharacter() == null){
                    targetImage.getStyleClass().add("targetImageEmpty");
                } else if(hoveredOverCell instanceof CollectibleCell) {
                    if (((CollectibleCell) hoveredOverCell).getCollectible() instanceof Vaccine) {
                        // vaccine
                        targetImage.getStyleClass().add("targetImageVaccine");
                    } else {
                        // supply
                        targetImage.getStyleClass().add("targetImageSupply");
                    }
                } else if (hoveredOverCell instanceof CharacterCell){
                    // zombie aw hero
                    if (((CharacterCell) hoveredOverCell).getCharacter() instanceof Zombie){
                        // zombie
                        targetImage.getStyleClass().add("targetImageZombie" + ((Zombie)((CharacterCell) hoveredOverCell).getCharacter()).getZombieImageIndex());
                    } else if (((CharacterCell) hoveredOverCell).getCharacter() instanceof Hero) {
                        Hero hero = (Hero)(((CharacterCell) hoveredOverCell).getCharacter());

                        // hero
                        if (hero == Main.allHeroes.get(0))
                            targetImage.getStyleClass().add("heroImage1");
                        else if (hero == Main.allHeroes.get(1))
                            targetImage.getStyleClass().add("heroImage2");
                        else if (hero == Main.allHeroes.get(2))
                            targetImage.getStyleClass().add("heroImage3");
                        else if (hero == Main.allHeroes.get(3))
                            targetImage.getStyleClass().add("heroImage4");
                        else if (hero == Main.allHeroes.get(4))
                            targetImage.getStyleClass().add("heroImage5");
                        else if (hero == Main.allHeroes.get(5))
                            targetImage.getStyleClass().add("heroImage6");
                        else if (hero == Main.allHeroes.get(6))
                            targetImage.getStyleClass().add("heroImage7");
                        else if (hero == Main.allHeroes.get(7))
                            targetImage.getStyleClass().add("heroImage8");
                    }
                } else {
                    // trap or empty
                    targetImage.getStyleClass().add("targetImageEmpty");
                }

                // description

                // text to be added as details **** TO BE CHANGED TO BE DYNAMIC (according to currentTarget)
                if (hoveredOverCell instanceof CharacterCell) {
                    Character character = ((CharacterCell) hoveredOverCell).getCharacter();
                    if (character instanceof Zombie){
//                        targetText.setSpacing(30);
                        targetText.spacingProperty().bind(Bindings.divide(Main.height,26.667));
//                        targetText.setPadding(new Insets(100, 0, 0, 0));
                        targetText.styleProperty().bind(Bindings.concat("-fx-padding: ", Bindings.divide(Main.height,8), " 0 0 0;"));
                        targetName.setText("Name : " + character.getName());
                        health.setText("Health : " + character.getCurrentHp() + "/" + character.getMaxHp() + " HP");
                        attackDamage.setText("Attack Damage : " + character.getAttackDmg());
                    } else if (character instanceof Hero){
                        targetName.setText("Name : " + character.getName());
                        targetType.setText(((Hero) character).getType());
                        health.setText("Health : " + character.getCurrentHp() + "/" + character.getMaxHp() + " HP");
                        attackDamage.setText("Attack Damage : " + character.getAttackDmg());
                        remainingActionPoints.setText("Moves left : " + ((Hero) character).getActionsAvailable());
                        supplies.setText("Supplies in inventory : " + ((Hero) character).getSupplyInventory().size());
                        vaccines.setText("Vaccines in inventory : " + ((Hero) character).getVaccineInventory().size());
                    }
                } else {
                    targetName.setText("");
                    targetType.setText("");
                    health.setText("");
                    attackDamage.setText("");
                    remainingActionPoints.setText("");
                    supplies.setText("");
                    vaccines.setText("");
                }

            }
        } else {
            // not hovering so display the selected Target

            // targetImage
            if ((currentTargetCell != null && !currentTargetCell.isVisible())) { // || (currentTargetCell instanceof CharacterCell && currentTarget)
                targetImage.getStyleClass().add("targetImageInvisible");
            } else {
                if (currentTargetCell instanceof TrapCell){
                    targetImage.getStyleClass().add("targetImageTrap");
                } else if (currentTargetCell instanceof CharacterCell && currentTarget == null){
                    targetImage.getStyleClass().add("targetImageEmpty");
                } else if(currentTargetCell instanceof CollectibleCell) {
                    if (((CollectibleCell) currentTargetCell).getCollectible() instanceof Vaccine) {
                        // vaccine
                        targetImage.getStyleClass().add("targetImageVaccine");
                    } else {
                        // supply
                        targetImage.getStyleClass().add("targetImageSupply");
                    }
                } else if (currentTargetCell instanceof CharacterCell){
                    // zombie aw hero
                    if (currentTarget instanceof Zombie){
                        // zombie
                        targetImage.getStyleClass().add("targetImageZombie" + ((Zombie)currentTarget).getZombieImageIndex());
                    } else if (currentTarget instanceof Hero) {
                        // hero
                    }
                } else {
                    // trap or empty
                    targetImage.getStyleClass().add("targetImageEmpty");
                }
            }

            // description

            // text to be added as details **** TO BE CHANGED TO BE DYNAMIC (according to currentTarget)
            if (currentTarget instanceof Zombie){
//                targetText.setSpacing(30);
                targetText.spacingProperty().bind(Bindings.divide(Main.height,26.667));
//                targetText.setPadding(new Insets(100, 0, 0, 0));
                targetText.styleProperty().bind(Bindings.concat("-fx-padding: ", Bindings.divide(Main.height,8), " 0 0 0;"));
                targetName.setText("Name : " + currentTarget.getName());
                health.setText("Health : " + currentTarget.getCurrentHp() + "/" + currentTarget.getMaxHp() + " HP");
                attackDamage.setText("Attack Damage : " + currentTarget.getAttackDmg());
            } else if (currentTarget instanceof Hero){
                targetName.setText("Name : " + currentTarget.getName());
                targetType.setText(((Hero) currentTarget).getType());
                health.setText("Health : " + currentTarget.getCurrentHp() + "/" + currentTarget.getMaxHp() + " HP");
                attackDamage.setText("Attack Damage : " + currentTarget.getAttackDmg());
                remainingActionPoints.setText("Moves left : " + ((Hero) currentTarget).getActionsAvailable());
                supplies.setText("Supplies in inventory : " + ((Hero) currentTarget).getSupplyInventory().size());
                vaccines.setText("Vaccines in inventory : " + ((Hero) currentTarget).getVaccineInventory().size());
            } else if (currentTargetCell instanceof TrapCell){
//                targetText.setSpacing(10);
                targetText.spacingProperty().bind(Bindings.divide(Main.height,80));
                targetName.setText("You caught a trap!");
                attackDamage.setText("Damage inflicted : " + ((TrapCell) currentTargetCell).getTrapDamage());
            } else {
//            targetName.setText("Name : Danny 7ayawan");
//            targetType.setText("Fighter");
//            health.setText("Health : 50/70 HP");
//            attackDamage.setText("Attack Damage : 4 Points");
//            remainingActionPoints.setText("Moves left : 3");
//            supplies.setText("Supplies in inventory : 2");
//            vaccines.setText("Vaccines in inventory : 1");
            }
        }



        targetText.getChildren().addAll(targetName, targetType, health, attackDamage, remainingActionPoints, supplies, vaccines);

        // add el targetImage wel text details to el container that we then add to the rightSideBar (not replacing it ha)
        targetContainer.getChildren().addAll(targetImage, targetText);
        rightSideBar.getChildren().clear();
        rightSideBar.getChildren().add(targetContainer);

    }

    private static void setLeftSideBar() {
        // nafs fekret el right sidebar, besra7a mesh ader akteb tany fa see el notes el maktooba fel mathod beta3et setRightSideBar

        VBox heroContainer = new VBox();

        // container settings

//        heroContainer.setMinWidth(204);
        heroContainer.minWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        heroContainer.setMaxWidth(204);
        heroContainer.maxWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        heroContainer.setMinHeight(700);
        heroContainer.minHeightProperty().bind(Bindings.divide(Main.height,1.143));
        heroContainer.getStyleClass().add("heroContainer");

        // image settings
        Label heroImage = new Label();
//        heroImage.setMinWidth(200);
        heroImage.minWidthProperty().bind(Bindings.divide(Main.width,6));
//        heroImage.setMinHeight(300);
        heroImage.minHeightProperty().bind(Bindings.divide(Main.height,2.667));
        heroImage.getStyleClass().add("heroImage");

        if (currentHero != null) {
            if (currentHero == Main.allHeroes.get(0))
                heroImage.getStyleClass().add("heroImage1");
            else if (currentHero == Main.allHeroes.get(1))
                heroImage.getStyleClass().add("heroImage2");
            else if (currentHero == Main.allHeroes.get(2))
                heroImage.getStyleClass().add("heroImage3");
            else if (currentHero == Main.allHeroes.get(3))
                heroImage.getStyleClass().add("heroImage4");
            else if (currentHero == Main.allHeroes.get(4))
                heroImage.getStyleClass().add("heroImage5");
            else if (currentHero == Main.allHeroes.get(5))
                heroImage.getStyleClass().add("heroImage6");
            else if (currentHero == Main.allHeroes.get(6))
                heroImage.getStyleClass().add("heroImage7");
            else if (currentHero == Main.allHeroes.get(7))
                heroImage.getStyleClass().add("heroImage8");
        } else {
            heroImage.getStyleClass().add("mysteryImage");
        }

        // text details
        Text heroName = new Text();
        Text heroType = new Text();
        Text health = new Text();
        Text attackDamage = new Text();
        Text remainingActionPoints = new Text();
        Text supplies = new Text();
        Text vaccines = new Text();

        if (currentHero != null) {
            // text details
            heroName = new Text("Name : " + currentHero.getName());
            heroType = new Text(currentHero.getType()); //getType is a new method fe Hero class bet return type ka string zay getName
            health = new Text("Health : " + currentHero.getCurrentHp() + "/" + currentHero.getMaxHp() + " HP");
            attackDamage = new Text("Attack Damage : " + currentHero.getAttackDmg() + " Points");
            remainingActionPoints = new Text("Moves left : " + currentHero.getActionsAvailable() + " moves");
            supplies = new Text("Supplies in inventory : " + currentHero.getSupplyInventory().size());
            vaccines = new Text("Vaccines in inventory : " + currentHero.getVaccineInventory().size());
        } else {
            // currentHero not set yet
            heroName = new Text("Name : ---");
            heroType = new Text("---");
            health = new Text("Health : ---");
            attackDamage = new Text("Attack Damage : ---");
            remainingActionPoints = new Text("Moves left : ---");
            supplies = new Text("Supplies in inventory : ---");
            vaccines = new Text("Vaccines in inventory : ---");
        }

        // text details settings
        VBox heroText = new VBox();
        heroText.getStyleClass().add("heroText");
        heroText.setAlignment(Pos.CENTER);

//        heroText.setSpacing(20);
        heroText.spacingProperty().bind(Bindings.divide(Main.height,40));
//        heroText.setPadding(new Insets(50, 0, 0, 0));
        heroText.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,75), "px; -fx-padding: ", Bindings.divide(Main.height,16), " 0 0 0;"));
        heroText.getChildren().addAll(heroName, heroType, health, attackDamage, remainingActionPoints, supplies, vaccines);

        // add all to container and then add that container to the leftSideBar
        heroContainer.getChildren().addAll(heroImage, heroText);
        leftSideBar.getChildren().addAll(heroContainer);
    }

    private static void setTopPane() {
        // nafs fekret el left wel rightsidebars
        HBox topPanelContainer = new HBox();

        // container settings
//        topPanelContainer.getStyleClass().add("topPanelContainer");
        topPanelContainer.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,66.667), "px;"));
//        topPanelContainer.setMinWidth(700);
        topPanelContainer.minWidthProperty().bind(Bindings.divide(Main.width,1.714));
//        topPanelContainer.setSpacing(30);
        topPanelContainer.spacingProperty().bind(Bindings.divide(Main.width,40));
        topPanelContainer.setAlignment(Pos.CENTER);

        // element 1 and settings
        showHeroes = new Button("Toggle Heroes Alive");
//        showHeroes.setMinHeight(40);
        showHeroes.minHeightProperty().bind(Bindings.divide(Main.height,20));
//        showHeroes.setMinWidth(100);
        showHeroes.minWidthProperty().bind(Bindings.divide(Main.width,12));
        showHeroes.setOnMouseClicked(e -> {
            if (root.getChildren().contains(heroesAlive)) {
                root.getChildren().remove(heroesAlive);
                heroesAlive.getChildren().clear();
            } else {
                showHeroesAlive();
                root.getChildren().add(heroesAlive);
            }
            updateScene();
        });

        endTurnButton = new Button("End Turn");
//        endTurnButton.setMinHeight(40);
        endTurnButton.minHeightProperty().bind(Bindings.divide(Main.height,20));
//        endTurnButton.setMinWidth(100);
        endTurnButton.minWidthProperty().bind(Bindings.divide(Main.width,12));
        endTurnButton.setOnMouseClicked(e -> {
            try {
                Game.endTurn();
            } catch (InvalidTargetException ex) {
                ex.printStackTrace();
            } catch (NotEnoughActionsException ex) {
                ex.printStackTrace();
            }
            // set current hero w current target b null
            currentHero = null;
            currentTarget = null;
            currentTargetCell = null;
            currentHeroCell = null;

            // increment round number
            currentRound++;

            // refresh scene
            updateScene();
        });

        // element 2 and settings **** EB2A KHALIH TIMER BEGAD
        Label timer = new Label("Time : " + timeFormat);
//        timer.setMinHeight(40);
        timer.minHeightProperty().bind(Bindings.divide(Main.height,20));
//        timer.setMinWidth(100);
        timer.minWidthProperty().bind(Bindings.divide(Main.width,12));

        // element 3 and settings **** INCREMENTS WITH GAME.ENDTURN()
        Label roundCounter = new Label("Round " + currentRound);
//        roundCounter.setMinHeight(40);
        roundCounter.minHeightProperty().bind(Bindings.divide(Main.height,20));
//        roundCounter.setMinWidth(100);
        roundCounter.minWidthProperty().bind(Bindings.divide(Main.width,12));

        // add kolo lel container then add container to topPane
        topPanelContainer.getChildren().addAll(roundCounter, timer, showHeroes, endTurnButton);
        topPane.getChildren().clear();
        topPane.getChildren().removeAll();
        topPane.getChildren().add(topPanelContainer);
    }

    public static void setAlertBoxContainer(String message) { //TO DO : E3MEL TEXT WRAP
        // create alert box
        StackPane alertBox = new StackPane();

        // the close button inside the alert box to force close it (remove it from the root.getChildren().get(1))
        Button xButton = new Button();
        xButton.getStyleClass().add("alertXButton");
        xButton.minHeightProperty().bind(Bindings.divide(Main.height,20));
//        xButton.setMinWidth(20);
        xButton.minWidthProperty().bind(Bindings.divide(Main.width,60));
//        xButton.setMaxWidth(20);
        xButton.maxWidthProperty().bind(Bindings.divide(Main.height,40));
//        xButton.setMinHeight(20);
        xButton.minHeightProperty().bind(Bindings.divide(Main.width,60));
//        xButton.setMaxHeight(20);
        xButton.maxHeightProperty().bind(Bindings.divide(Main.height,40));
        xButton.setAlignment(Pos.TOP_RIGHT);
//        xButton.setTranslateX(135);
        xButton.translateXProperty().bind(Bindings.divide(Main.width,8.889));
//        xButton.setTranslateY(-35);
        xButton.translateYProperty().bind(Bindings.divide(Main.height,-22.857));
        xButton.setOnMouseClicked(e -> {
            root.getChildren().remove(alertBox);
        });

        FadeTransition fadeOut = createEffect();
        fadeOut.setNode(alertBox);

        Timer timer = new Timer();
        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    fadeOut.play();
                });
            }
        };

        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.getChildren().remove(alertBox);
                });
            }
        };

        timer.schedule(t1,5000);
        timer.schedule(t2,7000);

        // add xButton actionlistener here (pressed yeb2a remove it from the root

        // create the message to be added aw inserted as error
        Label labelMessage = new Label(message);
        labelMessage.setAlignment(Pos.CENTER);
        labelMessage.setWrapText(true);
        labelMessage.setTextAlignment(TextAlignment.CENTER);
//        labelMessage.setMaxWidth(280);
        labelMessage.maxWidthProperty().bind(Bindings.divide(Main.width,4.286));

        // settings
//        alertBox.setMinWidth(300);
        alertBox.minWidthProperty().bind(Bindings.divide(Main.width,4));
//        alertBox.setMaxWidth(300);
        alertBox.maxWidthProperty().bind(Bindings.divide(Main.width,4));
//        alertBox.setMinHeight(100);
        alertBox.minHeightProperty().bind(Bindings.divide(Main.height,8));
//        alertBox.setMaxHeight(100);
        alertBox.maxHeightProperty().bind(Bindings.divide(Main.height,8));
        alertBox.setAlignment(Pos.CENTER);
        alertBox.getStyleClass().add("alertBox");
        alertBox.styleProperty().bind(Bindings.concat("-fx-background-radius: ", Bindings.divide(Main.width, 80),"px; -fx-font-size: ", Bindings.divide(Main.width, 66.667),";"));


        // coordinates
//        alertBox.setTranslateX(-200);
        alertBox.translateXProperty().bind(Bindings.divide(Main.width,-6));
//        alertBox.setTranslateY(-280);
        alertBox.translateYProperty().bind(Bindings.divide(Main.height,-2.857));

        // add kollo lel alertBox w add el alertBox lel root (sheelo bel action listener or be timeout) easy ya3ny isa
        alertBox.getChildren().addAll(labelMessage, xButton);
        root.getChildren().add(alertBox);
    }

    private static FadeTransition createEffect() {
        FadeTransition result = new FadeTransition();
        result.setDuration(Duration.millis(2000));
        result.setFromValue(1);
        result.setToValue(0);
        result.setCycleCount(1000);

        return result;
    }

    public static void updateLeftSideBar() {
        leftSideBar.getChildren().clear();
        leftSideBar.getChildren().removeAll();
        setLeftSideBar(); // aw update
    }

    public static void updateRightSideBar() {
        rightSideBar.getChildren().clear();
        rightSideBar.getChildren().removeAll();
        setRightSideBar(); // aw update
    }

    public static void updateScene() {
        if(Game.checkWin() && !gameDone){
            gameDone = true;
            GameWinScene.timeTaken = timeFormat;
            GameWinScene.roundNumber = currentRound;
            // win, delay 3 seconds
            Timer timer = new Timer();
            TimerTask t1 = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
//                        Main.currentStage.setScene(Main.gameWin);
                        Main.currentStage.getScene().setRoot(GameWinScene.root);
                        Main.gameWin.startTimer();
                    });
                }
            };
            timer.schedule(t1,3000);

        }else if(Game.checkGameOver() && !gameDone) {
            gameDone = true;
            // game over, delay 3 seconds!
            Timer timer = new Timer();
            TimerTask t2 = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
//                        Main.currentStage.setScene(Main.gameOver);
                        Main.currentStage.getScene().setRoot(GameOverScene.root);
                        Main.gameOver.startTimer();

                    });
                }
            };
            timer.schedule(t2, 3000);
        }

        //refresh grid
        Game.setCellsIcons();
        setGridElements();

        //refresh leftsidebar
        updateLeftSideBar();

        //refresh rightsidebar
        updateRightSideBar();

        //refresh top pane
        showHeroesAlive();
        setTopPane();

        //refresh bottom pane
        setBottomPane();
    }

    public static void startTimer() {
        Timer t = new Timer();
        TimerTask t1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    seconds++;
                    createTimeString();
                    setTopPane();
                    startTimer();
                });
            }
        };

        t.schedule(t1, 1000);
    }

    public static void createTimeString(){
        String result = "";
        int minutes = (int) (seconds / 60);
        int secondsLeft = seconds % 60;

        String minString = (minutes<10)?("0"):("");
        String secString = (secondsLeft<10)?("0"):("");

        minString += minutes;
        secString += secondsLeft;

        result = minString + " : " + secString;

        timeFormat = result;
    }

    private EventHandler<KeyEvent> keyListener = new javafx.event.EventHandler<KeyEvent>(){
        @Override
        public void handle(KeyEvent keyEvent) {
            // wasd - arrows : movement
            // e: end turn - tab: swap selected hero - esc: remove alertbox - c: cure - q: activate special ability
            // t: select target mode - enter: select the currently hovered cell to be the target
            // k: attacks the selected target

            // arrow keys : up is right, down is left, left is down, right is up
            KeyCode[] moveKeys = {KeyCode.UP,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.W,KeyCode.A,KeyCode.S,KeyCode.D};

            if(elementExistsInsideArray(moveKeys ,keyEvent.getCode())) {
                if (!selectingTarget) {
                    if (currentHero == null) {
                        setAlertBoxContainer("Select a hero first!");
                        return;
                    }

                    Cell oldCell = findCellOfCharacter(currentHero);

                    Cell cellToMoveTo = null;
                    boolean movedSuccessfully = false;

                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        try {
                            cellToMoveTo = Game.map[currentHero.getLocation().x][currentHero.getLocation().y + 1];
                            currentHero.move(Direction.RIGHT);
                            movedSuccessfully = true;
                        } catch (MovementException | NotEnoughActionsException ex) {
                            ex.printStackTrace();
                        }
                    } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        try {
                            cellToMoveTo = Game.map[currentHero.getLocation().x][currentHero.getLocation().y - 1];
                            currentHero.move(Direction.LEFT);
                            movedSuccessfully = true;
                        } catch (MovementException | NotEnoughActionsException ex) {
                            ex.printStackTrace();
                        }
                    } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        try {
                            cellToMoveTo = Game.map[currentHero.getLocation().x - 1][currentHero.getLocation().y];
                            currentHero.move(Direction.DOWN);
                            movedSuccessfully = true;
                        } catch (MovementException | NotEnoughActionsException ex) {
                            ex.printStackTrace();
                        }
                    } else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        try {
                            cellToMoveTo = Game.map[currentHero.getLocation().x + 1][currentHero.getLocation().y];
                            currentHero.move(Direction.UP);
                            movedSuccessfully = true;
                        } catch (MovementException | NotEnoughActionsException ex) {
                            ex.printStackTrace();
                        }
                    }
                    currentHeroCell = findCellOfCharacter(currentHero);

                    if (cellToMoveTo instanceof TrapCell && movedSuccessfully) {
                        currentTargetCell = cellToMoveTo;
                        updateScene();
                    }
                } else {
                    // selecting target mode is on
                    int x = getLocationOfCell(hoveredOverCell).x;
                    int y = getLocationOfCell(hoveredOverCell).y;

                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                            y++;
                    } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                            y--;
                    } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                            x--;
                    } else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                            x++;
                    }

                    if (x > 14 | x < 0 | y > 14 | y < 0) {
                        // out of map
                        setAlertBoxContainer("Select a cell within the map!");
                        return;
                    }
                    hoveredOverCell = Game.map[x][y];
                }
            } else if(keyEvent.getCode() == KeyCode.E){
                try {
                    Game.endTurn();
                } catch (InvalidTargetException ex) {
                    ex.printStackTrace();
                } catch (NotEnoughActionsException ex) {
                    ex.printStackTrace();
                }
                // set current hero w current target b null
                currentHero = null;
                currentTarget = null;
                currentTargetCell = null;
                currentHeroCell = null;

                // increment round number
                currentRound++;
            } else if(keyEvent.getCode() == KeyCode.TAB){
                if (currentHero == null && Game.heroes.size() > 0) {
                    currentHero = Game.heroes.get(0);
                    currentHeroCell = findCellOfCharacter(currentHero);
                } else {
                    int indexOfHero = Game.heroes.indexOf(currentHero);
                    indexOfHero++;
                    if (indexOfHero >= Game.heroes.size()) {
                        indexOfHero = 0;
                    }
                    currentHero = Game.heroes.get(indexOfHero);
                    currentHeroCell = findCellOfCharacter(currentHero);
                }
            } else if(keyEvent.getCode() == KeyCode.ESCAPE) {
                if (root.getChildren().size() > 1) {
                    root.getChildren().remove(1);
                }
            } else if(keyEvent.getCode() == KeyCode.C) {
                if (currentHero == null) {
                    setAlertBoxContainer("Please select a hero!");
                    return;
                }
                if (!(currentTarget instanceof Zombie)){
                    setAlertBoxContainer("Please select a zombie to cure!");
                    return;
                }

                currentHero.setTarget(currentTarget);

                try {
                    currentHero.cure();
                } catch (InvalidTargetException e) {
                    e.printStackTrace();
                } catch (NoAvailableResourcesException e) {
                    setAlertBoxContainer("No enough vaccines!");
                } catch (NotEnoughActionsException e) {
                    setAlertBoxContainer("No enough action points!");
                }
            } else if(keyEvent.getCode() == KeyCode.Q) {
                if (currentHero == null){
                    setAlertBoxContainer("Please select a hero!");
                    return;
                }

                if (currentHero instanceof Medic) {
                    if (currentTarget == null) {
                        setAlertBoxContainer("Please select a target!");
                        return;
                    } else {
                        currentHero.setTarget(currentTarget);
                    }
                }

                try {
                    currentHero.useSpecial();
                    if (currentHero instanceof Medic)
                        addEffectOnScreen("heal");
                    else if (currentHero instanceof Fighter)
                        addEffectOnScreen("berserk");
                    else
                        addEffectOnScreen("light");
                } catch (InvalidTargetException e) {
                    e.printStackTrace();
                } catch (NoAvailableResourcesException e) {
                    e.printStackTrace();
                }
            } else if(keyEvent.getCode() == KeyCode.T) {
                // toggle select target mode
                selectingTarget = !selectingTarget;
                isHovering = selectingTarget;
                if (selectingTarget) {
                    if (currentHero == null) {
                        // initially cell is 0,0 if no hero is currently selected
                        hoveredOverCell = Game.map[0][0];
                    } else {
                        // initial cell selected is the hero
                        hoveredOverCell = currentHeroCell;
                    }
                } else {
                    hoveredOverCell = null;
                }
            } else if (keyEvent.getCode() == KeyCode.ENTER) {
                if (selectingTarget) {
                    selectingTarget = false;
                    isHovering = false;
                    currentTargetCell = hoveredOverCell;
                    if (currentTargetCell instanceof CharacterCell) {
                        currentTarget = ((CharacterCell) currentTargetCell).getCharacter();
                    }
                    hoveredOverCell = null;
                }
            } else if (keyEvent.getCode() == KeyCode.K) {
                if (currentHero == null) {
                    setAlertBoxContainer("Please select a hero!");
                    return;
                }

                if (currentTarget == null) {
                    setAlertBoxContainer("Please select a target to attack!");
                    return;
                }

                currentHero.setTarget(currentTarget);
                try {
                    currentHero.attack();
                } catch (NotEnoughActionsException e) {
                    e.printStackTrace();
                } catch (InvalidTargetException e) {
                    e.printStackTrace();
                }
            }
            // else if () {}
            updateScene();

        }
    };

    private Cell findCellOfCharacter(Character character) {
        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
                Cell currentCell = Game.map[i][j];
                if (currentCell instanceof CharacterCell && ((CharacterCell) currentCell).getCharacter() == character)
                    return currentCell;
            }
        }
        return null;
    }

    private Point getLocationOfCell(Cell cell) {
        for (int i = 0; i<15; i++) {
            for (int j=0; j<15; j++) {
                if (Game.map[i][j] == cell)
                    return new Point(i,j);
            }
        }
        return new Point(-1,-1);
    }

    private boolean elementExistsInsideArray(KeyCode[] array, KeyCode object){
        for (int i=0; i<array.length; i++) {
            if (array[i] == object) {
                return true;
            }
        }
        return false;
    }

    private void createKeysActionsListeners() {
        Main.myScene.addEventFilter(KeyEvent.KEY_PRESSED, keyListener);
    }

    private void setButtonsFocusable(boolean bool) {
//        up.setFocusTraversable(bool);
//        down.setFocusTraversable(bool);
//        left.setFocusTraversable(bool);
//        right.setFocusTraversable(bool);
//        attackButton.setFocusTraversable(bool);
//        specialAbilityButton.setFocusTraversable(bool);
//        cureButton.setFocusTraversable(bool);
//        endTurnButton.setFocusTraversable(bool);
    }

    private static void showHeroesAlive() {

        // refresh the heroesAlive array
//        heroesAlive.clear();
//        for (int i = 0; i < Game.heroes.size(); i++) {
//            heroesAlive.add(Game.heroes.get(i));
//        }
        VBox heroesAliveContainer = new VBox();

        for (int i = 0; i<Game.heroes.size(); i++) {
            VBox currentHeroAliveContainer = new VBox();
            Hero currentHeroAlive = Game.heroes.get(i);
            Label name = new Label(currentHeroAlive.getName());
            Label type = new Label(currentHeroAlive.getType());
            Label currentHp = new Label("Current HP : " + currentHeroAlive.getCurrentHp());
            Label attackDmg = new Label("Attack Damage : " + currentHeroAlive.getAttackDmg());
            Label maxActions = new Label("Max actions per turn : " + currentHeroAlive.getMaxActions());

            name.getStyleClass().add("heroes-alive-details");
            type.getStyleClass().add("heroes-alive-details");
            currentHp.getStyleClass().add("heroes-alive-details");
            attackDmg.getStyleClass().add("heroes-alive-details");
            maxActions.getStyleClass().add("heroes-alive-details");

            name.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,100), "px;"));
            type.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,100), "px;"));
            currentHp.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,100), "px;"));
            attackDmg.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,100), "px;"));
            maxActions.styleProperty().bind(Bindings.concat("-fx-font-size: ", Bindings.divide(Main.width,100), "px;"));

            name.setTextAlignment(TextAlignment.CENTER);
            type.setTextAlignment(TextAlignment.CENTER);
            currentHp.setTextAlignment(TextAlignment.CENTER);
            attackDmg.setTextAlignment(TextAlignment.CENTER);
            maxActions.setTextAlignment(TextAlignment.CENTER);

            name.setAlignment(Pos.CENTER);
            type.setAlignment(Pos.CENTER);
            currentHp.setAlignment(Pos.CENTER);
            attackDmg.setAlignment(Pos.CENTER);
            maxActions.setAlignment(Pos.CENTER);

//            currentHeroAliveContainer.setMaxWidth(194);
            currentHeroAliveContainer.maxWidthProperty().bind(Bindings.divide(Main.width, 6.185));
//            currentHeroAliveContainer.setMaxHeight(100);
            currentHeroAliveContainer.maxHeightProperty().bind(Bindings.divide(Main.height, 8));
            currentHeroAliveContainer.setAlignment(Pos.CENTER);
            currentHeroAliveContainer.getStyleClass().add("heroes-alive-container");
            currentHeroAliveContainer.styleProperty().bind(Bindings.concat("-fx-border-radius: ", Bindings.divide(Main.width,48),"px;"));
            currentHeroAliveContainer.getChildren().addAll(name, type, currentHp, attackDmg, maxActions);
            heroesAliveContainer.getChildren().add(currentHeroAliveContainer);

        }

        heroesAliveContainer.setStyle("-fx-background-color:white;");
//        heroesAliveContainer.setSpacing(5);
        heroesAliveContainer.spacingProperty().bind(Bindings.divide(Main.height,160));
//        heroesAliveContainer.maxWidth(204);
        heroesAliveContainer.maxWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        heroesAliveContainer.maxHeight(800);
        heroesAliveContainer.maxHeightProperty().bind(Bindings.divide(Main.height,1));
        heroesAliveContainer.setAlignment(Pos.TOP_CENTER);

        heroesAlive.getChildren().clear();
//        heroesAlive.setMaxWidth(204);
        heroesAlive.maxWidthProperty().bind(Bindings.divide(Main.width,5.882));
//        heroesAlive.setMaxHeight(800);
        heroesAlive.maxWidthProperty().bind(Bindings.divide(Main.width,5.882));
        heroesAlive.setMouseTransparent(true);
        heroesAlive.setAlignment(Pos.TOP_CENTER);
//        heroesAlive.setTranslateY(-300);
//        heroesAlive.setTranslateX(-400);
//        heroesAlive.setTranslateY(50);
        heroesAlive.translateYProperty().bind(Bindings.divide(Main.height, 16));
//        heroesAlive.setTranslateX(-477);
        heroesAlive.translateXProperty().bind(Bindings.divide(Main.width, -2.516));
        heroesAlive.getChildren().add(heroesAliveContainer);
    }

    private void setWindowResizeableListener() {
        Main.height.bind(Main.myScene.heightProperty());
        Main.width.bind(Main.myScene.widthProperty());

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Main.WINDOW_WIDTH = Main.myScene.getWidth();
                Main.WINDOW_HEIGHT = Main.myScene.getHeight();
            }
        };

        Main.myScene.widthProperty().addListener(changeListener);
        Main.myScene.heightProperty().addListener(changeListener);
    }

    private static void addEffectOnScreen(String effectName) {
        Label label = new Label ();
        label.setMinHeight(Main.height.getValue());
        label.setMinWidth(Main.width.getValue());
        label.setMouseTransparent(true);
        if (effectName == "heal") {
            label.setStyle("-fx-background-color: green;");
        } else if (effectName == "berserk") {
            label.setStyle("-fx-background-color: orange;");
        } else if (effectName == "light") {
            label.setStyle("-fx-background-color: lightblue;");
        } else {
            label.setStyle("-fx-background-color: black;");
        }

        FadeTransition transition = new FadeTransition();
        transition.setAutoReverse(false);
        transition.setDuration(Duration.millis(2000));
        transition.setFromValue(0.5);
        transition.setToValue(0.0);
        transition.setNode(label);

        root.getChildren().add(label);
        transition.play();

        Timer ScreenEffectTimer = new Timer();
        TimerTask removeEffectTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.getChildren().remove(label);
                });
            }
        };

        ScreenEffectTimer.schedule(removeEffectTask, 2000);

    }


}
