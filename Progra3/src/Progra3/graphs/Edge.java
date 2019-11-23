package Progra3.graphs;

import java.util.UUID;

public class Edge {

	private String id;
	private String start;
    private String end;
    private double weight;
    
    public Edge() {
    	
    }
    
    public Edge(String startNode, String endNode, double weight) {
    	
    	this.id = UUID.randomUUID().toString();
    	this.start = startNode;
    	this.end = endNode;
    	this.weight = weight;
    	
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
    
}
