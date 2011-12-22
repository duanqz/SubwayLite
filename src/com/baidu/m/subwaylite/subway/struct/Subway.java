package com.baidu.m.subwaylite.subway.struct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baidu.m.subwaylite.strategy.Path;
import com.baidu.m.subwaylite.strategy.ShortestPathStrategy;
import com.baidu.m.subwaylite.subway.SubwayShortestPath;
import com.baidu.m.subwaylite.subway.SubwayStrategy;

/**
 * Subway.
 * implements {@link Serializable}, {@link SubwayStrategy}
 * <p>
 * Subway is a structure contains {@link Station}s, {@link Hop} between two stations and {@link line}s.
 * <br/>
 * Station, hop or line can be added to or removed from subway.
 * </p>
 * 
 * @author duanqizhi
 *
 */
public class Subway implements Serializable, SubwayStrategy {

	private static final long serialVersionUID = 1L;

	/** Stations of the subway. */
	private List<Station> stations = new ArrayList<Station>();
	
	/** Hops of the subway. */
	private List<Hop> hops = new ArrayList<Hop>();
	
	/** Lines of the subway. */
	private List<Line> lines = new ArrayList<Line>();
	
	/** Shortest path strategy. It has not to be serialized. */
	private transient ShortestPathStrategy strategy;
	
	public Subway addStation(Station station) {
		stations.add(station);
		return this;
	}
	
	public Subway removeStation(Station station) {
		stations.remove(station);
		return this;
	}
	
	public Subway addHop(Hop hop) {
		hops.add(hop);
		return this;
	}
	
	public Subway removeHop(Hop hop) {
		hops.remove(hop);
		return this;
	}
	
	public Subway addLine(Line line) {
		lines.add(line);
		return this;
	}
	
	public Subway removeLine(Line line) {
		lines.remove(line);
		return this;
	}
	
	public List<Station> getStations() {
		return stations;
	}

	public List<Hop> getHops() {
		return hops;
	}

	public List<Line> getLines() {
		return lines;
	}
	
	@Override
	public Subway setShortestPathStrategy(ShortestPathStrategy strategy) {
		this.strategy = strategy;
		return this;
		
	}
	
	@Override
	public SubwayShortestPath getSubwayShortestPath(Station start, Station end) {
		final Path path = strategy.findShortestPath(start.getLocalId(), end.getLocalId());
		
		/** 
		 * Anonymous class implements {@link SubwayShortestpath} interface. 
		 */
		return new SubwayShortestPath() {

			@Override
			public List<Station> getStations() {
				List<Station> shortestStations = new ArrayList<Station>();
				List<Integer> vertices = path.getVertexes();
				for(int i = vertices.size()-1; i >= 0; i--) {
					shortestStations.add(stations.get(vertices.get(i)));
				}
				return shortestStations;
			}

			@Override
			public float getWeight() {
				return path.getWeight();
			}
			
		};
	}

}
