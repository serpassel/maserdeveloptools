package es.marser.backgroundtools.objectslistables.base.controller;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;


/**
 * @author sergio
 *         Created by Sergio on 30/04/2017.
 *         Controlador de selección para listas simples
 *         <p>
 *         [EN]  Simple List Controller
 * @see BaseSelectionController
 * @see es.marser.backgroundtools.recyclerviews.simple.adapter.BaseBindAdapterList
 */

@SuppressWarnings("unused")
public class SelectionController<T> extends BaseSelectionController<T> {
    /*Variable de arreglo de registros [EN]  Registry Array Variable*/
    protected ArrayList<T> items;

    public SelectionController(ArrayList<T> items, ListExtra selectionmode) {
        super(selectionmode);
        this.items = items;
    }

    @Override
    protected int getItemsCount() {
        return items.size();
    }

    @Override
    protected T getItemAt(int position) {
        return items.get(position);
    }

    @Override
    protected void itemsClear() {
        items.clear();
    }

    @Override
    protected void itemsAddAll(List<T> list) {
        items.addAll(list);
    }
}
