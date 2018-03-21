package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import vos.Empresa;
import vos.Operador;

public class DAOEmpresa {
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
			public DAOEmpresa() {
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
		public void addEmpresa(Empresa empresa) throws SQLException, Exception {

			
			
			
			String tiempo1=empresa.getHorarioApertura()+"";
			String tiempo2=empresa.getHorarioCierre()+"";
			String NUMREGISTROSUPERINTENDENCIA= empresa.getNumRegistroSuperintendencia()+"";
			String NUMREGISTROCAMARACOMERCIO=empresa.getNumRegistroCamaraComercio()+"";
			
			if(empresa.getTipo().equals(Empresa.HOSTAL))
			{

				String sql = String.format("INSERT INTO %1$s.EMPRESA (UBICACION, TIPO, HORARIOAPERTURA, HORARIOCIERRE, NUMREGISTROSUPERINTENDENCIA,NUMREGISTROCAMARADECOMERCIO, ID) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s','%8$s')", 
						USUARIO,  
						empresa.getUbicacion(), 
						empresa.getTipo(),
						tiempo1, 
						tiempo2,
						NUMREGISTROSUPERINTENDENCIA,
						NUMREGISTROCAMARACOMERCIO,
						empresa.getDocumento());
				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				prepStmt.executeQuery();
			}
			else if(empresa.getTipo().equals(Empresa.VIVIENDA_UNIVERSITARIA) ||empresa.getTipo().equals(Empresa.HOTEL ))
			{
				String sql = String.format("INSERT INTO %1$s.EMPRESA ( UBICACION, TIPO, HORARIOAPERTURA, HORARIOCIERRE, NUMREGISTROSUPERINTENDENCIA,NUMREGISTROCAMARADECOMERCIO, ID) VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s','%8$s')", 
						USUARIO,  
						
						empresa.getUbicacion(), 
						empresa.getTipo(),
						"NULL", 
						"NULL",
						NUMREGISTROSUPERINTENDENCIA,
						NUMREGISTROCAMARACOMERCIO,
						empresa.getDocumento());
				System.out.println(sql);

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				prepStmt.executeQuery();
			}
			else
			{
				throw new Exception("El tipo de la empresa se tiene que poner en el formato indicado");
			}
			

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
		public Empresa  findEmpresaById(String id) throws SQLException, Exception 
		{
			Empresa empresa = null;

			String sql = String.format("SELECT * FROM %1$s.OPERADOR op INNER JOIN %1$s.EMPRESA em ON OP.DOCUMENTO=EM.ID WHERE EM.ID=%2$d;", USUARIO);
			

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs1 = prepStmt.executeQuery();

			
			if(rs1.next()) {
				empresa = convertResultSetToEmpresa(rs1);
			}

			return empresa;
		}
		public List<Empresa> getEmpresas() throws SQLException, Exception {
			ArrayList<Empresa> empresas = new ArrayList<Empresa>();

			//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			String sql = String.format("SELECT * FROM %1$s.OPERADOR op INNER JOIN %1$s.EMPRESA em ON OP.DOCUMENTO=EM.ID;", USUARIO);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();



			while (rs.next() ) {
			empresas.add(convertResultSetToEmpresa(rs));
			}
			return empresas;
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
		public Empresa convertResultSetToEmpresa(ResultSet resultSetEmpresa) throws SQLException {
		//Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		// Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		String ubicacion = resultSetEmpresa.getString("UBICACION");
		String tipo = resultSetEmpresa.getString("TIPO");
		String horarioApertura =resultSetEmpresa.getString("HORARIOAPERTURA");
		String horarioCierre=resultSetEmpresa.getString("HORARIOCIERRE");
		long numRegistroSuperintendencia=Long.parseLong(resultSetEmpresa.getString("NUMREGISTROSUPERINTENDENCIA"));
		long numRegistroCamaraComercio=Long.parseLong(resultSetEmpresa.getString("NUMREGISTROCAMARACOMERCIO"));

		String login= resultSetEmpresa.getString("LOGIN");
		String contrasenha= resultSetEmpresa.getString("CONTRASENHA");
		String documento= resultSetEmpresa.getString("DOCUMENTO");
		String correo= resultSetEmpresa.getString("CORREO");
		String tipoDocumento= resultSetEmpresa.getString("TIPODOCUMENTO");
		String nombre= resultSetEmpresa.getString("NOMBRE");



		Empresa em = new Empresa(ubicacion, tipo, horarioApertura, horarioCierre, numRegistroSuperintendencia, numRegistroCamaraComercio, login, contrasenha, documento, tipoDocumento, nombre, correo);

		return em;
		}
		
}
