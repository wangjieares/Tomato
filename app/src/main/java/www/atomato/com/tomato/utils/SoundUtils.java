package www.atomato.com.tomato.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import www.atomato.com.tomato.R;

/**
 * Created by wangjie on 12/24/2016.
 */

public class SoundUtils {
    static SoundPool sp;
    static HashMap<Integer, Integer> spMap = new HashMap<Integer, Integer>();

    public static void playSounds(Context context, int soundId, final int sound, final int number) {
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        spMap.put(1, sp.load(context, soundId, 1));
        //实例化AudioManager对象，控制声音
        AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        //最大音量
        final float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        final float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        final float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        //播放
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                sp.play(spMap.get(sound),     //声音资源
                        volumnRatio,         //左声道
                        volumnRatio,         //右声道
                        1,             //优先级，0最低
                        number,         //循环次数，0是不循环，-1是永远循环
                        1);            //回放速度，0.5-2.0之间。1为正常速度
            }
        });

    }

    public static void restartSound() {
        sp.autoResume();
    }

    public static void pauseSound() {
        if (sp != null) {
            sp.autoPause();
        }
    }

    public static void stopSound() {
        if (sp != null) {
            sp.stop(1);
            sp.release();
        }
    }
}
