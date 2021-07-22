package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import com.example.myapplication.Model.ToDoModel;
import com.example.myapplication.Utils.DataBaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddNewTask extends BottomSheetDialogFragment {

    public static String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSavebutton;
    private DataBaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return  view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        newTaskSavebutton = getView().findViewById(R.id.newTaskButton);

        boolean isUpdate = false;   //?????? check if we are updating or adding new task
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if(task.length()>0){
                newTaskSavebutton.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary_dark));
                newTaskSavebutton.setBackgroundColor(R.color.teal_200);
            }
        }
        db = new DataBaseHandler(getActivity());
        db.openDataBase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                if(c.toString().equals("")){
                    newTaskSavebutton.setEnabled(false);
                    newTaskSavebutton.setTextColor(Color.GRAY);
                }else{
                    newTaskSavebutton.setEnabled(true);
                    newTaskSavebutton.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary_dark));
            }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                view.setClickable(true);
                view.setOnClickListener(this);
                if (finalIsUpdate==false) {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }
            }
        });
        //dismiss(); // to recycles/refreshes automatically
    }

    //@Override
    /*public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }*/
}

