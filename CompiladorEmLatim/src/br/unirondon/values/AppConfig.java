package br.unirondon.values;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import br.unirondon.exception.BasicException;
import br.unirondon.view.xml.UsuarioConfig;

public class AppConfig {

	private static final File configXML = new File(getPropertie("App.configXML"));

	public AppConfig() {
		
	}

	public static File getConfigXML() {
		return configXML;
	}

	public static String getSymbolString(String key) {
		try {
			Properties properties = new Properties();
			InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("SymbolTable.properties");

			properties.load(input);

			input.close();

			return properties.getProperty(key);
		} catch (FileNotFoundException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageFileNotFoundException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageIOException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
		}

		return null;
	}

	public static String getPropertie(String key) {
		try {
			Properties properties = new Properties();
			InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("AppConfig.properties");

			properties.load(input);

			input.close();

			return properties.getProperty(key);
		} catch (FileNotFoundException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageFileNotFoundException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageIOException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
		}

		return null;
	}

	public static void writeXML(UsuarioConfig usuarioConfig) {
		try {
			Marshaller marshaller = (JAXBContext.newInstance(UsuarioConfig.class)).createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(usuarioConfig, configXML);
		} catch (JAXBException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageJAXBException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	public static UsuarioConfig readXML() {
		try {
			return (UsuarioConfig) ((JAXBContext.newInstance(UsuarioConfig.class)).createUnmarshaller().unmarshal(configXML));
		} catch (JAXBException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					getPropertie("Dialogs.titleException"),
					getPropertie("Dialogs.masterheadException"),
					getPropertie("Dialogs.messageJAXBException"),
					getPropertie("Dialogs.messageExceptionWas"), e);
			return null;
		}
	}
	
	public static void checkFileConfigExits() throws IOException {
		if(!configXML.exists()) {
			configXML.createNewFile();
		}
	}
	
}
