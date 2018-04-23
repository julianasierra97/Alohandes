package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Contrato;
import vos.Empresa;
import vos.Operador;
import vos.Usuario;

public class DAOContrato 
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

	private int contadorNumeroContratos;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	 */
	public DAOContrato() {
		recursos = new ArrayList<Object>();
		contadorNumeroContratos=1;
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
	public void addContratoHabitacion(Contrato contrato) throws SQLException, Exception {




		contadorNumeroContratos++;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = "'"+dateFormat.format(contrato.getFechaInicio())+"'";
		String fechaFin = "'"+dateFormat.format(contrato.getFechaFin())+"'";
		String fechaCreacion = "'"+dateFormat.format(contrato.getFechaCreacion())+"'";
		
		String sql = String.format("INSERT INTO %1$s.CONTRATO (FECHAINICIO, FECHAFIN, COSTO, ID, NUMEROPERSONAS,ID_CLIENTE,FECHACREACION,TIPO,ESTADO) VALUES (%2$s, %3$s, '%4$s', '%5$s', '%6$s', '%7$s',%8$s,'%9$s','%10$s')", 
				USUARIO,
				fechaInicio,
				fechaFin, 
				(int)contrato.getCosto(),
				contrato.getId(),
				contrato.getNumeroDePersonas(),
				contrato.getIdCliente(),
				fechaCreacion,
				contrato.getTipo(),
				contrato.getEstado());
		System.out.println(sql);

		PreparedStatement prepStmt6 = conn.prepareStatement(sql);
		recursos.add(prepStmt6);
		prepStmt6.executeQuery();
		
		String sql2 = String.format("INSERT INTO %1$s.CONTRATOHABITACION (ID_CONTRATO, ID_HABITACION) VALUES (%2$s, '%3$s')", 
				USUARIO,
				contadorNumeroContratos,
				contrato.getIdHabitacion());
			
		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		cerrarRecursos();
		contadorNumeroContratos++;




	}
	public void addContratoVivienda(Contrato contrato) throws SQLException, Exception
	{
	
		contadorNumeroContratos++;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = "'"+dateFormat.format(contrato.getFechaInicio())+"'";
		String fechaFin = "'"+dateFormat.format(contrato.getFechaFin())+"'";
		String fechaCreacion = "'"+dateFormat.format(contrato.getFechaCreacion())+"'";
		
		String sql = String.format("INSERT INTO %1$s.CONTRATO (FECHAINICIO, FECHAFIN, COSTO, ID, NUMEROPERSONAS,ID_CLIENTE,FECHACREACION,TIPO,ESTADO) VALUES (%2$s, %3$s, '%4$s', '%5$s', '%6$s', '%7$s',%8$s, '%9$s','%10$s')", 
				USUARIO,
				fechaInicio,
				fechaFin, 
				(int)contrato.getCosto(),
				contrato.getId(),
				contrato.getNumeroDePersonas(),
				contrato.getIdCliente(),
				fechaCreacion,
				contrato.getTipo(),
				contrato.getEstado());
		System.out.println(sql);

		PreparedStatement prepStmt6 = conn.prepareStatement(sql);
		recursos.add(prepStmt6);
		prepStmt6.executeQuery();
		
		String sql2 = String.format("INSERT INTO %1$s.CONTRATOVIVIENDA (ID_CONTRATO, ID_VIVIENDA) VALUES (%2$s, '%3$s')", 
				USUARIO,
				contadorNumeroContratos,
				contrato.getIdVivienda());
			
		System.out.println(sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		
		contadorNumeroContratos++;
		
	}
	public int darUsoEnEsteAno(Contrato contrato) throws SQLException
	{
		Date hoy= new Date();
		int rpta=0;
		String fechaAcutual=hoy.getYear()+"";
		String capacidad= String.format("WITH Q1 AS(SELECT TO_DATE(FECHAINICIO,'yyyy/mm/dd') as FechaInicio, SUBSTR(FECHAINICIO,0,4) as ANO FROM CONTRATO WHERE CONTRATO.ID_VIVIENDA=%2$d),Q2 AS(SELECT TO_DATE(FECHAFIN,'yyyy/mm/dd') as FechaFin FROM CONTRATO WHERE CONTRATO.ID_VIVIENDA=%2$d),Q3 AS (SELECT Q2.FechaFin-Q1.FechaInicio  AS DIFERENCIA FROM Q1, Q2)SELECT Q3.DIFERENCIA AS DIFERENCIA FROM Q3, Q1 WHERE Q1.ANO=%3$d ;",USUARIO, contrato.getIdVivienda(), fechaAcutual);

		PreparedStatement prepStmt3 = conn.prepareStatement(capacidad);
		ResultSet rs=prepStmt3.executeQuery();
		while(rs.next())
		{
			rpta+=Integer.parseInt(rs.getString("DIFERENCIA"));
		}
		return rpta;
	}
	public ArrayList<Contrato> getContratoByIdHabitacionEnFechas(int id, Date fechaInicio, Date fechaFin) throws SQLException
	{
		ArrayList<Contrato> contrato = new ArrayList<>();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "'"+dateFormat.format(fechaInicio)+"'";
		String fecha2 = "'"+dateFormat.format(fechaFin)+"'";
		
		String sql = String.format("SELECT * FROM CONTRATO INNER JOIN CONTRATOHABITACION ON CONTRATO.ID= CONTRATOHABITACION.ID_CONTRATO WHERE ID_HABITACION ='%3$s' AND  %2$s < FECHAFIN AND  %2$s< FECHAINICIO AND ESTADO='Activo'",fecha1, fecha2, id ); 

		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			contrato.add(convertResultSetToContrato(rs));
		}


		return contrato;
	}
	public ArrayList<Contrato> getContratoByidViviendaEnFechas(Integer id, Date fechaInicio, Date fechaFin) throws SQLException
	{
		ArrayList<Contrato> contrato = new ArrayList<>();

		String sql = String.format("SELECT * FROM CONTRATO INNER JOIN CONTRATOVIVIENDA ON CONTRATO.ID= CONTRATOVIVIENDA.ID_CONTRATO WHERE ID_VIVIENDA ='%3$s' AND  %1$s < FECHAFIN AND  %2$s< FECHAINICIO AND ESTADO='Activo'", USUARIO, fechaInicio, fechaFin, id ); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			contrato.add(convertResultSetToContrato(rs));
		}


		return contrato;
	}
	public ArrayList<Contrato> getContratoByidCliente(String id) throws SQLException
	{
		ArrayList<Contrato> contrato = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID_CLIENTE = %2$s  ", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			contrato.add(convertResultSetToContrato(rs));
		}


		return contrato;
	}
	
	/**
	 * Metodo que actualiza la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param contrato contrato que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteContrato(Contrato contrato) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.CONTRATO WHERE ID = %2$d", USUARIO, contrato.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void cancelarReserva(Contrato contrato) throws SQLException, Exception {

		String sql = String.format("UPDATE CONTRATO SET COSTO='%1$s', ESTADO='%2$s' WHERE ID=%3$s ",(int) contrato.getCosto(), contrato.getEstado(),contrato.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	/**
	 * Metodo que obtiene la informacion del contrato en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del contrato
	 * @return la informacion del contrato que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el contrato conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Contrato findContratoById(Long id) throws SQLException, Exception 
	{
		Contrato contrato = null;

		String sql = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			contrato = convertResultSetToContrato(rs);
		}

		return contrato;
	}
	public ArrayList<Contrato> selectTop20() throws SQLException, Exception 
	{
		ArrayList<Contrato> contrato = new ArrayList<>();

		String sql = String.format("WITH Q1 AS(SELECT ID_VIVIENDA AS id, COUNT(ID_VIVIENDA) as cuenta FROM %1$s.CONTRATO GROUP BY ID_VIVIENDA order by COUNT(ID_VIVIENDA) DESC) select  *  from q1 inner join %1$s.contrato on contrato.ID=q1.id where rownum<=20 ", USUARIO); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			contrato.add(convertResultSetToContrato(rs));
		}

		return contrato;
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
	public Contrato convertResultSetToContrato(ResultSet resultSet) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)



		String fechaInicio = resultSet.getString("FECHAINICIO");
		String fif = fechaInicio.substring(2, 10);
		String [] array = fif.split("-");
		int anho = Integer.parseInt(array[0])+100;
		int mes = Integer.parseInt(array[1])-1;
		int dia = Integer.parseInt(array[2]);
		Date date1 = new Date(anho, mes, dia);


		String fechaFin = resultSet.getString("FECHAFIN");
		String fif2 = fechaFin.substring(2, 10);
		String [] array2 = fif.split("-");
		int anho2 = Integer.parseInt(array2[0])+100;
		int mes2 = Integer.parseInt(array2[1])-1;
		int dia2 = Integer.parseInt(array2[2]);
		Date date2 = new Date(anho2, mes2, dia2);

		String fechaCreacion = resultSet.getString("FECHACREACION");
		String[] dias3=fechaCreacion.split("/");
		Date laFechaCreacion= new Date();
		laFechaCreacion.setYear(Integer.parseInt(dias3[0]));
		laFechaCreacion.setMonth(Integer.parseInt(dias3[1]));
		laFechaCreacion.setDate(Integer.parseInt(dias3[2]));

		String tipo = resultSet.getString("TIPO");
		double costo = Double.parseDouble(resultSet.getString("COSTO"));
		Integer id = Integer.parseInt(resultSet.getString("ID"));
		
		int numeroDePersonas = Integer.parseInt(resultSet.getString("NUMEROPERSONAS"));
		String idCliente = resultSet.getString("ID_CLIENTE");
		String estado = resultSet.getString("ESTADO");




		Contrato co= new Contrato(date1, date2, tipo, costo, id, null, null, numeroDePersonas, idCliente, laFechaCreacion, estado);
		return co;
	}
	public Contrato convertResultSetToContratoHabitacion(ResultSet resultSetContrato, Integer idHab) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)


		String fechaInicio = resultSetContrato.getString("FECHAINICIO");
		String fif = fechaInicio.substring(2, 10);
		String [] array = fif.split("-");
		int anho = Integer.parseInt(array[0])+100;
		int mes = Integer.parseInt(array[1])-1;
		int dia = Integer.parseInt(array[2]);
		Date date1 = new Date(anho, mes, dia);


		String fechaFin = resultSetContrato.getString("FECHAFIN");
		String fif2 = fechaFin.substring(2, 10);
		String [] array2 = fif.split("-");
		int anho2 = Integer.parseInt(array2[0])+100;
		int mes2 = Integer.parseInt(array2[1])-1;
		int dia2 = Integer.parseInt(array2[2]);
		Date date2 = new Date(anho2, mes2, dia2);

		String fechaCreacion = resultSetContrato.getString("FECHACREACION");
		String[] dias3=fechaCreacion.split("/");
		Date laFechaCreacion= new Date();
		laFechaCreacion.setYear(Integer.parseInt(dias3[0]));
		laFechaCreacion.setMonth(Integer.parseInt(dias3[1]));
		laFechaCreacion.setDate(Integer.parseInt(dias3[2]));

		String tipo = resultSetContrato.getString("TIPO");
		double costo = Double.parseDouble(resultSetContrato.getString("COSTO"));
		long id = Integer.parseInt(resultSetContrato.getString("ID"));
		
		
		Integer vivienda = resultSetContrato.getInt("ID_VIVIENDA");
		
		
		
		int numeroDePersonas = Integer.parseInt(resultSetContrato.getString("NUMERO_DE_PERSONAS"));
		String idCliente = resultSetContrato.getString("ID_CLIENTE");
		String estado = resultSetContrato.getString("ESTADO");



		Contrato co= new Contrato(date1, date2, tipo, costo, id, null, idHab, numeroDePersonas, idCliente, laFechaCreacion, estado);
		return co;
	}

	public Contrato convertResultSetToContratoVivivenda(ResultSet resultSetContrato, Integer idViv) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)


		String fechaInicio = resultSetContrato.getString("FECHAINICIO");
		String fif = fechaInicio.substring(2, 10);
		String [] array = fif.split("-");
		int anho = Integer.parseInt(array[0])+100;
		int mes = Integer.parseInt(array[1])-1;
		int dia = Integer.parseInt(array[2]);
		Date date1 = new Date(anho, mes, dia);


		String fechaFin = resultSetContrato.getString("FECHAFIN");
		String fif2 = fechaFin.substring(2, 10);
		String [] array2 = fif.split("-");
		int anho2 = Integer.parseInt(array2[0])+100;
		int mes2 = Integer.parseInt(array2[1])-1;
		int dia2 = Integer.parseInt(array2[2]);
		Date date2 = new Date(anho2, mes2, dia2);

		String fechaCreacion = resultSetContrato.getString("FECHACREACION");
		String[] dias3=fechaCreacion.split("/");
		Date laFechaCreacion= new Date();
		laFechaCreacion.setYear(Integer.parseInt(dias3[0]));
		laFechaCreacion.setMonth(Integer.parseInt(dias3[1]));
		laFechaCreacion.setDate(Integer.parseInt(dias3[2]));

		String tipo = resultSetContrato.getString("TIPO");
		double costo = Double.parseDouble(resultSetContrato.getString("COSTO"));
		long id = Integer.parseInt(resultSetContrato.getString("ID"));
		
		
		Integer vivienda = resultSetContrato.getInt("ID_VIVIENDA");
		
		
		
		int numeroDePersonas = Integer.parseInt(resultSetContrato.getString("NUMERO_DE_PERSONAS"));
		String idCliente = resultSetContrato.getString("ID_CLIENTE");
		String estado = resultSetContrato.getString("ESTADO");




		Contrato co= new Contrato(date1, date2, tipo, costo, id, idViv, null, numeroDePersonas, idCliente, laFechaCreacion, estado);
		return co;
	}

}
