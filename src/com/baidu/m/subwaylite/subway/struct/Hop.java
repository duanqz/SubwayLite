package com.baidu.m.subwaylite.subway.struct;

import java.io.Serializable;

/**
 * Hop.
 * 
 * <p>
 * Hop is a POJO used to cache the hop information of subway, which is
 * generated from persistent data source.
 * </p>
 * 
 * @author duanqizhi
 */
public class Hop implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String Table_Name = "hop";
	
	/**
	 * Table columns of the hop.
	 */
	public enum Column {
		/** Identifier of the Hop. */
		ID(0, "_id"),
		
		/** Identifier of station 1. */
		STATION1(1, "station1_id"),
		
		/** Identifier of station 2. */
		STATION2(2, "station2_id"),
		
		/** Time cost of the hop. */
		TIME_COST(3, "time_cost"),
		DISTANCE(4, "distance");
		
		private int colIndex;
		
		private String colName;
		
		private Column(int colIndex, String colName) {
			this.colIndex = colIndex;
			this.colName = colName;
		}
		
		public int getColIndex() {
			return colIndex;
		}
		
		public String getColName() {
			return colName;
		}
	} // end of enum Column

	private Integer id;
	
	private Station station1;
	
	private Station station2;
	
	private Float timeCost;
	
	private Float distance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Station getStation1() {
		return station1;
	}

	public void setStation1(Station station1) {
		this.station1 = station1;
	}

	public Station getStation2() {
		return station2;
	}

	public void setStation2(Station station2) {
		this.station2 = station2;
	}

	public Float getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(Float timeCost) {
		this.timeCost = timeCost;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Hop From ")
		.append(station1.getName())
		.append(" To ")
		.append(station2.getName());
		return sb.toString();
	}
}
