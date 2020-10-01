package com.example.gift_app;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder1 extends RecyclerView.ViewHolder {



    public ViewHolder1(@NonNull View itemView) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistener.onItemlongClick(view,getAdapterPosition());
                return false;
            }
        });

    }

    public void setData(Context context, String giftname, String kindgift, String gender){
        //showing rows
        TextView textView = itemView.findViewById(R.id.textview_row);
        textView.setText("GiftName: "+ giftname + "\n" + "KindGift: " + kindgift + "Gender: " + gender);

    }

    private ViewHolder1.Clicklistener mClicklistener;
    //implement interface to delete data on long item click
    public interface Clicklistener{
        void onItemlongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder1.Clicklistener clickListener){
        mClicklistener = clickListener;
    }
}
