package com.example.t1405031.project_teamb;

import android.Manifest;
import android.accounts.AccountManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.widget.*;
import android.view.View;
import android.content.Intent;

import android.provider.CalendarContract;
import static android.provider.CalendarContract.Calendars;
import android.database.Cursor;
import android.net.Uri;

import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;


public class ListViewEvent extends AppCompatActivity {

    ListView listview;
    ArrayList<String> members = new ArrayList<String>();

    private static final int RESULT_SELECT_ACCOUNT = 3000;
    private static final int RESULT_PERMISSION = 4000;

    private static final String[] CALENDAR_PROJECTION = new String[]{
            Calendars._ID,
            Calendars.NAME,
            Calendars.ACCOUNT_NAME,
            Calendars.ACCOUNT_TYPE,
    };
    public static final int CALENDAR_PROJECTION_IDX_ID = 0;

    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };
    public static final int EVENT_PROJECTION_IDX_TITLE = 0;

    Uri uri = CalendarContract.Calendars.CONTENT_URI;
    String[] projection = CALENDAR_PROJECTION;
    String selection = null;
    String[] selectionArgs = null;
    String sortOrder = null;
    Cursor cur;

    Uri uri2 = CalendarContract.Events.CONTENT_URI;
    String[] projection2 = EVENT_PROJECTION;
    String selection2 = null;
    String[] selectionArgs2 = null;
    String sortOrder2 = null;
    Cursor cur2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //アカウントを選択するActivityを起動
        Intent intent = AccountManager.newChooseAccountIntent(null,null,new String[]{"com.google"},true,null,null,null,null);
        startActivityForResult(intent,RESULT_SELECT_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            //アカウントの選択結果
            case RESULT_SELECT_ACCOUNT:{
                String name = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                String type = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

                setCursorForCalendars(name,type);

                // permission check
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CALENDAR)==PackageManager.PERMISSION_GRANTED) {
                    //すでに許可されていた場合の処理
                    cur = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

                    while(cur!= null && cur.moveToNext()) {
                        setCursorForEvents();
                        cur2 = getContentResolver().query(uri2, projection2, selection2, selectionArgs2, sortOrder2);
                        while (cur2 != null && cur2.getCount() > 0 && cur2.moveToNext())
                            members.add(cur2.getString(EVENT_PROJECTION_IDX_TITLE));
                    }
                    if(members.isEmpty()){
                        cur.close();
                        cur2.close();
                        Intent intent = new Intent();
                        intent.putExtra("SELECTED_EVENT", "該当するイベントが存在しませんでした.");
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                } else {
                    // 許可されていなかった場合の処理
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, RESULT_PERMISSION);
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, RESULT_PERMISSION);
                    }
                }
            }
        }
        showActivity();
    }

    //カレンダーのパーミッションの要請結果
    //許可された場合,カーソルを取得して該当するイベントを検索してリストに入れて表示
    //拒否された場合,アクティビティを閉じてエラーメッセージを表示
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //許可された場合の処理
                    cur = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

                    while(cur!= null &&cur.moveToNext()){
                        setCursorForEvents();
                        cur2 = getContentResolver().query(uri2, projection2, selection2, selectionArgs2, sortOrder2);
                        while (cur2 != null && cur2.getCount() > 0 && cur2.moveToNext())
                            members.add(cur2.getString(EVENT_PROJECTION_IDX_TITLE));
                    }
                    if(members.isEmpty()){
                        cur.close();
                        cur2.close();
                        Intent intent = new Intent();
                        intent.putExtra("ERROR_SENTENCE", "該当するイベントが存在しませんでした.");
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                } else {
                    //拒否された場合の処理
                    cur.close();
                    cur2.close();
                    Intent intent = new Intent();
                    intent.putExtra("ERROR_SENTENCE", "カレンダーへのアクセスが拒否されました.");
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
                break;
            }
        }
    showActivity();
    }

    private void setCursorForCalendars(String name, String type){
        selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?))";
        selectionArgs = new String[] {name, type};
    }

    private void setCursorForEvents(){
        selection2 = "((" + CalendarContract.Events.CALENDAR_ID + " = ?) AND ("
                + CalendarContract.Events.DTSTART + " < ?) AND ("
                + CalendarContract.Events.DTEND + " > ?))";
        selectionArgs2 = new String[]{
                cur.getString(CALENDAR_PROJECTION_IDX_ID),
                String.valueOf(System.currentTimeMillis()),
                String.valueOf(System.currentTimeMillis())};
    }

    private void showActivity(){
        listview = (ListView)findViewById(R.id.ListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, members);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent,View view,int position,long id){
                cur.close();
                cur2.close();
                Intent intent = new Intent();
                intent.putExtra("SELECTED_EVENT",members.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
