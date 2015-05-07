package br.unirondon.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import br.unirondon.values.AppConfig;
import br.unirondon.view.NewProjectView;
import br.unirondon.view.xml.Binary;
import br.unirondon.view.xml.Project;
import br.unirondon.view.xml.Source;

public class NewProjectController implements Initializable {

	@FXML
	private TextField fieldNewProject;

	@FXML
	private Button btnSalvarNewProject, btnCancelarNewProject;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		fieldNewProject.requestFocus();
	}

	@FXML
	private void btnSalvarNewProjectOnAction(ActionEvent event) {
		if (!fieldNewProject.getText().isEmpty()) {
			for (int i = 0; i < MainViewController.getConfig().getDirectories().size(); i++) {
				if(MainViewController.getConfig().getDirectories().get(i).isSelected()) {
					List<Source> srcs = new ArrayList<Source>();
					List<Binary> bins = new ArrayList<Binary>();
					
					srcs.add(new Source(AppConfig.getPropertie("App.projectSrc")));
					bins.add(new Binary(AppConfig.getPropertie("App.projectBin")));
					
					Project project = new Project(fieldNewProject.getText().trim(), srcs, bins);
					
					MainViewController.getConfig().getDirectories().get(i).getProjects().add(project);
					
					new File(MainViewController.getConfig().getDirectories().get(i).getDirectoryPath() +
							AppConfig.getSymbolString("division") + project.getName() + project.getSrcs().get(0)).mkdirs();
					new File(MainViewController.getConfig().getDirectories().get(i).getDirectoryPath() +
							AppConfig.getSymbolString("division") + project.getName() + project.getBins().get(0)).mkdirs();
				}
			}
			
			NewProjectView.close();
			showDialogInformation();
			AppConfig.writeXML(MainViewController.getConfig());
			MainViewController.buildingTreeProjects();
		} else {
			showDialogInformation();
		}
	}

	@FXML
	private void btnCancelarNewProjectOnAction(ActionEvent event) {
		if(showDialogConfirm()) {
			NewProjectView.close();
		}
	}

	private void showDialogInformation() {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setHeaderText(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setContentText(AppConfig.getPropertie("Dialogs.conclusion"));

		alert.show();
	}

	private boolean showDialogConfirm() {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setHeaderText(AppConfig.getPropertie("Dialogs.titleInformation"));
		alert.setContentText(AppConfig.getPropertie("Dialogs.outInformation"));

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
			return true;
		else
			return false;
	}

}
