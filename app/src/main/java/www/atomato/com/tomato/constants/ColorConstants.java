package www.atomato.com.tomato.constants;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by wangjie on 2016-11-20.
 */

public class ColorConstants {
    private static final int EMERALD = Color.parseColor("#2ecc71");
    private static final int PETER_REVIER = Color.parseColor("#2980b9");
    private static final int ALIZARIN = Color.parseColor("#e74c3c");
    private static final int SUNFLOWER = Color.parseColor("#f1c40f");
    private static final int AMETHYST = Color.parseColor("#9b59b6");

    public static int randomBackground() {
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
