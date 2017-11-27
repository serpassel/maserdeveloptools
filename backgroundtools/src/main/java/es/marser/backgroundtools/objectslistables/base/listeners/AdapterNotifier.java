package es.marser.backgroundtools.objectslistables.base.listeners;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Notificador de adapter
 *         <p>
 *         [EN]  Adapter Notifier
 */

public interface AdapterNotifier {
    void notifyItemRemoved(int index, int viewType);
    @SuppressWarnings("unused")
    void notifyDataSetChanged(int viewType);
    void notifyAddRange(int indexStart, int indexEnd, int viewType);
    void notifyRemoveRange(int indexStart, int indexEnd, int viewType);
    void notifyItemChanged(int index, int viewType);
    void notifyItemInserted(int index, int viewType);


    @SuppressWarnings("unused")
    int flatPos(int index, int viewType);

    int indexPos(int flaPos, int viewType);
}
