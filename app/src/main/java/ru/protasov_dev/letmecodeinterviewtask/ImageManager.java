package ru.protasov_dev.letmecodeinterviewtask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Тут загрузка изображения по URL, возвращаем Bitmap
public class ImageManager {
    static Bitmap bitmap;
    private final static String TAG = "ImageManager";

    public static Bitmap fetchImage(final String iUrl) {

        final Thread thread = new Thread() {
            @Override
            public void run() {
                bitmap = downloadImage(iUrl);
            }
        };
        thread.setPriority(10);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap downloadImage(String iUrl) {
        bitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream buf_stream = null;
        try {
            Log.v(TAG, "Старт загрузки по URL: " + iUrl);
            conn = (HttpURLConnection) new URL(iUrl).openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
            bitmap = BitmapFactory.decodeStream(buf_stream);
            buf_stream.close();
            conn.disconnect();
            buf_stream = null;
            conn = null;
        } catch (MalformedURLException ex) {
            Log.e(TAG, "Ошибка парсинга URL: " + iUrl);
        } catch (IOException ex) {
            Log.d(TAG, iUrl + " не существует");
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "Недостаточно памяти");
            return null;
        } finally {
            if ( buf_stream != null )
                try { buf_stream.close(); } catch (IOException ex) {}
            if ( conn != null )
                conn.disconnect();
        }
        return bitmap;
    }
}