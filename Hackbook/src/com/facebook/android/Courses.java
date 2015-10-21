package com.facebook.android;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Courses extends ListActivity {
	
	private EditText et;
	// All static variables
			static final String URL = "http://54.235.75.120/webservice/courses.php";
			// XML node keys
			
			static final String KEY_COURSE = "course";  // parent node
			static final String KEY_NAME = "name";
			static final String KEY_RECORDSCORE = "recordscore";
			static final String KEY_BIO = "bio";
			
			static final String KEY_FACEBOOK = "facebook";
			static final String KEY_TWITTER = "twitter";
			static final String KEY_YOUTUBE = "youtube";
			static final String KEY_WEBSITE = "website";
			static final String KEY_TOUR_IMAGE = "image";
			
			static final String KEY_FOUNDED = "founded";
			static final String KEY_LENGTH = "length";
			
			
			static final String KEY_LATITUDE = "lat";
			static final String KEY_LONGITUDE = "long";
			
			 private Animation animShow, animHide;
			 com.facebook.android.TransparentPanel popup;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.courses);
						
		

			
			StrictMode.ThreadPolicy tourpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(tourpolicy); 
			
			
			
			et = (EditText) findViewById(R.id.PlayerSearchEditText);
			
		    popup = (TransparentPanel) findViewById(R.id.popup_window);
		      //Declare the timer
		        Timer t = new Timer();
		        //Set the schedule function and rate
		        t.scheduleAtFixedRate(new TimerTask() {

		            @Override
		            public void run() {
		                //Called each time when 1000 milliseconds (1 second) (the period parameter)
		                
		      	      //We must use this function in order to change the text view text
		      	        runOnUiThread(new Runnable() {

		      	            @Override
		      	            public void run() {
		      	              popup.setVisibility(View.GONE);
		      	           initPopup();
		      	            }
		      	             
		      	        });
		            }
		                 
		        },
		        //Set how long before to start calling the TimerTask (in milliseconds)
		        10,
		        //Set the amount of time between each execution (in milliseconds)
		        30000);
		        
		  
		        
		        
		
		      
		  
		       
		        
		    }
		     
		    private void initPopup() {
		         
		        final TransparentPanel popup = (TransparentPanel) findViewById(R.id.popup_window);
		 
		     
		         
		         
		         
		        animShow = AnimationUtils.loadAnimation( this, R.anim.popup_show);
		        animHide = AnimationUtils.loadAnimation( this, R.anim.popup_hide);
		         
		       
		        final Button   hideButton = (Button) findViewById(R.id.hide_popup_button);
		        
		        
		        
		        final ImageView locationDescription = (ImageView) findViewById(R.id.location_description);
		         
		      
		        locationDescription.setBackgroundResource(R.drawable.nike_logo);
		        
		        
		        	       
		                popup.setVisibility(View.VISIBLE);
		                popup.startAnimation( animShow );
		                hideButton.setEnabled(true);
		      
		         
		        hideButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View view) {
		                popup.startAnimation( animHide );
		                hideButton.setEnabled(false);
		                popup.setVisibility(View.GONE);
		        }});
		 
			
			
			
			 final List<HashMap<String, String>> coursesCopy  = new ArrayList<HashMap<String, String>>();;

		
			final ArrayList<HashMap<String, String>> courses = new ArrayList<HashMap<String, String>>();

			final XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // getting XML
			Document doc = parser.getDomElement(xml); // getting DOM element
	if (doc != null) {
			NodeList nl = doc.getElementsByTagName(KEY_COURSE);

			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
				map.put(KEY_RECORDSCORE, parser.getValue(e, KEY_RECORDSCORE));
				map.put(KEY_BIO, parser.getValue(e, KEY_BIO));
				map.put(KEY_FACEBOOK, parser.getValue(e, KEY_FACEBOOK));
				map.put(KEY_TWITTER, parser.getValue(e, KEY_TWITTER));
				map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));
				
				map.put(KEY_TOUR_IMAGE, parser.getValue(e, KEY_TOUR_IMAGE));
				map.put(KEY_FOUNDED, parser.getValue(e, KEY_FOUNDED));
				map.put(KEY_LENGTH, parser.getValue(e, KEY_LENGTH));
				map.put(KEY_LATITUDE, parser.getValue(e, KEY_LATITUDE));
				map.put(KEY_LONGITUDE, parser.getValue(e, KEY_LONGITUDE));
				
			
				// adding HashList to ArrayList
				courses.add(map);
			}
			
			
	}
	
	

			// Adding menuItems to ListView
			final ListAdapter adapter = new SimpleAdapter(this, courses,
					R.layout.courses_list_item,
					new String[] {KEY_NAME, KEY_RECORDSCORE, KEY_BIO, KEY_FACEBOOK, KEY_TWITTER, KEY_WEBSITE, KEY_TOUR_IMAGE, KEY_FOUNDED, KEY_LENGTH, KEY_LATITUDE,  KEY_LONGITUDE  }, new int[] {
							R.id.name, R.id.recordscore, R.id.bio, R.id.facebook, R.id.twitter, R.id.website, R.id.tourimage, R.id.founded, R.id.length, R.id.latitude, R.id.longitude});

			setListAdapter(adapter); 

			// selecting single ListView item
			
		final ListView lv = getListView(); 
		lv.setTextFilterEnabled(true);
		
		 /** for loop to copy all kay values contained in courses hashmap arraylist to coursesCopy
         * 
         */
		
		   for (HashMap<String, String> map  : courses)
	        {
	        	 coursesCopy.add(map);
	            
	       
	        }
	                
	        /** the below Text changed listener is used to sort the hashmap based on 
	         * the text letter(s) entered and display the results in a list form
	         * 
	         */
	        et.addTextChangedListener(new TextWatcher() //adds a text changed listener to the edittext field
	        {
	            @Override
	            public void afterTextChanged(Editable s)
	            {
	            	courses.clear();
	                String search=s.toString();
	                for(HashMap<String, String> map : coursesCopy)
	                {
	                    if(map.get("name").toLowerCase().contains(search.toLowerCase())) //get map value "name" and check if search string is contained in it
	                    	courses.add(map);
	                    ((BaseAdapter) adapter).notifyDataSetChanged();
	                }
	            }

				@Override //before text changed do something
				public void beforeTextChanged(CharSequence arg0, int arg1, 
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override   //on text changed do something
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					System.out.println("It worked");
				};
	          //  ... other overridden methods can go here ...
	        });

		
		

			
			lv.setOnItemClickListener(new OnItemClickListener() {
				
				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					String recordscore = ((TextView) view.findViewById(R.id.recordscore)).getText().toString();
					String bio = ((TextView) view.findViewById(R.id.bio)).getText().toString();
					String facebook = ((TextView) view.findViewById(R.id.facebook)).getText().toString();
					String twitter = ((TextView) view.findViewById(R.id.twitter)).getText().toString();
					String website = ((TextView) view.findViewById(R.id.website)).getText().toString();
					String tourimage = ((TextView) view.findViewById(R.id.tourimage)).getText().toString();
					String founded = ((TextView) view.findViewById(R.id.founded)).getText().toString();
					String length = ((TextView) view.findViewById(R.id.length)).getText().toString();
					String latitude = ((TextView) view.findViewById(R.id.latitude)).getText().toString();
					String longitude = ((TextView) view.findViewById(R.id.longitude)).getText().toString();
					
					
					

				
					
					
				
					
					
					
					// Starting new intent
					Intent in = new Intent(getApplicationContext(), CoursesSingleMenuItemActivity.class);
					in.putExtra(KEY_NAME, name);
					in.putExtra(KEY_RECORDSCORE, recordscore);
				    in.putExtra(KEY_BIO, bio);
					in.putExtra(KEY_FACEBOOK, facebook);
					in.putExtra(KEY_TWITTER, twitter);
					in.putExtra(KEY_WEBSITE, website);
					in.putExtra(KEY_TOUR_IMAGE, tourimage);
					in.putExtra(KEY_FOUNDED, founded);
					in.putExtra(KEY_LENGTH, length);
					in.putExtra(KEY_LATITUDE, latitude);
					in.putExtra(KEY_LONGITUDE, longitude);
						startActivity(in);

				}
			});
			
		
			
	
		
	}
	
	
	




	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_main, menu);
			return true;
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		    case R.id.homemenu:
	        	homemenu();        	
	        	return true;
		       
		        case R.id.preferences:
		            preferences(); 
		            return true;
		        case R.id.players:
		        players(); 
		        return true;
		        case R.id.courses:
		        	courses(); 
		              	return true;
		        default:
		            return super.onOptionsItemSelected(item);
		            
		    }     

		    

		}


	

	private void homemenu() 

	{
		// TODO Auto-generated method stub
	startActivity (new Intent(getApplicationContext(), Home.class));

		
	}
	private void preferences() {
		// TODO Auto-generated method stub
		startActivity (new Intent(getApplicationContext(), PushPreferencesActivity.class));
	}

	private void players() {
		// TODO Auto-generated method stub
		
		startActivity (new Intent(getApplicationContext(), Players.class));
	}

	
	
	   
	private void courses() {
		// TODO Auto-generated method stub
		startActivity (new Intent(getApplicationContext(), Courses.class));
	

	}

	



}