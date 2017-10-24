package www.atomato.com.tomato.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.ToDoItem;
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
        ContentValues values = new ContentValues();
        //初始化总数据
        //总数据内容包括 总时间,总个数,一些别的别的选项后补
        values.put("total_time",0);
        viewDAO.getWritableDatabase().insert(Constants.TOTAL_NAME,null,values);
    }

    private void openDataBase() {
        mSQLDatabase = viewDAO.getWritableDatabase();
    }

    public void closedb() {
        if (mSQLDatabase.isOpen()) {
            mSQLDatabase.close();
        }
    }

    public void insert(ToDoItem toDoData) {
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
        values.put("todo_plan_num", toDoData.getmPlanNum());
        values.put("todo_total_num", toDoData.getTotalTime());
        values.put("todo_total_time", toDoData.getTotalTime());
        try {
            mSQLDatabase.insert(Constants.TABLE_NAME, null, values);
        } catch (SQLiteConstraintException e) {
            ToastUtils.show(mContext, "标题名称重复！");
        } finally {
            closedb();
        }
    }

    public void insert(GroupItem groupItem) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put("todo_title", groupItem.getTitle());
        values.put("todo_time", groupItem.getTime());
        values.put("todo_state", groupItem.getState());
        values.put("todo_progress", groupItem.getProgress());
        values.put("todo_color", groupItem.getDrawBackColor());
        values.put("todo_stick", groupItem.getStickState());
        values.put("todo_stick_time", groupItem.getStickStateTime());
        values.put("todo_create", groupItem.getCreate());
        values.put("todo_destory", groupItem.getDestory());
        values.put("todo_plan_time", groupItem.getPlanTime());
        values.put("todo_plan_num", groupItem.getmPlanNum());
        values.put("todo_total_num", groupItem.getTotalTime());
        values.put("todo_total_time", groupItem.getTotalTime());
        try {
            mSQLDatabase.insert(Constants.TABLE_NAME, null, values);
        } catch (SQLiteConstraintException e) {
            ToastUtils.show(mContext, "标题名称重复！");
        } finally {
            closedb();
        }
    }
//    public void update(ToDoData toDoData) {
//        openDataBase();
//        ContentValues values = new ContentValues();
//        values.put("todo_title", toDoData.getTitle());
//        values.put("todo_time", toDoData.getTime());
//        values.put("todo_state", toDoData.getState());
//        values.put("todo_progress", toDoData.getProgress());
//        values.put("todo_color", toDoData.getDrawBackColor());
//        try {
//            mSQLDatabase.update(Constants.TABLE_NAME, values, null, null);
//        } finally {
//            closedb();
//        }
//    }

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

    public int sumColumn(String column, String table) {
        openDataBase();
//         Cursor cursor = mSQLDatabase.rawQuery("SELECT * FROM item_info",null);
        try (Cursor cursor = mSQLDatabase.rawQuery("SELECT SUM(" + column + ") FROM " + table, null)) {
            cursor.moveToNext();
            closedb();
            return cursor.getInt(0);
        }
    }
    public Cursor query() {
        openDataBase();
        //        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, "todo_stick  asc");
        return mSQLDatabase.query(Constants.TABLE_NAME, null, null, null, null, null, "todo_stick asc ,todo_stick_time desc");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        openDataBase();
        return mSQLDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
