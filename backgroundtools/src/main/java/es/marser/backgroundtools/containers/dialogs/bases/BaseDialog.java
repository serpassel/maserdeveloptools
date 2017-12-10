package es.marser.backgroundtools.containers.dialogs.bases;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
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
        implements RecyclerView.OnItemTouchListener, SimpleGestureFilter.SimpleGestureListener, ClosableView {

    //View.OnTouchListener,

    protected Context context;
    protected Dialog dialog;
    protected View view;
    protected SimpleGestureFilter gestureFilter;

    public BaseDialog() {
        gestureFilter = new SimpleGestureFilter(getActivity(), this);
    }


    //EVENTs____________________________________________________________
    //RECYCLERVIEW.ONITEMTOUCHLISTENER
    /**
     * Silently observe and/or take over touch events sent to the RecyclerView
     * before they are handled by either the RecyclerView itself or its child views.
     * <p>
     * <p>The onInterceptTouchEvent methods of each attached OnItemTouchListener will be run
     * in the order in which each listener was added, before any other touch processing
     * by the RecyclerView itself or child views occurs.</p>
     *
     * @param rv {@link RecyclerView}
     * @param e  MotionEvent describing the touch event. All coordinates are in
     *           the RecyclerView's coordinate system.
     * @return true if this OnItemTouchListener wishes to begin intercepting touch events, false
     * to continue with the current behavior and continue observing future events in
     * the gesture.
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureFilter.onTouchEvent(e);
        //   Log.d(TAG, "onInterceptTouchEvent: ");
        return false;
    }

    /**
     * Process a touch event as part of a gesture that was claimed by returning true from
     * a previous call to {@link #onInterceptTouchEvent}.
     *
     * @param rv  {@link RecyclerView}
     * @param e  MotionEvent describing the touch event. All coordinates are in
     */
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        //    Log.w(TAG, "onTouchEvent: " );
    }

    /**
     * Called when a child of RecyclerView does not want RecyclerView and its ancestors to
     * intercept touch events with
     * {@link ViewGroup#onInterceptTouchEvent(MotionEvent)}.
     *
     * @param disallowIntercept True if the child does not want the parent to
     *                          intercept touch events.
     * @see ViewParent#requestDisallowInterceptTouchEvent(boolean)
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        //  Log.i(TAG, "onRequestDisallowInterceptTouchEvent: ");
    }
    /**
     * Agregar captador de eventos de ventana
     * <p>
     * [EN]  Add window event sensor
     */
    @SuppressWarnings("EmptyMethod")
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
   //     view.setOnTouchListener(this);
    }

/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureFilter.onTouchEvent(event);
        return true;
    }
*/
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
                //setWindowCallback();

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
