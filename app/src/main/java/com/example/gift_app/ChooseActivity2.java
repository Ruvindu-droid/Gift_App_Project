package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseActivity2 extends AppCompatActivity {

    GridView gridview;
    //array
    String[] ItemName = {"Pinky","Black","Red","Yellow","Bronze Bag","White Bag","Pink Bag","Black and White"};
    //String[] ItemPrice = {"400","450","400","400","350","350","350","350"};
    int[] ItemImage = {R.drawable.f,R.drawable.f1,R.drawable.f5,R.drawable.f6,R.drawable.bag5,R.drawable.bag6,R.drawable.bag7,R.drawable.bag8};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose2);

        gridview = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),GridItemActivity2.class);
                intent.putExtra("name",ItemName[i]);
                //intent.putExtra("price",ItemPrice[i]);
                intent.putExtra("image",ItemImage[i]);
                startActivity(intent);
            }
        });


    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ItemImage.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            TextView name = view1.findViewById(R.id.box);
            //TextView price = view1.findViewById(R.id.pricepinky);
            ImageView image = view1.findViewById(R.id.boximg);

            name.setText(ItemName[i]);
            //price.setText(ItemPrice[i]);
            image.setImageResource(ItemImage[i]);

            return view1;
        }


    }
}