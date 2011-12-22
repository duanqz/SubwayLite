package com.baidu.m.subwaylite.subway;

import com.baidu.m.subwaylite.strategy.ShortestPathStrategy;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;

/**
 * 
 * Subway strategy.
 * 
 * <p>
 * </p>
 * 
 * @author duanqizhi
 */
public interface SubwayStrategy {

	/**
	 * Set shortest path finding strategy of the subway.
	 * 
	 * @param strategy
	 * @return The {@link Subway} object.
	 */
	Subway setShortestPathStrategy(ShortestPathStrategy strategy);
	
	/**
	 * Get the shortest path between start and end station.
	 * 
	 * @param start Start station
	 * @param end End station
	 * @return {@link SubwayShortestPath}.
	 */
	SubwayShortestPath getSubwayShortestPath(Station start, Station end);
}
