package es.marser.backgroundtools.listables.complex.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergio
 *         <p>
 *         Objeto de expandible
 *         <p>
 *         [EN]  Expandable
 * @see es.marser.backgroundtools.listables.complex.models.ListableItems
 */
@SuppressWarnings("unused")
public class ExpandableGroup<T extends Parcelable> extends ListableItems<T> implements Parcelable {
    /*Variable de titulo para listas genéricas*/
    protected String title;
    /*Variable de control de selección de elementos anidados
    [EN]  Selection control variable for nested elements*/
    protected SparseBooleanArray selectedChildren;

    //CONSTRUCTORS________________________________________________________________________________
    public ExpandableGroup() {
        this("", null);
    }

    public ExpandableGroup(String title, List<T> items) {
        super(items);
        this.title = title;
        selectedChildren = new SparseBooleanArray();
    }
    //ACCESS TO NESTED ELEMENT SELECTION STATUS___________________________________________________

    /**
     * Recuperara la posición del primer elemento seleccionado
     * <p>
     * [EN]  Retrieve the position of the first selected item
     *
     * @return Valor de la posición del primer elemento seleccionado o -1 si no hay selección
     * [EN]  Value of the position of the first selected item or -1 if there is no selection
     */
    public int getSingleSelected() {
        for (int i = 0; i < selectedChildren.size(); ++i) {
            if (selectedChildren.get(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Devuelve la lista de posiciones de los elementos seleccionados
     * <p>
     * [EN]  Returns the list of positions of the selected items
     *
     * @return Lista de posiciones seleccionadas [EN]  List of selected positions
     */
    public List<Integer> getSelectedPositions() {
        List<Integer> selected = new ArrayList<>();
        /*Agregar las posiciones seleccionadas a la lista de resultado
        [EN]  Add the selected positions to the result list*/
        for (int i = 0; i < selectedChildren.size(); ++i) {
            if (selectedChildren.get(i)) {
                selected.add(i);
            }
        }
        return selected;
    }

    /**
     * Comprobar si hay elementos seleccionados
     * <p>
     * [EN]  Check for selected items
     *
     * @return verdadero si hay algún elemento seleccionado [EN]  true if there is a selected item
     */
    public boolean haveSelected() {
        return (getSingleSelected() > -1);
    }

    /**
     * Determina si el elemento de una posición está seleccionado
     * <p>
     * [EN]  Determines whether the item in a position is selected
     *
     * @param position posición del elemento [EN]  position of the element
     * @return verdadero si está seleccionado [EN]  true if selected
     */
    public boolean isChildSelected(int position) {
        /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        try {
            return (position > -1 && position < items.size()) && selectedChildren.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cambiar el estado de selección de una posición de un elemento anidado
     * <p>
     * [EN]  Change the selection state of a nested item's position
     *
     * @param position posición del elemento [EN]  position of the element
     * @param selected Valor de estado de selección [EN]  Selection status value
     */
    public void setSelected(int position, boolean selected) {
        /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((position > -1 && position < items.size())) {
            selectedChildren.put(position, selected);
        }
    }

    /**
     * Eliminar el resgitro de selecciones
     * <p>
     * [EN]  Delete the selection repository
     */
    public void clearSelections() {
        if (selectedChildren != null) {
            selectedChildren.clear();
        }
    }

    /**
     * Eliminar los elementos anidados seleccionados
     * <p>
     * [EN]  Delete Selected Nested Items
     */
    public void deleteSelected() {
       /*Eliminar elementos seleccionados [EN]  Delete Selected Items*/
        deleteItems(getSelectedPositions());
       /*Limpiar selección [EN]  Clear Selection*/
        clearSelections();
    }

    //CHAIN ​​CONVERSION_______________________________________________________________________________________
    @Override
    public String toString() {
        return "ExpandableGroup{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }

    //PARCEL IMPLEMENTS______________________________________________________________________________________
    protected ExpandableGroup(Parcel in) {
        title = in.readString();
        selectedChildren = in.readSparseBooleanArray();
        byte hasItems = in.readByte();
        int size = in.readInt();
        if (hasItems == 0x01) {
            items = new ArrayList<>(size);
            if (items.size() > 0) {
                Class<?> type = (Class<?>) in.readSerializable();
                in.readList(items, type.getClassLoader());
            }
        } else {
            items = new ArrayList<>();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeSparseBooleanArray(selectedChildren);
        if (items == null) {
            dest.writeByte((byte) (0x00));
            dest.writeInt(0);
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(items.size());
            if (!items.isEmpty()) {
                final Class<?> objectsType = items.get(0).getClass();
                dest.writeSerializable(objectsType);
            }
            dest.writeList(items);

        }
    }

    @SuppressWarnings("unused")
    public static final Creator<ExpandableGroup> CREATOR =
            new Creator<ExpandableGroup>() {
                @Override
                public ExpandableGroup createFromParcel(Parcel in) {
                    return new ExpandableGroup(in);
                }

                @Override
                public ExpandableGroup[] newArray(int size) {
                    return new ExpandableGroup[size];
                }
            };
}
