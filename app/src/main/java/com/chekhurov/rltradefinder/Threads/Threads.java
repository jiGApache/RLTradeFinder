package com.chekhurov.rltradefinder.Threads;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chekhurov.rltradefinder.RLItem;
import com.chekhurov.rltradefinder.popular_items.PopularItemsAdapter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

public class Threads {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public static Thread loadPopularItemsThread (List<RLItem> popularItems, PopularItemsAdapter adapter){
        return new Thread(() -> {
            try {
                Connection connection = Jsoup.connect("https://rocket-league.com/items/popular");
                Document doc = connection.get();
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
                if (ioException instanceof SocketTimeoutException){
                    //ToDO показать сообщение об ошибке загрузки данных и предложить повторить
                }
            }
        });
    }
}


