package com.chekhurov.rltradefinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chekhurov.rltradefinder.databinding.FragmentPopularBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment {

    private FragmentPopularBinding binding;
    PopularItemsAdapter adapter;
    private List<RLItem> popularItems;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularItems = new ArrayList<>();

        adapter = new PopularItemsAdapter(popularItems);
        binding.rvTrending.setAdapter(adapter);
        binding.rvTrending.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false)
        );

        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onStart() {
        super.onStart();

        Thread parseTrending = new Thread( () -> {
            try {
                Document doc = Jsoup.connect("https://rocket-league.com/items/popular").get();
                Elements trendingElements = doc.getElementsByClass("rlg-item__container");

                for (Element src : trendingElements){
                    Element elementWithItemImage = src.select(".rlg-item__itemimage").get(0);
                    String itemImageURL = "https://rocket-league.com" + elementWithItemImage.attr("src");

                    String itemColor = null;
                    for (Element element : src.select(".rlg-item__paintbadge"))
                        itemColor = element.text();

                    String itemName = src.select("h2").get(0).text();

//                    Log.d("TAG", "image: " + itemImageURL + ", color: " + (itemColor != null ?  itemColor : "NONE") + ", item name: " + itemName);

                    popularItems.add(new RLItem(
                            itemName,
                            itemColor
                    ));
                }
                handler.post(() -> {
                    Log.d("TAG", popularItems.toString());
                    adapter.notifyDataSetChanged();
                });
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        parseTrending.start();
    }
}
