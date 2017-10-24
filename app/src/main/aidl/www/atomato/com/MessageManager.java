package www.atomato.com;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/10/24.
 */

public class MessageManager implements Parcelable {
    private String content;//需求内容
    private int level;//需求级别

    public MessageManager(Parcel in) {
        this.content = in.readString();
        this.level = in.readInt();
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

    public MessageManager(String string, int i) {
        this.content = string;
        this.level = i;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.level);
        dest.writeString(this.content);
    }
}
