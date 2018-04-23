
package tm;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import dao.DAOContrato;
import dao.DAOEmpresa;
import dao.DAOHabitacion;
import dao.DAOOperador;
import dao.DAOPersonaNatural;
import dao.DAOUsuario;
import dao.DAOVivienda;
import dao.RFC1DAO;
import dao.RFC3DAO;
import dao.RFC4DAO;
import dao.RFC5DAO;
import dao.RFC6DAO;
import dao.RFC8DAO;
import dao.RFC9DAO;
import sun.security.util.DisabledAlgorithmConstraints;
import vos.Contrato;
import vos.Empresa;
import vos.Habitacion;
import vos.Operador;
import vos.PersonaNatural;
import vos.Servicio;
import vos.Usuario;
import vos.Vivienda;

/**
 * 
 * 
 * Clase que representa al Manejador de Transacciones de la Aplicacion (Fachada en patron singleton de la aplicacion)
 * Responsabilidades de la clase: 
 * 		Intermediario entre los servicios REST de la aplicacion y la comunicacion con la Base de Datos
 * 		Modelar y manejar autonomamente las transacciones y las reglas de negocio.
 */
public class AlohonadesTransactionManager {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;

	/**
	 * Constatne que representa el numero maximo de Bebedores que pueden haber en una ciudad
	 */
	private final static Integer CANTIDAD_MAXIMA = 345;

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public AlohonadesTransactionManager(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(AlohonadesTransactionManager.CONNECTION_DATA_PATH));
		Properties properties = new Properties();

		properties.load(fileInputStream);
		fileInputStream.close();

		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");

		//Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------










