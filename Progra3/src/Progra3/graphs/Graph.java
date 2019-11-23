package Progra3.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Graph {

	private String id;
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	 
	public Graph() {
		
		this.id = UUID.randomUUID().toString();
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		
	}
	
    public void addNode(Node node) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
    }
 
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    public ArrayList<Edge> getEdges() {
        return edges;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    
    
}
