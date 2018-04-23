package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;
import vos.Operador;
import vos.PersonaNatural;
import vos.Seguro;
import vos.Servicio;
import vos.Vivienda;

public class DAOPersonaNatural {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
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
	public DAOPersonaNatural() {
		recursos = new ArrayList<Object>();
	}


	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<PersonaNatural> getPersonasNatural() throws SQLException, Exception {
		ArrayList<PersonaNatural> personas = new ArrayList<PersonaNatural>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sqlA = String.format("SELECT * FROM %1$s.PERSONANATURAL PN, %1$s.OPERADOR OP WHERE ROWNUM <= 50 AND PN.DOCUMENTO = OP.DOCUMENTO", USUARIO);

		PreparedStatement prepStmtA = conn.prepareStatement(sqlA);
		recursos.add(prepStmtA);
		ResultSet rsA = prepStmtA.executeQuery();

		if(rsA.next())
		{
			//Segunda sentencia

			String sql2 = String.format("SELECT * FROM %1$s.VIVIENDA  WHERE ID_PERSONA = '%2$s'", USUARIO, rsA.getString("DOCUMENTO")); 

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			if(rs2.next())
			{

				//Tercera sentencia

				String sql3 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_VIVIENDA = %2$d", USUARIO, rs2.getInt("ID")); 

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();

				//cuarta sentencia

				String sql4 = String.format("SELECT * FROM %1$s.SEGURO WHERE ID_VIVIENDA = %2$d", USUARIO, rs2.getInt("ID")); 

				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet rs4 = prepStmt4.executeQuery();

				//quinta sentencia

				String sql5 = String.format("SELECT * FROM %1$s.HABITACION WHERE ID_PERSONA = '%2$s'", USUARIO, rsA.getString("DOCUMENTO")); 

				PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
				recursos.add(prepStmt5);
				ResultSet rs5 = prepStmt5.executeQuery();

				//sexta sentencia

				String sql6 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_HABITACION = %2$d", USUARIO, rs2.getInt("ID")); 

				PreparedStatement prepStmt6 = conn.prepareStatement(sql6);
				recursos.add(prepStmt6);
				ResultSet rs6 = prepStmt6.executeQuery();

				while (rsA.next()) {

					ArrayList<Vivienda> viviendas = new ArrayList<>();
					while(rs2.next())
					{
						ArrayList<Servicio> servicios = new ArrayList<>();
						while(rs3.next())
						{
							servicios.add(new Servicio(rs3.getDouble("COSTO"), rs3.getString("NOMBRE"), rs3.getInt("ID")));
						}

						boolean incendio = false;

						boolean robo = false;

						boolean inundaciones = false;

						if(rs4.next()) 
						{

							if(rs4.getString("INCENDIO").charAt(0) == 'T')
							{
								incendio = true;
							}
							if(rs4.getString("ROBO").charAt(0) == 'T')
							{
								robo = true;
							}
							if(rs4.getString("INUNDACION").charAt(0) == 'T')
							{
								inundaciones = true;
							}

						}
						viviendas.add(new Vivienda(rs2.getInt("CAPACIDAD"), rs2.getString("TIPO"), rs2.getInt("NUMEROHABITACIONES"), rs2.getDouble("COSTO"), rs2.getInt("ID"), rs2.getString("DIRECCION"), servicios, new Seguro(rs4.getDouble("COSTO"), incendio, robo, inundaciones, rs4.getInt("ID"))));

					}

					ArrayList<Habitacion> habitaciones = new ArrayList<>();
					while(rs5.next())
					{
						ArrayList<Servicio> servicios = new ArrayList<>();
						while(rs6.next())
						{
							servicios.add(new Servicio(rs6.getDouble("COSTO"), rs6.getString("NOMBRE"), rs6.getInt("ID")));
						}
						boolean compartida = false;
						if(rs5.getString("ESCOMPARTIDA").charAt(0) == 'T')
						{
							compartida = true;
						}
						habitaciones.add(new Habitacion(rs5.getInt("CAPACIDAD"), rs5.getString("TIPO"), rs5.getDouble("PRECIO"), rs5.getString("UBICACION"), compartida, servicios, rs5.getInt("ID")));
					}

					personas.add(convertResultSetToPersonaNatural(rsA, habitaciones, viviendas));
				}
			}
		}
		return personas;
	}


