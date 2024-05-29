package GUI;

import java.io.File;
import java.io.IOException;

import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

public class ChoosePlayer extends Scene {

	private static Scene maingame;
	private static Scene GameEnded;
	public ImageView tess = create_image("TessP.png");
	public ImageView riley = create_image("RileyP.png");
	public ImageView tommy = create_image("TommyP.png");
	public ImageView david = create_image("davidP.png");
	public ImageView ellie = create_image("elllieP.png");
	public ImageView joel = create_image("joelP.png");
	public ImageView bill = create_image("billP.png");
	public ImageView henry = create_image("henryP.png");
	public ImageView parchment= new ImageView(new Image("parchment.png"));;
	public MediaPlayer hover=createAudio();
	public boolean isClicked = false;
	public Label text=new Label();
	public static DropShadow highlightEffect = new DropShadow();
	public ChoosePlayer() {
		super(new StackPane(), 3024, 1964);
		StackPane PlayerStack = new StackPane();
		Image background = new Image("blured.png");
		BackgroundSize backgroundSize = new BackgroundSize(3024, 1964, true, true, true, true);
		BackgroundImage background1 = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);

		Background backgroundObject = new Background(background1);
		PlayerStack.setBackground(backgroundObject);

		parchment.setFitWidth(800);
		parchment.setFitHeight(600);
		
