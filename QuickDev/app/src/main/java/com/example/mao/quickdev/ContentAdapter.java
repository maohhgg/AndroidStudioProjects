package com.example.mao.quickdev;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.example.mao.quickdev.util.StringMatcher;

import java.util.List;

class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {

    private String mSection = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ContentAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }


    @Override
    public Object[] getSections() {
        String[] section = new String[mSection.length()];
        for (int i = 0; i < mSection.length(); i++) {
            section[i] = String.valueOf(mSection.charAt(i));
        }
        return section;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {

        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    for (int k = 0; k <= 9; k++){
                        if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)),String.valueOf(k)) ){
                            return j;
                        }
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)),String.valueOf(mSection.charAt(i))) ){
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
