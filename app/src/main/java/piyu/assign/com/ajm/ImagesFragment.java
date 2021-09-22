package piyu.assign.com.ajm;


//import android.app.Fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.widget.LinearLayout;

        import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImagesFragment extends Fragment {


    RecyclerView recyclerView ;
    ArrayList<ImageHolder> images_list ;
    public ImagesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        System.out.println("IN CREATEVIEW FRAGMENT");

        Bundle bundle= this.getArguments();
        images_list= bundle.getParcelableArrayList("images_arraylist");
        //System.out.println(images_list.get(130).getDate_published());



        View root_view =inflater.inflate(R.layout.fragment_images, container, false);
        recyclerView =(RecyclerView) root_view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);


        StaggeredGridLayoutManager stag_grid = new StaggeredGridLayoutManager(getContext().getResources().getInteger(R.integer.grid_cols), LinearLayout.VERTICAL);
        //StaggeredGridView etsy_stag = new StaggeredGridView(getContext());
        stag_grid.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(stag_grid);
 

       RecyclerAdapter mAdapter =new RecyclerAdapter(images_list,getActivity());
        recyclerView.setAdapter(mAdapter);

        System.out.println("AT END OF FRAGMENT");
        return root_view;
    }

}
