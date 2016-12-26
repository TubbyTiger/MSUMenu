package com.themelon.msumenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class HolmesActivity extends MainActivity
        implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    String url = "https://eatatstate.com/menus/holmes";
    ProgressDialog mProgressDialog;

    public void backgrounddoin(Document document, String locationFood, Elements Breakfast, Elements Lunch, Elements Dinner, Elements LateNight,
                               ArrayList<String> BreakfastArray, ArrayList<String> LunchArray, ArrayList<String> DinnerArray, ArrayList<String> LateNightArray){
        Elements menuTitle = document.select("table[class=views-table cols-4]");
        ArrayList<Integer> locArray = new ArrayList<Integer>();
        Elements all = document.select("[class=views-table cols-4]");
        findIndex(all.size(),locationFood, menuTitle, locArray);
        int finalint = setIndex(locArray,Breakfast,Lunch,Dinner,LateNight);
        Element BreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalint);
        Element LunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalint);
        Element DinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalint);
        Element LateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalint);
        Elements counterB = BreakfastElem.getElementsByTag("div");
        int countB = counterB.size();

        Elements counterL = LunchElem.getElementsByTag("div");
        int countL = counterL.size();

        Elements counterD = DinnerElem.getElementsByTag("div");
        int countD = counterD.size();

        Elements counterLN = LateNightElem.getElementsByTag("div");
        int countLN = counterLN.size();
        foodArray(countB,BreakfastArray,BreakfastElem);
        foodArray(countL,LunchArray,LunchElem);
        foodArray(countD,DinnerArray,DinnerElem);
        foodArray(countLN,LateNightArray,LateNightElem);
    }
    public void equalTextview(TextView breakfast, TextView lunch, TextView dinner, TextView latenight) {
        int max = 0;
        ArrayList<String> intArray = new ArrayList<String>();
        int intB = breakfast.getLayout().getLineCount();
        int intL = lunch.getLayout().getLineCount();
        int intD = dinner.getLayout().getLineCount();
        int intLN = latenight.getLayout().getLineCount();

        intArray.add(Integer.toString(intB));
        intArray.add(Integer.toString(intL));
        intArray.add(Integer.toString(intD));
        intArray.add(Integer.toString(intLN));

        for (int i = 0; i < 4; i++) {
            int compare = Integer.parseInt(intArray.get(i));
            if (max < compare) {
                max = compare;
            }
        }

        while (intB != max) {
            intB++;
            breakfast.append("\n");
        }
        while (intL != max) {
            intL++;
            lunch.append("\n");
        }

        while (intD != max) {
            intD++;
            dinner.append("\n");
        }
        while (intLN != max) {
            intLN++;
            latenight.append("\n");
        }

    }



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


    public void findIndex(int total, String foodLoc, Elements menuTitle,ArrayList<Integer> foodLocArray){

        for( int i=0;i<total;i++){
            if(foodLoc.equals(menuTitle.get(i).child(0).text())) {
                foodLocArray.add(i);

            }
        }
    }



    public Integer setIndex(ArrayList<Integer> foodLocArray, Elements mealCountBCheck, Elements mealCountLCheck, Elements mealCountDCheck, Elements mealCountLNCheck ){
        if (foodLocArray.size()>1){
            if ((mealCountBCheck.get(foodLocArray.get(0)).childNodeSize()+ mealCountLCheck.get(foodLocArray.get(0)).childNodeSize() + mealCountDCheck.get(foodLocArray.get(0)).childNodeSize()
                    + mealCountLNCheck.get(foodLocArray.get(0)).childNodeSize()) > (mealCountBCheck.get(foodLocArray.get(1)).childNodeSize()+ mealCountLCheck.get(foodLocArray.get(1)).childNodeSize()
                    + mealCountDCheck.get(foodLocArray.get(1)).childNodeSize() + mealCountLNCheck.get(foodLocArray.get(1)).childNodeSize())){
                return foodLocArray.get(0);
            }else{
                return foodLocArray.get(1);
            }

        }
        return foodLocArray.get(0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holmes);

        new Title().execute();


        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);


        // ...From section above...
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);


        // summerfontshadow(R.id.textView32);
        //summerfont(R.id.textView33);
        //summerfont(R.id.textView34);
        // summerfont(R.id.textView35);
        // summerfont(R.id.textView36);
        // summerfontblack(R.id.SSBreakfasttxt);
        // summerfontblack(R.id.SSLunchTxt);
        // summerfontblack(R.id.SSDinnerTxt);
        //summerfontblack(R.id.SSLateNightTxt);




    }
    public void summerfontshadow(int id){
        TextView tx = (TextView)findViewById(id);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/KGSummerSunshineShadow.ttf");
        tx.setTypeface(custom_font);
    }

    public void summerfontblack(int id) {
        TextView tx = (TextView) findViewById(id);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/KGSummerSunshineBlackout.ttf");
        tx.setTypeface(custom_font);
    }

    public void summerfont(int id){
        TextView tx = (TextView)findViewById(id);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/KGSummerSunshine.ttf");
        tx.setTypeface(custom_font);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }




    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {

            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                Intent intent = new Intent(HolmesActivity.this, MainActivity.class);
                startActivity(intent);
                break;


            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();


    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class Title extends AsyncTask<Void, Void, Void> {


        ArrayList<String> ORBreakfastArray = new ArrayList<String>();
        ArrayList<String> ORLunchArray = new ArrayList<String>();
        ArrayList<String> ORDinnerArray = new ArrayList<String>();
        ArrayList<String> ORLateNightArray = new ArrayList<String>();



        ArrayList<String> LEBreakfastArray = new ArrayList<String>();
        ArrayList<String> LELunchArray = new ArrayList<String>();
        ArrayList<String> LEDinnerArray = new ArrayList<String>();
        ArrayList<String> LELateNightArray = new ArrayList<String>();

        ArrayList<String> TFBreakfastArray = new ArrayList<String>();
        ArrayList<String> TFLunchArray = new ArrayList<String>();
        ArrayList<String> TFDinnerArray = new ArrayList<String>();
        ArrayList<String> TFLateNightArray = new ArrayList<String>();











        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(HolmesActivity.this);
            mProgressDialog.setTitle("Holmes Menu");
            mProgressDialog.setMessage("Loading Holmes Menu");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void...params){
            try {
                // Connect to website
                Document document = Jsoup.connect(url).get();

                Elements menuTitle = document.select("table[class=views-table cols-4]");

                Elements all = document.select("[class=views-table cols-4]");

                if(all.hasText()){
                    System.out.println("TRUE");




                Elements Breakfast = document.select("td[class=views-field views-field-field-breakfast-menu-value]");
                Elements Lunch = document.select("td[class=views-field views-field-field-lunch-menu-value]");
                Elements Dinner = document.select("td[class=views-field views-field-field-dinner-menu-value]");
                Elements LateNight = document.select("td[class=views-field views-field-field-late-night-value]");







                backgrounddoin(document, "Origins", Breakfast, Lunch,Dinner,LateNight,ORBreakfastArray,ORLunchArray,ORDinnerArray,ORLateNightArray);
                backgrounddoin(document, "Levels", Breakfast, Lunch,Dinner,LateNight,LEBreakfastArray,LELunchArray,LEDinnerArray,LELateNightArray);
                backgrounddoin(document, "Today's Features", Breakfast, Lunch,Dinner,LateNight,TFBreakfastArray,TFLunchArray,TFDinnerArray,TFLateNightArray);




            }
            else{
                System.out.println("False");
            }


            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(Void result) {
            //     Set description into TextView
            TextView ORBreakfast = (TextView)findViewById(R.id.ORBreakfasttxt);
            settingText(ORBreakfastArray,ORBreakfast);


            TextView ORLunch = (TextView)findViewById(R.id.ORLunchTxt);
            settingText(ORLunchArray,ORLunch);


            TextView ORDinner = (TextView) findViewById(R.id.ORDinnerTxt);
            settingText(ORDinnerArray,ORDinner);

            TextView ORLateNight = (TextView) findViewById(R.id.ORLateNightTxt);
            settingText(ORLateNightArray,ORLateNight);
            equalTextview(ORBreakfast,ORLunch,ORDinner,ORLateNight);



            TextView LEBreakfast = (TextView)findViewById(R.id.LEBreakfasttxt);
            settingText(LEBreakfastArray,LEBreakfast);

            TextView LELunch = (TextView)findViewById(R.id.LELunchTxt);
            settingText(LELunchArray,LELunch);

            TextView LEDinner = (TextView) findViewById(R.id.LEDinnerTxt);
            settingText(LEDinnerArray,LEDinner);

            TextView LELateNight = (TextView) findViewById(R.id.LELateNightTxt);
            settingText(LELateNightArray,LELateNight);
            equalTextview(LEBreakfast,LELunch,LEDinner,LELateNight);



            TextView TFBreakfast = (TextView)findViewById(R.id.TFBreakfasttxt);
            settingText(TFBreakfastArray,TFBreakfast);

            TextView TFLunch = (TextView)findViewById(R.id.TFLunchTxt);
            settingText(TFLunchArray,TFLunch);

            TextView TFDinner = (TextView) findViewById(R.id.TFDinnerTxt);
            settingText(TFDinnerArray,TFDinner);

            TextView TFLateNight = (TextView) findViewById(R.id.TFLateNightTxt);
            settingText(TFLateNightArray,TFLateNight);
            equalTextview(TFBreakfast,TFLunch,TFDinner,TFLateNight);









            mProgressDialog.dismiss();
        }

    }
}
