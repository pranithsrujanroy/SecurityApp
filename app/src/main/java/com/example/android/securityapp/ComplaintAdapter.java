package com.example.android.securityapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ramana on 10/6/2017.
 */

public class ComplaintAdapter extends ArrayAdapter<Complaint>{

    public ComplaintAdapter(Activity context, ArrayList<Complaint> complaints){
        super(context,0,complaints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the {@link Word} object located at this position in the list
        Complaint currentComp = getItem(position);

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view, parent, false);
        }
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentComp.getmTitle());

        TextView content = (TextView) listItemView.findViewById(R.id.content);
        content.setText(currentComp.getmContent());

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        time.setText(currentComp.getmDate());

        TextView status = (TextView) listItemView.findViewById(R.id.status);
        status.setText(currentComp.getmStatus());

        TextView roll = (TextView) listItemView.findViewById(R.id.roll);
        roll.setText(currentComp.getmRoll());

        return listItemView;
    }
}
