package com.example.notelt.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.notelt.R;

import static android.app.DialogFragment.STYLE_NORMAL;
import static java.security.AccessController.getContext;

public class AddNewTask extends BottomSheetDialogFragment{

    public static String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSavebutton;
    private DatabaseErrorHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskTesk);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);

        db = new DatabaseErrorHandler(getActivity()) {
            @Override
            public void onCorruption(SQLiteDatabase sqLiteDatabase) {

            }
        };
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if(task.length()>0){
                newTaskButton.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            }
        }

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                if(c.toString().equals("")){
                  newTaskSavebutton.setEnabled(false);
                  newTaskSavebutton.setTextColor(Color.GRAY);
                }else{
                    newTaskSavebutton.setEnabled(true);
                    newTaskSavebutton.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        boolean finalIsUpdate = isUpdate;
        newTaskSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }else{
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                }
                dismiss(); // to recycles/refreshes automatically
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
