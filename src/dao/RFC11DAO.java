package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CondicionesRFC10;

public class RFC11DAO {

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



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RFC11DAO() {
		recursos = new ArrayList<Object>();

	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	public String RFC11(String id, CondicionesRFC10 condiciones) throws SQLException
	{
		String respuesta = "Los clientes son: \n";

		String fechaInicio = condiciones.getFechaInicio();
		String fechaFin = condiciones.getFechaFin();
		String condicion = condiciones.getCondicion();
		Integer idAlojamiento = condiciones.getIdAlojamiento();
		String tipo = condiciones.getTipo();

		String sql = "";

		if(tipo.equalsIgnoreCase(("Vivienda")))
		{

			sql = String.format("SELECT * FROM USUARIO WHERE USUARIO.DOCUMENTO NOT IN (SELECT ID_CLIENTE FROM CONTRATO WHERE ID IN ( SELECT ID_CONTRATO FROM CONTRATOVIVIENDA WHERE ID_VIVIENDA = %1$d ) AND FECHAINICIO BETWEEN '%2$s' AND '%3$s' ) ORDER BY '%4$s'",
					idAlojamiento,
					fechaInicio,
					fechaFin,
					condicion
					);
			System.out.println("Entro 3");
		}
		else if(tipo.equalsIgnoreCase("Habitacion"))
		{
			sql = String.format("SELECT * FROM USUARIO WHERE USUARIO.DOCUMENTO IN (SELECT ID_CLIENTE FROM CONTRATO WHERE ID IN ( SELECT ID_CONTRATO FROM CONTRATOHABITACION WHERE ID_HABITACION = %1$d ) AND FECHAINICIO BETWEEN '%2$s' AND '%3$s' ) ORDER BY '%4$s'",
					idAlojamiento,
					fechaInicio,
					fechaFin,
					condicion
					);
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);

		ResultSet rs=prepStmt.executeQuery();

		while(rs.next())
		{
			System.out.println("Entro 4");
			respuesta += "Nombre: " + rs.getString("Nombre") + "\n"
					+ "Apellido" + rs.getString("Apellido")+ "\n"
					+ "Documento: " + rs.getString("Documento")+ "\n"
					+ "Correo: " + rs.getString("Correo")+ "\n"
					+ "Login: " + rs.getString("Correo")+ "\n";
		}


		System.out.println(respuesta);
		return respuesta;
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
