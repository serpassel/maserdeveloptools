package es.marser.backgroundtools.listables.base.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Modelo de configuración de listas simples para patrón de diseño MVP
 *         <p>
 *         [EN]  Simple list configuration model for MVP design pattern
 */

@SuppressWarnings("unused")
public class BaseAdapterModel<T extends Parcelable, ADP extends BaseListAdapter>
        implements AdapterModel<ADP>, Selectionable {

    private Context context;
    protected ADP adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected int holderLayout;

    protected static int defaultHolderLayout = R.layout.mvp_item_object_chooser;

    //CONSTRUCTORS_____________________________________________
    public BaseAdapterModel(@NonNull Context context) {
        this(context, defaultHolderLayout);
    }

    public BaseAdapterModel(@NonNull Context context, int holderLayout) {
        this(context, new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false), holderLayout);
    }

    public BaseAdapterModel(@NonNull Context context, int rows, int holderLayout) {
        this(context,
                rows < 2
                        ? new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        : new GridLayoutManager(context, rows),
                holderLayout);
    }

    public BaseAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        this(context, layoutManager, defaultHolderLayout);
    }

    public BaseAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager, int holderLayout) {
        this.context = context;
        this.layoutManager = layoutManager;
        if (holderLayout < 0) {
            holderLayout = defaultHolderLayout;
        }
        this.holderLayout = holderLayout;
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
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public void setLayoutManager(@NonNull RecyclerView.LayoutManager layoutManager) {
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

    //SELECTIONABLE_________________________________________________________________

    @Nullable
    @Override
    public ListExtra getSelectionmode() {
        Selectionable selectionable = adapter != null ? adapter.getSelectionable(null) : null;
        return selectionable != null ? selectionable.getSelectionmode() : null;
    }

    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        Selectionable selectionable = adapter != null ? adapter.getSelectionable(null) : null;
        if (selectionable != null) {
            selectionable.setSelectionmode(selectionmode);
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
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return 0;
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
