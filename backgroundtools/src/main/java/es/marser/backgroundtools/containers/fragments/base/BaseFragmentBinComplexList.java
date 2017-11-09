package es.marser.backgroundtools.containers.fragments.base;

import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.listeners.FragmentAction;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.handlers.ViewComplexHandler;
import es.marser.backgroundtools.objectslistables.complex.adapter.ComplexAdapter;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexExpandController;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexSelectionController;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableList;

/**
 * @author sergio
 *         Created by Sergio on 28/04/2017.
 *         Base para listas expandibles con objetos enlazados
 *         <p>
 *         [EN]  Base for expandable lists with linked objects
 *         <p>
 *         <ul>
 *         <il>Variable start</il>
 *         <il>Abstract Methods of Configuration</il>
 *         <il>View event handlers</il>
 *         <il>Control of items</il>
 *         <il>Change listeners in fragments</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentBinComplexList<G extends ExpandableGroup<C>, C extends Parcelable>
        extends BaseFragmentList implements ComplexTouchabeViewHandler<G, C>, ViewComplexHandler<G, C> {

    protected ComplexAdapter<G, C> adapter;

    @Override
    protected void instanceVariables() {

    }

    //ABSTRACT METHODS OF CONFIGURATION_______________________________________________________________

    /*Vistas [EN] Views*/

    /**
     * Definición de la vista de los items de la lista
     * <p>
     * [EN]  Defining the view of items in the list
     *
     * @return R.layout.XXXX Vista de los items [EN]  View items
     */
    protected abstract int getGroupLayoutHolder();//{return 0;}

    protected abstract int getChildLayoutHolder();//{return 0;}

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvc_frag_simple_list;
    }

    /*Vistas de componentes [EN]  Component views*/

    /**
     * Enlace del adaptador de la lista {@link BaseFragmentList#initActivityCreated()}
     * <p>
     * [EN]  List adapter link
     */
    protected void bindAdapter() {
        adapter = new ComplexAdapter<G, C>() {
            @Override
            protected int getGroupLayoutHolder() {
                return BaseFragmentBinComplexList.this.getGroupLayoutHolder();
            }

            @Override
            protected int getChildLayoutHolder() {
                return BaseFragmentBinComplexList.this.getChildLayoutHolder();
            }

            @Override
            public ComplexTouchabeViewHandler<G, C> getComplexTouchabeViewHandler() {
                return BaseFragmentBinComplexList.this;
            }

            @Override
            public ViewComplexHandler<G, C> getViewComplexHandler() {
                return BaseFragmentBinComplexList.this;
            }
        };

        recyclerView.setAdapter(adapter);
    }

    //VIEW EVENT HANDLERS_____________________________________________________________________________

    /*{@link ComplexTouchabeViewHandler}*/
    @Override
    public void onGroupClick(View view, int flap, View root, int index, G group) {

    }

    @Override
    public boolean onGroupLongClick(View view, int flap, View root, int index, G group) {
        return false;
    }

    @Override
    public void onChildClick(View view, int flap, View root, int groupid, G group, int childid, C child) {

    }

    @Override
    public boolean onChildLongClick(View view, int flap, View root, int groupid, G group, int childid, C child) {
        return true;
    }

    /*{@link ViewComplexHandler}*/
    @Override
    public void onGroupExpanded(G group) {

    }

    @Override
    public void onGroupCollapsed(G group) {

    }

    @Override
    public void onClickGroupItem(int grouppos, G group, int flatpos, ListExtra selectionmode) {

    }

    @Override
    public boolean onLongClickGroupItem(int grouppos, G group, int flatpos, ListExtra selectionmode) {
        return true;
    }

    @Override
    public void onClickChildItem(int grouppos, int childpos, C child, int flatpos, ListExtra selectionmode) {

    }

    @Override
    public boolean onLongClickChildItem(int grouppos, int childpos, C child, int flatpos, ListExtra selectionmode) {
        return true;
    }


    //CONTROL OF ITEMS____________________________________________________________________________
    /**
     * Método de carga de datos
     * <p>
     * [EN]  Data Upload Method
     */
    public abstract void load(FragmentAction fragmentAction);

    /**
     * Acceso al controlador de selección del adaptador de elementos
     * <p>
     * [EN]  Access to the items Adapter Selection Controller
     *
     * @return Controlador de selección {@link ComplexSelectionController}
     * [EN]  Selection controller {@link ComplexSelectionController}
     */
    public ComplexSelectionController<G, C> getSelectionController() {
        return adapter.getComplexSelectionController();
    }

    /**
     * Acceso a los elementos de la lista
     * <p>
     * [EN]  Access to list items
     *
     * @return Lista expansible de elementos {@link ExpandableList}
     * [EN]  Expandable list of items {@link ExpandableList}
     */
    public ExpandableList<G, C> getExpandableList() {
        return adapter.getExpandableList();
    }

    /**
     * Controlador de expansión de grupos
     * <p>
     * [EN]  Group Expansion Controller
     *
     * @return Controlador de expansión de grupos {@link ComplexSelectionController}
     * [EN]  Group Expansion Controller {@link ComplexSelectionController}
     */
    public ComplexExpandController<G, C> getExpandController() {
        return adapter.getExpandCollapseController();
    }

    /**
     * Reemplaza todos los elementos del adaptador
     * <p>
     * [EN]  Replaces all adapter elements
     *
     * @param items Lista de elementos de tipo genérico [EN]  List of elements of generic type
     */
    public void setGroups(ArrayList<G> groups) {
        if (groups != null) {
            adapter.replaceAllGroups(groups);
        }
    }


    /**
     * Eliminar la lista de elementos
     * <p>
     * [EN]  Delete item list
     */
    public void clear() {
        adapter.removeAllGroups();
    }

    /**
     * Número de elementos en la lista
     * <p>
     * [EN]  Number of items in the list
     *
     * @return número de elementos de la lista [EN]  number of items in the list
     */
    public int getItemCount() {
        return adapter.getItemCount();
    }

    /**
     * Agregar un elemento no nulo al final de la lista
     * <p>
     * [EN]  Add a non-null element to the end of the list
     *
     * @param item Objeto genérico nuevo [EN]  New Generic Object
     */
    public void addGroup(G item) {
        if (item != null) {
            adapter.addGroup(item);
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
    public void insertGroup(int id, G item) {
        if (item != null && id > -1 && id < getItemCount()) {
            adapter.insertGroup(id, item);
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
    public void updateGroup(int id, G item) {
        if (item != null && id > -1 && id < getItemCount()) {
            adapter.updateGroup(id, item);
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
    public void deleteGroup(int id) {
        if (id > -1 && id < getItemCount()) {
            adapter.removeGroup(id);
            scrollToId(id);
            savedScroll();
        }
    }

    /**
     * Comprobar si hay elementos en el adapter
     * <p>
     * [EN]  Check for items in the adapter
     *
     * @return verdadero si no hay elementos
     * [EN]  true if there are no elements
     */
    public boolean isEmpty() {
        return adapter.getGroups().isEmpty();
    }


    /**
     * Notificar cambios en el adapter
     * <p>
     * [EN]  Notify changes to the adapter
     */
    public void notifyChangedData() {
        adapter.notifyDataSetChanged();
    }

}
