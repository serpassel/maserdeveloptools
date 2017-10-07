package es.marser.backgroundtools.async;

/**
 * Created by Sergio on 31/08/2017.
 * Fragmento de manejo de Objetos Post de mansajería
 */

public interface TaskFailure<T> {
   void onFinish(T finish);
   void onFailure(Throwable e);
}
