package www.atomato.com.tomato;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.sqlite.ViewSQLite;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("www.atomato.com.tomato", appContext.getPackageName());
    }

    @Test
    public void useUpdate(){
        ViewSQLite viewSQLite = new ViewSQLite(InstrumentationRegistry.getTargetContext());
        ContentValues values = new ContentValues();
        values.put("todo_title","读书");
        viewSQLite.update(Constants.TABLE_NAME,values,"_id=?",new String[]{"1"});
    }


}
