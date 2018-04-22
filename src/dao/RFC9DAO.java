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

public class RFC9DAO 
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

	private DAOUsuario DaoUsuario;



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RFC9DAO() {
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
	public String RFC9() throws SQLException, Exception {

		String rpta="Las ofertas con poca demanda son:";


		String sql1 = String.format("SELECT  TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY') AS DIAS, %1$s.VIVIENDA.ID FROM %1$s.VIVIENDA LEFT OUTER JOIN (%1$s.CONTRATO INNER JOIN %1$s.CONTRATOVIVIENDA ON %1$s.CONTRATO.ID=%1$s.CONTRATOVIVIENDA.ID_CONTRATO) ON %1$s.VIVIENDA.ID= %1$s.CONTRATOVIVIENDA.ID_VIVIENDA WHERE TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY') <30;",  
				USUARIO);

		System.out.println(sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);

		ResultSet rs1=prepStmt1.executeQuery();
		while(rs1.next())
		{
			rpta+=rs1.getString("ID")+" ,";
		}





		String sql2 = String.format("SELECT  TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY') AS DIAS, %1$s.HABITACION.ID FROM %1$s.HABITACION LEFT OUTER JOIN (%1$s.CONTRATO INNER JOIN %1$s.CONTRATOHABITACION ON %1$s.CONTRATO.ID=%1$s.CONTRATOHABITACION.ID_CONTRATO) ON %1$s.HABITACION.ID= %1$s.CONTRATOHABITACION.ID_HABITACION WHERE TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY') <30;" ,
				USUARIO);

		System.out.println(sql2);


		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);

		ResultSet rs2=prepStmt2.executeQuery();
		while(rs2.next())
		{
			rpta+=rs2.getString("ID")+" ,";
		}

		cerrarRecursos();


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
