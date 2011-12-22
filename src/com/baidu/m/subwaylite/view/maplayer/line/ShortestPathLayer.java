package com.baidu.m.subwaylite.view.maplayer.line;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;

import com.baidu.m.subwaylite.subway.SubwayShortestPath;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.view.maplayer.AbstractMapLayer;
import com.baidu.m.subwaylite.view.subwaymap.MapView;
import com.baidu.m.subwaylite.view.subwaymap.SubwayMapActivity;

/**
 * Shortest path layer.
 * 
 * @author duanqizhi
 *
 */
public class ShortestPathLayer extends AbstractMapLayer {

	private SubwayShortestPath shortestPath;
	
	/**
	 * Constructor.
	 * <br/>
	 * 
	 * @param mapView
	 * @param shortestPath
	 */
	public ShortestPathLayer(MapView mapView, SubwayShortestPath shortestPath) {
		super(mapView);
		
		this.shortestPath = shortestPath;
		this.paint.setStrokeWidth(3.0f);
		this.paint.setColor(Color.YELLOW);
	}
	
	@Override
	public void draw(Canvas canvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("Recommended Route : [ ");
		List<Station> stations = shortestPath.getStations();
		int i, j, startId, endId;
		
		Station station = stations.get(0);
		sb.append(station.getName());
		
		for(i = 0, j = 1; j < stations.size(); i++, j++) {
			// Information to display
			station = stations.get(j);
			sb.append(" -> ").append(station.getName());
			
			// Shortest Path to display
			startId = stations.get(i).getLocalId();
			endId = station.getLocalId();
			canvas.drawLine(
					mapView.getStationPosX(startId), mapView.getStationPosY(startId), 
					mapView.getStationPosX(endId), mapView.getStationPosY(endId), 
					paint);
		}
		
		sb.append("]\nCost is ").append(shortestPath.getWeight());
		
		SubwayMapActivity.setRouteInfo(sb.toString());
	}

}
