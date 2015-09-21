package com.example.hoon.myrepresentative;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RepresentativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative);

        if (haveNetworkConnection() == true) {

            final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.states, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            adapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                            this));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    switch (position) {
                        case 0:

                            break;
                        case 1:
                            // Get select item
                            //int sid = spinner.getSelectedItemPosition();
                            //String st = String.valueOf(sid);
                            String state = "AL";

                            try {
                                Intent i = new Intent(RepresentativeActivity.this,
                                        ListRepActivity.class);
                                i.putExtra("State", state);
                                overridePendingTransition(0, 0);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();

                                overridePendingTransition(0, 0);
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
//                        Toast.makeText(getBaseContext(), "You have selected City : " + state,
//                                Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            String state1 = "AR";

                            try {
                                Intent i = new Intent(RepresentativeActivity.this,
                                        ListRepActivity.class);
                                i.putExtra("State", state1);
                                overridePendingTransition(0, 0);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();

                                overridePendingTransition(0, 0);
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            String state2 = "AZ";

                            try {
                                Intent i = new Intent(RepresentativeActivity.this,
                                        ListRepActivity.class);
                                i.putExtra("State", state2);
                                overridePendingTransition(0, 0);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                finish();

                                overridePendingTransition(0, 0);
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "You are not connected to the internet, Check your internet connection.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
