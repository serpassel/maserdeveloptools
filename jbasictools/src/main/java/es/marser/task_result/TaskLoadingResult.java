package es.marser.task_result;

import java.util.List;

/**
 * Created by Sergio on 02/09/2017.
 * @author sergio
 *         Created by Sergio on 31/08/2017.
 *         Resultados y evolución de tareas asíncronas en listas de objectos
 *         <p>
 *         [EN]  Results and evolution of asynchronous tasks in object lists
 *         @see TaskUpdater
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class TaskLoadingResult<T> implements TaskUpdater<Integer,T,List<T>> {
    public static final String START = "start";
    public static final String UPDATE = "update";
    public static final String FINISH = "finish";

    @Override
    public void onStart(Integer start) {

    }

    @Override
    public void onUpdate(T update) {

    }

    @Override
    public void onFinish(List<T> finish) {

    }

    @Override
    public void onFailure(Throwable e) {

    }
}
