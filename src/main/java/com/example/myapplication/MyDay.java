package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adapter.ToDoAdapter;
import com.example.myapplication.Model.ToDoModel;
import com.example.myapplication.Utils.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MyDay extends AppCompatActivity implements DialogCloseListener{
    private DataBaseHandler db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksadapter;
    private List<ToDoModel> tasksList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);

        //Calendar calendar= Calendar.getInstance();
        //String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate=findViewById(R.id.TextDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd, MMM, yyyy ");
        String currentDateandTime = sdf.format(new Date());
        textViewDate.setText(currentDateandTime);
        getSupportActionBar().hide();

        db=new DataBaseHandler(this);
        db.openDataBase();

        tasksRecyclerView=findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksadapter=new ToDoAdapter(db,this);
        tasksRecyclerView.setAdapter(tasksadapter);


        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerItemSwipe(tasksadapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab=findViewById(R.id.addNote);
       tasksList=db.getAllTasks();

        Collections.reverse(tasksList);
        tasksadapter.setTasks(tasksList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.setClickable(true);
                //view.setOnClickListener(this);
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        tasksList=db.getAllTasks();
        Collections.reverse(tasksList);
        tasksadapter.setTasks(tasksList);
        tasksadapter.notifyDataSetChanged();
    }
}