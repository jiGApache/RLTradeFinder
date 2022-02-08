package com.chekhurov.rltradefinder.Utils;

import android.util.Log;

import com.chekhurov.rltradefinder.popular_items.PopularItemsAdapter;

import java.util.HashMap;
import java.util.Map;

public class PopularImageAdapterHelper {


    private static Map<RLItem, Integer> waitingQueue;
    private static volatile PopularImageAdapterHelper instance;
    private static PopularItemsAdapter adapter;

    private PopularImageAdapterHelper() {
        waitingQueue = new HashMap<>();
    }

    public static PopularImageAdapterHelper getInstance(){
        //ToDo бросить исключение если не настроен адаптер
        PopularImageAdapterHelper localInstance = instance;
        if (localInstance == null){
            synchronized (PopularImageAdapterHelper.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new PopularImageAdapterHelper();
                }
            }
        }
        return localInstance;
    }

    public void setUpAdapter(PopularItemsAdapter adapter){
        PopularImageAdapterHelper.adapter = adapter;
    }

    public  void addToWaitingQueue(RLItem rlItem, int position){
        waitingQueue.put(rlItem, position);
    }

    public  void notifyAdapter(RLItem rlItem){
        Integer position = waitingQueue.remove(rlItem);
        if (position != null) {
            Log.d("TAG", "notifyAdapter, pos: " + position);
            adapter.notifyItemChanged(position);
        }
    }
}

