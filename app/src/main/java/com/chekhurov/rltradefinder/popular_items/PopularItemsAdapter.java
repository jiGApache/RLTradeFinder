package com.chekhurov.rltradefinder.popular_items;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chekhurov.rltradefinder.Utils.RLItem;
import com.chekhurov.rltradefinder.Utils.PopularImageAdapterHelper;
import com.chekhurov.rltradefinder.databinding.PopularItemLayoutBinding;

import java.util.List;

public class PopularItemsAdapter
        extends RecyclerView.Adapter<PopularItemsAdapter.PopularItemViewHolder>{

    private List<RLItem> popularItems;

    public PopularItemsAdapter(List<RLItem> popularItems){
        this.popularItems = popularItems;
    }

    @NonNull
    @Override
    public PopularItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularItemViewHolder(PopularItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PopularItemViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return popularItems.size();
    }

    class PopularItemViewHolder extends RecyclerView.ViewHolder{

        private PopularItemLayoutBinding binding;

        public PopularItemViewHolder(@NonNull PopularItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(int position){
            String name = popularItems.get(position).getName();
            this.binding.popularItemName.setText(name);

            String color = popularItems.get(position).getColor();
            this.binding.popularItemColor.setText(color != null ? color : "");

            Bitmap image = popularItems.get(position).getImage();
            if (image != null) {
                this.binding.popularImageView.setImageBitmap(image);
            }
            else {
                Log.d("TAG", "addToWaitingQueue, pos: " + position);
                PopularImageAdapterHelper.getInstance().addToWaitingQueue(popularItems.get(position), position);
            }
        }

    }

    public List<RLItem> getPopularItems() {
        return popularItems;
    }
}
