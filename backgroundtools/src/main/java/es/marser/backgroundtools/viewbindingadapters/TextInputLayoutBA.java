package es.marser.backgroundtools.viewbindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 2/11/17.
 *         Adaptadores para cajas de texto
 *         <p>
 *         [EN]  Adapters for text boxes
 */
@SuppressWarnings("unused")
@BindingMethods({
        @BindingMethod(type = TextInputLayout.class, attribute = "android:hint", method = "setHint"),
        @BindingMethod(type = TextInputEditText.class, attribute = "android:lines", method = "setLines")
})
public class TextInputLayoutBA {

    @BindingAdapter({"android:lines"})
    public static void setLines(TextInputEditText view, int value) {
        view.setLines(value);
        if (value > 1) {
            view.setHorizontallyScrolling(false);
        } else {
            view.setHorizontallyScrolling(true);
        }
    }

    @BindingAdapter({"android:hint"})
    public static void setHint(TextInputLayout view, String value) {
        view.setHintAnimationEnabled(!TextTools.isEmpty(value));
        view.setHintEnabled(!TextTools.isEmpty(value));
        view.setHint(TextTools.nc(value));

    }

    @BindingAdapter({"passwordToggleEnabled"})
    public static void setPasswordVisibilityToggleEnabled(TextInputLayout view, int value) {
        view.setPasswordVisibilityToggleEnabled(value > 0);
    }

    @BindingAdapter({"errorText"})
    public static void setErrorText(TextInputLayout view, String value) {
        view.setErrorEnabled(!TextTools.isEmpty(value));
        view.setError(TextTools.nc(value));
    }

    @BindingAdapter({"counterEnabled"})
    public static void setCounterEnabled(TextInputLayout view, int value) {
        view.setCounterEnabled(value > 0);
        if (value > 0) {
            view.setCounterMaxLength(value);
        }
    }


}