	public PersonaNatural findPersonaById(String id) throws SQLException, Exception
	{
		PersonaNatural persona = null;

		//Primera sentencia 
		String sql1 = String.format("SELECT op.LOGIN as login, op.contrasenha as contrasenha, op.documento as documento, op.correo as correo,op.TIPODOCUMENTO as tipodocumento, op.nombre as nombre, pn.APELLIDO as apellido, pn.EDAD as edad, pn.GENERO as genero, pn.TIPO as tipo, pn.tipo as tipo, oh.ID as idHabitacion, VIV.ID as idVivienda  FROM OPERADOR op, PERSONANATURAL pn, OPERADORHABITACION OH, VIVIENDA VIV WHERE OP.DOCUMENTO=pn.DOCUMENTO AND pn.DOCUMENTO='%2$s' AND OH.ID_OPERADOR = pn.DOCUMENTO AND pn.DOCUMENTO = VIV.ID_PERSONA ", USUARIO, id);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();

		while(rs1.next())
		{
			ArrayList<Habitacion> habitaciones = new ArrayList<>();
			ArrayList<Vivienda> viviendas = new ArrayList<>();

			//segunda sentencia


			persona = convertResultSetToPersonaNatural(rs1, habitaciones, viviendas);
		}

		return persona;


		//		//Primera sentencia
		//
		//		PersonaNatural persona = null;
		//
		//		String sql = String.format("SELECT * FROM %1$s.PERSONANATURAL PN, %1$s.OPERADOR OPE  WHERE PN.DOCUMENTO = '%2$s' AND OPE.DOCUMENTO = '%2$s'", USUARIO, id); 
		//
		//		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//		recursos.add(prepStmt);
		//		ResultSet rs = prepStmt.executeQuery();
		//
		//		//Segunda sentencia
		//
		//		String sql2 = String.format("SELECT * FROM %1$s.VIVIENDA  WHERE ID_PERSONA = '%2$s'", USUARIO, id); 
		//
		//		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		//		recursos.add(prepStmt2);
		//		ResultSet rs2 = prepStmt2.executeQuery();
		//
		//		//Tercera sentencia
		//		rs2.next();
		//		String sql3 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_VIVIENDA = %2$s", USUARIO, rs2.getInt("ID")); 
		//
		//		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		//		recursos.add(prepStmt3);
		//		ResultSet rs3 = prepStmt3.executeQuery();
		//
		//		//cuarta sentencia
		//
		//		String sql4 = String.format("SELECT * FROM %1$s.SEGURO WHERE ID_VIVIENDA = %2$s", USUARIO, rs2.getInt("ID")); 
		//
		//		PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
		//		recursos.add(prepStmt4);
		//		ResultSet rs4 = prepStmt4.executeQuery();
		//
		//		//quinta sentencia
		//
		//		String sql5 = String.format("SELECT * FROM %1$s.HABITACION WHERE ID_PERSONA = '%2$s'", USUARIO, id); 
		//
		//		PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
		//		recursos.add(prepStmt5);
		//		ResultSet rs5 = prepStmt5.executeQuery();
		//
		//		//sexta sentencia
		//
		//		String sql6 = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID_HABITACION = %2$s", USUARIO, rs2.getInt("ID")); 
		//
		//		PreparedStatement prepStmt6 = conn.prepareStatement(sql6);
		//		recursos.add(prepStmt6);
		//		ResultSet rs6 = prepStmt6.executeQuery();
		//
		//		ArrayList<Vivienda> viviendas = new ArrayList<>();
		//		while(rs2.next())
		//		{
		//			ArrayList<Servicio> servicios = new ArrayList<>();
		//			while(rs3.next())
		//			{
		//				servicios.add(new Servicio(rs3.getDouble("COSTO"), rs3.getString("NOMBRE"), rs3.getInt("ID")));
		//			}
		//
		//			boolean incendio = false;
		//
		//			boolean robo = false;
		//
		//			boolean inundaciones = false;
		//
		//			if(rs4.next())
		//			{
		//				if(rs4.getString("INCENDIO").charAt(0) == 'T')
		//				{
		//					incendio = true;
		//				}
		//				if(rs4.getString("ROBO").charAt(0) == 'T')
		//				{
		//					robo = true;
		//				}
		//				if(rs4.getString("INUNDACION").charAt(0) == 'T')
		//				{
		//					inundaciones = true;
		//				}
		//			}
		//			viviendas.add(new Vivienda(rs2.getInt("CAPACIDAD"), rs2.getString("TIPO"), rs2.getInt("NUMEROHABITACIONES"), rs2.getDouble("COSTO"), rs2.getInt("ID"), rs2.getString("DIRECCION"), servicios, new Seguro(rs4.getDouble("COSTO"), incendio, robo, inundaciones, rs4.getInt("ID"))));
		//		}
		//
		//		ArrayList<Habitacion> habitaciones = new ArrayList<>();
		//		while(rs5.next())
		//		{
		//			ArrayList<Servicio> servicios = new ArrayList<>();
		//			while(rs6.next())
		//			{
		//				servicios.add(new Servicio(rs6.getDouble("COSTO"), rs6.getString("NOMBRE"), rs6.getInt("ID")));
		//			}
		//			boolean compartida = false;
		//			if(rs5.next()&&rs5.getString("ESCOMPARTIDA").charAt(0) == 'T')
		//			{
		//				compartida = true;
		//			}
		//			habitaciones.add(new Habitacion(rs5.getInt("CAPACIDAD"), rs5.getString("TIPO"), rs5.getDouble("PRECIO"), rs5.getString("UBICACION"), compartida, servicios, rs5.getInt("ID")));
		//		}
		//
		//		if(rs.next())
		//		{
		//			persona = convertResultSetToPersonaNatural(rs, habitaciones, viviendas);
		//		}

		//		String sql = String.format("SELECT * FROM %1$s.PERSONANATURAL PN , %1$s.VIVIENDA VIV    WHERE PN.DOCUMENTO = %2$d AND VIV.ID_PERSONA = %2$d", USUARIO, id); 
		//
		//		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//		recursos.add(prepStmt);
		//		ResultSet rs = prepStmt.executeQuery();
		//		
		//		String sql2 = String.format("SELECT SER.*, SEG.* FROM %1$s.VIVIENDA VIV , %1$s.SERVICIO SER , %1$s.SEGURO SEG WHERE VIV.ID = %2$d AND SER.ID_VIVIENDA = %2$d AND VIV.ID_SEGURO = SEG.ID", USUARIO, rs.getInt("ID")); 
		//
		//		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		//		recursos.add(prepStmt);
		//		ResultSet rs2 = prepStmt.executeQuery();
		//		
		//		
		//
		//		if(rs.next()) {
		//			ArrayList<Servicio> servicios = new ArrayList<>();
		//			while(rs2.next())
		//			{
		//				servicios.add(new Servicio(rs2.getDouble("SER.COSTO"), rs2.getString("SER.NOMBRE"), rs2.getInt("SER.ID")));
		//			}
		//			ArrayList<Vivienda> viviendas = new ArrayList<>();
		//			viviendas.add(new Vivienda(rs.getInt("CAPACIDAD"), rs.getString("TIPO"), rs.getInt("NUMEROHABITACIONES"), rs.getDouble("COSTO"), id, rs.getString("DIRECCION"), servicios, seguro));
		//			persona = convertResultSetToPersonaNatural(rs, , viviendas);
		//		}

	}

