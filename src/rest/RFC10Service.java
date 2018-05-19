package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oracle.jdbc.proxy.annotation.Post;
import tm.AlohonadesTransactionManager;
import vos.CondicionesRFC10;

@Path( "RFC10" )
public class RFC10Service {

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
	@Path("hola")
	@Consumes({ MediaType.APPLICATION_JSON})
	@Produces({ MediaType.TEXT_PLAIN})
	public Response RF9(CondicionesRFC10 condicion)
	{
		try{
			AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

			String rta = tm.RFC10("", condicion);
			return Response.status(200).entity(rta).build();
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

}
