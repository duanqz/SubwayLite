package com.baidu.m.subwaylite.subway.struct;

import java.io.Serializable;

/**
 * Line.
 * 
 * <p>
 * Line is a POJO used to cache the line information of subway, which is
 * generated from persistent data source.
 * </p>
 * 
 * @author duanqizhi
 */
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "line";
	
	public enum Column {
		/** Identifier of Line */
		ID(0, "_id"),
		
		/** Line Name */
		NAME(1, "name"),
		
		/** Line Color */
		COLOR(2, "color");
		
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
	
	private String name;

	private String color;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return name;
	}
}
