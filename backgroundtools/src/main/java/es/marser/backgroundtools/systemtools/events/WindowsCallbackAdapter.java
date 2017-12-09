package es.marser.backgroundtools.systemtools.events;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Adaptador de eventos de ventana [EN]  Window event adapter
 */

@SuppressWarnings("unused")
public class WindowsCallbackAdapter implements Window.Callback {
    /**
     * Called to process key events.  At the very least your
     * implementation must call
     * {@link Window#superDispatchKeyEvent} to do the
     * standard key processing.
     *
     * @param event The key event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    /**
     * Called to process a key shortcut event.
     * At the very least your implementation must call
     * {@link Window#superDispatchKeyShortcutEvent} to do the
     * standard key shortcut processing.
     *
     * @param event The key shortcut event.
     * @return True if this event was consumed.
     */
    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return false;
    }

    /**
     * Called to process touch screen events.  At the very least your
     * implementation must call
     * {@link Window#superDispatchTouchEvent} to do the
     * standard touch screen processing.
     *
     * @param event The touch screen event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * Called to process trackball events.  At the very least your
     * implementation must call
     * {@link Window#superDispatchTrackballEvent} to do the
     * standard trackball processing.
     *
     * @param event The trackball event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return false;
    }

    /**
     * Called to process generic motion events.  At the very least your
     * implementation must call
     * {@link Window#superDispatchGenericMotionEvent} to do the
     * standard processing.
     *
     * @param event The generic motion event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return false;
    }

    /**
     * Called to process population of {@link AccessibilityEvent}s.
     *
     * @param event The event.
     * @return boolean Return true if event population was completed.
     */
    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    /**
     * Instantiate the view to display in the panel for 'featureId'.
     * You can return null, in which case the default content (typically
     * a menu) will be created for you.
     *
     * @param featureId Which panel is being created.
     * @return view The top-level view to place in the panel.
     * @see #onPreparePanel
     */
    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return null;
    }

    /**
     * Initialize the contents of the menu for panel 'featureId'.  This is
     * called if onCreatePanelView() returns null, giving you a standard
     * menu in which you can place your items.  It is only called once for
     * the panel, the first time it is shown.
     * <p>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreatePanelMenu() is called for this feature.
     *
     * @param featureId The panel being created.
     * @param menu      The menu inside the panel.
     * @return boolean You must return true for the panel to be displayed;
     * if you return false it will not be shown.
     */
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return false;
    }

    /**
     * Prepare a panel to be displayed.  This is called right before the
     * panel window is shown, every time it is shown.
     *
     * @param featureId The panel that is being displayed.
     * @param view      The View that was returned by onCreatePanelView().
     * @param menu      If onCreatePanelView() returned null, this is the Menu
     *                  being displayed in the panel.
     * @return boolean You must return true for the panel to be displayed;
     * if you return false it will not be shown.
     * @see #onCreatePanelView
     */
    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return false;
    }

    /**
     * Called when a panel's menu is opened by the user. This may also be
     * called when the menu is changing from one type to another (for
     * example, from the icon menu to the expanded menu).
     *
     * @param featureId The panel that the menu is in.
     * @param menu      The menu that is opened.
     * @return Return true to allow the menu to open, or false to prevent
     * the menu from opening.
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return false;
    }

    /**
     * Called when a panel's menu item has been selected by the user.
     *
     * @param featureId The panel that the menu is in.
     * @param item      The menu item that was selected.
     * @return boolean Return true to finish processing of selection, or
     * false to perform the normal menu handling (calling its
     * Runnable or sending a Message to its target Handler).
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    /**
     * This is called whenever the current window attributes change.
     *
     * @param attrs atributos de ventana
     */
    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {

    }

    /**
     * This hook is called whenever the content view of the screen changes
     * (due to a call to
     * {@link Window#setContentView(View, ViewGroup.LayoutParams)
     * Window.setContentView} or
     * {@link Window#addContentView(View, ViewGroup.LayoutParams)
     * Window.addContentView}).
     */
    @Override
    public void onContentChanged() {

    }

    /**
     * This hook is called whenever the window focus changes.  See
     * {@link View#onWindowFocusChanged(boolean)
     * View.onWindowFocusChangedNotLocked(boolean)} for more information.
     *
     * @param hasFocus Whether the window now has focus.
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    /**
     * Called when the window has been attached to the window manager.
     * See {@link View#onAttachedToWindow() View.onAttachedToWindow()}
     * for more information.
     */
    @Override
    public void onAttachedToWindow() {

    }

    /**
     * Called when the window has been attached to the window manager.
     * See {@link View#onDetachedFromWindow() View.onDetachedFromWindow()}
     * for more information.
     */
    @Override
    public void onDetachedFromWindow() {

    }

    /**
     * Called when a panel is being closed.  If another logical subsequent
     * panel is being opened (and this panel is being closed to make room for the subsequent
     * panel), this method will NOT be called.
     *
     * @param featureId The panel that is being displayed.
     * @param menu      If onCreatePanelView() returned null, this is the Menu
     */
    @Override
    public void onPanelClosed(int featureId, Menu menu) {

    }

    /**
     * Called when the user signals the desire to start a search.
     *
     * @return true if search launched, false if activity refuses (blocks)
     * @see Activity#onSearchRequested()
     */
    @Override
    public boolean onSearchRequested() {
        return false;
    }

    /**
     * Called when the user signals the desire to start a search.
     *
     * @param searchEvent A {@link SearchEvent} describing the signal to
     *                    start a search.
     * @return true if search launched, false if activity refuses (blocks)
     */
    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    /**
     * Called when an action mode is being started for this window. Gives the
     * callback an opportunity to handle the action mode in its own unique and
     * beautiful way. If this method returns null the system can choose a way
     * to present the mode or choose not to start the mode at all. This is equivalent
     * to {@link #onWindowStartingActionMode(ActionMode.Callback, int)}
     * with type {@link ActionMode#TYPE_PRIMARY}.
     *
     * @param callback Callback to control the lifecycle of this action mode
     * @return The ActionMode that was started, or null if the system should present it
     */
    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    /**
     * Called when an action mode is being started for this window. Gives the
     * callback an opportunity to handle the action mode in its own unique and
     * beautiful way. If this method returns null the system can choose a way
     * to present the mode or choose not to start the mode at all.
     *
     * @param callback Callback to control the lifecycle of this action mode
     * @param type     One of {@link ActionMode#TYPE_PRIMARY} or {@link ActionMode#TYPE_FLOATING}.
     * @return The ActionMode that was started, or null if the system should present it
     */
    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    /**
     * Called when an action mode has been started. The appropriate mode callback
     * method will have already been invoked.
     *
     * @param mode The new mode that has just been started.
     */
    @Override
    public void onActionModeStarted(ActionMode mode) {

    }

    /**
     * Called when an action mode has been finished. The appropriate mode callback
     * method will have already been invoked.
     *
     * @param mode The mode that was just finished.
     */
    @Override
    public void onActionModeFinished(ActionMode mode) {

    }
}
