package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Consultas {Connection conexionExt = null;
	Connection conexionLoc = null;
	
	//----------Añadir contactos----------
	public void addContactos(String nomContacto, String nomUsuario) {
		conexionExt = Conexiones.conectarExt();
		conexionLoc = Conexiones.conectarLoc();
		
		String sentenciaComprobar = "SELECT nombre FROM usuario WHERE nombre = ?";
		String sentenciaAddContacto = "INSERT INTO contactos (nombre, usuario) VALUES (?, ?)";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		 
		try {
			sentencia = conexionExt.prepareStatement(sentenciaComprobar);
			sentencia.setString(1, nomContacto);
			resultado = sentencia.executeQuery();
			
			if(resultado.next()) {
				System.out.println("El usuario se encuentro en la base de datos, procediendo a añadirlo a los contactos.");
				sentencia.close();
				
				sentencia = conexionLoc.prepareStatement(sentenciaAddContacto);
				sentencia.setString(1, nomContacto);
				sentencia.setString(2, nomUsuario);
				sentencia.executeUpdate();
				
				System.out.println("El contacto '"+nomContacto+"' añadido satisfactoriamente.");
			}else {
				System.out.println("El nuevo contacto que intentas añadir no existe.");
			}
			
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
		
		Conexiones.desconectar(conexionExt);
		Conexiones.desconectar(conexionLoc);
	}
	
	//----------Listar contactos----------
	public void getContactos(String usuarioConectado) {
		conexionLoc = Conexiones.conectarLoc();
		
		String sentenciaGetContactos = "SELECT nombre FROM contactos WHERE usuario = ?";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexionLoc.prepareStatement(sentenciaGetContactos);
			sentencia.setString(1, usuarioConectado);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addContactos(resultado.getString(1));
			}
			
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
		
		Conexiones.desconectar(conexionLoc);
	}
	
	//----------Crear conversación----------
	public void newConversacion() {
		int IDconver = 0;
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaAddConver = "INSERT INTO conversacion VALUES (default)";
		String sentenciaGetLastConver = "SELECT * FROM conversacion ORDER BY id_conversacion DESC LIMIT 1";
		String sentenciaAddParticipantes = "INSERT INTO participa (id_user, id_conversacion) VALUES (?, ?)";
		String sentenciaAddTipoConver = "INSERT INTO individual (id_conversacion) VALUES (?)";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			conexionExt.setAutoCommit(false);
			
			sentencia = conexionExt.prepareStatement(sentenciaAddConver);
			sentencia.execute();
			sentencia.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaGetLastConver);
			resultado = sentencia.executeQuery();
			while(resultado.next()) {
				IDconver = resultado.getInt(1);
			}
			sentencia.close();
			resultado.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaAddParticipantes);
			sentencia.setInt(1, Datos.getSelectedIDnewConver(0));
			sentencia.setInt(2, IDconver);
			sentencia.execute();
			sentencia.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaAddParticipantes);
			sentencia.setInt(1, Datos.getSelectedIDnewConver(1));
			sentencia.setInt(2, IDconver);
			sentencia.execute();
			sentencia.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaAddTipoConver);
			sentencia.setInt(1, IDconver);
			sentencia.execute();
			
			conexionExt.commit();
			
			
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
		
		Conexiones.desconectar(conexionExt);
	}
	
	//Mete en una array los IDS del usuario conectado y el usuario con el que va a interactuar
	public void getIDUser(String usuarioConectado, String usuarioSeleccionado) {
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaGetIDs = "SELECT id_user FROM usuario WHERE nombre = ?";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaGetIDs);
			sentencia.setString(1, usuarioConectado);
			resultado = sentencia.executeQuery();
			
			Datos.clearIDsNewConver();
			
			while(resultado.next()) {
				Datos.addIDsNewConver(resultado.getInt(1));
			}

			sentencia.close();
			resultado.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaGetIDs);
			sentencia.setString(1, usuarioSeleccionado);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addIDsNewConver(resultado.getInt(1));
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

		Conexiones.desconectar(conexionExt);
	}

	//----------Listar conversaciones----------
	public void getConversaciones1(String nombreContacto) {
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaGetIDContactos = "SELECT id_user FROM usuario WHERE nombre = ?";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaGetIDContactos);
			sentencia.setString(1, nombreContacto);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addIDContacto(resultado.getInt(1));
			}
			
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
		
		Conexiones.desconectar(conexionExt);
	}
	
	public void getConversaciones2(int IDUsuarioLogeado, int IDContactos) {
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaGetIDConversacionesCreadas = "SELECT id_conversacion FROM participa WHERE id_user = ?";
		String sentenciaGetNombresConConverCreada = "SELECT nombre FROM usuario WHERE id_user = ?";
		
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaGetIDConversacionesCreadas);
			sentencia.setInt(1, IDUsuarioLogeado);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addIDConversacion(resultado.getInt(1));
			}
			
			sentencia.close();
			resultado.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaGetNombresConConverCreada);
			sentencia.setInt(1, IDContactos);
			
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
		
		Conexiones.desconectar(conexionExt);
	}
	
	public void getConversacionBien(int IDUserConectado) {
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaGetConversaciones = "SELECT id_conversacion FROM participa WHERE id_user = ?";

		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaGetConversaciones);
			sentencia.setInt(1, IDUserConectado);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addIDConversacion(resultado.getInt(1));
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
		
		Conexiones.desconectar(conexionExt);
	}
	
	//----------Enviar y recibir mensajes----------
	public void enviarMensaje(int IDUsuarioEmisor, int IDConversacion, String mensaje) {
		conexionExt = Conexiones.conectarExt();
		conexionLoc = Conexiones.conectarLoc();
		
		String sentenciaMsgExt = "INSERT INTO mensaje (id_user, id_conversacion, mensaje) VALUES (?, ?, ?)";
		String sentenciaMsgLcl = "INSERT INTO mensajes (id_emisor, id_conversacion, mensaje) VALUES (?, ?, ?)";

		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaMsgExt);
			sentencia.setInt(1, IDUsuarioEmisor);
			sentencia.setInt(2, IDConversacion);
			sentencia.setString(3, mensaje);
			sentencia.executeUpdate();
			
			sentencia.close();
			
			sentencia = conexionLoc.prepareStatement(sentenciaMsgLcl);
			sentencia.setInt(1, IDUsuarioEmisor);
			sentencia.setInt(2, IDConversacion);
			sentencia.setString(3, mensaje);
			sentencia.executeUpdate();
			
			sentencia.close();
			
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
		
		Conexiones.desconectar(conexionExt);
		Conexiones.desconectar(conexionLoc);
	}
	
	public void descargarMensajes1(int IDUserConectado) {
		conexionExt = Conexiones.conectarExt();
		
		String sentenciaDescargarMensajes = "SELECT mensaje, id_user, id_conversacion FROM mensaje WHERE id_user = ?";
		String sentenciaBorrarMensajesExt = "DELETE FROM mensaje WHERE id_user = ?";

		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		Datos.clearMensajesNuevos();
		
		try {
			sentencia = conexionExt.prepareStatement(sentenciaDescargarMensajes);
			sentencia.setInt(1, IDUserConectado);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Datos.addMensajesNuevos(resultado.getString(1));
			}
			
			sentencia.close();
			
			sentencia = conexionExt.prepareStatement(sentenciaBorrarMensajesExt);
			sentencia.setInt(1, IDUserConectado);
			sentencia.executeUpdate();
			
			sentencia.close();
			
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
		
		Conexiones.desconectar(conexionExt);
	}
	
	public void descargarMensajes2() {
		conexionLoc = Conexiones.conectarLoc();
		
		String sentenciaGuardarMensajes = "INSERT INTO mensajes (";
	}
}
