package com.facebook.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;






public class Home extends ListActivity {
ViewPager myPager;
TextView text;
Button b1;
	 	 

// All static variables


// All static variables
		static final String URL1 = "http://54.235.75.120/webservice/tournaments.php";
		// XML node keys
		static final String KEY_TOURNAMENTS = "tournaments"; // parent node
		static final String KEY_TOURNAMENT = "tournament";
		static final String KEY_TNAME = "name";
		static final String KEY_FROMDATE = "fromdate";
		static final String KEY_TODATE = "todate";
		static final String KEY_TOUR_ID = "tourid";
		static final String KEY_CURRENTHOLDER = "currentholderid";
		static final String KEY_MAJOR = "major";
		static final String KEY_RECORDSCORE = "recordscore";
		static final String KEY_SPONSOR = "sponsor";
		static final String KEY_PRIZEFUND = "prizefund";
		static final String KEY_BIO = "bio";
		static final String KEY_FACEBOOK = "facebook";
		static final String KEY_TWITTER = "twitter";
		static final String KEY_WEBSITE = "website";
		static final String KEY_TOUR_IMAGE = "image";
		static final String KEY_YOUTUBE = "youtube";
		static final String KEY_BANNER = "banner";
		static final String KEY_TOURNAMENT_ID = "tournamentId";


		static final String URL = "http://54.235.75.120/webservice/news.php";
		// XML node keys
		static final String KEY_NEWS = "news"; // parent node
		static final String KEY_ARTICLE = "article";
		static final String KEY_TITLE = "title";
		static final String KEY_INTROTEXT = "introtext";
		static final String KEY_BODY = "body";
		static final String KEY_CREATED = "created";
		static final String KEY_INTROIMAGE = "intro_image";
		
