/**
 * Copyright 2010-present Facebook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.android;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;

@SuppressWarnings("deprecation")
public class Hackbook extends Activity implements OnItemClickListener {

    /*
     * Your Facebook Application ID must be set before running this example See
     * http://www.facebook.com/developers/createapp.php
     */
    public static final String APP_ID = "579212538756274";

    private LoginButton mLoginButton;
    private TextView mText;
    private ImageView mUserPic;
    private Handler mHandler;
    ProgressDialog dialog;

    final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
    final static int PICK_EXISTING_PHOTO_RESULT_CODE = 1;
    
    static final String KEY_TNAME = "name";
	static final String KEY_LATITUDE = "lat";
	static final String KEY_LONGITUDE = "lon";

    String name = null;
   String lat12 = null;
    String long12 = null;
    
    
    private ListView list;
    String[] main_items = { "Update Status", "Place Check-in", };
    String[] permissions = { "offline_access", "publish_stream", "user_photos", "publish_checkins",
            "photo_upload" };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
 Intent in = getIntent();
        
       name = in.getStringExtra(KEY_TNAME);
        lat12 = in.getStringExtra(KEY_LATITUDE);
        long12 = in.getStringExtra(KEY_LONGITUDE);
        
        System.out.println(name);
        System.out.println(lat12);
        System.out.println(long12);
        
        

        if (APP_ID == null) {
            Util.showAlert(this, "Warning", "Facebook Applicaton ID must be "
                    + "specified before running this example: see FbAPIs.java");
            return;
        }

        setContentView(R.layout.main2);
        mHandler = new Handler();

        mText = (TextView) Hackbook.this.findViewById(R.id.txt);
        mUserPic = (ImageView) Hackbook.this.findViewById(R.id.user_pic);

        // Create the Facebook Object using the app id.
        Utility.mFacebook = new Facebook(APP_ID);
        // Instantiate the asynrunner object for asynchronous api calls.
        Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);

        mLoginButton = (LoginButton) findViewById(R.id.login);

        // restore session if one exists
        SessionStore.restore(Utility.mFacebook, this);
        SessionEvents.addAuthListener(new FbAPIsAuthListener());
        SessionEvents.addLogoutListener(new FbAPIsLogoutListener());

        /*
         * Source Tag: login_tag
         */
        mLoginButton.init(this, AUTHORIZE_ACTIVITY_RESULT_CODE, Utility.mFacebook, permissions);

        if (Utility.mFacebook.isSessionValid()) {
            requestUserData();
        }

        list = (ListView) findViewById(R.id.main_list);

        list.setOnItemClickListener(this);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.main_list_item, main_items));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Utility.mFacebook != null) {
            if (!Utility.mFacebook.isSessionValid()) {
                mText.setText("You are logged out! ");
                mUserPic.setImageBitmap(null);
            } else {
                Utility.mFacebook.extendAccessTokenIfNeeded(this, null);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        /*
         * if this is the activity result from authorization flow, do a call
         * back to authorizeCallback Source Tag: login_tag
         */
            case AUTHORIZE_ACTIVITY_RESULT_CODE: {
                Utility.mFacebook.authorizeCallback(requestCode, resultCode, data);
                break;
            }
            /*
             * if this is the result for a photo picker from the gallery, upload
             * the image after scaling it. You can use the Utility.scaleImage()
             * function for scaling
             */
            case PICK_EXISTING_PHOTO_RESULT_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    Uri photoUri = data.getData();
                    if (photoUri != null) {
                        Bundle params = new Bundle();
                        try {
                            params.putByteArray("photo",
                                    Utility.scaleImage(getApplicationContext(), photoUri));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                      
                               
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error selecting image from the gallery.", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No image selected for upload.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        switch (position) {
        /*
         * Source Tag: update_status_tag Update user's status by invoking the
         * feed dialog To post to a friend's wall, provide his uid in the 'to'
         * parameter Refer to
         * https://developers.facebook.com/docs/reference/dialogs/feed/ for more
         * info.
         */
            case 0: {
                Bundle params = new Bundle();
                params.putString("caption", getString(R.string.app_name));
                params.putString("description", getString(R.string.app_desc));
                params.putString("picture", Utility.HACK_ICON_URL);
                params.putString("name", getString(R.string.app_action));

                Utility.mFacebook.dialog(Hackbook.this, "feed", params, new UpdateStatusListener());
                String access_token = Utility.mFacebook.getAccessToken();
                System.out.println(access_token);
                break;
            }

            /*
             * Source Tag: app_requests Send an app request to friends. If no
             * friend is specified, the user will see a friend selector and will
             * be able to select a maximum of 50 recipients. To send request to
             * specific friend, provide the uid in the 'to' parameter Refer to
             * https://developers.facebook.com/docs/reference/dialogs/requests/
             * for more info.
             */
            
            /*
             * Source Tag: friends_tag You can get friends using
             * graph.facebook.com/me/friends, this returns the list sorted by
             * UID OR using the friend table. With this you can sort the way you
             * want it.
             * Friend table - https://developers.facebook.com/docs/reference/fql/friend/
             * User table - https://developers.facebook.com/docs/reference/fql/user/
            
            /*
             * Source Tag: upload_photo You can upload a photo from the media
             * gallery or from a remote server How to upload photo:
             * https://developers.facebook.com/blog/post/498/
          
            /*
             * User can check-in to a place, you require publish_checkins
             * permission for that. You can use the default Times Square
             * location or fetch user's current location. Get user's checkins:
             * https://developers.facebook.com/docs/reference/api/checkin/
             */
            case 1: {
                final Intent myIntent = new Intent(getApplicationContext(), Places.class);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.get_location)
                        .setMessage("Get current location or check-in at " + name)
                        .setPositiveButton(name, new DialogInterface.OnClickListener() {
                        	                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        myIntent.putExtra("LOCATION", name);
                                        myIntent.putExtra("KEY_LATITUDE", lat12);
                                        myIntent.putExtra("KEY_LONGITUDE", long12);
                                        startActivity(myIntent);
                                    }

                                }).show();
                break;
            }

          
           
            
        }
    }

    /*
     * callback for the feed dialog which updates the profile status
     */
    public class UpdateStatusListener extends BaseDialogListener {
        @Override
        public void onComplete(Bundle values) {
            final String postId = values.getString("post_id");
            if (postId != null) {
                Intent back_to_tournaments = new Intent(Hackbook.this, Tournaments.class);
                startActivity(back_to_tournaments);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "No wall post made",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onFacebookError(FacebookError error) {
            Toast.makeText(getApplicationContext(), "Facebook Error: " + error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast toast = Toast.makeText(getApplicationContext(), "Update status cancelled",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*
     * callback for the apprequests dialog which sends an app request to user's
     * friends.
     */
    public class AppRequestsListener extends BaseDialogListener {
        @Override
        public void onComplete(Bundle values) {
            Toast toast = Toast.makeText(getApplicationContext(), "App request sent",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public void onFacebookError(FacebookError error) {
            Toast.makeText(getApplicationContext(), "Facebook Error: " + error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast toast = Toast.makeText(getApplicationContext(), "App request cancelled",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*
     * callback after friends are fetched via me/friends or fql query.
     */
 

    /*
     * callback for the photo upload
     */
   

     
    /*
     * Callback for fetching current user's name, picture, uid.
     */
    public class UserRequestListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);

                final String picURL = jsonObject.getJSONObject("picture")
                        .getJSONObject("data").getString("url");
                final String name = jsonObject.getString("name");
                Utility.userUID = jsonObject.getString("id");

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mText.setText("Welcome " + name + "!");
                        mUserPic.setImageBitmap(Utility.getBitmap(picURL));
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * The Callback for notifying the application when authorization succeeds or
     * fails.
     */

    public class FbAPIsAuthListener implements AuthListener {

        @Override
        public void onAuthSucceed() {
            requestUserData();
        }

        @Override
        public void onAuthFail(String error) {
            mText.setText("Login Failed: " + error);
        }
    }

    /*
     * The Callback for notifying the application when log out starts and
     * finishes.
     */
    public class FbAPIsLogoutListener implements LogoutListener {
        @Override
        public void onLogoutBegin() {
            mText.setText("Logging out...");
        }

        @Override
        public void onLogoutFinish() {
            mText.setText("You have logged out! ");
            mUserPic.setImageBitmap(null);
        }
    }

    /*
     * Request user name, and picture to show on the main screen.
     */
    public void requestUserData() {
        mText.setText("Fetching user name, profile pic...");
        Bundle params = new Bundle();
        params.putString("fields", "name, picture");
        Utility.mAsyncRunner.request("me", params, new UserRequestListener());
    }

    /**
     * Definition of the list adapter
     */
    public class MainListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MainListAdapter() {
            mInflater = LayoutInflater.from(Hackbook.this.getBaseContext());
        }

        @Override
        public int getCount() {
            return main_items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View hView = convertView;
            if (convertView == null) {
                hView = mInflater.inflate(R.layout.main_list_item, null);
                ViewHolder holder = new ViewHolder();
                holder.main_list_item = (TextView) hView.findViewById(R.id.main_api_item);
                hView.setTag(holder);
            }

            ViewHolder holder = (ViewHolder) hView.getTag();

            holder.main_list_item.setText(main_items[position]);

            return hView;
        }

    }

    class ViewHolder {
        TextView main_list_item;
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

