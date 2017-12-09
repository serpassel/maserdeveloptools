package es.marser.backgroundtools.containers.dialogs.bases;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import es.marser.backgroundtools.containers.dialogs.model.ClosableView;
import es.marser.backgroundtools.enums.EventsExtras;
import es.marser.backgroundtools.systemtools.events.SimpleGestureFilter;

/**
 * @author sergio
 *         Created by Sergio on 15/09/2017.
 *         Base para creación de dialogos personalizados.
 *         Mantener un constructor vacío para evitar problemas de compilación
 *         <p>
 *         [EN]  Basis for creating custom dialogs
 *         Keep an empty constructor to avoid compilation problems
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseDialog
        extends DialogFragment
        implements View.OnTouchListener,SimpleGestureFilter.SimpleGestureListener, ClosableView {
    protected Context context;
    protected Dialog dialog;
    protected View view;
    protected SimpleGestureFilter gestureFilter;

    public BaseDialog() {
        gestureFilter = new SimpleGestureFilter(getActivity(), this);
    }


    //EVENTs____________________________________________________________

    /**
     * Agregar captador de eventos de ventana
     * <p>
     * [EN]  Add window event sensor
     */
    private void setWindowCallback() {
     /*
        Window w = dialog != null ? dialog.getWindow() : null;
        if (w != null) {
            w.setCallback(new WindowsCallbackAdapter() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        getDialog().dismiss();
                    }
                    return false;
                }

                @Override
                public boolean dispatchTouchEvent(MotionEvent event) {
                    if (gestureFilter != null) {
                        gestureFilter.onTouchEvent(event);
                    }
                    return false;
                }
            });
        }
*/
        view.setOnTouchListener(this);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureFilter.onTouchEvent(event);
        return true;
    }

    @Override
    public void onSwipe(EventsExtras eventsExtras) {
    }

    @Override
    public void onDoubleTap() {
    }

    /**
     * Procedimiento para crear el dialog. método para sobreescribir
     * <p>
     * [EN]  Procedure to create the dialog.  method to overwrite
     */
    protected abstract void createDialog();

    /**
     * Vista principal del dialogo
     * <p>
     * [EN]  Main view of the dialogue
     *
     * @return R.layout.XXXXXX
     */
    protected abstract int getDialogLayout();

    /**
     * Procedimiento para mostrar el dialogo. Oculta el teclado por defecto
     * <p>
     * [EN]  Procedure to display the dialog.  Hides the default keyboard
     */
    public void show() {
        if (!isShowing()) {
            createDialog();

            if (view != null) {
                setWindowCallback();

                view.clearFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            dialog.show();
        }
    }

    /**
     * Método de cierre del dialogo
     * <p>
     * [EN]  Method of closing the dialog
     */
    @Override
    public void close() {
        if (dialog != null && isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * Indentificar si el dialogo está activado
     * <p>
     * [EN]  Indicate whether the dialog is activated
     *
     * @return verdadero si está activo [EN]  true if active
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * Getter del contexto que ha lanzado del dialogo
     * <p>
     * [EN]  Context getter that has released dialog
     *
     * @return Contexto vinculado al dialogo [EN]  Context linked to the dialogue
     */
    @Override
    public Context getContext() {
        return context;
    }

    /**
     * Setter de la variable de contexto
     * <p>
     * [EN]  Context Variable Setter es
     *
     * @param context Contexto sobre el que se vincula el dialogo [EN]  Context on which the dialogue is linked
     */
    public void setContext(Context context) {
        this.context = context;
    }
}
