package es.marser.backgroundtools.async;

import java.util.List;

/**
 * Created by Sergio on 02/09/2017.
 * Fragmento de manejo de Objetos Post de mansajería
 */

public class TaskLoadingResult<T> implements TaskUpdater<Integer,T,List<T>> {
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
