package Progra3.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Progra3.graphs.Edge;
import Progra3.graphs.Form;
import Progra3.graphs.Graph;
import Progra3.graphs.JsonObj;
import Progra3.graphs.Node;
import Progra3.graphs.ResourcesMap;

@Path("/graphs") 		// Path por la que responderá el servicio
public class NodeResources {
	
	/** Metodo que crea un arista en el grafo
	 * 
	 * @param id: id del grafo a crear el arista
	 * @param newEdge: Objeto Edje producido a traves de un Json por parte del server
	 * @return 200 si todo sale bien, 500 en otro caso
	 */
	
	@POST
	@Path("/{id}/edges")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEdge(@PathParam("id") String id, Edge newEdge) {
		// id se refiere al id del grafo
		
		if(ResourcesMap.GraphMap.get(id) != null) {	// Si encuentra el grafo con el id indicado
		
			newEdge.setId(UUID.randomUUID().toString());	// NewEdge es el Edge obtenido por el Json
			ResourcesMap.EdgesMap.put(newEdge.getId(), newEdge);	// Agrega el arista al map de aristas
			return Response.ok().build();
			
		}else {			
			return Response.status(500).entity("Error, el grafo especificado no existe").build();
		}
		
	}
	
	/**
	 * Crea un nuevo grafo y lo agrega al mapa de los grafos
	 * @return: Objeto Json con el id del grafo
	 */
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGraph() throws Exception{
		
		try {
		Graph graph = new Graph();	// Creo un grafo
		ResourcesMap.GraphMap.put(graph.getId(), graph);	// Añado la llave: id del grafo, clave: grafo
		
		JsonObj json = new JsonObj(graph.getId());		// Creo objeto Json con id del grafo
			return Response.ok(json).build();
		} catch(Exception e) {
			return Response.status(500).build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGraphs() {
		
		return Response.ok(ResourcesMap.GraphMap.values()).build();
	}
	
	/**
	 * Elimina todos los grafos del servidor
	 * @return: Nada, solo elimina grafos del servidor
	 */
	@DELETE
	public Response deleteGraphs() {
		
		ResourcesMap.GraphMap.clear(); 	// Elimina todos los maps del graphmap
		
		return Response.ok().build();	// Respuesta de eliminación correcta
	}
	
	/**
	 * Retorna el grafo con el id indicado
	 * @return: Objeto tipo graph, el indicado por el id
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGraph(@PathParam("id") String id) {
		
		Graph graph= ResourcesMap.GraphMap.get(id);
		
		if(graph != null) {
			return Response.ok(graph).build();
		}else {
			ArrayList l = new ArrayList();
			return Response.ok(l).build();
		}
	} 
	
	/**
	 * Elimina un grafo indicado por id
	 * @param id: Id del grafo que quiere eliminar
	 * @return: Respuesta 200 o 404
	 */
	
	@DELETE
	@Path("/{id}")
	public Response deleteGraph(@PathParam("id") String id) {
		
		Graph graph =  ResourcesMap.GraphMap.remove(id);
		
		if( graph != null) {		// Se borró correctamente
			return Response.ok().build();
		}else {
			return Response.status(404).build();
		}
	}
	
	/**
	 * Crea un nuevo nodo en el grafo del id indicado
	 * @return: Objeto Json con id
	 */
	
	@POST
	@Path("/{id}/nodes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNode(@PathParam("id") String id, Node newNode) {
		
		try {
		
			Graph graph = ResourcesMap.GraphMap.get(id);	// Obtengo el grafo accesando con la llave del id
			graph.addNode(newNode);		// Agrego el nodo indicado por el Json
			return Response.ok(new JsonObj(newNode.getId())).build();		// Creo objeto json con el id del nodo que cree
		}catch(Exception e) {
			
			return Response.status(404).build();
		}
		
	}
	
	/**
	 * Retorna una lista con los nodos de un grafo especificado por id
	 * @return: Lista con los nodos del grafo
	 */
	@GET
	@Path("/{id}/nodes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNodes(@PathParam("id") String id) {
		
		try {
			
			Graph graph = ResourcesMap.GraphMap.get(id);	// Obtengo el grafo identificado por id
			ArrayList<Node> nodes = graph.getNodes();	// Obtengo la lista de nodos del grafo
			return Response.ok(nodes).build();
		} catch(Exception e) {
			return Response.status(404).build();
		}
		
	}

	/**
	 * Actualiza la entidad de un nodo en un grafo y nodo indicado
	 * @param idGraph: Id del grafo
	 * @param idNode: Id del nodo
	 * @return status 200 si se actualiza correctamente, 500 en caso de error
	 */
	
	@PUT
	@Path("/{id1}/nodes/{id2}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNode(@PathParam("id1") String idGraph, @PathParam("id2") String idNode, Node nodeInf) {
		
		try {
			
			Graph graph = ResourcesMap.GraphMap.get(idGraph);	// Obtengo el grafo identificado por id
			ArrayList<Node> nodes = graph.getNodes();	// Obtengo la lista de nodos del grafo
			Boolean founded = false;

			for(Node node: nodes) {	 // Recorro la lista de nodos, para buscar el que tengo que actualizar

				if(node.getId().equals(idNode)) {	// Cuando encuentro el nodo por id
					
					node.setEntity(nodeInf.getEntity());	// Cambio al entidad del nodo almacenado
					founded = true;		// Para saber que encontró el elemento
				}

			}

			if(!founded) {		// Si no encuentra el nodo manda error
				return Response.status(500).build();
			}else {		// Sino manda ok
				return Response.ok().build();
			}

		} catch(Exception e) {
			return Response.status(500).build();
		}
		
		
	}

	/**
	 * Elimina un nodo especificado en un grafo especificado
	 * @param idGraph: Id del grafo
	 * @param nodeId: Id del nodo
	 * @return
	 */
	
	//************************************ ARREGLAR **************************************************
	
	@DELETE
	@Path("/{id1}/nodes/{id2}")
	public Response deleteNode(@PathParam("id1") String idGraph, @PathParam("id2")String idNode){
		
		if(ResourcesMap.GraphMap.isEmpty()) {
			return Response.status(404).build();
		}else {
			return Response.ok().build();
		}
		/*
		Graph graph = ResourcesMap.GraphMap.get(idGraph);	// Obtengo el grafo identificado por id
		ArrayList<Node> nodes = graph.getNodes();	// Obtengo la lista de nodos del grafo
		Boolean eliminated = false;
		
		if(nodes == null) {
			return Response.status(500).build();
		}else {

			for(Node node: nodes) {	 // Recorro la lista de nodos, para buscar el que tengo que actualizar

				if(node.getId().equals(idNode)) {	// Cuando encuentro el nodo por id

					nodes.remove(node);		// Elimina el nodo del arreglo de nodos
					eliminated = true;
				}

			}
			
			if(eliminated) {
				return Response.ok().build();
			}else {
				return Response.status(500).build();
			}
			
			
		}
	*/
		
	}
	
	/**
	 * Se encarga de eliminar la lista de nodos de un grafo especificado por id
	 * @param id: Id del grafo
	 * @return: 200 exito, 500 error
	 */
	
	@DELETE
	@Path("/{id}/nodes")
	public Response deleteNode(@PathParam("id") String id) {
		
		Graph graph = ResourcesMap.GraphMap.get(id);
		
		if(graph == null) {
			return Response.status(500).build();
		}else {
			
			graph.getNodes().clear();
			
			return Response.ok().build();
		}
		
	}
	
	@GET
	@Path("/{id}/edges")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEdges(@PathParam("id") String id) {
		
		Graph graph = ResourcesMap.GraphMap.get(id);
		
		if(!(graph == null)) {
			
			return Response.ok(graph.getEdges()).build();
		}else {
			return Response.status(500).build();
		}
		
		
	}
	
	/**
	 * Elimina todas las aristas de un grafo especificado
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}/edges")
	public Response deleteEdges(@PathParam("id") String id) {
		
		Graph graph = ResourcesMap.GraphMap.get(id);
		
		if(!(graph == null)) {	// Si encuentra el grafo especificado
			
			if(graph.getEdges().isEmpty()) {	// Si no tiene aristas
				return Response.status(404).build();
			}else {		// Si tiene aristas, las elimina
				graph.getEdges().clear();
				return Response.ok().build();
			}
			
		}else {
			
			return Response.status(500).build();
		}
		
	}
	
	@PUT
	@Path("/{id1}/edges/{id2}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizeEdge(@PathParam("id1") String idGraph, @PathParam("id2") String idEdge, Edge edgeInfo) {
		
		Graph graph = ResourcesMap.GraphMap.get(idGraph);
		
		if(!(graph == null)) { 		// Si el grafo no es nulo
			
			ArrayList<Edge> edges = graph.getEdges();	// Obtengo la lista de aristas
			Boolean finded = false;		// para saber si encuentra el arista a actualizar
			
			for(Edge edge: edges) {			// Recorro el arreglo, buscando por id
				
				if(edge.getId().equals(idEdge)) {	// Si los id son iguales
					
					edge = edgeInfo;
					finded = true;
					
				}
				
			}
			
			if(finded) {
				return Response.ok().build();
			}else {
				return Response.status(404).build();
			}
			
			
		}else {			// El grafo esta vacío
			
			return Response.status(500).build();
		}
		
	}
		
	/**
	 * Elimina un arista especificado
	 * @param idGraph
	 * @param idEdge
	 * @return
	 */
	@DELETE
	@Path("/{id1}/edges/{id2}")
	public Response deleteEdge(@PathParam("id1") String idGraph, @PathParam("id2") String idEdge) {
		
		Graph graph = ResourcesMap.GraphMap.get(idGraph);	// Obtengo el grafo con el id especificado
		
		if(!(graph == null)) {	// Si lo encuentra
			
			ArrayList<Edge> edges = graph.getEdges();	// Obtengo las aristas del grafo
			Boolean eliminated = false;
			
			for(Edge edge: edges) {		// Recorro la lista para buscar el arista a eliminar
				
				if(edge.getId().equals(idEdge)) {	// Si son iguales los id
					
					edges.remove(edge);
					eliminated = true;
							
				}

			}
			
			if(eliminated) {		// si la encontro y elimino
				return Response.ok().build();
			}else {
				return Response.status(404).build();
			}
			
		}else {		// Si no lo encuentra
			return Response.status(500).build();
		}
		
	}
	
	
	@POST
	@Path("/getCSV")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeGraph(ArrayList<String> delinquents) {
		
		if(delinquents == null) {
			return Response.status(500).build();
		}else {
			
			return Response.status(200).entity(delinquents).build();
		}
	}
	

}
