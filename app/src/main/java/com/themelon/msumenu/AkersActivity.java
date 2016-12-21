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

public class AkersActivity extends MainActivity
        implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    String url = "https://eatatstate.com/menus/akers";
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
        setContentView(R.layout.activity_akers);

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
                Intent intent = new Intent(AkersActivity.this, MainActivity.class);
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


        ArrayList<String> TPBreakfastArray = new ArrayList<String>();
        ArrayList<String> TPLunchArray = new ArrayList<String>();
        ArrayList<String> TPDinnerArray = new ArrayList<String>();
        ArrayList<String> TPLateNightArray = new ArrayList<String>();



        ArrayList<String> SLBreakfastArray = new ArrayList<String>();
        ArrayList<String> SLLunchArray = new ArrayList<String>();
        ArrayList<String> SLDinnerArray = new ArrayList<String>();
        ArrayList<String> SLLateNightArray = new ArrayList<String>();

        ArrayList<String> SNBreakfastArray = new ArrayList<String>();
        ArrayList<String> SNLunchArray = new ArrayList<String>();
        ArrayList<String> SNDinnerArray = new ArrayList<String>();
        ArrayList<String> SNLateNightArray = new ArrayList<String>();

        ArrayList<String> SPBreakfastArray = new ArrayList<String>();
        ArrayList<String> SPLunchArray = new ArrayList<String>();
        ArrayList<String> SPDinnerArray = new ArrayList<String>();
        ArrayList<String> SPLateNightArray = new ArrayList<String>();

        ArrayList<String> STBreakfastArray = new ArrayList<String>();
        ArrayList<String> STLunchArray = new ArrayList<String>();
        ArrayList<String> STDinnerArray = new ArrayList<String>();
        ArrayList<String> STLateNightArray = new ArrayList<String>();


        ArrayList<String> TABreakfastArray = new ArrayList<String>();
        ArrayList<String> TALunchArray = new ArrayList<String>();
        ArrayList<String> TADinnerArray = new ArrayList<String>();
        ArrayList<String> TALateNightArray = new ArrayList<String>();












        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AkersActivity.this);
            mProgressDialog.setTitle(" Lunch Menu");
            mProgressDialog.setMessage("Loading Brody Square Lunch Menu");
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





                Elements Breakfast = document.select("td[class=views-field views-field-field-breakfast-menu-value]");
                Elements Lunch = document.select("td[class=views-field views-field-field-lunch-menu-value]");
                Elements Dinner = document.select("td[class=views-field views-field-field-dinner-menu-value]");
                Elements LateNight = document.select("td[class=views-field views-field-field-late-night-value]");







                backgrounddoin(document, "The Pit", Breakfast, Lunch,Dinner,LateNight,TPBreakfastArray,TPLunchArray,TPDinnerArray,TPLateNightArray);
                backgrounddoin(document, "Slices", Breakfast, Lunch,Dinner,LateNight,SLBreakfastArray,SLLunchArray,SLDinnerArray,SLLateNightArray);
                backgrounddoin(document, "Sticks & Noodles", Breakfast, Lunch,Dinner,LateNight,SNBreakfastArray,SNLunchArray,SNDinnerArray,SNLateNightArray);
                backgrounddoin(document, "Sprinkles", Breakfast, Lunch,Dinner,LateNight,SPBreakfastArray,SPLunchArray,SPDinnerArray,SPLateNightArray);
                backgrounddoin(document, "Stacks", Breakfast, Lunch,Dinner,LateNight,STBreakfastArray,STLunchArray,STDinnerArray,STLateNightArray);
                backgrounddoin(document, "Tandoori", Breakfast, Lunch,Dinner,LateNight,TABreakfastArray,TALunchArray,TADinnerArray,TALateNightArray);






            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(Void result) {

            //     Set description into TextView
            TextView TPBreakfast = (TextView)findViewById(R.id.TPBreakfasttxt);
            settingText(TPBreakfastArray,TPBreakfast);


            TextView TPLunch = (TextView)findViewById(R.id.TPLunchTxt);
            settingText(TPLunchArray,TPLunch);


            TextView TPDinner = (TextView) findViewById(R.id.TPDinnerTxt);
            settingText(TPDinnerArray,TPDinner);

            TextView TPLateNight = (TextView) findViewById(R.id.TPLateNightTxt);
            settingText(TPLateNightArray,TPLateNight);
            equalTextview(TPBreakfast,TPLunch,TPDinner,TPLateNight);



            TextView SLBreakfast = (TextView)findViewById(R.id.SLBreakfasttxt);
            settingText(SLBreakfastArray,SLBreakfast);

            TextView SLLunch = (TextView)findViewById(R.id.SLLunchTxt);
            settingText(SLLunchArray,SLLunch);

            TextView SLDinner = (TextView) findViewById(R.id.SLDinnerTxt);
            settingText(SLDinnerArray,SLDinner);

            TextView SLLateNight = (TextView) findViewById(R.id.SLLateNightTxt);
            settingText(SLLateNightArray,SLLateNight);
            equalTextview(SLBreakfast,SLLunch,SLDinner,SLLateNight);



            TextView SNBreakfast = (TextView)findViewById(R.id.SNBreakfasttxt);
            settingText(SNBreakfastArray,SNBreakfast);

            TextView SNLunch = (TextView)findViewById(R.id.SNLunchTxt);
            settingText(SNLunchArray,SNLunch);

            TextView SNDinner = (TextView) findViewById(R.id.SNDinnerTxt);
            settingText(SNDinnerArray,SNDinner);

            TextView SNLateNight = (TextView) findViewById(R.id.SNLateNightTxt);
            settingText(SNLateNightArray,SNLateNight);
            equalTextview(SNBreakfast,SNLunch,SNDinner,SNLateNight);




            TextView SPBreakfast = (TextView)findViewById(R.id.SPBreakfasttxt);
            settingText(SPBreakfastArray,SPBreakfast);


            TextView SPLunch = (TextView)findViewById(R.id.SPLunchTxt);
            settingText(SPLunchArray,SPLunch);

            TextView SPDinner = (TextView) findViewById(R.id.SPDinnerTxt);
            settingText(SPDinnerArray,SPDinner);

            TextView SPLateNight = (TextView) findViewById(R.id.SPLateNightTxt);
            settingText(SPLateNightArray,SPLateNight);
            equalTextview(SPBreakfast,SPLunch,SPDinner,SPLateNight);




            TextView STBreakfast = (TextView)findViewById(R.id.STBreakfasttxt);
            settingText(STBreakfastArray,STBreakfast);

            TextView STLunch = (TextView)findViewById(R.id.STLunchTxt);
            settingText(STLunchArray,STLunch);

            TextView STDinner = (TextView) findViewById(R.id.STDinnerTxt);
            settingText(STDinnerArray,STDinner);

            TextView STLateNight = (TextView) findViewById(R.id.STLateNightTxt);
            settingText(STLateNightArray,STLateNight);
            equalTextview(STBreakfast,STLunch,STDinner,STLateNight);




            TextView TABreakfast = (TextView)findViewById(R.id.TABreakfasttxt);
            settingText(TABreakfastArray,TABreakfast);

            TextView TALunch = (TextView)findViewById(R.id.TALunchTxt);
            settingText(TALunchArray,TALunch);

            TextView TADinner = (TextView) findViewById(R.id.TADinnerTxt);
            settingText(TADinnerArray,TADinner);

            TextView TALateNight = (TextView) findViewById(R.id.TALateNightTxt);
            settingText(TALateNightArray,TALateNight);
            equalTextview(TABreakfast,TALunch,TADinner,TALateNight);













            mProgressDialog.dismiss();
        }

    }
}
