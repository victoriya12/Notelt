package com.example.notelt.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.notelt.MainActivity;
import com.example.notelt.R;
import com.example.notelt.Utils.AddNewTask;
import com.example.notelt.Utils.DataBaseHandler;

import java.util.List;

public class ToDoAdapter {

    private List<ToDoAdapter> todoList;
    private MainActivity activity;
    public DataBaseHandler db;

    public ToDoAdapter(DataBaseHandler db,MainActivity activity){
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View intemView = LayoutInflater.form(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(intemView);
    }

    public void onBindViewHolder(ViewHolder h, int pos){
        db.openDataBase();
    ToDoModel item = todoList.get(pos);
    holder.task.setText(item.getTask());
    holder.task.setChecked(toBoolean(item.getStatus()));
    holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton bView, boolean isChecked){
            if(isChecked){
                db.updateStatus(item.getId(), 1);
            }else{
                db.updateStatus(item.getId(), 0);
            }
        }
    });
    }

    public int getItemCount() {return todoList.size();}

    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void editItem(int pos){
        ToDoModel item = todoList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

}
