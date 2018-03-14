package com.champion.crawler.entity.gis;

public class Point {

	public String id;
	public String lat;
	public String lng;
	public String address;
	public String precise;
	public String confidence;
	public String level;
	public String latLngType;

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPrecise() {
		return precise;
	}

	public void setPrecise(String precise) {
		this.precise = precise;
	}

	public String getLatLngType() {
		return latLngType;
	}

	public void setLatLngType(String latLngType) {
		this.latLngType = latLngType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public  Point (String id) {
		this.id = id;
	}

	public  Point() {
		
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getId() {
		return id;
	}

}
