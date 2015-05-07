package br.unirondon.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import br.unirondon.values.AppConfig;

public class BasicException extends Exception {

	private static final long serialVersionUID = 1L;

	public BasicException(String msg) {
		super(msg);
	}

	public BasicException(String msg, Throwable e) {
		super(msg, e);
	}

	public void showExceptioDialog(String title, String header, String content,
			String was, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(was);

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		
		Stage otherStage = (Stage) alert.getDialogPane().getScene().getWindow();
		otherStage.getIcons().add(new Image(this.getClass().getResourceAsStream(AppConfig.getPropertie("App.mainIcon"))));

		alert.showAndWait();
	}

}
