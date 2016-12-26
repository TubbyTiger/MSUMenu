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

public class GalleryActivity extends MainActivity
        implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    String url = "https://eatatstate.com/menus/gallery";
    ProgressDialog mProgressDialog;

    public void backgrounddoin(Document document, String locationFood, ArrayList<Integer> locArray, Elements Breakfast, Elements Lunch, Elements Dinner, Elements LateNight,
                              ArrayList<String> BreakfastArray, ArrayList<String> LunchArray, ArrayList<String> DinnerArray, ArrayList<String> LateNightArray){
        Elements menuTitle = document.select("table[class=views-table cols-4]");

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
        setContentView(R.layout.activity_gallery);

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
                Intent intent = new Intent(GalleryActivity.this, MainActivity.class);
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


        ArrayList<String> BLBreakfastArray = new ArrayList<String>();
        ArrayList<String> BLLunchArray = new ArrayList<String>();
        ArrayList<String> BLDinnerArray = new ArrayList<String>();
        ArrayList<String> BLLateNightArray = new ArrayList<String>();



        ArrayList<String> BGBreakfastArray = new ArrayList<String>();
        ArrayList<String> BGLunchArray = new ArrayList<String>();
        ArrayList<String> BGDinnerArray = new ArrayList<String>();
        ArrayList<String> BGLateNightArray = new ArrayList<String>();

        ArrayList<String> CIBreakfastArray = new ArrayList<String>();
        ArrayList<String> CILunchArray = new ArrayList<String>();
        ArrayList<String> CIDinnerArray = new ArrayList<String>();
        ArrayList<String> CILateNightArray = new ArrayList<String>();

        ArrayList<String> LABreakfastArray = new ArrayList<String>();
        ArrayList<String> LALunchArray = new ArrayList<String>();
        ArrayList<String> LADinnerArray = new ArrayList<String>();
        ArrayList<String> LALateNightArray = new ArrayList<String>();

        ArrayList<String> NTBreakfastArray = new ArrayList<String>();
        ArrayList<String> NTLunchArray = new ArrayList<String>();
        ArrayList<String> NTDinnerArray = new ArrayList<String>();
        ArrayList<String> NTLateNightArray = new ArrayList<String>();


        ArrayList<String> TBBreakfastArray = new ArrayList<String>();
        ArrayList<String> TBLunchArray = new ArrayList<String>();
        ArrayList<String> TBDinnerArray = new ArrayList<String>();
        ArrayList<String> TBLateNightArray = new ArrayList<String>();




        ArrayList<Integer> BlissArray = new ArrayList<Integer>();
        ArrayList<Integer> BrimstoneGrilleArray = new ArrayList<Integer>();
        ArrayList<Integer> CiaoArray = new ArrayList<Integer>();
        ArrayList<Integer> LatitudesArray = new ArrayList<Integer>();
        ArrayList<Integer> NewTraditionsArray = new ArrayList<Integer>();
        ArrayList<Integer> BergArray = new ArrayList<Integer>();











        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(GalleryActivity.this);
            mProgressDialog.setTitle("The Gallery Menu");
            mProgressDialog.setMessage("Loading The Gallery Menu");
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



      //          findIndex(all.size(),"Bliss", menuTitle, BlissArray);
                findIndex(all.size(),"Brimstone Grille", menuTitle, BrimstoneGrilleArray);
                findIndex(all.size(),"Ciao", menuTitle, CiaoArray);
                findIndex(all.size(),"Latitudes", menuTitle, LatitudesArray);
                findIndex(all.size(),"New Traditions", menuTitle, NewTraditionsArray);
                findIndex(all.size(),"The Berg", menuTitle, BergArray);









         //       int finalintBL = setIndex(BlissArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintBG = setIndex(BrimstoneGrilleArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintCI = setIndex(CiaoArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintLA= setIndex(LatitudesArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintNT = setIndex(NewTraditionsArray,Breakfast,Lunch,Dinner,LateNight);
                int finalintTB = setIndex(BergArray,Breakfast,Lunch,Dinner,LateNight);












                //get html document title
                // The first food elements are boiling point, 0

         //       Element BLBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintBL);
           //     Element BLLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintBL);
             //   Element BLDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintBL);
               // Element BLLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintBL);




                Element BGBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintBG);
                Element BGLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintBG);
                Element BGDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintBG);
                Element BGLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintBG);

                Element CIBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintCI);
                Element CILunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintCI);
                Element CIDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintCI);
                Element CILateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintCI);

                Element LABreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintLA);
                Element LALunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintLA);
                Element LADinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintLA);
                Element LALateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintLA);


                Element NTBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintNT);
                Element NTLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintNT);
                Element NTDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintNT);
                Element NTLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintNT);



                Element TBBreakfastElem = document.select("td[class=views-field views-field-field-breakfast-menu-value]").get(finalintTB);
                Element TBLunchElem = document.select("td[class=views-field views-field-field-lunch-menu-value]").get(finalintTB);
                Element TBDinnerElem = document.select("td[class=views-field views-field-field-dinner-menu-value]").get(finalintTB);
                Element TBLateNightElem = document.select("td[class=views-field views-field-field-late-night-value]").get(finalintTB);










  //              Elements BLcounterB = BLBreakfastElem.getElementsByTag("div");
    //            int BLcountB = BLcounterB.size();

