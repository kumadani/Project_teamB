package com.example.t1405031.project_teamb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button TakePhotoButton = (Button) findViewById(R.id.TakePhotoButton);
        TakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int requestCode = 0;
                Intent intent = new Intent(getApplication(), TakePhotoActivity.class);//撮影アクテビティのクラスへ.
                startActivity(intent);

                //Intent intent2 = new Intent(getApplication(), Main2Activity.class);//撮影が完了したら保存確認アクテビティへ.
                //startActivity(intent2);
                //requestCode = 1000;
                //startActivityForResult(intent2, requestCode);
            }
        });

        Button EditButton = (Button) findViewById(R.id.EditButton);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), EditFolderActivity.class);//編集アクテビティのクラスへ.
                startActivity(intent);
            }
        });

        /*
        Button LogoutButton = (Button)findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),hoge.class);//ログインアクテビティのクラスへ
                startActivity(intent);
            }
        });
        */
    }

    /*
    protected void onActivityResult(int requestCode,String resultCode,Intent intent){

        int requestCode2;
        if(requestCode == 1000){
            if(resultCode == "RETURN"){

                Intent intent3 = new Intent(getApplication(),Main3Activity.class);
                requestCode2 = 2000;
                startActivityForResult(intent3,requestCode2);
            }else if(resultCode == "TAKE_PHOTO"){

            }
        }
    }
    */
}
