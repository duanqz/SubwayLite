package com.baidu.m.subwaylite.subway.struct;

import java.io.Serializable;
import java.util.List;

/**
 * Station.
 * 
 * <p>
 * Station is a POJO used to cache the station information of subway, which is generated
 * from persistent data source.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class Station implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String TAG = "Station";
	
	// Table columns
	public static final String TABLE_NAME = "station";
	
	/** Station Column */
	public enum Column {
		/** Identifier of Station. */
		ID(0, "_id"),
		
		/** Name of station. */
		NAME(1, "name"),
		
		/** Detail information of station. */
		DETAIL(2, "detail"),
		
		/** Whether or not is a transfer station. */
		ISTRANSFER(3, "isTransfer"),
		
		/** Latitude. */
		LATITUDE(4, "latitude"),
		
		/** Longitude */
		LONGITUDE(5, "longitude");
		
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
	}
	
	/** Identifier of the station. */
	private Integer id;
	
	/** Local Id used to identify a station in a domain. */
	private Integer localId;
	
	/** Station name. */
	private String name;
	
	/** Station Detail. */
	private String detail;
	
	/** Is a transfer station. */
	private Boolean isTransfer;
	
	/** Latitude of the station. */
	private Float latitude;
	
	/** Longitude of the station. */
	private Float longitude;
	
	/** 
	 * Lines the station belongs to.
	 * 
	 * Many to many(Station and Line.)
	 */
	private List<Line> lines;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLocalId() {
		return this.localId;
	}
	
	public void setLocalId(Integer localId) {
		this.localId = localId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean isTransfer() {
		return isTransfer;
	}

	public void setTransfer(Boolean isTransfer) {
		this.isTransfer = isTransfer;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return name;
	}
}
