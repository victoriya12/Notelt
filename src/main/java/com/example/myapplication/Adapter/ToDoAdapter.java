package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.ToDoModel;
import com.example.myapplication.MyDay;
import com.example.myapplication.R;
import com.example.myapplication.Utils.DataBaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> toDoList;
    //private MainActivity activity;
    private MyDay activity1;
    private DataBaseHandler db;


    public ToDoAdapter(DataBaseHandler db, MyDay activity1){
        this.db=db;
        this.activity1=activity1;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);

    }
    public void onBindViewHolder(ViewHolder holder,int position){
        db.openDataBase();
        ToDoModel item=toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);
                }
                else{
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /* public int getItemCount(){
         return toDoList.size();
     }*/
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<ToDoModel> todoList){
        this.toDoList=todoList;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return activity1;
    }
    public void deleteItem(int position){
        ToDoModel item=toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super( view);
            task=view.findViewById(R.id.toDoCheckBox);
        }
    }
}
