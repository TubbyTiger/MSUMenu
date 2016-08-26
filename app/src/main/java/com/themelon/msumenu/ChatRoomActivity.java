package com.themelon.msumenu;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;

    private String user_name,room_name;
    private DatabaseReference root ;
    private String temp_key;
    private DatabaseReference rootBrodyP;
    private DatabaseReference rootAkersP;
    private DatabaseReference rootHolmesP;
    private DatabaseReference rootHubbardP;
    private DatabaseReference rootShawP;
    private DatabaseReference rootRiverwalkP;
    private DatabaseReference rootHoldenP;
    private DatabaseReference rootSouthpointeP;
    private DatabaseReference rootWilsonP;
    private DatabaseReference rootHeritageP;
    private DatabaseReference rootGalleryP;
    boolean brodyPop;
    boolean akersPop;
    boolean holmesPop;
    boolean hubbardPop;
    boolean shawPop;
    boolean riverwalkPop;
    boolean holdenPop;
    boolean southpointePop;
    boolean wilsonPop;
    boolean heritagePop;
    boolean galleryPop;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        btn_send_msg = (Button) findViewById(R.id.btn_send);
        input_msg = (EditText) findViewById(R.id.msg_input);
        chat_conversation = (TextView) findViewById(R.id.textView);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - "+room_name);


        root = FirebaseDatabase.getInstance().getReference().child("Rooms").child(room_name);
        rootBrodyP = FirebaseDatabase.getInstance().getReference().child("Population").child("BrodyP");
        rootAkersP = FirebaseDatabase.getInstance().getReference().child("Population").child("AkersP");
        rootHolmesP = FirebaseDatabase.getInstance().getReference().child("Population").child("HolmesP");
        rootHubbardP = FirebaseDatabase.getInstance().getReference().child("Population").child("HubbardP");
        rootShawP = FirebaseDatabase.getInstance().getReference().child("Population").child("ShawP");
        rootRiverwalkP = FirebaseDatabase.getInstance().getReference().child("Population").child("RiverwalkP");
        rootHoldenP = FirebaseDatabase.getInstance().getReference().child("Population").child("HoldenP");
        rootSouthpointeP = FirebaseDatabase.getInstance().getReference().child("Population").child("SouthpointeP");
        rootWilsonP = FirebaseDatabase.getInstance().getReference().child("Population").child("WilsonP");
        rootHeritageP = FirebaseDatabase.getInstance().getReference().child("Population").child("HeritageP");
        rootGalleryP = FirebaseDatabase.getInstance().getReference().child("Population").child("GalleryP");






        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);
                input_msg.setText("");

            }
        });
        if (room_name.equals("BRODY SQUARE")){
            brodyPop = true;
        }else{
            brodyPop = false;
        }
        if (room_name.equals("AKERS")){
            akersPop = true;
        }else{
            akersPop = false;
        }
        if (room_name.equals("HOLMES")){
            holmesPop = true;
        }else{
            holmesPop = false;
        }
        if (room_name.equals("HUBBARD")){
            hubbardPop = true;
        }else{
            hubbardPop = false;
        }
        if (room_name.equals("SHAW")){
            shawPop = true;
        }else{
            shawPop = false;
        }
        if (room_name.equals("RIVERWALK MARKET")){
            riverwalkPop = true;
        }else{
            riverwalkPop = false;
        }
        if (room_name.equals("HOLDEN")){
            holdenPop = true;
        }else{
            holdenPop = false;
        }
        if (room_name.equals("SOUTH POINTE")){
            southpointePop = true;
        }else{
            southpointePop = false;
        }
        if (room_name.equals("WILSON")){
            wilsonPop = true;
        }else{
            wilsonPop= false;
        }
        if (room_name.equals("HERITAGE COMMONS AT LANDON")){
            heritagePop = true;
        }else{
            heritagePop = false;
        }
        if (room_name.equals("THE GALLERY")){
            galleryPop = true;
        }else{
            galleryPop = false;
        }
        rootBrodyP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ds = dataSnapshot.getValue(int.class);

                if (brodyPop==true){
                    rootBrodyP.setValue(ds+1);
                    brodyPop = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rootAkersP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ds = dataSnapshot.getValue(int.class);

                if (akersPop==true){
                    rootAkersP.setValue(ds+1);
                    akersPop = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String chat_msg,chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name +" : "+chat_msg +" \n");
        }


    }


public void onDestroy() {
    super.onDestroy();

    rootBrodyP.addValueEventListener(new ValueEventListener() {
        boolean brodyPopExit = true;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int test = dataSnapshot.getValue(int.class);
            if (room_name.equals("BRODY SQUARE")) {
                if (brodyPopExit == true) {
                    rootBrodyP.setValue(test - 1);
                    brodyPopExit = false;
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    rootAkersP.addValueEventListener(new ValueEventListener() {
        boolean akersPopExit = true;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int test = dataSnapshot.getValue(int.class);
            if (room_name.equals("AKERS")) {
                if (akersPopExit == true) {
                    rootAkersP.setValue(test - 1);
                    akersPopExit = false;
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
}
