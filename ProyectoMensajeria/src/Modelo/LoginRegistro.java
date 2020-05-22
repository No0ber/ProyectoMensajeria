package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRegistro {
	public String usuarioConectado;
	public int IDUserLogged;
	
	//----------El login se comprueba en la base de datos local----------
	public void login(String usuario, String contraseña) throws Exception {
		Connection conexionLcl = Conexiones.conectarLoc();
		Connection conexionExt = Conexiones.conectarExt();
		
		String sentenciaUser = "SELECT usuario FROM usuario WHERE nombre = ?";
		String sentenciaPass = "SELECT usuario FROM usuario WHERE password = ?";
		String sentenciaGetIDUserLogin = "SELECT id_user FROM usuario WHERE nombre = ?";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		boolean estaRegistrado = false;
		boolean contrasenaBien = false;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaGetIDUserLogin);
			sentencia.setString(1, usuario);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				IDUserLogged = resultado.getInt(1);
			}
			
			sentencia.close();
			resultado.close();
			
			sentencia = conexionLcl.prepareStatement(sentenciaUser);
			sentencia.setString(1, usuario);
			resultado = sentencia.executeQuery();
			
			if(resultado.next()) {
				estaRegistrado = true;
				
			}else {
				estaRegistrado = false;
				
			}
			sentencia.close();
			resultado.close();
			
			sentencia = conexionLcl.prepareStatement(sentenciaPass);
			sentencia.setBytes(1, Cifrado.cifra(contraseña));
			resultado = sentencia.executeQuery();
			
			if(resultado.next()) {
				contrasenaBien = true;
			}else {
				contrasenaBien = false;
			}
			
			if(estaRegistrado & contrasenaBien) {
				System.out.println("Login correcto.");
				usuarioConectado = usuario;
			}else {
				System.out.println("Login erróneo.");
			}
			
			sentencia.close();
			resultado.close();
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			
		}finally {
			if(sentencia != null)
				try {
					sentencia.close();
					resultado.close();
					
		    }catch(SQLException sqle) {
		    	sqle.printStackTrace();
		    	
		    }
		}
		
		Conexiones.desconectar(conexionLcl);
		Conexiones.desconectar(conexionExt);
	}
	
	//----------El registro se hace en ambas bases de datos----------
	public void registro(String mail, String nombre, String password) throws Exception {
		//----------Registro general----------
		Connection conexion = Conexiones.conectarExt();
		
		String sentenciaResgitro = "INSERT INTO usuario (mail, nombre, password) VALUES (?, ?, ?)";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement(sentenciaResgitro);
			sentencia.setString(1, mail);
			sentencia.setString(2, nombre);
			sentencia.setBytes(3, Cifrado.cifra(password));
			sentencia.executeUpdate();
			
			System.out.println("Registro externo satisfactorio.");
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		  
		}finally {
			if(sentencia != null)
				try {
					sentencia.close();
					
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					
				}
		}
		
		Conexiones.desconectar(conexion);
		
		
		//----------Registro local----------
		conexion = Conexiones.conectarLoc();
		
		sentencia = null;
		resultado = null;
		
		try {
			sentencia = conexion.prepareStatement(sentenciaResgitro);
			sentencia.setString(1, mail);
			sentencia.setString(2, nombre);
			sentencia.setBytes(3, Cifrado.cifra(password));
			sentencia.executeUpdate();
			
			System.out.println("Registro local satisfactorio.");
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		  
		}finally {
			if(sentencia != null)
				try {
					sentencia.close();
					
				}catch(SQLException sqle) {
					sqle.printStackTrace();
					
				}
		}
		
		Conexiones.desconectar(conexion);
	}
}
