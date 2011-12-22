package com.baidu.m.subwaylite.view.maplayer;

import android.graphics.Paint;

import com.baidu.m.subwaylite.view.subwaymap.MapView;

/**
 * Abstract implementation of {@link MapLayer}.
 * 
 * @author duanqizhi
 */
public abstract class AbstractMapLayer implements MapLayer {

	protected MapView mapView;
	
	/** Paint to draw the layer. */
	protected Paint paint;
	
	protected AbstractMapLayer(MapView mapView) {
		this.mapView = mapView;
		this.paint = new Paint();
	}
}
