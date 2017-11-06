package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
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
        @BindingMethod(type = AppCompatCheckBox.class, attribute = "android:onCheckedChanged", method = "setOnCheckedChangeListener")
})
public class CheckBoxBA {


    @BindingAdapter({"android:onCheckedChanged"})
    public static void setOnCheckedChangeListener(AppCompatCheckBox checkBox,
                                                  CompoundButton.OnCheckedChangeListener changeListener){
        checkBox.setOnCheckedChangeListener(changeListener);
        Log.i(LOG_TAG.TAG, "SETEADO listener");
    }
}
