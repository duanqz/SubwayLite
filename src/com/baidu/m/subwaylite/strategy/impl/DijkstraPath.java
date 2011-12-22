package com.baidu.m.subwaylite.strategy.impl;

import java.util.LinkedList;
import java.util.List;

import com.baidu.m.subwaylite.strategy.Path;

/**
 * Dijkstra Path.
 * 
 * @author duanqizhi
 *
 */
public class DijkstraPath implements Path {
	
	private List<Integer> vertices = new LinkedList<Integer>();
	
	private int prePathIndex = -1;
	
	private float weight = Float.MAX_VALUE;
	
	@Override
	public float getWeight() {
		return weight;
	}
	
	@Override
	public List<Integer> getVertexes() {
		return vertices;
	}
	
	public int getPrePathIndex() {
		return prePathIndex;
	}
	
	public void setPrePathIndex(int prePathIndex) {
		this.prePathIndex = prePathIndex;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
