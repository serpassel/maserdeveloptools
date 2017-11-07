package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.view.View;

/**
 * @author sergio
 *         Created by sergio on 6/11/17.
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

    @BindingAdapter({"android:visibility"})
    public static void setVisibility(View view, boolean value) {
        if (value) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
