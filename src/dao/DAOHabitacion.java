package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Bebedor;
import vos.Habitacion;
import vos.Servicio;
import vos.Usuario;

public class DAOHabitacion {

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

	private int numeroHabitaciones;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public DAOHabitacion() {
		recursos = new ArrayList<Object>();
		numeroHabitaciones=0;
	}



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los bebedores en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Habitacion> getHabitaciones() throws SQLException, Exception {
		ArrayList<Habitacion> habitaciones = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		//Primera sentencia

		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ROWNUM <= 50 ;", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()){

			//Segudna sentencia
			String sql2 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_HABITACION = %2$d ;", USUARIO, rs.getInt("ID"));

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			while (rs.next()) {
				ArrayList<Servicio> servicios = new ArrayList<>();
				while(rs2.next())
				{
					servicios.add(new Servicio(rs2.getDouble("COSTO"), rs2.getString("NOMBRE"), rs2.getInt("ID")));
				}
				habitaciones.add(convertResultSetToHabitacion(rs, servicios));
			}
		}

		return habitaciones;
	}



	/**
	 * Metodo que obtiene la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del bebedor
	 * @return la informacion del bebedor que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Habitacion findHabitacionById(Integer id) throws SQLException, Exception 
	{
		Habitacion habitacion = null;

		//Primera sentencia
		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ID = %2$d ;", USUARIO, id);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{

			//Segudna sentencia
			String sql2 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_HABITACION = %2$d ;", USUARIO, rs.getInt("ID"));

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();


			if(rs.next()) {
				ArrayList<Servicio> servicios = new ArrayList<>();
				while(rs2.next())
				{
					servicios.add(new Servicio(rs2.getDouble("COSTO"), rs2.getString("NOMBRE"), rs2.getInt("ID")));
				}
				habitacion = convertResultSetToHabitacion(rs, servicios);
			}
		}

		return habitacion;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo bebedor en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHabitacionEmpresa(Habitacion habitacion, String idEmpresa) throws SQLException, Exception {

		numeroHabitaciones++;

		char compartida = 'T';
		if(!habitacion.isCompartida())
		{
			compartida = 'F';
		}

		String sql = String.format("INSERT INTO %1$s.HABITACION (ID, CAPACIDAD, TIPO, UBICACION, PRECIO, ID_EMPRESA, ESCOMPARTIDA, ID_PERSONA) VALUES (%2$d, %3$d, '%4$s', '%5$s', %6$d, '%7$s', '%8$s', '%9$s') ;", 
				USUARIO, 
				numeroHabitaciones, 
				habitacion.getCapacidad(),
				habitacion.getTipo(),
				habitacion.getUbicacion(),
				habitacion.getPrecio(),
				idEmpresa,
				compartida,
				"null");
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addHabitacionPersona(Habitacion habitacion, String idPersona) throws SQLException, Exception {

		char compartida = 'T';
		if(!habitacion.isCompartida())
		{
			compartida = 'F';
		}

		String sql = String.format("INSERT INTO %1$s.HABITACION (ID, CAPACIDAD, TIPO, UBICACION, PRECIO, ID_EMPRESA, ESCOMPARTIDA, ID_PERSONA) VALUES (%2$d, %3$d, '%4$s', '%5$s', %6$d, '%7$s', '%8$s', '%9$s') ;", 
				USUARIO, 
				habitacion.getId(), 
				habitacion.getCapacidad(),
				habitacion.getTipo(), 
				habitacion.getUbicacion(),
				habitacion.getPrecio(),
				"null",
				compartida,
				idPersona);
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}



	//----------------------
	//TODO UPDATE
	//----------------------

	/**
	 * Metodo que actualiza la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteHabitacion(Habitacion habitacion) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABITACIONES WHERE ID = %2$d ;", USUARIO, habitacion.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
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
	 * @return Bebedor cuyos atributos corresponden a los valores asociados a un registro particular de la tabla BEBEDORES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Habitacion convertResultSetToHabitacion(ResultSet resultSet, ArrayList<Servicio> servicios) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean compartida = false;
		if(resultSet.getString("ESCOMPARTIDA").charAt(0) == 'T')
		{
			compartida = true;
		}

		Integer capacidad = resultSet.getInt("CAPACIDAD");
		String tipo = resultSet.getString("TIPO");
		double precio =resultSet.getDouble("PRECIO");
		String ubicacion=resultSet.getString("UBICACION");
		Integer id = resultSet.getInt("ID");
		Habitacion habitacion = new Habitacion(capacidad, tipo, precio, ubicacion, compartida, servicios, id);

		return habitacion;
	}

}
