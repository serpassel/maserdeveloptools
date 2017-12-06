package es.marser.backgroundtools.widget.calendar.model;

import es.marser.backgroundtools.BR;

import android.os.Parcel;
import android.os.Parcelable;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;

import java.lang.String;

import es.marser.tools.TextTools;
import es.marser.tools.MathTools;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * @author sergio
 *         Created by sergio on 18-11-2017
 *         Objeto Modelo
 *         <p>
 *         [EN]  Object Model
 */
@SuppressWarnings("unused")
@DbTable(name = "AH")
public class HolidayModel extends BaseObservable implements Parcelable {

    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "codigo", indexorder = 1)
    private String codigo;
    @DbColumn(col_name = "lines", indexorder = 2)
    private String lines;
    @DbColumn(col_name = "type", indexorder = 3)
    private int type;

    public HolidayModel() {
        this.codigo = "";
        this.lines = "";
        this.type = 0;
    }

    public HolidayModel setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getKey() {
        return this.key;
    }

    public HolidayModel setCodigo(String codigo) {
        this.codigo = codigo;
        notifyPropertyChanged(BR.codigo);
        return this;
    }

    @Bindable
    public String getCodigo() {
        return this.codigo;
    }

    public HolidayModel setLines(String lines) {
        this.lines = lines;
        notifyPropertyChanged(BR.lines);
        return this;
    }

    @Bindable
    public String getLines() {
        return this.lines;
    }

    public HolidayModel setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
        return this;
    }

    @Bindable
    public int getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "AH" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(codigo) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(lines) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(type) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.RETORNO_CARRO_SALTO_LINEA;
        return builder.replace("null", "");
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TextTools.nc(codigo));
        dest.writeString(TextTools.nc(lines));
        dest.writeInt(MathTools.notNaN(type));

    }

    @SuppressWarnings("WeakerAccess")
    protected HolidayModel(Parcel in) {
        codigo = in.readString();
        lines = in.readString();
        type = in.readInt();

    }

    public static final Parcelable.Creator<HolidayModel> CREATOR = new Parcelable.Creator<HolidayModel>() {
        @Override
        public HolidayModel createFromParcel(Parcel in) {
            return new HolidayModel(in);
        }

        @Override
        public HolidayModel[] newArray(int size) {
            return new HolidayModel[size];
        }
    };

}