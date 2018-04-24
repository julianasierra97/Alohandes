package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tm.AlohonadesTransactionManager;
import vos.Contrato;
import vos.Empresa;
import vos.Operador;
import vos.Usuario;

public class RF10DAO 
{


	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A671810";

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	private DAOContrato daoContrato;
	private RFC4DAO daoRFC4;
	private AlohonadesTransactionManager tm;

	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RF10DAO() {
		recursos = new ArrayList<Object>();
		
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void RF10(String tipo, String id) throws SQLException, Exception {

		conn.setAutoCommit(false);
	
		if(tipo.equals("Habitacion"))
		{
			
			
			String sql0 = String.format("SELECT ID FROM %1$s.OPERADORHABITACION WHERE ID_OPERADOR='%2$s'" ,
					USUARIO,
					id);

			System.out.println(sql0);

			PreparedStatement prepStmt0 = conn.prepareStatement(sql0);
			recursos.add(prepStmt0);

			ResultSet rs=prepStmt0.executeQuery();
			while(rs.next())
			{
				String sql = String.format("UPDATE %1$s.HABITACION SET ESTADO =null WHERE %1$s.HABITACION.ID=%2$s" ,
						USUARIO,
						rs.getString("ID"));

				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);

				prepStmt.executeQuery();
			}
			
			
					
				
			}
			cerrarRecursos();
		
		if(tipo.equals("Vivienda"))
		{
			String sql0 = String.format("SELECT ID FROM %1$s.OPERADORVIVIENDA WHERE ID_OPERADOR='%2$s'" ,
					USUARIO,
					id);

			System.out.println(sql0);

			PreparedStatement prepStmt0 = conn.prepareStatement(sql0);
			recursos.add(prepStmt0);

			ResultSet rs=prepStmt0.executeQuery();
			while(rs.next())
			{
				String sql = String.format("UPDATE %1$s.VIVIENDA SET ESTADO =null WHERE %1$s.VIVIENDA.ID=%2$s" ,
						USUARIO,
						rs.getString("ID"));

				conn.commit();
				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);

				prepStmt.executeQuery();
			}
		}
		
		


	}





	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}

	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}





}
