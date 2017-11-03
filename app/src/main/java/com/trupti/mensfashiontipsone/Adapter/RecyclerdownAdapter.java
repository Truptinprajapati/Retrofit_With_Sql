package com.trupti.mensfashiontipsone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trupti.mensfashiontipsone.Activity.AppHubActivity;
import com.trupti.mensfashiontipsone.Activity.BAckAppActivity;
import com.trupti.mensfashiontipsone.R;
import com.trupti.mensfashiontipsone.Response.AppOnlineDialogResponse;

import java.util.List;

/**
 * Created by vaksys-android-51 on 4/8/17.
 */

public class RecyclerdownAdapter extends RecyclerView.Adapter<RecyclerdownAdapter.MyViewHolder> {
    Context context;
    List<AppOnlineDialogResponse.AppList> row;

    public RecyclerdownAdapter(AppHubActivity appHubActivity, List<AppOnlineDialogResponse.AppList> data) {
        this.context=appHubActivity;
        this.row=data;
    }

    public RecyclerdownAdapter(BAckAppActivity bAckAppActivity, List<AppOnlineDialogResponse.AppList> data) {
        this.context=bAckAppActivity;
        this.row=data;
    }


    @Override
    public RecyclerdownAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_2,parent,false);
        return  new RecyclerdownAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerdownAdapter.MyViewHolder holder, final int position) {
        Glide.with(context).load(row.get(position).getImage()).into(holder.imgbelow1);
        holder.txtbelow1.setText(row.get(position).getName());
        holder.btnbelow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        holder.lvbelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(row.get(position).getLink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return row.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgbelow1;
        TextView txtbelow1;
        Button btnbelow1;
        LinearLayout lvbelow;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgbelow1=itemView.findViewById(R.id.more_apps_image_view);
            txtbelow1=itemView.findViewById(R.id.more_apps_text_view);
            btnbelow1=itemView.findViewById(R.id.download_button);
            lvbelow=itemView.findViewById(R.id.lvbelow);
        }
    }
}
