package mainPackage;

import java.io.File;

/*
 * El GestorDOM debe tener 3 parámetros: la dirección del documento (String),
 * un documentBuilder null, y un Document en null.
 * 
 * Primero debemos hacer un constructor para el GestorDOM. En el constructor
 * se debe de inicializar un DocumentBuilderFactory que se usará para crear un
 * DocumentBuilder (que está ya creado como atributo) y un documento (que también
 * está ya creado como un atributo). 
 * 
 * Después deberemos crear un método para crear el documento desde 0 (o leer un
 * documento ya hecho) y otro para hacer un guardado físico del documento.
 * 
 * En el método de guardado deberemos crear primero un TransformerFactory, el cual
 * nos generará un Transformer, que sirve para darle formato al archivo
 * antes de guardarlo. Además hay que designar un origen (Source ej = new DOMSource(doc))
 * y un resultado, que en este caso deberá ser un archivo físico 
 * (Result res = new StreamResult(new File(direccion))).
 * 
 * 			TransformerFactory transFactory = TransformerFactory.newInstance();
 *			Transformer trans = transFactory.newTransformer();	
 *		
 *			Source origen =  new DOMSource(doc);
 *			Result resultado = new StreamResult(new File("compraVenta.xml"));	
 *		
 *			trans.setOutputProperty(OutputKeys.INDENT,"yes");
 *			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
 *
 *			trans.tranform(origen, resultado);
 * 
 * En caso de querer importar un archivo deberemos usar el DocumentBuilder para 
 * parsearlo, pasándole la dirección del archivo xml (this.doc = dBuilder.parse(direccionDoc);).
 * En caso de que por otro lado queramos crear un documento nuevo deberemos crear un elemento raiz
 * y hacerle append al documento como tal, y después guardar el documento.
 */

import java.util.Scanner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class main {

	static Scanner scan = new Scanner(System.in);
	static GestorDOM gDOM = new GestorDOM();
	
	public static void main(String[] args) {
		gDOM.inicializarDocumento();
		menu();

	}
	
	private static void menu() {
		int opcion = 0;
		do {
			System.out.println("Compras & Ventas -------------------------------------------------------\n");
			System.out.println("1. Mostrar todos los datos");
			System.out.println("2. Agregar venta o compra");
			System.out.println("3. Mostrar");
			System.out.println("4. Mostrar los productos cuyo precio sea mayor que el leído por teclado");
			System.out.println("5. Mostrar los productor del tipo del leído por teclado");
			System.out.println("\n0. Cerrar el programa");
			System.out.println("------------------------");
			System.out.print("Opcion: ");
			opcion = scan.nextInt();
			switch(opcion) {
			case 1:
				mostrarDatos();
				break;
			case 2:
				agregarVenta();
				break;
			case 3:
				agregarCompra();
				break;
			case 0:
				break;
			default:
				System.out.println("Número no válido");
				break;
			}
		}while(opcion != 0);
		System.out.println("Hasta pronto!");
	}

	private static void mostrarDatos() {
		
		gDOM.guardarDocumento();
	}
		
	private static void agregarVenta() {
		gDOM.crearProducto("venta");
		gDOM.guardarDocumento();
	}
	
	private static void agregarCompra() {
		gDOM.crearProducto("compra");
		gDOM.guardarDocumento();
	}
	
	private static boolean comprobarInt(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Pide una pregunta y devuelve una String como respuesta
	 * @param pregunta
	 * @return
	 */
	public static String pedirString(String pregunta) {
		System.out.println(pregunta);
		String respuesta = scan.nextLine();
		return respuesta;
	}
	
	/**
	 * Nos pide una pregunta e intenta pasar el valor pedido a número
	 * En caso de que de error devuelve -1, si no devuelve el valor dado
	 * @param pregunta
	 * @return
	 */
	public static int pedirInt(String pregunta) {
		System.out.println(pregunta);
		String num = scan.nextLine();
		try {
			int numFinal = Integer.parseInt(num);
			return numFinal;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	/**
	 * Nos pide una pregunta e intenta pasar el valor pedido a float
	 * En caso de que de error devuelve -1, si no devuelve el valor dado 
	 * @param 
	 * @return
	 */
	public static float pedirFloat(String pregunta) {
		System.out.println(pregunta);
		String num = scan.nextLine();
		try {
			float numFinal = Float.parseFloat(num);
			return numFinal;
		} catch (NumberFormatException e) {
			System.out.println("No válido.");
			return -1;
		}
	}
}
