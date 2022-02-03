package com.chekhurov.rltradefinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chekhurov.rltradefinder.databinding.PopularItemLayoutBinding;

import java.util.List;

public class PopularItemsAdapter extends RecyclerView.Adapter<PopularItemsAdapter.PopularItemViewHolder> {

    private PopularItemLayoutBinding binding;
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
        }

    }

}
