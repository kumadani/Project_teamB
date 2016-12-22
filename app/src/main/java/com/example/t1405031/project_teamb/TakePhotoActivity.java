package com.example.t1405031.project_teamb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.view.View;

public class TakePhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        //ホーム画面に戻るボタンをクリック
        Button Button = (Button)findViewById(R.id.button);
        Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplication(),Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
