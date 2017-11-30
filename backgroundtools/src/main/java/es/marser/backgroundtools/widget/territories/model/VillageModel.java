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
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 4/11/17.
 *         Definición de modelo de datos para municipios
 *         <p>
 *         [EN]  Definition of data model for municipalities
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@DbTable(name = "MUN")
public class VillageModel extends BaseObservable implements Selectable {

    @SuppressWarnings("FieldCanBeLocal")
    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "codauto", indexorder = 1)
    private String codauto;
    @DbColumn(col_name = "cpro", indexorder = 2)
    private String cpro;
    @DbColumn(col_name = "cmun", indexorder = 3)
    private String cmun;
    @DbColumn(col_name = "dc", indexorder = 4)
    private String dc;
    @DbColumn(col_name = "name", indexorder = 5)
    private String name;

    public VillageModel() {
        this.codauto = "";
        this.cpro = "";
        this.cmun = "";
        this.dc = "";
        this.name = "";
    }

    public VillageModel setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getKey() {
        return cpro + cmun + dc;
    }

    public VillageModel setCodauto(String codauto) {
        this.codauto = codauto;
        notifyPropertyChanged(BR.codauto);
        return this;
    }

    @Bindable
    public String getCodauto() {
        return this.codauto;
    }

    public VillageModel setCpro(String cpro) {
        this.cpro = cpro;
        notifyPropertyChanged(BR.cpro);
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getCpro() {
        return this.cpro;
    }

    public VillageModel setCmun(String cmun) {
        this.cmun = cmun;
        notifyPropertyChanged(BR.cmun);
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getCmun() {
        return this.cmun;
    }

    public VillageModel setDc(String dc) {
        this.dc = dc;
        notifyPropertyChanged(BR.dc);
        notifyPropertyChanged(BR.key);
        return this;
    }

    @Bindable
    public String getDc() {
        return this.dc;
    }

    public VillageModel setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        return this;
    }

    @Bindable
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "MUN" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(codauto) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(cpro) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(cmun) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(dc) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(name) +
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
        dest.writeString(TextTools.nc(codauto));
        dest.writeString(TextTools.nc(cpro));
        dest.writeString(TextTools.nc(cmun));
        dest.writeString(TextTools.nc(dc));
        dest.writeString(TextTools.nc(name));

    }

    protected VillageModel(Parcel in) {
        codauto = in.readString();
        cpro = in.readString();
        cmun = in.readString();
        dc = in.readString();
        name = in.readString();

    }

    public static final Parcelable.Creator<VillageModel> CREATOR = new Parcelable.Creator<VillageModel>() {
        @Override
        public VillageModel createFromParcel(Parcel in) {
            return new VillageModel(in);
        }

        @Override
        public VillageModel[] newArray(int size) {
            return new VillageModel[size];
        }
    };

    @Override
    public SpannableString toSpannableString() {
        return new SpannableString(name);
    }

    @Override
    public String preSelectValue() {
        return cpro + cmun + dc;
    }
}
