package piyu.assign.com.ajm;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yaadesh on 14/8/17.
 */


//ADAPTER FOR SEARCH SUGGESTIONS

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>  {

    ArrayList<String> search_list;
    Activity activity;

    public RecyclerListAdapter(ArrayList<String> search_list,Activity activity) {
        this.search_list = search_list;
        this.activity =activity;


    }
    @Override
    public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_child,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerListAdapter.ViewHolder holder, final int position) {

         final EditText search_edit = (EditText)activity.findViewById(R.id.search_edit);

        holder.text.setText(search_list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_edit.clearFocus();
                search_edit.setText(search_list.get(position).toString());

                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                MainActivity.getImages(search_list.get(position).toString(),activity);
                search_list.clear();
                notifyDataSetChanged();


            }
        }) ;
    }


    @Override
    public int getItemCount() {
        return search_list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        TextView text;



        public ViewHolder(View itemView) {

            super(itemView);

            text =(TextView)itemView.findViewById(R.id.s_text);

        }


    }
}
