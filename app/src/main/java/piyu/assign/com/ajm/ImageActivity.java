package piyu.assign.com.ajm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity  {

    ImageView imageView;
    TextView desc;

    int position;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final ArrayList<ImageHolder> images_list = getIntent().getExtras().getParcelableArrayList("image_array");
        position = getIntent().getExtras().getInt("pos");



        imageView=(ImageView)findViewById(R.id.enlarged_image);
        desc=(TextView)findViewById(R.id.image_text);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        Glide.with(getBaseContext())
                .load(images_list.get(position).getContent_url())
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        desc.setText(images_list.get(position).getName());
        //date.setText(images_list.get(position).getDate_published().toString());


        View view = findViewById(R.id.image_act);
        view.setOnTouchListener(new OnSwipeTouchListener(ImageActivity.this) {


            public void onSwipeTop() {
                //Toast.makeText(ImageActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(ImageActivity.this, "right", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                if(position>=0)
                    Glide.with(getBaseContext())
                            .load(images_list.get(--position).getContent_url())

                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageView);
                desc.setText(images_list.get(position).getName());

            }
            public void onSwipeLeft() {
                //Toast.makeText(ImageActivity.this, "left", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                if(position<images_list.size()-1)
                    Glide.with(getBaseContext())
                            .load(images_list.get(++position).getContent_url())

                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageView);
                desc.setText(images_list.get(position).getName());

            }
            public void onSwipeBottom() {
                //Toast.makeText(ImageActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });



    }




}

