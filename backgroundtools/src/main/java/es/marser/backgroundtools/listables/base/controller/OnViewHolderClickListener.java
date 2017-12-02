package es.marser.backgroundtools.listables.base.controller;

import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Oyente de pulsaciones de sobre una vista del tipo ViewHolder
 *         <p>
 *         [EN]  Listener of pulsations on a view of the ViewHolder type
 *         {@link es.marser.backgroundtools.listables.simple.holder.ViewHolderBinding}
 */

public interface OnViewHolderClickListener<T> {

    /**
     * Pulsación sencilla sobre un elemento
     * <p>
     * [EN]  Single click on an element
     *
     * @param view     vista pulsada [EN]  pulsed view
     * @param position posición de la vista en el adapter [EN]  position of the view on the adapter
     */
    void onClick(BaseViewHolder<T> holder, int position);

    /**
     * Pulsación prolongada sobre un elemento
     * <p>
     * [EN]  Long press on an element
     *
     * @param view     vista pulsada [EN]  pulsed view
     * @param position posición de la vista en el adapter [EN]  position of the view on the adapter
     */
    @SuppressWarnings("SameReturnValue")
    boolean onLongClick(BaseViewHolder<T> view, int position);
}
