package com.example.t1405031.project_teamb;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.database.Cursor;
import android.content.*;
import android.net.Uri;

import android.support.v4.app.ActivityCompat;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.util.Log;

public class ListViewEvent extends AppCompatActivity {

    ListView listview;
    ArrayList<String> members = new ArrayList<String>();
    Account account = AccountManager.get(this).getAccounts()[0];


    private static final String[] CALENDAR_PROJECTION = new String[] {
            Calendars._ID,
            Calendars.NAME,
            Calendars.ACCOUNT_NAME,
            Calendars.ACCOUNT_TYPE,
            //Calendars.CALENDAR_COLOR,
            //Calendars.CALENDAR_DISPLAY_NAME,
            //Calendars.CALENDAR_ACCESS_LEVEL,
            //Calendars.CALENDAR_TIME_ZONE,
            //Calendars.VISIBLE,
            //Calendars.SYNC_EVENTS,
            Calendars.OWNER_ACCOUNT,
    };
    public static final int CALENDAR_PROJECTION_IDX_ID =0;
    public static final int CALENDAR_PROJECTION_IDX_NAME =1;
    public static final int CALENDAR_PROJECTION_IDX_ACCOUNT_NAME =2;
    public static final int CALENDAR_PROJECTION_IDX_ACCOUNT_TYPE =3;
    public static final int CALENDAR_PROJECTION_IDX_OWNER_ACCOUNT =4;


    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };
    public static final int EVENT_PROJECTION_IDX_TITLE =0;
    public static final int EVENT_PROJECTION_IDX_DTSTART =1;
    public static final int EVENT_PROJECTION_IDX_DTEND =2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] projection = CALENDAR_PROJECTION;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {account.name, account.type, account.name};
        String sortOrder = null;

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        while(cur.moveToNext()) {
            Uri uri2 = CalendarContract.Events.CONTENT_URI;
            String[] projection2 = EVENT_PROJECTION;
            String selection2 = "((" + CalendarContract.Events.CALENDAR_ID + " = ?) AND ("
                    + CalendarContract.Events.DTSTART + " < ?) AND ("
                    + CalendarContract.Events.DTEND + " > ?))";
            String[] selectionArgs2 = new String[]{
                    cur.getString(CALENDAR_PROJECTION_IDX_ID),
                    String.valueOf(System.currentTimeMillis()),
                    String.valueOf(System.currentTimeMillis())};
            String sortOrder2 = null;

            ContentResolver cr2 = getContentResolver();
            Cursor cur2 = cr2.query(uri2, EVENT_PROJECTION, selection2, selectionArgs2, null);
            if (cur == null) {
                Intent intent = new Intent();
                intent.putExtra("SELECTED_EVENT", "エラーがおきました");
                setResult(RESULT_OK, intent);
                finish();
            } else if (cur.getCount() < 1) {
                Intent intent = new Intent();
                intent.putExtra("SELECTED_EVENT", "該当するイベントが存在しませんでした.");
                setResult(RESULT_OK, intent);
                finish();
            } else while (cur2.moveToNext()) {
                members.add(cur2.getString(EVENT_PROJECTION_IDX_TITLE));
            }
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
