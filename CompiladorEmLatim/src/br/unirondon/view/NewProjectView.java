package br.unirondon.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import br.unirondon.exception.BasicException;
import br.unirondon.values.AppConfig;

public class NewProjectView extends Application {

	private AnchorPane pane;
	private Scene scene;
	private static Stage stage;

	@Override
	@SuppressWarnings("static-access")
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		initNewProjectView();
	}

	private void initNewProjectView() {
		try {
			this.pane = (AnchorPane) FXMLLoader.load(getClass().getResource(
					AppConfig.getPropertie("App.newProjectFXML")));
			this.scene = new Scene(pane);

			stage.setTitle(AppConfig.getPropertie("App.newProjectTitle"));
			stage.setScene(scene);
			stage.getIcons().add(new Image(AppConfig.getPropertie("App.mainIcon")));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNIFIED);

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
	
	public static boolean isShowing() {
		return stage.isShowing();
	}

}
