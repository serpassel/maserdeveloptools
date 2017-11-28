package es.marser.backgroundtools.dialogs.model;

import es.marser.backgroundtools.BR;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.String;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.tools.TextTools;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.SpannableString;

/**
 * @author sergio
 *         Created by sergio on 28-11-2017
 *         Objeto Modelo
 *         <p>
 *         [EN]  Object Model
 */
@SuppressWarnings("unused")

public class MonthTitle extends BaseObservable implements Selectable {

    private String name;

    public MonthTitle() {
        this.name = "";
    }

    public MonthTitle(String name) {
        this.name = name;
    }

    public MonthTitle setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TextTools.nc(name));

    }

    @SuppressWarnings("WeakerAccess")
    protected MonthTitle(Parcel in) {
        name = in.readString();

    }

    public static final Parcelable.Creator<MonthTitle> CREATOR = new Parcelable.Creator<MonthTitle>() {
        @Override
        public MonthTitle createFromParcel(Parcel in) {
            return new MonthTitle(in);
        }

        @Override
        public MonthTitle[] newArray(int size) {
            return new MonthTitle[size];
        }
    };

    @Override
    public SpannableString toSpannableString() {
        return new SpannableString(name);
    }

    @Override
    public String preSelectValue() {
        return "";
    }
}