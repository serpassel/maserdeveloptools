package es.marser.backgroundtools.dialogs.task;

import android.view.View;

import es.marser.backgroundtools.enums.DialogExtras;

/**
 * @author sergio
 *         Created by sergio on 20/10/17.
 *         Resultado para dialogos
 *         <p>
 *         [EN]  Result for dialogues
 */

@SuppressWarnings({"EmptyMethod", "unused"})
public interface OnResult<T> {
    void onResult(DialogExtras result, T value);

    void onClick(View view, T value);
}


