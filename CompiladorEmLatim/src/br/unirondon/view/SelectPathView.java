package br.unirondon.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import br.unirondon.exception.BasicException;
import br.unirondon.values.AppConfig;

public class SelectPathView extends Application {

	private AnchorPane pane;
	private Scene scene;
	private static Stage stage;
	
	@Override
	@SuppressWarnings("static-access")
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initSelectPathView();
	}

	private void initSelectPathView() {
		try {
			this.pane = (AnchorPane) FXMLLoader.load(getClass().getResource(AppConfig.getPropertie("App.selectPathFXML")));
			this.scene = new Scene(pane);

			stage.setTitle(AppConfig.getPropertie("App.selectPathTitle"));
			stage.setScene(scene);
			stage.getIcons().add(new Image(AppConfig.getPropertie("App.mainIcon")));
			stage.initStyle(StageStyle.UNDECORATED);

			stage.show();
			stage.centerOnScreen();
			stage.setResizable(false);
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageIOException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	public static void close() {
		stage.close();
	}
	
}
