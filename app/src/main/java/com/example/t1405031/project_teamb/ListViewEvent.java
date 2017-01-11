package com.example.t1405031.project_teamb;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.*;
import android.view.View;
import android.content.Intent;

import android.provider.CalendarContract;
import android.provider.CalendarContract.Instances;
//import java.util.Calendar;
import android.database.Cursor;
import android.content.*;
import android.net.Uri;
import android.util.Log;

public class ListViewEvent extends AppCompatActivity {

    ListView listview;
    ArrayList<String> members = new ArrayList<String>();

    //Account account = AccountManager.get(this).getAccounts()[0];
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        /*
        String[] EVENT_PROJECTION = new String[] {
                Calendars._ID,                           // 0
                Calendars.ACCOUNT_NAME,                  // 1
                Calendars.CALENDAR_DISPLAY_NAME,         // 2
                Calendars.OWNER_ACCOUNT                  // 3
        };

        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";

        String[] selectionArgs = new String[] {account.name, account.type,""};

        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        */

        String[] Projection = {
                CalendarContract.Instances.EVENT_ID,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END
        };

        String[] INSTANCE_PROJECTION = new String[] {
                Instances.EVENT_ID,      // 0
                Instances.BEGIN,         // 1
                Instances.TITLE          // 2
        };

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2011, 9, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2011, 10, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();

        Cursor cur = null;
        ContentResolver cr = getContentResolver();

        String selection = Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {"207"};

        Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        cur =  cr.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null);

        /*
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
        if(cursor.getCount() < 1){
            Intent intent = new Intent();
            intent.putExtra("SELECTED_EVENT", "現在時刻に登録されているイベントがみつかりませんでした.");
            finish();
        }else if(cursor == null){
            finish();
        }else{
            while (cursor.moveToNext()) {
                members.add(cursor.getString(titleIndex));
            }
        }
        */

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
