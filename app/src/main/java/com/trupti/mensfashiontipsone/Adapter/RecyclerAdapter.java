package com.trupti.mensfashiontipsone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.trupti.mensfashiontipsone.Activity.DiscriptionActivity;
import com.trupti.mensfashiontipsone.Activity.MainActivity;
import com.trupti.mensfashiontipsone.Database.DBHelper;
import com.trupti.mensfashiontipsone.R;
import com.trupti.mensfashiontipsone.Response.AppListResponse;

import java.util.List;

/**
 * Created by vaksys-android-52 on 25/7/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    List<AppListResponse.CategoryEntity> row;
    private SQLiteDatabase sqLiteDatabase;

    public RecyclerAdapter(MainActivity mainActivity, List<AppListResponse.CategoryEntity> data) {
        this.context=mainActivity;
        this.row=data;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleradapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, final int position) {
        DBHelper dbHelper = new DBHelper(context);
        sqLiteDatabase  = context.openOrCreateDatabase("manfashiontips",Context.MODE_PRIVATE,null);
        holder.btnlist.setText(row.get(position).getCategory());
        dbHelper.addData(String.valueOf(row.get(position).getId()), row.get(position).getCategory(), row.get(position).getDescription());
        holder.btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topicName = null, description = null;

                Cursor cursor = sqLiteDatabase.rawQuery("SELECT topic,description FROM manfashion WHERE COL_ID =" + (position + 1), null);
                if (cursor.moveToFirst()) {
                    do {
                        topicName = cursor.getString(cursor.getColumnIndex("topic"));
                        description = cursor.getString(cursor.getColumnIndex("description"));

                    } while (cursor.moveToNext());
                }
                Intent intent = new Intent(context,DiscriptionActivity.class);
                intent.putExtra("topicName", topicName);
                intent.putExtra("description", description);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return row.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btnlist;
        public MyViewHolder(View itemView) {
            super(itemView);
            btnlist=itemView.findViewById(R.id.btn_list);
        }
    }
}
