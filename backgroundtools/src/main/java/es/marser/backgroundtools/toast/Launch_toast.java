package es.marser.backgroundtools.toast;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.toast.model.ToastModel;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Clase para lanzar toast. Activar DataBinding
 *         <p>
 *         Para que la librería funcione activar Biblioteca de vinculación de datos de android, en el módulo de la app
 *         <p>
 *         [EN]  Class to throw toast
 *         <p>
 *         <p>
 *         In order for the library to work activate android Databinding Library, in the module of the app
 *         <p>
 *         android {...
 *         dataBinding{
 *         enabled = true
 *         }
 *         }
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Launch_toast {

    private static void launchToast(Context context, ToastModel toastModel) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mvp_toast, null, false);
        View view = viewDataBinding.getRoot();

        viewDataBinding.setVariable(BR.model, toastModel);
        viewDataBinding.executePendingBindings();


        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        try {
            toast.show();
        } catch (Exception ignored) {
        }
    }

    /**
     * Lanza un mensaje de información
     * <p>
     * [EN]  Launch an information message
     *
     * @param context contexto desde el que se lanza el toast [EN]  context from which the toast is launched
     * @param message Mensaje de información [EN]  Message of information
     */
    public static void informationToast(Context context, String message) {
        ToastModel toastModel = new ToastModel();
        toastModel.icon.set(ToastModel.INFORMATION_ICON);
        toastModel.title.set(context.getResources().getStringArray(R.array.toast_title)[0]);
        toastModel.msg.set(TextTools.nc(message));
        launchToast(context, toastModel);
    }

    /**
     * Lanza un mensaje de advertencia
     * <p>
     * [EN]  Throw a warning message
     *
     * @param context contexto desde el que se lanza el toast [EN]  context from which the toast is launched
     * @param message Mensaje de advertencia [EN]  Warning message
     */
    public static void warningToast(Context context, String message) {
        ToastModel toastModel = new ToastModel();
        toastModel.icon.set(ToastModel.WARNING_ICON);
        toastModel.title.set(context.getResources().getStringArray(R.array.toast_title)[1]);
        toastModel.msg.set(TextTools.nc(message));
        launchToast(context, toastModel);
    }

    /**
     * Lanza un mensaje de error
     * <p>
     * [EN]  Throws an error message
     *
     * @param context contexto desde el que se lanza el toast [EN]  context from which the toast is launched
     * @param message Mensaje de error [EN]  Error message
     */
    public static void errorToast(Context context, String message) {
        ToastModel toastModel = new ToastModel();
        toastModel.icon.set(ToastModel.ERROR_ICON);
        toastModel.title.set(context.getResources().getStringArray(R.array.toast_title)[2]);
        toastModel.msg.set(TextTools.nc(message));
        launchToast(context, toastModel);
    }

}
