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
		
		//Elección de login o registro
		LOGorREG = Integer.parseInt(JOptionPane.showInputDialog("¡Bienvenido a la aplicación de mensajería!\n\n"
				+ "¿Qué te gustaría hacer?\n\n"
				+ "Pulsa 1 para iniciar sesión.\n"
				+ "Pulsa 2 para registrarte.\n"
				+ "Pulsa cualquier otro número para salir de la aplicación."));
		
		try {
			logORreg(LOGorREG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Si te registras, puedes elegir si iniciar sesión o salir del programa
		if(LOGorREG == 2) {
			login();
		}
		
		//Primer menú para añadir nuevos contactos o listar los ya existentes con la cuenta conectada
		while(menu) {
			opMenu = Integer.parseInt(JOptionPane.showInputDialog("¿Qué te gustaría hacer?\n\n"
					+ "1: Añadir nuevo contacto.\n"
					+ "2: Listar contactos.\n"
					+ "3: Listar conversaciones.\n"
					+ "4: Ver mensajes.\n"
					+ "5: Salir"));
			
			switch(opMenu) {
				case 1: //Añadir contacto
					newContacto = JOptionPane.showInputDialog("Inserte el nombre del contacto al que deseas añadir:");
					
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
					
					seleccion = Integer.parseInt(JOptionPane.showInputDialog("Elige el número del contacto con el que quieras chatear:\n\n"));
					
					while(loop) {
						if(seleccion<=controlador.getSizeContactos() && seleccion>0) {
							contacto = controlador.getArrayContactos().get(seleccion-1);
							
							controlador.getParticipantes(contacto);
							
							controlador.crearConversación();
							
							loop = false;
						}else {
							System.out.println("No has seleccionado ningún número existente.");
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
					
					conversacionElegida = Integer.parseInt(JOptionPane.showInputDialog("Elige la conversación a la que mandar mensaje:"));
					
					mensaje = JOptionPane.showInputDialog("Manda tu mensaje:");
					
					controlador.mandarMensaje(conversacionElegida, mensaje);
					
					break;
				
				case 4:
					break;
					
				case 5: //Salir
					System.out.println("Que tengas un buen día. :)");
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
		String usuario, contraseña, option;
		
		System.out.println();
		
		int opcion = Integer.parseInt(JOptionPane.showInputDialog("¿Te gustaría iniciar sesión?\n\n"
				+ "Pulsa 1 para iniciar sesión.\n"
				+ "Pulsa lo que sea para salir."));
		
		switch(opcion) {
			case 1: //Login
				usuario = JOptionPane.showInputDialog("¿Cuál es tu usuario?");
				
				contraseña = JOptionPane.showInputDialog("¿Y tu contraseña?");

				try {
					controlador.logear(usuario, contraseña);
					System.out.println("Has iniciado sesión satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			default: //Salir
				System.out.println("Que tengas un buen día. :)");
				System.exit(0);
		}
	}
	
	public static void logORreg(int opcion) {
		Scanner sc = new Scanner(System.in);
		String usuario, contraseña, mail;
		
		switch(opcion) {
			case 1: //Login
				usuario = JOptionPane.showInputDialog("Has elegido iniciar sesión.\n\n"
						+ "¿Cuál es tu usuario?");
				
				contraseña = JOptionPane.showInputDialog("¿Y tu contraseña?");

				try {
					controlador.logear(usuario, contraseña);
					System.out.println("Has iniciado sesión satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}

				sc.close();
				break;
			
			case 2: //Registro
				mail = JOptionPane.showInputDialog("Has elegido registrarte.\n\n"
						+ "¿Cuál es tu email?");
				
				usuario = JOptionPane.showInputDialog("¿Cuál será tu nombre de usuario?");
				
				contraseña = JOptionPane.showInputDialog("Y para acabar, necesitas una contraseña:");

				try {
					controlador.registrar(mail, usuario, contraseña);
					System.out.println("Te has registrado satisfactoriamente.\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sc.close();
				break;
				
			default: //Salir
				sc.close();
				System.out.println("Que tengas un buen día. :)");
				System.exit(0);
		}
	}
}
