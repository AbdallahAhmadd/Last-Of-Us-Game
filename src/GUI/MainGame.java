package GUI;

import java.io.File;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.characters.Direction;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class MainGame extends Scene {

	public GridPane map;
	public StackPane origin;
	public ImageView Ellie = create_image("Ellie.png");
	public ImageView Bill = create_image("Bill.png");
	public ImageView Tess = create_image("tess.png");
	public ImageView Henry = create_image("henry.png");
	public ImageView Joel = create_image("Joel.png");
	public ImageView Tommy = create_image("tommy_miller.png");
	public ImageView Riley = create_image("Riley.png");
	public ImageView David = create_image("David.png");
	public ImageView Ellieh = create_image("Ellie.png");
	public ImageView Billh = create_image("Bill.png");
	public ImageView Tessh = create_image("tess.png");
	public ImageView Henryh = create_image("henry.png");
	public ImageView Joelh = create_image("Joel.png");
	public ImageView Tommyh = create_image("tommy_miller.png");
	public ImageView Rileyh = create_image("Riley.png");
	public ImageView Davidh = create_image("David.png");
	public ImageView ellie1 = create_image1("Ellie1.png");
	public ImageView bill1 = create_image1("Bill1.png");
	public ImageView tess1 = create_image1("Tess1.png");
	public ImageView henry1 = create_image1("Henry1.png");
	public ImageView joel1 = create_image1("Joel1.png");
	public ImageView tommy1 = create_image1("Tommy1.png");
	public ImageView riley1 = create_image1("Riley1.png");
	public ImageView david1 = create_image1("David1.png");
	public ImageView arrowR = new ImageView(new Image("moveR.png"));
	public ImageView arrowL = new ImageView(new Image("moveL.png"));
	public ImageView arrowU = new ImageView(new Image("moveU.png"));
	public ImageView arrowD = new ImageView(new Image("moveD.png"));
	public ImageView attack = new ImageView(new Image("Sniper.png"));
	public Button attackButton = new Button("ATTACK", attack);
	public ImageView heal = new ImageView(new Image("heal.png"));
	public ImageView cure = new ImageView(new Image("cure.png"));
	public Button cureButton = new Button("CURE", cure);
	public ImageView useSpecial = new ImageView(new Image("special.png"));
	public Button useSpecialButton = new Button("USE SPECIAL", useSpecial);
	public ImageView target = new ImageView(new Image("target.png"));
	public Button targetButton = new Button("SET TARGET", target);
	public ImageView fire = new ImageView(new Image("bonfire.png"));
	public Button endTurn = new Button("END TURN");
	public static String Selected;
	public static ImageView targetSelected = null;
	public DropShadow d = new DropShadow();
	public boolean errorAlreadyDisplayed = false;
	public boolean errorWindOn = false;
	public Stage popUpStage = new Stage();
	public boolean setTargetOn = false;
	public Label selectedData = new Label();
	public Label heroesData = new Label();
	private ProgressBar healthbar = new ProgressBar(1);
	public Label healthp = new Label("100%");
	public HBox heros = new HBox();
	public MediaPlayer soundeffect;
	public Button messageLabel = new Button();

	public MainGame() {
		super(new StackPane(), 3024, 1964);
		updateHealthBar();
		Image background = new Image("blured.png");
		BackgroundSize backgroundSize = new BackgroundSize(3024, 1964, true, true, true, true);
		BackgroundImage background1 = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);
		Background backgroundObject = new Background(background1);

		selectedData.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 25));
		selectedData.setTextFill(Color.WHITE);
		selectedData.setMaxHeight(300);

		heroesData.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 20));
		heroesData.setTextFill(Color.WHITE);
		heroesData.setMaxHeight(200);
		heroesData.setVisible(false);

		map = new GridPane();
		map.setVisible(true);
		map.setMaxSize(15 * 66, 15 * 58);
		StackPane.setMargin(map, new Insets(3, 460, 0, 5));

		origin = new StackPane();
		origin.setBackground(backgroundObject);
		origin.setMaxSize(3024, 1964);
		map.setVisible(true);

		target.setFitHeight(80);
		target.setFitWidth(80);

		setTransparent(targetButton);
		setTransparent(cureButton);
		setTransparent(useSpecialButton);
		setTransparent(attackButton);

		attack.setFitHeight(80);
		attack.setFitWidth(80);

		fire.setFitWidth(70);
		fire.setFitHeight(70);

		d.setColor(Color.RED);
		d.setWidth(5);
		d.setHeight(5);
		d.setRadius(5);

		place(arrowR, new Insets(450 + 70, 0, 20, 730 + 400));
		place(arrowL, new Insets(450 + 68, 110, 20, 610 + 400));
		place(arrowU, new Insets(270 + 120, 0, 0, 610 + 400));
		place(arrowD, new Insets(530 + 120, 0, 20, 610 + 400));
		place(fire, new Insets(450 + 76, 0, 20, 610 + 400));
		place(attackButton, new Insets(795, 0, 0, 650));
		place(cureButton, new Insets(800, 0, 0, 870));
		place(useSpecialButton, new Insets(800, 0, 0, 1100));
		place(targetButton, new Insets(800, 0, 0, 1350));
		place(tommy1, new Insets(0, 0, 760, 730));
		place(tess1, new Insets(0, 0, 760, 730));
		place(riley1, new Insets(0, 0, 760, 730));
		place(david1, new Insets(0, 0, 760, 730));
		place(joel1, new Insets(0, 0, 760, 730));
		place(ellie1, new Insets(0, 0, 760, 730));
		place(bill1, new Insets(0, 0, 760, 730));
		place(henry1, new Insets(0, 0, 760, 730));
		place(selectedData, new Insets(0, 0, 670, 1100));

		popUpStage.initStyle(StageStyle.UTILITY);

		tommy1.setVisible(false);
		tess1.setVisible(false);
		riley1.setVisible(false);
		david1.setVisible(false);
		joel1.setVisible(false);
		ellie1.setVisible(false);
		bill1.setVisible(false);
		henry1.setVisible(false);

		selectAtBeginning();
		addHero();

		heros.setMaxWidth(66 * 6);
		heros.setMaxHeight(55);

		heros.setVisible(true);

		((StackPane) this.getRoot()).getChildren().add(heros);

		Label data1 = new Label();
		data1.setTextFill(Color.AQUAMARINE);
		data1.setPrefSize(200, 200);
		place(data1, new Insets(300, 0, 0, 300));
		place(heros, new Insets(0, 0, 250, 1000));
		place(endTurn, new Insets(275, 0, 20, 610 + 400));

		endTurn.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 30));
		endTurn.setStyle("-fx-background-color: transparent; " + "-fx-shape: 'M0 0 L100 0 L100 50 L0 50 Z'; "
				+ "-fx-text-fill: white;" + "-fx-border-color: #D27D2D; " + "-fx-border-width: 3px;");

		endTurn.setMaxWidth(220);
		heal.setFitHeight(80);
		heal.setFitWidth(80);

		cure.setFitWidth(77);
		cure.setFitHeight(77);

		useSpecial.setFitHeight(80);
		useSpecial.setFitWidth(80);

		healthbar.setStyle("-fx-accent: #228B22;");
		// healthbar.setMaxHeight(0);
		healthbar.setMaxWidth(200);
		healthp.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 25));
		healthp.setTextFill(Color.BLACK);

		place(healthbar, new Insets(400, 0, 760, 1100));
		place(healthp, new Insets(400, 0, 760, 1100));
		place(heroesData, new Insets(0, 0, 0, 1000));

		origin.getChildren().addAll(arrowR, arrowL, arrowD, arrowU, attack, map, attackButton, cureButton,
				useSpecialButton, targetButton, fire, endTurn, bill1, tess1, ellie1, tommy1, joel1, david1, riley1,
				henry1, selectedData, healthbar, healthp, heros, heroesData);

		((StackPane) this.getRoot()).getChildren().add(origin);

		map_display();

		checkSelected();

		checkMove(arrowR, arrowL, arrowU, arrowD);

		targetButton.setOnMouseClicked(e -> {
			setTargetOn = true;
			ChoosePlayer.highlightEffect.setRadius(10);
			ChoosePlayer.highlightEffect.setColor(Color.RED);
			target.setEffect(ChoosePlayer.highlightEffect);
			animate(targetButton);
		});

		messageLabel.setVisible(false);

		setTarget();

		useSpecialExc();

		attackExc();

		cureExc();

		endTurn();

		checkHovered();

		setOnClicked();

	}

	private void addHero() {
		heros.getChildren().clear();
		for (Hero h : Game.heroes) {
			switch (h.getName()) {
			case "Joel Miller":
				heros.getChildren().add(Joelh);
				break;
			case "Tess":
				heros.getChildren().add(Tessh);
				break;
			case "Bill":
				heros.getChildren().add(Billh);
				break;
			case "Ellie Williams":
				heros.getChildren().add(Ellieh);
				break;
			case "Henry Burell":
				heros.getChildren().add(Henryh);
				break;
			case "Riley Abel":
				heros.getChildren().add(Rileyh);
				break;
			case "David":
				heros.getChildren().add(Davidh);
				break;
			case "Tommy Miller":
				heros.getChildren().add(Tommyh);
				break;
			}
		}
	}

	private void selectAtBeginning() {
		switch (MainGame.Selected) {
		case "Joel Miller":
			joel1.setVisible(true);
			break;
		case "Tess":
			tess1.setVisible(true);
			break;
		case "Bill":
			bill1.setVisible(true);
			break;
		case "Ellie Williams":
			ellie1.setVisible(true);
			break;
		case "Henry Burell":
			henry1.setVisible(true);
			break;
		case "Riley Abel":
			riley1.setVisible(true);
			break;
		case "David":
			david1.setVisible(true);
			break;
		case "Tommy Miller":
			tommy1.setVisible(true);
			break;
		}
		if (MainGame.Selected != null)
			selectedData.setText(create_text2(Selected));
	}

	private void animate(Button button) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(button.scaleXProperty(), 1.0)),
				new KeyFrame(Duration.ZERO, new KeyValue(button.scaleYProperty(), 1.0)),
				new KeyFrame(Duration.seconds(0.3), new KeyValue(button.scaleXProperty(), 1.1)),
				new KeyFrame(Duration.seconds(0.3), new KeyValue(button.scaleYProperty(), 1.1)),
				new KeyFrame(Duration.seconds(0.6), new KeyValue(button.scaleXProperty(), 1)),
				new KeyFrame(Duration.seconds(0.6), new KeyValue(button.scaleYProperty(), 1)));

		// Set animation properties
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		// Play the animation
		timeline.play();
	}

	private void endTurn() {
		endTurn.setOnMouseClicked(e -> {
			try {
				Game.endTurn();
				if (targetSelected != null)
					targetSelected.setEffect(null);
				animate(endTurn);
				if (getHeroClicked() == null) {
					heroDeath();
				} else {
					updateText();
					updateHealthBar();
				}
				addHero();
				map_display();
				if (Game.checkGameOver())
					GameEnded();
			} catch (InvalidTargetException | NotEnoughActionsException e1) {
				throwError(e1);
			}

		});
	}

	private void heroDeath() {
		addHero();
		healthbar.setProgress(0);
		MainMenu.mediaPlayer.setVolume(0.3);
		;
		soundeffect = createAudio("death.mp3");
		soundeffect.play();
		soundeffect.setOnEndOfMedia(() -> {
			MainMenu.mediaPlayer.setVolume(1);
			unSelect();
			selectedData.setVisible(false);
			healthbar.setVisible(false);
			healthp.setVisible(false);
			Selected = null;
		});

	}

	private void setTransparent(Button button) {
		button.setContentDisplay(ContentDisplay.TOP);
		button.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 30));
		button.setTextFill(Color.WHITE);
		button.setStyle(
				"-fx-background-color: transparent; -fx-border-color: transparent;-fx-pref-width: 150px; -fx-pref-height: 100px;");
	}

	private void useSpecialExc() {
		useSpecialButton.setOnMouseClicked(e -> {
			animate(useSpecialButton);
			try {
				getHeroClicked().useSpecial();
				if (getHeroClicked() == null)
					heroDeath();
				else {
					if (getHeroClicked() instanceof Medic) {
						MainMenu.mediaPlayer.setVolume(0.3);
						soundeffect = createAudio("healing.mp3");
						soundeffect.play();
						soundeffect.setOnEndOfMedia(() -> {
							MainMenu.mediaPlayer.setVolume(1);
							updateHealthBar();
						});
					}
					else 
					   updateHealthBar();
					updateText();
				}
				map_display();
				if (Game.checkGameOver())
					GameEnded();
			} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
				throwError(e1);
			}
			if (Game.checkGameOver()) {
				GameEnded();
			}
		});
	}

	private void attackExc() {
		attackButton.setOnMouseClicked(e -> {
			animate(attackButton);
			try {
				getHeroClicked().attack();
				MainMenu.mediaPlayer.setVolume(0.3);
				soundeffect = createAudio("gunshot.mp3");
				soundeffect.play();
				soundeffect.setOnEndOfMedia(() -> {
					MainMenu.mediaPlayer.setVolume(1);
					if (getHeroClicked() == null) {
						heroDeath();
					} else {
						updateText();
						updateHealthBar();

					}
					map_display();
				});
				if (Game.checkGameOver())
					GameEnded();
			} catch (InvalidTargetException | NotEnoughActionsException e1) {
				throwError(e1);
			}
		});
	}

	private void cureExc() {
		cureButton.setOnMouseClicked(e -> {
			animate(cureButton);
			try {
				getHeroClicked().cure();
				MainMenu.mediaPlayer.setVolume(0.3);
				soundeffect = createAudio("curing.mp3");
				soundeffect.play();
				soundeffect.setOnEndOfMedia(() -> {
					MainMenu.mediaPlayer.setVolume(1);
					updateText();
					updateHealthBar();
					map_display();
					addHero();
				});
				if (Game.checkGameOver())
					GameEnded();
			} catch (InvalidTargetException | NoAvailableResourcesException | NotEnoughActionsException e1) {
				throwError(e1);
			}
			if (Game.checkGameOver()) {
				GameEnded();
			}
		});
	}

	private void setTarget() {
		map.setOnMouseClicked(e -> {
			if (setTargetOn) {
				int i = map.getRowIndex((Node) e.getTarget());
				int j = map.getColumnIndex((Node) e.getTarget());
				if (Game.map[14 - i][j] instanceof CharacterCell) {
					model.characters.Character t = ((CharacterCell) Game.map[14 - i][j]).getCharacter();
					getHeroClicked().setTarget(t);
					if (t != null) {
						if (targetSelected != null)
							targetSelected.setEffect(null);
						targetSelected = (ImageView) e.getTarget();
						targetSelected.setEffect(d);
						target.setEffect(null);
					}
					setTargetOn = false;
				}
			}
		});
	}

	private void checkSelected() {
		Ellie.setOnMouseClicked(e -> {
			click(ellie1,"Ellie Williams");
		});
		Bill.setOnMouseClicked(e -> {
			click(bill1,"Bill");
		});
		Tess.setOnMouseClicked(e -> {
			click(tess1,"Tess");
		});
		Henry.setOnMouseClicked(e -> {
			click(henry1,"Henry Burell");
		});
		Joel.setOnMouseClicked(e -> {
			click(joel1,"Joel Miller");
		});
		Tommy.setOnMouseClicked(e -> {
			click(tommy1,"Tommy Miller");
		});
		Riley.setOnMouseClicked(e -> {
			click(riley1,"Riley Abel");
		});
		David.setOnMouseClicked(e -> {
			click(david1,"David");
		});
	}

	private void checkHovered() {
		Ellieh.setOnMouseEntered(e -> {
			hover(Ellieh,"Ellie Williams");
		});
		Billh.setOnMouseEntered(e -> {
			hover(Billh,"Bill");
		});
		Tessh.setOnMouseEntered(e -> {
			hover(Tessh,"Tess");
		});
		Henryh.setOnMouseEntered(e -> {
			hover(Henryh,"Henry Burell");
		});
		Joelh.setOnMouseEntered(e -> {
			hover(Joelh,"Joel Miller");
		});
		Tommyh.setOnMouseEntered(e -> {
			hover(Tommyh,"Tommy Miller");
		});
		Rileyh.setOnMouseEntered(e -> {
			hover(Rileyh,"Riley Abel");
		});
		Davidh.setOnMouseEntered(e -> {
			hover(Davidh,"David");
		});
	}

	private void hover(ImageView im,String text) {
		heroesData.setVisible(true);
		heroesData.setText(create_text3(text));
		im.setOnMouseExited(e1 -> {
			heroesData.setVisible(false);
		});
	}

	private void setOnClicked() {
		Ellieh.setOnMouseClicked(e -> {
			click(ellie1,"Ellie Williams");
		});
		Billh.setOnMouseClicked(e -> {
			click(bill1,"Bill");
		});
		Tessh.setOnMouseClicked(e -> {
			click(tess1,"Tess");
		});
		Henryh.setOnMouseClicked(e -> {
			click(henry1,"Henry Burell");
		});
		Joelh.setOnMouseClicked(e -> {
			click(joel1,"Joel Miller");
		});
		Tommyh.setOnMouseClicked(e -> {
			click(tommy1,"Tommy Miller");
		});
		Rileyh.setOnMouseClicked(e -> {
			click(riley1,"Riley Abel");
		});
		Davidh.setOnMouseClicked(e -> {
			click(david1,"David");
		});
	}

	private void click(ImageView im,String text) {
		if (!setTargetOn) {
			checkIfSelected();
			im.setVisible(true);
			Selected = text;
			selectedData.setVisible(true);
			selectedData.setText(create_text2(Selected));
			updateHealthBar();
		}
	}

	private void checkMove(ImageView arrowR, ImageView arrowL, ImageView arrowU, ImageView arrowD) {
		arrowU.setOnMouseClicked(e -> moveExc(Direction.UP));
		arrowD.setOnMouseClicked(e -> moveExc(Direction.DOWN));
		arrowL.setOnMouseClicked(e -> moveExc(Direction.LEFT));
		arrowR.setOnMouseClicked(e -> moveExc(Direction.RIGHT));
	}

	private void moveExc(Direction direction) {
		if (!errorWindOn) {
			try {
				int prevhp = getHeroClicked().getCurrentHp();
				getHeroClicked().move(direction);
				if (prevhp != getHeroClicked().getCurrentHp()) {
					MainMenu.mediaPlayer.setVolume(0.3);
					;
					soundeffect = createAudio("trapsound.mp4");
					soundeffect.play();
					soundeffect.setOnEndOfMedia(() -> {
						MainMenu.mediaPlayer.setVolume(1);
						messageLabel = new Button("You fell into a trap!");
						messageLabel.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 50));
						messageLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
						messageLabel.setTextFill(Color.WHITE);
						messageLabel.setVisible(true);
						((StackPane) this.getRoot()).getChildren().add(messageLabel);
						PauseTransition pause = new PauseTransition(Duration.seconds(1));
						pause.setOnFinished(e -> {
							messageLabel.setVisible(false);

						});
						pause.play();

					});
				}
				map_display();
				updateText();
				updateHealthBar();
				if (Game.checkGameOver())
					GameEnded();
			} catch (GameActionException e) {
				throwError(e);
			}
		}
	}

	private void throwError(GameActionException e) {
		if (!errorAlreadyDisplayed) {
			errorAlreadyDisplayed = true;
			errorWindOn = true;
			StackPane sp = new StackPane();
			Label label = new Label(e.getMessage());
			label.setAlignment(Pos.CENTER);
			sp.getChildren().add(label);
			Scene scene = new Scene(sp, 300, 150);
			popUpStage.setTitle("Take Care!");
			popUpStage.setScene(scene);
			popUpStage.show();
			popUpStage.setOnCloseRequest(o -> {
				errorAlreadyDisplayed = false;
				errorWindOn = false;
			});
		}
	}

	public void map_display() {
		map.getChildren().clear();
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				Rectangle rectangle = new Rectangle(66, 58);
				map.add(rectangle, col, 14 - row);
				rectangle.setStrokeWidth(0.2);
				rectangle.setStroke(Color.WHEAT); // Color row 2
				if (Game.map[row][col].isVisible())
					rectangle.setFill(Color.rgb(0, 0, 0, 0.1));
				else
					rectangle.setFill(Color.rgb(0, 0, 0, 0.7));

			}
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Cell cell = Game.map[i][j];
				if (cell.isVisible()) {
					if (cell instanceof CharacterCell) {
						if (((CharacterCell) cell).getCharacter() instanceof Hero) {
							Hero h = (Hero) ((CharacterCell) cell).getCharacter();
							switch (h.getName()) {
							case "Joel Miller":
								map.add(Joel, j, 14 - i);
								break;
							case "Tess":
								map.add(Tess, j, 14 - i);
								break;
							case "Bill":
								map.add(Bill, j, 14 - i);
								break;
							case "Ellie Williams":
								map.add(Ellie, j, 14 - i);
								break;
							case "Henry Burell":
								map.add(Henry, j, 14 - i);
								break;
							case "Riley Abel":
								map.add(Riley, j, 14 - i);
								break;
							case "David":
								map.add(David, j, 14 - i);
								break;
							case "Tommy Miller":
								map.add(Tommy, j, 14 - i);
								break;
							}
						} else if (((CharacterCell) cell).getCharacter() instanceof Zombie) {
							ImageView z = create_image("Zombie.jpeg");
							map.add(z, j, 14 - i, 1, 1);
							if (getHeroClicked().getTarget() != null) {
								if (getHeroClicked().getTarget().getLocation().x == i
										&& getHeroClicked().getTarget().getLocation().y == j) {
									targetSelected = z;
									targetSelected.setEffect(d);
								}
							}

						}
					} else if (cell instanceof CollectibleCell) {
						if (((CollectibleCell) cell).getCollectible() instanceof Vaccine) {
							ImageView v = create_Supplies("syringe.png");
							GridPane.setMargin(v, new Insets(0, 0, 0, 5));
							map.add(v, j, 14 - i);
						} else {
							ImageView iview = new ImageView(new Image("backpack.png"));
							iview.setFitWidth(66);
							iview.setFitHeight(52);
							iview.setRotate(30);
							map.add(iview, j, 14 - i);
						}
					}
				}

			}
		}

	}

	public static void place(Node node, Insets position) {
		StackPane.setMargin(node, position);
	}

	public static ImageView create_image(String x) {
		ImageView iview = new ImageView(new Image(x));
		iview.setFitWidth(66);
		iview.setFitHeight(52);
		return iview;
	}

	public static ImageView create_image1(String x) {
		ImageView iview = new ImageView(new Image(x));
		iview.setFitWidth(150);
		iview.setFitHeight(120);
		return iview;
	}

	public static Hero getHeroClicked() {
		for (Hero h : Game.heroes) {
			if (h.getName().equals(Selected))
				return h;
		}
		return null;
	}

	public ImageView create_Supplies(String x) {
		ImageView iview = new ImageView(new Image(x));
		iview.setFitWidth(55);
		iview.setFitHeight(40);
		return iview;
	}

	public void checkIfSelected() {
		if (MainGame.Selected != null)
			unSelect();
	}

	public void unSelect() {
		if (targetSelected != null)
			targetSelected.setEffect(null);
		switch (MainGame.Selected) {
		case "Joel Miller":
			joel1.setVisible(false);
			break;
		case "Tess":
			tess1.setVisible(false);
			break;
		case "Bill":
			bill1.setVisible(false);
			break;
		case "Ellie Williams":
			ellie1.setVisible(false);
			break;
		case "Henry Burell":
			henry1.setVisible(false);
			break;
		case "Riley Abel":
			riley1.setVisible(false);
			break;
		case "David":
			david1.setVisible(false);
			break;
		case "Tommy Miller":
			tommy1.setVisible(false);
			break;
		}

	}

	public void updateText() {
		selectedData.setVisible(true);
		selectedData.setText(create_text2(Selected));

	}

	public static String create_text2(String name) {
		Hero h = null;
		for (Hero hero : Game.heroes) {
			if (hero.getName().equals(name)) {
				h = hero;
				break;
			}
		}
		String type;
		if (h instanceof Fighter)
			type = "Fighter";
		else if (h instanceof Medic)
			type = "Medic";
		else
			type = "Explorer";

		int numVaccines = h.getVaccineInventory().size();
		int numSupplies = h.getSupplyInventory().size();

		return "Name: " + h.getName() + "\n\nType: " + type + "\n\nCurrent Hp: " + h.getCurrentHp() + "/" + h.getMaxHp()
				+ "\n\nCurrent Actions: " + h.getActionsAvailable() + "/" + h.getMaxActions() + "\n\nAttack Damage: "
				+ h.getAttackDmg() + "\n\nVaccines: " + numVaccines + "\n\nSupplies: " + numSupplies;
	}

	public static String create_text3(String name) {
		Hero h = null;
		for (Hero hero : Game.heroes) {
			if (hero.getName().equals(name)) {
				h = hero;
				break;
			}
		}
		String type;
		if (h instanceof Fighter)
			type = "Fighter";
		else if (h instanceof Medic)
			type = "Medic";
		else
			type = "Explorer";
		int numVaccines = h.getVaccineInventory().size();
		int numSupplies = h.getSupplyInventory().size();
		String hname = h.getName();

		if (hname.equals("Bill") || hname.equals("Tess"))
			hname += "                              Type:";
		else if (hname.equals("David"))
			hname += "                         Type:";
		else
			hname += "\t\t\tType";

		return "Name: " + hname + type + "\n\nCurrent Hp: " + h.getCurrentHp() + "/" + h.getMaxHp()
				+ "\t\t\tCurrent Actions: " + h.getActionsAvailable() + "/" + h.getMaxActions() + "\n\nAttack Damage: "
				+ h.getAttackDmg() + "\t\t\tVaccines: " + numVaccines + "\n\nSupplies: " + numSupplies;
	}

	public void updateHealthBar() {
		healthbar.setVisible(true);
		healthp.setVisible(true);
		double ppar = ((double) getHeroClicked().getCurrentHp() / getHeroClicked().getMaxHp());
		int p = (int) (ppar * 100);
		healthbar.setProgress(ppar);
		healthp.setText(p + "%");

		// Adjust the fill color of the health bar based on the currentHP
		if (getHeroClicked().getCurrentHp() <= getHeroClicked().getMaxHp() / 4) {
			healthbar.setStyle("-fx-accent: #CC5500;");

		} else if (getHeroClicked().getCurrentHp() <= getHeroClicked().getMaxHp() / 2) {
			healthbar.setStyle("-fx-accent: #FFA500;");

		} else {
			healthbar.setStyle("-fx-accent: #228B22;");

		}

	}

	public void GameEnded() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
		pause.setOnFinished(e -> {
			Text state = new Text();
			state.setFont(Font.loadFont(getClass().getResourceAsStream("OhTheHorror.ttf"), 50));
			state.setFill(Color.WHITE);
			if (Game.checkWin()) {
				state.setFill(Color.GREEN);
				state.setText("you survived the apocalypse!\n\n                YOU WON!");

			} else {
				state.setFill(Color.RED);
				state.setText("You couldn't escape the\n\nclutches of the undead\n\n          DEFEATED");
			}
			BoxBlur blur = new BoxBlur(10, 10, 3);
			origin.setEffect(blur);
			((StackPane) this.getRoot()).getChildren().add(state);
			TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), state);

			state.setTranslateY(400);

			translateTransition.setToY(0);

			translateTransition.play();

			endTurn.setDisable(true);
			attackButton.setDisable(true);
			useSpecialButton.setDisable(true);
			cureButton.setDisable(true);
			targetButton.setDisable(true);
			map.setDisable(true);
			arrowU.setDisable(true);
			arrowR.setDisable(true);
			arrowL.setDisable(true);
			arrowD.setDisable(true);
		});
		pause.play();

	}

	public MediaPlayer createAudio(String m) {
		return new MediaPlayer(new Media(new File(m).toURI().toString()));
	}
}
