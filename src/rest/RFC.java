package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohonadesTransactionManager;

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
		@Path("RFC5")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response getUso()
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
		@Path("RFC3")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC3()
		{
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC3();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC4")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC4( )
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC4();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC6")
		@Produces({ MediaType.TEXT_PLAIN})
		public Response RFC6( )
		{
			//todo preguntar bien los path param
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

				String rta = tm.RFC6();
				return Response.status(200).entity(rta).build();
			}
			catch(Exception e){
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		
		@GET 
		@Path("RFC7")
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
