package com.tananaev.unifytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;

    public LoadImageTask(Context context) {
        mContext = context;
    }

    @Override
    protected Bitmap doInBackground(String... files) {
        FileInputStream stream = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new KeyManager().getKey());
            byte[] bytes = new byte[(int) new File(mContext.getFilesDir() + "/" + files[0]).length()];
            stream = mContext.openFileInput(files[0]);
            stream.read(bytes);
            stream.close();
            byte[] decoded = cipher.doFinal(bytes);
            return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        } catch (GeneralSecurityException | IOException e) {
            Log.w(LoadImageTask.class.getSimpleName(), e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.w(LoadImageTask.class.getSimpleName(), e);
                }
            }
        }
        return null;
    }

}
