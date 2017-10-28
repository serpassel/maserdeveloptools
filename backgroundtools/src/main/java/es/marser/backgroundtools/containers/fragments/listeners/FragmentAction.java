package es.marser.backgroundtools.containers.fragments.listeners;

@SuppressWarnings("unused")
public interface FragmentAction {

    /**
     * Inicio de carga de datos
     * <p>
     * [EN]  Start loading data
     */
    void onLoadStart();

    /**
     * Conclusión de la carga de datos
     * <p>
     * [EN]  Conclusion of the data load
     */
    void onLoadFinish();
}