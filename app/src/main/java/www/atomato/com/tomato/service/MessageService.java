package www.atomato.com.tomato.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import www.atomato.com.MessageAidlInterface;
import www.atomato.com.MessageManager;


/**
 * Created by admin on 2017/10/24.
 */

public class MessageService extends BaseService {
    MessageAidlInterface.Stub myBinder = new MessageAidlInterface.Stub() {
        @Override
        public MessageManager getDemand() throws RemoteException {
            MessageManager demand=new MessageManager("xiaoxi",1);
            return demand;
        }

        @Override
        public void setDemandIn(MessageManager msg) throws RemoteException {

        }

        @Override
        public void setDemandOut(MessageManager msg) throws RemoteException {

        }

        @Override
        public void setDemandInOut(MessageManager msg) throws RemoteException {

        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


}
