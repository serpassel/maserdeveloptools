package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.widget.CompoundButton;

import es.marser.LOG_TAG;

/**
 * @author sergio
 *         Created by sergio on 6/11/17.
 */
@SuppressWarnings("unused")
@BindingMethods({
        @BindingMethod(type = AppCompatCheckBox.class, attribute = "android:onCheckedChanged", method = "setOnCheckedChangeListener"),
        @BindingMethod(type = AppCompatCheckBox.class, attribute = "android:checked", method = "setChecked")
})
public class CheckBoxBA {


    @BindingAdapter({"android:onCheckedChanged"})
    public static void setOnCheckedChangeListener(AppCompatCheckBox checkBox,
                                                  CompoundButton.OnCheckedChangeListener changeListener) {
        checkBox.setOnCheckedChangeListener(changeListener);
        Log.i(LOG_TAG.TAG, "SETEADO listener");
    }

/*
    @BindingAdapter({"android:checked"})
    public static void setChecked(AppCompatCheckBox checkBox,
                                  ObservableBoolean observableBoolean) {
        checkBox.setChecked(observableBoolean.get());
    }

    /*
    @InverseBindingAdapter(attribute = "android:checked")
    public static void isChecked(AppCompatCheckBox checkBox, ObservableBoolean observableBoolean) {
        observableBoolean.set(checkBox.isChecked());
    }
    */
}
