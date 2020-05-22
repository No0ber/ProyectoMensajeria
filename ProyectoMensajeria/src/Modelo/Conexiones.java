package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexiones {
	private static Connection conexion = null;
	
	//----------Conexión a la base de datos general----------
	public static Connection conectarExt() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mensajeria", 
					"alumno", "alumno");
			
			System.out.println("Conectado a la BBDD externa.");
			
			return conexion;
			
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			
		}catch(InstantiationException ie) {
			ie.printStackTrace();
			
		}catch(IllegalAccessException iae) {
			iae.printStackTrace();
			
		}
		return null;
		
	}
	
	//----------Conexión a la base de datos local----------
	public static Connection conectarLoc() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mensajerialocal", 
					"alumno", "alumno");
			
			System.out.println("Conectado a la BBDD local.");
			
			return conexion;
			
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			
		}catch(InstantiationException ie) {
			ie.printStackTrace();
			
		}catch(IllegalAccessException iae) {
			iae.printStackTrace();
			
		}
		return null;
	}
	
	//----------Método de desconexión----------
	public static void desconectar(Connection conex) {
		try {
			conex.close();
			conex = null;
			System.out.println("Desconectado de la BBDD.");
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			
		}
	}
}
