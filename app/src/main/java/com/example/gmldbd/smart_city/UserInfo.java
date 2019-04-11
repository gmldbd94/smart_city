package com.example.gmldbd.smart_city;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    String name;
    String birth;
    String gender;
    String num;

    public UserInfo(String name_, String birth_, String gender_, String num_){
        name = name_;
        birth = birth_;
        gender = gender_;
        num = num_;
    }

    public UserInfo(Parcel src) {
        name = src.readString();
        birth = src.readString();
        gender = src.readString();
        num = src.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(birth);
        dest.writeString(gender);
        dest.writeString(num);
    }
}