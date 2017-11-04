package es.marser.backgroundtools.territories;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.backgroundtools.BR;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by sergio on 4/11/17.
 *         Modelo de datos de provincia
 *         <p>
 *         [EN]  Provincial data model
 *
 *           // 'PRO' | CODAUTO | CPRO | NOMBRE| MUN_COUNT |
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
@DbTable(name = "PRO")
public class ProvincieModel extends BaseObservable implements Parcelable {

    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "codauto", indexorder = 1)
    private int codauto;
    @DbColumn(col_name = "cpro", indexorder = 2)
    private int cpro;
    @DbColumn(col_name = "name", indexorder = 3)
    private String name;
    @DbColumn(col_name = "villagesCount", indexorder = 4)
    private int villagesCount;

    public ProvincieModel() {
        this.codauto = 0;
        this.cpro = 0;
        this.name = "";
        this.villagesCount = 0;
    }

    public ProvincieModel setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getKey() {
        return this.key;
    }

    public ProvincieModel setCodauto(int codauto) {
        this.codauto = codauto;
        notifyPropertyChanged(BR.codauto);
        return this;
    }

    @Bindable
    public int getCodauto() {
        return this.codauto;
    }

    public ProvincieModel setCpro(int cpro) {
        this.cpro = cpro;
        notifyPropertyChanged(BR.cpro);
        setKey(MathTools.formatCifra(cpro,2));
        return this;
    }

    @Bindable
    public int getCpro() {
        return this.cpro;
    }

    public ProvincieModel setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    public ProvincieModel setVillagesCount(int villagesCount) {
        this.villagesCount = villagesCount;
        notifyPropertyChanged(BR.villagesCount);
        return this;
    }

    @Bindable
    public int getVillagesCount() {
        return this.villagesCount;
    }

    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "PRO" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(codauto) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(cpro) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(name) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(villagesCount) +
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
        dest.writeInt(MathTools.notNaN(cpro));
        dest.writeString(TextTools.nc(name));
        dest.writeInt(MathTools.notNaN(villagesCount));

    }

    protected ProvincieModel(Parcel in) {
        codauto = in.readInt();
        cpro = in.readInt();
        name = in.readString();
        villagesCount = in.readInt();

    }

    public static final Parcelable.Creator<ProvincieModel> CREATOR = new Parcelable.Creator<ProvincieModel>() {
        @Override
        public ProvincieModel createFromParcel(Parcel in) {
            return new ProvincieModel(in);
        }
        @Override
        public ProvincieModel[] newArray ( int size){
            return new ProvincieModel[size];
        }
    };

}