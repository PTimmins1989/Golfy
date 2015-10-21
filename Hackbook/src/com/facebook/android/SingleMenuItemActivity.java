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

public class SingleMenuItemActivity  extends Activity {
	
	// XML node keys
	static final String KEY_NAME = "name";
	static final String KEY_PIC_URL = "image";
	static final String KEY_DOB = "dob";
	static final String KEY_WEIGHT = "weight";
	static final String KEY_HEIGHT = "height";
	static final String KEY_HANDED = "handed";
	static final String KEY_COUNTRY = "country";
	static final String KEY_COLLEGE = "college";
	static final String KEY_BIO = "bio";
	static final String KEY_FACEBOOK = "facebook";
	static final String KEY_TWITTER = "twitter";
	static final String KEY_WEBSITE = "website";
	
	String DetailText;
	WebView wb;
	ImageButton b1;
	ImageButton b2;
	ImageButton b3;
	
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
        String name = in.getStringExtra(KEY_NAME);
        String picurl = in.getStringExtra(KEY_PIC_URL);
        String dob = in.getStringExtra(KEY_DOB);
        String weight = in.getStringExtra(KEY_WEIGHT);
        String height = in.getStringExtra(KEY_HEIGHT);
        String handed = in.getStringExtra(KEY_HANDED);
        String country = in.getStringExtra(KEY_COUNTRY);
        String college = in.getStringExtra(KEY_COLLEGE);
        String bio = in.getStringExtra(KEY_BIO);
        final String facebook = in.getStringExtra(KEY_FACEBOOK);
		final String twitter = in.getStringExtra(KEY_TWITTER);
		final String website = in.getStringExtra(KEY_WEBSITE);
        
        
        DetailText = "<html>" + "<body bgcolor='#FFFFFF' style='margin-left: 20px; margin-right: 20px' background='http://54.235.75.120/images/grass.jpg' >" + "<FONT FACE= 'Arial'>" + "<div class='blog-featured' align='center' >" + 
        "<div class='blog-player' style='border: 10px solid #ACACAC; background-color:#DEDEDE;'><h2>" + name + "</h2></div>" +  "</div>" + "<div class='blogfeature' align='center'>" + "<img src='" + picurl + "' align='center' />" + 
        		"<div class='profile-cover' style='border: 10px solid #ACACAC; background-color:#DEDEDE;  padding: 10px; margin 10px;'  align='center'>" + "<table>" +
        "<tbody>" + "<tr><th>Born</th><td>" + dob + "</td></tr>" + 
        "<tr><th>Weight</th><td>" + weight + "</td></tr>" + 
        "<tr><th>Height</th><td>" + height + "</td></tr>" + 
        "<tr><th>Handed</th><td>" + handed + "</td></tr>" + 
        "<tr><th>Country</th><td>" + country + "</td></tr>" + 
        "<tr><th>College</th><td>" + college + "</td></tr>" +
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
