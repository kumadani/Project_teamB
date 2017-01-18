package com.example.t1405031.project_teamb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.provider.MediaStore;
import android.graphics.Bitmap;

public class MainActivity extends AppCompatActivity {

    private final static int RESULT_CAMERA = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button TakePhotoButton = (Button) findViewById(R.id.TakePhotoButton);
        TakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //暗黙的intent(カメラ撮影)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, RESULT_CAMERA);
                //Intent intent =new Intent(getApplication(),TakePhotoActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CAMERA && data != null) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            //Bitmap形式で表示
            //imageView.setImageBitmap(bitmap);

            Intent intent = new Intent(getApplication(), Main2Activity.class);//撮影が完了したら保存確認アクテビティへ.
            intent.putExtra("DATA",bitmap); //撮影した写真を渡す
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}
