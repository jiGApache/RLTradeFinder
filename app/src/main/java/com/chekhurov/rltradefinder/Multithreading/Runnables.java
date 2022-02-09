package com.chekhurov.rltradefinder.Multithreading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.chekhurov.rltradefinder.PopularItems.PopularItemsAdapter;
import com.chekhurov.rltradefinder.Utils.PopularImageAdapterHelper;
import com.chekhurov.rltradefinder.Utils.RLItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Runnables {



    private static final Handler mainHandler = new Handler(Looper.getMainLooper());



    public static class LoadPopularRunnable implements Runnable{

        private final PopularItemsAdapter adapter;

        private final String RL_BASE_URL = "https://rocket-league.com";
        private final String POPULAR_URL = "/items/popular";
        private final String TRENDING_ITEMS_CONTAINER = "rlg-item__container";
        private final String ITEM_COLOR_CONTAINER = ".rlg-item__paintbadge";
        private final String ITEM_NAME_CONTAINER = "h2";
        private final String ITEM_IMAGE_CONTAINER = ".rlg-item__itemimage";


        public LoadPopularRunnable(PopularItemsAdapter adapter){
            this.adapter = adapter;
        }

        @Override
        public void run() {
            try {
                Connection connection = Jsoup.connect(RL_BASE_URL + POPULAR_URL);
                Document doc = connection.get();
                Elements trendingElements = doc.getElementsByClass(TRENDING_ITEMS_CONTAINER);

                for (Element src : trendingElements){

                    String itemName = src.select(ITEM_NAME_CONTAINER).get(0).text();

                    String itemColor = null;
                    for (Element element : src.select(ITEM_COLOR_CONTAINER))
                        itemColor = element.text();

                    Element elementWithItemImage = src.select(ITEM_IMAGE_CONTAINER).get(0);
                    String itemImageURL = RL_BASE_URL + elementWithItemImage.attr("src");

                    adapter.getPopularItems().add(new RLItem(
                            itemName,
                            itemColor,
                            itemImageURL
                    ));
                }
                mainHandler.post(() -> {
                    adapter.notifyDataSetChanged();

                    ExecutorService executorService = new ExecutorService(ExecutorService.LOAD_POPULAR_IMAGES);
                    for (RLItem item : adapter.getPopularItems()){
                        executorService.execute(new LoadImageRunnable(item));
                    }
                });
            } catch (IOException ioException) {
                if (ioException instanceof SocketTimeoutException){
                    //ToDO показать сообщение об ошибке загрузки данных и предложить повторить
                }
                ioException.printStackTrace();
            }
        }
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
