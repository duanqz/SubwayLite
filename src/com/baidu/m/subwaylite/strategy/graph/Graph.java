package com.baidu.m.subwaylite.strategy.graph;

import java.util.List;

/**
 * Graph.
 * 
 * <p>
 * Storage format is adjacent matrix.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class Graph {

	private int[] vertices;
	
	private List<List<Edge>> edges;

	public Graph() {
		
	}
	
	/**
	 * Get an edge of the graph.
	 * 
	 * @param start
	 * @param end
	 * @return {@link Edge}
	 */
	public Edge getEdge(int start, int end) {
		for(Edge edge : edges.get(start)) {
			if(edge.getVertex1() == end || edge.getVertex2() == end) {
				return edge;
			}
		}
		return null;
	}
	
	public int[] getVertexes() {
		return vertices;
	}

	/**
	 * Set vertices.
	 * 
	 * @param vertices
	 */
	public void setVertexes(int[] vertices) {
		this.vertices = vertices;
	}
	
	/**
	 * Set edges.
	 * 
	 * @param edges
	 */
	public void setEdges(List<List<Edge>> edges) {
		this.edges = edges;
	}
	
	/**
	 * Get adjacent edges of the vertex.
	 * 
	 * @param vertex
	 * @return
	 */
	public List<Edge> getAdjacentEdgesOf(int vertex) {
		return edges.get(vertex);
	}
}
