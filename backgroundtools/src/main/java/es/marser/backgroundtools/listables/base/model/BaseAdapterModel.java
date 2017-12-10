package es.marser.backgroundtools.listables.base.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo de configuración de listas simples para patrón de diseño MVP
 *         <p>
 *         [EN]  Simple list configuration model for MVP design pattern
 */

@SuppressWarnings("unused")
public abstract class BaseAdapterModel<ADP extends BaseListAdapter> extends BaseObservable
        implements AdapterModel<ADP, LinearLayoutManager>, Selectionable {

    private Context context;
    protected ADP adapter;
    protected LinearLayoutManager layoutManager;
    private RecyclerView.OnItemTouchListener onItemTouchListener;

    private static String[] extras = new String[]{"hoderLayout_key"};
    protected static int defaultHolderLayout = R.layout.mvp_item_object_chooser;

    //CONSTRUCTORS_____________________________________________
    public BaseAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
        this.onItemTouchListener = null;
    }

    //ADAPTER MODEL_________________________________________________
    @Override
    public ADP getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(ADP adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isHasFixedSize() {
        return true;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Bindable
    @Nullable
    @Override
    public RecyclerView.OnItemTouchListener getOnItemTouchListener() {
        return onItemTouchListener;
    }

    public void setOnItemTouchListener(RecyclerView.OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
        notifyPropertyChanged(BR.onItemTouchListener);
    }

    @Override
    public void setLayoutManager(@NonNull LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    //GETTERS___________________________________________________
    public Context getContext() {
        return context;
    }

    //SAVED AND RESTORE_____________________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        //    Log.d(LOG_TAG.TAG, "RESTAURANDO  MODELO");
        if (adapter != null) {
            //      Log.d(LOG_TAG.TAG, "Guardando adaptador");
            adapter.onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
          if (adapter != null) {
                adapter.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    //SCROLLABLE______________________________________________________________________

    /**
     * Posiciona la vista en el primer elemento de la lista
     * <p>
     * [EN]  Position the view on the first item in the list
     */
    @Override
    public void scrollToFirst() {
        scrollToPosition(0);
    }

    /**
     * Posicionar la vista en la posición señalada
     * <p>
     * [EN]  Position the view in the indicated position
     *
     * @param position posición plana en el adaptador [EN]  flat position on the adapter
     */
    @Override
    public void scrollToPosition(int position) {
        if (position > -1 && position < layoutManager.getItemCount()) {
            layoutManager.scrollToPosition(position);
        }
    }

    /**
     * Guarda la posición del pimer elemento visible
     * <p>
     * [EN]  Save the position of the visible element pimer
     */
    @Override
    public int getCurrentScrollPosition() {
        return layoutManager != null ? layoutManager.findFirstVisibleItemPosition() : 0;
    }

    /**
     * Posiciona el foco en la última posición de la lista de elementos
     * <p>
     * [EN]  Position the focus in the last position in the list of elements
     */
    @Override
    public void scrollToLast() {
        scrollToPosition(layoutManager.getItemCount() - 1);
    }
}
