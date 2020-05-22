package com.example.autochat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Message> conversation;
    private ListView mList;
    private MessageAdapter mAdapter;
    private static int x=0;
    private View mView=null;
    private GestureDetectorCompat detectorCompat;
    private SharedPreferences mSharedPreferences;
    private MediaPlayer mediaPlayer;
    private AlertDialog alertDialog;
    private ArrayList<Message> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(checkInternetConnectivity())
                    addMessage();
            }
        });


    }

    private void init() {

        stories=new ArrayList<>();
        conversation=new ArrayList<>();
        mList=findViewById(R.id.msg_list);

        mAdapter=new MessageAdapter(this,R.layout.item_message,conversation);
        detectorCompat= new GestureDetectorCompat(this,new TapGestureDetector());
        mList.setAdapter(mAdapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        makeStory();
        checkInternetConnectivity();
    }

    private void makeStory() {
        stories.add(new Message("sender","Hey Udit here",null));
        stories.add(new Message("sender","Thanks for sharing your number",null));
        stories.add(new Message("receiver","No problem",null));
        stories.add(new Message("receiver","DMs can be annoying",null));
        stories.add(new Message("receiver","And it's been months",null));
        stories.add(new Message("sender","Ikr,i prefer texting",null));
        stories.add(new Message("sender","But waiting for your number was worth it",null));
        stories.add(new Message("receiver","Me too!",null));
        stories.add(new Message("receiver","oh shhh don't get all mushy",null));
        stories.add(new Message("receiver","So, what's up?",null));
        stories.add(new Message("sender","Nothing really ",null));
        stories.add(new Message("sender","I've been studying for this exam and it's so stressful",null));
        stories.add(new Message("sender","I hate it",null));
        stories.add(new Message("receiver","what's the exam for",null));
        stories.add(new Message("sender","It's history of literature",null));
        stories.add(new Message("receiver","I thought you Loved lit",null));
        stories.add(new Message("sender","I do, i really do",null));
        stories.add(new Message("sender","but it get stressful learning all of the history of it at once",null));
        stories.add(new Message("receiver","Oh I can understand that",null));
        stories.add(new Message("receiver","I wish we could have more cooperative learning space",null));
        stories.add(new Message("sender","ugh tell me about it",null));
        stories.add(new Message("sender","What about you ",null));
        stories.add(new Message("sender","what are you up to?",null));
        stories.add(new Message("receiver","I'm travelling this summer!",null));
        stories.add(new Message("receiver","I went to so many places Already",null));
        stories.add(new Message("receiver","It's crazy",null));
        stories.add(new Message("sender","I would love to travel someday too",null));
        stories.add(new Message("sender","Tum kahan se ho vaise?",null));
        stories.add(new Message("receiver","Same as you",null));
        stories.add(new Message("receiver","Pehle hi bataya tha",null));
        stories.add(new Message("sender","oh han ",null));
        stories.add(new Message("sender","Sorry",null));
        stories.add(new Message("sender","So where did you go?",null));
        stories.add(new Message("receiver","I recently came back from Pataya",null));
        stories.add(new Message("receiver","It is so beautiful",null));
        stories.add(new Message("receiver","I could die",null));
        stories.add(new Message("sender","Haha",null));
        stories.add(new Message("sender","I swear your humor is beyond me",null));
        stories.add(new Message("receiver","You'll get used to it",null));
        stories.add(new Message("receiver","You just need to spend some time with me",null));
        stories.add(new Message("sender","So, i'll get to spend more time with you?",null));
        stories.add(new Message("receiver","Ya",null));
        stories.add(new Message("receiver","I would love to actually meet you",null));
        stories.add(new Message("sender","Me too",null));
        stories.add(new Message("sender","some day",null));
        stories.add(new Message("reciever","okay cool cool",null));




    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private class TapGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(checkInternetConnectivity())
                addMessage();

            return true;


        }



    }

    private void addMessage() {
        Log.e("tap event: ",""+x);
        releaseMediaPlayer();
        Message message=null;

        if(x<stories.size()) {
            message = stories.get(x);
            assert message != null;
            if(message.getUser().equals("sender")){

                conversation.add(message);
                mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.sender);
                mediaPlayer.start();
            }
            else{
                conversation.add(message);
                mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.reciever);
                mediaPlayer.start();

            }
            x++;
        }
        else{
            Toast.makeText(MainActivity.this,"To read complete Story download Blushed",Toast.LENGTH_SHORT).show();
        }

        mAdapter.notifyDataSetChanged();

    }

    private void releaseMediaPlayer() {
        if (mediaPlayer!=null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences=getSharedPreferences("LastConversation",Context.MODE_PRIVATE);
        x=mSharedPreferences.getInt("LastConversationIndex",1);
        Log.e("onResume: ",""+x);

        if((checkInternetConnectivity())) {
            if (conversation.isEmpty()) {
                previousChat(0, x);
            } else if (conversation.size() != x) {
                previousChat(conversation.size() - 1, x);
            }
        }

    }

    private void previousChat(int start,int index) {
        if(index>stories.size())
        {
            index=stories.size();
        }
        for(int i=start;i<index;i++)
        {
            Message msg=stories.get(i);
            if(msg.getUser().equals("sender")){

                conversation.add(msg);
            }
            else{
                conversation.add(msg);

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSharedPreferences=getSharedPreferences("LastConversation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putInt("LastConversationIndex",x);
        editor.apply();
        //editor.commit();
        Log.e("onPause: ",""+x);

    }

    private boolean checkInternetConnectivity() {
        ConnectivityManager cm=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        if (isConnected)
        {

            if (alertDialog!=null && alertDialog.isShowing())
                 alertDialog.dismiss();
            if (conversation.isEmpty()) {
                previousChat(0, x);
            } else if (conversation.size() != x) {
                previousChat(conversation.size() - 1, x);
            }
            return true;
        }else{
            showDialogBox();
            return false;
        }
    }

    private void showDialogBox() {
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Check Internet Connectivity to Further read the Story");
        alertDialogBuilder.setCancelable(false);


        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        if(checkInternetConnectivity())
                        {
                            alertDialog.dismiss();
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
