package com.themelon.msumenu;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;


public class BrodySquareActivity extends Activity {


    String url = "https://eatatstate.com/menus/brody";
    ProgressDialog mProgressDialog;



    public void foodArray(int count, ArrayList<String> Arrayfood,Element mealElement) {
        if (count==0){
            Arrayfood.add(mealElement.text());
        } else {
            for (int i = 0; i < count; i++) {

                Element foodElement = mealElement.child(i);


                Arrayfood.add(foodElement.text());
            }
        }
    }




    public void settingText(ArrayList<String> Arrayfood,TextView foodloc){
        for (int k = 0; k<Arrayfood.size();k++){
            String temptxt = Arrayfood.get(k);
            foodloc.append(temptxt.trim() + "\n"+"\n");
        }
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brody_square);
        new Title().execute();



    }


    private class Title extends AsyncTask<Void, Void, Void>{


        ArrayList<String> BPBreakfastArray = new ArrayList<String>();
        ArrayList<String> BPLunchArray = new ArrayList<String>();
        ArrayList<String> BPDinnerArray = new ArrayList<String>();
        ArrayList<String> BPLateNightArray = new ArrayList<String>();

        ArrayList<String> BGBreakfastArray = new ArrayList<String>();
        ArrayList<String> BGLunchArray = new ArrayList<String>();
        ArrayList<String> BGDinnerArray = new ArrayList<String>();
        ArrayList<String> BGLateNightArray = new ArrayList<String>();

        ArrayList<String> CABreakfastArray = new ArrayList<String>();
        ArrayList<String> CALunchArray = new ArrayList<String>();
        ArrayList<String> CADinnerArray = new ArrayList<String>();
        ArrayList<String> CALateNightArray = new ArrayList<String>();

        ArrayList<String> COBreakfastArray = new ArrayList<String>();
        ArrayList<String> COLunchArray = new ArrayList<String>();
        ArrayList<String> CODinnerArray = new ArrayList<String>();
        ArrayList<String> COLateNightArray = new ArrayList<String>();

        ArrayList<String> DOBreakfastArray = new ArrayList<String>();
        ArrayList<String> DOLunchArray = new ArrayList<String>();
        ArrayList<String> DODinnerArray = new ArrayList<String>();
        ArrayList<String> DOLateNightArray = new ArrayList<String>();






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

                Element BGBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(1);
                Element BGLunchElem = document.select("td[class =views-field views-field-field-lunch-menu-value]").get(1);
                Element BGDinnerElem = document.select("td[class =views-field views-field-field-dinner-menu-value]").get(1);
                Element BGLateNightElem = document.select("td[class =views-field views-field-field-late-night-value]").get(1);

                Element CABreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(2);
                Element CALunchElem = document.select("td[class =views-field views-field-field-lunch-menu-value]").get(2);
                Element CADinnerElem = document.select("td[class =views-field views-field-field-dinner-menu-value]").get(2);
                Element CALateNightElem = document.select("td[class =views-field views-field-field-late-night-value]").get(2);

                Element COBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(3);
                Element COLunchElem = document.select("td[class =views-field views-field-field-lunch-menu-value]").get(3);
                Element CODinnerElem = document.select("td[class =views-field views-field-field-dinner-menu-value]").get(3);
                Element COLateNightElem = document.select("td[class =views-field views-field-field-late-night-value]").get(3);


                Element DOBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(4);
                Element DOLunchElem = document.select("td[class =views-field views-field-field-lunch-menu-value]").get(4);
                Element DODinnerElem = document.select("td[class =views-field views-field-field-dinner-menu-value]").get(4);
                Element DOLateNightElem = document.select("td[class =views-field views-field-field-late-night-value]").get(4);




                Elements BPcounterB = BPBreakfastElem.getElementsByTag("div");
                int BPcountB = BPcounterB.size();

                Elements BPcounterL = BPLunchElem.getElementsByTag("div");
                int BPcountL = BPcounterL.size();

                Elements BPcounterD = BPDinnerElem.getElementsByTag("div");
                int BPcountD = BPcounterD.size();

                Elements BPcounterLN = BPLateNightElem.getElementsByTag("div");
                int BPcountLN = BPcounterLN.size();



                Elements BGcounterB = BGBreakfastElem.getElementsByTag("div");
                int BGcountB = BGcounterB.size();

                Elements BGcounterL = BGLunchElem.getElementsByTag("div");
                int BGcountL = BGcounterL.size();

                Elements BGcounterD = BGDinnerElem.getElementsByTag("div");
                int BGcountD = BGcounterD.size();

                Elements BGcounterLN = BGLateNightElem.getElementsByTag("div");
                int BGcountLN = BGcounterLN.size();



                Elements CAcounterB = CABreakfastElem.getElementsByTag("div");
                int CAcountB = CAcounterB.size();

                Elements CAcounterL = CALunchElem.getElementsByTag("div");
                int CAcountL = CAcounterL.size();

                Elements CAcounterD = CADinnerElem.getElementsByTag("div");
                int CAcountD = CAcounterD.size();

                Elements CAcounterLN = CALateNightElem.getElementsByTag("div");
                int CAcountLN = CAcounterLN.size();


                Elements COcounterB = COBreakfastElem.getElementsByTag("div");
                int COcountB = COcounterB.size();

                Elements COcounterL = COLunchElem.getElementsByTag("div");
                int COcountL = COcounterL.size();

                Elements COcounterD = CODinnerElem.getElementsByTag("div");
                int COcountD = COcounterD.size();

                Elements COcounterLN = COLateNightElem.getElementsByTag("div");
                int COcountLN = COcounterLN.size();



                Elements DOcounterB = DOBreakfastElem.getElementsByTag("div");
                int DOcountB = DOcounterB.size();

                Elements DOcounterL = DOLunchElem.getElementsByTag("div");
                int DOcountL = DOcounterL.size();

                Elements DOcounterD = DODinnerElem.getElementsByTag("div");
                int DOcountD = DOcounterD.size();

                Elements DOcounterLN = DOLateNightElem.getElementsByTag("div");
                int DOcountLN = DOcounterLN.size();





                //for (int i=0;i<BPcountL;i++){
                    //String temp = Integer.toString(i);
                    //Element BPLunchtxt = document.select("div[class=field-item field-item-"+temp+"]").get(0);
                   // System.out.println(BPLunchtxt.text());

                 //   BPLunchArray.add(BPLunchtxt.text());

               // }

                foodArray(BPcountB,BPBreakfastArray,BPBreakfastElem);
                foodArray(BPcountL,BPLunchArray,BPLunchElem);
                foodArray(BPcountD,BPDinnerArray,BPDinnerElem);
                foodArray(BPcountLN,BPLateNightArray,BPLateNightElem);



                foodArray(BGcountB,BGBreakfastArray,BGBreakfastElem);
                foodArray(BGcountL,BGLunchArray,BGLunchElem);
                foodArray(BGcountD,BGDinnerArray,BGDinnerElem);
                foodArray(BGcountLN,BGLateNightArray,BGLateNightElem);



                foodArray(CAcountB,CABreakfastArray,CABreakfastElem);
                foodArray(CAcountL,CALunchArray,CALunchElem);
                foodArray(CAcountD,CADinnerArray,CADinnerElem);
                foodArray(CAcountLN,CALateNightArray,CALateNightElem);



                foodArray(COcountB,COBreakfastArray,COBreakfastElem);
                foodArray(COcountL,COLunchArray,COLunchElem);
                foodArray(COcountD,CODinnerArray,CODinnerElem);
                foodArray(COcountLN,COLateNightArray,COLateNightElem);


                foodArray(DOcountB,DOBreakfastArray,DOBreakfastElem);
                foodArray(DOcountL,DOLunchArray,DOLunchElem);
                foodArray(DOcountD,DODinnerArray,DODinnerElem);
                foodArray(DOcountLN,DOLateNightArray,DOLateNightElem);

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
            settingText(BPBreakfastArray,BPBreakfast);

            TextView BPLunch = (TextView)findViewById(R.id.BPLunchTxt);
            settingText(BPLunchArray,BPLunch);


            TextView BPDinner = (TextView) findViewById(R.id.BPDinnerTxt);
            settingText(BPDinnerArray,BPDinner);

            TextView BPLateNight = (TextView) findViewById(R.id.BPLateNightTxt);
            settingText(BPLateNightArray,BPLateNight);



            TextView BGBreakfast = (TextView)findViewById(R.id.BGBreakfasttxt);
            settingText(BGBreakfastArray,BGBreakfast);

            TextView BGLunch = (TextView)findViewById(R.id.BGLunchTxt);
            settingText(BGLunchArray,BGLunch);

            TextView BGDinner = (TextView) findViewById(R.id.BGDinnerTxt);
            settingText(BGDinnerArray,BGDinner);

            TextView BGLateNight = (TextView) findViewById(R.id.BGLateNightTxt);
            settingText(BGLateNightArray,BGLateNight);



            TextView CABreakfast = (TextView)findViewById(R.id.CABreakfasttxt);
            settingText(CABreakfastArray,CABreakfast);

            TextView CALunch = (TextView)findViewById(R.id.CALunchTxt);
            settingText(CALunchArray,CALunch);

            TextView CADinner = (TextView) findViewById(R.id.CADinnerTxt);
            settingText(CADinnerArray,CADinner);

            TextView CALateNight = (TextView) findViewById(R.id.CALateNightTxt);
            settingText(CALateNightArray,CALateNight);




            TextView COBreakfast = (TextView)findViewById(R.id.COBreakfasttxt);
            settingText(COBreakfastArray,COBreakfast);

            TextView COLunch = (TextView)findViewById(R.id.COLunchTxt);
            settingText(COLunchArray,COLunch);

            TextView CODinner = (TextView) findViewById(R.id.CODinnerTxt);
            settingText(CODinnerArray,CODinner);

            TextView COLateNight = (TextView) findViewById(R.id.COLateNightTxt);
            settingText(COLateNightArray,COLateNight);




            TextView DOBreakfast = (TextView)findViewById(R.id.DOBreakfasttxt);
            settingText(DOBreakfastArray,DOBreakfast);

            TextView DOLunch = (TextView)findViewById(R.id.DOLunchTxt);
            settingText(DOLunchArray,DOLunch);

            TextView DODinner = (TextView) findViewById(R.id.DODinnerTxt);
            settingText(DODinnerArray,DODinner);

            TextView DOLateNight = (TextView) findViewById(R.id.DOLateNightTxt);
            settingText(DOLateNightArray,DOLateNight);
            mProgressDialog.dismiss();
        }

    }
}
