package com.facebook.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class FeaturedNewsActivity  extends Activity {
	
	// XML node keys
	
	
	static final String KEY_TITLE = "title";
	static final String KEY_INTROTEXT = "introtext";
	static final String KEY_BODY = "body";
	static final String KEY_CREATED = "created";
	static final String KEY_INTROIMAGE = "intro_image";
	
		String DetailText;
		
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	WebView wv;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.featurednews);
        
        wv = (WebView) findViewById(R.id.webView1);
      
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        // getting intent data
        Intent in = getIntent(); 
        
        // Get XML values from previous intent    
        
    	String title = in.getStringExtra(KEY_TITLE);
		String intro = in.getStringExtra(KEY_INTROTEXT);
		String body = in.getStringExtra(KEY_BODY);
		String created = "Date Created:  " + in.getStringExtra(KEY_CREATED);
		String introimage = in.getStringExtra(KEY_INTROIMAGE);
	
		
        
      tv1.setText(title);
      tv2.setText(intro);
      tv3.setText(created);
      tv4.setText(body);
      
      DetailText =  "<html>" + "<img src=" + introimage + " align='center' width='100%' height='100%' />"  + "</html>";
        
      wv.loadData(DetailText, "text/html", "UTF-8");


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
