package br.unirondon.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import br.unirondon.compiler.Compiler;
import br.unirondon.exception.BasicException;
import br.unirondon.interfaces.CompileOnActions;
import br.unirondon.values.AppConfig;
import br.unirondon.view.MainView;
import br.unirondon.view.NewProjectView;
import br.unirondon.view.SelectPathView;
import br.unirondon.view.xml.Directory;
import br.unirondon.view.xml.Project;
import br.unirondon.view.xml.Source;
import br.unirondon.view.xml.UsuarioConfig;

public class MainViewController implements Initializable, CompileOnActions {

	@FXML
	private MenuItem miNovoProjeto, miNovoLoba, miSair;

	@FXML
	private TabPane tabPaneEditorText;

	@FXML
	private Tab tabProject;

	@FXML
	private Button btnSaveLobaFile, btnRunLoba;

	@FXML
	public TextArea txtAreaConsole;

	private static UsuarioConfig config;
	private static List<TextArea> editorFields;
	private SelectPathView selectPathView;
	private NewProjectView newProjectView;
	private static TreeItem<String> treeProjects;
	private TreeView<String> treeView;
	private static ImageView sourceIcon;
	private static ImageView emptyFolderIcon;
	private static ImageView folderIcon;
	private static ImageView projectIcon;
	private ImageView projecstIcon;
	private ImageView saveLoba;
	private ImageView runLoba;
	private static List<Project> projects;
	private File lobaSource;
	private Compiler compiler;

	@Override
	public void initialize(URL url, ResourceBundle recourceBundle) {
		try {
			AppConfig.checkFileConfigExits();
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageIOException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}

		config = (AppConfig.getConfigXML().length() == 0l) ? new UsuarioConfig() : AppConfig.readXML();
		sourceIcon = new ImageView(AppConfig.getPropertie("App.mainSourceLoba"));
		emptyFolderIcon = new ImageView(AppConfig.getPropertie("App.mainEmptyFolder"));
		folderIcon = new ImageView(AppConfig.getPropertie("App.mainFolder"));
		projectIcon = new ImageView(new Image(AppConfig.getPropertie("App.mainNewProjectIcon")));
		projecstIcon = new ImageView(new Image(AppConfig.getPropertie("App.mainProjectsIcon")));
		saveLoba = new ImageView(new Image(AppConfig.getPropertie("App.mainSaveLobaIcon")));
		runLoba = new ImageView(new Image(AppConfig.getPropertie("App.mainRunLobaIcon")));
		treeProjects = new TreeItem<String>(AppConfig.getPropertie("App.mainProjects"), projecstIcon);
		treeView = new TreeView<String>(treeProjects);

		tabProject.setContent(treeView);

		if (selectDirectoryPath()) {
			editorFields = new ArrayList<TextArea>();

			miNovoProjeto.setGraphic(projectIcon);
			miNovoLoba.setGraphic(sourceIcon);
			btnSaveLobaFile.setGraphic(saveLoba);
			btnRunLoba.setGraphic(runLoba);

			btnSaveLobaFile.setText("");
			btnRunLoba.setText("");

			buildingTreeProjects();
		}
	}

	protected static UsuarioConfig getConfig() {
		return config;
	}

	protected static void setConfig(UsuarioConfig config) {
		MainViewController.config = config;
	}

	private boolean selectDirectoryPath() {
		try {
			selectPathView = new SelectPathView();
			selectPathView.start(new Stage(MainView.getStage().getStyle()));
		} catch (Exception e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}

		return true;
	}

