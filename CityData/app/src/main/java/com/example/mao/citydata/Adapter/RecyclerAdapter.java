package com.example.mao.citydata.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mao.citydata.DB.Model.City;
import com.example.mao.citydata.R;

import java.util.List;

/**
 * Created by maohh on 2016/8/25.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    private Context mContent;
    private List<City> dataList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RecyclerAdapter(Context content, List<City> list){
        mContent = content;
        dataList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.item_cell,parent,false);
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.getCityText().setText(dataList.get(position).ZoneName);
        vh.getCityKey().setText(dataList.get(position).CityKey);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView cityText,cityKey;
        private OnRecyclerViewItemClickListener mlistener = null;

        public ViewHolder(View itemView,OnRecyclerViewItemClickListener listener) {
            super(itemView);
            cityText = (TextView) itemView.findViewById(R.id.item_city);
            cityKey = (TextView) itemView.findViewById(R.id.item_city_key);
            this.mlistener = listener;
            itemView.setOnClickListener(this);
        }

        public TextView getCityText() {
            return cityText;
        }

        public TextView getCityKey() {
            return cityKey;
        }

        @Override
        public void onClick(View view) {
            if (mlistener != null){
                mlistener.onItemClick(view,getPosition());
            }
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }
}
