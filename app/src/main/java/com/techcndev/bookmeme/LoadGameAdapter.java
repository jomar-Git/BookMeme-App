package com.techcndev.bookmeme;

import static androidx.core.content.ContextCompat.startActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoadGameAdapter extends RecyclerView.Adapter<LoadGameAdapter.ViewHolder> {
    private ListData[] listdata;
    public static String EXTRA_MESSAGE = "com.example.appdevgrouptask.extra.MESSAGE";

    String LOG_TAG = SelectionActivity.class.getSimpleName();
    // RecyclerView recyclerView;
    public LoadGameAdapter(ListData[] listdata) {
        this.listdata = listdata;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.custom_textview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListData myListData = listdata[position];
        final String username = listdata[position].getTitle();
        final String stage_level = listdata[position].getContent();
        holder.texttitle.setText(username);
        holder.textcontent.setText(stage_level);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                Log.d(LOG_TAG,"ContentListAdapter: " + username + stage_level);
                String[] mMessage = {username, stage_level};
                intent.putExtra(EXTRA_MESSAGE, mMessage);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView texttitle,textcontent;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
//            this.texttitle = (TextView) itemView.findViewById(R.id.textTitle);
//            this.textcontent = (TextView) itemView.findViewById(R.id.textContent);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.customview_layout);
        }
    }
}