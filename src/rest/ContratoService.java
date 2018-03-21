package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohonadesTransactionManager;
import vos.Contrato;

@Path("contrato")
public class ContratoService 
{

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

		
		/**
		 * Metodo que recibe un contrato en formato JSON y lo agrega a la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al contrato. <br/>
		 * <b>URL: </b> http://localhost:8080/Alohandes/rest/contrato <br/>
		 * @param bebedor JSON con la informacion del bebedor que se desea agregar
		 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que ha sido agregado <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		// Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		// Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addContrato(Contrato contrato) {

			//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );
				tm.addContrato(contrato);
				
				
				return Response.status( 200 ).entity( contrato ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
		
		/**
		 * Metodo que recibe un contrato en formato JSON y lo elimina de la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se elimina de la Base de datos al bebedor con la informacion correspondiente.<br/>
		 * <b>URL: </b> http://localhost:8080/Alohandes/rest/contrato <br/>
		 * @param bebedor JSON con la informacion del bebedor que se desea eliminar
		 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que se desea eliminar <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		// Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO PATH Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
		@DELETE
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteContrato(Contrato contrato) {
			//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );
				tm.deleteReserva(contrato);


				return Response.status( 200 ).entity( contrato ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
	
}
