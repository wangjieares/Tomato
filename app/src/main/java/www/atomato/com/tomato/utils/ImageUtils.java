package www.atomato.com.tomato.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by wangjie on 2016-11-20.
 */

public class ImageUtils {
    public static Bitmap drawableToBitamp(Resources res,int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        return bitmap;
    }
}
