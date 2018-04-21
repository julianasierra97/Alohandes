package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RFC5DAO {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A631810";

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

	public RFC5DAO() {
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
	public String RFC5() throws SQLException, Exception {

		String rta = "El uso de alohAndes de los usuarios es el siguiente: ";


		String sql = String.format( 
				"WITH " +
						"QUERY1 AS " +
						"(SELECT NOMBRE AS NOMBRE, DOCUMENTO AS DOCUMENTO, TIPODOCUMENTO AS TIPODOCUMENTO " +
						"FROM %1$s.USUARIO), " +

		"QUERY2 AS " + 
		"(SELECT ID AS IDEMPRESA " +
		"FROM %1$s.EMPRESA), " +

		"QUERY3 AS " +
		"(SELECT ID AS IDCONTRATO, ID_CLIENTE AS IDCLIENTE " +
		" FROM %1$s.CONTRATO), " +

		"QUERY4 AS " +
		"(SELECT ID_CONTRATO AS IDCONTRATO, ID_HABITACION AS IDHABITACION " +
		"FROM %1$s.CONTRATOHABITACION), " +

		"QUERY5 AS " +
		"(SELECT ID_CONTRATO AS IDCONTRATO, ID_VIVIENDA AS IDVIVIENDA " +
		"FROM %1$s.CONTRATOVIVIENDA), " +

		"QUERY6 AS " +
		"(SELECT NOMBRE AS NOMBRE, DOCUMENTO AS DOCUMENTO " +
		"FROM %1$s.OPERADOR), " +

		"QUERY7 AS " +
		"(SELECT ID AS IDHABITACION " +
		"FROM %1$s.OPERADORHABITACION) " +

		"SELECT QUERY1.DOCUMENTO, QUERY4.IDHABITACION, QUERY6.NOMBRE " +
		"FROM QUERY1, QUERY2, QUERY3, QUERY4, QUERY6, QUERY7 " +
		"WHERE QUERY3.IDCLIENTE = QUERY1.DOCUMENTO AND QUERY6.DOCUMENTO = QUERY2.IDEMPRESA AND QUERY4.IDHABITACION = QUERY7.IDHABITACION " +
		"GROUP BY QUERY1.DOCUMENTO, QUERY4.IDHABITACION, QUERY6.NOMBRE " +
		"ORDER BY QUERY1.DOCUMENTO ", USUARIO);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql);
		recursos.add(prepStmt1);

		ResultSet rs1=prepStmt1.executeQuery();

		while (rs1.next())
		{
			rta+="El usuario " + rs1.getString("DOCUMENTO") + " ha utilizado la habitacion o vivienda " + rs1.getInt("IDHABITACION") + " de la empresa " + rs1.getString("NOMBRE");
		}
		return rta;
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
