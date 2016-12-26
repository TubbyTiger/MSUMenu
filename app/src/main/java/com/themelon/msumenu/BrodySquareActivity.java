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

public class BrodySquareActivity extends MainActivity
implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    String url = "https://eatatstate.com/menus/brody";
    ProgressDialog mProgressDialog;


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
                System.out.println(i);
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
        setContentView(R.layout.activity_brodysquare);

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
                Intent intent = new Intent(BrodySquareActivity.this, MainActivity.class);
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


        ArrayList<String> HOBreakfastArray = new ArrayList<String>();
        ArrayList<String> HOLunchArray = new ArrayList<String>();
        ArrayList<String> HODinnerArray = new ArrayList<String>();
        ArrayList<String> HOLateNightArray = new ArrayList<String>();


        ArrayList<String> PABreakfastArray = new ArrayList<String>();
        ArrayList<String> PALunchArray = new ArrayList<String>();
        ArrayList<String> PADinnerArray = new ArrayList<String>();
        ArrayList<String> PALateNightArray = new ArrayList<String>();


        ArrayList<String> SSBreakfastArray = new ArrayList<String>();
        ArrayList<String> SSLunchArray = new ArrayList<String>();
        ArrayList<String> SSDinnerArray = new ArrayList<String>();
        ArrayList<String> SSLateNightArray = new ArrayList<String>();



        ArrayList<String> STBreakfastArray = new ArrayList<String>();
        ArrayList<String> STLunchArray = new ArrayList<String>();
        ArrayList<String> STDinnerArray = new ArrayList<String>();
        ArrayList<String> STLateNightArray = new ArrayList<String>();



        ArrayList<String> VOBreakfastArray = new ArrayList<String>();
        ArrayList<String> VOLunchArray = new ArrayList<String>();
        ArrayList<String> VODinnerArray = new ArrayList<String>();
        ArrayList<String> VOLateNightArray = new ArrayList<String>();

        ArrayList<Integer> DolceArray = new ArrayList<Integer>();
        ArrayList<Integer> BoilingPointArray = new ArrayList<Integer>();
        ArrayList<Integer> BrimstoneGrilleArray = new ArrayList<Integer>();
        ArrayList<Integer> CayennesArray = new ArrayList<Integer>();
        ArrayList<Integer> CiaoArray = new ArrayList<Integer>();
        ArrayList<Integer> HomestyleArray = new ArrayList<Integer>();
        ArrayList<Integer> PangeaArray = new ArrayList<Integer>();
        ArrayList<Integer> SaladAndSushiArray = new ArrayList<Integer>();
        ArrayList<Integer> StacksArray = new ArrayList<Integer>();
        ArrayList<Integer> VegOutArray = new ArrayList<Integer>();










        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(BrodySquareActivity.this);
            mProgressDialog.setTitle("Brody Square Menu");
            mProgressDialog.setMessage("Loading Brody Square Menu");
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
                // There is more than 1 individual entering the data for each cafe.
                    //This is very annoying. They take all of the food locations out for Brody and for Akers they leave all the food locations "closed,"
                            //which would make things simple if all of the cafes did it that way.
                    //This if statement checks if there is the class: "views-table cols-4". If yes, ,continue with code, if no, then I might leave it blank or add the string "closed" to the arrays.







                Elements Breakfast = document.select("td[class=views-field views-field-field-breakfast-menu-value]");
                Elements Lunch = document.select("td[class=views-field views-field-field-lunch-menu-value]");
                Elements Dinner = document.select("td[class=views-field views-field-field-dinner-menu-value]");
                Elements LateNight = document.select("td[class=views-field views-field-field-late-night-value]");
                findIndex(all.size(),"Dolce", menuTitle, DolceArray);
                findIndex(all.size(),"Boiling Point", menuTitle, BoilingPointArray);
                findIndex(all.size(),"Brimstone Grille", menuTitle, BrimstoneGrilleArray);
                findIndex(all.size(),"Cayenne's", menuTitle, CayennesArray);
                findIndex(all.size(),"Ciao", menuTitle, CiaoArray);
                findIndex(all.size(),"Homestyle", menuTitle, HomestyleArray);
                findIndex(all.size(),"Pangea", menuTitle, PangeaArray);
                findIndex(all.size(),"S2: Salad and Sushi", menuTitle, SaladAndSushiArray);
                findIndex(all.size(),"Stacks", menuTitle, StacksArray);
                findIndex(all.size(),"Veg Out", menuTitle, VegOutArray);
                // findIndex: Finds elements within the string: food locations then adds them to the appropriate array.







                int finalintDO = setIndex(DolceArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintBP = setIndex(BoilingPointArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintBG = setIndex(BrimstoneGrilleArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintCA = setIndex(CayennesArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintCO = setIndex(CiaoArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintHO = setIndex(HomestyleArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintPA = setIndex(PangeaArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintSS = setIndex(SaladAndSushiArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintST = setIndex(StacksArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintVO = setIndex(VegOutArray,Breakfast,Lunch,Dinner,LateNight);

                // This was to account for the duplicate food location titles.
                //setIndex: If there is a duplicate there will be 2 elements in the array with sub-elements (food items)
                //  It compares the size of the 2 elements and it returns the bigger size element as the final integer finalint--.








                //get html document title
                // The first food elements are boiling point, 0

                Element BPBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintBP);
                Element BPLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintBP);
                Element BPDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintBP);
                Element BPLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintBP);




                Element BGBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintBG);
                Element BGLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintBG);
                Element BGDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintBG);
                Element BGLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintBG);

                Element CABreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintCA);
                Element CALunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintCA);
                Element CADinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintCA);
                Element CALateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintCA);

                Element COBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintCO);
                Element COLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintCO);
                Element CODinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintCO);
                Element COLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintCO);


                Element DOBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintDO);
                Element DOLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintDO);
                Element DODinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintDO);
                Element DOLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintDO);



                Element HOBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintHO);
                Element HOLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintHO);
                Element HODinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintHO);
                Element HOLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintHO);



                // IDK WHY BUT I HAD TO RENAME ALL THESE ELEMENTS FOR IT TO PARSE. LOOK INTO THIS LATER
                Element PABreakfastEle = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintPA);
                Element PALunchEle = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintPA);
                Element PADinnerEle = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintPA);
                Element PALateNightEle = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintPA);








                Element SSBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintSS);
                Element SSLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintSS);
                Element SSDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintSS);
                Element SSLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintSS);


                // IDK WHY BUT I HAD TO RENAME ALL THESE ELEMENTS FOR IT TO PARSE. LOOK INTO THIS LATER

                Element STBreakfastEle = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintST);
                Element STLunchEle = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintST);
                Element STDinnerEle = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintST);
                Element STLateNightEle = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintST);


                Element VOBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintVO);
                Element VOLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintVO);
                Element VODinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintVO);
                Element VOLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintVO);







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





                Elements HOcounterB = HOBreakfastElem.getElementsByTag("div");
                int HOcountB = HOcounterB.size();

                Elements HOcounterL = HOLunchElem.getElementsByTag("div");
                int HOcountL = HOcounterL.size();

                Elements HOcounterD = HODinnerElem.getElementsByTag("div");
                int HOcountD = HOcounterD.size();

                Elements HOcounterLN = HOLateNightElem.getElementsByTag("div");
                int HOcountLN = HOcounterLN.size();


                Elements PAcounterB = PABreakfastEle.getElementsByTag("div");
                int PAcountB = PAcounterB.size();

                Elements PAcounterL = PALunchEle.getElementsByTag("div");
                int PAcountL = PAcounterL.size();

                Elements PAcounterD = PADinnerEle.getElementsByTag("div");
                int PAcountD = PAcounterD.size();

                Elements PAcounterLN = PALateNightEle.getElementsByTag("div");
                int PAcountLN = PAcounterLN.size();



                Elements SScounterB = SSBreakfastElem.getElementsByTag("div");
                int SScountB = SScounterB.size();

                Elements SScounterL = SSLunchElem.getElementsByTag("div");
                int SScountL = SScounterL.size();

                Elements SScounterD = SSDinnerElem.getElementsByTag("div");
                int SScountD = SScounterD.size();

                Elements SScounterLN = SSLateNightElem.getElementsByTag("div");
                int SScountLN = SScounterLN.size();



                Elements STcounterB = STBreakfastEle.getElementsByTag("div");
                int STcountB = STcounterB.size();

                Elements STcounterL = STLunchEle.getElementsByTag("div");
                int STcountL = STcounterL.size();

                Elements STcounterD = STDinnerEle.getElementsByTag("div");
                int STcountD = STcounterD.size();

                Elements STcounterLN = STLateNightEle.getElementsByTag("div");
                int STcountLN = STcounterLN.size();


                Elements VOcounterB = VOBreakfastElem.getElementsByTag("div");
                int VOcountB = VOcounterB.size();

                Elements VOcounterL = VOLunchElem.getElementsByTag("div");
                int VOcountL = VOcounterL.size();

                Elements VOcounterD = VODinnerElem.getElementsByTag("div");
                int VOcountD = VOcounterD.size();

                Elements VOcounterLN = VOLateNightElem.getElementsByTag("div");
                int VOcountLN = VOcounterLN.size();

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





                foodArray(HOcountB,HOBreakfastArray,HOBreakfastElem);
                foodArray(HOcountL,HOLunchArray,HOLunchElem);
                foodArray(HOcountD,HODinnerArray,HODinnerElem);
                foodArray(HOcountLN,HOLateNightArray,HOLateNightElem);



                foodArray(PAcountB,PABreakfastArray,PABreakfastEle);
                foodArray(PAcountL,PALunchArray,PALunchEle);
                foodArray(PAcountD,PADinnerArray,PADinnerEle);
                foodArray(PAcountLN,PALateNightArray,PALateNightEle);


                foodArray(SScountB,SSBreakfastArray,SSBreakfastElem);
                foodArray(SScountL,SSLunchArray,SSLunchElem);
                foodArray(SScountD,SSDinnerArray,SSDinnerElem);
                foodArray(SScountLN,SSLateNightArray,SSLateNightElem);



                foodArray(STcountB,STBreakfastArray,STBreakfastEle);
                foodArray(STcountL,STLunchArray,STLunchEle);
                foodArray(STcountD,STDinnerArray,STDinnerEle);
                foodArray(STcountLN,STLateNightArray,STLateNightEle);



                foodArray(VOcountB,VOBreakfastArray,VOBreakfastElem);
                foodArray(VOcountL,VOLunchArray,VOLunchElem);
                foodArray(VOcountD,VODinnerArray,VODinnerElem);
                foodArray(VOcountLN,VOLateNightArray,VOLateNightElem);
                }
                else{
                    System.out.println("FALSE");




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
            TextView BPBreakfast = (TextView)findViewById(R.id.BPBreakfasttxt);
            settingText(BPBreakfastArray,BPBreakfast);

            TextView BPLunch = (TextView)findViewById(R.id.BPLunchTxt);
            settingText(BPLunchArray,BPLunch);


            TextView BPDinner = (TextView) findViewById(R.id.BPDinnerTxt);
            settingText(BPDinnerArray,BPDinner);

            TextView BPLateNight = (TextView) findViewById(R.id.BPLateNightTxt);
            settingText(BPLateNightArray,BPLateNight);
            equalTextview(BPBreakfast,BPLunch,BPDinner,BPLateNight);

            TextView BGBreakfast = (TextView)findViewById(R.id.BGBreakfasttxt);
            settingText(BGBreakfastArray,BGBreakfast);

            TextView BGLunch = (TextView)findViewById(R.id.BGLunchTxt);
            settingText(BGLunchArray,BGLunch);

            TextView BGDinner = (TextView) findViewById(R.id.BGDinnerTxt);
            settingText(BGDinnerArray,BGDinner);

            TextView BGLateNight = (TextView) findViewById(R.id.BGLateNightTxt);
            settingText(BGLateNightArray,BGLateNight);
            equalTextview(BGBreakfast,BGLunch,BGDinner,BGLateNight);



            TextView CABreakfast = (TextView)findViewById(R.id.CABreakfasttxt);
            settingText(CABreakfastArray,CABreakfast);

            TextView CALunch = (TextView)findViewById(R.id.CALunchTxt);
            settingText(CALunchArray,CALunch);

            TextView CADinner = (TextView) findViewById(R.id.CADinnerTxt);
            settingText(CADinnerArray,CADinner);

            TextView CALateNight = (TextView) findViewById(R.id.CALateNightTxt);
            settingText(CALateNightArray,CALateNight);
            equalTextview(CABreakfast,CALunch,CADinner,CALateNight);




            TextView COBreakfast = (TextView)findViewById(R.id.COBreakfasttxt);
            settingText(COBreakfastArray,COBreakfast);


            TextView COLunch = (TextView)findViewById(R.id.COLunchTxt);
            settingText(COLunchArray,COLunch);

            TextView CODinner = (TextView) findViewById(R.id.CODinnerTxt);
            settingText(CODinnerArray,CODinner);

            TextView COLateNight = (TextView) findViewById(R.id.COLateNightTxt);
            settingText(COLateNightArray,COLateNight);
            equalTextview(COBreakfast,COLunch,CODinner,COLateNight);




            TextView DOBreakfast = (TextView)findViewById(R.id.DOBreakfasttxt);
            settingText(DOBreakfastArray,DOBreakfast);

            TextView DOLunch = (TextView)findViewById(R.id.DOLunchTxt);
            settingText(DOLunchArray,DOLunch);

            TextView DODinner = (TextView) findViewById(R.id.DODinnerTxt);
            settingText(DODinnerArray,DODinner);

            TextView DOLateNight = (TextView) findViewById(R.id.DOLateNightTxt);
            settingText(DOLateNightArray,DOLateNight);
            equalTextview(DOBreakfast,DOLunch,DODinner,DOLateNight);




            TextView HOBreakfast = (TextView)findViewById(R.id.HOBreakfasttxt);
            settingText(HOBreakfastArray,HOBreakfast);

            TextView HOLunch = (TextView)findViewById(R.id.HOLunchTxt);
            settingText(HOLunchArray,HOLunch);

            TextView HODinner = (TextView) findViewById(R.id.HODinnerTxt);
            settingText(HODinnerArray,HODinner);

            TextView HOLateNight = (TextView) findViewById(R.id.HOLateNightTxt);
            settingText(HOLateNightArray,HOLateNight);
            equalTextview(HOBreakfast,HOLunch,HODinner,HOLateNight);



            TextView PABreakfast = (TextView)findViewById(R.id.PABreakfasttxt);
            settingText(PABreakfastArray,PABreakfast);

            TextView PALunch = (TextView)findViewById(R.id.PALunchTxt);
            settingText(PALunchArray,PALunch);

            TextView PADinner = (TextView) findViewById(R.id.PADinnerTxt);
            settingText(PADinnerArray,PADinner);

            TextView PALateNight = (TextView) findViewById(R.id.PALateNightTxt);
            settingText(PALateNightArray,PALateNight);
            equalTextview(PABreakfast,PALunch,PADinner,PALateNight);


            TextView SSBreakfast = (TextView)findViewById(R.id.SSBreakfasttxt);
            settingText(SSBreakfastArray,SSBreakfast);

            TextView SSLunch = (TextView)findViewById(R.id.SSLunchTxt);
            settingText(SSLunchArray,SSLunch);

            TextView SSDinner = (TextView) findViewById(R.id.SSDinnerTxt);
            settingText(SSDinnerArray,SSDinner);

            TextView SSLateNight = (TextView) findViewById(R.id.SSLateNightTxt);
            settingText(SSLateNightArray,SSLateNight);
            equalTextview(SSBreakfast,SSLunch,SSDinner,SSLateNight);


            TextView STBreakfast = (TextView)findViewById(R.id.STBreakfasttxt);
            settingText(STBreakfastArray,STBreakfast);

            TextView STLunch = (TextView)findViewById(R.id.STLunchTxt);
            settingText(STLunchArray,STLunch);

            TextView STDinner = (TextView) findViewById(R.id.STDinnerTxt);
            settingText(STDinnerArray,STDinner);

            TextView STLateNight = (TextView) findViewById(R.id.STLateNightTxt);
            settingText(STLateNightArray,STLateNight);
            equalTextview(STBreakfast,STLunch,STDinner,STLateNight);




            TextView VOBreakfast = (TextView)findViewById(R.id.VOBreakfasttxt);
            settingText(VOBreakfastArray,VOBreakfast);

            TextView VOLunch = (TextView)findViewById(R.id.VOLunchTxt);
            settingText(VOLunchArray,VOLunch);

            TextView VODinner = (TextView) findViewById(R.id.VODinnerTxt);
            settingText(VODinnerArray,VODinner);

            TextView VOLateNight = (TextView) findViewById(R.id.VOLateNightTxt);
            settingText(VOLateNightArray,VOLateNight);
            equalTextview(VOBreakfast,VOLunch,VODinner,VOLateNight);











            mProgressDialog.dismiss();
        }

    }
}
