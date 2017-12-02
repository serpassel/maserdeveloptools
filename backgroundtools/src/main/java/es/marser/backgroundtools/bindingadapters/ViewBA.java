package es.marser.backgroundtools.bindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.view.View;

/**
 * @author sergio
 *         Created by sergio on 6/11/17.
 *         Adpatador de métodos para vistas MVP
 */
@SuppressWarnings("unused")
@BindingMethods({
        @BindingMethod(type = View.class, attribute = "android:visibility", method = "setVisibility")
})
public class ViewBA {


    @BindingAdapter({"android:visibility"})
    public static void setVisibility(View view, int value) {
        view.setVisibility(value);
    }

    @BindingAdapter({"android:visibility"})
    public static void setVisibility(View view, String value) {
        if (value == null || value.length() <= 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter({"inversevisibility"})
    public static void setInverseVisibility(View view, String value) {
        if (value == null || value.length() <= 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @BindingAdapter({"android:visibility"})
    public static void setVisibility(View view, boolean value) {
        if (value) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
