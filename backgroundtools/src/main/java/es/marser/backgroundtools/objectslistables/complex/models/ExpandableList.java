package es.marser.backgroundtools.objectslistables.complex.models;

import android.os.Parcelable;
import android.util.SparseBooleanArray;

import java.util.List;

/**
 * @author sergio
 *         <p>
 *         Clase identificativa de posiciones de grupo y listas anidadas
 *         <p>
 *         [EN]  Class identifying group positions and nested lists
 *         <ul>
 *         <il>Posiciones globales</il>
 *         <il>Posiciones de los grupos</il>
 *         </ul>
 *         Terminología:
 *         <li> posición plana - posición de la lista plana, la posición de un elemento relativo a todas lasotros elementos
 *         visibles en la pantalla. Por ejemplo, si tiene tres grupos, cada uno con
 *         2 niños y todos están colapsados, la "posición plana" del último grupo sería 2.
 *         Y si el primero de esos tres grupos se expandió, la posición plana del último grupo sería ahora 4.
 *         <p>
 *         Esta clase actúa como un traductor entre la posición de la lista plana, es decir, qué grupos
 *         y los niños que ve en la pantalla: hacia y desde la lista completa de grupos y sus hijos
 *         <p>
 *         <p>
 *         [EN]
 *         <ul>
 *         <il>Global Positions</il>
 *         <il>Group Positions</il>
 *         </ul>
 *         Terminology:
 *         <li> flat position - Flat list position, the position of an item relative to all the
 *         other *visible* items on the screen. For example, if you have a three groups, each with
 *         2 children and all are collapsed, the "flat position" of the last group would be 2. And if
 *         the first of those three groups was expanded, the flat position of the last group would now be 4.
 *         <p>
 *         <p>
 *         This class acts as a translator between the flat list position - i.e. what groups
 *         and children you see on the screen - to and from the full backing list of groups & their children
 * @see ExpandableGroup
 */
@SuppressWarnings("unused")
public class ExpandableList<X extends ExpandableGroup<T>, T extends Parcelable> {

    /*Variable listado de grupos [EN]  Variable list of groups*/
    public List<X> groups;
    /*Variable de estado de expansión de los grupos [EN]  Expansion state variable for groups*/
    public SparseBooleanArray expandedGroupIndexes;


    //CONSTRUCTORS________________________________________________________________________________
    public ExpandableList(List<X> groups) {
        this.groups = groups;
        expandedGroupIndexes = new SparseBooleanArray();
    }

    //GLOBAL POSITIONS________________________________________________________________________________

    /**
     * Limpia el estado de expansión de los grupos
     * <p>
     * [EN]  Clears the group expansion status
     */
    public void clear() {
        expandedGroupIndexes.clear();
    }

    /**
     * Devuelve la cantidad de elementos de fila visibles para el grupo en particular. Si el grupo está colapsado,
     * devuelve 1 para el encabezado del grupo. Si el grupo se expande, regrese la cantidad total de niños en el
     * grupo + 1 para el encabezado del grupo
     * <p>
     * [EN] the number of visible row items for the particular group. If the group is collapsed,
     * return 1 for the group header. If the group is expanded return total number of children in the
     * group + 1 for the group header
     *
     * @param group el índice de {@link ExpandableGroup} en la colección completa {@link #groups}
     *              [EN] the index of the {@link ExpandableGroup} in the full collection {@link #groups}
     * @return cantidad total de elementos anidados + el grupo [EN]  total number of nested elements the group
     */
    private int numberOfVisibleItemsInGroup(int group) {

        if (expandedGroupIndexes.get(group)) {
            return groups.get(group).getItemCount() + 1;
        } else {
            return 1;
        }
    }

