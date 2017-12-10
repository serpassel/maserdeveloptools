package es.marser.backgroundtools.bindingadapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import es.marser.LOG_TAG;

/**
 * @author sergio
 *         Created by Sergio on 21/04/2017.
 *         Adaptadores de enlace para textview
 *         <p>
 *         [EN]  Link adapters for textview
 */

@SuppressWarnings({"unused"})//, "WeakerAccess"})
@BindingMethods({
        @BindingMethod(type = RecyclerView.class, attribute = "android:adapter", method = "setAdapter"),
        @BindingMethod(type = RecyclerView.class, attribute = "android:hasFixedSize", method = "setHasFixedSize"),
        @BindingMethod(type = RecyclerView.class, attribute = "android:onItemTouchListener", method = "addOnItemTouchListener"),
        @BindingMethod(type = RecyclerView.class, attribute = "android:layoutManager", method = "setLayoutManager")
})
public class RecyclerViewBA {

    @BindingAdapter("android:adapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("android:layoutManager")
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter("android:hasFixedSize")
    public static void setHasFixedSize(RecyclerView view, boolean value) {
        view.setHasFixedSize(value);
    }

    @BindingAdapter("android:onItemTouchListener")
    public static void addOnItemTouchListener(RecyclerView view, RecyclerView.OnItemTouchListener onItemTouchListener) {
        if (onItemTouchListener != null) {
            view.addOnItemTouchListener(onItemTouchListener);
            Log.i(LOG_TAG.TAG, "SETEADO onItemTouchListener" );
        }
    }
}
