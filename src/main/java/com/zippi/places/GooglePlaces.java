package com.zippi.places;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Min;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/**
 * @author jramoyo
 */
public class GooglePlaces {

	private static final Logger logger = LoggerFactory.getLogger(GooglePlaces.class);

	private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?reference={searchId}&sensor=false&key={key}";

	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	private Cache<String, PlaceDetails> placeDetails = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(24, TimeUnit.HOURS)
			.build(new CacheLoader<String, PlaceDetails>() {
				public PlaceDetails load(String searchId) throws Exception {
					PlaceDetailsResponse response = restTemplate.getForObject(PLACE_DETAILS_URL, PlaceDetailsResponse.class, searchId, apiKey);
					if (response.getResult() != null) {
						return response.getResult();
					} else {
						throw new PlacesException("Unable to find details for reference: " + searchId);
					}
				}
			});

	/**
	 * Fetches the details of a place identified by a reference (searchId)
	 * 
	 * @param searchId
	 *            the place reference identifier
	 * @return the details of a place identified by a reference (searchId)
	 */
	public PlaceDetails getPlaceDetails(String searchId) {
		try {
			return placeDetails.get(searchId);
		} catch (ExecutionException e) {
			logger.warn("An exception occurred while fetching place details!", e.getCause());
			return null;
		}
	}
	
	public PlaceDetails search(String searchId) throws Exception {
		PlaceDetailsResponse response = restTemplate.getForObject(PLACE_DETAILS_URL, PlaceDetailsResponse.class, searchId, apiKey);
		if (response.getResult() != null) {
			return response.getResult();
		} else {
			throw new PlacesException("Unable to find details for reference: " + searchId);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlaceDetails {

		@JsonProperty("name")
		private String name;

		@JsonProperty("icon")
		private String icon;

		@JsonProperty("url")
		private String url;

		@JsonProperty("formatted_address")
		private String address;

		@JsonProperty("geometry")
		private PlaceGeometry geometry;

		@JsonProperty("photos")
		private List<PlacePhoto> photos = Collections.emptyList();

		public String getAddress() {
			return address;
		}

		public PlaceGeometry getGeometry() {
			return geometry;
		}

		public String getIcon() {
			return icon;
		}

		public String getName() {
			return name;
		}

		public List<PlacePhoto> getPhotos() {
			return photos;
		}

		public String getUrl() {
			return url;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setGeometry(PlaceGeometry geometry) {
			this.geometry = geometry;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setPhotos(List<PlacePhoto> photos) {
			this.photos = photos;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlaceDetailsResponse {
		@JsonProperty("result")
		private PlaceDetails result;

		public PlaceDetails getResult() {
			return result;
		}

		public void setResult(PlaceDetails result) {
			this.result = result;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class UserAccessRequest {
		
		@JsonProperty("userName")
		@Email
		private String userName;
		
		@JsonProperty("password")
		@Min(6) 
		private String password;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NearRequest {
		
		@JsonProperty("latLong")
		private double[] latLong;
		
		@JsonProperty("distance")
		private double distance;
		
		@JsonProperty("limit")
		private int limit;


		public double[] getLatLong() {
			return latLong;
		}

		public void setLatLong(double[] latLong) {
			this.latLong = latLong;
		}
		
		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}
		
		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}
		
	}
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Place {
		

		@JsonProperty("name")
		private String name;

		@JsonProperty("icon")
		private String icon;

		@JsonProperty("place_id")
		private String place_id;

		@JsonProperty("vicinity")
		private String vicinity;

		@JsonProperty("geometry")
		private PlaceGeometry geometry;
		

		@JsonProperty("types")
		private String[] types;

		public String getVicinity() {
			return vicinity;
		}

		public PlaceGeometry getGeometry() {
			return geometry;
		}

		public String getIcon() {
			return icon;
		}

		public String getName() {
			return name;
		}


		public String getPlaceId() {
			return place_id;
		}

		public void setVicinity(String address) {
			this.vicinity = address;
		}

		public void setGeometry(PlaceGeometry geometry) {
			this.geometry = geometry;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public void setName(String name) {
			this.name = name;
		}



		public void setPlaceId(String place_id) {
			this.place_id = place_id;
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
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlacesResponse {
		
		@JsonProperty("results")
		private Place[] results;
		
		@JsonProperty("next_page_token")
		private String next_page_token;
		
		public Place[] getResult() {
			return results;
		}

		public void setResult(Place[] results) {
			this.results = results;
		}
		
		public String getNext_page_token() {
			return next_page_token;
		}

		public void setNext_page_token(String next_page_token) {
			this.next_page_token = next_page_token;
		}

	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlacesRequest{
		
		@JsonProperty("lat")
		private String lat;

		@JsonProperty("lng")
		private String lng;
		
		@JsonProperty("radius")
		private String radius;
		
		@JsonProperty("types")
		private String types;
		
		public String getLat() {
			return lat;
		}

		public String getLng() {
			return lng;
		}
		
		public String getRadius() {
			return radius;
		}

		public void setRadius( String radius) {
			this.radius = radius;
		}
		
		public String getTypes() {
			return types;
		}

		public void setTypes( String types) {
			this.types = types;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public void setLng(String lng) {
			this.lng = lng;
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlaceGeometry {

		@JsonProperty("location")
		private PlaceLocation location;

		public PlaceLocation getLocation() {
			return location;
		}

		public void setLocation(PlaceLocation location) {
			this.location = location;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlaceLocation {

		@JsonProperty("lat")
		private String lat;

		@JsonProperty("lng")
		private String lng;

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

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PlacePhoto {

		@JsonProperty("photo_reference")
		private String reference;

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}
	}

	public static class PlacesException extends Exception {

		private static final long serialVersionUID = 433629765655711368L;

		public PlacesException(String message) {
			super(message);
		}

		public PlacesException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}