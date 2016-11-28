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
    private SQLiteDatabase mSQLDatabase;
    private ViewDAOHelper viewDAO;

    public ViewSQLite(Context context) {
        viewDAO = ViewDAOHelper.getInstance(context);
    }

    private void openDataBase() {
        mSQLDatabase = viewDAO.getWritableDatabase();
    }

    public void closedb() {
        if (mSQLDatabase.isOpen()) {
            mSQLDatabase.close();
        }
    }

    public long insert(ToDoData toDoData) {
        long state;
        openDataBase();
        ContentValues values = new ContentValues();
        values.put("todo_title", toDoData.getTitle());
        values.put("todo_time", toDoData.getTime());
        values.put("todo_state", toDoData.getState());
        values.put("todo_progress", toDoData.getProgress());
        values.put("todo_color", toDoData.getDrawBackColor());
        values.put("todo_type", toDoData.getType());//必须修改
        values.put("todo_plan", toDoData.getPlan());
        values.put("todo_day", toDoData.getDay());
        values.put("todo_create", toDoData.getDay());
        values.put("todo_destory", toDoData.getDay());
        values.put("todo_day_index", toDoData.getDay());
        values.put("todo_day_total_time", toDoData.getDay());
        try {
            state = mSQLDatabase.insert(Constants.TABLE_NAME, null, values);
        } finally {
            closedb();
        }
        return state;
    }

    public void update(ToDoData toDoData) {
        openDataBase();
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

    public void update(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs) {
        openDataBase();
        try {
            mSQLDatabase.update(tableName, contentValues, whereClause, whereArgs);
        } finally {
            closedb();
        }
    }

    public void delete(String name) {
        openDataBase();
        try {
            mSQLDatabase.delete(Constants.TABLE_NAME, "todo_title = ?", new String[]{name});
        } finally {
            closedb();
        }
    }

    public Cursor query() {
        openDataBase();
        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        openDataBase();
        return mSQLDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
