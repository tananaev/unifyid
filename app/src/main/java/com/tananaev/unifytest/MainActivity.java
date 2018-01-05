package com.tananaev.unifytest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.cameraview.CameraView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 1;

    private static final int CAPTURE_NUMBER = 10;
    private static final long CAPTURE_INTERVAL = 500;

    private View mCaptureButton;
    private CameraView mCameraView;
    private boolean mCameraStarted;

    private Handler mHandler;

    private int mCaptureCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();

        mCameraView = findViewById(R.id.camera);
        mCameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                new StoreImageTask(getApplicationContext()).doInBackground(data);
                if (++mCaptureCount < CAPTURE_NUMBER) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCameraView.takePicture();
                        }
                    }, CAPTURE_INTERVAL);
                } else {
                    Toast.makeText(MainActivity.this, R.string.capture_complete, Toast.LENGTH_LONG).show();
                    mCaptureButton.setEnabled(true);
                }
            }
        });

        mCaptureButton = findViewById(R.id.capture);
        mCaptureButton.setEnabled(false);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCaptureButton.setEnabled(false);
                mCaptureCount = 0;
                mCameraView.takePicture();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // TODO: show an explanation to the user and try again
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
            }
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                finish(); // TODO: better error handing
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCamera();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void startCamera() {
        if (!mCameraStarted) {
            mCameraView.start();
            mCaptureButton.setEnabled(true);
            mCameraStarted = true;
        }
    }

    private void stopCamera() {
        if (mCameraStarted) {
            mCameraView.stop();
            mCaptureButton.setEnabled(false);
            mCameraStarted = false;
        }
    }

}
