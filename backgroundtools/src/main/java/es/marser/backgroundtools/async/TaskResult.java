package es.marser.backgroundtools.async;

/**
 * Created by Sergio on 24/08/2017.
 * Fragmento de manejo de Objetos Post de mansajería
 */

public interface TaskResult<T> {
    void onTaskFinish(int resultCode, T result);
}
