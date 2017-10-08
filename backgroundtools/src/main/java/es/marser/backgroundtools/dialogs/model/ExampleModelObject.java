package es.marser.backgroundtools.dialogs.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import es.marser.backgroundtools.BR;

/**
 * @author sergio
 *         Created by sergio on 5/10/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ExampleModelObject extends BaseObservable implements Parcelable {

    private String name;
    private String subname;

    public ExampleModelObject() {
    }

    public ExampleModelObject(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
        notifyPropertyChanged(BR.subname);
    }

    protected ExampleModelObject(Parcel in) {
        name = in.readString();
        subname = in.readString();
    }

    public static final Creator<ExampleModelObject> CREATOR = new Creator<ExampleModelObject>() {
        @Override
        public ExampleModelObject createFromParcel(Parcel in) {
            return new ExampleModelObject(in);
        }

        @Override
        public ExampleModelObject[] newArray(int size) {
            return new ExampleModelObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(subname);
    }
}
