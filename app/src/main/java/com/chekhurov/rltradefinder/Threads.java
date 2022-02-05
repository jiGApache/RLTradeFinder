package com.chekhurov.rltradefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chekhurov.rltradefinder.popular_items.PopularItemsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public static Thread loadPopularItemsThread (List<RLItem> popularItems, PopularItemsAdapter adapter){
        return new Thread(() -> {
            try {
                Document doc = Jsoup.connect("https://rocket-league.com/items/popular").timeout(120000).get();
                Log.d("TAG", "got the document");
                Elements trendingElements = doc.getElementsByClass("rlg-item__container");

                for (Element src : trendingElements){

                    String itemName = src.select("h2").get(0).text();

                    String itemColor = null;
                    for (Element element : src.select(".rlg-item__paintbadge"))
                        itemColor = element.text();

                    Element elementWithItemImage = src.select(".rlg-item__itemimage").get(0);
                    String itemImageURL = "https://rocket-league.com" + elementWithItemImage.attr("src");

//                    Log.d("TAG", "image: " + itemImageURL + ", color: " + (itemColor != null ?  itemColor : "NONE") + ", item name: " + itemName);

                    popularItems.add(new RLItem(
                            itemName,
                            itemColor,
                            itemImageURL
                    ));
                }
                mainHandler.post(() -> {
//                    Log.d("TAG", popularItems.toString());
                    adapter.notifyDataSetChanged();

                    ImageExecutorService executorService = new ImageExecutorService();
                    for (RLItem item : popularItems){
                        String imageURL = item.getImageURL();
                        executorService.execute(new ImageExecutorService.LoadImageRunnable(imageURL));
                    }
                });
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }



}

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
