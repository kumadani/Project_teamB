package com.example.t1405031.project_teamb;


import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import android.graphics.Bitmap;

public class Main2Activity extends AppCompatActivity {


    private final static int RESULT_CAMERA = 1001;
    private final static int RESULT_SELECT_EVENT = 1002;

    private String Event = null;//選択したイベント名を入れる場所




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        Bitmap bmp = (Bitmap)intent.getParcelableExtra("DATA");

        //ホーム画面に戻るボタンをクリック
        Button ReturnButton = (Button) findViewById(R.id.ReturnButton);
        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //再撮影ボタンをクリック
        Button TakePhotoButton = (Button) findViewById(R.id.TakePhotoButton);
        TakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //暗黙的intent(カメラ撮影)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,RESULT_CAMERA);
                //Intent intent = new Intent(getApplication(), TakePhotoActivity.class);//撮影画面のアクテビティへ.
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
            }
        });

        //Yesボタンをクリック
        Button UploadButton = (Button) findViewById(R.id.UproadButton);
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Event != null) {
                    //Driveへのアップロード処理


                    Intent intent = new Intent(getApplication(), Main3Activity.class);//保存完了アクテビティのクラスへ.
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //finish();
                } else {
                    TextView textview3 = (TextView) findViewById(R.id.textView3);
                    textview3.setText("イベントが選択されてません.");
                }
            }
        });

        //他のフォルダに保存ボタンをクリック
        Button SelectFolderButton = (Button) findViewById(R.id.SelectFolderButton);
        SelectFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SelectFolderActivity.class);//フォルダ選択アクテビティのクラスへ.
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //該当イベントを選択ボタンをクリック
        Button SelectEventButton = (Button) findViewById(R.id.SelectEventButton);
        SelectEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ListViewEvent.class);//リストビューアクテビティのクラスへ.
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,RESULT_SELECT_EVENT);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case RESULT_CAMERA: {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                    Intent intent = new Intent(getApplication(), Main2Activity.class);//撮影が完了したら保存確認アクテビティへ.
                    intent.putExtra("DATA", bitmap); //撮影した写真を渡す
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                }
                case RESULT_SELECT_EVENT: {
                    String res = data.getStringExtra("SELECTED_EVENT");
                    Event = data.getStringExtra("SELECTED_EVENT");
                    TextView textview3 = (TextView) findViewById(R.id.textView3);
                    textview3.setText("選択中のイベント:" + res);
                    break;
                }
                case RESULT_CANCELED: {
                    String res = data.getStringExtra("ERROR_SENTENCE");
                    TextView textview3 = (TextView) findViewById(R.id.textView3);
                    textview3.setText(res);
                    break;
                }
            }
        }
    }
}
