package com.facebook.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

public class TournamentsSingleMenuItemActivity  extends Activity {
	
	// XML node keys
	
	
	
	static final String KEY_TNAME = "name";
	static final String KEY_FROMDATE = "fromdate";
	static final String KEY_TODATE = "todate";
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
	
	
	static final String KEY_COURSE = "course";
	static final String KEY_LATITUDE = "latitude";
	static final String KEY_LONGITUDE = "longitude";
	
	ImageButton b1;
	ImageButton b2;
	ImageButton b3;
	
	String DetailText;
	WebView wb;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        WebView wb = (WebView) findViewById(R.id.webView1);
        
        b1 = (ImageButton) findViewById(R.id.imageButton1);        
        b2 = (ImageButton) findViewById(R.id.imageButton2);
        b3 = (ImageButton) findViewById(R.id.imageButton3);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get XML values from previous intent
    
        
    	String name = in.getStringExtra(KEY_TNAME);
		String fdate = in.getStringExtra(KEY_FROMDATE);
		String tdate = in.getStringExtra(KEY_TODATE);
		String cholder = in.getStringExtra(KEY_CURRENTHOLDER);
		String major = in.getStringExtra(KEY_MAJOR);
		String recordscore = in.getStringExtra(KEY_RECORDSCORE);
		String sponsor = in.getStringExtra(KEY_SPONSOR);
		String prizefund = in.getStringExtra(KEY_PRIZEFUND);
		String bio = in.getStringExtra(KEY_BIO);
		final String facebook = in.getStringExtra(KEY_FACEBOOK);
		final String twitter = in.getStringExtra(KEY_TWITTER);
		final String website = in.getStringExtra(KEY_WEBSITE);
		String tourimage = in.getStringExtra(KEY_TOUR_IMAGE);
		
        
        if (cholder.contains("4") ){
        	cholder = "Bubba Watson";
        }
        
        
        DetailText = "<html>" + "<body bgcolor='#FFFFFF' style='margin-left: 20px; margin-right: 20px' background='http://54.235.75.120/images/grass.jpg' >" + "<FONT FACE= 'Arial'>" + "<div class='blog-featured' align='center' >" + 
        "<div class='blog-player' style='border: 10px solid #ACACAC; background-color:#DEDEDE;'><h2>" + name + "</h2></div>" +  "</div>" + "<div class='blogfeature' align='center'>" + "<img src=" + tourimage + " align='center' width='250' height='200' />" + 
        		"<div class='profile-cover' style='border: 10px solid #ACACAC; background-color:#DEDEDE;  padding: 10px; margin 10px;'  align='center'>" + "<table>" +
        "<tbody>" + "<tr><th>Major</th><td>" + major + "</td></tr>" + 
        "<tr><th>Record Score</th><td>" + recordscore + "</td></tr>" + 
        "<tr><th>Sponsor</th><td>" + sponsor + "</td></tr>" + 
        "<tr><th>Prizefund</th><td>" + prizefund + "</td></tr>" + 
        "<tr><th>Start Date</th><td>" + fdate + "</td></tr>" + 
        "<tr><th>Current Holder</th><td>" + cholder + "</td></tr>" + 
        "</tbody></table></div>" + "<div class='player' align='left'>"  + 
        "<div class='nav' align='left' style='border: 10px solid #ACACAC; background-color:#DEDEDE;  padding: 10px; margin 10px'>" + "<h3>Bio</h3>" + bio + "</div>" + "</div>" + "</div>" + "</FONT>" + "</body>" + "</html>";
        
        wb.loadData(DetailText, "text/html", "UTF-8");
        
        
        
        
        
        
        b1.setOnClickListener(new View.OnClickListener()  {
          	 @Override
               public void onClick(View view) {
          		 Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
          		  myWebLink.setData(Uri.parse(facebook));
                       startActivity(myWebLink);
               }
           });

        b2.setOnClickListener(new View.OnClickListener()  {
         	 @Override
              public void onClick(View view) {
         		 Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
         		  myWebLink.setData(Uri.parse(twitter));
                      startActivity(myWebLink);
              }
          });

        b3.setOnClickListener(new View.OnClickListener()  {
         	 @Override
              public void onClick(View view) {
         		 Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
         		  myWebLink.setData(Uri.parse(website));
                      startActivity(myWebLink);
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
