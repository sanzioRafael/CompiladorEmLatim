package br.unirondon.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import br.unirondon.values.AppConfig;
import br.unirondon.view.MainView;
import br.unirondon.view.SelectPathView;
import br.unirondon.view.xml.Directory;

public class SelectPathController implements Initializable {

	protected static long INTERVAL = 30;

	@FXML
	private TextField fieldDirectoryPath;

	@FXML
	private Button btnSelectPath, btnConfirmarSelctPath, btnCancelarSelctPath;

	@FXML
	private ProgressBar progressBarSelectPath;

	@FXML
	private Label lbProgressBar;

	private DirectoryChooser chooser;
	private File selectedDirectory;
	private Task<Void> directoryTask;
	private Directory directorySelected;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		fieldDirectoryPath.setFocusTraversable(false);
		progressBarSelectPath.setVisible(false);
		lbProgressBar.setVisible(false);
		btnSelectPath.requestFocus();

		chooser = new DirectoryChooser();
		directorySelected = new Directory();

		if (AppConfig.readXML() != null) {
			MainViewController.setConfig(AppConfig.readXML());
			
			for (Directory d : MainViewController.getConfig().getDirectories()) {
				if(d.isSelected()) {
					directorySelected = d;
					break;
				}
			}
			
			fieldDirectoryPath.setText(directorySelected.getDirectoryPath());
			selectedDirectory = new File(directorySelected.getDirectoryPath());
		} else
			MainViewController.getConfig().setDirectories(new ArrayList<Directory>());
		
		chooser.setTitle(AppConfig.getPropertie("File.directoryChooserTitle"));
		chooser.setInitialDirectory(new File(AppConfig.getPropertie("File.defaultDirectory")));
		
		progressBarSelectPath.setProgress(0);
		directoryTask = createWorker();
		
		progressBarSelectPath.progressProperty().unbind();
		progressBarSelectPath.progressProperty().bind(directoryTask.progressProperty());
		
		directoryTask.messageProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				lbProgressBar.setText("Inicializando Compilador: " + newValue + "\n");

				if(newValue.equalsIgnoreCase("100%")) {
					SelectPathView.close();
					MainView.showMainView();
				}
			}
			
		});
	}

	@FXML
	private void btnSelectPathOnAction(ActionEvent event) {
		selectedDirectory = chooser.showDialog(MainView.getStage());

		if (selectedDirectory != null) {
			fieldDirectoryPath.setText(selectedDirectory.getAbsolutePath());
		}
	}

	@FXML
	private void btnConfirmarSelctPathOnAction(ActionEvent event) {
		if (selectedDirectory != null && !fieldDirectoryPath.getText().isEmpty()) {
			lbProgressBar.setVisible(true);
			progressBarSelectPath.setVisible(true);
			
			new Thread(directoryTask).start();
			
			if(MainViewController.getConfig().getDirectories().size() > 0) {
				INTERVAL *= MainViewController.getConfig().getDirectories().size();
				INTERVAL += 10;
				boolean exists = false;
				int j = 0;
				
				for (int i = 0; i < MainViewController.getConfig().getDirectories().size(); i++) {
					if(MainViewController.getConfig().getDirectories().get(i).getDirectoryPath().equalsIgnoreCase(fieldDirectoryPath.getText())) {
						exists = true;
						j = i;
					}
					MainViewController.getConfig().getDirectories().get(i).setSelected(false);
				}
				
				if(exists) {
					MainViewController.getConfig().getDirectories().get(j).setSelected(exists);
					AppConfig.writeXML(MainViewController.getConfig());
				} else {
					directorySelected = new Directory();
					
					directorySelected.setDirectoryPath(fieldDirectoryPath.getText());
					directorySelected.setSelected(true);
					
					MainViewController.getConfig().getDirectories().add(directorySelected);
					
					AppConfig.writeXML(MainViewController.getConfig());
				}
			} else {
				INTERVAL += 10;
				directorySelected = new Directory();
				
				directorySelected.setDirectoryPath(fieldDirectoryPath.getText());
				directorySelected.setSelected(true);
				
				MainViewController.getConfig().getDirectories().add(directorySelected);
				
				AppConfig.writeXML(MainViewController.getConfig());
			}
		} else {
			showDialogWarning();
		}
	}

	@FXML
	private void btnCancelarSelctPathOnAction(ActionEvent event) {
		if (AppConfig.readXML() != null) {
			System.exit(0);
		} else {
			if (showDialogInformation())
				System.exit(0);
		}
	}
	
	private boolean showDialogInformation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setHeaderText(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setContentText(AppConfig.getPropertie("Dialogs.outInformation"));

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
			return true;
		else
			return false;
	}

	private void showDialogWarning() {
		Alert alert = new Alert(AlertType.WARNING);

		alert.setTitle(AppConfig.getPropertie("Dialogs.titleWarning"));
		alert.setHeaderText(AppConfig.getPropertie("Dialogs.masterheadWarning"));
		alert.setContentText(AppConfig.getPropertie("Dialogs.selectDirectoryWarning"));

		alert.showAndWait();
	}
	
	private Task<Void> createWorker() {
		return new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(INTERVAL);
					updateMessage((i + 1) + "%");
					updateProgress(i + 1, 100);
				}
				
				return null;
			}
			
		};
	}
	
}
