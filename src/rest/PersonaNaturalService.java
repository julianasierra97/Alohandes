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

import vos.Empresa;
import vos.Habitacion;
import vos.PersonaNatural;
import vos.Vivienda;

@Path("personaNatural")
public class PersonaNaturalService 
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
			 * Metodo que recibe una empresa en formato JSON y lo agrega a la Base de Datos <br/>
			 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al la empresa. <br/>
			 * <b>URL: </b> http://localhost:8080/Alohandes/rest/empresa <br/>
			 * @param bebedor JSON con la informacion del bebedor que se desea agregar
			 * @return	<b>Response Status 200</b> - JSON que contiene al la empresa que ha sido agregado <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */
			// Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

			// Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
			@POST
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response addPersona(PersonaNatural persona) {

				//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
				try{
					AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );
					tm.agregarPersonaNatural(persona);
					
					
					return Response.status( 200 ).entity( persona ).build( );			
				}
				catch( Exception e )
				{
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}
			/**
			 * Metodo GET que trae al bebedor en la Base de Datos con el ID dado por parametro <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores/{id} <br/>
			 * @return	<b>Response Status 200</b> - JSON Bebedor que contiene al bebedor cuyo ID corresponda al parametro <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */
			@POST
			@Path("agregarVivienda")
			@Produces( { MediaType.APPLICATION_JSON } )
			public Response addViviendaParaPersona(@QueryParam("id_persona")String id_persona, Vivienda vivienda)
			{
				try{
					AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );

					tm.agregarVivienda(vivienda, id_persona);
					return Response.status( 200 ).entity( vivienda ).build( );			
				}
				catch( Exception e )
				{
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}
			/**
			 * Metodo GET que trae al bebedor en la Base de Datos con el ID dado por parametro <br/>
			 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
			 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores/{id} <br/>
			 * @return	<b>Response Status 200</b> - JSON Bebedor que contiene al bebedor cuyo ID corresponda al parametro <br/>
			 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
			 */
			@POST
			@Path("agregarHabitacion")
			@Produces( { MediaType.APPLICATION_JSON } )
			public Response addHabitacionParaPersona(@QueryParam("id_persona")String id_persona,
					@QueryParam("id_seguro")Integer id_seguro, Habitacion habitacion)
			{
				try{
					AlohonadesTransactionManager tm = new AlohonadesTransactionManager( getPath( ) );

					tm.agregarHabitacion(habitacion, id_persona);
					return Response.status( 200 ).entity( habitacion ).build( );			
				}
				catch( Exception e )
				{
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}
			
			
			@GET
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getPersonas() {

				try {
					AlohonadesTransactionManager tm = new AlohonadesTransactionManager(getPath());

					List<PersonaNatural> personas;
					//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
					personas = tm.getAllPersonas();
					return Response.status(200).entity(personas).build();
				} 
				catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
			}

			
			
}
