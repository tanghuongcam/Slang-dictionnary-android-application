package com.example.slangapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DictionnaryFragment extends Fragment {

    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;
    private ArrayList<String> mSource = new ArrayList<String>();
    public DictionnaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionnary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        dicList = view.findViewById(R.id.dictionnaryList);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,mSource);
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listener != null)
                    listener.onItemClick(mSource.get(position));

            }
        });
    }

    public void resetDataSource(ArrayList<String> source){
        mSource = source;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, source);
        dicList.setAdapter(adapter);
    }

    public void filterValue(String value){
        adapter.getFilter().filter(value);
    }




    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }
    public void setOnFragmentListener(FragmentListener listener){
        this.listener =listener;
    }
}