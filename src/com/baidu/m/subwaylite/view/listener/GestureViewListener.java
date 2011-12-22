package com.baidu.m.subwaylite.view.listener;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


/**
 * GestureViewListener.
 * 
 * <p>
 * Listener to gestures happened in view.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class GestureViewListener extends SimpleOnGestureListener {

	private Gestureable view;
	
	/**
	 * Constructor.
	 * @param view
	 */
	public GestureViewListener(Gestureable view) {
		this.view = view;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
		view.onScroll((int) dx, (int) dy);
		return true;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		view.onDbClick(e.getRawX(), e.getRawY());
		return true;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		view.onSingleClick(e.getRawX(), e.getRawY());
		return true;
	}
	
}
