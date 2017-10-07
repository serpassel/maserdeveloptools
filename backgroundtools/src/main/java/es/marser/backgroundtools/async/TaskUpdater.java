package es.marser.backgroundtools.async;

/**
 * Created by Sergio on 31/08/2017.
 * Fragmento de manejo de Objetos Post de mansajería
 */

public interface TaskUpdater<A,B,C> {
   void onStart(A start);
   void onUpdate(B update);
   void onFinish(C finish);
   void onFailure(Throwable e);
}
