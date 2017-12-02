package es.marser.backgroundtools.listables.complex.models;

import android.widget.ExpandableListView;

import java.util.ArrayList;

import es.marser.backgroundtools.listables.base.holder.ViewHolderType;

/**
 * Copia exacta de android.widget.ExpandableListPosition porque
 * android.widget.ExpandableListPosition tiene alcance local del paquete
 * <p>
 * <p>
 * ExpandableListPosition puede referirse a una posición de grupos o a un niño
 * posición.  Hacer referencia a la posición de un niño requiere tanto una posición de grupo (el
 * grupo que contiene al niño) y una posición infantil (la posición del niño dentro de
 * ese grupo).  Para crear objetos, use{@link #obtainChildPosition(int, int)} o
 * {@link #obtainGroupPosition(int)}.
 * android.widget.ExpandableListPosition tiene alcance local del paquete
 * <p>
 * <p>
 * [EN]
 * Exact copy of android.widget.ExpandableListPosition because
 * android.widget.ExpandableListPosition has package local scope
 * <p>
 * <p>
 * ExpandableListPosition can refer to either a group's position or a child's
 * position. Referring to a child's position requires both a group position (the
 * group containing the child) and a child position (the child's position within
 * that group). To create objects, use {@link #obtainChildPosition(int, int)} or
 * {@link #obtainGroupPosition(int)}.
 */

@SuppressWarnings("unused")
public class ExpandableListPosition {

    private static final int MAX_POOL_SIZE = 5;
    private static final ArrayList<ExpandableListPosition> sPool =
            new ArrayList<>(MAX_POOL_SIZE);

    /**
     * La posición del grupo al que se hace referencia o al padre
     * grupo del niño al que se refiere
     * <p>
     * [EN]The position of either the group being referred to, or the parent
     * group of the child being referred to
     */
    public int groupPos;

    /**
     * La posición del niño dentro de su grupo principal
     * <p>
     * [EN] The position of the child within its parent group
     */
    public int childPos;

    /**
     * La posición del elemento en la lista plana (opcional, se usa internamente cuando
     * se conoce la posición de la lista plana correspondiente para el grupo o el niño)
     * <p>
     * [EN] The position of the item in the flat list (optional, used internally when
     * the corresponding flat list position for the group or child is known)
     */
    int flatListPos;

    /**
     * Tipo de posición representa esta ExpandableListPosition
     * <p>
     * [EN] What type of position this ExpandableListPosition represents
     */
    public int type;

    private void resetState() {
        groupPos = 0;
        childPos = 0;
        flatListPos = 0;
        type = 0;
    }

    private ExpandableListPosition() {
    }

    public long getPackedPosition() {
        if (type == ViewHolderType.CHILD.ordinal()) {
            return ExpandableListView.getPackedPositionForChild(groupPos, childPos);
        } else {
            return ExpandableListView.getPackedPositionForGroup(groupPos);
        }
    }

    static ExpandableListPosition obtainGroupPosition(int groupPosition) {
        return obtain(ViewHolderType.GROUP.ordinal(), groupPosition, 0, 0);
    }

    static ExpandableListPosition obtainChildPosition(int groupPosition, int childPosition) {
        return obtain(ViewHolderType.CHILD.ordinal(), groupPosition, childPosition, 0);
    }

    static ExpandableListPosition obtainPosition(long packedPosition) {
        if (packedPosition == ExpandableListView.PACKED_POSITION_VALUE_NULL) {
            return null;
        }

        ExpandableListPosition elp = getRecycledOrCreate();
        elp.groupPos = ExpandableListView.getPackedPositionGroup(packedPosition);
        if (ExpandableListView.getPackedPositionType(packedPosition) ==
                ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            elp.type = ViewHolderType.CHILD.ordinal();
            elp.childPos = ExpandableListView.getPackedPositionChild(packedPosition);
        } else {
            elp.type = ViewHolderType.GROUP.ordinal();
        }
        return elp;
    }

    public static ExpandableListPosition obtain(int type, int groupPos, int childPos,
                                                int flatListPos) {
        ExpandableListPosition elp = getRecycledOrCreate();
        elp.type = type;
        elp.groupPos = groupPos;
        elp.childPos = childPos;
        elp.flatListPos = flatListPos;
        return elp;
    }

    private static ExpandableListPosition getRecycledOrCreate() {
        ExpandableListPosition elp;
        synchronized (sPool) {
            if (sPool.size() > 0) {
                elp = sPool.remove(0);
            } else {
                return new ExpandableListPosition();
            }
        }
        elp.resetState();
        return elp;
    }

    /**
     * No llame a esto a menos que haya obtenido esto a través de ExpandableListPosition.obtain().
     * PositionMetadata se encargará de reciclar sus propios hijos.
     * <p>
     * [EN] Do not call this unless you obtained this via ExpandableListPosition.obtain().
     * PositionMetadata will handle recycling its own children.
     */
    public void recycle() {
        synchronized (sPool) {
            if (sPool.size() < MAX_POOL_SIZE) {
                sPool.add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpandableListPosition that = (ExpandableListPosition) o;

        return groupPos == that.groupPos && childPos == that.childPos && flatListPos == that.flatListPos && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = groupPos;
        result = 31 * result + childPos;
        result = 31 * result + flatListPos;
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "ExpandableListPosition{" +
                "groupPos=" + groupPos +
                ", childPos=" + childPos +
                ", flatListPos=" + flatListPos +
                ", type=" + type +
                '}';
    }
}

