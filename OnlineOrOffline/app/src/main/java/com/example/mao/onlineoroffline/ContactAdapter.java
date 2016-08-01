package com.example.mao.onlineoroffline;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MAO on 2016/8/1.
 */
public class ContactAdapter extends RecyclerView.Adapter {
    private Cursor cursor;
    public ContactAdapter(Cursor cursor){
        super();
        this.cursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder Vholder = (ViewHolder) holder;
        cursor.moveToPosition(position);
        Vholder.getName().setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
        Vholder.getNumber().setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView number,name;

        public TextView getName() {
            return name;
        }

        public TextView getNumber() {
            return number;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.isName);
            this.number = (TextView) itemView.findViewById(R.id.isNumber);
        }
    }
}
