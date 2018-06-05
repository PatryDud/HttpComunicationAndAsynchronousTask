package com.example.pati.download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pati on 03.06.2018.
 */

public class ProgressInfo implements Parcelable {
    public int mDownloadedBytes;
    public int mSize;
    public String mResult;

    public ProgressInfo(int m){
        this.mDownloadedBytes=m;

    }
    public ProgressInfo(){}
    public ProgressInfo (Parcel bundle){
        mDownloadedBytes= bundle.readInt();
        mSize= bundle.readInt();
        mResult= bundle.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeInt(mDownloadedBytes);
        dest.writeInt(mSize);
        dest.writeString(mResult);
    }
    public static final Parcelable.Creator<ProgressInfo> CREATOR =
            new Parcelable.Creator<ProgressInfo>() {
                @Override
                public ProgressInfo createFromParcel(Parcel source) {
                    return new ProgressInfo(source);
                }
                @Override
                public ProgressInfo[] newArray(int size) {
                    return new ProgressInfo[size];
                }
            };
}
