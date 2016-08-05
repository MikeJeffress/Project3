package com.example.michaeljeffress.project3.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michaeljeffress.project3.R;
import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;

/**
 * Created by patrickcummins on 8/4/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Business> data;
    private Context context;
    private static OnRecyclerViewItemClickListener recyclerViewOnClickListener;

    public RecyclerViewAdapter(ArrayList<Business> data, OnRecyclerViewItemClickListener listener) {
        this.recyclerViewOnClickListener = listener;

        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<Business>();
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(Business currentBusiness);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView businessImage;
        public TextView businessTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            businessImage = (ImageView) itemView.findViewById(R.id.business_image_imageview);
            businessTitle = (TextView) itemView.findViewById(R.id.business_title_textview);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    recyclerViewOnClickListener.onItemClick(data.get(getLayoutPosition()));
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemLayout = inflater.inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Business currentBusiness = data.get(position);

        TextView title = holder.businessTitle;
        ImageView image = holder.businessImage;


        title.setText(currentBusiness.name());
        Picasso picasso = new Picasso.Builder(holder.businessImage.getContext()).listener(new Picasso.Listener() {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
        picasso.load(currentBusiness.imageUrl()).into(image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Log.d("Adapter", "Success");
            }

            @Override
            public void onError() {
                Log.d("Adapter", "No Success");

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
