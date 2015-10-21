package com.facebook.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

         // title
    	TextView name = (TextView) vi.findViewById(R.id.name);
    	TextView picurl = (TextView) vi.findViewById(R.id.picurl);
    	TextView dob = (TextView) vi.findViewById(R.id.dob);
    	TextView weight = (TextView) vi.findViewById(R.id.weight);
    	TextView height = (TextView) vi.findViewById(R.id.height);
    	TextView handed = (TextView) vi.findViewById(R.id.handed);
    	TextView country = (TextView) vi.findViewById(R.id.country);
    	TextView bio = (TextView) vi.findViewById(R.id.bio);
    	TextView college = (TextView) vi.findViewById(R.id.college);
    	TextView facebook = (TextView) vi.findViewById(R.id.facebook);
		TextView twitter = (TextView) vi.findViewById(R.id.twitter);
		TextView website = (TextView) vi.findViewById(R.id.website);
    	
           ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> playerdata = new HashMap<String, String>();
        playerdata = data.get(position);
        
        // Setting all values in listview
        name.setText(playerdata.get(Players.KEY_NAME));
        picurl.setText(playerdata.get(Players.KEY_PIC_URL));
        dob.setText(playerdata.get(Players.KEY_DOB));
        weight.setText(playerdata.get(Players.KEY_WEIGHT));
        height.setText(playerdata.get(Players.KEY_HEIGHT));
        handed.setText(playerdata.get(Players.KEY_HANDED));
        country.setText(playerdata.get(Players.KEY_COUNTRY));
        bio.setText(playerdata.get(Players.KEY_BIO));
        college.setText(playerdata.get(Players.KEY_COLLEGE));
        facebook.setText(playerdata.get(Players.KEY_FACEBOOK));
        twitter.setText(playerdata.get(Players.KEY_TWITTER));
        website.setText(playerdata.get(Players.KEY_WEBSITE));
        
               imageLoader.DisplayImage(playerdata.get(Players.KEY_PIC_URL), thumb_image);
        return vi;
    }
    
}    
            
            