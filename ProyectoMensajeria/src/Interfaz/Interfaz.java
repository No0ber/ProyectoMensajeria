package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.*;

import Controlador.Controlador;

public class Interfaz extends JFrame {
	private static Listeners listener;
	private static Controlador controlador;
	
	private JPanel contentPane;
	
	public static void main(String[] args) {
		listener = new Listeners();
		Interfaz frame = new Interfaz(listener);
		controlador = new Controlador(frame);
		int LOGorREG, opMenu;
		String newContacto;
		boolean menu = true;
		
		//Elecci�n de login o registro
		LOGorREG = Integer.parseInt(JOptionPane.showInputDialog("�Bienvenido a la aplicaci�n de mensajer�a!\n\n"
				+ "�Qu� te gustar�a hacer?\n\n"
				+ "Pulsa 1 para iniciar sesi�n.\n"
				+ "Pulsa 2 para registrarte.\n"
				+ "Pulsa cualquier otro n�mero para salir de la aplicaci�n."));
		
		try {
			logORreg(LOGorREG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Si te registras, puedes elegir si iniciar sesi�n o salir del programa
		if(LOGorREG == 2) {
			login();
		}
		
		//Primer men� para a�adir nuevos contactos o listar los ya existentes con la cuenta conectada
		while(menu) {
			opMenu = Integer.parseInt(JOptionPane.showInputDialog("�Qu� te gustar�a hacer?\n\n"
					+ "1: A�adir nuevo contacto.\n"
					+ "2: Listar contactos.\n"
					+ "3: Listar conversaciones.\n"
					+ "4: Ver mensajes.\n"
					+ "5: Salir"));
			
			switch(opMenu) {
				case 1: //A�adir contacto
					newContacto = JOptionPane.showInputDialog("Inserte el nombre del contacto al que deseas a�adir:");
					
					controlador.addContacto(newContacto);
					break;
					
				case 2: //Listar contactos
					int seleccion, numContactos = controlador.getSizeContactos();
					boolean loop = true;
					String contacto = null;
					Scanner sc = new Scanner(System.in);
					
					System.out.println("Estos son tus contactos:\n\n");
					for(int i=0;i<numContactos;i++) {
						System.out.println((i+1)+". "+controlador.getArrayContactos().get(i)+"\n");
					}
					
					seleccion = Integer.parseInt(JOptionPane.showInputDialog("Elige el n�mero del contacto con el que quieras chatear:\n\n"));
					
					while(loop) {
						if(seleccion<=controlador.getSizeContactos() && seleccion>0) {
							contacto = controlador.getArrayContactos().get(seleccion-1);
							
							controlador.getParticipantes(contacto);
							
							controlador.crearConversaci�n();
							
							loop = false;
						}else {
							System.out.println("No has seleccionado ning�n n�mero existente.");
							loop = true;
						}
					}
					
					break;
				
				case 3:
					int conversacionElegida;
					String mensaje;
					
					System.out.println("Estas son tus conversaciones:\n\n");
					
					for(int i=0;i<controlador.getSizeIDConversacion();i++) {
						System.out.println(controlador.getIDConversacion().get(i)+"\n");
					}
					
					conversacionElegida = Integer.parseInt(JOptionPane.showInputDialog("Elige la conversaci�n a la que mandar mensaje:"));
					
					mensaje = JOptionPane.showInputDialog("Manda tu mensaje:");
					
					controlador.mandarMensaje(conversacionElegida, mensaje);
					
					break;
				
				case 4:
					break;
					
				case 5: //Salir
					System.out.println("Que tengas un buen d�a. :)");
					System.exit(0);
					
				default: //Loop
					break;
			}
		}
		
		
	}

	public Interfaz(Listeners listener) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	public static void login() {
		String usuario, contrase�a, option;
		
		System.out.println();
		
		int opcion = Integer.parseInt(JOptionPane.showInputDialog("�Te gustar�a iniciar sesi�n?\n\n"
				+ "Pulsa 1 para iniciar sesi�n.\n"
				+ "Pulsa lo que sea para salir."));
		
		switch(opcion) {
			case 1: //Login
				usuario = JOptionPane.showInputDialog("�Cu�l es tu usuario?");
				
				contrase�a = JOptionPane.showInputDialog("�Y tu contrase�a?");

				try {
					controlador.logear(usuario, contrase�a);
					System.out.println("Has iniciado sesi�n satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			default: //Salir
				System.out.println("Que tengas un buen d�a. :)");
				System.exit(0);
		}
	}
	
	public static void logORreg(int opcion) {
		Scanner sc = new Scanner(System.in);
		String usuario, contrase�a, mail;
		
		switch(opcion) {
			case 1: //Login
				usuario = JOptionPane.showInputDialog("Has elegido iniciar sesi�n.\n\n"
						+ "�Cu�l es tu usuario?");
				
				contrase�a = JOptionPane.showInputDialog("�Y tu contrase�a?");

				try {
					controlador.logear(usuario, contrase�a);
					System.out.println("Has iniciado sesi�n satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}

				sc.close();
				break;
			
			case 2: //Registro
				mail = JOptionPane.showInputDialog("Has elegido registrarte.\n\n"
						+ "�Cu�l es tu email?");
				
				usuario = JOptionPane.showInputDialog("�Cu�l ser� tu nombre de usuario?");
				
				contrase�a = JOptionPane.showInputDialog("Y para acabar, necesitas una contrase�a:");

				try {
					controlador.registrar(mail, usuario, contrase�a);
					System.out.println("Te has registrado satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sc.close();
				break;
				
			default: //Salir
				sc.close();
				System.out.println("Que tengas un buen d�a. :)");
				System.exit(0);
		}
	}
}
