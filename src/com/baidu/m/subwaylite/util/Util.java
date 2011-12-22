package com.baidu.m.subwaylite.util;

import junit.framework.Assert;

public class Util {

	/**
	 * Get the minimum and maximum value of an array.
	 * 
	 * @param array
	 * @return range[0] is the minimum value, range[1] is the maximum value.
	 */
	public static float[] getMinNMax(float[] array) {
		Assert.assertNotNull(array);
		
		float[] range = new float[]{Float.MAX_VALUE, Float.MIN_VALUE};
		int i = 0, j = array.length - 1;
		/* 
		 * Find the minimum and maximum simultaneously
		 * Time cost is 3 * (n/2) 
		 */
		while(i < j) {
			if(array[i] < array[j]) {
				if(array[i] < range[0])  range[0] = array[i];
				
				if(array[j] > range[1])  range[1] = array[j];
			} else {
				if(array[i] > range[1])  range[1] = array[i];
				
				if(array[j] < range[0])  range[0] = array[j];
			}
			
			i++;
			j--;
		}
		return range;
	}
	
}
