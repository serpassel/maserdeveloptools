package es.marser.backgroundtools.objectslistables.base.listeners;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Notificador de adapter
 *         <p>
 *         [EN]  Adapter Notifier
 */

@SuppressWarnings("unused")
public interface AdapterNotifier {
    @SuppressWarnings("unused")

    void notifyItemInserted(int index, int count, int viewType);

    void notifyItemRemoved(int index, int viewType);

    void notifyItemChanged(int index, int viewType);

    void notifyDataSetChanged(int viewType);

    void notifyDataAdd(int count, int viewType);

    void notifyDataRemoved(int count, int viewType);


    @SuppressWarnings("unused")
    int flatPos(int index, int viewType);

    int indexPos(int flaPos, int viewType);
}
