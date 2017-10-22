package com.example.android.securityapp;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ramana on 10/6/2017.
 */

public class ComplaintsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public ComplaintsFragment(){
        //
    }
    public static ComplaintsFragment newInstance(int sectionNumber) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View listv = inflater.inflate(R.layout.complaints_list,container,false);
        RecyclerView rv = (RecyclerView) listv.findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        final ArrayList<Complaint> complaints = new ArrayList<Complaint>();

        complaints.add(new Complaint("Ear rings lost","My gold ear ring is lost which has two hangings and two balls weighing 3 grams.If anyone finds please contact 8908583616","processing","115CS0244","11:00 AM"));
        complaints.add(new Complaint("Loss of Cycle Keys","My cycle keys is lost which has red belt.If anyone finds please contact 8908583616","processing","115EC0368","Oct 5th"));
        complaints.add(new Complaint("Iphone6 lost","My Iphone of gray color is lost yesterday in LA .If anyone finds please contact 8908583616","processing","115EI0329","Oct 4th"));
        complaints.add(new Complaint("Ear rings lost","My gold ear ring is lost which has two hangings and two balls weighing 3 grams.If anyone finds please contact 8908583616","processing","115CS0244","11:00 AM"));
        complaints.add(new Complaint("Loss of Cycle Keys","My cycle keys is lost which has red belt.If anyone finds please contact 8908583616","processing","115EC0368","Oct 5th"));
        complaints.add(new Complaint("Iphone6 lost","My Iphone of gray color is lost yesterday in LA .If anyone finds please contact 8908583616","processing","115EI0329","Oct 4th"));
        complaints.add(new Complaint("Ear rings lost","My gold ear ring is lost which has two hangings and two balls weighing 3 grams.If anyone finds please contact 8908583616","processing","115CS0244","11:00 AM"));
        complaints.add(new Complaint("Loss of Cycle Keys","My cycle keys is lost which has red belt.If anyone finds please contact 8908583616","processing","115EC0368","Oct 5th"));
        complaints.add(new Complaint("Iphone6 lost","My Iphone of gray color is lost yesterday in LA .If anyone finds please contact 8908583616","processing","115EI0329","Oct 4th"));

        RVAdapter adapter;
        adapter = new RVAdapter(complaints,this.getContext());
        rv.setAdapter(adapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent complaint = new Intent(MainActivity, ComplaintActivity.class);
//                startActivity(complaint);
//
//            }
//        });
        return listv;
    }
}
