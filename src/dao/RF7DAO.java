package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohonadesTransactionManager;
import vos.Contrato;
import vos.Habitacion;
import vos.Servicio;

@Path("RFC7")
public class RF7DAO {



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
	public RF7DAO() {
		recursos = new ArrayList<Object>();

	}


	public String reservaColectiva(int cantidad, String servicios, String tipoAlojamiento, String fechaInicio, String fechaFin) throws Exception
	{
		conn.setAutoCommit(false);
		
		RFC4DAO necesito = new RFC4DAO();

		String rta = "";
		int cantidadTotal = 0;
		ArrayList<String> alojamientos = new ArrayList<>();

		String sql1 = String.format("SELECT COUNT(ID) AS CUENTA FROM HABITACION");

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);

		String sql2 = String.format("SELECT COUNT(ID) AS CUENTA FROM VIVIENDA");

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);

		ResultSet rs1=prepStmt1.executeQuery();
		ResultSet rs2=prepStmt2.executeQuery();


		if(rs1.next() && rs2.next())
		{
			cantidadTotal += rs1.getInt("CUENTA");
			cantidadTotal += rs2.getInt("CUENTA");
			if(cantidadTotal < cantidad)
			{
				rta = "La cantidad especificada es mayor a la disponible";
				conn.rollback();
				return rta;
			}

			String alojamientosUtiles = necesito.RFC4(servicios, fechaInicio, fechaFin);
			String[] arregloIdAlojamientos = alojamientosUtiles.split(",");

			//Quiero obtener el tipo de cada uno de los alojamientos que me es util.
			//Pienso popner condicionales que me ayuden a mirar que tipo de alojamiento es: habitacion o vivienda 
			if(tipoAlojamiento.equals(Habitacion.ESTANDAR)||tipoAlojamiento.equals(Habitacion.SUITES)||tipoAlojamiento.equals(Habitacion.SEMI_SUITES))
			{
				for (String idAlojamiento : arregloIdAlojamientos) {
					String sql3 = String.format("SELECT TIPO FROM TIPOHABITACION WHERE ID =" + idAlojamiento);

					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);

					ResultSet rs3=prepStmt1.executeQuery();
					while(rs3.next())
					{
						if(rs3.getString("TIPO").equals(tipoAlojamiento))
						{
							alojamientos.add(idAlojamiento);
							//TODO
						}
					}
				}
			}
			else if(tipoAlojamiento.equals(Servicio.HABITACION_COMPARTIDA))
			{
				for (String idAlojamiento : arregloIdAlojamientos) {
					String sql3 = String.format("SELECT ID_VIVIENDA FROM SERVICIOVIVIENDA WHERE IDSERVICIO = 21");

					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);

					ResultSet rs3=prepStmt1.executeQuery();
					while(rs3.next())
					{
						if(rs3.getInt("ID_VIVIENDA") == Integer.parseInt(idAlojamiento))
						{
							alojamientos.add(idAlojamiento);
						}
					}

				}
			}

			if(alojamientos.size()<cantidad)
			{
				rta += "No es posible generar la reserva, la cantidad de alojamientos solicitado es menor que la cantidad de alojamientos disponibles.";
				conn.rollback();
				return rta;
			}
			else{
				if(tipoAlojamiento.equals(Habitacion.ESTANDAR)||tipoAlojamiento.equals(Habitacion.SUITES)||tipoAlojamiento.equals(Habitacion.SEMI_SUITES))
				{
					DAOContrato daoContrato = new DAOContrato();
					
					conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					for (String idAlojamiento : arregloIdAlojamientos) {
						//TODO
					}
				}
			}


		}

		return rta;
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
