package es.marser.backgroundtools.containers.fragments;

import es.marser.backgroundtools.containers.fragments.base.BaseFragmentList;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.headerbody.adapter.HeadBodyListAdapter;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base de construcción de fragment con lista de multiples vistas cabezera y cuerpo
 *         <p>
 *         [EN]  Fragment construction base with list of multiple head and body views
 */

@SuppressWarnings({"unused", "SameReturnValue"})
public abstract  class BaseFragmentHeadBodyBinList<H,B> extends BaseFragmentList {

    protected HeadBodyListAdapter<H,B> adapter;

    public abstract  int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return null;
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return null;
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return null;
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return null;
    }

    @Override
    protected void bindAdapter() {
        adapter = new HeadBodyListAdapter<H, B>() {
            @Override
            public int getHeadHolderLayout() {
                return BaseFragmentHeadBodyBinList.this.getHeadHolderLayout();
            }

            @Override
            public int getBodyHolderLayout() {
                return BaseFragmentHeadBodyBinList.this.getBodyHolderLayout();
            }

            @Override
            public ViewItemHandler<H> getHeadItemHandler() {
                return BaseFragmentHeadBodyBinList.this.getHeadItemHandler();
            }

            @Override
            public ViewItemHandler<B> getBodyItemHandler() {
                return BaseFragmentHeadBodyBinList.this.getBodyItemHandler();
            }

            @Override
            public TouchableViewHandler<H> getHeadTouchableViewHandler() {
                return BaseFragmentHeadBodyBinList.this.getHeadTouchableViewHandler();
            }

            @Override
            public TouchableViewHandler<B> getBodyTouchableViewHandler() {
                return BaseFragmentHeadBodyBinList.this.getBodyTouchableViewHandler();
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.hGlobalController.selectionController.setSelectionMode(getInitialSelectionMode());
        adapter.bGlobalController.selectionController.setSelectionMode(getInitialSelectionMode());
    }

    @Override
    protected int getFragmentLayout() {
        return 0;
    }

}
