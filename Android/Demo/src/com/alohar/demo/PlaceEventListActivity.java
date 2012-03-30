/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PlaceEventListActivity extends ListActivity {
	
	EventAdapter mAdapter;
	LayoutInflater mInflater;
	ArrayList<PlaceEvent> events = new ArrayList<PlaceEvent>();
	TextView statusView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		mInflater = LayoutInflater.from(this);
		statusView = (TextView)findViewById(R.id.title);
		
		ListView mainList = getListView();
		mAdapter = new EventAdapter();
		mainList.setAdapter(mAdapter);
		refresh();
	}
	
	private void refresh() {
		events = EventsManager.getInstance().events;
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
		statusView.setText("Event Triggered:" + events.size() + "|since " + Utils.formatTime(EventsManager.getInstance().trackingTime));
	}
	
	class EventAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return events.size();
		}

		@Override
		public Object getItem(int position) {
			return events.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_event, null);
			}
			PlaceEvent e = (PlaceEvent)getItem(position);
			((TextView)convertView.findViewById(R.id.type)).setText(e.type.toString());
			((TextView)convertView.findViewById(R.id.time)).setText(Utils.formatTime(e.time));
			if (e.stay != null) {
				((TextView)convertView.findViewById(R.id.stay)).setText(e.stay.toString());
			} else {
				((TextView)convertView.findViewById(R.id.stay)).setText(e.getLocDesc());
			}
			return convertView;
		}
	}
	
    private static final int MENU_REFRESH = 1;
    private static final int MENU_EMAIL = 2;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_REFRESH, 0, "refresh");
        menu.add(0, MENU_EMAIL, 0, "email");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_REFRESH:
            	refresh();
                return true;
            case MENU_EMAIL:
                ALAppUtil mAppUtil = new ALAppUtil(this);
                StringBuilder builder = new StringBuilder();
                for(PlaceEvent e: events){
                	builder.append(e.type.toString()).append("\n");
                	builder.append(Utils.formatTime(e.time)).append("\n");
                	builder.append(e.toString()).append("\n");
                }
                mAppUtil.email(builder.toString(), "Place Events Log");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
