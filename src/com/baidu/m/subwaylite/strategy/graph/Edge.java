package com.baidu.m.subwaylite.strategy.graph;

/**
 * Edge.
 * 
 * <p>
 * Edge has two vertices and a weight value.
 * </p>
 * 
 * @author duanqizhi
 */
public class Edge {
	
	private int vertex1;
	private int vertex2;
	private float weight;
	
	public Edge() {
		
	}

	public int getVertex1() {
		return vertex1;
	}

	public void setVertex1(int vertex1) {
		this.vertex1 = vertex1;
	}

	public int getVertex2() {
		return vertex2;
	}

	public void setVertex2(int vertex2) {
		this.vertex2 = vertex2;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
