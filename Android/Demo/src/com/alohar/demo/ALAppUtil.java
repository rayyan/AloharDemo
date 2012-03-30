/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ALAppUtil {

    private static Context mContext;

    public ALAppUtil(Context context) {
        mContext = context;
    }

    public void call(long mobileNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobileNumber));
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(dialIntent);

    }

    public void text(long mobileNumber) {
        // URI for SMS/MMS: URI starts with "mms:", "smsto:", "mmsto:",
        // "SMSTO:", and "MMSTO:"
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobileNumber));
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(smsIntent);

    }

    public void email(String message_body, String subject) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.putExtra(Intent.EXTRA_TEXT, message_body);
        smsIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mContext.startActivity(smsIntent);
    }
    
    public void emailFeedback(Activity owner, String subject, String[] toEmailList) {
    	Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
    	emailIntent.setType("plain/text");
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, toEmailList);
    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject); 
    	owner.startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
    }


    public void route(double sourceLat, double sourceLon, double destLat, double destLon) {
        StringBuilder urlBuilder = new StringBuilder("http://maps.google.com/maps?").append("saddr=").append(sourceLat)
                        .append(",").append(sourceLon).append("&daddr=").append(destLat).append(",").append(destLon);

        Intent routeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBuilder.toString()));
        routeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(routeIntent);
    }

    public void map(double lat, double lon) {

        StringBuilder uriBuilder = new StringBuilder().append("geo:").append(lat).append(",").append(lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriBuilder.toString()));
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mapIntent);
    }

    public void browser(String url) {
        String formatedUrl = url;
        if (!url.startsWith("http")) {
            formatedUrl = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formatedUrl));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(browserIntent);
    }
    
    public void streetView(double lat, double lon) {
    	streetView(lat, lon, 0, 0, 1.0, 16);
    }
    
    public void streetView(double lat, double lon, int yaw, int pitch, double zoom, int mapZoom) {
    	StringBuilder builder = new StringBuilder("google.streetview:cbll=");
    	builder.append(lat).append(",");
    	builder.append(lon).append(",");
    	builder.append("&cbp=1").append(",").append(yaw).append(",").append(",").append(pitch).append(",").append(zoom);
    	builder.append("&mz=").append(mapZoom);
    	
    	Intent  streetViewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.toString()));
    	streetViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	mContext.startActivity(streetViewIntent);
    }
}
