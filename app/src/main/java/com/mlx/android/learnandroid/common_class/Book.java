package com.mlx.android.learnandroid.common_class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MLXPHONE on 2018/5/27.
 */

public class Book implements Parcelable{

    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    public static final Parcelable.Creator<Book> CREATOR=new Parcelable.Creator<Book>(){
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };

    private Book(Parcel in){
        bookId=in.readInt();
        bookName=in.readString();
    }


}
