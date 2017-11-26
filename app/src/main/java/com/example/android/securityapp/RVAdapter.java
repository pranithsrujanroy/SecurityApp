package com.example.android.securityapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//
//

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    ArrayList<Complaint> complaints = new ArrayList<Complaint>();
    Context context;

    RVAdapter(ArrayList<Complaint> complaints, Context context){
        this.complaints = complaints;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ArrayList<Complaint> complaints = new ArrayList<Complaint>();
        Context context;
        CardView cv;
        TextView description;
        TextView title;
        TextView status;
        TextView roll;
        TextView time;

        ViewHolder(View itemView, Context context,ArrayList<Complaint> complaints) {
            super(itemView);
            this.context = context;
            this.complaints = complaints;
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.content);
            status = (TextView) itemView.findViewById(R.id.status);
            roll = (TextView) itemView.findViewById(R.id.roll);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Complaint complaint=this.complaints.get(position);
            Intent intent=new Intent(this.context,ComplaintActivity.class);
            intent.putExtra("title",complaint.getmTitle());
            intent.putExtra("content",complaint.getmContent());
            intent.putExtra("status",complaint.getmStatus());
            intent.putExtra("roll",complaint.getmRoll());
            intent.putExtra("id",complaint.getmComplaintId());
            intent.putExtra("upvotes",complaint.getmCount());
            //intent.putExtra("time",complaint.getmDate());
            this.context.startActivity(intent);


        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        final ViewHolder pvh = new ViewHolder(v,context,complaints);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.title.setText(complaints.get(i).getmTitle());
        viewHolder.description.setText(complaints.get(i).getmContent());
        viewHolder.status.setText(complaints.get(i).getmStatus());
        viewHolder.roll.setText(complaints.get(i).getmRoll());
        viewHolder.time.setText(complaints.get(i).getmDate());

//        Glide.with(MainActivity.getContext())
//                .load(feature.get(i).featured_image)
//                .placeholder(R.drawable.ic_glide_waiting)
//                .error(R.drawable.ic_glide_failed)
//                .crossFade()
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(personViewHolder.photo);
    }


    @Override
    public int getItemCount() {
        return complaints.size();
    }
}

