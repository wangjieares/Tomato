package www.atomato.com.tomato.data;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by wangjie on 2016-11-20.
 * 随机颜色返回类
 */

public class TextConstants {
    private static final String EMERALD = "精诚所知，金石为开";
    private static final String PETER_REVIER = "精诚所知，金石为开";
    private static final String ALIZARIN = "精诚所知，金石为开";
    private static final String SUNFLOWER = "精诚所知，金石为开";
    private static final String AMETHYST = "精诚所知，金石为开";

    public static String randomString() {
        int max = 6;
        int min = 0;
        Random random = new Random();
        int bom = random.nextInt(max) % (max - min + 1) + min;
        switch (bom) {
            case 0:
                return EMERALD;
            case 1:
                return PETER_REVIER;
            case 2:
                return ALIZARIN;
            case 3:
                return SUNFLOWER;
            case 4:
                return AMETHYST;
            case 5:
                return EMERALD;
            case 6:
                return EMERALD;
            default:
                return EMERALD;
        }
    }
}
