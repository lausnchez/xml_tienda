package mainPackage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class GestorDOM {
	private static String direccionDoc = "./compraventa.xml";
	private static DocumentBuilder dBuilder;
	private static Document doc;
	
	Scanner scan = new Scanner(System.in);
	
	/***
	 * Primero crea un documentBuilderFactory que se crea una instancia que permite usar el
	 * DocumentBuilder. Una vez creada la factory nos creamos un nuevo DocumentBuilder y un
	 * nuevo documento con su método y se lo asignamos a los atributos de la clase.
	 * En resumen, sirve para asignar valores al Document y al DocumentBuilder (que necesita
	 * el DocumentBuilderFactory)
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
	 * Nos guarda físicamente el documento
	 */
	public void guardarDocumento() {
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();	
			
			Source origen =  new DOMSource(doc);
			Result resultado = new StreamResult(new File("compraVenta.xml"));	
			
			trans.setOutputProperty(OutputKeys.INDENT,"yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			trans.transform(origen, resultado);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Debe usarse al inicializar el proyecto. En caso de que no se pueda
	 * parsear el documento (es decir, no exista) nos generará uno nuevo.
	 */
	public void inicializarDocumento() {
		try {
			this.doc = dBuilder.parse(direccionDoc);
		} catch (SAXException | IOException e) {
			this.doc = dBuilder.newDocument();
			Element raiz = doc.createElement("datos");
			doc.appendChild(raiz);
			Element compras = doc.createElement("compras");
			Element ventas = doc.createElement("ventas");
			raiz.appendChild(compras);
			raiz.appendChild(ventas);
			guardarDocumento();
		}
	}
	
	/**
	 * Pide los datos para crear un nuevo producto y los valida
	 * Crea un producto y dependiendo de lo que le pasemos por
	 * parámetros lo inserta en compras y ventas
	 * @param compraVenta "compras" / "ventas"
	 */
	public void crearProducto(String compraVenta) {
		//Pedir datos para la generación del producto
		String tipo = main.pedirString("Tipo de producto: ");
		int codigo = -1;
		while(codigo == -1 || codigoDeProductoExistente(codigo)) {
			codigo = main.pedirInt("Código de producto: ");
		}
		String nombre = main.pedirString("Nombre del producto: ");
		String descripcion = main.pedirString("Descripción del producto: ");
		float precio = -1;
		while(precio == -1 || precio < 0) {
			precio = main.pedirFloat("Precio del producto: ");
		}
		
		// Generar el producto
		Element productoEl = doc.createElement("producto");
		productoEl.setAttribute("tipo", tipo);
		productoEl.setAttribute("codigo", String.valueOf(codigo));
		Element nombreEl = doc.createElement("nombre");
		nombreEl.setTextContent(nombre);
		Element descEl = doc.createElement("descripcion");
		descEl.setTextContent(descripcion);
		Element precioEl = doc.createElement("precio");
		precioEl.setTextContent(String.valueOf(precio));
		
		productoEl.appendChild(nombreEl);
		productoEl.appendChild(descEl);
		productoEl.appendChild(precioEl);
		
		switch(compraVenta) {
			case "compra":
				Element compra = (Element)doc.getElementsByTagName("compras").item(0);
				compra.appendChild(productoEl);
				break;
			case "venta":
				Element venta = (Element)doc.getElementsByTagName("ventas").item(0);
				venta.appendChild(productoEl);
				break;
		}
	}
	
	/**
	 * Comprueba todos los códigos de producto y devuelve true si encuentra una coincidencia
	 * @param codigo
	 * @return
	 */
	public boolean codigoDeProductoExistente(int codigo) {
		Boolean encontrado = false;
		NodeList listado = doc.getElementsByTagName("producto");
		String codeString = String.valueOf(codigo);
		for(int i = 0; i < listado.getLength(); i++) {
			Element e = (Element)listado.item(i);
			if(codeString.equals( e.getAttribute("codigo"))) {
				encontrado = true;
				System.out.println("Código ya ingresado. Intente otro.");
			}
		}
		return encontrado;
	}
	
	
	public void mostrarUnProducto(Element producto) {
		String nombre = producto.getElementsByTagName("nombre").item(0).getTextContent();
		String descripcion = producto.getElementsByTagName("descripcion").item(0).getTextContent();
		String precio = producto.getElementsByTagName("precio").item(0).getTextContent();
		String codigo = producto.getAttribute("codigo");
		String tipo = producto.getAttribute("tipo");
		
		System.out.println("\n " + nombre + "----------------");
		System.out.println("Código " + codigo + " / Tipo " + tipo);
		System.out.println("- Descripción: " + descripcion);
		System.out.println("- Precio: " + precio + "\n");
		
	}
	
	/**
	 * Muestra todos los productos del listado
	 */
	public void mostrarTodosDatos() {
		NodeList listado = doc.getElementsByTagName("producto");
		for(int i = 0; i < listado.getLength(); i++) {
			Element producto = (Element)listado.item(i);
			mostrarUnProducto(producto);
		}
	}
	
	/**
	 * Muestra los productos de un tipo específico pedido por el usuario
	 */
	public void mostrarPorTipo() {
		System.out.println("Tipo que desea buscar: ");
		String busqueda = scan.nextLine();
		NodeList listado = doc.getElementsByTagName("producto");
		int contador = 0;
		for(int i = 0; i < listado.getLength(); i++) {
			Element producto = (Element)listado.item(i);
			if(producto.getAttribute("tipo").equalsIgnoreCase(busqueda)) {
				mostrarUnProducto(producto);
				contador++;
			}		
		}
		if(contador == 0) System.out.println("No se encontraron resultados");
	}
	
	/**
	 * Pide un precio por teclado y muestra los productos con un precio mayor
	 */
	public void mostrarMayorPrecioTeclado() {
		float precio = main.pedirFloat("Precio mínimo a buscar: ");
		int contador = 0;
		if(precio > 0) {
			NodeList listado = doc.getElementsByTagName("producto");
			for(int i = 0; i < listado.getLength(); i++) {
				Element producto = (Element)listado.item(i);
				float precioEncontrado = Float.parseFloat(producto.getElementsByTagName("precio").item(0).getTextContent());
				if(precioEncontrado > precio) {
					mostrarUnProducto(producto);
					contador++;
				}
			}
			if(contador == 0) System.out.println("No se encontraron resultados");
		}	
	}
	
	/**
	 * Pide un precio y un tipo por teclado al usuario y muestra los productos
	 * del mismo tipo y con un precio mayor al insertado.
	 */
	public void mostrarMayorMediaMismoTipo() {
		System.out.println("Media: " + mediaPrecios());
		String tipo = main.pedirString("Tipo a buscar: ");
		int contador = 0;
		NodeList listado = doc.getElementsByTagName("producto");
		for(int i = 0; i < listado.getLength(); i++) {
			Element producto = (Element)listado.item(i);
			float precioEncontrado = Float.parseFloat(producto.getElementsByTagName("precio").item(0).getTextContent());
			String tipoEncontrado = producto.getAttribute("tipo");
			if(mediaPrecios() < precioEncontrado && tipo.equalsIgnoreCase(tipoEncontrado)) {
				mostrarUnProducto(producto);
				contador++;
			}
		}	
		if(contador == 0) System.out.println("No se encontraron resultados");
	}
	
	
	public float mediaPrecios() {
		float total = 0;
		int contador = 0;
		NodeList listado = doc.getElementsByTagName("producto");
		for(int i = 0; i < listado.getLength(); i++) {
			Element producto = (Element)listado.item(i);
			float precioEncontrado = Float.parseFloat(producto.getElementsByTagName("precio").item(0).getTextContent());	
			total += precioEncontrado;
			contador++;
		}
		return total/contador;
	}
	/*
	public void inicializarArchivoNuevo() {
		Element raiz = doc.createElement("datos");
		doc.appendChild(raiz);
		guardarDocumento();
	}
	
	public void importarXML() {
		try {
			this.doc = dBuilder.parse(direccionDoc);
		} catch (SAXException | IOException e) {
			System.err.println("Error al parsear el archivo XML");
			e.printStackTrace();
		}
	}
	*/
}
