package com.predic8.membrane.core.interceptor.balancer;

import java.util.*;

import org.apache.commons.logging.*;

public class Cluster {

	private static Log log = LogFactory.getLog(Cluster.class.getName());
	
	private String name;
	private List<Node> nodes = new LinkedList<Node>();

	public Cluster(String name) {
		this.name = name;
	}

	public void nodeUp(Node ep) {		
		log.info("endpoint: " + ep +" up");
		getNode(ep).setLastUpTime(System.currentTimeMillis());
		getNode(ep).setUp(true);
	}

	public void nodeDown(Node ep) {
		log.info("endpoint: " + ep +" down");
		getNode(ep).setUp(false);		
	}
	
	public boolean removeNode(Node node) {		
		return nodes.remove(node);
	}

	public List<Node> getAvailableNodes(long timeout) {
		List<Node> l = new LinkedList<Node>();
		for (Node n : getAllNodes(timeout)) {
			if ( n.isUp() ) l.add(n);
		}
		return l;
	}
	
	public List<Node> getAllNodes(long timeout) {	
		if (timeout > 0) {
			for (Node n : nodes) {
				if ( System.currentTimeMillis()-n.getLastUpTime() > timeout ) n.setUp(false);
			}
		}
		return nodes;
	}

	private Node getNode(Node ep) {
		if ( !nodes.contains(ep) ) {
			log.info("creating endpoint: "+ep);
			nodes.add(new Node(ep.getHost(), ep.getPort()));
		}
		return nodes.get(nodes.indexOf(ep));		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}