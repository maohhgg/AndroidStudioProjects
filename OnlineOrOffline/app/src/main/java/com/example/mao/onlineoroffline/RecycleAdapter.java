package com.example.mao.onlineoroffline;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MAO on 2016/7/27.
 */
class RecycleAdapter extends RecyclerView.Adapter{
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,name,passwd,time;
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.user_id);
            name = (TextView) itemView.findViewById(R.id.user_name);
            passwd = (TextView) itemView.findViewById(R.id.user_passwd);
            time = (TextView) itemView.findViewById(R.id.user_time);
        }

        public TextView getId() {
            return id;
        }

        public TextView getName() {
            return name;
        }

        public TextView getPasswd() {
            return passwd;
        }

        public TextView getTime() {
            return time;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder Vholder = (ViewHolder) holder;

        cursor.moveToPosition(position);
        Vholder.getId().setText(cursor.getInt(cursor.getColumnIndex("_id")) + "");
        Vholder.getName().setText(cursor.getString(cursor.getColumnIndex("name")));
        Vholder.getTime().setText(cursor.getString(cursor.getColumnIndex("time")));
        Vholder.getPasswd().setText(cursor.getString(cursor.getColumnIndex("password")));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    private Cursor cursor;

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    public void addData(Cursor cursor,int position) {
        this.cursor = cursor;
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        notifyItemRemoved(position);
    }
}
