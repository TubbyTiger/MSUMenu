package com.themelon.msumenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ChatActivity extends AppCompatActivity {



    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);

        listView.setAdapter(arrayAdapter);



        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        request_user_name();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });



    }







        private void request_user_name() {
            try {

                    System.out.println("NAME");
                    FileInputStream fls = openFileInput("MSUMenu_user.txt");
             //       String name = FileUtils.readFileToString(new File("MSUMENU_user.txt"));

                  //  name = new Scanner(new File("MSUMenu_user.txt")).useDelimiter("\\Z").next().toString();


                    InputStreamReader isr = new InputStreamReader(fls);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    String Text;
                    while ((Text = br.readLine())!=null){
                        sb.append(Text);

                    }
                    name = sb.toString();
                    System.out.println("USERNAME"+name);
                    Toast.makeText(getApplicationContext(),"Awesome Username: "+name,Toast.LENGTH_LONG).show();







            } catch (FileNotFoundException e) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter your awesome nickname:");
                builder.setCancelable(false);

                final EditText input_field = new EditText(this);


                builder.setView(input_field);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name = input_field.getText().toString();
                        try {
                            FileOutputStream fos = openFileOutput("MSUMenu_user.txt",Context.MODE_PRIVATE);
                            fos.write(name.getBytes());
                            fos.close();
                            Toast.makeText(getApplicationContext(), "Username Saved", Toast.LENGTH_LONG).show();

                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }
                });


                builder.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



}
