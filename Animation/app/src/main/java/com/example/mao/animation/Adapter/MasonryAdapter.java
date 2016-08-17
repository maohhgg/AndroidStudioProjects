package com.example.mao.animation.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mao.animation.R;
import com.example.mao.animation.Other.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by maohh on 2016/8/16.
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView>{
    private MyItemClickListener mItemClickListener;

    public MasonryAdapter() {

    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.masonry_item, viewGroup, false);
        return new MasonryView(view,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        Product product = Product.Product[position];
        Picasso.with(masonryView.imageView.getContext()).load(product.getImg()).into(masonryView.imageView);
        masonryView.textView.setText(product.getTitle());
    }

    @Override
    public int getItemCount() {
        return Product.Product.length;
    }
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public static class MasonryView extends  RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        private MyItemClickListener mListener;

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,Product.Product[getPosition()]);
            }
        }
        public MasonryView(View itemView ,MyItemClickListener listener){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

    }

    public interface MyItemClickListener {
        public void onItemClick(View view, Product postion);
    }

}