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

public class RFC1DAO 
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
	public RFC1DAO() {
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
	public String RFC1() throws SQLException, Exception {

		String rpta="";
		rpta+="En el año actual:"+"\n";
			

		Date fechaActual= new Date();
		Integer elAnio= fechaActual.getYear();
		elAnio-=2000;
		String anio=elAnio+"";
		
	

		String sql = String.format("SELECT SUM(COSTO) as GananciaActualHabitacion,  %1$s.OPERADORHABITACION.ID_OPERADOR as ID FROM %1$s.CONTRATO  INNER JOIN %1$s.OPERADORHABITACION ON %1$s.CONTRATO.ID_HABITACION= %1$s.OPERADORHABITACION.ID  WHERE %1$s.CONTRATO.FECHAFIN< '31/12/%2$s' AND %1$s.CONTRATO.FECHAINICIO>'01/01/%2$s' AND %1$s.CONTRATO.ESTADO='Activo' GROUP BY %1$s.OPERADORHABITACION.ID_OPERADOR; " ,
				USUARIO,
				anio);

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);

		ResultSet rs=prepStmt.executeQuery();
		String sql2 = String.format("SELECT SUM(%1$s.CONTRATO.COSTO) as GananciaActualVivienda, %1$s.VIVIENDA.ID_PERSONA as ID FROM (%1$s.CONTRATO INNER JOIN %1$s.CONTRATOVIVIENDA ON %1$s.CONTRATO.ID=%1$s.CONTRATOVIVIENDA.ID_CONTRATO) INNER JOIN %1$s.VIVIENDA ON %1$s.CONTRATOVIVIENDA.ID_VIVIENDA= %1$s.VIVIENDA.ID WHERE %1$s.CONTRATO.FECHAFIN< '31/12/%2$s' AND %1$s.CONTRATO.FECHAINICIO>'01/01/%2$s' AND %1$s.CONTRATO.ESTADO='Activo' GROUP BY %1$s.VIVIENDA.ID_PERSONA ;" ,
				USUARIO,
				anio);

		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);

		ResultSet rs2=prepStmt2.executeQuery();
		while(rs.next()&& rs2.next() )
		{
			rpta+=convertResultSetActualToString(rs, rs2) +"\n";
		}
		cerrarRecursos();
		
		
		String elAnioPasado= elAnio-1+"";

		String sql3 = String.format("SELECT SUM(COSTO) as GananciaPasadaHabitacion,  %1$s.OPERADORHABITACION.ID_OPERADOR as ID FROM %1$s.CONTRATO  INNER JOIN %1$s.OPERADORHABITACION ON %1$s.CONTRATO.ID_HABITACION= %1$s.OPERADORHABITACION.ID  WHERE %1$s.CONTRATO.FECHAFIN< '31/12/%2$s' AND %1$s.CONTRATO.FECHAINICIO>'01/01/%2$s' AND %1$s.CONTRATO.ESTADO='Activo' GROUP BY %1$s.OPERADORHABITACION.ID_OPERADOR;" ,
				USUARIO,
				elAnioPasado);

		System.out.println(sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);

		ResultSet rs3=prepStmt3.executeQuery();
		String sql4 = String.format("SELECT SUM(%1$s.CONTRATO.COSTO) as GananciaActualVivienda, %1$s.VIVIENDA.ID_PERSONA as ID FROM (%1$s.CONTRATO INNER JOIN %1$s.CONTRATOVIVIENDA ON %1$s.CONTRATO.ID=%1$s.CONTRATOVIVIENDA.ID_CONTRATO) INNER JOIN %1$s.VIVIENDA ON %1$s.CONTRATOVIVIENDA.ID_VIVIENDA= %1$s.VIVIENDA.ID WHERE %1$s.CONTRATO.FECHAFIN< '31/12/%2$s' AND %1$s.CONTRATO.FECHAINICIO>'01/01/%2$s' AND %1$s.CONTRATO.ESTADO='Activo' GROUP BY %1$s.VIVIENDA.ID_PERSONA ;" ,
				USUARIO,
				elAnioPasado);

		System.out.println(sql4);

		PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
		recursos.add(prepStmt4);

		ResultSet rs4=prepStmt4.executeQuery();
		while(rs3.next()&& rs4.next() )
		{
			rpta+=convertResultSetPasadoToString(rs3, rs4) +"\n";
		}
		cerrarRecursos();
		


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