//                Elements BLcounterL = BLLunchElem.getElementsByTag("div");
  //              int BLcountL = BLcounterL.size();

//                Elements BLcounterD = BLDinnerElem.getElementsByTag("div");
  //              int BLcountD = BLcounterD.size();

//                Elements BLcounterLN = BLLateNightElem.getElementsByTag("div");
  //              int BLcountLN = BLcounterLN.size();



                Elements BGcounterB = BGBreakfastElem.getElementsByTag("div");
                int BGcountB = BGcounterB.size();

                Elements BGcounterL = BGLunchElem.getElementsByTag("div");
                int BGcountL = BGcounterL.size();

                Elements BGcounterD = BGDinnerElem.getElementsByTag("div");
                int BGcountD = BGcounterD.size();

                Elements BGcounterLN = BGLateNightElem.getElementsByTag("div");
                int BGcountLN = BGcounterLN.size();



                Elements CIcounterB = CIBreakfastElem.getElementsByTag("div");
                int CIcountB = CIcounterB.size();

                Elements CIcounterL = CILunchElem.getElementsByTag("div");
                int CIcountL = CIcounterL.size();

                Elements CIcounterD = CIDinnerElem.getElementsByTag("div");
                int CIcountD = CIcounterD.size();

                Elements CIcounterLN = CILateNightElem.getElementsByTag("div");
                int CIcountLN = CIcounterLN.size();


                Elements LAcounterB = LABreakfastElem.getElementsByTag("div");
                int LAcountB = LAcounterB.size();

                Elements LAcounterL = LALunchElem.getElementsByTag("div");
                int LAcountL = LAcounterL.size();

                Elements LAcounterD = LADinnerElem.getElementsByTag("div");
                int LAcountD = LAcounterD.size();

                Elements LAcounterLN = LALateNightElem.getElementsByTag("div");
                int LAcountLN = LAcounterLN.size();



                Elements NTcounterB = NTBreakfastElem.getElementsByTag("div");
                int NTcountB = NTcounterB.size();

                Elements NTcounterL = NTLunchElem.getElementsByTag("div");
                int NTcountL = NTcounterL.size();

                Elements NTcounterD = NTDinnerElem.getElementsByTag("div");
                int NTcountD = NTcounterD.size();

                Elements NTcounterLN = NTLateNightElem.getElementsByTag("div");
                int NTcountLN = NTcounterLN.size();





                Elements TBcounterB = TBBreakfastElem.getElementsByTag("div");
                int TBcountB = TBcounterB.size();

                Elements TBcounterL = TBLunchElem.getElementsByTag("div");
                int TBcountL = TBcounterL.size();

                Elements TBcounterD = TBDinnerElem.getElementsByTag("div");
                int TBcountD = TBcounterD.size();

                Elements TBcounterLN = TBLateNightElem.getElementsByTag("div");
                int TBcountLN = TBcounterLN.size();




                //for (int i=0;i<BPcountL;i++){
                //String temp = Integer.toString(i);
                //Element BPLunchtxt = document.select("div[class=field-item field-item-"+temp+"]").get(0);
                // System.out.println(BPLunchtxt.text());

                //   BPLunchArray.add(BPLunchtxt.text());

                // }

     //           foodArray(BLcountB,BLBreakfastArray,BLBreakfastElem);
       //         foodArray(BLcountL,BLLunchArray,BLLunchElem);
         //       foodArray(BLcountD,BLDinnerArray,BLDinnerElem);
           //     foodArray(BLcountLN,BLLateNightArray,BLLateNightElem);



                foodArray(BGcountB,BGBreakfastArray,BGBreakfastElem);
                foodArray(BGcountL,BGLunchArray,BGLunchElem);
                foodArray(BGcountD,BGDinnerArray,BGDinnerElem);
                foodArray(BGcountLN,BGLateNightArray,BGLateNightElem);



                foodArray(CIcountB,CIBreakfastArray,CIBreakfastElem);
                foodArray(CIcountL,CILunchArray,CILunchElem);
                foodArray(CIcountD,CIDinnerArray,CIDinnerElem);
                foodArray(CIcountLN,CILateNightArray,CILateNightElem);



                foodArray(LAcountB,LABreakfastArray,LABreakfastElem);
                foodArray(LAcountL,LALunchArray,LALunchElem);
                foodArray(LAcountD,LADinnerArray,LADinnerElem);
                foodArray(LAcountLN,LALateNightArray,LALateNightElem);


                foodArray(NTcountB,NTBreakfastArray,NTBreakfastElem);
                foodArray(NTcountL,NTLunchArray,NTLunchElem);
                foodArray(NTcountD,NTDinnerArray,NTDinnerElem);
                foodArray(NTcountLN,NTLateNightArray,NTLateNightElem);





                foodArray(TBcountB,TBBreakfastArray,TBBreakfastElem);
                foodArray(TBcountL,TBLunchArray,TBLunchElem);
                foodArray(TBcountD,TBDinnerArray,TBDinnerElem);
                foodArray(TBcountLN,TBLateNightArray,TBLateNightElem);


                backgrounddoin(document, "Bliss", BlissArray, Breakfast, Lunch,Dinner,LateNight,BLBreakfastArray,BLLunchArray,BLDinnerArray,BLLateNightArray);
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
            TextView BLBreakfast = (TextView)findViewById(R.id.BLBreakfasttxt);
            settingText(BLBreakfastArray,BLBreakfast);


            TextView BLLunch = (TextView)findViewById(R.id.BLLunchTxt);
            settingText(BLLunchArray,BLLunch);


            TextView BLDinner = (TextView) findViewById(R.id.BLDinnerTxt);
            settingText(BLDinnerArray,BLDinner);

            TextView BLLateNight = (TextView) findViewById(R.id.BLLateNightTxt);
            settingText(BLLateNightArray,BLLateNight);
            equalTextview(BLBreakfast,BLLunch,BLDinner,BLLateNight);



            TextView BGBreakfast = (TextView)findViewById(R.id.BGBreakfasttxt);
            settingText(BGBreakfastArray,BGBreakfast);

            TextView BGLunch = (TextView)findViewById(R.id.BGLunchTxt);
            settingText(BGLunchArray,BGLunch);

            TextView BGDinner = (TextView) findViewById(R.id.BGDinnerTxt);
            settingText(BGDinnerArray,BGDinner);

            TextView BGLateNight = (TextView) findViewById(R.id.BGLateNightTxt);
            settingText(BGLateNightArray,BGLateNight);
            equalTextview(BGBreakfast,BGLunch,BGDinner,BGLateNight);



            TextView CIBreakfast = (TextView)findViewById(R.id.CIBreakfasttxt);
            settingText(CIBreakfastArray,CIBreakfast);

            TextView CILunch = (TextView)findViewById(R.id.CILunchTxt);
            settingText(CILunchArray,CILunch);

            TextView CIDinner = (TextView) findViewById(R.id.CIDinnerTxt);
            settingText(CIDinnerArray,CIDinner);

            TextView CILateNight = (TextView) findViewById(R.id.CILateNightTxt);
            settingText(CILateNightArray,CILateNight);
            equalTextview(CIBreakfast,CILunch,CIDinner,CILateNight);




            TextView LABreakfast = (TextView)findViewById(R.id.LABreakfasttxt);
            settingText(LABreakfastArray,LABreakfast);


            TextView LALunch = (TextView)findViewById(R.id.LALunchTxt);
            settingText(LALunchArray,LALunch);

            TextView LADinner = (TextView) findViewById(R.id.LADinnerTxt);
            settingText(LADinnerArray,LADinner);

            TextView LALateNight = (TextView) findViewById(R.id.LALateNightTxt);
            settingText(LALateNightArray,LALateNight);
            equalTextview(LABreakfast,LALunch,LADinner,LALateNight);




            TextView TBBreakfast = (TextView)findViewById(R.id.TBBreakfasttxt);
            settingText(TBBreakfastArray,TBBreakfast);

            TextView TBLunch = (TextView)findViewById(R.id.TBLunchTxt);
            settingText(TBLunchArray,TBLunch);

            TextView TBDinner = (TextView) findViewById(R.id.TBDinnerTxt);
            settingText(TBDinnerArray,TBDinner);

            TextView TBLateNight = (TextView) findViewById(R.id.TBLateNightTxt);
            settingText(TBLateNightArray,TBLateNight);
            equalTextview(TBBreakfast,TBLunch,TBDinner,TBLateNight);




            TextView NTBreakfast = (TextView)findViewById(R.id.NTBreakfasttxt);
            settingText(NTBreakfastArray,NTBreakfast);

            TextView NTLunch = (TextView)findViewById(R.id.NTLunchTxt);
            settingText(NTLunchArray,NTLunch);

            TextView NTDinner = (TextView) findViewById(R.id.NTDinnerTxt);
            settingText(NTDinnerArray,NTDinner);

            TextView NTLateNight = (TextView) findViewById(R.id.NTLateNightTxt);
            settingText(NTLateNightArray,NTLateNight);
            equalTextview(NTBreakfast,NTLunch,NTDinner,NTLateNight);













            mProgressDialog.dismiss();
        }

    }
}
