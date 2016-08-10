package com.themelon.msumenu;

import java.io.IOException;
import java.io.InputStream;

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

        String boilingPointDesc;

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

                Element elements = document.select("td[class = views-field views-field-field-lunch-menu-value]").get(0);



                boilingPointDesc = elements.text();
                System.out.println(boilingPointDesc);











            }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;


        }
        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView BPTitle = (TextView) findViewById(R.id.BPLunchTxt);
            BPTitle.setText(boilingPointDesc);
            mProgressDialog.dismiss();
        }

    }
}
