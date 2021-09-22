package piyu.assign.com.ajm;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

   RecyclerView recyclerView;
   ArrayList<String> search_list;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Bundle bundle= this.getArguments();


        search_list= bundle.getStringArrayList("search_array");
        System.out.println(search_list);

        View root_view =inflater.inflate(R.layout.fragment_search_suggestion, container, false);

        recyclerView =(RecyclerView) root_view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        RecyclerListAdapter mAdapter =new RecyclerListAdapter(search_list,getActivity());
        recyclerView.setAdapter(mAdapter);

        System.out.println("AT END OF FRAGMENT");
        return root_view;
    }

}
