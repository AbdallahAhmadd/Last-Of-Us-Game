package GUI;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu extends Application {
	private Text percentagetext;
	private ProgressBar loadingBar;
	private Text title=new Text("Press Anywhere to Start Game");;
	public static MediaPlayer mediaPlayer;
	public Image unmute= new Image("audio-speaker-on.png");;
	public Image mute= new Image("no-sound.png");
	public ImageView sound= new ImageView(unmute);
	public Scene choosePlayer;
	public boolean alreadyLoading = false; // indicates the state of the mute button
	public Font customFont=Font.loadFont(getClass().getResourceAsStream("OhTheHorror.ttf"), 30);
	public StackPane elboss = new StackPane();

	@Override
	public void init() throws Exception {	
		// Text of start game
				title.setFill(Color.web("#6e0202"));
				title.setFont(customFont);
		// setting the stackPaneSize;
				elboss.setMaxSize(100, 100);
		// setting sound size
				sound.setFitWidth(50);
				sound.setFitHeight(50);
		// percentage on loading bar
				percentagetext = new Text();
				percentagetext.setFill(Color.BLACK);
				Font customFont1 = Font.loadFont(getClass().getResourceAsStream("AC-Compacta.ttf"), 18);
				percentagetext.setFont(customFont1);
		// loading bar
				loadingBar = new ProgressBar(0);
				loadingBar.setPrefWidth(300);
				loadingBar.setVisible(false);
				loadingBar.setStyle("-fx-accent: #6e0202;");	
	}

	public void start(Stage primary) {
		choosePlayer=new ChoosePlayer();
	
		// background image
		Image backgroundImage = new Image("Main_Menu2.jpeg");
		
		BackgroundSize backgroundSize = new BackgroundSize(3024, 1964, true, true, true, true); // Adjust the values as																							     	// needed
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background backgroundObject = new Background(background);
		
		// setting the holders and its children alignment
		StackPane holder = new StackPane();
		holder.setAlignment(Pos.BOTTOM_CENTER); 
		place(title, new Insets(0, 0, 50, 0));
		place(sound, new Insets(0, 0, 810, 1220));
		place(loadingBar, new Insets(0, 0, 60, 0));
		place(percentagetext, new Insets(0, 0, 62, 0));
		holder.setBackground(backgroundObject);
		holder.getChildren().addAll(title, loadingBar, percentagetext, sound);

		elboss.getChildren().addAll(choosePlayer.getRoot(),holder);
		Scene scene = new Scene(elboss,3024, 1964);

		// background music
		Media media = new Media(new File("THLOU_piano.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(() -> {
			mediaPlayer.seek(javafx.util.Duration.ZERO);
			mediaPlayer.play();
		});

		// fade in text
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), title);
		fadeIn.setFromValue(0.3);
		fadeIn.setToValue(1.0);

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), title);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.3);

		SequentialTransition fadeSequence = new SequentialTransition(fadeIn, fadeOut);
		fadeSequence.setCycleCount(10000);

		// Click anywhere
		scene.setOnMouseClicked((e) -> {
			// I have 2 cases:-
			// i)I either click on the sound Button ->Mute/UnMute
			// ii)I click anywhere else on the screen
			if (e.getTarget() == sound) {
				if (mediaPlayer.isMute()) {
					sound.setImage(unmute);
					mediaPlayer.setMute(false);
				} else {
					sound.setImage(mute);
					mediaPlayer.setMute(true);
					
				}
			} else {
				if (!alreadyLoading) {
					startLoading(holder);
					alreadyLoading = true;

				}
			}

		});

		scene.setOnKeyPressed((e) -> {
			if (!alreadyLoading && e.getCode() != KeyCode.ESCAPE) {
				startLoading(holder);
				alreadyLoading = true;

			}

		});

		primary.setTitle("The LAST OF US LEGACY");
		primary.setScene(scene);
		
		primary.setFullScreen(true);
		primary.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		
		primary.setResizable(true);
		primary.show();
		
		fadeSequence.play();
		primary.setOnCloseRequest(e->{
			e.consume();
			Platform.exit();
		});
	}

	// loading bar method
	private void startLoading(StackPane holder) {

		title.setVisible(false);
		loadingBar.setProgress(0);
		loadingBar.setVisible(true);
		percentagetext.setVisible(true);

		new Thread(() -> {
			try {
				for (int i = 0; i <= 100; i++) {
					double progress = i / 100.0;
					int percentage = (int) (progress * 100);
					javafx.application.Platform.runLater(() -> {
						loadingBar.setProgress(progress);
						percentagetext.setText(percentage + "%");
					});
					Thread.sleep(40);
				}
				// End of for loop

				javafx.application.Platform.runLater(() -> {

					FadeTransition fadeOut = new FadeTransition(Duration.millis(3500), holder);
					fadeOut.setFromValue(10.0);
					fadeOut.setToValue(0.0);
					fadeOut.setOnFinished(event -> holder.setVisible(false));
					fadeOut.play();

				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	public static void place(Node node,Insets position) {
		StackPane.setMargin(node, position);
	}
	public static void main(String[] args) {
		launch(args);

	}
	

	
}
