package es.marser.backgroundtools.objectslistables.base.controller;

import java.util.ArrayList;

import es.marser.backgroundtools.enums.ListExtra;


/**
 * @author sergio
 *         Created by Sergio on 30/04/2017.
 *         Controlador de selección para listas simples
 *         <p>
 *         [EN]  Simple List Controller
 * @see BaseSelectionControllerD
 * @see es.marser.backgroundtools.recyclerviews.simple.adapter.BaseBindAdapterList
 */

@SuppressWarnings("unused")
@Deprecated
public class SelectionControllerD<T> extends BaseSelectionControllerD<T> {
    /*Variable de arreglo de registros [EN]  Registry Array Variable*/
    protected ArrayList<T> arrayListController;

    public SelectionControllerD(ArrayList<T> arrayListController, ListExtra selectionmode) {
        super(selectionmode);
        this.arrayListController = arrayListController;
    }
}
