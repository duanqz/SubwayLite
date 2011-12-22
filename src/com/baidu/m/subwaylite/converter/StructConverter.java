package com.baidu.m.subwaylite.converter;

/**
 * Structure converter.
 * 
 * @author duanqizhi
 *
 * @param <SRC>
 * @param <DEST>
 */
public interface StructConverter<SRC, DEST> {

	/**
	 * Convert SRC structure to DEST structure.
	 * 
	 * @param srcStruct
	 * @param destStruct
	 * @return Whether convert successfully or not.
	 */
	boolean convert(SRC srcStruct, DEST destStruct);
}
