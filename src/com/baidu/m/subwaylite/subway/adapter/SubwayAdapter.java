package com.baidu.m.subwaylite.subway.adapter;

import com.baidu.m.subwaylite.subway.struct.Subway;


/**
 * SubwayAdapter.
 * 
 * <p>
 * Used to fill domain structure of subway from persistent data.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public interface SubwayAdapter {
	
	/**
	 * Open data connection.
	 */
	SubwayAdapter open();
	
	/**
	 * Set the subway structure to be filled.
	 * 
	 * @param subway
	 */
	SubwayAdapter setSubway(Subway subway);

	/**
	 * Fill the data.
	 * 
	 * The {@link Subway} structure must NOT be null before call this method. 
	 */
	SubwayAdapter fillData();
	
	
	/**
	 * Close data connection.
	 */
	SubwayAdapter close();
}
