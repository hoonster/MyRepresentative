package com.example.hoon.myrepresentative;

/**
 * Created by hoon on 9/18/2015.
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListRepActivity extends Activity {

    String str = null;
    Context context = this;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /** The parsing of the xml data is done in a non-ui thread */
        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

        /** Start parsing xml data */
        listViewLoaderTask.execute();
        //Log.e("URL",str);
        Log.e("@@@", "111");
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

        /**
         * Doing the parsing of xml data in a non-ui thread
         */
        @Override
        protected SimpleAdapter doInBackground(String... url) {
            Log.e("@@@", "222");
            Intent intent = getIntent();
            String st = intent.getStringExtra("State");

            try {
                Log.e("@@@", "333");
                String url1 = "http://whoismyrepresentative.com/getall_reps_bystate.php?state=" + st;
                URL url_new = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) url_new.openConnection();
                connection.setRequestMethod("GET");
                BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
                str = convertStreamToString(inputStream);
            } catch (MalformedURLException me) {
                System.out.println("MalformedURLException: " + me);
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe);
            }
            Log.e("@@@", "444");
            StringReader reader = new StringReader(str);
            System.out.print("READER" + reader);
            XmlParser countryXmlParser = new XmlParser();
            System.out.print("countryXmlParser" + countryXmlParser);
            List<HashMap<String, String>> countries = null;

            try {
                Log.e("@@@", "555");
                /** Getting the parsed data as a List construct */
                countries = countryXmlParser.parse(reader);
                Log.e("@@@", "666");
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            /** Keys used in Hashmap */
            String[] from = {"Name", "Party", "State", "District", "Phone", "Office", "Link"  };

            /** Ids of views in listview_layout */
            int[] to = {R.id.tv_name, R.id.tv_party, R.id.tv_state, R.id.tv_district, R.id.tv_phone, R.id.tv_office, R.id.tv_link};

            /** Instantiating an adapter to store each items
             *  R.layout.listview_layout defines the layout of each item
             */
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.lv_layout, from, to);

            return adapter;
        }

        @Override
        protected void onPostExecute(SimpleAdapter adapter) {

            Log.e("@@@", "777");
            /** Getting a reference to listview of main.xml layout file */
            lv = (ListView) findViewById(R.id.lv_countries);

            /** Setting the adapter containing the country list to listview */
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Object listItem = lv.getItemAtPosition(position);

                    // getting values from selected ListItem
                    String name = ((TextView) view.findViewById(R.id.tv_name)).getText().toString();
                    String party = ((TextView) view.findViewById(R.id.tv_party)).getText().toString();
                    String state = ((TextView) view.findViewById(R.id.tv_state)).getText().toString();
                    String district = ((TextView) view.findViewById(R.id.tv_district)).getText().toString();
                    String phone = ((TextView) view.findViewById(R.id.tv_phone)).getText().toString();
                    String office = ((TextView) view.findViewById(R.id.tv_office)).getText().toString();
                    String link = ((TextView) view.findViewById(R.id.tv_link)).getText().toString();

                    Intent i = new Intent(ListRepActivity.this,
                            ProfileActivity.class);
                    i.putExtra("iName", name);
                    i.putExtra("iParty", party);
                    i.putExtra("iState", state);
                    i.putExtra("iDistrict", district);
                    i.putExtra("iPhone", phone);
                    i.putExtra("iOffice", office);
                    i.putExtra("iLink", link);
                    overridePendingTransition(0, 0);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();

                    overridePendingTransition(0, 0);
                    startActivity(i);

                }
            });
        }
    }
}
