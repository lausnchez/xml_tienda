package mainPackage;

import java.util.Scanner;

import org.w3c.dom.Document;

public class main {

	static Scanner scan = new Scanner(System.in);
	static GestorDOM gDOM = new GestorDOM();
	
	public static void main(String[] args) {
		menu();

	}
	
	private static void menu() {
		int opcion = 0;
		do {
			System.out.println("Compras & Ventas");
			System.out.println("1. Mostrar todos los datos");
			System.out.println("2. Agregar venta");
			System.out.println("3. Agregar compra");
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
			default:
				System.out.println("Número no válido");
				break;
			}
		}while(opcion != 0);
		System.out.println("Hasta pronto!");
	}

	private static void mostrarDatos() {
		
		gDOM.generarDocumentoArchivos();
	}
		
	private static void agregarVenta() {
		
		gDOM.generarDocumentoArchivos();
	}
	
	private static void agregarCompra() {
		
		gDOM.generarDocumentoArchivos();
	}
	
	private static boolean comprobarInt(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
