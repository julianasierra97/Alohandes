package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Habitacion;
import vos.Seguro;
import vos.Servicio;
import vos.Vivienda;

public class DAOVivienda {

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

	private int contadorNumeroVivivendas;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public DAOVivienda() {
		recursos = new ArrayList<Object>();
		contadorNumeroVivivendas=1;
	}

	/**
	 * Metodo que obtiene la informacion de todos los bebedores en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Vivienda> getViviendas() throws SQLException, Exception {
		ArrayList<Vivienda> viviendas = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		//Primera sentencia
		String sql = String.format("SELECT * FROM %1$s.VIVIENDA", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{

			//Segudna sentencia
			String sql2 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_VIVIENDA = %2$d ", USUARIO, rs.getInt("ID"));

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();


			//tercera sentencia
			String sql3 = String.format("SELECT * FROM %1$s.SEGURO WHERE ID = %2$d ", USUARIO, rs.getInt("ID_SEGURO"));

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();

			while (rs.next()) {
				ArrayList<Servicio> servicios = new ArrayList<>();
				while(rs2.next())
				{
					servicios.add(new Servicio(rs2.getDouble("COSTO"), rs2.getString("NOMBRE"), rs2.getInt("ID")));
				}

				boolean incendio = false;
				if(rs3.getString("INCENDIO").charAt(0) == 'T')
				{
					incendio = true;
				}
				boolean robo = false;
				if(rs3.getString("ROBO").charAt(0) == 'T')
				{
					robo = true;
				}
				boolean inundaciones = false;
				if(rs3.getString("INUNDACION").charAt(0) == 'T')
				{
					inundaciones = true;
				}

				Seguro seguro = new Seguro(rs3.getDouble("COSTO"), incendio, robo, inundaciones, rs3.getInt("ID"));

				viviendas.add(convertResultSetToVivienda(rs, servicios, seguro));
			}
		}

		return viviendas;
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
	public Vivienda findViviendaById(Integer id) throws SQLException, Exception 
	{
		Vivienda vivienda = null;

		//Primera sentencia
		String sql = String.format("SELECT * FROM %1$s.VIVIENDA WHERE ID = %2$d ", USUARIO, id);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{

			//			//Segudna sentencia
			//			String sql2 = String.format("SELECT * FROM SERVICIO WHERE ID_VIVIENDA = %2$d ", USUARIO, rs.getInt("ID"));
			//
			//			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			//			recursos.add(prepStmt2);
			//			ResultSet rs2 = prepStmt2.executeQuery();
			//
			//			//tercera sentencia
			//			String sql3 = String.format("SELECT * FROM %1$s.SEGURO WHERE ID = %2$d ", USUARIO, rs.getInt("ID_SEGURO"));
			//
			//			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			//			recursos.add(prepStmt3);
			//			ResultSet rs3 = prepStmt3.executeQuery();
			//
			//
			//			if(rs.next()) {
			//				ArrayList<Servicio> servicios = new ArrayList<>();
			//				while(rs2.next())
			//				{
			//					servicios.add(new Servicio(rs2.getDouble("COSTO"), rs2.getString("NOMBRE"), rs2.getInt("ID")));
			//				}
			//				boolean incendio = false;
			//				if(rs3.getString("INCENDIO").charAt(0) == 'T')
			//				{
			//					incendio = true;
			//				}
			//				boolean robo = false;
			//				if(rs3.getString("ROBO").charAt(0) == 'T')
			//				{
			//					robo = true;
			//				}
			//				boolean inundaciones = false;
			//				if(rs3.getString("INUNDACION").charAt(0) == 'T')
			//				{
			//					inundaciones = true;
			//				}
			//
			//				Seguro seguro = new Seguro(rs3.getDouble("COSTO"), incendio, robo, inundaciones, rs3.getInt("ID"));
			vivienda = convertResultSetToVivienda(rs, new ArrayList<Servicio>(), null);
		}
		return vivienda;



	}


	/**
	 * Metodo que agregar la informacion de un nuevo bebedor en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addVivienda(Vivienda vivienda, String idPersona) throws SQLException, Exception {

		contadorNumeroVivivendas++;


		try{
			conn.setAutoCommit(false);

			String sql0 = String.format("INSERT INTO SEGURO(ID, COSTO, INCENDIO, ROBO, INUNDACIONES) VALUES(%2$s, %3$s, '%4$s', '%5$s', '%6$s')", USUARIO, "1010200", "100000", "F", "T", "F");

			PreparedStatement prepStmt0 = conn.prepareStatement(sql0);
			recursos.add(prepStmt0);
			prepStmt0.executeQuery();

			String sql = String.format("INSERT INTO %1$s.Vivienda(id,capacidad,tipo,direccion,numeroHabitantes,costo,id_seguro,id_persona) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', %6$s, %7$s, %8$s, '%9$s') ", 
					USUARIO, 
					vivienda.getId(), 

					vivienda.getCapacidad(),
					vivienda.getTipo(), 
					vivienda.getDireccion(),
					vivienda.getNumeroDeHabitaciones(),
					vivienda.getCosto(),
					"1010200",
					idPersona
					);
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

			for (Servicio servicio : vivienda.getServicios()) {
				String sql2 = String.format("INSERT INTO %1$s.SERVICIOVIVIENDA(IDSERVICIO, ID_VIVIENDA, COSTO) VALUES (%2$s, %3$s, %4$s) ",USUARIO, servicio.getId(), vivienda.getId(), servicio.getCosto());
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				prepStmt2.executeQuery();
			}

			conn.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			conn.rollback();
			throw e;
		}

	}

	/**
	 * Metodo que actualiza la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteVivienda(Vivienda vivienda, int idVivienda) throws SQLException, Exception {

		try {
			conn.setAutoCommit(false);

			String sql2 = String.format("DELETE FROM SERVICIOVIVIENDA WHERE ID_VIVIENDA = %2$d", USUARIO, idVivienda);

			System.out.println(sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();



			String sql1 = String.format("DELETE FROM %1$s.CONTRATOVIVIENDA WHERE ID_VIVIENDA = %2$d", USUARIO, idVivienda);

			System.out.println(sql1);

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			prepStmt1.executeQuery();



			String sql = String.format("DELETE FROM %1$s.VIVIENDA WHERE ID = %2$d", USUARIO, idVivienda);

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

		}

		catch(Exception e)
		{
			conn.rollback();
			e.printStackTrace();
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
	public Vivienda convertResultSetToVivienda(ResultSet resultSet, ArrayList<Servicio> servicios, Seguro seguro) throws SQLException {


		Integer id = resultSet.getInt("ID");
		Integer capacidad = resultSet.getInt("CAPACIDAD");
		String tipo =resultSet.getString("TIPO");
		String direccion=resultSet.getString("DIRECCION");
		Integer numeroDeHabitaciones = resultSet.getInt("NUMEROHABITANTES");
		double costo = resultSet.getDouble("COSTO");
		Vivienda vivienda  = new Vivienda(capacidad, tipo, numeroDeHabitaciones, costo, id, direccion, servicios, seguro);
		return vivienda;
	}

	public String getIdPersonaByIdVivienda(Integer idVivienda) throws SQLException  {

		//Primera sentencia
		String sql = String.format("SELECT ID_PERSONA FROM %1$s.VIVIENDA WHERE ID = %2$d ", USUARIO, idVivienda);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		return rs.getString("ID_PERSONA") + "";
	}

	public List<Vivienda> darViviendasPorIdUsuario(String id) throws SQLException {
		List<Vivienda> viviendas = new ArrayList<>();
		String sql = String.format("SELECT ID, CAPACIDAD, TIPO, DIRECCION, NUMEROHABITANTES, COSTO FROM VIVIENDA WHERE ID_persona = '%2$s'", USUARIO, id);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			Integer capacidad = rs.getInt("CAPACIDAD");
			String tipo = rs.getString("TIPO");
			Integer numeroDeHabitaciones = rs.getInt("NUMEROHABITANTES");
			Double costo = rs.getDouble("COSTO");
			String direccion = rs.getString("DIRECCION");
			Integer idV = rs.getInt("ID");
			
			Vivienda vivienda = new Vivienda(capacidad, tipo, numeroDeHabitaciones, costo, idV, direccion, new ArrayList<Servicio>(), null);
			viviendas.add(vivienda);

		}
		return viviendas;
	}


}
