package com.technoidtintin.android.moviesmela.ui.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.Adapters.ItemListAdapter;
import com.technoidtintin.android.moviesmela.Model.HomeItem;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>  {

    private static final String TAG = HomeAdapter.class.getSimpleName();
    private Context context;
    private List<HomeItem> homeItemList = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.main_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        HomeItem homeItem = homeItemList.get(position);
        holder.itemTypename.setText(homeItem.getTypeTitle());
        List<ListItem> listItemList = homeItem.getListItemList();
        if (listItemList != null){
            ItemListAdapter itemListAdapter = new ItemListAdapter(context);
            holder.itemTypeRecyclerView.setHasFixedSize(true);
            holder.itemTypeRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            holder.itemTypeRecyclerView.setAdapter(itemListAdapter);
            itemListAdapter.setListItemList(listItemList);
        }else {
            Log.e(TAG,"Movie Item list is null");
        }
    }

    @Override
    public int getItemCount() {
        return homeItemList.size();
    }

    public void setHomeItemList(List<HomeItem> itemList) {

        homeItemList = itemList;
        notifyDataSetChanged();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{

        TextView itemTypename;
        RecyclerView itemTypeRecyclerView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTypename = itemView.findViewById(R.id.item_type_name);
            itemTypeRecyclerView = itemView.findViewById(R.id.item_type_recycler);
        }
    }
}
