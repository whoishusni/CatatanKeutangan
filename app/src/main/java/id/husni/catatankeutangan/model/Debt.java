package id.husni.catatankeutangan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Debt implements Parcelable {
    private int id;
    private String date;
    private String name;
    private int value;

    public Debt(int id, String date, String name, int value) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public Debt() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeInt(this.value);
    }

    protected Debt(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        this.name = in.readString();
        this.value = in.readInt();
    }

    public static final Creator<Debt> CREATOR = new Creator<Debt>() {
        @Override
        public Debt createFromParcel(Parcel source) {
            return new Debt(source);
        }

        @Override
        public Debt[] newArray(int size) {
            return new Debt[size];
        }
    };
}
