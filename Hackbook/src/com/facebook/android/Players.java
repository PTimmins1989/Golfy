package com.facebook.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Players extends Activity
{
	
	private EditText et;
	 // All static variables
    static final String URL = "http://54.235.75.120/webservice/players.php";
    // XML node keys
	static final String KEY_PLAYER = "player"; // parent node
	static final String KEY_NAME = "name";
	static final String KEY_DOB = "dob";
	static final String KEY_WEIGHT = "weight";
	static final String KEY_HEIGHT = "height";
	static final String KEY_HANDED = "handed";
	static final String KEY_COUNTRY = "country";
	static final String KEY_BIO = "bio";
	static final String KEY_PIC_URL = "image";
	static final String KEY_PLAYERID = "playerid";
	static final String KEY_TURNEDPRO = "turnedpro";
	static final String KEY_FACEBOOK = "facebook";
	static final String KEY_TWITTER = "twitter";
	static final String KEY_WEBSITE = "website";
	static final String KEY_COLLEGE = "college";

	
	
	
	ListView list;
    LazyAdapter adapter;
  

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainplayers);
		
		
		StrictMode.ThreadPolicy tourpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(tourpolicy); 
		
		et = (EditText) findViewById(R.id.PlayerSearchEditText);
		
		
		
 final List<HashMap<String, String>> playerListcopy  = new ArrayList<HashMap<String, String>>();;
	 final ArrayList<HashMap<String, String>> playerList = new ArrayList<HashMap<String, String>>();
		
		

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element
		
		NodeList nl = doc.getElementsByTagName(KEY_PLAYER);
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) 
		{
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			 // adding each child node to HashMap key => value
            map.put(KEY_NAME, parser.getValue(e, KEY_NAME));

           // String play = parser.getValue(e, KEY_NAME);
            
            //listview_array.add(play);
            
          //  listview_array[listview_array_index] = play;
         //   listview_array_index++;
           
           
            map.put(KEY_DOB, parser.getValue(e, KEY_DOB));
            map.put(KEY_WEIGHT, parser.getValue(e, KEY_WEIGHT));
            map.put(KEY_HEIGHT, parser.getValue(e, KEY_HEIGHT));
            map.put(KEY_HANDED, parser.getValue(e, KEY_HANDED));
            map.put(KEY_COUNTRY, parser.getValue(e, KEY_COUNTRY));
            map.put(KEY_BIO, parser.getValue(e, KEY_BIO));
            map.put(KEY_COLLEGE, parser.getValue(e, KEY_COLLEGE));
            map.put(KEY_FACEBOOK, parser.getValue(e, KEY_FACEBOOK));
			map.put(KEY_TWITTER, parser.getValue(e, KEY_TWITTER));
			map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));
			
            map.put(KEY_PIC_URL, parser.getValue(e, KEY_PIC_URL));
			

			// adding HashList to ArrayList
			playerList.add(map);
			
			
			
		}
		

		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, playerList);        
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        
        
        for (HashMap<String, String> map  : playerList)
        {
        	 playerListcopy.add(map);
          
       
        }
                
        
        et.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                playerList.clear();
                String search=s.toString();
                for(HashMap<String, String> map : playerListcopy)
                {
                    if(map.get("name").toLowerCase().contains(search.toLowerCase()))
                        playerList.add(map);
                    adapter.notifyDataSetChanged();
                }
            }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				System.out.println("It worked");
			};
          //  ... other overridden methods can go here ...
        });
        
        
       
       /* for( int j=0; j < listview_array.length; j++ )
        {  
              System.out.println("listview_array["+j+"] is "+ listview_array[j]  );
        }
        
        
        et.addTextChangedListener(new TextWatcher()
        {
	        public void afterTextChanged(Editable s)
	        {
	                                                                        // Abstract Method of TextWatcher Interface.
	        }
	        
	        
	        
	        public void beforeTextChanged(CharSequence s,int start, int count, int after)
			 {
			        // Abstract Method of TextWatcher Interface.
			  }
			        
			        
			        
			 public void onTextChanged(CharSequence s,int start, int before, int count)
			 {
			        textlength = et.getText().length();
			        
			        array_sort.clear();
			        
			        for (int i = 0; i < listview_array.length; i++)
			        {
			        	if (textlength <= listview_array[i].length())
			        	{
			        	    if(et.getText().toString().equalsIgnoreCase( (String)listview_array[i].subSequence(0,textlength)))
			                {
			                                      array_sort.add(listview_array[i]);
			                 }
			             }
			        }
			        
			        
			        list.setAdapter(new ArrayAdapter<String> ( Players.this , android.R.layout.simple_list_item_1, array_sort) );  
			        		
			  }
		 
		 
		  }              
        );
        */
        
        
        
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							

				// getting values from selected ListItem
	         	String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String picurl = ((TextView) view.findViewById(R.id.picurl)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.dob)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.weight)).getText().toString();
				String height = ((TextView) view.findViewById(R.id.height)).getText().toString();
				String handed = ((TextView) view.findViewById(R.id.handed)).getText().toString();
				String country = ((TextView) view.findViewById(R.id.country)).getText().toString();
				String college = ((TextView) view.findViewById(R.id.college)).getText().toString();
				String bio = ((TextView) view.findViewById(R.id.bio)).getText().toString();
				String facebook = ((TextView) view.findViewById(R.id.facebook)).getText().toString();
				String twitter = ((TextView) view.findViewById(R.id.twitter)).getText().toString();
				String website = ((TextView) view.findViewById(R.id.website)).getText().toString();
				
			
				
				
			
				
				
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_NAME, name);
				in.putExtra(KEY_PIC_URL, picurl);
				in.putExtra(KEY_DOB, cost);
				in.putExtra(KEY_WEIGHT, description);
				in.putExtra(KEY_HEIGHT, height);
				in.putExtra(KEY_HANDED, handed);
				in.putExtra(KEY_COUNTRY, country);
				in.putExtra(KEY_BIO, bio);
				in.putExtra(KEY_COLLEGE, college);
				in.putExtra(KEY_FACEBOOK, facebook);
				in.putExtra(KEY_TWITTER, twitter);
				in.putExtra(KEY_WEBSITE, website);
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