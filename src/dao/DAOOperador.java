package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Operador;

public class DAOOperador 
{

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

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public DAOOperador() {
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
	public void addOperador(Operador operador) throws SQLException, Exception {

		conn.setAutoCommit(false);
		try {
			String sql = String.format("INSERT INTO %1$s.OPERADOR (DOCUMENTO, LOGIN, CONTRASENHA, CORREO, TIPODOCUMENTO, NOMBRE) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
					USUARIO,  
					operador.getDocumento(), 
					operador.getLogin(),
					operador.getContrasenha(), 
					operador.getCorreo(),
					operador.getTipoDocumento(),
					operador.getNombre());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			conn.commit();
		}
		catch (Exception e) {
			conn.rollback();
			throw e;
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

	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla BEBEDORES) en una instancia de la clase Bebedor.
	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
	 * @return Operador cuyos atributos corresponden a los valores asociados a un registro particular de la tabla BEBEDORES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Operador convertResultSetToOperador(ResultSet resultSet) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)
		Operador op = null;
		
		if(resultSet.next()) {
			String documento = resultSet.getString("DOCUMENTO");
			String login = resultSet.getString("LOGIN");
			String contrasenha =resultSet.getString("CONTRASENHA");
			String correo=resultSet.getString("CORREO");
			String nombre=resultSet.getString("NOMBRE");
			String tipoDocumento=resultSet.getString("TIPODOCUMENTO");

			op = new Operador(login, contrasenha, documento, tipoDocumento, nombre, correo);
		}

		return op;
	}

	public Operador getOperadorById(String id) throws SQLException {
		// TODO Auto-generated method stub
		Operador operador = null;
		String sql = String.format("SELECT DOCUMENTO, LOGIN, CONTRASENHA, CORREO, TIPODOCUMENTO, NOMBRE FROM OPERADOR WHERE DOCUMENTO = '%2$s'", 
				USUARIO,  
				id);

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();


		operador = convertResultSetToOperador(rs);

		return operador;
	}

}
