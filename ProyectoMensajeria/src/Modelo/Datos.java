package Modelo;

import java.util.ArrayList;

public class Datos {
	private static ArrayList<String> contactos, converCreadas, mensajesNuevos;
	private static ArrayList<Integer> IDsNewConver, IDsContactos, IDsConversaciones;
	
	public Datos() {
		contactos = new ArrayList<String>();
		converCreadas = new ArrayList<String>();
		IDsNewConver = new ArrayList<Integer>();
		IDsContactos = new ArrayList<Integer>();
		IDsConversaciones = new ArrayList<Integer>();
	}
	
	//----------Trabajando con lo contactos----------
	public static void addContactos(String contacto) {
		contactos.add(contacto);
	}
	
	public ArrayList<String> getContactos() {
		return contactos;
	}
	
	public int sizeContactos() {
		return contactos.size();
	}
	
	//----------Trabajando con los IDs para crear conversaciones----------
	public static void addIDsNewConver(int ID) {
		IDsNewConver.add(ID);
	}
	
	public static int getSelectedIDnewConver(int posicion) {
		return IDsNewConver.get(posicion);
	}
	
	public static void clearIDsNewConver() {
		IDsNewConver.clear();
	}
	
	//----------ArrayList con los ID de los contactos del usuario conectado----------
	public static void addIDContacto(int ID) {
		IDsContactos.add(ID);
	}
	
	public static int getIDContacto(int ID) {
		return IDsContactos.get(ID);
	}
	
	public static void clearIDsContactos() {
		IDsContactos.clear();
	}
	
	//----------ArrayList con los ID de las conversaciones del usuario registrado----------
	public static void addIDConversacion(int ID) {
		IDsConversaciones.add(ID);
	}
	
	public int getSizeIDConversacion() {
		return IDsConversaciones.size();
	}
	
	public ArrayList<Integer> getIDConversacion() {
		return IDsConversaciones;
	}
	
	public static void clearIDsConversaciones() {
		IDsConversaciones.clear();
	}
	
	//----------ArrayList que almacena las conversaciones ya creadas----------
	public static void addConverCreada(String nomConverCreada) {
		converCreadas.add(nomConverCreada);
	}
	
	public static void clearConverCreadas() {
		converCreadas.clear();
	}
	
	//----------ArrayList que lista los mensajes nuevos descargados----------
	public static void addMensajesNuevos(String mensaje) {
		mensajesNuevos.add(mensaje);
	}
	
	public static void clearMensajesNuevos() {
		mensajesNuevos.clear();
	}
}
