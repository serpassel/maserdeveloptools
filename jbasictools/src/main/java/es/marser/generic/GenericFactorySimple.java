package es.marser.generic;

import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.async.TaskLoadingResult;
import es.marser.generic.GenericFactory;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 19/11/17.
 *         Construcción de objetos anidados asíncrona simplificada
 *         <p>
 *         [EN]  Asynchronous nested object construction simplified
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class GenericFactorySimple {

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param iter     Longitud del registro individual dentro del subcampo [EN]  Length of the individual record within the subfield
     * @param tlr Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, int iter, TaskLoadingResult<T> tlr) {
        return itemsBuilder(cls, registro, iter, TextTools.ITEM_SEPARATOR_SPLIT, tlr);
    }

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param tlr Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, TaskLoadingResult<T> tlr) {
        return itemsBuilder(cls, registro, GenericFactory.getReadableColumnsCount(cls), TextTools.ITEM_SEPARATOR_SPLIT, tlr);
    }

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param tlr      Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @param iter     Longitud del registro individual dentro del subcampo [EN]  Length of the individual record within the subfield
     * @param market   Separador de sub-registros [EN]  Sub-record separator
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, int iter, String market, final TaskLoadingResult<T> tlr) {
        if (tlr == null) {
            return GenericFactory.itemsBuilder(cls, registro, iter, market, null);
        }

        return GenericFactory.itemsBuilder(cls, registro, iter, market, new DataUploaderTask<Integer, T, List<T>>() {
            @Override
            public void onStart(Integer start) {
                tlr.onStart(null);
            }

            @Override
            public void onUpdate(T update) {
                tlr.onUpdate(update);
            }

            @Override
            public void onFinish(List<T> finish) {
                tlr.onFinish(null);
            }

            @Override
            public void onFailure(Throwable e) {
                tlr.onFailure(e);
            }
        });
    }
}