		 String title;
			String intro;
			String body;
			String created;
			String introimage; 
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageArra, stringArray);
		 ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);
		
			
		StrictMode.ThreadPolicy tourpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(tourpolicy); 
		
		  final TextView text = (TextView) findViewById(R.id.title);
		  text.setVisibility(View.VISIBLE);
		  b1 = (Button) findViewById(R.id.button1);
		
		   CirclePageIndicator mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	        mIndicator.setViewPager(myPager);
	        
	        text.bringToFront(); 
	        b1.bringToFront(); 
	        myPager.invalidate();
	        
	        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

			final XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL1); // getting XML
			Document doc = parser.getDomElement(xml); // getting DOM element
	if (doc != null) {
			NodeList nl = doc.getElementsByTagName(KEY_TOURNAMENT);

			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_TNAME, parser.getValue(e, KEY_TNAME));
				map.put(KEY_FROMDATE, parser.getValue(e, KEY_FROMDATE));
				map.put(KEY_TODATE, parser.getValue(e, KEY_TODATE));
				map.put(KEY_TOUR_ID, parser.getValue(e, KEY_TOUR_ID));
				map.put(KEY_CURRENTHOLDER, parser.getValue(e, KEY_CURRENTHOLDER));
				map.put(KEY_MAJOR, parser.getValue(e, KEY_MAJOR));
				
				String majorout = parser.getValue(e, KEY_MAJOR);
				
				
				
				map.put(KEY_RECORDSCORE, parser.getValue(e, KEY_RECORDSCORE));
				map.put(KEY_SPONSOR, parser.getValue(e, KEY_SPONSOR));
				map.put(KEY_PRIZEFUND, parser.getValue(e, KEY_PRIZEFUND));
				map.put(KEY_BIO, parser.getValue(e, KEY_BIO));
				map.put(KEY_FACEBOOK, parser.getValue(e, KEY_FACEBOOK));
				map.put(KEY_TWITTER, parser.getValue(e, KEY_TWITTER));
				map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));
				
				map.put(KEY_TOUR_IMAGE, parser.getValue(e, KEY_TOUR_IMAGE));
				map.put(KEY_BANNER, parser.getValue(e, KEY_BANNER));
				map.put(KEY_TOURNAMENT_ID, parser.getValue(e, KEY_TOURNAMENT_ID));
				
			
				// adding HashList to ArrayList
				menuItems.add(map);
			}
			
			
	}
	

	 

			// Adding menuItems to ListView
			ListAdapter adapter1 = new SimpleAdapter(this, menuItems,
					R.layout.tour_list_item,
					new String[] {KEY_TNAME, KEY_FROMDATE, KEY_TODATE, KEY_CURRENTHOLDER, KEY_MAJOR, KEY_RECORDSCORE, KEY_SPONSOR, KEY_PRIZEFUND, 
					KEY_BIO, KEY_FACEBOOK, KEY_TWITTER, KEY_WEBSITE, KEY_TOUR_IMAGE,}, new int[] {
							R.id.name, R.id.fromdate, R.id.todate, R.id.currentholder, R.id.major , 
							R.id.recordscore , R.id.sponsor , R.id.prizefund, R.id.bio, R.id.facebook, R.id.twitter, R.id.website, R.id.tourimage});

			setListAdapter(adapter1); 

			// selecting single ListView item
			
		final ListView lv = getListView(); 
		
		
		

		
		

			
			lv.setOnItemClickListener(new OnItemClickListener() {
				
				

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					// getting values from selected ListItem
					String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
					String fdate = ((TextView) view.findViewById(R.id.fromdate)).getText().toString();
					String tdate = ((TextView) view.findViewById(R.id.todate)).getText().toString();
					String cholder = ((TextView) view.findViewById(R.id.currentholder)).getText().toString();
					String major = ((TextView) view.findViewById(R.id.major)).getText().toString();
					String recordscore = ((TextView) view.findViewById(R.id.recordscore)).getText().toString();
					String sponsor = ((TextView) view.findViewById(R.id.sponsor)).getText().toString();
					String prizefund = ((TextView) view.findViewById(R.id.prizefund)).getText().toString();
					String bio = ((TextView) view.findViewById(R.id.bio)).getText().toString();
					String facebook = ((TextView) view.findViewById(R.id.facebook)).getText().toString();
					String twitter = ((TextView) view.findViewById(R.id.twitter)).getText().toString();
					String website = ((TextView) view.findViewById(R.id.website)).getText().toString();
					String tourimage = ((TextView) view.findViewById(R.id.tourimage)).getText().toString();
					
					
					

				
					
					
				
					
					
					
					// Starting new intent
					Intent in = new Intent(getApplicationContext(), TournamentsSingleMenuItemActivity.class);
					in.putExtra(KEY_TNAME, name);
					in.putExtra(KEY_FROMDATE, fdate);
					in.putExtra(KEY_TODATE, tdate);
					in.putExtra(KEY_CURRENTHOLDER, cholder);
					in.putExtra(KEY_MAJOR, major);
					in.putExtra(KEY_RECORDSCORE, recordscore);
					in.putExtra(KEY_SPONSOR, sponsor);
					in.putExtra(KEY_PRIZEFUND, prizefund);
					in.putExtra(KEY_BIO, bio);
					in.putExtra(KEY_FACEBOOK, facebook);
					in.putExtra(KEY_TWITTER, twitter);
					in.putExtra(KEY_WEBSITE, website);
					in.putExtra(KEY_TOUR_IMAGE, tourimage);
						startActivity(in);

				}
			});
	        
	        
	        
	        
	        
	        

			ArrayList<HashMap<String, String>> menuItems0 = new ArrayList<HashMap<String, String>>();

			final XMLParser parser0 = new XMLParser();
			String xml0 = parser0.getXmlFromUrl(URL); // getting XML
			Document doc0 = parser0.getDomElement(xml0); // getting DOM element
	if (doc0 != null) {
			NodeList nl = doc0.getElementsByTagName(KEY_ARTICLE);

			// looping through all item nodes <item>
			for (int i = 0; i < 1; i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_TITLE, parser0.getValue(e, KEY_TITLE));
				map.put(KEY_INTROTEXT, parser0.getValue(e, KEY_INTROTEXT));
				map.put(KEY_BODY, parser0.getValue(e, KEY_BODY));
				map.put(KEY_CREATED, parser0.getValue(e, KEY_CREATED));
				map.put(KEY_INTROIMAGE, parser0.getValue(e, KEY_INTROIMAGE));
				
		         title = parser0.getValue(e, KEY_TITLE);
				 intro = parser0.getValue(e, KEY_INTROTEXT);
				 body = parser0.getValue(e, KEY_BODY);
				 created = parser0.getValue(e, KEY_CREATED);
				 introimage = parser0.getValue(e, KEY_INTROIMAGE);
				
				System.out.println(title);
				System.out.println(intro);
				System.out.println(body);
				System.out.println(created);
				System.out.println(introimage);
				
				menuItems0.add(map);
	}  
	
	}
	
	
	 text.setText(title);
    
	
	
	b1.setOnClickListener(new View.OnClickListener() {
		
		
		
        public void onClick(View v) {

            //v.getId() will give you the image id
        	
        	// getting values from selected ListItem
		       				
        	
        	// Starting new intent
			Intent in = new Intent(getApplicationContext(), FeaturedNewsActivity.class);
			in.putExtra(KEY_TITLE, title);
			in.putExtra(KEY_INTROTEXT, intro);
			in.putExtra(KEY_BODY, body);
			in.putExtra(KEY_CREATED, created);
			in.putExtra(KEY_INTROIMAGE, introimage);
			
			
				startActivity(in);

        }
    });
	
	        
	       //We set this on the indicator, NOT the pager
	        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                      	
	    	        
	    	         if (position == 1)
	    	        {
	    	        	
	    	        	ArrayList<HashMap<String, String>> menuItems1 = new ArrayList<HashMap<String, String>>();

						final XMLParser parser1 = new XMLParser();
						String xml1 = parser.getXmlFromUrl(URL); // getting XML
						Document doc1 = parser.getDomElement(xml1); // getting DOM element
				if (doc1 != null) {
						NodeList nl = doc1.getElementsByTagName(KEY_ARTICLE);

						// looping through all item nodes <item>
						for (int i = 1; i < 2; i++) {
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							// adding each child node to HashMap key => value
							map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
							map.put(KEY_INTROTEXT, parser.getValue(e, KEY_INTROTEXT));
							map.put(KEY_BODY, parser.getValue(e, KEY_BODY));
							map.put(KEY_CREATED, parser.getValue(e, KEY_CREATED));
							map.put(KEY_INTROIMAGE, parser.getValue(e, KEY_INTROIMAGE));
							
					         title = parser.getValue(e, KEY_TITLE);
							 intro = parser.getValue(e, KEY_INTROTEXT);
							 body = parser.getValue(e, KEY_BODY);
							 created = parser.getValue(e, KEY_CREATED);
							 introimage = parser.getValue(e, KEY_INTROIMAGE);
							
						
							
							menuItems1.add(map);
				}  
				
				}
				
				
				 text.setText(title);
 	          
				
				
				b1.setOnClickListener(new View.OnClickListener() {
					
					
					
                    public void onClick(View v) {

                        //v.getId() will give you the image id
                    	
                    	// getting values from selected ListItem
        			       				
                    	
                    	// Starting new intent
						Intent in = new Intent(getApplicationContext(), FeaturedNewsActivity.class);
						in.putExtra(KEY_TITLE, title);
						in.putExtra(KEY_INTROTEXT, intro);
						in.putExtra(KEY_BODY, body);
						in.putExtra(KEY_CREATED, created);
						in.putExtra(KEY_INTROIMAGE, introimage);
						
						
							startActivity(in);

                    }
                });
				
				
	    	        	
	    	       	       
	    	        }
	    	        else if (position == 2)
	    	        {
	    	        	
	    	        	
	    	        	ArrayList<HashMap<String, String>> menuItems2 = new ArrayList<HashMap<String, String>>();

						final XMLParser parser2 = new XMLParser();
						String xml2 = parser.getXmlFromUrl(URL); // getting XML
						Document doc2 = parser.getDomElement(xml2); // getting DOM element
				if (doc2 != null) {
						NodeList nl = doc2.getElementsByTagName(KEY_ARTICLE);

						// looping through all item nodes <item>
						for (int i = 2; i < 3; i++) {
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							// adding each child node to HashMap key => value
							map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
							map.put(KEY_INTROTEXT, parser.getValue(e, KEY_INTROTEXT));
							map.put(KEY_BODY, parser.getValue(e, KEY_BODY));
							map.put(KEY_CREATED, parser.getValue(e, KEY_CREATED));
							map.put(KEY_INTROIMAGE, parser.getValue(e, KEY_INTROIMAGE));
							
					         title = parser.getValue(e, KEY_TITLE);
							 intro = parser.getValue(e, KEY_INTROTEXT);
							 body = parser.getValue(e, KEY_BODY);
							 created = parser.getValue(e, KEY_CREATED);
							 introimage = parser.getValue(e, KEY_INTROIMAGE);
							
						
							
							menuItems2.add(map);
				}  
				
				}
				
				
				 text.setText(title);
 	        
				
				
				b1.setOnClickListener(new View.OnClickListener() {
					
					
					
                    public void onClick(View v) {

                        //v.getId() will give you the image id
                    	
                    	// getting values from selected ListItem
        			       				
                    	
                    	// Starting new intent
						Intent in = new Intent(getApplicationContext(), FeaturedNewsActivity.class);
						in.putExtra(KEY_TITLE, title);
						in.putExtra(KEY_INTROTEXT, intro);
						in.putExtra(KEY_BODY, body);
						in.putExtra(KEY_CREATED, created);
						in.putExtra(KEY_INTROIMAGE, introimage);
						
						
							startActivity(in);

                    }
                });
				
	    	        	
	    	        	       
	    	        }
	    	        else  if (position == 3)   {
	    	        	
	    	        	
	    	        	ArrayList<HashMap<String, String>> menuItems3 = new ArrayList<HashMap<String, String>>();

						final XMLParser parser3 = new XMLParser();
						String xml3 = parser.getXmlFromUrl(URL); // getting XML
						Document doc3 = parser.getDomElement(xml3); // getting DOM element
				if (doc3 != null) {
						NodeList nl = doc3.getElementsByTagName(KEY_ARTICLE);

						// looping through all item nodes <item>
						for (int i = 3; i < 4; i++) {
							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							// adding each child node to HashMap key => value
							map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
							map.put(KEY_INTROTEXT, parser.getValue(e, KEY_INTROTEXT));
							map.put(KEY_BODY, parser.getValue(e, KEY_BODY));
							map.put(KEY_CREATED, parser.getValue(e, KEY_CREATED));
							map.put(KEY_INTROIMAGE, parser.getValue(e, KEY_INTROIMAGE));
							
					         title = parser.getValue(e, KEY_TITLE);
							 intro = parser.getValue(e, KEY_INTROTEXT);
							 body = parser.getValue(e, KEY_BODY);
							 created = parser.getValue(e, KEY_CREATED);
							 introimage = parser.getValue(e, KEY_INTROIMAGE);
							
						
							
							menuItems3.add(map);
				}  
				
				}
				
				
				 text.setText(title);
 	           
				
				
				b1.setOnClickListener(new View.OnClickListener() {
					
					
					
                    public void onClick(View v) {

                        //v.getId() will give you the image id
                    	
                    	// getting values from selected ListItem
        			       				
                    	
                    	// Starting new intent
						Intent in = new Intent(getApplicationContext(), FeaturedNewsActivity.class);
						in.putExtra(KEY_TITLE, title);
						in.putExtra(KEY_INTROTEXT, intro);
						in.putExtra(KEY_BODY, body);
						in.putExtra(KEY_CREATED, created);
						in.putExtra(KEY_INTROIMAGE, introimage);
						
						
							startActivity(in);

                    }
                });
				
	    	        }
				
				else  {
					ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
				

				final XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(URL); // getting XML
				Document doc = parser.getDomElement(xml); // getting DOM element
		if (doc != null) {
				NodeList nl = doc.getElementsByTagName(KEY_ARTICLE);

				// looping through all item nodes <item>
				for (int i = 0; i < 1; i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_INTROTEXT, parser.getValue(e, KEY_INTROTEXT));
					map.put(KEY_BODY, parser.getValue(e, KEY_BODY));
					map.put(KEY_CREATED, parser.getValue(e, KEY_CREATED));
					map.put(KEY_INTROIMAGE, parser.getValue(e, KEY_INTROIMAGE));
					
			         title = parser.getValue(e, KEY_TITLE);
					 intro = parser.getValue(e, KEY_INTROTEXT);
					 body = parser.getValue(e, KEY_BODY);
					 created = parser.getValue(e, KEY_CREATED);
					 introimage = parser.getValue(e, KEY_INTROIMAGE);
					
					
					
					menuItems.add(map);
		}  
		
		}
		
		
		 text.setText(title);
	   
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			
			
	        public void onClick(View v) {

	            //v.getId() will give you the image id
	        	
	        	// getting values from selected ListItem
			       				
	        	
	        	// Starting new intent
				Intent in = new Intent(getApplicationContext(), FeaturedNewsActivity.class);
				in.putExtra(KEY_TITLE, title);
				in.putExtra(KEY_INTROTEXT, intro);
				in.putExtra(KEY_BODY, body);
				in.putExtra(KEY_CREATED, created);
				in.putExtra(KEY_INTROIMAGE, introimage);
				
				
					startActivity(in);

	        }
	    });
	    	    
				}
	    	        
	                
	            }
	        

	            @Override
	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	            }

	            @Override
	            public void onPageScrollStateChanged(int state) {
	            }
	        });
	        
	       
	     
	}
	
	
	

	private int imageArra[] = { R.drawable.featured4, R.drawable.featured1,
			R.drawable.featured3, R.drawable.featured2
			 };
	private String[] stringArray = new String[] { "Image a", "Image b","Image c","Image d"};

	
	
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
	
	case R.id.tournaments:
	tournaments();
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


	private void tournaments() {
	// TODO Auto-generated method stub
	startActivity (new Intent(getApplicationContext(), Tournaments.class));

	}


	private void homemenu() 

	{
	//TODO Auto-generated method stub
	startActivity (new Intent(getApplicationContext(), Home.class));


	}
	private void preferences() {
	//TODO Auto-generated method stub
	startActivity (new Intent(getApplicationContext(), PushPreferencesActivity.class));
	}

	private void players() {
	//TODO Auto-generated method stub

	startActivity (new Intent(getApplicationContext(), Players.class));
	}



	private void courses() {
	//TODO Auto-generated method stub
	startActivity (new Intent(getApplicationContext(), Courses.class));


	}

	}
