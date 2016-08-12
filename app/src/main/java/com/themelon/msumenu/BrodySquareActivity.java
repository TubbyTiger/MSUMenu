package com.themelon.msumenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class BrodySquareActivity extends Activity {


    String url = "https://eatatstate.com/menus/brody";
    ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brody_square);
        new Title().execute();



    }


    private class Title extends AsyncTask<Void, Void, Void>{
        String BPBreakfastraw,BPLunchraw,BPDinnerraw,BPLateNightraw;
        String testing;
        String BPLunchtxt,BPLunchtxta;
        ArrayList<String> BPLunchArray = new ArrayList<String>();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(BrodySquareActivity.this);
            mProgressDialog.setTitle("Brody Square Lunch Menu");
            mProgressDialog.setMessage("Loading Brody Square Lunch Menu");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void...params){
            try {
                // Connect to website
                Document document = Jsoup.connect(url).get();

                //get html document title
                // The first food elements are boiling point, 0
                Element BPBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(0);
                Element BPLunchElem = document.select("td[class =views-field views-field-field-lunch-menu-value]").get(0);
                Element BPDinnerElem = document.select("td[class =views-field views-field-field-dinner-menu-value]").get(0);
                Element BPLateNightElem = document.select("td[class =views-field views-field-field-late-night-value]").get(0);

                Elements counter = BPLunchElem.getElementsByTag("div");



                int count = counter.size();


                for (int i=0;i<count;i++){
                    String temp = Integer.toString(i);
                    Element BPLunchtxt = document.select("div[class=field-item field-item-"+temp+"]").get(0);
                    System.out.println(BPLunchtxt.text());

                    BPLunchArray.add(BPLunchtxt.text());

                }



                Element testing = document.select("div[class=field-item field-item-3]").get(0);


                BPBreakfastraw = BPBreakfastElem.text();
                BPLunchraw = BPLunchElem.text();
                BPDinnerraw = BPDinnerElem.text();
                BPLateNightraw = BPLateNightElem.text();
            }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;


        }

        @Override
        protected void onPostExecute(Void result) {

        //     Set description into TextView
            TextView BPBreakfast = (TextView)findViewById(R.id.BPBreakfasttxt);
            BPBreakfast.setText(BPBreakfastraw);

            TextView BPLunch = (TextView) findViewById(R.id.BPLunchTxt);
            for (int k=0;k<BPLunchArray.size();k++){
                System.out.println("HWEF");
                String Temptxt = BPLunchArray.get(k);
                BPLunch.append(Temptxt + "\n");
            }



            TextView BPDinner = (TextView) findViewById(R.id.BPDinnerTxt);
            BPDinner.setText(BPDinnerraw);

            TextView BPLateNight = (TextView) findViewById(R.id.BPLateNightTxt);
            BPLateNight.setText(BPLateNightraw);


            mProgressDialog.dismiss();
        }

    }
}
