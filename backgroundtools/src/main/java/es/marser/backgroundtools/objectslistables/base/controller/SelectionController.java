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
    protected ArrayList<T> arrayListController;

    public SelectionController(ArrayList<T> arrayListController, ListExtra selectionmode) {
        super(selectionmode);
        this.arrayListController = arrayListController;
    }


    @Override
    protected int getItemsCount() {
        return arrayListController.size();
    }

    @Override
    protected T getItemAt(int position) {
        return arrayListController.get(position);
    }

    @Override
    protected void itemsClear() {
        arrayListController.clear();
    }

    @Override
    protected void itemsAddAll(List<T> list) {
        arrayListController.addAll(list);
    }
}
