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
import com.trupti.mensfashiontipsone.R;

import java.util.ArrayList;

/**
 * Created by vaksys-android-52 on 25/7/17.
 */

public class Sqliteadapter extends RecyclerView.Adapter<Sqliteadapter.MyViewholder> {
    Context context;
    ArrayList<String> topicArrayList;
    private SQLiteDatabase sqLiteDatabase;

    public Sqliteadapter(MainActivity mainActivity, ArrayList<String> topicArrayList) {
        this.context=mainActivity;
        this.topicArrayList=topicArrayList;
    }

    @Override
    public Sqliteadapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sqliteadapter, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(Sqliteadapter.MyViewholder holder, final int position) {
        sqLiteDatabase = context.openOrCreateDatabase("manfashiontips", Context.MODE_PRIVATE, null);
        holder.sqliteCategoryListBtn.setText(topicArrayList.get(position));

        holder.sqliteCategoryListBtn.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(context, DiscriptionActivity.class);
                intent.putExtra("topicName", topicName);
                intent.putExtra("description", description);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        private  final Button sqliteCategoryListBtn;
        public MyViewholder(View itemView) {
            super(itemView);
            sqliteCategoryListBtn=itemView.findViewById(R.id.btn_sql_list);
        }
    }
}
