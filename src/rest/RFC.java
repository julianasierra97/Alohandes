package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohonadesTransactionManager;
import vos.Contrato;

@Path("RFC")
public class RFC {
	
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

		@GET 
		@Path("RFC1")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC1()
		{
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC1();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET
		@Path("RFC2")
		@Produces({ MediaType.TEXT_PLAIN })
		public Response getTop20Contratos() {

			try {
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String contratos;
				//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
				contratos = tm.getTo20Contratos();
				return Response.status(200).entity(contratos).build();
			} 
			catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC3")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC3()
		{
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC3("31/12/2016");
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@POST
		@Path("RFC4")
		@Produces({ MediaType.TEXT_PLAIN})
		@Consumes({ MediaType.APPLICATION_JSON})
		public Response RFC4( vos.RFC4 rfc4)
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC4(rfc4.getServicios(),rfc4.getFechaInicio(),rfc4.getFechaFin());
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		
		@GET 
		@Path("RFC5")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC5()
		{
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.darUso();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		
		@GET 
		@Path("RFC6/{id: \\\\d+}")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC6  (@QueryParam("id") String id)
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC6(id);
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC8")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC8( )
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC8();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC9")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC9( )
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC9();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}

}
