package com.baidu.m.subwaylite.view.maplayer.station;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.baidu.m.subwaylite.R;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.view.maplayer.AbstractMapLayer;
import com.baidu.m.subwaylite.view.subwaymap.MapView;

/**
 * Station Layer.
 * 
 * @author duanqizhi
 */
public class StationLayer extends AbstractMapLayer {

	private Bitmap stationBitmap, transferStationBitmap;
	private float offsetW, offsetH;
	private List<Station> stations;
	
	/**
	 * Constructor.
	 * @param mapView
	 * @param stations
	 */
	public StationLayer(MapView mapView, List<Station> stations) {
		super(mapView);
		this.stations = stations;
		stationBitmap = getBitmap(R.drawable.station);
		transferStationBitmap = getBitmap(R.drawable.station_transfer);
		
		offsetW = stationBitmap.getWidth()/2;
		offsetH = stationBitmap.getHeight()/2;
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap drawingBitmap;
		
		for(int i = 0; i < stations.size(); i++) {
			drawingBitmap = stations.get(i).isTransfer() ? transferStationBitmap : stationBitmap;
			canvas.drawBitmap(drawingBitmap, 
					mapView.getStationPosX(i)-offsetW, 
					mapView.getStationPosY(i)-offsetH, 
					paint);
//			canvas.drawBitmap()
//			canvas.drawBitmap(drawingBitmap, 
//				stationPosX[i]-offsetW, stationPosY[i]-offsetH, paint);
		}
	}
	
	/**
	 * Get bitmap from resource.
	 * @param resId
	 * @return
	 */
	private Bitmap getBitmap(int resId) {
		return BitmapFactory.decodeResource(mapView.getResources(), resId);
	}

}
