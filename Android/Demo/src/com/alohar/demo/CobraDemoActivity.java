/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */

package com.alohar.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alohar.core.Alohar;
import com.alohar.user.callback.ALEventListener;
import com.alohar.user.callback.ALMotionListener;
import com.alohar.user.content.ALMotionManager;
import com.alohar.user.content.ALPlaceManager;
import com.alohar.user.content.data.ALEvents;
import com.alohar.user.content.data.MotionState;
import com.alohar.user.content.data.UserStay;

public class CobraDemoActivity extends Activity implements ALEventListener, ALMotionListener {

	/** The alohar instance */
	Alohar mAlohar;
	
	/** The place manager. */
	ALPlaceManager mPlaceManager;
	
	/** The motion manager. */
	ALMotionManager mMotionManager;
	
	View mAuthLayout, mMainLayout, mProgress;
	TextView mAccountView, mStatusView;
	EditText mUIDView;
	ToggleButton mToggleButton;
	
	/** The main handler. */
	Handler mainHandler;
	
	EventsManager mEventManager;
	
	/** The prefs. */
	SharedPreferences prefs;
	
	/** The Constant PREF_KEY. */
	private static final String PREF_KEY = "uid";
	
	//register your application at https://www.alohar.com/develper to get AppId and ApiKey
	public static final int APP_ID = -1; 

	//Keep this key secretly
	public static final String API_KEY = "YOUR API KEY";
	
	/** The uid. */
	public String uid;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        mainHandler = new Handler();
        
        mAuthLayout = findViewById(R.id.auth_layout);
        mMainLayout = findViewById(R.id.main_layout);
        mProgress = findViewById(R.id.progress_spin);
        
        mAccountView = (TextView)findViewById(R.id.account);
        mStatusView = (TextView)findViewById(R.id.service_status);
        mToggleButton = (ToggleButton)findViewById(R.id.toggle);
        mUIDView = (EditText)findViewById(R.id.uid);
        
        mAlohar = Alohar.init(getApplication());

        mPlaceManager = mAlohar.getPlaceManager();
        mMotionManager = mAlohar.getMotionManager();
        
        mEventManager = EventsManager.getInstance();
        //register listener
        mPlaceManager.registerPlaceEventListener(mEventManager);
        mMotionManager.registerMotionListener(this);
        
        uid = prefs.getString(PREF_KEY, null);
        if (uid != null) {
        	mUIDView.setText(String.valueOf(uid));
        	onAuthenClick(mUIDView);
        } else {
        	mAuthLayout.setVisibility(View.VISIBLE);
        }
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//update the title
		String title = String.format("%s v%s %d", getString(R.string.app_name), getString(R.string.version), mAlohar.version());
		setTitle(title);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateServiceStatus();
		//update current user stay
    	UserStay stay = mPlaceManager.getLastKnownStay();
    	if (stay != null) {
    		((TextView)findViewById(R.id.current_stay)).setText(stay.toString());
    	}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
    /**
     * On service click.
     *
     * @param view the view
     */
    public void onServiceClick(View view) {
    	boolean isChecked = ((ToggleButton)view).isChecked();
    	if (isChecked) {
    		//turn on
    		mAlohar.startServices();
    		mStatusView.setText("Service is running!");
    	} else {
    		//turn off
    		mAlohar.stopServices();
    		mStatusView.setText("Service stopped");
    	}
    }
    
	/**
	 * Toast error.
	 *
	 * @param msgId the msg id
	 */
	public void toastError(final int msgId) {
		final String message = getText(msgId).toString();
		toastError(message);
	}

