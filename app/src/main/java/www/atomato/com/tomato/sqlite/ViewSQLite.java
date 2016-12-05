package www.atomato.com.tomato.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangj on 2016-11-23.
 */

public class ViewSQLite {
    private Context mContext;
    private SQLiteDatabase mSQLDatabase;
    private ViewDAOHelper viewDAO;

    public ViewSQLite(Context context) {
        mContext = context;
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

    public void insert(ToDoData toDoData) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put("todo_title", toDoData.getTitle());
        values.put("todo_time", toDoData.getTime());
        values.put("todo_state", toDoData.getState());
        values.put("todo_progress", toDoData.getProgress());
        values.put("todo_color", toDoData.getDrawBackColor());
        values.put("todo_stick", toDoData.getStickState());
        values.put("todo_stick_time", toDoData.getStickStateTime());
        values.put("todo_create", toDoData.getCreate());
        values.put("todo_destory", toDoData.getDestory());
        values.put("todo_plan_time", toDoData.getPlanTime());
        values.put("todo_total_time", toDoData.getTotalTime());
        try {
            mSQLDatabase.insert(Constants.TABLE_NAME, null, values);
        } catch (SQLiteConstraintException e) {
            ToastUtils.show(mContext, "标题名称重复！");
        } finally {
            closedb();
        }
    }

    public void update(ToDoData toDoData) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put("todo_title", toDoData.getTitle());
        values.put("todo_time", toDoData.getTime());
        values.put("todo_state", toDoData.getState());
        values.put("todo_progress", toDoData.getProgress());
        values.put("todo_color", toDoData.getDrawBackColor());
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
//        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, "todo_stick  asc");
        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, "todo_stick asc ,todo_stick_time desc");//排序规则不对
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        openDataBase();
        return mSQLDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
