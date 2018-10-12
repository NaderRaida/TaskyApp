package com.example.naderwalid.tasky.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naderwalid.tasky.Database.DB;
import com.example.naderwalid.tasky.MainActivity;
import com.example.naderwalid.tasky.R;
import com.example.naderwalid.tasky.Task;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

    List<Task> list = new ArrayList<>();
    LayoutInflater inflater;
    Context mainContext;

    public RecyclerAdapter(Context context, List<Task> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        mainContext = context;
    }


    @Override
    public RecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item_list, parent, false);

        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerAdapter.MyHolder holder, final int position) {

        Task task = list.get(position);
        switch (task.getPriority_number()) {

            case 1:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(android.R.color.holo_red_light));
                break;
            case 2:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(android.R.color.holo_blue_dark));
                break;
            case 4:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(android.R.color.holo_purple));
                break;
            case 5:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(android.R.color.holo_orange_light));
                break;
            default:
                holder.container.setBackgroundColor(mainContext.getResources().getColor(android.R.color.white));

                break;

        }
        holder.nameOfTask_textView.setText(task.getName());
        holder.priorityNumber_TextView.setText(String.valueOf(task.getPriority_number()));
        holder.date_textView.setText(String.valueOf(task.getDate()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nameOfTask_textView, priorityNumber_TextView, date_textView;
        ViewGroup container;

        public MyHolder(View itemView) {
            super(itemView);
            nameOfTask_textView = itemView.findViewById(R.id.name_of_task);
            priorityNumber_TextView = itemView.findViewById(R.id.priority_number);
            date_textView = itemView.findViewById(R.id.date);
            container = itemView.findViewById(R.id.container);
        }
    }
}

