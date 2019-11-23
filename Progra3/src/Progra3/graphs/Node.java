package Progra3.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Node {

	private String id;
	private String entity;
	private ArrayList<Edge> edges;

	public Node() {
		
		this.entity = "";
		this.id = UUID.randomUUID().toString();
	}
	

	public String getEntity() {
		return entity;
	}


	public void setEntity(String entity) {
		this.entity = entity;
	}



	public ArrayList<Edge> getEdges() {
		return edges;
	}



	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void addEdge(Edge edge) {
        if (edges == null) {
            edges = new ArrayList<>();
        }
        edges.add(edge);
    }
	 
}
