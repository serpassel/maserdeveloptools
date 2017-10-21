package es.marser.backgroundtools.developtools;

import android.os.Parcel;
import android.os.Parcelable;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.backgroundtools.BR;
import es.marser.tools.BooleanTools;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.math.BigDecimal;
import java.util.Date;


@SuppressWarnings("unused")
@DbTable(name = "P")
public class Prueba extends BaseObservable implements Parcelable {

    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "field1", indexorder = 1)
    private String field1;
    @DbColumn(col_name = "date1", indexorder = 2)
    private Date date1;
    @DbColumn(col_name = "num1", indexorder = 3)
    private BigDecimal num1;
    @DbColumn(col_name = "num2", indexorder = 4)
    private int num2;
    @DbColumn(col_name = "flag1", indexorder = 5)
    private boolean flag1;
    @DbColumn(col_name = "obj1", indexorder = 6)
    private Object obj1;

    public Prueba() {
        this.field1 = "";
        this.date1 = new Date();
        this.num1 = new BigDecimal("0.0");
        this.num2 = 0;
        this.flag1 = false;
        this.obj1 = null;
    }

    public Prueba setField1(String field1) {
        this.field1 = field1;
        notifyPropertyChanged(BR.field1);
        return this;
    }

    @Bindable
    public String getField1() {
        return this.field1;
    }

    public Prueba setDate1(Date date1) {
        this.date1 = date1;
        notifyPropertyChanged(BR.date1);
        return this;
    }

    @Bindable
    public Date getDate1() {
        return this.date1;
    }

    public Prueba setNum1(BigDecimal num1) {
        this.num1 = num1;
        notifyPropertyChanged(BR.num1);
        return this;
    }

    @Bindable
    public BigDecimal getNum1() {
        return this.num1;
    }

    public Prueba setNum2(int num2) {
        this.num2 = num2;
        notifyPropertyChanged(BR.num2);
        return this;
    }

    @Bindable
    public int getNum2() {
        return this.num2;
    }

    public Prueba setFlag1(boolean flag1) {
        this.flag1 = flag1;
        notifyPropertyChanged(BR.flag1);
        return this;
    }

    @Bindable
    public boolean isFlag1() {
        return this.flag1;
    }

    public Prueba setObj1(Object obj1) {
        this.obj1 = obj1;
        notifyPropertyChanged(BR.obj1);
        return this;
    }

    @Bindable
    public Object getObj1() {
        return this.obj1;
    }

    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "P" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                field1 +
                TextTools.OBJECT_SEPARATOR_CHAR +
                date1 +
                TextTools.OBJECT_SEPARATOR_CHAR +
                num1 +
                TextTools.OBJECT_SEPARATOR_CHAR +
                num2 +
                TextTools.OBJECT_SEPARATOR_CHAR +
                flag1 +
                TextTools.OBJECT_SEPARATOR_CHAR +
                obj1 +
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
        dest.writeString(TextTools.nc(field1));
        dest.writeLong(date1.getTime());
        dest.writeString(TextTools.nc(num1));
        dest.writeInt(MathTools.notNaN(num2));
        dest.writeByte((byte) (BooleanTools.nc(flag1) ? 1 : 0));
    }

    protected Prueba(Parcel in) {
        field1 = in.readString();
        date1 = new Date();
        date1.setTime(in.readLong());
        num1 = new BigDecimal(in.readString());
        num2 = in.readInt();
        flag1 = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Prueba> CREATOR = new Parcelable.Creator<Prueba>() {
        @Override
        public Prueba createFromParcel(Parcel in) {
            return new Prueba(in);
        }
        @Override
        public Prueba[] newArray ( int size){
            return new Prueba[size];
        }
    };

}