    /**
     * Número total de elementos visibles, expandidos más los grupos
     * <p>
     * [EN]  Total number of visible, expanded elements plus groups
     *
     * @return el número total de filas visibles [EN ]the total number visible rows
     */
    public int getVisibleItemCount() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            count += numberOfVisibleItemsInGroup(i);
        }
        return count;
    }

    /**
     * Traduce una posición de lista plana (la posición sin procesar de un elemento (niño o grupo) en la lista) a
     * bien
     * a) grupo pos si la posición especificada de la lista plana corresponde a un grupo, o
     * b) niño
     * pos si corresponde a un niño. Realiza una búsqueda binaria en la expansión
     * lista de grupos para encontrar la lista plana pos si es un grupo exp, de lo contrario
     * encuentra donde la lista plana pos encaja entre los grupos de exp.
     * <p>
     * Translates a flat list position (the raw position of an item (child or group) in the list) to
     * either a) group pos if the specified flat list position corresponds to a group, or b) child
     * pos if it corresponds to a child.  Performs a binary search on the expanded
     * groups list to find the flat list pos if it is an exp group, otherwise
     * finds where the flat list pos fits in between the exp groups.
     *
     * @param flPos la posición de la lista plana a traducir [EN] the flat list position to be translated
     * @return la posición del grupo o la posición del niño de la lista plana especificada
     * posición comprendida en un objeto {@link ExpandableListPosition}
     * que contiene información útil adicional para inserción, etc.
     * [EN]the group position or child position of the specified flat list
     * position encompassed in a {@link ExpandableListPosition} object
     * that contains additional useful info for insertion, etc.
     */
    public ExpandableListPosition getUnflattenedPosition(int flPos) {
        int groupItemCount;
        int adapted = flPos;

        for (int i = 0; i < groups.size(); i++) {
            groupItemCount = numberOfVisibleItemsInGroup(i);
            if (adapted == 0) {
                return ExpandableListPosition.obtain(ExpandableListPosition.GROUP, i, -1, flPos);
            } else if (adapted < groupItemCount) {
                return ExpandableListPosition.obtain(ExpandableListPosition.CHILD, i, adapted - 1, flPos);
            }
            adapted -= groupItemCount;
        }
        throw new RuntimeException("Unknown state");
    }

    //GROUP POSITIONS________________________________________________________________________________________

    /**
     * Índice del grupo dentro de una lista completa
     * <p>
     * [EN]  Index of the group within a complete list
     *
     * @param listPosition representando un niño o un grupo [EN] representing either a child or a group
     * @return el índice de un grupo dentro de {@link #getVisibleItemCount ()}
     * [EN]the index of a group within the {@link #getVisibleItemCount()}
     */
    public int getFlattenedGroupIndex(ExpandableListPosition listPosition) {
        int groupIndex = listPosition.groupPos;
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal;
    }

    /**
     * Devuelve el índice del grupos dentro del número total de elementos visibles
     * <p>
     * [EN]  Returns the index of the groups within the total number of visible elements
     *
     * @param groupIndex representa el índice de un grupo dentro de  {@link #groups}
     *                   [EN] representing the index of a group within  {@link #groups}
     * @return el índice de un grupo dentro del {@link #getVisibleItemCount()}
     * [EN]the index of a group within the {@link #getVisibleItemCount()}
     */
    public int getFlattenedGroupIndex(int groupIndex) {
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal;
    }

    /**
     * Devuelve el índice de un grupo dentro del total de vistas disponibles
     * <p>
     * [EN]  Returns the index of a group within the total of available views
     *
     * @param group un {@link ExpandableGroup} dentro de {@link #groups}
     *              an {@link ExpandableGroup} within {@link #groups}
     * @return el índice de un grupo dentro del {@link #getVisibleItemCount()} o 0 si los groups.indexOf no pueden encontrar el grupo
     * [EN] the index of a group within the {@link #getVisibleItemCount()} or 0 if the groups.indexOf cannot find the group
     */
    public int getFlattenedGroupIndex(X group) {
        int groupIndex = group != null ? groups.indexOf(group) : -1;
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal;
    }

    /**
     * Convierte una posición infantil a una posición de lista plana.
     * <p>
     * [EN] Converts a child position to a flat list position.
     *
     * @param packedPosition Las posiciones del niño que se convertirán en su representación de posición empaquetada.
     *                       [EN] The child positions to be converted in it's packed position representation.
     * @return La posición de lista plana para el niño dado [EN]The flat list position for the given child
     */
    public int getFlattenedChildIndex(long packedPosition) {
        ExpandableListPosition listPosition = ExpandableListPosition.obtainPosition(packedPosition);
        return getFlattenedChildIndex(listPosition);
    }

    /**
     * Convierte una posición infantil a una posición de lista plana.
     * <p>
     * [EN]Converts a child position to a flat list position.
     *
     * @param listPosition Las posiciones de los niños para convertirse en su  {@link ExpandableListPosition} representación.
     *                     [EN] The child positions to be converted in it's {@link ExpandableListPosition} representation.
     * @return La posición de lista plana para el niño dado [EN] The flat list position for the given child
     */
    public int getFlattenedChildIndex(ExpandableListPosition listPosition) {
        int groupIndex = listPosition.groupPos;
        int childIndex = listPosition.childPos;
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal + childIndex + 1;
    }

    /**
     * Convierte los detalles de la posición de un niño a una posición de lista plana.
     * <p>
     * [EN] Converts the details of a child's position to a flat list position.
     *
     * @param groupIndex El índice de un grupo dentro de {@link #groups}
     *                   [EN] The index of a group within {@link #groups}
     * @param childIndex el índice de un niño dentro de su {@link ExpandableGroup}
     *                   [EN] the index of a child within it's {@link ExpandableGroup}
     * @return La posición de lista plana para el niño dado
     * [EN] The flat list position for the given child
     */
    public int getFlattenedChildIndex(int groupIndex, int childIndex) {
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal + childIndex + 1;
    }

    /**
     * Devuelve la posición de la lista plana para el primer hijo en un grupo
     * <p>
     * [EN]  Returns the position of the flat list for the first child in a group
     *
     * @param groupIndex El índice de un grupo dentro  {@link #groups}
     *                   [EN]The index of a group within {@link #groups}
     * @return La posición de la lista plana para el primer hijo en un grupo
     * [EN]The flat list position for the first child in a group
     */
    public int getFlattenedFirstChildIndex(int groupIndex) {
        return getFlattenedGroupIndex(groupIndex) + 1;
    }

    /**
     * Devuelve la posición de la lista plana para el primer hijo en un grupo
     * <p>
     * [EN]  Returns the position of the flat list for the first child in a group
     *
     * @param listPosition Las posiciones de los niños para convertirse en su {@link ExpandableListPosition} representación
     *                     [EN]The child positions to be converted in it's {@link ExpandableListPosition} representation.
     * @return La posición de la lista plana para el primer hijo en un grupo
     * [EN] The flat list position for the first child in a group
     */
    public int getFlattenedFirstChildIndex(ExpandableListPosition listPosition) {
        return getFlattenedGroupIndex(listPosition) + 1;
    }

    /**
     * Devuelve el número de total de elementos anidados para una posición
     * <p>
     * [EN]  Returns the total number of nested elements for a position
     *
     * @param listPosition Un {@link ExpandableListPosition} representando un niño o grupo
     *                     [EN] An {@link ExpandableListPosition} representing either a child or group
     * @return el número total de niños dentro del grupo asociado con @param listPosition
     * [EN ]the total number of children within the group associated with the @param listPosition
     */
    public int getExpandableGroupItemCount(ExpandableListPosition listPosition) {
        return groups.get(listPosition.groupPos).getItemCount();
    }

    /**
     * Traduce un grupo pos o un niño pos a un{@link ExpandableGroup}.
     * Si el {@link ExpandableListPosition} es una posición de niño, devuelve el {@link ExpandableGroup} perteneciente a
     * <p>
     * [EN] Translates either a group pos or a child pos to an {@link ExpandableGroup}.
     * If the {@link ExpandableListPosition} is a child position, it returns the {@link
     * ExpandableGroup} it belongs to
     *
     * @param listPosition en {@link ExpandableListPosition} representando ya sea una posición de grupo
     *                     o posición anidada
     *                     a {@link ExpandableListPosition} representing either a group position
     *                     or child position
     * @return el {@link ExpandableGroup} objeto que contiene la listaPosición
     *                [EN] the {@link ExpandableGroup} object that contains the listPosition
     */
    public X getExpandableGroup(ExpandableListPosition listPosition) {
        return groups.get(listPosition.groupPos);
    }
}
