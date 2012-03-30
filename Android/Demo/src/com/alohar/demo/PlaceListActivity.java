/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alohar.core.Alohar;
import com.alohar.user.callback.ALEventListener;
import com.alohar.user.content.ALPlaceManager;
import com.alohar.user.content.data.ALEvents;
import com.alohar.user.content.data.Place;
import com.alohar.user.content.data.PlaceProfile;

public class PlaceListActivity extends ListActivity implements ALEventListener {

	PlaceAdapter mAdapter;
	ALPlaceManager mPlaceManager;
	TextView statusView;
	
    ArrayList<PlaceProfile> places = new ArrayList<PlaceProfile>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		statusView = (TextView)findViewById(R.id.title);
		
		ListView mainList = getListView();
		mAdapter = new PlaceAdapter(this);
		mainList.setAdapter(mAdapter);
		
		mainList.setOnItemClickListener(mPlaceClickListener);
		
		mPlaceManager = Alohar.getInstance().getPlaceManager();
		mPlaceManager.searchPlaces(0, System.currentTimeMillis()/ 1000, ".*", 0, 100, this);
	}
	
	AdapterView.OnItemClickListener mPlaceClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Object tag = view.getTag();
			if (tag instanceof Place) {
				Intent intent = new Intent(PlaceListActivity.this, UserStayListActivity.class);
				intent.putExtra("Place", (Place)tag);
				startActivity(intent);
			}
		}
	};

	class PlaceAdapter extends BaseAdapter {
		
		Context mContext;
		
		public PlaceAdapter(Context c) {
			this.mContext = c;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return places.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return places.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.row_place, null);
			}
			
			PlaceProfile p = (PlaceProfile)getItem(position);
			if (p != null) {
				((TextView)convertView.findViewById(R.id.place_name)).setText(p.getName());
				((TextView)convertView.findViewById(R.id.place_address)).setText(p.getAddress());
			}
			
			convertView.setTag(p);
			
			return convertView;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(ALEvents event, Object data) {
		if (event == ALEvents.PLACES_QUERY_CALLBACK) {
			places = (ArrayList<PlaceProfile>)data;
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mAdapter.notifyDataSetChanged();
					statusView.setText("Total Places found:" + places.size());
				}
			});
		}
	}
}
