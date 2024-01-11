package com.example.molkiyat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<Property> {
    public DetailsAdapter(@NonNull Context context, @NonNull List<Property> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView== null){

        }
        return convertView;
    }
}
