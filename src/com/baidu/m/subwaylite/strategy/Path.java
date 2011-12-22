package com.baidu.m.subwaylite.strategy;

import java.util.List;

/**
 * Path refers to a list of vertices, and has its weight. 
 * 
 * @author duanqizhi
 *
 */
public interface Path {

	/**
	 * Get the weight of the path.
	 * 
	 * @return Weight value.
	 */
	float getWeight();
	
	/**
	 * Get a list of vertices from end to start of the path.
	 * 
	 */
	List<Integer> getVertexes();
}
