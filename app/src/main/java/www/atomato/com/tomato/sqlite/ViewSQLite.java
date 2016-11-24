package www.atomato.com.tomato.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.ToDoData;

/**
 * Created by wangj on 2016-11-23.
 */

public class ViewSQLite {
    private Context mContext;
    private SQLiteDatabase mSQLDatabase;

    public ViewSQLite(Context context) {
        this.mContext = context;
    }

    private void openDataBase(Context context) {
        ViewDAOHelper viewDAO = new ViewDAOHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        mSQLDatabase = viewDAO.getWritableDatabase();
    }

    public void closedb() {
        if (mSQLDatabase.isOpen()) {
            mSQLDatabase.close();
        }
    }

    public long insert(ToDoData toDoData) {
        long state;
        openDataBase(mContext);
        ContentValues values = new ContentValues();
        values.put("todo_title", toDoData.getTitle());
        values.put("todo_time", toDoData.getTime());
        values.put("todo_state", toDoData.getState());
        values.put("todo_progress", toDoData.getProgress());
        values.put("todo_color", toDoData.getDrawBackColor());
        values.put("todo_type", toDoData.getType());//必须修改
        values.put("todo_plan", toDoData.getPlan());
        values.put("todo_day", toDoData.getDay());
        try {
            state = mSQLDatabase.insert(Constants.TABLE_NAME, null, values);
        } finally {
            closedb();
        }
        return state;
    }

    public void update(ToDoData toDoData) {
        openDataBase(mContext);
        ContentValues values = new ContentValues();
        values.put("todo_title", toDoData.getTitle());
        values.put("todo_time", toDoData.getTime());
        values.put("todo_state", toDoData.getState());
        values.put("todo_progress", toDoData.getProgress());
        values.put("todo_color", toDoData.getDrawBackColor());
        values.put("todo_type", toDoData.getType());//必须修改
        values.put("todo_plan", toDoData.getPlan());
        values.put("todo_day", toDoData.getDay());
        try {
            mSQLDatabase.update(Constants.TABLE_NAME, values, null, null);
        } finally {
            closedb();
        }
    }

    public void delete(String name) {
        openDataBase(mContext);
        try {
            mSQLDatabase.delete(Constants.TABLE_NAME, "todo_title = ?", new String[]{name});
        } finally {
            closedb();
        }
    }

    public Cursor query() {
        openDataBase(mContext);
        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, null);
    }

}
