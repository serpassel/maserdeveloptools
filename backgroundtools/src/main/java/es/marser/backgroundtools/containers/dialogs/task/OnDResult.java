package es.marser.backgroundtools.containers.dialogs.task;

import es.marser.backgroundtools.enums.DialogExtras;

/**
 * @author sergio
 *         Created by sergio on 20/10/17.
 *         Resultado para dialogos
 *         <p>
 *         [EN]  Result for dialogues
 */

@SuppressWarnings({"EmptyMethod", "unused"})
public interface OnDResult<T, X> {
    void onResult(DialogExtras result, T value1, X value2);
}


