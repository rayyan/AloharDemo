/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import com.alohar.user.content.data.UserStay;

public class PlaceEvent {
	
	public enum EVENT_TYPE{
		ARRIVAL, DEPATURE, UPDATE,
	}
	
	public EVENT_TYPE type;
	public long time;
	public UserStay stay;
	public double latitude;
	public double longitude;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((stay == null) ? 0 : stay.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaceEvent other = (PlaceEvent) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (stay == null) {
			if (other.stay != null)
				return false;
		} else if (!stay.equals(other.stay))
			return false;
		if (time != other.time)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[" + type + "]:" + Utils.formatTime(time) + "\n");
		if (stay != null) {
			builder.append(stay.toString());
		} else {
			builder.append("(Lat, Long) = (" + latitude + "," + longitude +")");
		}
		return builder.toString();
	}
	
	public String getLocDesc() {
		return "lat=" + latitude + " lon=" + longitude;
	}
}
