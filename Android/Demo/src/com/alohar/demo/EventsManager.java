/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import java.util.ArrayList;

import com.alohar.demo.PlaceEvent.EVENT_TYPE;
import com.alohar.user.callback.ALPlaceEventListener;
import com.alohar.user.content.data.UserStay;

public class EventsManager implements ALPlaceEventListener {

	public static final EventsManager instance = new EventsManager();
	
	private EventsManager() {
	}
	
	public static synchronized EventsManager getInstance(){
		return instance;
	}
	
	public ArrayList<PlaceEvent> events = new ArrayList<PlaceEvent>();
	public long trackingTime = System.currentTimeMillis();

	@Override
	public void onArrival(double latitude, double longitude) {
		PlaceEvent newEvent = new PlaceEvent();
		newEvent.time = System.currentTimeMillis();
		newEvent.type = EVENT_TYPE.ARRIVAL;
		newEvent.latitude = latitude;
		newEvent.longitude = longitude;
		events.add(0, newEvent);
	}

	@Override
	public void onDeparture(double latitude, double longitude) {
		PlaceEvent newEvent = new PlaceEvent();
		newEvent.time = System.currentTimeMillis();
		newEvent.type = EVENT_TYPE.DEPATURE;
		newEvent.latitude = latitude;
		newEvent.longitude = longitude;
		events.add(0, newEvent);
	}

	@Override
	public void onUserStayChanged(UserStay newUserStay) {
		PlaceEvent newEvent = new PlaceEvent();
		newEvent.time = System.currentTimeMillis();
		newEvent.type = EVENT_TYPE.UPDATE;
		newEvent.stay = newUserStay;
		events.add(0, newEvent);
	}
	
}
