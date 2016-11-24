package www.atomato.com.tomato.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import www.atomato.com.tomato.constants.Constants;

/**
 * Created by wangj on 2016-11-23.
 */

public class ViewDAOHelper extends SQLiteOpenHelper {
    String sql = "create table " + Constants.TABLE_NAME + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "todo_title TEXT NOT NULL," +
            "todo_time INTEGER," +
            "todo_state INTEGER," +
            "todo_progress INTEGER,"+
            "todo_color INTEGER,"+
            "todo_type INTEGER,"+
            "todo_plan INTEGER,"+
            "todo_day INTEGER)";

    public ViewDAOHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    public static ViewDAOHelper getInstance(Context context){
//        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
//
//        re
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
