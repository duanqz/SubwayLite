package com.baidu.m.subwaylite.subway.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.baidu.m.subwaylite.subway.adapter.SubwayAdapter;
import com.baidu.m.subwaylite.subway.struct.Hop;
import com.baidu.m.subwaylite.subway.struct.Line;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;

/**
 * SubwayDbAdapter.
 * <p>
 * Load the persistence data from SQLite database.
 * <br/>
 * Implements {@link SubwayAdapter}
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class SubwayDbAdapter implements SubwayAdapter {

	private static final String TAG = "SubwayDbAdapter";
	final String mapLineStaionSql = "SELECT line._id FROM map_line_station as t, line WHERE t.station_id=? AND t.line_id=line._id";
	
	private static final String DB_NAME = "cn_bj.db.sqlite";
	private static final int DB_VERSION = 1;
	
	/** Context */
	protected Context ctx;
	
	/** Database helper */
	protected DatabaseHelper dbHelper;
	
	/** database */
	protected SQLiteDatabase db;
	
	protected Subway subway;
	
	// Used to cache line and station
	private Map<Integer, Station> mapStation = new HashMap<Integer, Station>();
	private Map<Integer, Line> mapLine = new HashMap<Integer, Line>();
	
	/**
	 * Constructor.
	 * <p>
	 * Context should be injected.
	 * </p>
	 * @param ctx
	 */
	public SubwayDbAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Database Helper. 
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "Database created.");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "Database upgraded.");
		}
		
	}

	@Override
	public SubwayDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(ctx);
		
		try {
			db = dbHelper.getWritableDatabase();
		} catch(IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		}
		
		Log.d(TAG, "Writable database Opened.");
		return this;
	}

	@Override
	public SubwayAdapter close() {
		try {
			dbHelper.close();
		} catch(IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		}
		
		Log.d(TAG, "Database closed.");
		return this;
	}

	@Override
	public SubwayAdapter setSubway(Subway subway) {
		this.subway = subway;
		
		Log.d(TAG, "Subway setted.");
		return this;
	}
	
	@Override
	public SubwayAdapter fillData() {
		Assert.assertNotNull(subway);
		
		// Fill Line, Station and Hop orderly.
		fillLine();
		fillStaiton();
		fillHop();
		
		Log.d(TAG, "Subway data filled.");
		return this;
	}
	
	/**
	 * Fill Line.
	 */
	private void fillLine() {
		Cursor cur = db.query(Line.TABLE_NAME,
				new String[]{
				Line.Column.ID.getColName(), 
				Line.Column.NAME.getColName(),
				Line.Column.COLOR.getColName()}, 
				null, null, null, null, null);
		((Activity) ctx).startManagingCursor(cur);
		
		Line line;
		Integer id;
		String name, color;
		for(cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			line = new Line();
			// id
			id = cur.getInt(Line.Column.ID.getColIndex());
			line.setId(id);
			// name
			name = cur.getString(Line.Column.NAME.getColIndex());
			line.setName(name);
			// color
			color = cur.getString(Line.Column.COLOR.getColIndex());
			line.setColor(color);
			
			subway.addLine(line);
			
			// Map line used for initiating station.
			mapLine.put(id, line);
		}
		Log.d(TAG, "Finishi filling lines data.");
	}

	/**
	 * Fill station from database.
	 */
	private void fillStaiton() {
		// Station cursor to cache result select from station table.
		Cursor stationCur = db.query(Station.TABLE_NAME,
				new String[]{
				Station.Column.ID.getColName(),
				Station.Column.NAME.getColName(),
				Station.Column.DETAIL.getColName(),
				Station.Column.ISTRANSFER.getColName(),
				Station.Column.LATITUDE.getColName(),
				Station.Column.LONGITUDE.getColName()},
				null, null, null, null, null);
		((Activity) ctx).startManagingCursor(stationCur);
		
		Station station;
		Integer id, lineId;
		Short isTransfer;
		List<Line> lines = new ArrayList<Line>();
		Cursor mapCur;
		for(stationCur.moveToFirst(); !stationCur.isAfterLast(); stationCur.moveToNext()) {
			station = new Station();
			// Id
			id = stationCur.getInt(Station.Column.ID.getColIndex());
			station.setId(id);
			// Name
			station.setName(
					stationCur.getString(Station.Column.NAME.getColIndex()));
			// Detail
			station.setDetail(
					stationCur.getString(Station.Column.DETAIL.getColIndex()));
			// Is Transfer
			isTransfer = stationCur.getShort(Station.Column.ISTRANSFER.getColIndex());
			station.setTransfer(isTransfer != 0);
			// Lines. Have to query the database.
			mapCur = queryLines(id);
			for(mapCur.moveToFirst(); !mapCur.isAfterLast(); mapCur.moveToNext()) {
				lineId = mapCur.getInt(Line.Column.ID.getColIndex());
				lines.add(mapLine.get(lineId));
			}
			station.setLines(lines);
			// Latitude
			station.setLatitude(
					stationCur.getFloat(Station.Column.LATITUDE.getColIndex()));
			// Longitude
			station.setLongitude(
					stationCur.getFloat(Station.Column.LONGITUDE.getColIndex()));
			
			subway.addStation(station);
			// Map station used for initiating hop.
			mapStation.put(id, station);
		}

		Log.d(TAG, "Finish filling stations data.");
	}
	
	/**
	 * Fill Hop.
	 */
	private void fillHop() {
		Cursor cur = db.query(Hop.Table_Name, 
			new String[]{
				Hop.Column.ID.getColName(),
				Hop.Column.STATION1.getColName(),
				Hop.Column.STATION2.getColName(),
				Hop.Column.TIME_COST.getColName(),
				Hop.Column.DISTANCE.getColName()
			}, 
			null, null, null, null, null);
		((Activity) ctx).startManagingCursor(cur);
		
		Integer id, station1Id, station2Id;
		Float timeCost, distance;
		Hop hop;
		for(cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			hop = new Hop();
			// id
			id = cur.getInt(Hop.Column.ID.getColIndex());
			hop.setId(id);
			// station 1
			station1Id = cur.getInt(Hop.Column.STATION1.getColIndex());
			hop.setStation1(mapStation.get(station1Id));
			// station 2
			station2Id = cur.getInt(Hop.Column.STATION2.getColIndex());
			hop.setStation2(mapStation.get(station2Id));
			// time cost
			timeCost = cur.getFloat(Hop.Column.TIME_COST.getColIndex());
			hop.setTimeCost(timeCost);
			// distance
			distance = cur.getFloat(Hop.Column.DISTANCE.getColIndex());
			hop.setDistance(distance);
			
			subway.addHop(hop);
		}
		
		Log.d(TAG, "Finish filling hops data.");
	}
	
	/**
	 * Query lines on which the station is.
	 * 
	 * @param stationId
	 * @return
	 */
	private Cursor queryLines(Integer stationId) {
		String resSql = mapLineStaionSql.replace("?", String.valueOf(stationId));
		return db.rawQuery(resSql, new String[]{});
	}
	
}
