package com.example.autochat;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message>{
    private Context mContext;
    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Message> objects) {
        super(context, resource, objects);
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_message,parent,false);
        }
        ImageView msgReceiverImage=convertView.findViewById(R.id.message_receiver_image);
        ImageView msgSenderImage=convertView.findViewById(R.id.message_sender_image);
        TextView msgSenderText=convertView.findViewById(R.id.sender_msg_text);
        TextView msgReceiverText=convertView.findViewById(R.id.receiver_msg_text);


        Message msg=getItem(position);
        assert msg != null;
        boolean isImageMessage=msg.getImageUrl()!=null;
        if (msg.getUser().equals("sender"))
        {
            msgReceiverText.setVisibility(View.GONE);
            msgReceiverImage.setVisibility(View.GONE);
            if(isImageMessage){
                msgSenderText.setVisibility(View.GONE);
                msgSenderImage.setVisibility(View.VISIBLE);
                Glide.with(msgSenderImage.getContext())
                        .load(msg.getImageUrl())
                        .into(msgSenderImage);

            }else{
                msgSenderImage.setVisibility(View.GONE);
                msgSenderText.setVisibility(View.VISIBLE);
                msgSenderText.setText(msg.getMessage());
            }
        }else{
            msgSenderText.setVisibility(View.GONE);
            msgSenderImage.setVisibility(View.GONE);
            if(isImageMessage){
                msgReceiverText.setVisibility(View.GONE);
                msgReceiverImage.setVisibility(View.VISIBLE);
                Glide.with(msgReceiverImage.getContext())
                        .load(msg.getImageUrl())
                        .into(msgReceiverImage);

            }else{
                msgReceiverImage.setVisibility(View.GONE);
                msgReceiverText.setVisibility(View.VISIBLE);
                msgReceiverText.setText(msg.getMessage());
            }
        }

        return convertView;
    }
}
