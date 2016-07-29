package com.example.mao.cardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecycleAdapter extends RecyclerView.Adapter {
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,content;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.content = (TextView) itemView.findViewById(R.id.content);
        }

        public TextView getContent() {
            return content;
        }

        public TextView getTitle() {
            return title;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.getTitle().setText("Title" + position);
        vh.getContent().setText("this Content" + position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
