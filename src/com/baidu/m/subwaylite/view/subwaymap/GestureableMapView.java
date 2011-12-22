package com.baidu.m.subwaylite.view.subwaymap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import com.baidu.m.subwaylite.converter.StructConverter;
import com.baidu.m.subwaylite.converter.impl.SubwayStructConverter;
import com.baidu.m.subwaylite.strategy.graph.Graph;
import com.baidu.m.subwaylite.strategy.impl.DijkstraStrategy;
import com.baidu.m.subwaylite.subway.SubwayShortestPath;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;
import com.baidu.m.subwaylite.view.listener.Gestureable;
import com.baidu.m.subwaylite.view.maplayer.MapLayer;
import com.baidu.m.subwaylite.view.maplayer.line.ShortestPathLayer;
import com.baidu.m.subwaylite.view.maplayer.station.StationMarkLayer;
import com.baidu.m.subwaylite.view.maplayer.station.StationMarkLayer.StationStyle;

/**
 * Gestureable map view.
 * 
 * <p>
 * Map view that can handle gestures.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class GestureableMapView extends MapView implements Gestureable {

	private static final String TAG = GestureableMapView.class.getSimpleName();
	
	/** Tolerance of click. */
	private static final float TOLERANCE = 10;
	
	/** Grid used for click checking. */
	private List<Integer>[][] grid;
	private float gridSideX, gridSideY;
	
	/** Flag identify station selected. */
	private boolean startSelected;
	
	/** Start station index. */
	private int startIdx;
	
	/** MapLayers. */
	private Set<MapLayer> mapLayers;
	private Set<MapLayer> oldLayers;
	
	/**
	 * Constructor.
	 * @param context
	 */
	public GestureableMapView(Context context) {
		super(context);
	}
	
	/**
	 * Constructor.
	 * @param context
	 * @param attrs
	 */
	public GestureableMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Initiate gestureable map.
	 */
	public void init() {
		super.init();
		
		Log.d(TAG, "Initiate GestureableMapView. " +
				"In order GraphicsToll, ClipSize, Grid and SubwayStrategy");
		initClipSize().initGrid().initSubwayStrategy();
			
		mapLayers = new HashSet<MapLayer>();
		oldLayers = new HashSet<MapLayer>();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Draw layers if gesture is valid.
		Iterator<MapLayer> iter = mapLayers.iterator();
		while(iter.hasNext()) {
			iter.next().draw(canvas);
		}
		
	}
	
	@Override
	public void onScroll(int dx, int dy) {
		// Fix the clip range
//		clipX = (clipX+dx > maxClipX) ? maxClipX : clipX+dx;
//		clipX = (clipX < 0) ? 0 : clipX;
//		clipY = (clipY+dy > maxClipY) ? maxClipY : clipY+dy;
//		clipY = (clipY < 0) ? 0: clipY;
		
//		displayMatrix.postTranslate(dx, dy);
		
		Log.d(TAG, "On scroll");
		invalidate();
	}

	@Override
	public void onZoom(float sx, float sy) {
		displayMatrix.postScale(sx, sy);
		invalidate();
	}
	
	@Override
	public void onSingleClick(float x, float y) {
		int stationIdx = getClickStation(x, y);
		if(stationIdx != -1) {
			onStationSelected(stationIdx, startSelected);
		} else {
			clearStationSelected();
		}
		
		invalidate();
	}
	
	@Override
	public void onDbClick(float x, float y) {
		int stationIdx = getClickStation(x, y);
		if(stationIdx >= 0) {
			
			StringBuilder sb = new StringBuilder();
			Station station = subway.getStations().get(stationIdx);
			sb.append(station.getName())
			.append("\n").append(station.getDetail());
			Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Calculate the dimensions of bitmap and window.
	 * 
	 * @param width
	 * @param height
	 */
	private GestureableMapView initClipSize() {
		// Bitmap size
//		bitmapWidth = bitmap.getWidth();
//		bitmapHeight = bitmap.getHeight();
		
//		maxClipX = Math.max(bitmapWidth-maxWidth, 0);
//		maxClipY = Math.max(bitmapHeight-maxHeight, 0);
		
		// Layout size
//		setLayoutParams(new LayoutParams(bitmapWidth, bitmapHeight));
		
		return this;
	}
		
	/**
	 * Initiate grid for getting clicked station.
	 * <br/>
	 * The grid size is 5*5;
	 */
	@SuppressWarnings("unchecked")
	private GestureableMapView initGrid() {
		gridSideX = maxWidth/4;
		gridSideY = maxHeight/4;
		
		// Create an 5*5 grid
		grid = new List[5][5];
		int i = 0, j = 0;
		for(; i < 5; i++){
			for(j = 0; j < 5; j++) {
				grid[i][j] = new ArrayList<Integer>();
			}
		}
		
		// Put station index to the appropriate grid.
		for(int k = 0; k < getNumOfStations(); k++) {
			i =  (int) (stationPosX[k] / gridSideX);
			j = (int) (stationPosY[k] / gridSideY);
			grid[i][j].add(k);
		}
		
		return this;
	}
	
	/**
	 * Initiate subway strategy.
	 */
	private GestureableMapView initSubwayStrategy() {
		// Converting Subway structure to Graph structure
		StructConverter<Subway, Graph> converter = new SubwayStructConverter<Subway, Graph>();
		Graph graph = new Graph();
		converter.convert(subway, graph);
		
		// Set shortest path finding strategy
		subway.setShortestPathStrategy(new DijkstraStrategy(graph));
		
		return this;
	}
	
	/**
	 * Get click station by position (x, y).
	 * 
	 * @param x
	 * @param y
	 * @return station index
	 */
	private int getClickStation(float x, float y) {
		// Map (x, y) with display matrix.
		float[] mapPoint = new float[]{x, y};
//		displayMatrix.
		int i = (int) (x/gridSideX), j = (int) (y/gridSideY);
		int stationIdx;
		for(int k = 0; k < grid[i][j].size(); k++) {
			stationIdx = grid[i][j].get(k);
			if( Math.abs(x - mapPoint[0]) < TOLERANCE &&
				Math.abs(y - mapPoint[1]) < TOLERANCE) {
				return stationIdx;
			}
		}
		return -1;
	}
	
	/**
	 * Actions on station selected.
	 * @param stationIdx
	 * @param isEndStation
	 */
	private void onStationSelected(int stationIdx, boolean isEndStation) {
		StationMarkLayer stationLayer;
		if(isEndStation) {
			// End station selected
			stationLayer = new StationMarkLayer(this, StationStyle.END);
			// Get shortest path.
			SubwayShortestPath shortestPath = subway.getSubwayShortestPath(
					subway.getStations().get(startIdx), 
					subway.getStations().get(stationIdx));
			MapLayer pathLayer = new ShortestPathLayer(this, shortestPath);
			
			// Remove old layers and add new layers
			mapLayers.removeAll(oldLayers);
			mapLayers.add(pathLayer);
			mapLayers.add(stationLayer);
			
			// Add to the old layers
			oldLayers.add(pathLayer);
			oldLayers.add(stationLayer);
		} else {
			// Start station selected
			stationLayer = new StationMarkLayer(this, StationStyle.START);
			startSelected = true;
			startIdx = stationIdx;
		}
		
		stationLayer.setPosition(getStationPosX(stationIdx), getStationPosY(stationIdx));
		mapLayers.add(stationLayer);
	}
	
	/**
	 * Clear station selected.
	 */
	private void clearStationSelected() {
		startSelected = false;
		mapLayers.clear();
		oldLayers.clear();
		
		SubwayMapActivity.setRouteInfo(null);
	}

}
