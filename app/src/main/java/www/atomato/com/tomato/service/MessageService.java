package www.atomato.com.tomato.service;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import www.atomato.com.MessageBean;

/**
 * Created by admin on 2017/10/24.
 */

public class MessageService extends BaseService {
    private IBinder binder = new MessageBean.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