	/**
	 * Toast error.
	 *
	 * @param message the message
	 */
	public void toastError(final String message) {
		mainHandler.post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		    	mAuthLayout.setVisibility(View.VISIBLE);
		    	mMainLayout.setVisibility(View.GONE);
		    	mProgress.setVisibility(View.GONE);
			}
		});
	}
    
    private static final int MENU_FLUSH = 1;
    private static final int MENU_EXIT  = 2;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_FLUSH, 0, "flush");
        menu.add(0, MENU_EXIT, 0, "Exit");

        return super.onCreateOptionsMenu(menu);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_FLUSH:
        		// try to trigger one post every 15 minutes
            	mAlohar.flush();
        		return true;
            case MENU_EXIT:
    			mAlohar.stopServices();
            	mAlohar.teardown();
    			this.finish();
    			return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    /**
     * On register click.
     *
     * @param v the view
     */
    public void onRegisterClick(View v) {
    	mAuthLayout.setVisibility(View.GONE);
    	mMainLayout.setVisibility(View.GONE);
    	mProgress.setVisibility(View.VISIBLE);
		mAlohar.register(APP_ID, API_KEY, this);
    }
    
    /**
     * On authen click.
     *
     * @param v the view
     */
    public void onAuthenClick(View v) {
    	String inputUID  = mUIDView.getText().toString();
    	if (inputUID.trim().length() == 0) {
    		toastError("Please give a valid UID");
    	} else {
    		uid = inputUID;
    		try {
        		mAlohar.authenticate(uid, APP_ID, API_KEY, this);
    		} catch (Exception e) {
//    			e.printStackTrace();
			}
    	}
    	mProgress.setVisibility(View.VISIBLE);
    }
    
    /**
     * On search place profiles.
     *
     * @param v the view
     */
    public void onSearchPlaceProfiles(View v) {
    	Intent intent = new Intent(this, PlaceListActivity.class);
    	startActivity(intent);
    }

    /**
     * On search user stays.
     *
     * @param v the view
     */
    public void onSearchUserStays(View v) {
    	Intent intent = new Intent(this, UserStayListActivity.class);
    	startActivity(intent);
    }
    
    /**
     * Update service status.
     */
    private void updateServiceStatus() {
    	if (mAlohar.isServiceRunning()) {
    		mToggleButton.setChecked(true);
        	mStatusView.setText("Service is running!");
    	} else {
    		mToggleButton.setChecked(false);
    		mStatusView.setText("Service stopped");
    	}
    }
    
    /**
     * On show place events.
     *
     * @param v the view
     */
    public void onShowPlaceEvents(View v) {
    	Intent intent = new Intent(this, PlaceEventListActivity.class);
    	startActivity(intent);
    }

	/* (non-Javadoc)
	 * @see com.alohar.user.callback.ALEventListener#handleEvent(com.alohar.user.content.data.ALEvents, java.lang.Object)
	 */
	@Override
	public void handleEvent(ALEvents event, Object data) {
		if (event == ALEvents.AUTHENTICATE_CALLBACK || event == ALEvents.REGISTRATION_CALLBACK) {
			if (data instanceof String) {
				uid = (String)data;
				Log.i("CobraDemo", "######UID=" + uid);
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				prefs.edit().putString("uid", uid).commit();
			}
			
			//alohar service is ready to start
          mainHandler.post(new Runnable() {
			
			@Override
			public void run() {
                //switch to main layout
		    	mAuthLayout.setVisibility(View.GONE);
		    	mMainLayout.setVisibility(View.VISIBLE);
		    	mProgress.setVisibility(View.GONE);
                mAccountView.setText(uid);
			}
          });
		} else if (event == ALEvents.GENERAL_ERROR_CALLBACK || event == ALEvents.SERVER_ERROR_CALLBACK) {
			toastError((String)data);
		}
	}


	/* (non-Javadoc)
	 * @see com.alohar.user.callback.ALMotionListener#onMotionStateChanged(com.alohar.user.content.data.MotionState, com.alohar.user.content.data.MotionState)
	 */
	@Override
	public void onMotionStateChanged(MotionState oldState, MotionState newState) {
		final String motionStateSwitch = oldState.name() + "=>" + newState.name();
		final String userStateSwitch = "Stationary=" + mMotionManager.isStationary() + "|OnCommute=" + mMotionManager.isOnCommute();
		mainHandler.post(new Runnable() {
			
			@Override
			public void run() {
				((TextView)findViewById(R.id.motion_state)).setText(motionStateSwitch);
				((TextView)findViewById(R.id.user_state)).setText(userStateSwitch);
			}
		});
	}
}