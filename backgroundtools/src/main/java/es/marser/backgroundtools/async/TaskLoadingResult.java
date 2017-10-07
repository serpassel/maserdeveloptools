package es.marser.backgroundtools.async;

import java.util.List;

/**
 * @author sergio
 *         Created by Sergio on 02/09/2017.
 *         Clase adaptadora para definiciones de oyentes de carga de datos asíncrona, con un unico objeto genérico de resultado
 *         <p>
 *         [EN]  Adapter class for asynchronous data load listeners definitions, with a single generic result object
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class TaskLoadingResult<T> implements DataUploaderTask<Integer, T, List<T>> {

    /**
     *
     * @param start Número de Objetos a procesar
     */
    @Override
    public void onStart(Integer start) {

    }

    /**
     *
     * @param update objeto genérico procesado
     */
    @Override
    public void onUpdate(T update) {

    }

    /**
     *
     * @param finish Lista de objetos genéricos procesados
     */
    @Override
    public void onFinish(List<T> finish) {

    }

    /**
     *
     * @param e Objeto de error generado durante lla ejecución de la tarea
     */
    @Override
    public void onFailure(Throwable e) {

    }
}
