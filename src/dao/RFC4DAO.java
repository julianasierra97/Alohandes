package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oracle.net.aso.s;
import vos.Contrato;
import vos.Empresa;
import vos.Operador;
import vos.Usuario;

public class RFC4DAO 
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



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RFC4DAO() {
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
	public String RFC4(String servicios, String fechaComienzo, String fechaFin) throws SQLException, Exception {

		String rpta="Las ofertas que cumples con los parametros dados son: ";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "'"+dateFormat.format(fechaComienzo)+"'";
		
		
		String fecha2 = "'"+dateFormat.format(fechaFin)+"'";
		



		String sql1 = String.format("WITH Q1 AS(SELECT %1$s.CONTRATO.ID AS %1$s.CONTRATO FROM %1$s.CONTRATO WHERE '%2$s' < FECHAINICIO AND FECHAFIN < '%2$s' OR FECHAINICIO > '%3$s' AND FECHAFIN > '%3$s')SELECT * FROM  %1$s.CONTRATOHABITACION LEFT OUTER JOIN Q1 ON %1$s.CONTRATOHABITACION.ID_CONTRATO  = Q1.CONTRATO WHERE Q1.CONTRATO IS NULL;",
				USUARIO);

		System.out.println(sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);

		ResultSet rs1=prepStmt1.executeQuery();
		String[] losServicios= servicios.split(",");
		while(rs1.next())
		{

			String sql2 = String.format("SELECT NOMBRE FROM %1$s.SERVICIOHABITACION LEFT OUTER JOIN %1$s.SERVICIO ON %1$s.SERVICIO.ID= %1$s.SERVICIOHABITACION.ID_HABITACION  WHERE %1$s.SERVICIOHABITACION.ID_HABITACION='%2$s';" ,

					USUARIO,
					rs1.getString("ID_HABITACION"));
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);


			int contador=0;
			ResultSet rs2=prepStmt2.executeQuery();
			String palabra="";
			while(rs2.next())
			{
				palabra+=rs2.getString("NOMBRE");

			}
			for(String s:losServicios)
			{
				if(palabra.contains(s))
				{
					contador++;
				}
			}
			if(losServicios.length==contador)
			{
				rpta+=rs1.getString("ID_HABITACION")+" ,";
			}


		}


		String sql3 = String.format("WITH Q1 AS(SELECT %1$s.CONTRATO.ID AS %1$s.CONTRATO FROM %1$s.CONTRATO WHERE '%2$s' < FECHAINICIO AND FECHAFIN < '%2$s' OR FECHAINICIO > '%3$s' AND FECHAFIN > '%3$s')SELECT * FROM  %1$s.CONTRATOHABITACION LEFT OUTER JOIN Q1 ON %1$s.CONTRATOHABITACION.ID_CONTRATO  = Q1.CONTRATO WHERE Q1.CONTRATO IS NULL;",
				USUARIO,
				fechaComienzo,
				fechaFin);

		System.out.println(sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);

		ResultSet rs3=prepStmt1.executeQuery();

		while(rs3.next())
		{
			String sql2 = String.format("SELECT NOMBRE FROM %1$s.SERVICIOVIVIENDA LEFT OUTER JOIN %1$s.SERVICIO ON %1$s.SERVICIO.ID= %1$s.SERVICIOVIVENDA.ID_VIVIENDA  WHERE %1$s.SERVICIOVIVIENDA.ID_VIVIENDA='%2$s';" ,

					USUARIO,
					rs1.getString("ID_VIVIENDA"));
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);


			int contador=0;
			ResultSet rs2=prepStmt2.executeQuery();
			String palabra="";
			while(rs2.next())
			{
				palabra+=rs2.getString("NOMBRE");

			}
			for(String s:losServicios)
			{
				if(palabra.contains(s))
				{
					contador++;
				}
			}
			if(losServicios.length==contador)
			{
				rpta+=rs1.getString("ID_VIVIENDA")+" ,";
			}
		}


		return rpta;



	}





	public String convertResultSetActualToString(ResultSet resultSet, ResultSet resultSet2) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)
		int numero= resultSet.getInt("GananciaActualHabitacion");
		resultSet.getString("ID");

		int numero2=resultSet2.getInt("GananciaActualVivienda");
		resultSet.getString("ID");

		numero+= numero2;
		return "El usuario con el ID "+ 	resultSet.getString("ID")+ "gano "+ numero+""+ " en este año ";




	}
	public String convertResultSetPasadoToString(ResultSet resultSet, ResultSet resultSet2) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)
		int numero= resultSet.getInt("GananciaPasadalHabitacion");
		resultSet.getString("ID");

		int numero2=resultSet2.getInt("GananciaPasadaVivienda");
		resultSet.getString("ID");

		numero+= numero2;
		return "El usuario con el ID "+ 	resultSet.getString("ID")+ "gano "+ numero+""+ " en este año ";




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
