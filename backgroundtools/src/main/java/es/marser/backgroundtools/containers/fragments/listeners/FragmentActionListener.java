package es.marser.backgroundtools.containers.fragments.listeners;

import android.os.Bundle;
import android.support.annotation.Nullable;

@SuppressWarnings("unused")
public interface FragmentActionListener {

    /**
     * Indicador de acción externa de un fragment
     * <p>
     * [EN]  External action indicator of a fragment
     *
     * @param bundle Argumentos de la acción
     */
    void onFragmentAction(@Nullable Bundle bundle);
}