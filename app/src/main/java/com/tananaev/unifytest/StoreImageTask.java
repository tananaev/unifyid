package com.tananaev.unifytest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

public class StoreImageTask extends AsyncTask<byte[], Void, Void> {

    private Context mContext;

    public StoreImageTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(byte[]... bytes) {
        FileOutputStream stream = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new KeyManager().getKey());
            stream = mContext.openFileOutput(String.valueOf(System.currentTimeMillis()), Context.MODE_PRIVATE);
            stream.write(cipher.doFinal(bytes[0]));
            stream.close();
        } catch (GeneralSecurityException | IOException e) {
            Log.w(StoreImageTask.class.getSimpleName(), e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.w(StoreImageTask.class.getSimpleName(), e);
                }
            }
        }
        return null;
    }

}
