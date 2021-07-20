package com.example.notelt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.notelt.Adapter.ToDoAdapter;
import com.example.notelt.Utils.AddNewTask;
import com.example.notelt.Utils.DataBaseHandler;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    public DataBaseHandler db;
    private FloatingActionButton fbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHandler(this);
        db.openDataBase();

        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecuclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(ab,this);
        taskRecyclerView.setAdapter(tasksAdapter);

        fbutton = findViewById(R.id.fbutton);

        taskList = db.getAllTasks();
        Collection.reverse(taskList);
        taskAdapter.setTasks(taskList);

        fbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    private void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collection.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChange();
    }
}