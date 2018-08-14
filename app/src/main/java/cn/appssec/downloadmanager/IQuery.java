package cn.appssec.downloadmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by T on 18/7/17.
 */

public class IQuery implements Parcelable {
    private long[] mIds = null;
    private Integer mStatusFlags = null;
    private String mFilterString = null;

    public long[] getmIds() {
        return mIds;
    }

    public void setmIds(long[] mIds) {
        this.mIds = mIds;
    }

    public Integer getmStatusFlags() {
        return mStatusFlags;
    }

    public void setmStatusFlags(Integer mStatusFlags) {
        this.mStatusFlags = mStatusFlags;
    }

    public String getmFilterString() {
        return mFilterString;
    }

    public void setmFilterString(String mFilterString) {
        this.mFilterString = mFilterString;
    }

    public IQuery(long[] mIds, Integer mStatusFlags, String mFilterString) {
        this.mIds = mIds;
        this.mStatusFlags = mStatusFlags;
        this.mFilterString = mFilterString;
    }

    protected IQuery(Parcel in) {
        mIds = in.createLongArray();
        if (in.readByte() == 0) {
            mStatusFlags = null;
        } else {
            mStatusFlags = in.readInt();
        }
        mFilterString = in.readString();
    }

    public static final Creator<IQuery> CREATOR = new Creator<IQuery>() {
        @Override
        public IQuery createFromParcel(Parcel in) {
            return new IQuery(in);
        }

        @Override
        public IQuery[] newArray(int size) {
            return new IQuery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(mIds);
        if (mStatusFlags == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mStatusFlags);
        }
        dest.writeString(mFilterString);
    }
}