		place(parchment, new Insets(0, 0, 330, 1000));
		place(text, new Insets(0,0,330,1005));
		parchment.setVisible(false);
		text.setVisible(false);
		text.setFont(Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 40));
		
		place(tommy, new Insets(0, 1210, 700, 0));
		place(tess, new Insets(0, 1210, 0, 0));
		place(riley, new Insets(700, 1210, 0, 0));
		place(david, new Insets(0, 600, 350, 0));
		place(joel, new Insets(350, 600, 0, 0));
		place(ellie, new Insets(0, 0, 700, 0));
		place(bill, new Insets(700, 0, 0, 0));
		
		
		try {
			Game.loadHeroes("/Users/farah/Documents/CSEN 401/Game/Milestone 1/Heros.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Label label = new Label("START GAME");
		label.setFont(Font.loadFont(getClass().getResourceAsStream("OhTheHorror.ttf"), 40));
		label.setTextFill(Color.RED);
		place(label, new Insets(600, 0, 0, 1000));
		checkEntered();
		checkClicked();
		StackPane elboss2=(StackPane) this.getRoot();
		elboss2.getChildren().add(PlayerStack);
		
		
		highlightEffect.setColor(Color.WHITE);
		highlightEffect.setWidth(30);
		highlightEffect.setHeight(30);
		highlightEffect.setRadius(40);

		
		
		label.setOnMouseClicked(e->{
			if (MainGame.Selected==null)
			{
				animate(label);
				StackPane s=new StackPane();
				Label l=new Label("You must select a hero to play with!");
				l.setAlignment(Pos.CENTER);
				s.getChildren().add(l);
				Scene sc=new Scene(s, 300,150);
				Stage popUp=new Stage();
				popUp.initStyle(StageStyle.UTILITY);
				popUp.setTitle("Take Care!");
				popUp.setScene(sc);
				popUp.show();
				
			}
			else {
			animate(label);
			PauseTransition pause=new PauseTransition(Duration.seconds(0.5));
			pause.setOnFinished(e1->{
				Game.startGame(getHeroClicked());
				maingame=new MainGame();
				elboss2.getChildren().clear();
				elboss2.getChildren().addAll(maingame.getRoot(),PlayerStack);
				FadeTransition fadeOut = new FadeTransition(Duration.millis(2500), PlayerStack);
				fadeOut.setFromValue(10.0);
				fadeOut.setToValue(0.0);
				fadeOut.setOnFinished(event ->PlayerStack.setVisible(false));
				fadeOut.play();
			});
			pause.play();
			}
			
			
			
			
		});
		
		PlayerStack.getChildren().addAll(tess, riley, tommy, david, ellie, joel, bill, henry, label, parchment,text);
		
		
	}
	
	public Hero getHeroClicked() {
		for (Hero h : Game.availableHeroes) {
			if (h.getName().equals(MainGame.Selected))
				return h;
		}
		return null;
	}
	
	public void checkEntered() {
		tommy.setOnMouseEntered(e -> {
			tommy.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Tommy Miller"));
			text.setVisible(true);
			
			tommy.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Tommy Miller"))
						tommy.setEffect(null);
				}
				else {
					tommy.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		tess.setOnMouseEntered(e -> {
			tess.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Tess"));
			text.setVisible(true);
			tess.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Tess"))
						tess.setEffect(null);
				}
				else
				{
					tess.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		david.setOnMouseEntered(e -> {
			david.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("David"));
			text.setVisible(true);
			david.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("David"))
						david.setEffect(null);
				}
				else {
					david.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		joel.setOnMouseEntered(e -> {
			joel.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Joel Miller"));
			text.setVisible(true);
			joel.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Joel Miller"))
						joel.setEffect(null);
				}
				else {
					joel.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		ellie.setOnMouseEntered(e -> {
			ellie.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Ellie Williams"));
			text.setVisible(true);
			ellie.setOnMouseExited(o -> {
				ellie.setEffect(null);
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Ellie Williams"))
						ellie.setEffect(null);
				}
				else {
					ellie.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		henry.setOnMouseEntered(e -> {
			henry.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Henry Burell"));
			text.setVisible(true);
			henry.setOnMouseExited(o -> {
				henry.setEffect(null);
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Henry Burell"))
						henry.setEffect(null);
				}
				else {
					henry.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		bill.setOnMouseEntered(e -> {
			bill.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Bill"));
			text.setVisible(true);
			bill.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Bill"))
						bill.setEffect(null);
				}
				else {
					bill.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
		riley.setOnMouseEntered(e -> {
			riley.setEffect(highlightEffect);
			hover.play();
			hover=createAudio();
			parchment.setVisible(true);
			text.setText(create_text("Riley Abel"));
			text.setVisible(true);
			riley.setOnMouseExited(o -> {
				if (isClicked) {
					text.setText(create_text(MainGame.Selected));
					FrameSelected(highlightEffect);
					if (!MainGame.Selected.equals("Riley Abel"))
						riley.setEffect(null);
				}
				else {
					riley.setEffect(null);
					parchment.setVisible(false);
					text.setVisible(false);
				}
			});

		});
	}

	public void checkClicked() {
		ellie.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Ellie Williams";
			isClicked = true;
		});
		bill.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Bill";
			isClicked = true;

		});
		tess.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Tess";
			isClicked = true;
		});
		henry.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Henry Burell";
			isClicked = true;
		});
		joel.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Joel Miller";
			isClicked = true;
		});
		tommy.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Tommy Miller";
			isClicked = true;
		});
		riley.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "Riley Abel";
			isClicked = true;
		});
		david.setOnMouseClicked(e -> {
			checkIfSelected();
			MainGame.Selected = "David";
			isClicked = true;
		});
	}

	public void checkIfSelected() {
		if (MainGame.Selected!=null)
			FrameSelected(null);
	}
	
	public void FrameSelected(Effect highlightEffect) {
		switch(MainGame.Selected) {
		case "Joel Miller":joel.setEffect(highlightEffect); break;
		case "Tess":tess.setEffect(highlightEffect); break;
		case "Bill": bill.setEffect(highlightEffect);break;
		case "Ellie Williams": ellie.setEffect(highlightEffect);;break;
		case "Henry Burell": henry.setEffect(highlightEffect);break;
		case "Riley Abel": riley.setEffect(highlightEffect); break;
		case "David": david.setEffect(highlightEffect);break;
		case "Tommy Miller":tommy.setEffect(highlightEffect);break;
		}
	}
	
	public static ImageView create_image(String x) {
		ImageView iview = new ImageView(new Image(x));
		iview.setFitWidth(250);
		iview.setFitHeight(200);
		return iview;
	}

	public static String create_text(String name) {
		Hero h = null;
		for (Hero hero : Game.availableHeroes) {
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

		return "Name: " + h.getName() + "\n\nType: " + type + "\n\nMax Hp: "+ h.getMaxHp() +
				"\n\nMax Actions: " +h.getMaxActions() + "\n\nAttack Damage: " + h.getAttackDmg();
	}
	
	public MediaPlayer createAudio() {
		return new MediaPlayer(new Media(new File("hoverr.mp3").toURI().toString()));
	}
	private void animate(Label image) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(image.scaleXProperty(), 1.0)),
				new KeyFrame(Duration.ZERO, new KeyValue(image.scaleYProperty(), 1.0)),
				new KeyFrame(Duration.seconds(0.3), new KeyValue(image.scaleXProperty(), 1.1)),
				new KeyFrame(Duration.seconds(0.3), new KeyValue(image.scaleYProperty(), 1.1)),
				new KeyFrame(Duration.seconds(0.6), new KeyValue(image.scaleXProperty(), 1)),
				new KeyFrame(Duration.seconds(0.6), new KeyValue(image.scaleYProperty(), 1)));

		// Set animation properties
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		// Play the animation
		timeline.play();
	}

	public static void place(Node node,Insets position) {
		StackPane.setMargin(node, position);
	}
}