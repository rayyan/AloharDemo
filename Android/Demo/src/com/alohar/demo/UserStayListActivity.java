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
import com.alohar.user.content.data.UserStay;

public class UserStayListActivity extends ListActivity implements ALEventListener {

	ListView mainList;
	UserStayAdapter mAdapter;
	ALPlaceManager mPlaceManager;
	
	//Alohar Mobile HQ
    private double latitude = 37.432110;
    private double longitude = -122.103274;

    ArrayList<UserStay> userStays = new ArrayList<UserStay>();
	
	TextView statusView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		statusView = (TextView)findViewById(R.id.title);
		
		mainList = getListView();
		mAdapter = new UserStayAdapter(this);
		mainList.setAdapter(mAdapter);
		mainList.setOnItemClickListener(mUserStayClickListener);

		mPlaceManager = Alohar.getInstance().getPlaceManager();
		Object obj = getIntent().getSerializableExtra("Place");
		if (obj instanceof PlaceProfile) {
			PlaceProfile place = (PlaceProfile)obj;
			mPlaceManager.getUserStays(place, this);
		} else {
			//show case the general search of user stay
			mPlaceManager.searchUserStays(latitude, longitude, 500, 0, System.currentTimeMillis()/1000, true, 100, this);
		}
	}

	AdapterView.OnItemClickListener mUserStayClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Object tag = view.getTag();
			if (tag instanceof UserStay) {
				Intent intent = new Intent(UserStayListActivity.this, CandidateListActivity.class);
				intent.putExtra("UserStay", (UserStay)tag);
				startActivity(intent);
			}
		}
	};

	
	class UserStayAdapter extends BaseAdapter {
		
		Context mContext;
		
		public UserStayAdapter(Context c) {
			this.mContext = c;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userStays.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return userStays.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.row_userstay, null);
			}
			
			UserStay stay = (UserStay)getItem(position);
			if (stay != null) {
				Place p = stay.getSelectedPlace();
				((TextView)convertView.findViewById(R.id.stay_name)).setText(p.getName());
				((TextView)convertView.findViewById(R.id.stay_address)).setText(p.getAddress());
				StringBuilder builder = new StringBuilder();
				builder.append(Utils.formatDateTime(stay.getStartTime() * 1000)).append(" -- ");
				builder.append(Utils.formatDateTime(stay.getEndTime() * 1000)).append("\n");
				builder.append(Utils.formatPeriod((stay.getEndTime() - stay.getStartTime()), false));
				((TextView)convertView.findViewById(R.id.stay_period)).setText(builder.toString());
			}
			
			convertView.setTag(stay);
			
			return convertView;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(ALEvents event, Object data) {
		if (event == ALEvents.USERSTAYS_QUERY_CALLBACK || event == ALEvents.USERSTATS_OF_A_PLACE_CALLBACK) {
			userStays = (ArrayList<UserStay>)data;
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mAdapter.notifyDataSetChanged();
					statusView.setText("Total User Stays found:" + userStays.size());
				}
			});
		}
	}
}
