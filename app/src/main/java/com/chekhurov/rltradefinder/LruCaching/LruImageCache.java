package com.chekhurov.rltradefinder.LruCaching;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.chekhurov.rltradefinder.popular_items.PopularItemsAdapter;

import java.util.HashMap;
import java.util.Map;

public class LruImageCache{

    private static volatile LruImageCache instance;

    private final LruCache<String, Bitmap> lruCache;
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;

    PopularItemsAdapter adapter;
    private final Map<String, Integer> subscribedElementsOfRV;

    public static LruImageCache getInstance(){
        LruImageCache localInstance = instance;
        if (localInstance == null) {
            synchronized (LruImageCache.class) {
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new LruImageCache();
                }
            }
        }
        return localInstance;
    }

    private LruImageCache(){
        Log.d("TAG", "cache size: " + cacheSize);
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        subscribedElementsOfRV = new HashMap<>();
    }

    public void addBitmapToMemoryCache(String key, Bitmap value){
        if (getImageByURL(key) == null){
            lruCache.put(key, value);

            Integer elementPosition = subscribedElementsOfRV.get(key);
            if (elementPosition != null){
                Log.d("TAG", "SETTLING on position: " + elementPosition);
                adapter.notifyItemChanged(subscribedElementsOfRV.remove(key));
            }
        }
    }

    public Bitmap getImageByURL(String key){
        return lruCache.get(key);
    }

    public void addToWaitingQueue(PopularItemsAdapter adapter, int elementPosition, String key){
        if (this.adapter != adapter) this.adapter = adapter;
        subscribedElementsOfRV.put(key, elementPosition);
        Log.d("TAG", "EMPTY position: " + elementPosition);
    }
}
