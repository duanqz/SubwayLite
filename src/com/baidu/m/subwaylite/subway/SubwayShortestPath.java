package com.baidu.m.subwaylite.subway;

import java.util.List;

import com.baidu.m.subwaylite.subway.struct.Station;

/**
 * Subway Shortest Path.
 * 
 * @author duanqizhi
 *
 */
public interface SubwayShortestPath {

	/**
	 * Get stations on the subway shortest path.
	 * 
	 * @return A list of {@link Station}s
	 */
	List<Station> getStations();
	
	/**
	 * Get weight of the subway shortest.
	 * 
	 * @return Weight of the path.
	 */
	float getWeight();
}
