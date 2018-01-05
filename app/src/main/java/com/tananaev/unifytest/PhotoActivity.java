package com.tananaev.unifytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_FILE = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        new LoadImageTask(getApplicationContext()) {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                ImageView imageView = findViewById(R.id.image);
                imageView.setImageBitmap(bitmap);
            }
        }.execute(getIntent().getStringExtra(EXTRA_FILE));
    }

}
