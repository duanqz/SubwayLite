package com.baidu.m.subwaylite.view.listener;

/**
 * GestureView.
 * 
 * <p>
 * View that can handle gestures.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public interface Gestureable {

	/**
	 * Take actions when scrolling the view.
	 * 
	 * @param dx
	 * @param dy
	 */
	void onScroll(int dx, int dy);
	
	/**
	 * Take actions when zoom the view.
	 * @param sx
	 * @param sy
	 */
	void onZoom(float sx, float sy);
	
	/**
	 * Take actions when double click on view.
	 * @param x
	 * @param y
	 */
	void onDbClick(float x, float y);
	
	/**
	 * Take action when single click on view.
	 * @param x
	 * @param y
	 */
	void onSingleClick(float x, float y);
}