	/**
	 * Metodo que modela la transaccion que agrega un contrato a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el contrato que entra como parametro <br/>
	 * @param contrato - el empresa a agregar. contrato != null
	 * @throws Exception - Cualquier error que se genere agregando el contrato
	 */
	public void addContrato(Contrato contrato) throws Exception 
	{

		DAOContrato daoContrato = new DAOContrato( );

		try
		{
			// Requerimiento 3D: Obtenga la conexion a la Base de Datos (revise los metodos de la clase)
			this.conn = darConexion();
			// Requerimiento 3E: Establezca la conexion en el objeto DAOBebedor (revise los metodos de la clase DAOBebedor)
			daoContrato.setConn(conn);
			if(contrato.getIdHabitacion()!=null && contrato.getIdVivienda()==null)
			{

				//caso habitacion
				DAOHabitacion daohabitacion= new DAOHabitacion();
				daohabitacion.setConn(conn);
				Habitacion laHabitacion=daohabitacion.findHabitacionById((contrato.getIdHabitacion()));
				if(laHabitacion==null)
				{
					throw new Exception("la habitacion que quieres reservar no esta en la base de datos");
				}
				else
				{
					if (!daoContrato.getContratoByIdHabitacionEnFechas(contrato.getIdHabitacion(), contrato.getFechaInicio(), contrato.getFechaFin()).isEmpty()) {
						throw new Exception("la habitacion que quieres reservar ya esta reservada");
					}
					else
					{
						if(laHabitacion.getCapacidad()<contrato.getNumeroDePersonas())
						{
							throw new Exception("la habitacion que quieres reservar no tiene suficiente espacio");
						}
						else
						{
							if(laHabitacion.getTipo().equals(Habitacion.HABITACION_VIVIENDA_UNIVERSITARIA))
							{
								DAOUsuario daoUsuario= new DAOUsuario();
								daoUsuario.setConn(conn);
								Usuario elUsuario=daoUsuario.findUsuarioById(contrato.getIdCliente());
								if(elUsuario==null)
								{
									throw new Exception("No eres un usuario habilitado para el uso de viviendas universitarias");
								}
								else
								{
									String	elTipo2 =elUsuario.getTipo();
									if(!elTipo2.equals(Usuario.ESTUDIANTE)||!elTipo2.equals(Usuario.PROFESOR)||!elTipo2.equals(Usuario.PROFESOR_INVITADO)||!elTipo2.equals(Usuario.EMPLEADO))
									{
										throw new Exception("No eres un usuario habilitado para el uso de viviendas universitarias");
									}
									else
									{
										Date ahora= new Date();
										boolean hayEnLaFecha=false;
										ArrayList<Contrato> contratos =daoContrato.getContratoByidCliente(contrato.getIdCliente());
										for(int i=0;i<contratos.size() && !hayEnLaFecha; i++)
										{
											if(contratos.get(i).getFechaCreacion().getDate()==ahora.getDate() && contratos.get(i).getFechaCreacion().getMonth()==ahora.getMonth()&& ahora.getYear()==contratos.get(i).getFechaCreacion().getYear())
											{
												hayEnLaFecha=true;
											}
										}


										if(hayEnLaFecha!=true)
										{
											daoContrato.addContratoHabitacion(contrato);		
											elUsuario.getContratos().add(contrato);
										}
										else
										{
											throw new Exception("No se pueden generar mas de una reserva en un dia");
										}

									}
								}

							}
							else
							{
								Date ahora= new Date();
								boolean hayEnLaFecha=false;
								DAOUsuario daoUsuario= new DAOUsuario();
								daoUsuario.setConn(conn);
								Usuario elUsuario=daoUsuario.findUsuarioById(contrato.getIdCliente());
								ArrayList<Contrato> contratos =daoContrato.getContratoByidCliente(contrato.getIdCliente());
								for(int i=0;i<contratos.size() && !hayEnLaFecha; i++)
								{
									if(contratos.get(i).getFechaCreacion().getDate()==ahora.getDate() && contratos.get(i).getFechaCreacion().getMonth()==ahora.getMonth()&& ahora.getYear()==contratos.get(i).getFechaCreacion().getYear())
									{
										hayEnLaFecha=true;
									}
								}


								if(hayEnLaFecha!=true)
								{
									daoContrato.addContratoHabitacion(contrato);		
									elUsuario.getContratos().add(contrato);
								}
								else
								{
									throw new Exception("No se pueden generar mas de una reserva en un dia");
								}
							}

						}
					}
				}
			}
			else if(contrato.getHabitacion()==null && contrato.getIdVivienda()!=null)

			{
				DAOVivienda daoVivienda= new DAOVivienda();
				daoVivienda.setConn(conn);
				String idPersona= daoVivienda.getIdPersonaByIdVivienda(contrato.getIdVivienda());
				DAOPersonaNatural daoPersonaNatural= new DAOPersonaNatural();
				daoPersonaNatural.setConn(conn);
				PersonaNatural persona= daoPersonaNatural.findPersonaById((idPersona));

				if (persona== null )
				{
					throw new Exception ("El cliente con ese id no esta registrado en la base de datos");
				}
				else
				{


					if (persona.getTipo().equals(PersonaNatural.VECINO))
					{

						int daysApart = (int)((contrato.getFechaFin().getTime() - contrato.getFechaInicio().getTime()) / (1000*60*60*24l));
						if(daoContrato.darUsoEnEsteAno(contrato)>30 ||daoContrato.darUsoEnEsteAno(contrato)+daysApart>30 )
						{
							throw new Exception("La vivienda no puede rentarse por mas de 30 dias por anio");
						}
						DAOUsuario daoUsuario= new DAOUsuario();
						Usuario elUsuario=daoUsuario.findUsuarioById(contrato.getIdCliente());
						if(elUsuario==null)
						{
							throw new Exception("No eres un usuario habilitado para el uso de viviendas universitarias");
						}
						else
						{
							Date ahora= new Date();
							boolean hayEnLaFecha=false;
							ArrayList<Contrato> contratos =daoContrato.getContratoByidCliente(contrato.getIdCliente());
							for(int i=0;i<contratos.size() && !hayEnLaFecha; i++)
							{
								if(contratos.get(i).getFechaCreacion().getDate()==ahora.getDate() && contratos.get(i).getFechaCreacion().getMonth()==ahora.getMonth()&& ahora.getYear()==contratos.get(i).getFechaCreacion().getYear())
								{
									hayEnLaFecha=true;
								}
							}


							if(hayEnLaFecha!=true)
							{
								daoContrato.addContratoHabitacion(contrato);		
								elUsuario.getContratos().add(contrato);
							}
							else
							{
								throw new Exception("No se pueden generar mas de una reserva en un dia");
							}

						}
					}
					else
					{
						int daysApart = (int)((contrato.getFechaFin().getTime() - contrato.getFechaInicio().getTime()) / (1000*60*60*24l));
						if(daysApart<30)
						{
							throw new Exception("La vivienda solo se puede tomar por mas de 30 dias");
						}
						else
						{
							daoContrato.addContratoVivienda(contrato);
						}
					}
				}

			}



		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoContrato.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega un bebedor a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el bebedor que entra como parametro <br/>
	 * @param bebedor - el bebedor a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addOperador(Operador operador) throws Exception 
	{

		DAOOperador daoOperador = new DAOOperador();
		try
		{
			// Requerimiento 3D: Obtenga la conexion a la Base de Datos (revise los metodos de la clase)
			this.conn = darConexion();
			// Requerimiento 3E: Establezca la conexion en el objeto DAOBebedor (revise los metodos de la clase DAOBebedor)
			daoOperador.setConn(conn);
			daoOperador.addOperador(operador);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un bebedor a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el bebedor que entra como parametro <br/>
	 * @param bebedor - el bebedor a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addEmpresa(Empresa empresa) throws Exception 
	{

		DAOEmpresa daoEmpresa = new DAOEmpresa();
		try
		{
			// Requerimiento 3D: Obtenga la conexion a la Base de Datos (revise los metodos de la clase)
			this.conn = darConexion();
			// Requerimiento 3E: Establezca la conexion en el objeto DAOBebedor (revise los metodos de la clase DAOBebedor)

			daoEmpresa.setConn(conn);

			if(empresa.getTipo().equals(Empresa.HOSTAL))
			{
				if(empresa.getHorarioApertura()=="" && empresa.getHorarioCierre()=="")
				{
					throw new Exception("Para agregar un hostal toca especificar los horarios de cierre y de apertura");
				}
				else
				{
					DAOOperador  daoOperador=  new DAOOperador();
					daoOperador.setConn(conn);

					daoEmpresa.addEmpresa(empresa);	
				}
			}
			else if(empresa.getTipo().equals(Empresa.VIVIENDA_UNIVERSITARIA))
			{
				int contador=0;
				for(Servicio ser :empresa.getServicios())
				{
					if(ser.getNombre().equals(Servicio.PORTERIA_24H)|| ser.getNombre().equals(Servicio.APOYO_ACADEMIDO)||ser.getNombre().equals(Servicio.APOYO_SOCIAL)
							||ser.getNombre().equals(Servicio.RESTAURANTE)||ser.getNombre().equals(Servicio.SALAS_ESTUDIO)||ser.getNombre().equals(Servicio.SALAS_ESPARCIMIENTO)
							||ser.getNombre().equals(Servicio.GIMANSIO))
					{
						contador++;
					}

				}
				if(contador==Empresa.NUM_SERVICIO_SOBLIGATORIOS_VIVIENDA_UNIVERSITARIA)
				{
					DAOOperador  daoOperador=  new DAOOperador();
					daoOperador.setConn(conn);

					daoEmpresa.addEmpresa(empresa);
				}
				else
				{
					throw new Exception("No se puede agregar a la base de datos una vivienda universitaria que no incluya todos los servicios requeridos para este tipo de empresa");
				}
			}
			else
			{
				DAOOperador  daoOperador=  new DAOOperador();
				daoOperador.setConn(conn);

				daoEmpresa.addEmpresa(empresa);
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoEmpresa.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void agregarPersonaNatural(PersonaNatural pn) throws Exception
	{
		DAOPersonaNatural daoPersona = new DAOPersonaNatural();
		if(pn.getTipo().equals(PersonaNatural.EGRESADO) || pn.getTipo().equals(PersonaNatural.ESTUDIANTE) || pn.getTipo().equals(PersonaNatural.PROFESOR) || pn.getTipo().equals(PersonaNatural.EMPLEADO))
		{
			if(!pn.getCorreo().contains("@uniandes.edu.co"))
				throw new Exception("El usuario no hace parte de la comunidad de uniandes");
		}
		else{
			//TODO verificacion de papa
		}

		try{
			this.conn = darConexion();
			// Requerimiento 3E: Establezca la conexion en el objeto DAOBebedor (revise los metodos de la clase DAOBebedor)
			daoPersona.setConn(conn);
			daoPersona.addPersonaNatural(pn);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoPersona.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}


	public void agregarUsuario(Usuario usuario) throws Exception
	{
		DAOUsuario daoUsuario = new DAOUsuario();
		if(usuario.getTipo().equals(PersonaNatural.EGRESADO) || usuario.getTipo().equals(PersonaNatural.ESTUDIANTE) || usuario.getTipo().equals(PersonaNatural.PROFESOR) || usuario.getTipo().equals(PersonaNatural.EMPLEADO))
		{
			if(!usuario.getCorreo().contains("@uniandes.edu.co"))
				throw new Exception("El usuario no hace parte de la comunidad de uniandes");
		}
		else{
			//TODO verificacion de papa
		}

		try{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(usuario);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public void agregarVivienda(Vivienda vivienda, String idPersona) throws Exception
	{
		DAOVivienda daoVivienda = new DAOVivienda();
		try
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);
			daoVivienda.addVivienda(vivienda, idPersona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}



	public void agregarHabitacion(Habitacion habitacion, String idOperador) throws Exception
	{
		if(habitacion.getTipo().equals(Habitacion.ESTANDAR) || 
				habitacion.getTipo().equals(Habitacion.SEMI_SUITES) || 
				habitacion.getTipo().equals(Habitacion.SUITES) ||
				habitacion.getTipo().equals(Habitacion.HABITACION_VIVIENDA_UNIVERSITARIA))
		{
			agregarHabitacionEmpresa(habitacion, idOperador);
		}
		else{
			agregarHabitacionPersona(habitacion, idOperador);
		}
	}




	private void agregarHabitacionPersona(Habitacion habitacion, String idPersona) throws Exception
	{
		PersonaNatural persona = getPersonaNaturalById(idPersona);

		if(persona == null)
		{
			throw new Exception("La persona natural que quiere agregar la habitacion dbee existir");
		}		

		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			daoHabitacion.addHabitacion(habitacion, idPersona);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public PersonaNatural getPersonaNaturalById(String idPersona) throws Exception {
		DAOPersonaNatural daoPersona = new DAOPersonaNatural();
		PersonaNatural persona = null;
		try 
		{
			this.conn = darConexion();
			daoPersona.setConn(conn);
			persona = daoPersona.findPersonaById(idPersona);
			if(persona == null)
			{
				throw new Exception("El bebedor con el id = " + idPersona + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoPersona.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return persona;

	}

	private void agregarHabitacionEmpresa(Habitacion habitacion, String idEmpresa) throws Exception
	{
		Empresa empresa = findEmpresaById(idEmpresa);

		if(empresa == null)
		{
			throw new Exception("La empresa que quiere agregar una habitacion debe existir");
		}

		if(habitacion.getTipo() == Habitacion.HABITACION_HOSTAL && !habitacion.isCompartida()){	
			throw new Exception("No se puede agregar una habitacion de hostal que no sea compartida");
		}

		//		if(habitacion.getTipo().equals(Habitacion.ESTANDAR) || 
		//				habitacion.getTipo().equals(Habitacion.SEMI_SUITES) || 
		//				habitacion.getTipo().equals(Habitacion.SUITES))
		//		{
		//
		//			if(habitacion.isCompartida())
		//				
		//				throw new Exception("Una habitacion de un hotel no puede ser compartida");
		//		}

		if(habitacion.getTipo().equals(Habitacion.HABITACION_VIVIENDA_UNIVERSITARIA))
		{
			int cont = 0;
			for (Servicio servicio : habitacion.getServicios()) {
				if(servicio.getNombre().equals(Servicio.AMOBLADO)||
						servicio.getNombre().equals(Servicio.COCINETA) ||
						servicio.getNombre().equals(Servicio.WIFI) ||
						servicio.getNombre().equals(Servicio.TV) ||
						servicio.getNombre().equals(Servicio.PORTERIA_24H) ||
						servicio.getNombre().equals(Servicio.SERVICIOS_ASEO) ||
						servicio.getNombre().equals(Servicio.APOYO_SOCIAL) ||
						servicio.getNombre().equals(Servicio.APOYO_ACADEMIDO))
				{
					cont++;
				}
				if(cont != 8)
				{
					throw new Exception("La vivienda universitaria debe poseer los servicios minimos");
				}
			}
		}



		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			daoHabitacion.addHabitacion(habitacion, idEmpresa);


		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}


	private Empresa findEmpresaById(String idEmpresa) throws Exception {
		DAOEmpresa daoEmpresa = new DAOEmpresa();
		Empresa empresa = null;
		try 
		{
			this.conn = darConexion();
			daoEmpresa.setConn(conn);
			empresa = daoEmpresa.findEmpresaById(idEmpresa);
			if(empresa == null)
			{
				throw new Exception("La empresa con el id = " + idEmpresa + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoEmpresa.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return empresa;

	}






	/**
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Bebedor - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteReserva(Contrato contrato) throws Exception 
	{
		DAOContrato daocontrato = new DAOContrato( );
		try
		{
			this.conn = darConexion();
			daocontrato.setConn( conn );

			if(daocontrato.findContratoById(contrato.getId())!=null)
			{
				if(contrato.getTipo().equals(Contrato.RESERVA))
				{
					int daysApart = (int)((contrato.getFechaFin().getTime() - contrato.getFechaInicio().getTime()) / (1000*60*60*24l));
					int tiempoLimite=3;
					if(tiempoLimite>7)
					{
						tiempoLimite=8;
					}
					Date hoy= new Date();
					int daysApart2 = (int)((contrato.getFechaCreacion().getTime() - hoy.getTime()) / (1000*60*60*24l));
					double porcentajeRecargo=0.1;
					if(tiempoLimite>30)
					{
						porcentajeRecargo=0.3;
					}
					if(hoy.compareTo(contrato.getFechaCreacion())>0)
					{
						porcentajeRecargo=0.3;
					}
					
					contrato.setCosto(contrato.getCosto()*porcentajeRecargo);
					contrato.setEstado("Cancelado");
					daocontrato.cancelarReserva(contrato);

				


				}
				else
				{
					throw new Exception("No se puede borrar una reserva de ese tipo");
				}

			}
			else
			{
				throw new Exception("No existe una reserva con ese id");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daocontrato.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public List<PersonaNatural> getAllPersonas() throws Exception {
		DAOPersonaNatural daoPersona = new DAOPersonaNatural();
		List<PersonaNatural> personas;
		try 
		{
			this.conn = darConexion();
			daoPersona.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			personas = daoPersona.getPersonasNatural();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoPersona.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}


	public List<Usuario> getAllUsuarios() throws Exception {
		DAOUsuario daoUsuario = new DAOUsuario();
		List<Usuario> usuarios;
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			usuarios = daoUsuario.getUsuarios();
			System.out.println("Entro al gel all");
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}



	public void deleteHabitacion(Integer idHabitacion) throws Exception
	{

		Habitacion habitacion = buscarHabitacionPorId(idHabitacion);
		if(habitacion.getTipo().equals(Habitacion.ESTANDAR) || 
				habitacion.getTipo().equals(Habitacion.SEMI_SUITES) || 
				habitacion.getTipo().equals(Habitacion.SUITES) ||
				habitacion.getTipo().equals(Habitacion.HABITACION_VIVIENDA_UNIVERSITARIA))
		{
			deleteHabitacionEmpresa(idHabitacion);
		}
		else{
			deleteHabitacionPersona(idHabitacion);
		}


	}



	private void deleteHabitacionEmpresa(Integer idHabitacion) throws Exception
	{
		Habitacion habitacion = buscarHabitacionPorId(idHabitacion);
		if(habitacion == null)
		{
			throw new Exception("No se existe la habitacion que se quiere eliminar");
		}
		//		if(getContratosByIdHabitacionEntreFechas(idHabitacion).size() != 0)
		//		{
		//			throw new Exception("La habitacion que se quiere eliminar esta reservada");
		//		}

		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn( conn );

			if(daoHabitacion.findHabitacionById(habitacion.getId())!=null)
			{
				daoHabitacion.deleteHabitacion(habitacion);
			}
			else
			{
				throw new Exception("No existe una habitacion con ese id");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	


	}

	public Habitacion buscarHabitacionPorId(Integer idHabitacion) throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		Habitacion habitacion = null;
		try 
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			habitacion = daoHabitacion.findHabitacionById(idHabitacion);
			if(habitacion == null)
			{
				throw new Exception("La habitacion con el id = " + idHabitacion + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return habitacion;
	}
	public Vivienda buscarViviendaPorId(Integer idVivienda) throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		Vivienda habitacion = null;
		try 
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);
			habitacion = daoVivienda.findViviendaById(idVivienda);
			if(habitacion == null)
			{
				throw new Exception("La vivienda con el id = " + idVivienda + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return habitacion;
	}

	public ArrayList<Contrato> getContratosByIdHabitacionEntreFechas(Integer idHabitacion) throws Exception
	{
		Habitacion habitacion = buscarHabitacionPorId(idHabitacion);
		if(habitacion == null)
		{
			throw new Exception("La habitacion no existe");
		}

		DAOContrato daoContrato= new DAOContrato();
		ArrayList<Contrato> contratos = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			daoContrato.setConn(conn);
			contratos = daoContrato.getContratoByIdHabitacionEnFechas(idHabitacion, new Date(), new Date(Long.MAX_VALUE));

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoContrato.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return contratos;
	}

	private void deleteHabitacionPersona(Integer idHabitacion) throws Exception
	{
		Habitacion habitacion = buscarHabitacionPorId(idHabitacion);

		if(habitacion == null)
		{
			throw new Exception("La habitacion a eliminar no existe");
		}

		//		if(getContratosByIdHabitacionEntreFechas(idHabitacion).size() != 0)
		//		{
		//			throw new Exception("La habitacion tiene reservas.");
		//		}

		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try
		{
			this.conn = darConexion();
			daoHabitacion.setConn( conn );

			if(daoHabitacion.findHabitacionById(habitacion.getId())!=null)
			{
				daoHabitacion.deleteHabitacion(habitacion);
			}
			else
			{
				throw new Exception("No existe una habitacion con ese id");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}



	public List<Habitacion> getAllHabitaciones() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteVivienda(Vivienda vivienda, String id) throws Exception {


		DAOVivienda daoVivienda = new DAOVivienda( );

		//		if(getContratosByIdVivienda(vivienda.getId()).size() != 0)
		//		{
		//			throw new Exception("La vivienda a eliminar tiene reservas");
		//		}

		try
		{
			this.conn = darConexion();
			daoVivienda.setConn( conn );
			if(daoVivienda.findViviendaById(vivienda.getId())!=null)
			{
				daoVivienda.deleteVivienda(vivienda, id);
			}
			else
			{
				throw new Exception("No existe una vivienda con ese id");
			}

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	private ArrayList<Contrato> getContratosByIdVivienda(int idHabitacion) throws Exception {

		Habitacion habitacion = buscarHabitacionPorId(idHabitacion);
		if(habitacion == null)
		{
			throw new Exception("La habitacion no existe");
		}

		DAOContrato daoContrato= new DAOContrato();
		ArrayList<Contrato> contratos = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			daoContrato.setConn(conn);
			contratos = daoContrato.getContratoByidViviendaEnFechas(idHabitacion, new Date(), new Date(Long.MAX_VALUE));

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daoContrato.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return contratos;

	}

	public List<Empresa> getAllEmpresas() throws Exception{
		// TODO Auto-generated method stub
		DAOEmpresa daoEmpresa = new DAOEmpresa();
		List<Empresa> bebedores;
		try 
		{
			this.conn = darConexion();
			daoEmpresa.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			bebedores = daoEmpresa.getEmpresas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoEmpresa.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return bebedores;
	}
	/**
	 * Metodo que modela la transaccion que busca el bebedor en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del bebedor a buscar. id != null
	 * @return Bebedor - Bebedor que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public String getTo20Contratos() throws Exception {
		DAOContrato daocontrato = new DAOContrato();
		String contratos = ""; 
		try 
		{
			this.conn = darConexion();
			daocontrato.setConn(conn);
			contratos = daocontrato.selectTop20();
  
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				daocontrato.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return contratos;
	}


	public String darUso() throws Exception
	{
		String rta = "";
		RFC5DAO dao5 = new RFC5DAO();
		try 
		{
			this.conn = darConexion();
			dao5.setConn(conn);
			rta = dao5.RFC5();

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao5.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public String RFC1() throws Exception
	{
		String rta = "";
		RFC1DAO dao1 = new RFC1DAO();
		try 
		{
			this.conn = darConexion();
			dao1.setConn(conn);
			rta = dao1.RFC1();

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao1.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public String RFC3(String fechaP) throws Exception
	{
		String rta = "";
		RFC3DAO dao3 = new RFC3DAO();
		try 
		{
			this.conn = darConexion();
			dao3.setConn(conn);
			rta = dao3.RFC3(fechaP);

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao3.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public String RFC4(String servicios, String fechaComienzo, String fechaFin) throws Exception
	{
		String rta = "";
		RFC4DAO dao4 = new RFC4DAO();
		try 
		{
			this.conn = darConexion();
			dao4.setConn(conn);
			rta = dao4.RFC4(servicios,fechaComienzo, fechaFin);

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao4.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public String RFC6(String id) throws Exception
	{
		String rta = "";
		RFC6DAO dao6 = new RFC6DAO();
		try 
		{
			this.conn = darConexion();
			dao6.setConn(conn);
			rta = dao6.RFC6(id);

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao6.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public ArrayList<Usuario> RFC8(String id) throws Exception
	{
		ArrayList<Usuario> rta = new ArrayList<>();
		RFC8DAO dao8 = new RFC8DAO();
		try 
		{
			this.conn = darConexion();
			dao8.setConn(conn);
			rta = dao8.RFC8(id);

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao8.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}

	public String RFC9() throws Exception
	{
		String rta = "";
		RFC9DAO dao9 = new RFC9DAO();
		try 
		{
			this.conn = darConexion();
			dao9.setConn(conn);
			rta = dao9.RFC9();

		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try { 
				dao9.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
	}


}
