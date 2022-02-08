package com.chekhurov.rltradefinder.Threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chekhurov.rltradefinder.LruCaching.LruImageCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ImageExecutorService {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final int numberOfProcessors = Runtime.getRuntime().availableProcessors();

    private final ExecutorService executorService;

    public ImageExecutorService(){
        executorService = Executors.newFixedThreadPool(numberOfProcessors);
    }

    public void execute(LoadImageRunnable runnable){
        executorService.execute(runnable);
    }


    static class LoadImageRunnable implements Runnable{

        private final String imageURL;

        public LoadImageRunnable(String imageURL){
            this.imageURL = imageURL;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(0);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                Log.d("TAG", "URL: " + imageURL + " | bitmap size: " + bitmap.getByteCount());

                mainHandler.post(() -> LruImageCache.getInstance().addBitmapToMemoryCache(imageURL, bitmap));

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}