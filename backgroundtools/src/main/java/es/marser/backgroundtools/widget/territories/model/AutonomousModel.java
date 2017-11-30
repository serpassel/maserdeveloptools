package es.marser.backgroundtools.widget.territories.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by sergio on 4/11/17.
 *         Modelo de datos de definición de objeto de comunidad autónoma
 *         <p>
 *         [EN]  Autonomous community object definition data model
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
@DbTable(name = "CCAA")
public class AutonomousModel extends BaseObservable implements Selectable {

    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "codauto", indexorder = 1)
    private int codauto;
    @DbColumn(col_name = "name", indexorder = 2)
    private String name;
    @DbColumn(col_name = "provincesCount", indexorder = 3)
    private int provincesCount;

    public AutonomousModel() {
        this.codauto = 0;
        this.name = "";
        this.provincesCount = 0;
    }

    @Bindable
    public String getKey() {
        return key;
    }

    public AutonomousModel setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
        return this;
    }

    public AutonomousModel setCodauto(int codauto) {
        this.codauto = codauto;
        notifyPropertyChanged(BR.codauto);
        setKey(MathTools.formatCifra(codauto, 2));
        return this;
    }

    @Bindable
    public int getCodauto() {
        return this.codauto;
    }

    public AutonomousModel setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    public AutonomousModel setProvincesCount(int provincesCount) {
        this.provincesCount = provincesCount;
        notifyPropertyChanged(BR.provincesCount);
        return this;
    }

    @Bindable
    public int getProvincesCount() {
        return this.provincesCount;
    }

    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "CCAA" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(codauto) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(name) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(provincesCount) +
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
        dest.writeInt(MathTools.notNaN(codauto));
        dest.writeString(TextTools.nc(name));
        dest.writeInt(MathTools.notNaN(provincesCount));

    }

    protected AutonomousModel(Parcel in) {
        codauto = in.readInt();
        name = in.readString();
        provincesCount = in.readInt();

    }

    public static final Parcelable.Creator<AutonomousModel> CREATOR = new Parcelable.Creator<AutonomousModel>() {
        @Override
        public AutonomousModel createFromParcel(Parcel in) {
            return new AutonomousModel(in);
        }

        @Override
        public AutonomousModel[] newArray(int size) {
            return new AutonomousModel[size];
        }
    };

    @Override
    public SpannableString toSpannableString() {
        return new SpannableString(name);
    }

    @Override
    public String preSelectValue() {
        return MathTools.formatCifra(codauto, 2);
    }
}
