package com.facebook.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Tournaments extends ListActivity {

	private EditText et;
	// All static variables
	static final String URL = "http://54.235.75.120/webservice/tournaments.php";
	// XML node keys
	static final String KEY_TOURNAMENTS = "tournaments"; // parent node
	static final String KEY_TOURNAMENT = "tournament";
	static final String KEY_TNAME = "name"; // <name>The Masters 2013</name>
	static final String KEY_FROMDATE = "fromdate";
	static final String KEY_TODATE = "todate"; // <todate>14th Apr 2013</todate>
	static final String KEY_TOUR_ID = "tourid";
	static final String KEY_CURRENTHOLDER = "currentholderid";
	static final String KEY_MAJOR = "major";
	static final String KEY_RECORDSCORE = "recordscore";
	static final String KEY_SPONSOR = "sponsor"; // <sponsor>IBM</sponsor>
	static final String KEY_PRIZEFUND = "prizefund"; // <prizefund>8,000,000</prizefund>
	static final String KEY_BIO = "bio";
	static final String KEY_FACEBOOK = "facebook";
	static final String KEY_TWITTER = "twitter";
	static final String KEY_WEBSITE = "website";
	static final String KEY_TOUR_IMAGE = "image";
	static final String KEY_YOUTUBE = "youtube";
	static final String KEY_BANNER = "banner";
	static final String KEY_TOURNAMENT_ID = "tournamentId";

	static final String KEY_COURSE = "course";
	static final String KEY_LATITUDE = "lat";
	static final String KEY_LONGITUDE = "lon";

	double latitude = 0;
	double longitude = 0;

	// GPSTracker class
	GPSTracker gps;

	final Context context = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tourmain);

		// let us print all the values available in list

		StrictMode.ThreadPolicy tourpolicy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(tourpolicy);

		gps = new GPSTracker(Tournaments.this);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		et = (EditText) findViewById(R.id.PlayerSearchEditText);

		final List<HashMap<String, String>> tournamentItemscopy = new ArrayList<HashMap<String, String>>();
		;

		final ArrayList<HashMap<String, String>> tournamentItems = new ArrayList<HashMap<String, String>>();

		final XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
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

				final String name = parser.getValue(e, KEY_TNAME);

				map.put(KEY_FROMDATE, parser.getValue(e, KEY_FROMDATE));
				map.put(KEY_TODATE, parser.getValue(e, KEY_TODATE));
				map.put(KEY_TOUR_ID, parser.getValue(e, KEY_TOUR_ID));
				map.put(KEY_CURRENTHOLDER,parser.getValue(e, KEY_CURRENTHOLDER));
				map.put(KEY_MAJOR, parser.getValue(e, KEY_MAJOR));

				map.put(KEY_RECORDSCORE, parser.getValue(e, KEY_RECORDSCORE));
				map.put(KEY_SPONSOR, parser.getValue(e, KEY_SPONSOR));
				map.put(KEY_PRIZEFUND, parser.getValue(e, KEY_PRIZEFUND));
				map.put(KEY_BIO, parser.getValue(e, KEY_BIO));
				map.put(KEY_FACEBOOK, parser.getValue(e, KEY_FACEBOOK));
				map.put(KEY_TWITTER, parser.getValue(e, KEY_TWITTER));
				map.put(KEY_WEBSITE, parser.getValue(e, KEY_WEBSITE));

				map.put(KEY_TOUR_IMAGE, parser.getValue(e, KEY_TOUR_IMAGE));
				map.put(KEY_BANNER, parser.getValue(e, KEY_BANNER));
				map.put(KEY_TOURNAMENT_ID,parser.getValue(e, KEY_TOURNAMENT_ID));

				map.put(KEY_LATITUDE, parser.getValue(e, KEY_LATITUDE));
				map.put(KEY_LONGITUDE, parser.getValue(e, KEY_LONGITUDE));

				final String lat1 = parser.getValue(e, KEY_LATITUDE);

				final String long1 = parser.getValue(e, KEY_LONGITUDE);

				// check if GPS enabled
				if (gps.canGetLocation()) {

					latitude = gps.getLatitude();
					longitude = gps.getLongitude();

					double x = latitude / 1;
					for (int n = 2; n < 3; n++) {
						System.out.println(format(x, n, -6));
						String lat12 = format(x, n, -6);

						if (lat12.contains(lat1)) {
							System.out.println("it worked " + name);
							System.out.println("it worked " + lat1);
							System.out.println("it worked " + long1);

							// set title
							alertDialogBuilder.setTitle("Facebook!");

							// set dialog message
							alertDialogBuilder
									.setMessage(
											"You are near the "
													+ name
													+ " tournament. Would you like to check in?")
									.setCancelable(false)
									.setPositiveButton(
											R.drawable.facebook_checkin,
											new DialogInterface.OnClickListener() {

												public void onClick(
														DialogInterface dialog,
														int id) {
													// if this button is
													// clicked, close
													// current activity
													Intent in = new Intent(
															getApplicationContext(),
															Hackbook.class);
													in.putExtra(KEY_TNAME, name);

													in.putExtra(KEY_LATITUDE,
															lat1);

													in.putExtra(KEY_LONGITUDE,
															long1);
													startActivity(in);
												}

											})
									.setNegativeButton(
											"        No Thanks        ",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// if this button is
													// clicked, just close
													// the dialog box and do
													// nothing
													dialog.cancel();
												}
											});
							//
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder
									.create();

							// show it
							alertDialog.show();

							Button button0 = alertDialog
									.getButton(AlertDialog.BUTTON_POSITIVE);
							button0.setCompoundDrawablesWithIntrinsicBounds(
									this.getResources().getDrawable(
											R.drawable.facebook_checkin_small),
									null, null, null);
							button0.setText("");

						}
					}

				}

				// adding HashList to ArrayList
				tournamentItems.add(map);
			}

		}

		// Adding menuItems to ListView
		final ListAdapter adapter = new SimpleAdapter(this, tournamentItems,
				R.layout.tour_list_item,
				new String[] { KEY_TNAME, KEY_FROMDATE, KEY_TODATE,
						KEY_CURRENTHOLDER, KEY_MAJOR, KEY_RECORDSCORE,
						KEY_SPONSOR, KEY_PRIZEFUND, KEY_BIO, KEY_FACEBOOK,
						KEY_TWITTER, KEY_WEBSITE, KEY_TOUR_IMAGE, }, new int[] {
						R.id.name, R.id.fromdate, R.id.todate,
						R.id.currentholder, R.id.major, R.id.recordscore,
						R.id.sponsor, R.id.prizefund, R.id.bio, R.id.facebook,
						R.id.twitter, R.id.website, R.id.tourimage });

		setListAdapter(adapter);

		// selecting single ListView item

		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		for (HashMap<String, String> map : tournamentItems) {
			tournamentItemscopy.add(map);
			System.out.println(map);

		}

		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				tournamentItems.clear();
				String search = s.toString();
				for (HashMap<String, String> map : tournamentItemscopy) {
					if (map.get("name").toLowerCase()
							.contains(search.toLowerCase()))
						tournamentItems.add(map);
					((BaseAdapter) adapter).notifyDataSetChanged();
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
			// ... other overridden methods can go here ...
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String fdate = ((TextView) view.findViewById(R.id.fromdate))
						.getText().toString();
				String tdate = ((TextView) view.findViewById(R.id.todate))
						.getText().toString();
				String cholder = ((TextView) view
						.findViewById(R.id.currentholder)).getText().toString();
				String major = ((TextView) view.findViewById(R.id.major))
						.getText().toString();
				String recordscore = ((TextView) view
						.findViewById(R.id.recordscore)).getText().toString();
				String sponsor = ((TextView) view.findViewById(R.id.sponsor))
						.getText().toString();
				String prizefund = ((TextView) view
						.findViewById(R.id.prizefund)).getText().toString();
				String bio = ((TextView) view.findViewById(R.id.bio)).getText()
						.toString();
				String facebook = ((TextView) view.findViewById(R.id.facebook))
						.getText().toString();
				String twitter = ((TextView) view.findViewById(R.id.twitter))
						.getText().toString();
				String website = ((TextView) view.findViewById(R.id.website))
						.getText().toString();
				String tourimage = ((TextView) view
						.findViewById(R.id.tourimage)).getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						TournamentsSingleMenuItemActivity.class);
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

	}

	static final String ZEROES = "000000000000";
	static final String BLANKS = "            ";

	/**
	 * 
	 * @param val
	 * @param n
	 * @param w
	 * @return
	 */
	static String format(double val, int n, int w) {
		// rounding
		double incr = 0.5;
		for (int j = n; j > 0; j--)
			incr /= 10;
		val += incr;

		String s = Double.toString(val);
		int n1 = s.indexOf('.');
		int n2 = s.length() - n1 - 1;

		if (n > n2)
			s = s + ZEROES.substring(0, n - n2);
		else if (n2 > n)
			s = s.substring(0, n1 + n + 1);

		if (w > 0 & w > s.length())
			s = BLANKS.substring(0, w - s.length()) + s;
		else if (w < 0 & (-w) > s.length()) {
			w = -w;
			s = s + BLANKS.substring(0, w - s.length());
		}
		return s;

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
		startActivity(new Intent(getApplicationContext(), Home.class));

	}

	private void preferences() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(),
				PushPreferencesActivity.class));
	}

	private void players() {
		// TODO Auto-generated method stub

		startActivity(new Intent(getApplicationContext(), Players.class));
	}

	private void courses() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), Courses.class));

	}

}
