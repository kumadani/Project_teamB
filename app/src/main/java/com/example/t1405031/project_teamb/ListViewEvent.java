package com.example.t1405031.project_teamb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.widget.*;
import android.view.View;
import android.content.Intent;

public class ListViewEvent extends AppCompatActivity {

    ListView listview;
    ArrayList<String> members = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        members.add("a");
        members.add("b");
        members.add("c");
        listview = (ListView)findViewById(R.id.ListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, members);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent,View view,int position,long id){
                Intent intent = new Intent();
                intent.putExtra("SELECTED_EVENT",members.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
