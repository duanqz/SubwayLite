package com.baidu.m.subwaylite.view.maplayer.station;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.baidu.m.subwaylite.R;
import com.baidu.m.subwaylite.view.maplayer.AbstractMapLayer;
import com.baidu.m.subwaylite.view.subwaymap.MapView;

/**
 * Station Layer.
 * 
 * <p>
 * To draw on the subway map.
 * </p>
 * @author duanqizhi
 *
 */
public class StationMarkLayer extends AbstractMapLayer {

	/** Marker styles of station. */
	public enum StationStyle {
		START, END, TRANSFER
	};
	
	/** Mark as a bitmap. */
	private Bitmap bitmap;
	
	/** Layer left and top postion. */
	private float x, y;
	
	private float offsetWidth, offsetHeight;
	
	public StationMarkLayer(MapView mapView, StationStyle style) {
		super(mapView);
		switch(style) {
		case START:
			bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.start_marker);
			break;
		case END:
			bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.end_marker);
			break;
		default:
			bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.start_marker);
			break;
		}
		
		offsetWidth = bitmap.getWidth()/2;
		offsetHeight = bitmap.getHeight();
		
		paint = new Paint();
	}
	
	/**
	 * Set station marker position.
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		this.x = x - offsetWidth;
		this.y = y - offsetHeight;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y, paint);
	}

}
