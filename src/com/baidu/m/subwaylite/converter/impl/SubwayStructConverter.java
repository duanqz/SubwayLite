package com.baidu.m.subwaylite.converter.impl;

import java.util.ArrayList;
import java.util.List;

import com.baidu.m.subwaylite.converter.StructConverter;
import com.baidu.m.subwaylite.strategy.graph.Edge;
import com.baidu.m.subwaylite.strategy.graph.Graph;
import com.baidu.m.subwaylite.subway.struct.Hop;
import com.baidu.m.subwaylite.subway.struct.Station;
import com.baidu.m.subwaylite.subway.struct.Subway;

public class SubwayStructConverter<SRC, DEST> implements StructConverter<SRC, DEST> {

	@Override
	public boolean convert(SRC srcStruct, DEST destStruct) {
		if(srcStruct instanceof Subway && destStruct instanceof Graph) {
			Subway subway = (Subway) srcStruct;
			Graph graph = (Graph) destStruct;
			
			// Stations to vertices
			List<Station> stations = subway.getStations();
			int size = stations.size();
			int[] vertices = new int[size];
			List<List<Edge>> edges = new ArrayList<List<Edge>>(size);
			int i;
			Station station;
			for (i = 0; i < size; i++) {
				station = stations.get(i);
				vertices[i] = station.getId();
				station.setLocalId(i);
				edges.add(new ArrayList<Edge>());
			}
			graph.setVertexes(vertices);

			// Hops to edges
			List<Hop> hops = subway.getHops();
			Hop hop;
			Edge edge;
			for (i = 0; i < hops.size(); i++) {
				hop = hops.get(i);
				// Create a new Edge
				edge = new Edge();
				edge.setVertex1(hop.getStation1().getLocalId());
				edge.setVertex2(hop.getStation2().getLocalId());
				edge.setWeight(hop.getTimeCost());
				// Construct an adjacent matrix
				edges.get(edge.getVertex1()).add(edge);
				edges.get(edge.getVertex2()).add(edge);
			}

			graph.setEdges(edges);
			return true;
		}
		return false;
	}
}
