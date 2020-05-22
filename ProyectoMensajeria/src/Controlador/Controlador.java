package Controlador;

import java.util.ArrayList;

import Interfaz.Interfaz;
import Modelo.Datos;
import Modelo.Conexiones;
import Modelo.LoginRegistro;
import Modelo.Consultas;

public class Controlador {
	private Conexiones conexion;
	private static LoginRegistro logeayregistra;
	private static Consultas consultando;
	private static Datos arrays;
	
	private Interfaz interfaz;
	
	public Controlador(Interfaz interfaz) {
		this.interfaz = interfaz;
		conexion = new Conexiones();
		logeayregistra = new LoginRegistro();
		consultando = new Consultas();
		arrays = new Datos();
		
		getContactos();
		getConversaciones();
	}
	
	public void logear(String usuario, String contraseña) throws Exception {
		logeayregistra.login(usuario, contraseña);
	}
	
	public void registrar(String mail, String usuario, String contraseña) throws Exception {
		logeayregistra.registro(mail, usuario, contraseña);
	}
	
	public void addContacto(String newContacto) {
		consultando.addContactos(newContacto, logeayregistra.usuarioConectado);
	}
	
	public ArrayList<String> getArrayContactos() {
		return arrays.getContactos();
	}
	
	public int getSizeContactos() {
		return arrays.sizeContactos();
	}
	
	public void getParticipantes(String usuarioSeleccionado) {
		consultando.getIDUser(logeayregistra.usuarioConectado, usuarioSeleccionado);
	}
	
	public void crearConversación() {
		consultando.newConversacion();
	}
	
	public void getContactos() {
		consultando.getContactos(logeayregistra.usuarioConectado);
	}
	
	public void getConversaciones() {
		consultando.getConversacionBien(logeayregistra.IDUserLogged);
	}
	
	public int getSizeIDConversacion() {
		return arrays.getSizeIDConversacion();
	}
	
	public ArrayList<Integer> getIDConversacion() {
		return arrays.getIDConversacion();
	}
	
	public void mandarMensaje(int IDConversacion, String mensaje) {
		consultando.enviarMensaje(logeayregistra.IDUserLogged, IDConversacion, mensaje);
	}
}
