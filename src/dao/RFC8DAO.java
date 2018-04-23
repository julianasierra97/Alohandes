package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Contrato;
import vos.Empresa;
import vos.Operador;
import vos.Usuario;

public class RFC8DAO 
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

	private DAOUsuario DaoUsuario;



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RFC8DAO() {
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
	public ArrayList<Usuario> RFC8( String id) throws SQLException, Exception {

		ArrayList<Usuario> rpta= new ArrayList<Usuario>();


		String sql1 = String.format("SELECT * FROM %1$s.USUARIO INNER JOIN (SELECT COUNT (%1$s.CONTRATO.ID_CLIENTE) as NumVisitas , %1$s.CONTRATO.ID_CLIENTE as IDCLIENTE FROM %1$s.CONTRATO INNER JOIN %1$s.CONTRATOVIVIENDA ON %1$s.CONTRATO.ID=%1$s.CONTRATOVIVIENDA.ID_CONTRATO WHERE %1$s.CONTRATOVIVIENDA.ID_VIVIENDA='%2$s'GROUP BY %1$s.CONTRATO.ID_CLIENTE HAVING COUNT(%1$s.CONTRATO.ID_CLIENTE)>3 ) ON IDCLIENTE= %1$s.USUARIO.DOCUMENTO;",  
				USUARIO,id);

		System.out.println(sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);

		ResultSet rs1=prepStmt1.executeQuery();
		while(rs1.next())
		{
			rpta.add(DaoUsuario.convertResultSetToUsuario(rs1, new ArrayList<Contrato>()));
		}





		String sql2 = String.format("SELECT  * from %1$s.usuario INNER JOIN(SELECT SUM(TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY')) AS DIAS, ID_CLIENTE as IDCLIENTE FROM %1$s.CONTRATO INNER JOIN %1$s.CONTRATOVIVIENDA ON %1$s.CONTRATO.ID= %1$s.CONTRATOVIVIENDA.ID_CONTRATO WHERE %1$s.CONTRATOVIVIENDA.ID_VIVIENDA=%2$s GROUP BY ID_CLIENTE HAVING SUM(TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY'))>15) on IDCLIENTE= %1$s.USUARIO.DOCUMENTO ;" ,
				USUARIO,
				id);

		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);

		ResultSet rs2=prepStmt2.executeQuery();
		while(rs2.next())
		{
			rpta.add(DaoUsuario.convertResultSetToUsuario(rs2, new ArrayList<Contrato>()));
		}

		String sql3 = String.format("SELECT * FROM %1$s.USUARIO INNER JOIN (SELECT COUNT (%1$s.CONTRATO.ID_CLIENTE) as NumVisitas , %1$s.CONTRATO.ID_CLIENTE as IDCLIENTE FROM %1$s.CONTRATO INNER JOIN %1$s.CONTRATOHABITACION ON %1$s.CONTRATO.ID=%1$s.CONTRATOHABITACION.ID_CONTRATO WHERE %1$s.CONTRATOHABITACION.ID_HABITACION='%2$s'GROUP BY %1$s.CONTRATO.ID_CLIENTE HAVING COUNT(%1$s.CONTRATO.ID_CLIENTE)>3 ) ON IDCLIENTE= %1$s.USUARIO.DOCUMENTO; ",  
				USUARIO,id);

		System.out.println(sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);

		ResultSet rs3=prepStmt3.executeQuery();
		while(rs3.next())
		{
			rpta.add(DaoUsuario.convertResultSetToUsuario(rs3, new ArrayList<Contrato>()));
		}





		String sql4 = String.format("SELECT  * from %1$s.usuario INNER JOIN(SELECT SUM(TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY')) AS DIAS, ID_CLIENTE as IDCLIENTE FROM %1$s.CONTRATO INNER JOIN %1$s.CONTRATOHABITACION ON %1$s.CONTRATO.ID= %1$s.CONTRATOHABITACION.ID_CONTRATO WHERE %1$s.CONTRATOHABITACION.ID_HABITACION='%2$s' GROUP BY ID_CLIENTE HAVING SUM(TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY'))>15) on IDCLIENTE= %1$s.USUARIO.DOCUMENTO ;" ,
				USUARIO,
				id);

		System.out.println(sql4);

		PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
		recursos.add(prepStmt4);

		ResultSet rs4=prepStmt4.executeQuery();
		while(rs4.next())
		{
			rpta.add(DaoUsuario.convertResultSetToUsuario(rs4, new ArrayList<Contrato>()));
		}


		cerrarRecursos();


		return rpta;


	}





	public String convertResultSet(ResultSet resultSet1,ResultSet resultSet2) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)
		String rpta="Cacteristicas: ";

		while(resultSet1.next())
		{
			rpta+=resultSet1.getString("NOMBRE")+", ";
		}

		while(resultSet2.next())
		{
			rpta+=resultSet2.getString("NOMBRE")+", ";
		}


		return rpta;



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
