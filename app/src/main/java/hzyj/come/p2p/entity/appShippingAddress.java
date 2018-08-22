package hzyj.come.p2p.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EverGlow on 2018/8/21 14:13
 */

public    class appShippingAddress implements Parcelable {
    private  String appAddress;
    private  String appAddressInfo;
    private  String appConsignee;
    private  int  appIsDefault;
    private  String appPhone;
    private  String appAddressId;

    public String getAppAddress() {
        return appAddress == null ? "" : appAddress;
    }

    public String getAppAddressInfo() {
        return appAddressInfo == null ? "" : appAddressInfo;
    }

    public String getAppConsignee() {
        return appConsignee == null ? "" : appConsignee;
    }

    public int getAppIsDefault() {
        return  appIsDefault;
    }

    public String getAppPhone() {
        return appPhone == null ? "" : appPhone;
    }

    public String getAppAddressId() {
        return appAddressId == null ? "" : appAddressId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appAddress);
        dest.writeString(this.appAddressInfo);
        dest.writeString(this.appConsignee);
        dest.writeInt(this.appIsDefault);
        dest.writeString(this.appPhone);
        dest.writeString(this.appAddressId);
    }

    public appShippingAddress() {
    }

    protected appShippingAddress(Parcel in) {
        this.appAddress = in.readString();
        this.appAddressInfo = in.readString();
        this.appConsignee = in.readString();
        this.appIsDefault = in.readInt();
        this.appPhone = in.readString();
        this.appAddressId = in.readString();
    }

    public static final Parcelable.Creator<appShippingAddress> CREATOR = new Parcelable.Creator<appShippingAddress>() {
        @Override
        public appShippingAddress createFromParcel(Parcel source) {
            return new appShippingAddress(source);
        }

        @Override
        public appShippingAddress[] newArray(int size) {
            return new appShippingAddress[size];
        }
    };
}
