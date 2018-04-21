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

public class RFC6DAO 
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
	public RFC6DAO() {
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
	public String RFC6( String id) throws SQLException, Exception {

		String rpta="";
		rpta+="El uso para el usuario con el id " +id +"\n";


		String sql1 = String.format("SELECT  TO_DATE(FECHAFIN, 'DD/MM/YYYY')-TO_DATE(FECHAINICIO, 'DD/MM/YYYY') AS DIAS FROM %1$s.CONTRATO WHERE ID_CLIENTE='%2$s' AND ESTADO ='Activo')" ,
				USUARIO,id);

		System.out.println(sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);

		ResultSet rs1=prepStmt1.executeQuery();

		String sql2 = String.format("SELECT  SUM(COSTO) AS GASTOTOTAL FROM %1$s.CONTRATO WHERE ID_CLIENTE='%2$s' AND ESTADO ='Activo'" ,
				USUARIO,
				id);

		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt1);

		ResultSet rs2=prepStmt2.executeQuery();

		
		
		String sql3 = String.format("SELECT NOMBRE FROM SERVICIO INNER JOIN (%1$s.CONTRATO INNER JOIN SERVICIOVIVIENDA ON %1$s.CONTRATO.ID_VIVIENDA=SERVICIOVIVIENDA.ID_VIVIENDA) ON SERVICIOVIVIENDA.IDSERVICIO=SERVICIO.ID WHERE ID_CLIENTE='%2$s'; " ,
				USUARIO,id);

		System.out.println(sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);

		ResultSet rs3=prepStmt3.executeQuery();

		
		
		if(rs1.next() && rs2.next())
		{
			String valor=rs1.getDouble("COUNT (ID)")+"";
			rpta+= "Dias: "+ valor +"\n" ;
			
			rpta+= "Gasto: "+ rs2.getDouble("COUNT (ID)")+"";
			
			rpta+=convertResultSet(rs3);
				
		}
		cerrarRecursos();
		
		
		return rpta;


	}





	public String convertResultSet(ResultSet resultSet) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)
		String rpta="Cacteristicas: ";
		
		while(resultSet.next())
		{
			rpta+=resultSet.getString("NOMBRE")+" ";
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
