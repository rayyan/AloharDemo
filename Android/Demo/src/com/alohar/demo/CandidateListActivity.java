/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alohar.core.Alohar;
import com.alohar.user.callback.ALEventListener;
import com.alohar.user.content.ALPlaceManager;
import com.alohar.user.content.data.ALEvents;
import com.alohar.user.content.data.Place;
import com.alohar.user.content.data.UserStay;

public class CandidateListActivity extends ListActivity implements ALEventListener {

	ListView mainList;
	PlaceAdapter mAdapter;
	ALPlaceManager mPlaceManager;
	

    ArrayList<Place> places = new ArrayList<Place>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		mainList = getListView();
		mAdapter = new PlaceAdapter(this);
		mainList.setAdapter(mAdapter);

		mPlaceManager = Alohar.getInstance().getPlaceManager();
		Object obj = getIntent().getSerializableExtra("UserStay");
		if (obj instanceof UserStay) {
			UserStay stay = (UserStay)obj;
			mPlaceManager.getCandidates(stay, this);
		}
	}

	
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
			
			Place p = (Place)getItem(position);
			if (p != null) {
				((TextView)convertView.findViewById(R.id.place_name)).setText(p.getName());
				((TextView)convertView.findViewById(R.id.place_address)).setText(p.getAddress());
				convertView.findViewById(R.id.next).setVisibility(View.GONE);
			}
			
			return convertView;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(ALEvents event, Object data) {
		if (event == ALEvents.USERSTAY_CANDIDATES_CALLBACK) {
			places = (ArrayList<Place>)data;
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mAdapter.notifyDataSetChanged();
				}
			});
		}
	}
}
