package ca.algomau.imagesaver;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CategoryFragment extends ListFragment {

    public CategoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        String [] myArray = getResources().getStringArray(R.array.Categories);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.category_list_fragment,
                myArray);
        setListAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        ((SelectionScreen)getActivity()).loadImages(pos);
    }
}
