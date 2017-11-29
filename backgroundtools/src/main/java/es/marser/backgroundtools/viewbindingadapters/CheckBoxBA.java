package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;

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
