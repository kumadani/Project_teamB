package com.example.t1405031.project_teamb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import android.provider.CalendarContract;
//import java.util.Calendar;
import android.database.Cursor;
import android.content.*;
import android.net.Uri;
import android.util.Log;

public class ListViewEvent extends AppCompatActivity {

    ListView listview;
    ArrayList<String> members = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        ContentResolver resolver = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
// アンドロイドのバージョンコードが7以下の時(Android2.1以下)は，com.android.calendarの代わりにcalendarにして下さい．
        Uri.Builder builder = uri.buildUpon();
        ContentUris.appendId(builder, System.currentTimeMillis());
        ContentUris.appendId(builder, System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        Cursor cursor = resolver.query(builder.build(), new String[]{}, "selected = 1", null, "begin ASC");
        int titleIndex = cursor.getColumnIndex("title");
        //while(cursor.moveToNext()){
          //  Log.d("INFO", "Title: " + cursor.getString(titleIndex));
        //}

        while(cursor.moveToNext()) {
            members.add(cursor.getString(titleIndex));
        }
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
