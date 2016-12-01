package com.allowify.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zippi.places.GooglePlaces.PlaceGeometry;

@Document
public class GPlace {
	

	@Id
	private String id;
	
	private String name;

	private String icon;

	private String placeId;

	private String vicinity;
	
	private String lat;

	private String lng;
	
	private String[] types;

	public GPlace(String name, String icon, String placeId, String vicinity,
			String lat, String lng, String[] types) {
		this.name = name;
		this.icon = icon;
		this.placeId = placeId;
		this.vicinity = vicinity;
		this.lat = lat;
		this.lng = lng;
		this.types = types;
	}

	public String getVicinity() {
		return vicinity;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}


	public String getPlaceId() {
		return placeId;
	}

	public void setVicinity(String address) {
		this.vicinity = address;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}



	public void setPlaceId(String place_id) {
		this.placeId = place_id;
	}

	/**
	 * @return the types
	 */
	public String[] getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
}