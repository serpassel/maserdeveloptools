package es.marser.backgroundtools.listables.complex.listeners;

@SuppressWarnings("unused")
public interface ExpandCollapseListener {

    /**
     *
     * Se llama cuando un grupo se expande
     * <p>
     * Called when a group is expanded
     *
     * @param positionStart la posición plana del primer anidado en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * the flat position of the first child in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * @param itemCount     el número total de anidados en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the total number of children in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     */
    void onGroupExpanded(int positionStart, int itemCount);

    /**
     *
     * Llamado cuando un grupo está colapsado
     * <p>
     * Called when a group is collapsed
     *
     * @param positionStart la posición plana del primer anidado en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the flat position of the first child in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     * @param itemCount     el número total de anidados en el {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     *                      the total number of children in the {@link es.marser.backgroundtools.listables.complex.models.ExpandableGroup}
     */
    void onGroupCollapsed(int positionStart, int itemCount);
}
