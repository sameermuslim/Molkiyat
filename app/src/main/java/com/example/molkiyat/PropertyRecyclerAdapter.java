package com.example.molkiyat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PropertyRecyclerAdapter extends RecyclerView.Adapter<PropertyRecyclerAdapter.ViewHolder>{

    public List<Property> properties;

    public PropertyRecyclerAdapter(List<Property> properties) {
        this.properties=properties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pric_data=properties.get(position).getPrice();
        
        holder.setPreceText(pric_data);
    }

    @Override
    public int getItemCount() {

        return properties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView priceText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setPreceText(String price){
            priceText=mView.findViewById(R.id.price);
            priceText.setText(price);



        }
    }
}






