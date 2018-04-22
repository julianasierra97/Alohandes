package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import tm.AlohonadesTransactionManager;
import vos.Contrato;
import vos.Empresa;
import vos.Operador;
import vos.Usuario;

public class RF9DAO 
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
	
	private DAOContrato daoContrato;
	private RFC4DAO daoRFC4;
	private AlohonadesTransactionManager tm;

	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public RF9DAO() {
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
	public void RF9(String tipo, String id) throws SQLException, Exception {

		conn.setAutoCommit(false);

		if(tipo.equals("Habitacion"))
		{
			String sql = String.format("UPDATE %1$s.HABITACION SET ESTADO ='Dasabilitada' WHERE %1$s.HABITACION.ID=(SELECT ID FROM %1$s.OPERADORHABITACION WHERE ID_OPERADOR='%2$s');" ,
					USUARIO,
					id);

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);

			ResultSet rs=prepStmt.executeQuery();
					String sql2 = String.format("SELECT * FROM CONTRATO FULL OUTER JOIN (HABITACION FULL OUTER JOIN CONTRATOHABITACION ON HABITACION.ID=CONTRATOHABITACION.ID_HABITACION) ON CONTRATO.ID=CONTRATOHABITACION.ID_CONTRATO WHERE CONTRATO.ESTADO='Activo' AND HABITACION.ESTADO ='Dasabilitada' AND CONTRATO.FECHAINICIO>'01/01/15' ORDER BY CONTRATO.FECHACREACION;" ,
					USUARIO,
					id);

			System.out.println(sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);

			ResultSet rs2=prepStmt2.executeQuery();
			
			
			while( rs2.next() )
			{
				String[] sirven=daoRFC4.RFC4("", rs2.getString("FECHAINICIO"), rs2.getString("FECHAFIN")).split(" ,");
				
				daoContrato.addContratoHabitacion(daoContrato.convertResultSetToContratoHabitacion(rs2, Integer.parseInt(sirven[0])));
				conn.commit();
				tm.deleteReserva(daoContrato.convertResultSetToContrato(rs2));
				conn.commit();
				//TODO Arreglar cambiar deleteReserva
				
			}
			cerrarRecursos();
		}
		
		


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
