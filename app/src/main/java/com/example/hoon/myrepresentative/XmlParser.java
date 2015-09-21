package com.example.hoon.myrepresentative;

/**
 * Created by hoon on 9/18/2015.
 */

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;

public class XmlParser {

    private static final String ns = null;
    String name = null;
    String party = null;
    String state = null;
    String district = null;
    String phone = null;
    String office = null;
    String link = null;

    Context context;

    /**
     * This is the only function need to be called from outside the class
     */
    public List<HashMap<String, String>> parse(Reader reader)
            throws XmlPullParserException, IOException {
        try {
            System.out.print("READER" + reader);
            Log.e("###", "111");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            Log.e("###", "222");
            parser.setInput(reader);
            Log.e("###", "333");
            parser.nextTag();
            Log.e("###", "444");
            return readCountries(parser);
        } finally {
        }
    }

    /**
     * This method read each country in the xml data and add it to List
     */
    private List<HashMap<String, String>> readCountries(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        //adapter = new RegistrationAdapter(context);

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        System.out.print("parser" + parser);
        parser.require(XmlPullParser.START_TAG, ns, "result");
        System.out.print("parser1" + parser);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            parser.require(XmlPullParser.START_TAG, ns, "rep");
            Log.e("###", "555");
            name = parser.getAttributeValue(ns, "name");
            party = parser.getAttributeValue(ns, "party");
            //Log.d("LOG_NAME", parser.getAttributeValue(ns, name));
            state = parser.getAttributeValue(ns, "state");
            district = parser.getAttributeValue(ns, "district");
            phone = parser.getAttributeValue(ns, "phone");
            office = parser.getAttributeValue(ns, "office");
            link = parser.getAttributeValue(ns, "link");

            String Party = "Party: " + party;
            String State = "State: " + state;
            String District = "District: " + district;
            String Phone = "Phone: " + phone;
            String Office = "Office: " + office;
            String Link = "Link: " + link;

            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("Name", name);
            hm.put("Party", Party);
            hm.put("State", State);
            hm.put("District", District);
            hm.put("Phone", Phone);
            hm.put("Office", Office);
            hm.put("Link", Link);

            list.add(hm);

            //long val = adapter.insertDetails(name, party, state, district,phone, office, link);

            Log.e("###", "666");
            String names = parser.getName();
            if (names.equals("rep")) {
                parser.require(XmlPullParser.START_TAG, ns, "rep");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    Log.e("###", "777");
                }
            } else {
                skip(parser);
            }
        }
        return list;
    }

    /**
     * This method read a country and returns its corresponding HashMap construct
     */
    private HashMap<String, String> readCountry(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        Log.e("###", "777");

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("pName", name);
//        editor.putString("pParty", party);
//        editor.putString("pState", state);
//        editor.putString("pDistrict", district);
//        editor.putString("pPhone", phone);
//        editor.putString("pOffice", office);
//        editor.putString("pLink", link);
//        editor.apply();
//        Log.d("party", party);

        parser.require(XmlPullParser.START_TAG, ns, "rep");

        String name = parser.getAttributeValue(ns, "name");
        String party = parser.getAttributeValue(ns, "party");
        //Log.d("LOG_NAME", parser.getAttributeValue(ns, name));
        String state = parser.getAttributeValue(ns, "state");
        String district = parser.getAttributeValue(ns, "district");
        String phone = parser.getAttributeValue(ns, "phone");
        String office = parser.getAttributeValue(ns, "office");
        String link = parser.getAttributeValue(ns, "link");

        Log.e("###", "888");
        String details = "Party : " + party;
        Log.d("LOG_NAME", name);
        Log.d("LOG_NAME1", details);
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("rep", name);
        //hm.put("flag", flag);
        hm.put("details", details);

        return hm;
    }

    /**
     * Process language tag in the xml data
     */
    private String readLanguage(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "language");
        String language = readText(parser);
        return language;
    }

    /**
     * Process Capital tag in the xml data
     */
    private void readCapital(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "capital");
        parser.nextTag();
    }

    /**
     * Process Currency tag in the xml data
     */
    private String readCurrency(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "currency");
        String currency = readText(parser);
        return currency;
    }

    /**
     * Getting Text from an element
     */
    private String readText(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
