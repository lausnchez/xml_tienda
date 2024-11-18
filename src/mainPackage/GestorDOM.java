package mainPackage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class GestorDOM {
	private static String direccionDoc = "./compraventa.xml";
	private static DocumentBuilder dBuilder;
	private static Document doc;
	
	/***
	 * Primero crea un documentBuilderFactory que se crea una instancia que permite usar el
	 * DocumentBuilder. Una vez creada la factory nos creamos un nuevo DocumentBuilder y un
	 * nuevo documento con su método y se lo asignamos a los atributos de la clase
	 */
	public GestorDOM() {
		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			this.dBuilder = dBuilderFactory.newDocumentBuilder();
			this.doc = dBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Primero generamos una instancia del TransformerFactory, el cual nos permitirá
	 * crear un transformer.
	 */
	public void generarDocumentoArchivos() {
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();			
			DOMSource origen =  new DOMSource(doc);
			Result resultado = new StreamResult(new File("compraVenta.xml"));		
			trans.setOutputProperty(OutputKeys.INDENT,"yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generar el archivo raiz en el archivo
	 */
	public void inicializarArchivoNuevo() {
		Element raiz = doc.createElement("datos");
		doc.appendChild(raiz);
		generarDocumentoArchivos();
	}
	
	/**
	 * Importar el archivo XML ya creado en el programa
	 */
	public void importarXML() {
		try {
			this.doc = dBuilder.parse(direccionDoc);
		} catch (SAXException | IOException e) {
			System.err.println("Error al parsear el archivo XML");
			e.printStackTrace();
		}
	}
	
	
}
