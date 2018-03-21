package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Contrato;
import vos.Usuario;

public class DAOUsuario {

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
	public DAOUsuario() {
		recursos = new ArrayList<Object>();

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
	public ArrayList<Usuario> getUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		//Primera Sentencia
		String sql = String.format("SELECT * FROM %1$s.USUARIO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())	
		{

			//Segunda sentencia
			String sql2 = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID_CLIENTE = '%2$s'", USUARIO, rs.getString("DOCUMENTO"));

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt.executeQuery();

			while (rs.next()) {
				ArrayList<Contrato> contratos = new ArrayList<Contrato>();
				while(rs2.next())
				{

					Date fechaInicio = new Date(rs2.getString("FECHAINICIO"));
					Date fechaFin = new Date(rs2.getString("FECHAFIN"));

					contratos.add(new Contrato(fechaInicio, fechaFin, rs2.getString("TIPO"), rs2.getDouble("COSTO"), rs2.getInt("ID"), rs2.getInt("ID_VIVIENDA"), rs2.getInt("ID_HABITACION"), rs2.getInt("NUMEROPERSONAS"), rs2.getString("ID_CLIENTE"), rs2.getDate("FECHA_CREACION")));
				}
				usuarios.add(convertResultSetToUsuario(rs, contratos));
			}
		}
		return usuarios;
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
	public Usuario findUsuarioById(String id) throws SQLException, Exception 
	{
		Usuario usuario = null;

		//Primera sentencia
		String sql = String.format("SELECT * FROM %1$s.USUARIO WHERE ID = '%2$d'", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		//Segunda sentencia
		String sql2 = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID_CLIENTE = '%2$s'", USUARIO, rs.getString("DOCUMENTO"));

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt.executeQuery();

		if(rs.next()) {
			ArrayList<Contrato> contratos = new ArrayList<>();
			while(rs2.next())
			{
				Date fechaInicio = new Date(rs2.getString("FECHAINICIO"));
				Date fechaFin = new Date(rs2.getString("FECHAFIN"));

				contratos.add(new Contrato(fechaInicio, fechaFin, rs2.getString("TIPO"), rs2.getDouble("COSTO"), rs2.getInt("ID"), rs2.getInt("ID_VIVIENDA") , rs2.getInt("ID_HABITACION"), rs2.getInt("NUMEROPERSONAS"), rs2.getString("ID_CLIENTE"), rs2.getDate("FECHA_CREACION")));
			}
			usuario = convertResultSetToUsuario(rs, contratos);
		}

		return usuario;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo bebedor en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception {

		char genero = 'F';
		if(usuario.isGenero())
		{
			genero = 'M';
		}

		String sql = String.format("INSERT INTO %1$s.USUARIO (DOCUMENTO, LOGIN, CONTRASENHA, CORREO, TIPODOCUMENTO, EDAD, GENERO, TIPO, APELLIDO, NOMBRE) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', %7$s, '%8$s', '%9$s', '%10$s', '%11$s')", 
				USUARIO, 
				usuario.getDocumento(), 
				usuario.getLogin(),
				usuario.getContrasenha(), 
				usuario.getCorreo(),
				usuario.getTipoDocumento(),
				usuario.getEdad(),
				genero,
				usuario.getTipo(),
				usuario.getApellido(),
				usuario.getNombre());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	//--------------------
	//TODO update usuario
	//--------------------


	/**
	 * Metodo que actualiza la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.USUARIO WHERE ID = '%2$d'", USUARIO, usuario.getDocumento());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

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
	public Usuario convertResultSetToUsuario(ResultSet resultSet, ArrayList<Contrato>contratos) throws SQLException {

		String documento = resultSet.getString("DOCUMENTO");
		String login = resultSet.getString("LOGIN");
		String contrasenha =resultSet.getString("CONTRASENHA");
		String correo=resultSet.getString("CORREO");
		String tipoDocumento = resultSet.getString("TIPODOCUMENTO");
		Integer edad = resultSet.getInt("EDAD");
		Boolean genero = resultSet.getBoolean("GENERO");
		String tipo = resultSet.getString("TIPO");
		String apellido = resultSet.getString("APELLIDO");
		String nombre = resultSet.getString("NOMBRE");
		Usuario usuario = new Usuario(login, contrasenha, documento, tipoDocumento, edad, genero, nombre, correo, tipo, apellido, contratos);

		return usuario;
	}


}
