package com.baidu.m.subwaylite.strategy.impl;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import com.baidu.m.subwaylite.strategy.Path;
import com.baidu.m.subwaylite.strategy.ShortestPathStrategy;
import com.baidu.m.subwaylite.strategy.graph.Edge;
import com.baidu.m.subwaylite.strategy.graph.Graph;

/**
 * Dijkstra implementation of shortest path algorithm.
 * <p>
 * Cost of time  is O(N^2), 
 * cost of space is O(N+M),
 * where N is the number of vertices and M is the number of edges.
 * </p>
 * 
 * @author duanqizhi
 * 
 */
public class DijkstraStrategy implements ShortestPathStrategy {
	
	private Graph graph;
	
	private int[] vertices;
	
	/** A list of paths refer to the temporary shortest path from start to the index vertex. */
	private DijkstraPath[] paths;
	
	/**
	 * Constructor of Dijkstra strategy.
	 * @param graph
	 */
	public DijkstraStrategy(Graph graph) {
		this.graph = graph;
		this.vertices = graph.getVertexes();
		paths = new DijkstraPath[this.vertices.length];
		
	}
	
	@Override
	public Path findShortestPath(int start, int end) {
		// Step 1: Initiate Dijkstra paths and an unchecked list
		List<Integer> unchecked = new LinkedList<Integer>();
		initPathsAndUncheckedList(start, unchecked);
		
		int i, j, k;
		float weight;
		List<Edge> adjEdges;
		Edge edge;
		// Step 2: Update the path with the current minimum path index
		while(unchecked.size() > 0) {
			// Find the path having minimum weight from unchecked list
			k = findMinPathIndex(unchecked);
			unchecked.remove(Integer.valueOf(k));
			
			// Update the shortest path with k if necessary
			adjEdges = graph.getAdjacentEdgesOf(k);
			for(i = 0; i < adjEdges.size(); i++) {
				edge = adjEdges.get(i);
				j = (edge.getVertex1() == k) ? edge.getVertex2() : edge.getVertex1();
				weight = paths[k].getWeight() + edge.getWeight();
				if(weight < paths[j].getWeight()) {
					paths[j].setWeight(weight);
					paths[j].setPrePathIndex(k);
				}
			}
		}
		
		// Step 3: Set the vertex on the shortest path from back to front.
		DijkstraPath sPath = paths[end];
		List<Integer> vertices = sPath.getVertexes();
		vertices.add(end);
		int prePathIndex;
		do {
			prePathIndex = sPath.getPrePathIndex();
			vertices.add(prePathIndex);
			sPath = paths[prePathIndex];
		} while(prePathIndex != start);
		
		return paths[end];
	}
	
	/**
	 * Initial Dijkstra paths and an unchecked list of vertices.
	 * <br/>
	 * Each vertex in unchecked list is to be checked to update the shortest path.
	 * 
	 * @param start
	 * @return
	 */
	private void initPathsAndUncheckedList(int start, List<Integer> uncheckedList) {
		Assert.assertNotNull(uncheckedList);
		Edge edge;
		for(int i = 0; i < vertices.length; i++) {
			uncheckedList.add(i);
			// Set the initial values of the paths
			paths[i] = new DijkstraPath();
			edge = graph.getEdge(start, i);
			if(edge != null) {
				paths[i].setWeight(edge.getWeight());
			}
			paths[i].setPrePathIndex(start);
		}
		
		// The weight from start to start is zero.
		paths[start].setWeight(0);
		uncheckedList.remove(Integer.valueOf(start));
	}
	
	/**
	 * Find the path which has minimum weight
	 * @param vertices
	 * @return The path index.
	 */
	private int findMinPathIndex(List<Integer> vertices) {
		int minIndex = -1;
		float minWeight = Float.MAX_VALUE;
		for(int k : vertices) {
			if(paths[k].getWeight() < minWeight) {
				minIndex = k;
			}
		}
		
		return minIndex;
	}
}
