package es.marser.backgroundtools.objectslistables.base.listeners;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Notificador de adapter
 *         <p>
 *         [EN]  Adapter Notifier
 */

public interface AdapterNotifier {
    @SuppressWarnings("unused")
    void notifyDataSetChanged(int viewType);

    void notifyAddItem(int index, int viewType);
    void notifyAddAll(int viewType);

    void notifyClear(int viewType);
    void notifyRemoveItem(int index, int viewType);


    void notifyItemChanged(int index, int viewType);


    @SuppressWarnings("unused")
    int flatPos(int index, int viewType);

    int indexPos(int flaPos, int viewType);
}
