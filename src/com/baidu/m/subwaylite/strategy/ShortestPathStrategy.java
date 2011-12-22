package com.baidu.m.subwaylite.strategy;



/**
 * Shortest Path refers to a list of vertices, which has the least weight. 
 * 
 * @author duanqizhi
 *
 */
public interface ShortestPathStrategy {

	/**
	 * To find the shortest path from start to end.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	Path findShortestPath(int start, int end);
}
