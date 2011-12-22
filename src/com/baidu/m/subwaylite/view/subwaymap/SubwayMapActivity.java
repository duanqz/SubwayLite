package com.baidu.m.subwaylite.view.subwaymap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.baidu.m.subwaylite.R;
import com.baidu.m.subwaylite.SubwayLiteActivity;
import com.baidu.m.subwaylite.subway.struct.Subway;
import com.baidu.m.subwaylite.view.listener.GestureViewListener;

/**
 * SubwayMap Activity.
 * 
 * <p>
 * Display a Subway map, and detect user actions.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class SubwayMapActivity extends Activity {

	private static final String TAG = SubwayMapActivity.class.getSimpleName();
	
	private static final int MENU_ZOOMIN = Menu.FIRST;
	private static final int MENU_ZOOMOUT = Menu.FIRST + 1;
	
	private GestureableMapView mapView;
	
	private GestureDetector detector;
	
	private static TextView routeInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.subwaymap);
		
		Log.d(TAG, "Initiate Subway Map View.");
		initViews();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0, MENU_ZOOMIN, 0, R.string.labelZoomIn);
    	
    	menu.add(0, MENU_ZOOMOUT, 1, R.string.labelZoomOut);
    	
    	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case MENU_ZOOMIN:
			mapView.onZoom(0.5f, 0.5f);
			break;
		case MENU_ZOOMOUT:
			mapView.onZoom(2.0f, 2.0f);
			break;
		default:
			break;
		}
		return true;
	}
	
	/**
	 * Initiate the layout.
	 */
	private SubwayMapActivity initViews() {
		// Map view
		mapView = (GestureableMapView) findViewById(R.id.gestureableMapView);
		detector = new GestureDetector(this, new GestureViewListener(mapView));
		
		Subway subway = (Subway) getIntent().getSerializableExtra(SubwayLiteActivity.KEY_SUBWAY);
		mapView.setSubway(subway);
		mapView.init();
		mapView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});

		// Route info
		routeInfo = (TextView) findViewById(R.id.routeInfo);		
		routeInfo.setVisibility(View.GONE);
		
		return this;
	}
	
	/**
	 * Set route information.
	 * @param msg
	 */
	public static synchronized void setRouteInfo(String msg) {
		if(null == msg) {
			routeInfo.setVisibility(View.INVISIBLE);
		} else {
			routeInfo.setText(msg);
			routeInfo.setVisibility(View.VISIBLE);
		}
	}
	
}
