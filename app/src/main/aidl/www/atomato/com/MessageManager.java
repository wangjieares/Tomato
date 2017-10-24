package www.atomato.com;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/10/24.
 */

public class MessageManager implements Parcelable {

    protected MessageManager(Parcel in) {

    }

    public static final Creator<MessageManager> CREATOR = new Creator<MessageManager>() {
        @Override
        public MessageManager createFromParcel(Parcel in) {
            return new MessageManager(in);
        }

        @Override
        public MessageManager[] newArray(int size) {
            return new MessageManager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
