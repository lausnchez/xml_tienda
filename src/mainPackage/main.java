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
			System.out.println("3. Mostrar los productos cuyo precio sea mayor que el leído por teclado");
			System.out.println("4. Mostrar los producto del tipo del leído por teclado");
			System.out.println("5. Mostrar todos los productos que sean de un tipo leido por teclado y \n tengan un precio superior a la media");
			System.out.println("6. Elimina todos los productos cuyo precio sea menor al insertado por \n teclado");
			System.out.println("7. Eliminar por código");
			System.out.println("8. Volcar compras en .csv y ventas en .dat");
			System.out.println("\n0. Cerrar el programa");
			System.out.println("------------------------");
			System.out.print("Opcion: ");
			opcion = scan.nextInt();
			scan.nextLine();
			switch(opcion) {
			case 1:
				gDOM.mostrarTodosDatos();
				break;
			case 2:
				System.out.println("1. Venta / 2. Compra");
				int opcionCompraVenta = scan.nextInt();
				switch(opcionCompraVenta){
					case 1: 
						scan.nextLine();
						gDOM.crearProducto("venta");
						gDOM.guardarDocumento();
						break;
					case 2: 
						scan.nextLine();
						gDOM.crearProducto("compra");
						gDOM.guardarDocumento();
						break;
					default: 
						System.out.println("Valor no válido");
						break;
				}
				break;
			case 3:
				gDOM.mostrarMayorPrecioTeclado();
				break;
			case 4:
				gDOM.mostrarPorTipo();
				break;
			case 5:
				gDOM.mostrarMayorMediaMismoTipo();
				break;
			case 6:
				gDOM.eliminarProductosMenorPrecio();
				break;
			case 0:
				System.out.println("Hasta pronto!");
				break;
			default:
				System.out.println("Número no válido");
				break;
			}
		}while(opcion != 0);
	}

	/**
	 * Comprueba que el valor pasado por parámetros es un int
	 * @param num
	 * @return
	 */
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
