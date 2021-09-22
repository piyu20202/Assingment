package piyu.assign.com.ajm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.percentlayout.widget.PercentFrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yaadesh on 13/8/17.
 */

/* ADAPTER FOR IMAGES*/

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<ImageHolder> images_list ;
    Activity activity;
    public RecyclerAdapter(ArrayList<ImageHolder> images_list,Activity activity) {
        this.images_list = images_list;
        this.activity =activity;
       // System.out.println(images_list.get(130).getName());

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
       // System.out.println(images_list.get(position).getThumb_url());

        PercentFrameLayout.LayoutParams layoutParams = (PercentFrameLayout.LayoutParams) holder.image.getLayoutParams();
        layoutParams.getPercentLayoutInfo().aspectRatio = (float)images_list.get(position).getThumb_width() / (float)images_list.get(position).getThumb_height();
        holder.image.setLayoutParams(layoutParams);
        Glide
            .with(activity)
            .load(images_list.get(position).getThumb_url())
            .into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("image_array",images_list);
                bundle.putInt("pos",position);
                Intent intent = new Intent(activity,ImageActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);


            }
        }) ;


    }

    @Override
    public int getItemCount() {
        return images_list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image =(ImageView)itemView.findViewById(R.id.image);
        }


    }
}