	private void newProject() {
		try {
			newProjectView = new NewProjectView();
			newProjectView.start(new Stage(MainView.getStage().getStyle()));
		} catch (Exception e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	@FXML
	private void onActionMiNovoProjeto(ActionEvent event) throws Exception {
		List<Project> projects = new ArrayList<Project>();
		for (int i = 0; i < config.getDirectories().size(); i++) {
			if (config.getDirectories().get(i).isSelected()) {
				if (config.getDirectories().get(i).getProjects() == null) {
					config.getDirectories().get(i).setProjects(projects);
				} else {
					projects = config.getDirectories().get(i).getProjects();
				}
			}
		}

		newProject();
	}

	@FXML
	private void onActionMiNovoLoba(ActionEvent event) {
		try {
			treeProjects.getChildren().clear();
			insertNewTextEditor();
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageIOException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	@FXML
	private void onActionMiSair(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void btnSaveLobaFileOnAction(ActionEvent event) {
		if (lobaSource != null) {
			try {
				FileWriter fileWriter = new FileWriter(lobaSource);
				BufferedWriter writer = new BufferedWriter(fileWriter);

				for (String line : editorFields.get(0).getText().split("\\n")) {
					writer.write(line);
					writer.newLine();
				}

				writer.flush();
				writer.close();
			} catch (IOException e) {
				new BasicException(e.getMessage(), e).showExceptioDialog(
						AppConfig.getPropertie("Dialogs.titleException"),
						AppConfig.getPropertie("Dialogs.masterheadException"),
						AppConfig.getPropertie("Dialogs.messageIOException"),
						AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
			}
		}
	}

	@FXML
	private void btnRunLobaOnAction(ActionEvent event) {
		if (lobaSource != null) {
			btnSaveLobaFile.fire();
			this.compiler = new Compiler(lobaSource, this);
			this.compiler.startCompilation();
		}
	}

	private void insertNewTextEditor() throws IOException {
		Source newLoba = new Source(AppConfig.getPropertie("App.loba"));

		for (int i = 0; i < config.getDirectories().size(); i++) {
			if (config.getDirectories().get(i).isSelected()) {
				if (config.getDirectories().get(i).getProjects().get(0) != null) {
					config.getDirectories().get(i).getProjects().get(0).getSrcs().add(newLoba);
					lobaSource = new File(config.getDirectories().get(i).getDirectoryPath()
							+ AppConfig.getSymbolString("division") + config.getDirectories().get(i).getProjects().get(0).getName()
							+ config.getDirectories().get(i).getProjects().get(0).getSrcs().get(0).getSrcName() + newLoba.getSrcName());
					lobaSource.createNewFile();
				}
			}
		}

		TextArea txtArea = new TextArea();
		Tab newTab = new Tab(newLoba.getSrcName().replace(AppConfig.getSymbolString("division"), "").replace(".txt", ".loba"), txtArea);

		editorFields.add(txtArea);
		tabPaneEditorText.getTabs().add(newTab);
		buildingTreeProjects();
	}

	protected static void buildingTreeProjects() {
		projects = new ArrayList<Project>();

		for (Directory d : config.getDirectories()) {
			if (d.isSelected()) {
				projects = d.getProjects() == null ? new ArrayList<Project>() : d.getProjects();
				break;
			}
		}

		if (projects == null || projects.isEmpty())
			return;

		TreeItem<String> projectNode = new TreeItem<>();
		treeProjects.setExpanded(true);

		for (int i = 0; i < projects.size(); i++) {
			projectNode = new TreeItem<String>(projects.get(i).getName(), projectIcon);
			TreeItem<String> srcNode = new TreeItem<>();
			TreeItem<String> codeNode = new TreeItem<>();

			for (int j = 0; j < projects.get(i).getSrcs().size(); j++) {
				if (j == 0) {
					if (projects.get(i).getSrcs().size() <= 1) {
						srcNode = new TreeItem<String>(projects.get(i).getSrcs().get(j).getSrcName().
								replaceAll(AppConfig.getSymbolString("division"), ""), emptyFolderIcon);
					} else {
						srcNode = new TreeItem<String>(projects.get(i).getSrcs().get(j).getSrcName()
								.replaceAll(AppConfig.getSymbolString("division"), ""), folderIcon);
					}
				} else {
					codeNode = new TreeItem<String>(projects.get(i).getSrcs().get(j).getSrcName()
							.replaceAll(AppConfig.getSymbolString("division"), "").replace(".txt", ".loba"), sourceIcon);
					srcNode.getChildren().add(codeNode);
				}
			}

			projectNode.setExpanded(true);
			srcNode.setExpanded(true);

			projectNode.getChildren().add(srcNode);
		}

		treeProjects.getChildren().add(projectNode);
	}

	@Override
	public void writeConsole(String value) {
		if (value.isEmpty()) {
			this.txtAreaConsole.setText(value + "\n");
		} else {
			this.txtAreaConsole.appendText(value + "\n");
		}
	}

}