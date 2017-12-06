package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.table.adapter.TableListAdapterDECREP;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base de construcción de fragment con lista de multiples vistas cabecera y cuerpo
 *         <p>
 *         [EN]  Fragment construction base with list of multiple head and body views
 */

@SuppressWarnings({"unused", "SameReturnValue"})
@Deprecated
public abstract class BaseFragmentBinTableDECREP<H extends Parcelable, B extends Parcelable>
        extends BaseFragmentList {

    protected TableListAdapterDECREP<H, B> adapter;

    public BaseFragmentBinTableDECREP() {
        super();
    }

    //SAVED AND RESTORE_________________________________________________________________________________

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        if (adapter != null) {
            adapter.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    //OBLIGATORY OVERWRITING___________________________________________________________________________
    public abstract int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    public int getTitleHolderLayout(){
        return -1;
    }

    //OPTIONAL OVERWRITE________________________________________________________________________________
    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return new ViewItemHandler<H>() {
            @Override
            public void onClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                BaseFragmentBinTableDECREP.this.onClickHeadItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                return BaseFragmentBinTableDECREP.this.onLongClickHeadItem(holder, item, position, mode);
            }
        };
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return new ViewItemHandler<B>() {
            @Override
            public void onClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                BaseFragmentBinTableDECREP.this.onClickBodyItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                return BaseFragmentBinTableDECREP.this.onLongClickBodyItem(holder, item, position, mode);
            }
        };
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return new TouchableViewHandler<H>() {
            @Override
            public void onClick(View view, int position, H item, View root) {
                BaseFragmentBinTableDECREP.this.onClickHead(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, H item, View root) {
                return BaseFragmentBinTableDECREP.this.onLongClickHead(view, position, item, root);
            }
        };
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return new TouchableViewHandler<B>() {
            @Override
            public void onClick(View view, int position, B item, View root) {
                BaseFragmentBinTableDECREP.this.onClickBody(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, B item, View root) {
                return BaseFragmentBinTableDECREP.this.onLongClickBody(view, position, item, root);
            }
        };
    }


    //SUPERCLASS OVERWRITING________________________________________________________________________
    @Override
    protected void bindAdapter(@Nullable Bundle savedInstanceState) {
       // Log.w(LOG_TAG.TAG, "bind adapter");

       // Log.i(LOG_TAG.TAG, "Adaptador nulo: " + (adapter == null));

        if (adapter == null) {
            adapter = new TableListAdapterDECREP<H, B>() {
                @Override
                public int getHeadHolderLayout() {
                    return BaseFragmentBinTableDECREP.this.getHeadHolderLayout();
                }

                @Override
                public int getBodyHolderLayout() {
                    return BaseFragmentBinTableDECREP.this.getBodyHolderLayout();
                }

                @Override
                public int getTitleHolderLayout() {
                    return BaseFragmentBinTableDECREP.this.getTitleHolderLayout();
                }

                @Override
                public ViewItemHandler<H> getHeadItemHandler() {
                    return BaseFragmentBinTableDECREP.this.getHeadItemHandler();
                }

                @Override
                public ViewItemHandler<B> getBodyItemHandler() {
                    return BaseFragmentBinTableDECREP.this.getBodyItemHandler();
                }

                @Override
                public TouchableViewHandler<H> getHeadTouchableViewHandler() {
                    return BaseFragmentBinTableDECREP.this.getHeadTouchableViewHandler();
                }

                @Override
                public TouchableViewHandler<B> getBodyTouchableViewHandler() {
                    return BaseFragmentBinTableDECREP.this.getBodyTouchableViewHandler();
                }
            };
           // adapter.hAdapterController.setSelectionmode(getInitialSelectionMode());
           // adapter.bAdapterController.setSelectionmode(getInitialSelectionMode());
        }

       // Log.i(LOG_TAG.TAG, "RecyclerView no tiene adaptador pre-set: " + (recyclerView.getAdapter() == null));
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(adapter);
        }

        //Log.i(LOG_TAG.TAG, "RecyclerView no tiene adaptador post-set: " + (recyclerView.getAdapter() == null));
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvc_frag_simple_list;
    }

    @Override
    public boolean isEmpty() {
        return getHeadGlobalController().isEmpty() && getBodyGlobalController().isEmpty();
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    //CONTROL OF ITEMS____________________________________________________________________________

    /**
     * @return controladora de cabecera
     * [EN]  header controller
     */
    public AdapterController<H> getHeadGlobalController() {
        return adapter.hAdapterController;
    }

    /**
     * @return Controladora de cuerpo
     * [EN]  Body controller
     */
    public AdapterController<B> getBodyGlobalController() {
        return adapter.bAdapterController;
    }

    /**
     * @return Controladora de títulos
     * [EN]  Controller of titles
     */
    public AdapterController<Selectable> getTitleGlobalController(){
        return adapter.tAdapterController;
    }

    /**
     * Reemplaza todos los elementos del adaptador
     * <p>
     * [EN]  Replaces all adapter elements
     *
     * @param items Lista de elementos de tipo genérico [EN]  List of elements of generic type
     */
    public void setHeadItems(ArrayList<H> items) {
        if (items != null) {
            getHeadGlobalController().replace(items);
        }
    }

    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clearBody() {
        getBodyGlobalController().clear();
    }

    /**
     * Agregar un elemento no nulo al final de la lista
     * <p>
     * [EN]  Add a non-null element to the end of the list
     *
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void addBodyItem(B item) {
        if (item != null) {
            getBodyGlobalController().add(item);
        }
    }

    /**
     * Agrega un elemento nuevo en la posición señalada
     * <p>
     * [EN]  Add a new item in the highlighted position
     *
     * @param id   posición donde inserta el nuevo elemento [EN]  position where you insert the new item
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void insertBodyItem(int id, B item) {
        if (item != null && id > -1 && id < getItemCount()) {
            getBodyGlobalController().add(id, item);
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Actualizar un registro existente
     * <p>
     * [EN]  Update an existing record
     *
     * @param id   posición del elemento en el adapter [EN]  position of the element in the adapter
     * @param item elemento actualizado [EN]  item updated
     */
    public void updateBodyItem(int id, B item) {
        if (item != null && id > -1 && id < getItemCount()) {
            getBodyGlobalController().set(id, item);
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Eliminar un registro en la posición indicada
     * <p>
     * [EN]  Delete a record in the indicated position
     *
     * @param id posición del elemento a eliminar
     *           [EN]  position of the item to be deleted
     */
    public void deleteBodyItem(int id) {
        if (id > -1 && id < getItemCount()) {
            adapter.adapterController.remove(id);
            adapter.adapterController.getSelectionItemsController().clear();
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Notificar cambios en el adapter
     * <p>
     * [EN]  Notify changes to the adapter
     */
    public void notifyChangedData() {
        adapter.notifyDataSetChanged();
    }

    //HANDLERS_____________________________________________________________________________

    /* @{link ViewItemHandler*/
    @SuppressWarnings("EmptyMethod")
    public void onClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
    }

    public boolean onLongClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
        return true;
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
    }

    public boolean onLongClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
        return true;
    }

    /*@{link TouchableViewHandler}*/
    @SuppressWarnings("EmptyMethod")
    public void onClickHead(View view, int position, H item, View root) {

    }

    public boolean onLongClickHead(View view, int position, H item, View root) {
        return true;
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBody(View view, int position, B item, View root) {

    }

    public boolean onLongClickBody(View view, int position, B item, View root) {
        return true;
    }
}