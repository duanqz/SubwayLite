package com.baidu.m.subwaylite;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.m.subwaylite.converter.StructConverter;
import com.baidu.m.subwaylite.converter.impl.SubwayStructConverter;
import com.baidu.m.subwaylite.strategy.graph.Graph;
import com.baidu.m.subwaylite.strategy.impl.DijkstraStrategy;
import com.baidu.m.subwaylite.subway.SubwayShortestPath;
import com.baidu.m.subwaylite.subway.adapter.SubwayAdapter;
import com.baidu.m.subwaylite.subway.adapter.impl.SubwayDbAdapter;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;
import com.baidu.m.subwaylite.view.subwaymap.SubwayMapActivity;

/**
 * The main activity.
 * 
 * @author duanqizhi
 *
 */
public class SubwayLiteActivity extends Activity {
	
	private static final String TAG = "SubwayLiteActivity";
	
	public static String KEY_SUBWAY = "subway";
	
	// Views
	private Spinner stationStart, stationEnd;
	private Button btnSearch, btnDetail1, btnDetail2;
	private TextView txtResult;
	
	// Data
	private Subway subway;
	
	protected static final int MENU_MAP = Menu.FIRST;
	protected static final int MENU_ABOUT = Menu.FIRST+1;
	protected static final int MENU_EXIT = Menu.FIRST+2;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d(TAG, "Initiate SubwayLite. " +
        		"In order SubwayGraph, Views, Listeners."); 
        initSubwayGraph().initViews().initListeners();
        
        // TODO Only for debug
//        onMenuItemMap();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0, MENU_MAP, 0, R.string.labelMap)
    	.setIcon(R.drawable.ic_menu_mapmode);
    	
    	menu.add(0, MENU_ABOUT, 1, R.string.labelAbout)
    	.setIcon(R.drawable.ic_menu_help);
    	
    	menu.add(0, MENU_EXIT, 2, R.string.labelExit)
    	.setIcon(R.drawable.ic_menu_exit);
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	Log.d(TAG, "Item selected");
    	switch(item.getItemId()) {
    	case MENU_MAP:
    		onMenuItemMap();
    		break;
    	case MENU_ABOUT:
    		onMenuItemAbout();
    		break;
    	case MENU_EXIT:
    		finish();
    		break;
    	default:
    		break;
    	}
    	return true;
    
    }
 
    /**
     * Initiate subway graph.
     */
    private SubwayLiteActivity initSubwayGraph() {
    	SubwayAdapter subwayAdapter = new SubwayDbAdapter(this);
    	subway = new Subway();
    	
    	// Fill subway data structure
    	subwayAdapter.open().setSubway(subway).fillData().close();
    	
    	// Converting Subway structure to Graph structure
    	StructConverter<Subway, Graph> converter = new SubwayStructConverter<Subway, Graph>();
    	Graph graph = new Graph();
    	converter.convert(subway, graph);
    	
    	// Set shortest path finding strategy
    	subway.setShortestPathStrategy(new DijkstraStrategy(graph));
    	
    	return this;
    }
    
    /**
     * Initiate views.
     */
    private SubwayLiteActivity initViews() {
    	// Spinner
    	stationStart = (Spinner) findViewById(R.id.spinStart);
    	stationEnd = (Spinner) findViewById(R.id.spinEnd);
    	// Button
    	btnSearch = (Button) findViewById(R.id.btnSearch);
    	btnDetail1 = (Button) findViewById(R.id.btnDetail1);
    	btnDetail2 = (Button) findViewById(R.id.btnDetail2);
    	// TextView
    	txtResult = (TextView) findViewById(R.id.txtResult);
    	
    	Log.d(TAG, "Initiate data of the views."); 
    	initViewsData();
    	
    	return this;
    }
    
    /**
     * Initiate data of the views.
     */
    private SubwayLiteActivity initViewsData() {
    	ArrayAdapter<Station> adapterStationStart = new ArrayAdapter<Station>(this,
    			android.R.layout.simple_spinner_item,
    			subway.getStations());
    	stationStart.setAdapter(adapterStationStart);
    	
    	ArrayAdapter<Station> adapterStationEnd = new ArrayAdapter<Station>(this,
    			android.R.layout.simple_spinner_item,
    			subway.getStations());
    	stationEnd.setAdapter(adapterStationEnd);
    	
    	return this;
    }
    
    /**
     * Initiate listeners.
     */
    private SubwayLiteActivity initListeners() {
    	OnClickListener lis = new ButtonClickListener();
    	btnSearch.setOnClickListener(lis);
    	btnDetail1.setOnClickListener(lis);
    	btnDetail2.setOnClickListener(lis);
    	
    	return this;
    }
    
    /**
     * MenuItem: Map
     */
    private void onMenuItemMap() {
    	Intent intent = new Intent(SubwayLiteActivity.this, SubwayMapActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putSerializable(KEY_SUBWAY, subway);
    	intent.putExtras(bundle);
    	startActivity(intent);
    }
    
    /**
     * MenuItem: About
     */
    private void onMenuItemAbout() {
    	new AlertDialog.Builder(this).setTitle(R.string.labelAbout)
    	.setMessage(R.string.msgAbout)
    	.setIcon(R.drawable.ic_menu_help)
		.setPositiveButton(R.string.labelOK,
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d(TAG, "Dialog OK clicked");
				}
			})
		.show();
    }
    
    /** 
     * ButtonClickListener.
     */
    private class ButtonClickListener implements  Button.OnClickListener {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnSearch:
				displayShortestRoute(
						(Station) stationStart.getSelectedItem(), 
						(Station) stationEnd.getSelectedItem());
				break;
			case R.id.btnDetail1:
				displayStationDetail((Station) stationStart.getSelectedItem());
				break;
			case R.id.btnDetail2:
				displayStationDetail((Station) stationEnd.getSelectedItem());
				break;
			default:
				break;
			}
			
		}
		
		/**
		 * Display station detail
		 * @param station
		 */
		private void displayStationDetail(Station station) {
			StringBuilder sb = new StringBuilder();
			sb.append(station.getName()).append("\n")
			.append(station.getDetail()).append("\n")
			.append(station.isTransfer() ? "It is a transfer station" : "");
			txtResult.setText(sb.toString());
		}
		
		/**
		 * Display shortest route.
		 * @param start
		 * @param end
		 */
		private void displayShortestRoute(Station start, Station end) {
			StringBuilder sb = new StringBuilder();
			sb.append("The Path is From ").append(start.getName());
			SubwayShortestPath shortestPath = subway.getSubwayShortestPath(start, end);
			List<Station> stations = shortestPath.getStations(); 
			Station station;
			for(int i = 1; i < stations.size(); i++) {
				station = stations.get(i);
				sb.append(" -> ").append(station.getName());
			}
			
			sb.append("\nCost is ").append(shortestPath.getWeight());
			txtResult.setText(sb.toString());
		}
    	
    }
}