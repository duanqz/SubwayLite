package com.baidu.m.subwaylite.provider;

import android.provider.BaseColumns;

public class Subway {

	
	public static class Line implements BaseColumns {
		
		/**
		 * Name of the line.
		 * <p>Type: TEXT</p>
		 */
		public static final String NAME = "name";
		
		/**
		 * Color of the line.
		 * <p>Type: TEXT</p>
		 */
		public static final String COLOR = "color";
	}
	
	public static class Station implements BaseColumns {
		
		/**
		 * Name of the line.
		 * <p>Type: TEXT</p>
		 */
		public static final String NAME = "name";
		
		/**
		 * Detail of the station.
		 * <p>Type: TEXT</p>
		 */
		public static final String DETAIL = "detail";
		
		/**
		 * Is a transfer station.
		 * <p>Type: INTEGER</p>
		 */
		public static final String IS_TRANSFER = "isTransfer";
		
		/**
		 * Latitude of the station.
		 * <p>Type: FLOAT</p>
		 */
		public static final String LATITUDE = "latitude";
		
		/**
		 * Latitude of the station.
		 * <p>Type: FLOAT</p>
		 */
		public static final String LONGITUDE = "longitude";
	}
	
	public static class Hop implements BaseColumns {
		
		/**
		 * One of the station
		 * <p>TYPE: INTEGER</p>
		 */
		public static final String STATION_1 = "station1_id";
		
		/**
		 * One of the station
		 * <p>TYPE: INTEGER</p>
		 */
		public static final String STATION_2 = "station2_id";
		
		/**
		 * Time cost of the hop.
		 * <p>TYPE: FLOAT</p>
		 */
		public static final String TIME_COST = "time_cost";
		
		/**
		 * Distance of the hop.
		 * <p>TYPE: FLOAT</p>
		 */
		public static final String DISTANCE = "distance";
	}
	
	public static class LineStation implements BaseColumns {
		/**
		 * <p>TYPE: INTEGER</p>
		 */
		public static final String LINE = "line_id";
		
		/**
		 * <p>TYPE:INTEGER</p>
		 */
		public static final String STATION = "station_id";
	}
}
