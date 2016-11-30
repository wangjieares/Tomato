package www.atomato.com.tomato.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import www.atomato.com.tomato.constants.Constants;

/**
 * Created by wangj on 2016-11-23.
 */

class ViewDAOHelper extends SQLiteOpenHelper {
    private static ViewDAOHelper instance;

    ViewDAOHelper(Context context) {
        super(context, Constants.TABLE_NAME, null, Constants.DATABASE_VERSION);
    }

    //    public static ViewDAOHelper getInstance(Context context){
//        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
//
//        re
//    }
    static ViewDAOHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ViewDAOHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Constants.TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo_title TEXT NOT NULL UNIQUE ," +
                "todo_time INTEGER," +
                "todo_state INTEGER," +
                "todo_progress INTEGER," +
                "todo_color INTEGER," +
                "todo_stick INTEGER," +
                "todo_stick_time INTEGER," +
                "todo_type INTEGER," +
                "todo_plan INTEGER," +
                "todo_day INTEGER," +
                "todo_create INTEGER," +//创建时间
                "todo_destory INTEGER," +//删除时间
                "todo_day_index INTEGER," +//本身执行总个数
                "todo_day_total_time INTEGER)";//本身执行总时间
        String sql1 = "create table " + Constants.TOTAL_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_account INTEGER," +// 账号
                "total_password INTEGER," +// 密码
                "total_num INTEGER," +// 总 一共完成个数
                "total_time INTEGER," +// 总 一共完成时间
                "total_day_num INTEGER," +// 总 一共完成时间
                "total_day_time INTEGER)"; // 总 一共完成时间
        db.execSQL(sql);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
