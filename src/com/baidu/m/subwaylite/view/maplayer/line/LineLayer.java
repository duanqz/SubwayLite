package com.baidu.m.subwaylite.view.maplayer.line;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.baidu.m.subwaylite.subway.struct.Hop;
import com.baidu.m.subwaylite.view.maplayer.AbstractMapLayer;
import com.baidu.m.subwaylite.view.subwaymap.MapView;

/**
 * Line Layer.
 * 
 * @author duanqizhi
 *
 */
public class LineLayer extends AbstractMapLayer {

	private List<Hop> hops;
	
	/**
	 * Constructor.
	 * @param mapView
	 * @param hops
	 */
	public LineLayer(MapView mapView, List<Hop> hops) {
		super(mapView);
		
		this.hops = hops;
		
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	public void draw(Canvas canvas) {
		Hop hop;
		int start, end;
		for (int i = 0; i < hops.size(); i++) {
			hop = hops.get(i);
			start = hop.getStation1().getLocalId();
			end = hop.getStation2().getLocalId();
			canvas.drawLine(
					mapView.getStationPosX(start), mapView.getStationPosY(start), 
					mapView.getStationPosX(end), mapView.getStationPosY(end), 
					paint);
		}
	}

}
