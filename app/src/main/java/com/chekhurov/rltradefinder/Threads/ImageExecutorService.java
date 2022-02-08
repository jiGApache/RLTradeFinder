package com.chekhurov.rltradefinder.Threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.chekhurov.rltradefinder.Utils.RLItem;
import com.chekhurov.rltradefinder.Utils.PopularImageAdapterHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ImageExecutorService {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final ExecutorService executorService;

    public ImageExecutorService(){
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numberOfProcessors);
    }

    public void execute(LoadImageRunnable runnable){
        executorService.execute(runnable);
    }


    static class LoadImageRunnable implements Runnable{

        private final RLItem rlItem;

        public LoadImageRunnable(RLItem rlItem){
            this.rlItem = rlItem;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(rlItem.getImageURL());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(30000);
                connection.connect();
                InputStream input = connection.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;

                Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
                rlItem.setImage(bitmap);

//                Log.d("TAG", "URL: " + imageURL + " | bitmap size: " + bitmap.getByteCount());

//                mainHandler.post(() -> LruImageCache.getInstance().addBitmapToMemoryCache(imageURL, bitmap));
                mainHandler.post(() -> {
                    PopularImageAdapterHelper.getInstance().notifyAdapter(rlItem);
                });

            } catch (IOException exception) {
                if (exception instanceof SocketTimeoutException){
                    //ToDo Вставить что-то вместо не скачанной картинки
                }
                exception.printStackTrace();
            }
        }
    }

}