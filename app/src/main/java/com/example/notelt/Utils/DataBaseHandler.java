package com.example.notelt.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

        private static final int VERSION = 1;
        private static final String NAME = "toDoListDataBase";
        private static final String TODO_TABLE = "toDo";
        private static final String ID = "id";
        private static final String TASK = "task";
        private static final String STATUS = "status";
        private static final String CREATE_TODO_TABLE = "CREATE TABLE "+ TODO_TABLE+ "(" + ID +" INTIGER PRIMARY KEY AUTOINCREMENT, "+ TASK + "TEXT, " + STATUS + "INTIGER)";
        private SQLiteDatabase db;
        private DataBaseHandler(Context context){
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
         db.execSQL(CREATE_TODO_TABLE);
        }

        @Override
        public void onnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            //drop the older table
            db.execSQL("DROP TABLE IF EXISTS"+ TODO_TABLE);
            //create new table
            onCreate(db);
        }

        public void openDataBase(){
            db = this.getWritableDatabase();
        }

        public void insertTask(ToDoModel task ){
            ContentValues cv = new ContentValues();
            cv.put(TASK, task.getTask());
            cv.put(STATUS, 0);
            db.insert(TODO_TABLE, null, cv);
        }

        public List<ToDoModel> getAllTasks(){
            List<ToDoModel> taskList = new ArrayList<>();
            Cursor cur = null;
            db.beginTransaction();
            try{
                cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
                if(cur != null){
                    if(cur.moveToFirst()){
                        do{
                            ToDoModel task = new ToDoModel();
                            task.setId(cur.getInt(cur.getColumnIndex(ID)));
                            task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                            task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                            taskList.add(task);
                        }while(cur.moveToNext());
                    }
                }
            }finally (){
                db.endTransaction();
            cur.close();
            }
            return taskList;
        }

        public void updateStatus(int id, int statusValue){
            ContentValues cv = new ContentValues();
            cv.put(STATUS, statusValue);
            db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf((id))});
        }

        public void updateTask(int id, String task){
            ContentValues cv = new ContentValues();
            cv.put(TASK, task);
            db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
        }

        public void deleteTask(int id){
            db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
        }
}

