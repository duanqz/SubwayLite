package com.baidu.m.subwaylite.view.subwaymap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.baidu.m.subwaylite.R;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;
import com.baidu.m.subwaylite.util.Util;
import com.baidu.m.subwaylite.view.maplayer.MapLayer;
import com.baidu.m.subwaylite.view.maplayer.line.LineLayer;
import com.baidu.m.subwaylite.view.maplayer.station.StationLayer;

/**
 * Subway Map View.
 * 
 * <p>
 * Display subway stations and lines.
 * </p>
 * @author duanqizhi
 *
 */
public abstract class MapView extends ImageView {

	private static final String TAG = MapView.class.getSimpleName();
	
	/** Maximum size of the map view. */
	protected int maxWidth, maxHeight;
	
	protected Matrix displayMatrix;
	
	protected Subway subway;
	
	// Fields used for display controlling
	/** Station positions. */
	protected float  stationPosX[], stationPosY[];
	
	/** Latitude and Longitude. */
	private float  latitudeMinNMax[],  longitudeMinNMax[], 
	  			   latitudeRange,      longitudeRange;
	
	/** Station and Line layers. */
	private Set<MapLayer> subwayMapLayers;
	
	/**
	 * Constructor. Used to construct a subway map view  
	 * @param context
	 */
	protected MapView(Context context) {
		super(context);
	}
	
	/**
	 * Constructor. Used to construct a subway map view
	 * @param context
	 * @param attrs
	 */
	protected MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * Initiate subway map.
	 */
	public void init() {
		Log.d(TAG, "Initiate MapView.");
		// Background
		setBackgroundResource(R.drawable.background);
		// Map dimension
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		maxWidth = metrics.widthPixels;
		maxHeight = metrics.heightPixels;
		
		// display matrix
		displayMatrix = getImageMatrix();
		// map layers
		subwayMapLayers = new HashSet<MapLayer>();
		// initiate station position and layers
		initStationPosOnView().initLayers();
	}
	
	/**
	 * Set subway.
	 * @param subway
	 */
	public void setSubway(Subway subway) {
		this.subway = subway;
	}
	
	/**
	 * Get station X position.
	 * @param stationIdx
	 * @return
	 */
	public float getStationPosX(int stationIdx) {
		return stationPosX[stationIdx];
	}
	
	/**
	 * Get station Y position.
	 * @param stationIdx
	 * @return
	 */
	public float getStationPosY(int stationIdx) {
		return stationPosY[stationIdx];
	}
	
	/**
	 * Get Number of stations.
	 * @return
	 */
	public int getNumOfStations() {
		return subway.getStations().size();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "Draw subway map layers.");
		canvas.setMatrix(displayMatrix);
		Iterator<MapLayer> iter = subwayMapLayers.iterator();
		while(iter.hasNext()) {
			iter.next().draw(canvas);
		}
	}
	
	/**
	 * Calculate station positions on view.
	 */
	protected MapView initStationPosOnView()	{
		List<Station> stations = subway.getStations();
		
		// Get all the latitudes and longitudes
		int numOfStations = stations.size();
		float[] latitudes = new float[numOfStations];
		float[] longitudes = new float[numOfStations];
		Station station;
		for(int i = 0; i < numOfStations; i++) {
			station = stations.get(i);
			latitudes[i] = station.getLatitude();
			longitudes[i] = station.getLongitude();
		}
		
		// Get the range of latitude and longitude
		latitudeMinNMax = Util.getMinNMax(latitudes);
		longitudeMinNMax = Util.getMinNMax(longitudes);
		latitudeRange = latitudeMinNMax[1] - latitudeMinNMax[0];
		longitudeRange = longitudeMinNMax[1] - longitudeMinNMax[0];
		
		// Calculate station positions
		stationPosX = new float[numOfStations];
		stationPosY = new float[numOfStations];
		for(int i = 0; i < numOfStations; i++) {
			stationPosX[i] = getXOnView(longitudes[i]);
			stationPosY[i] = getYOnView(latitudes[i]);
		}
		
		return this;
	}

	/**
	 * Initiate station and line layers.
	 * @return
	 */
	private MapView initLayers() {
		MapLayer lineLayer = new LineLayer(this, subway.getHops());
		MapLayer stationLayer = new StationLayer(this, subway.getStations());
		subwayMapLayers.add(lineLayer);
		subwayMapLayers.add(stationLayer);
		
		return this;
	}
	
	/**
	 * Get X position on the view by longitude.
	 * 
	 * @param longitude
	 * @return
	 */
	private float getXOnView(float longitude) {
		return maxWidth*(longitude-longitudeMinNMax[0])/longitudeRange;
	}
	
	/**
	 * Get Y position on the view by latitude.
	 * @param latitude
	 * @return
	 */
	private float getYOnView(float latitude) {
		return maxHeight*(latitudeMinNMax[1]-latitude)/latitudeRange + 25;
	}

}
