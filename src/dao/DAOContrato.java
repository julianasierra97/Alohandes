package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Bebedor;
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



		Date ahora=new Date();
		String fecha= (ahora.getYear()+"")+"-"+(ahora.getMonth()+"")+"-"+(ahora.getDate()+"");
		contadorNumeroContratos++;
		String sql = String.format("INSERT INTO %1$s.CONTRATO (FECHAINICIO, FECHAFIN, TIPO, COSTO, ID,ID_VIVIENDA, ID_HABITACION, NUMERODEPERSONAS,ID_CLIENTE,FECHACREACION) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
				USUARIO,
				contrato.getFechaInicio(),
				contrato.getFechaFin(), 
				contrato.getTipo(), 
				contrato.getCosto(),
				contadorNumeroContratos,
				"NULL",
				contrato.getHabitacion(), 
				contrato.getNumeroDePersonas(),
				contrato.getIdCliente(),
				fecha);

		System.out.println(sql);

		PreparedStatement prepStmt6 = conn.prepareStatement(sql);
		recursos.add(prepStmt6);
		prepStmt6.executeQuery();




	}
	public void addContratoVivienda(Contrato contrato) throws SQLException, Exception
	{
		Date ahora=new Date();
		contadorNumeroContratos++;
		String fecha= (ahora.getYear()+"")+"-"+(ahora.getMonth()+"")+"-"+(ahora.getDate()+"");
		String sql = String.format("INSERT INTO %1$s.CONTRATO (FECHAINICIO, FECHAFIN, TIPO, COSTO, ID,ID_VIVIENDA, ID_HABITACION, NUMERODEPERSONAS,ID_CLIENTE,FECHACREACION) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')", 
				USUARIO,
				contrato.getFechaInicio(),
				contrato.getFechaFin(), 
				contrato.getTipo(), 
				contrato.getCosto(),
				contadorNumeroContratos,
				contrato.getIdVivienda(),
				"NULL", 
				contrato.getNumeroDePersonas(),
				contrato.getIdCliente(),
				fecha);
		System.out.println(sql);

		PreparedStatement prepStmt6 = conn.prepareStatement(sql);
		recursos.add(prepStmt6);
		prepStmt6.executeQuery();
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

		String sql = String.format(
				"WITH Q1 AS(SELECT TO_DATE('%2$d','yyyy/mm/dd') as rangoPequeno from contrato),Q2 AS(SELECT TO_DATE('%3$d','yyyy/mm/dd') as rangoGrande from CONTRATO),Q3 AS(SELECT TO_DATE(FECHAINICIO,'yyyy/mm/dd') as FechaInicio FROM CONTRATO ),Q4 AS(SELECT TO_DATE(FECHAFIN,'yyyy/mm/dd') as FechaFin FROM CONTRATO )SELECT * FROM CONTRATO,Q1, Q2,Q3,Q4 WHERE ID_HABITACION=%3$d AND Q4.fechaFin BETWEEN Q1.rangoPequeno and q2.rangoGrande AND Q3.FechaInicio BETWEEN Q1.rangoPequeno and q2.rangoGrande;", USUARIO,fechaInicio, fechaFin, id ); 

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

		String sql = String.format(
				"WITH Q1 AS(SELECT TO_DATE('%2$d','yyyy/mm/dd') as rangoPequeno from contrato),Q2 AS(SELECT TO_DATE('%3$d','yyyy/mm/dd') as rangoGrande from CONTRATO),Q3 AS(SELECT TO_DATE(FECHAINICIO,'yyyy/mm/dd') as FechaInicio FROM CONTRATO ),Q4 AS(SELECT TO_DATE(FECHAFIN,'yyyy/mm/dd') as FechaFin FROM CONTRATO )SELECT * FROM CONTRATO,Q1, Q2,Q3,Q4 WHERE ID_VIVIENDA=%3$d AND Q4.fechaFin BETWEEN Q1.rangoPequeno and q2.rangoGrande AND Q3.FechaInicio BETWEEN Q1.rangoPequeno and q2.rangoGrande;", USUARIO,fechaInicio, fechaFin, id ); 

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

		String sql = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID_CLIENTE = %2$d ; ", USUARIO, id); 

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
	public void updateContrato(Contrato contrato) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.CONTRATO SET ", USUARIO));
		sql.append(String.format("COSTO = '%1$s' AND FECHAFIN = '%2$s' AND FECHAINICIO = '%3$s' ", contrato.getCosto(), contrato.getFechaFin(), contrato.getFechaInicio()));

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
		String[] dias=fechaInicio.split("/");
		Date laFechaInicio= new Date();
		laFechaInicio.setYear(Integer.parseInt(dias[0]));
		laFechaInicio.setMonth(Integer.parseInt(dias[1]));
		laFechaInicio.setDate(Integer.parseInt(dias[2]));


		String fechaFin = resultSet.getString("FECHAFIN");
		String[] dias2=fechaFin.split("/");
		Date laFechaFin= new Date();
		laFechaFin.setYear(Integer.parseInt(dias2[0]));
		laFechaFin.setMonth(Integer.parseInt(dias2[1]));
		laFechaFin.setDate(Integer.parseInt(dias2[2]));

		String fechaCreacion = resultSet.getString("FECHACREACION");
		String[] dias3=fechaCreacion.split("/");
		Date laFechaCreacion= new Date();
		laFechaCreacion.setYear(Integer.parseInt(dias3[0]));
		laFechaCreacion.setMonth(Integer.parseInt(dias3[1]));
		laFechaCreacion.setDate(Integer.parseInt(dias3[2]));

		String tipo = resultSet.getString("TIPO");
		double costo = Double.parseDouble(resultSet.getString("COSTO"));
		Integer id = Integer.parseInt(resultSet.getString("ID"));
		int vivienda = resultSet.getInt("ID_VIVIENDA");
		int habitacion = resultSet.getInt("ID_HABITACION");
		int numeroDePersonas = Integer.parseInt(resultSet.getString("NUMERO_DE_PERSONAS"));
		String idCliente = resultSet.getString("ID_CLIENTE");




		Contrato co= new Contrato(laFechaInicio, laFechaFin, tipo, costo, id, vivienda, habitacion, numeroDePersonas, idCliente, laFechaCreacion);
		return co;
	}


}
