package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohonadesTransactionManager;
import tm.ParranderosTransactionManager;
import vos.Bebedor;
import vos.Habitacion;
import vos.Vivienda;

@Path("viviendas")
public class ViviendaService {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS REST
	//----------------------------------------------------------------------------------------------------------------------------------


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVivienda(@QueryParam("id_operador")String idPersona, Vivienda vivienda) {

		try{
			AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );

			tm.agregarVivienda(vivienda, idPersona, vivienda.getSeguro().getId());
			
			return Response.status( 200 ).entity( vivienda ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteVivienda(Vivienda vivienda) {
		try{
			AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );
			tm.deleteVivienda(vivienda);


			return Response.status( 200 ).entity( vivienda ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

}
