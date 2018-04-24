package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
			
			
			try {
			String sql0 = String.format("SELECT ID FROM %1$s.OPERADORHABITACION WHERE ID_OPERADOR='%2$s'" ,
					USUARIO,
					id);

			System.out.println(sql0);

			PreparedStatement prepStmt0 = conn.prepareStatement(sql0);
			recursos.add(prepStmt0);

			ResultSet rs=prepStmt0.executeQuery();
			if(rs.wasNull())
			{
				throw new Exception ("No hay operadores con ese id");
			}
			else
			{
			while(rs.next())
			{
				String sql = String.format("UPDATE %1$s.HABITACION SET ESTADO ='Dasabilitada' WHERE %1$s.HABITACION.ID=%2$s" ,
						USUARIO,
						rs.getString("ID"));

				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);

				prepStmt.executeQuery();
			}
			
			
					String sql2 = String.format("SELECT * FROM CONTRATO FULL OUTER JOIN (HABITACION FULL OUTER JOIN CONTRATOHABITACION ON HABITACION.ID=CONTRATOHABITACION.ID_HABITACION) ON CONTRATO.ID=CONTRATOHABITACION.ID_CONTRATO WHERE CONTRATO.ESTADO='Activo' AND HABITACION.ESTADO ='Dasabilitada' AND CONTRATO.FECHAINICIO>'01/01/15' ORDER BY CONTRATO.FECHACREACION" ,
					USUARIO,
					id);

			System.out.println(sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);

			ResultSet rs2=prepStmt2.executeQuery();
			
			
			while( rs2.next() )
			{
				daoRFC4= new RFC4DAO();
				daoRFC4.setConn(conn);
				System.out.println("HOLA");
				
				String fechainicio=rs2.getString("FECHAINICIO");
				String fechaFin=rs2.getString("FECHAFIN");
				String fif = fechainicio.substring(2, 10);
				String [] array = fif.split("-");
				int anho = Integer.parseInt(array[0])+100;
				int mes = Integer.parseInt(array[1])-1;
				int dia = Integer.parseInt(array[2]);
				Date date1 = new Date(anho, mes, dia);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha1 = dateFormat.format(date1);
				
				String fif2 = fechaFin.substring(2, 10);
				String [] array2 = fif2.split("-");
				int anho2 = Integer.parseInt(array[0])+100;
				int mes2 = Integer.parseInt(array[1])-1;
				int dia2 = Integer.parseInt(array[2]);
				Date date2 = new Date(anho2, mes2, dia2);
				String fecha2 = dateFormat.format(date2);
				
			
				String idHabitacion= rs2.getString("ID_HABITACION");
				
				String sql3 = String.format("select NOMBRE FROM SERVICIOHABITACION INNER JOIN SERVICIO ON SERVICIO.ID=SERVICIOHABITACION.IDSERVICIO WHERE SERVICIOHABITACION.ID_HABITACION='%1$s'" ,
						idHabitacion);

				System.out.println(sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);

				ResultSet rs3=prepStmt3.executeQuery();
				
				String servicios="";
				while(rs3.next())
				{
					servicios+= rs3.getString("NOMBRE")+",";
				}
			
				
				String buenas=daoRFC4.RFC4(servicios, fecha1,fecha2);
				
				String[] sirven=buenas.substring(55, buenas.length()).split(" ,");
				System.out.println("HOLA2");
				daoContrato= new DAOContrato();
				int contador=0;
				daoContrato.setConn(conn);
				
				
				daoContrato.deleteContratoHabitacion(daoContrato.convertResultSetToContratoHabitacion(rs2, Integer.parseInt(sirven[contador])));
				conn.commit();
				
				daoContrato.addContratoHabitacion(daoContrato.convertResultSetToContratoHabitacion(rs2, Integer.parseInt(sirven[contador])));
				conn.commit();
				contador++;
				System.out.println("HOLA3");
				
				
				System.out.println("HOLA4");
				
				//TODO Arreglar cambiar deleteReserva
				
			}
			cerrarRecursos();
			}
			}catch (SQLException e) {
				if (e.getMessage().equals("No hay lectura de datos")) {
					throw new Exception("No hay un operador con ese id");
				}
			}
		}
		if(tipo.equals("Vivienda"))
		{
		
			String sql0 = String.format("SELECT ID FROM %1$s.OPERADORVIVIENDA WHERE ID_OPERADOR='%2$s'" ,
					USUARIO,
					id);

			System.out.println(sql0);

			PreparedStatement prepStmt0 = conn.prepareStatement(sql0);
			recursos.add(prepStmt0);

			ResultSet rs=prepStmt0.executeQuery();
			while(rs.next())
			{
				String sql = String.format("UPDATE %1$s.VIVIENDA SET ESTADO ='Dasabilitada' WHERE %1$s.VIVIENDA.ID=%2$s" ,
						USUARIO,
						rs.getString("ID"));

				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);

				prepStmt.executeQuery();
			}
			
			
					String sql2 = String.format("SELECT * FROM CONTRATO FULL OUTER JOIN (VIVIENDA FULL OUTER JOIN CONTRATOVIVIENDA ON VIVIENDA.ID=CONTRATOVIVIENDA.ID_VIVIENDA) ON CONTRATO.ID=CONTRATOVIVIENDA.ID_CONTRATO WHERE CONTRATO.ESTADO='Activo' AND VIVIENDA.ESTADO ='Dasabilitada' AND CONTRATO.FECHAINICIO>'01/01/15' ORDER BY CONTRATO.FECHACREACION" ,
					USUARIO,
					id);

			System.out.println(sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);

			ResultSet rs2=prepStmt2.executeQuery();
			
			
			while( rs2.next() )
			{
				daoRFC4= new RFC4DAO();
				daoRFC4.setConn(conn);
				System.out.println("HOLA");
				
				String fechainicio=rs2.getString("FECHAINICIO");
				String fechaFin=rs2.getString("FECHAFIN");
				String fif = fechainicio.substring(2, 10);
				String [] array = fif.split("-");
				int anho = Integer.parseInt(array[0])+100;
				int mes = Integer.parseInt(array[1])-1;
				int dia = Integer.parseInt(array[2]);
				Date date1 = new Date(anho, mes, dia);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha1 = dateFormat.format(date1);
				
				String fif2 = fechaFin.substring(2, 10);
				String [] array2 = fif2.split("-");
				int anho2 = Integer.parseInt(array[0])+100;
				int mes2 = Integer.parseInt(array[1])-1;
				int dia2 = Integer.parseInt(array[2]);
				Date date2 = new Date(anho2, mes2, dia2);
				String fecha2 = dateFormat.format(date2);
				
			
				String idHabitacion= rs2.getString("ID_VIVIENDA");
				
				String sql3 = String.format("select NOMBRE FROM SERVICIOVIVIENDA INNER JOIN SERVICIO ON SERVICIO.ID=SERVICIOVIVIENDA.IDSERVICIO WHERE SERVICIOVIVIENDA.ID_VIVIENDA='%1$s'" ,
						idHabitacion);

				System.out.println(sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);

				ResultSet rs3=prepStmt3.executeQuery();
				
				String servicios="";
				while(rs3.next())
				{
					servicios+= rs3.getString("NOMBRE")+",";
				}
			
				
				String buenas=daoRFC4.RFC4(servicios, fecha1,fecha2);
				
				String[] sirven=buenas.substring(55, buenas.length()).split(" ,");
				System.out.println("HOLA2");
				daoContrato= new DAOContrato();
				int contador=0;
				daoContrato.setConn(conn);
				
				
				daoContrato.deleteContratoVivienda(daoContrato.convertResultSetToContratoVivivenda(rs2, Integer.parseInt(sirven[contador])));
				conn.commit();
				
				daoContrato.addContratoVivienda(daoContrato.convertResultSetToContratoVivivenda(rs2, Integer.parseInt(sirven[contador])));
				conn.commit();
				contador++;
				System.out.println("HOLA3");
				
				
				System.out.println("HOLA4");
				
				//TODO Arreglar cambiar deleteReserva
				
			}
			cerrarRecursos();
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





}