	/**
	 * Metodo que agregar la informacion de un nuevo operador en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addPersonaNatural(PersonaNatural persona) throws SQLException, Exception {

		char genero = 'F';
		if(persona.isGenero())
		{
			genero = 'M';
		}

		conn.setAutoCommit(false);




		//Segunda sentencia
		try {
			String sql2 = String.format("INSERT INTO OPERADOR (DOCUMENTO, LOGIN, CONTRASENHA, CORREO, TIPODOCUMENTO, NOMBRE) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
					USUARIO,  
					persona.getDocumento(), 
					persona.getLogin(),
					persona.getContrasenha(), 
					persona.getCorreo(),
					persona.getTipoDocumento(),
					persona.getNombre());

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();

			//Primera sentencia
			String sql = String.format("INSERT INTO PERSONANATURAL (DOCUMENTO, TIPO, EDAD, GENERO, APELLIDO) VALUES ('%2$s', '%3$s', %4$d, '%5$s', '%6$s')", 
					USUARIO,  
					persona.getDocumento(), 
					persona.getTipo(),
					persona.getEdad(), 
					genero,
					persona.getApellido());


			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			conn.commit();
		}
		catch(Exception e)
		{
			conn.rollback();
		}





		//		for (Habitacion habitacion : persona.getHabitaciones()) {
		//			String sql2 = String.format("INSERT INTO %1$s.OPERADORHABITACION (ID, ID_OPERADOR) VALUES (%2$d, '%3$s')", USUARIO, habitacion.getId(), persona.getDocumento());
		//
		//			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		//			recursos.add(prepStmt2);
		//			prepStmt2.executeQuery();
		//		}
		//		
		//		for (Vivienda vivienda : persona.getVivienda()) {
		//			String sql3 = String.format("INSERT INTO %1$s.OPERADORHABITACION (ID, ID_OPERADOR) VALUES (%2$d, '%3$s')", USUARIO, habitacion.getId(), persona.getDocumento());
		//
		//			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		//			recursos.add(prepStmt3);
		//			prepStmt3.executeQuery();
		//		}

	}

	//--------------------------
	//TODO update 
	//--------------------------

	public void deletePersonaNatural(PersonaNatural persona) throws SQLException, Exception{

		String sql = String.format("DELETE FROM %1$s.PERSONANATURAL WHERE ID = '%2$d'", USUARIO, persona.getDocumento());

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
	 * @return Operador cuyos atributos corresponden a los valores asociados a un registro particular de la tabla BEBEDORES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public PersonaNatural convertResultSetToPersonaNatural(ResultSet resultSet, ArrayList<Habitacion> habitaciones, ArrayList<Vivienda> viviendas) throws SQLException {

		//TODO Aca puede haber un error, preguntar.

		String documento = resultSet.getString("DOCUMENTO");
		String tipo = resultSet.getString("TIPO");
		Integer edad =resultSet.getInt("EDAD");
		Boolean genero=resultSet.getBoolean("GENERO");
		String apellido=resultSet.getString("APELLIDO");
		String login = resultSet.getString("LOGIN");
		String contrasenha = resultSet.getString("CONTRASENHA");
		String tipoDocumento = resultSet.getString("TIPODOCUMENTO");
		String nombre = resultSet.getString("NOMBRE");
		String correo = resultSet.getString("CORREO");

		PersonaNatural pn = new PersonaNatural(login, contrasenha, documento, tipoDocumento, nombre, tipo, habitaciones, viviendas, edad, genero, correo, apellido);

		return pn;
	}

}
