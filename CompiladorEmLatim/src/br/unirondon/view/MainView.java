package br.unirondon.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import br.unirondon.exception.BasicException;
import br.unirondon.values.AppConfig;

public class MainView extends Application {

	private static Stage stage;
	private AnchorPane pane;
	private Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		
		initMainView();
	}

	private void initMainView() {
		try {
			this.pane = (AnchorPane) FXMLLoader.load(getClass().getResource(AppConfig.getPropertie("App.mainFXML")));
			this.scene = new Scene(pane);

			getStage().setTitle(AppConfig.getPropertie("App.mainTitle"));
			getStage().setScene(scene);
			getStage().getIcons().add(new Image(AppConfig.getPropertie("App.mainIcon")));
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageIOException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	public static Stage getStage() {
		return stage;
	}

	private static void setStage(Stage stage) {
		MainView.stage = stage;
	}
	
	public static void showMainView() {
		getStage().show();
		getStage().centerOnScreen();
		getStage().setMaximized(true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
