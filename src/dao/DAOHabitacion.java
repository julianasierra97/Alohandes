package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Habitacion;
import vos.Servicio;
import vos.Usuario;

public class DAOHabitacion {

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

		String sql = String.format("SELECT habitacion.escompartida as escompartida, HABITACION.id as ID, HABITACION.capacidad as capacidad, HABITACION.UBICACION as ubicacion, HABITACION.PRECIO, TIPOHABITACION.TIPO from HABITACION, TIPOHABITACION, OPERADORHABITACION where HABITACION.ID = TIPOHABITACION.ID", USUARIO);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			//Segunda rentencia
			String sql2 = String.format("SELECT * FROM %1$s.SERVICIOHABITACION WHERE ID_HABITACION = %2$d", USUARIO, rs.getInt("ID"));

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt.executeQuery();

			while(rs2.next())
			{

				//tercera sentencia
				String sql3 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID = %2$d", USUARIO, rs2.getString("IDSERVICIO"));

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt.executeQuery();
				ArrayList<Servicio> servicios = new ArrayList<>();

				while(rs3.next())
				{
					servicios.add(new Servicio(rs3.getDouble("COSTO"), rs3.getString("NOMBRE"), rs3.getInt("ID")));
				}

				habitaciones.add(convertResultSetToHabitacion(rs, servicios));
			}
		}

		return habitaciones;
		//		if(rs.next()){
		//
		//			//Segudna sentencia
		//			String sql2 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_HABITACION = %2$d ;", USUARIO, rs.getInt("ID"));
		//
		//			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		//			recursos.add(prepStmt2);
		//			ResultSet rs2 = prepStmt2.executeQuery();
		//
		//			while (rs.next()) {
		//				ArrayList<Servicio> servicios = new ArrayList<>();
		//				while(rs2.next())
		//				{
		//					servicios.add(new Servicio(rs2.getDouble("COSTO"), rs2.getString("NOMBRE"), rs2.getInt("ID")));
		//				}
		//				habitaciones.add(convertResultSetToHabitacion(rs, servicios));
		//			}
		//		}


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
		String sql = String.format("SELECT habitacion.escompartida as escompartida, HABITACION.id as ID, HABITACION.capacidad as capacidad, HABITACION.UBICACION as ubicacion, HABITACION.PRECIO, TIPOHABITACION.TIPO from HABITACION, TIPOHABITACION where HABITACION.ID = TIPOHABITACION.ID AND HABITACION.id = %2$d " , USUARIO, id);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			habitacion = convertResultSetToHabitacion(rs, new ArrayList<Servicio>());

			//			//Segunda rentencia
			//			String sql2 = String.format("SELECT * FROM %1$s.SERVICIOHABITACION WHERE ID_HABITACION = %2$d", USUARIO, rs.getInt("ID"));
			//
			//			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			//			recursos.add(prepStmt2);
			//			ResultSet rs2 = prepStmt.executeQuery();
			//
			//
			//			//tercera sentencia
			//			String sql3 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID = %2$d", USUARIO, rs2.getString("ID"));
			//
			//			System.out.println(sql3);
			//			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			//			recursos.add(prepStmt3);
			//			ResultSet rs3 = prepStmt.executeQuery();
			//			ArrayList<Servicio> servicios = new ArrayList<>();
			//
			//			while(rs3.next())
			//			{
			//				servicios.add(new Servicio(rs3.getDouble("COSTO"), rs3.getString("NOMBRE"), rs3.getInt("ID")));
			//			}
			//			habitacion = convertResultSetToHabitacion(rs, servicios);
		}
		return habitacion;
	}



	public void addHabitacion(Habitacion habitacion, String idOperador) throws SQLException, Exception 
	{

		char compartida = 'T';
		if(!habitacion.isCompartida())
		{
			compartida = 'F';
		}


		try{
			conn.setAutoCommit(false);

			String sql = String.format("INSERT INTO %1$s.HABITACION (ID, CAPACIDAD, UBICACION, PRECIO, ESCOMPARTIDA) VALUES (%2$d, %3$d, '%4$s', '%5$s') ", 
					USUARIO, 
					habitacion.getId(),
					habitacion.getCapacidad(),
					habitacion.getUbicacion(),
					habitacion.getPrecio(),
					compartida);
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

			String sql2 = String.format("INSERT INTO %1$s.TIPOHABITACION (ID, TIPO) VALUES (%2$d, '%3$s')", USUARIO, habitacion.getId(), habitacion.getTipo());

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();

			for (Servicio servicio : habitacion.getServicios()) {
				String sql3 = String.format("INSERT INTO %1$s.SERVICIOHABITACION (IDSERVICIO, ID_HABITACION, COSTO) VALUES (%2$d, %3$d, %4$d)", 
						USUARIO, servicio.getId(),habitacion.getId() , servicio.getCosto());

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
			}

			String sql4 = String.format("INSERT INTO %1$s.OPERADORHABITACION (ID, ID_OPERADOR) VALUES (%2$d, '%3$s')", USUARIO, habitacion.getId(), idOperador);

			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();


			conn.commit();
		}
		catch(Exception e)
		{
			conn.rollback();
		}



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



		try {
			conn.setAutoCommit(false);

			String sql5 = String.format("DELETE FROM %1$s.CONTRATOHABITACION WHERE ID_CONTRATO = %2$d ", USUARIO, habitacion.getId());

			System.out.println(sql5);

			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();


			String sql4 = String.format("DELETE FROM %1$s.OPERADORHABITACION WHERE ID = %2$d ", USUARIO, habitacion.getId());

			System.out.println(sql4);

			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			prepStmt4.executeQuery();


			String sql3 = String.format("DELETE FROM %1$s.SERVICIOHABITACION WHERE ID_HABITACION = %2$d ", USUARIO, habitacion.getId());

			System.out.println(sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			prepStmt3.executeQuery();


			String sql2 = String.format("DELETE FROM %1$s.TIPOHABITACION WHERE ID = %2$d ", USUARIO, habitacion.getId());

			System.out.println(sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();


			String sql = String.format("DELETE FROM %1$s.HABITACION WHERE ID = %2$d ", USUARIO, habitacion.getId());

			System.out.println(sql);


			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();


			conn.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		Integer id = resultSet.getInt("ID");
		Habitacion habitacion = new Habitacion(capacidad, tipo, precio, "", compartida, servicios, id);

		return habitacion;
	}



	public List<Habitacion> darHabitacionesPorIdUsuario(String idUsuario) throws SQLException {
		ArrayList<Habitacion> habitaciones = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		//Primera sentencia

		String sql = String.format("SELECT habitacion.escompartida as escompartida, HABITACION.id as ID, HABITACION.capacidad as capacidad, HABITACION.PRECIO, TIPOHABITACION.TIPO from HABITACION, TIPOHABITACION, OPERADORHABITACION where HABITACION.ID = TIPOHABITACION.ID AND OPERADORHABITACION.ID_OPERADOR='%2$s' AND OPERADORHABITACION.ID=HABITACION.ID", USUARIO,idUsuario);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			//			//Segunda rentencia
			//			String sql2 = String.format("SELECT * FROM %1$s.SERVICIOHABITACION WHERE ID_HABITACION = %2$d", USUARIO, rs.getInt("ID"));
			//
			//			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			//			recursos.add(prepStmt2);
			//			ResultSet rs2 = prepStmt.executeQuery();



			//				//tercera sentencia
			//				String sql3 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID = %2$d", USUARIO, rs2.getInt("ID"));
			//
			//				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			//				recursos.add(prepStmt3);
			//				ResultSet rs3 = prepStmt.executeQuery();
			//				ArrayList<Servicio> servicios = new ArrayList<>();
			//
			//				while(rs3.next())
			//				{
			//					servicios.add(new Servicio(rs3.getDouble("COSTO"), rs3.getString("NOMBRE"), rs3.getInt("ID")));
			//				}

			habitaciones.add(convertResultSetToHabitacion(rs, null));

		}

		return habitaciones;
	}

}