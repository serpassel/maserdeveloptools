package es.marser.backgroundtools.developtools;

import es.marser.tools.SystemColor;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 21/10/17.
 *         Herramienta para mapeo de pojos
 *         <p>
 *         [EN]  Tool for mapping pojos
 */

public class BuildPojo {

    private String tablename;
    private String className;
    private FieldBuilder[] list;
    private String build;

    /**
     * El:
     * <code>String tablename = "P";

     String nameclass = "Prueba";

     BuildPojo.FieldBuilder[] list = {
     BuildPojo.newfb("field1", String.class),
     BuildPojo.newfb("date1", Date.class),
     BuildPojo.newfb("num1", BigDecimal.class),
     BuildPojo.newfb("num2", int.class),
     BuildPojo.newfb("flag1", boolean.class),
     BuildPojo.newfb("obj1", Object.class)
     };

     BuildPojo buildPojo = new BuildPojo(tablename,nameclass,list);
     buildPojo.print(true);
     </code>
     * @param tablename Nombre de la tabla [EN]  Name of table
     * @param className Nombre de la clase a crear [EN]  Name of the class to create
     * @param list lista de campos [EN]  field list
     */
    public BuildPojo(String tablename, String className, FieldBuilder[] list) {
        this.tablename = tablename;
        this.className = className;
        this.list = list;
        this.build = "";
    }

    public static FieldBuilder newfb(String name, Class type) {
        FieldBuilder fieldBuilder = new FieldBuilder();
        fieldBuilder.name = name;
        fieldBuilder.type = type.getSimpleName();
        return fieldBuilder;
    }

    private void building(boolean bindable) {
        createimported(bindable);
//*********************************************************************************************************/
        createTableName();
//*********************************************************************************************************/
        openClass(bindable);
//*********************************************************************************************************/
        declareVariables();
//*********************************************************************************************************/
        createConstructor();
//*********************************************************************************************************/
        createSetterAndGetter(bindable);
//*********************************************************************************************************/
        createToString();
//*********************************************************************************************************/
        createParcelable();
//*********************************************************************************************************/
        close();
    }

    public void print(boolean bindable) {
        clear();

        building(bindable);

        if (bindable) {
            System.out.println(SystemColor.ANSI_CYAN + build);
        } else {
            System.out.println(SystemColor.ANSI_YELLOW + build);
        }
    }

    public void createimported(boolean bindable) {
        build += "import android.os.Parcel;\n" +
                "import android.os.Parcelable;\n" +
                "\n" +
                "import es.marser.annotation.DbColumn;\n" +
                "import es.marser.annotation.DbPrimaryKey;\n" +
                "import es.marser.annotation.DbTable;\n" +
                "import es.marser.tools.MathTools;\n" +
                "import es.marser.tools.BooleanTools;\n" +
                "import es.marser.tools.DateTools;\n" +
                "import es.marser.tools.TextTools;\n" +
                "import java.math.BigDecimal;\n" +
                "import java.util.Date;\n";


        if (bindable) {
            build += "import android.databinding.BaseObservable;\n" +
                    "import android.databinding.Bindable;\n";
        }
    }

    /**
     * Etiqueta de la tabla de datos
     * <p>
     * [EN]  Data table label
     */
    public void createTableName() {
        /*Nombre de la tabla*/
        build += "\n\n@SuppressWarnings(\"unused\")\n";
        build += "@DbTable(name = \"" + tablename + "\")";
    }

    /**
     * apertura de clase
     */
    public void openClass(boolean bindable) {
         /*Apertura de clase [EN]  Class opening*/
        build += "\npublic class " + className;
        if (bindable) {
            build += " extends BaseObservable";
        }
        build += " implements Parcelable {\n";
    }

    /**
     * Declarar variables
     * <p>
     * [EN]  Declare variables
     */
    public void declareVariables() {
/*Declaración de variables********************************************************************************************************************************************/
        /*Clave principal [EN]  Primary Key*/
        build += "\n@DbPrimaryKey\n" +
                "private String key;\n";

        build += "\n";
        /*Campos [EN]  Fields */
        for (int i = 0; i < list.length; ++i) {
            FieldBuilder f = list[i];
            build += "@DbColumn(col_name = \"" + f.name + "\", indexorder = " + (i + 1) + ")\n" +
                    "private " + f.type + " " + f.name + ";\n";
        }

        build += "\n";
    }

    /**
     * Crear constructor
     * [EN]  Create constructor
     */
    public void createConstructor() {
        /*Constructor [EN]  Constructor*****************************************************************************************************************************************/

        build += "public " + className + "() {\n";

        /*Inicio de variables [EN]  Variable start*/
        for (FieldBuilder f : list) {
            build += "this." + f.name + " = ";
            switch (f.type) {
                case "String":
                    build += "\"\"";
                    break;
                case "Date":
                    build += "new Date()";
                    break;
                case "long":
                case "Long":
                case "int":
                case "Integer":
                    build += "0";
                    break;
                case "double":
                case "Double":
                    build += "0.0";
                    break;
                case "float":
                case "Float":
                    build += "0.0f";
                    break;
                case "GregorianCalendar":
                    build += "new GregorianCalendar()";
                    break;
                case "BigDecimal":
                    build += "new BigDecimal(\"0.0\")";
                    break;
                case "Boolean":
                case "boolean":
                    build += "false";
                    break;
                default:
                    build += "null";
                    break;
            }
            build += ";\n";
        }
        build += "}";
    }

    public void createSetterAndGetter(boolean bindable) {
            /*Setter & Getter****************************************************************************************************************************************************/

        for (FieldBuilder f : list) {
            /*Setter*/
            build += "\n\npublic " + className + " set" + TextTools.capitalizeFirstChar(f.name) + "(" + f.type + " " + f.name + ") {\n" +
                    "this." + f.name + " = " + f.name + ";\n";
            if (bindable) {
                build += "notifyPropertyChanged(BR." + f.name + ");\n";
            }
            build += "return this;\n}";

            /*Getter*/
            if (bindable) {
                build += "\n\n@Bindable";
            }
            String action = "get";
            if (f.type.equals("Boolean") || f.type.equals("boolean")) {
                action = "is";
            }
            build += "\npublic " + f.type + " " + action + TextTools.capitalizeFirstChar(f.name) + "() {\n" +
                    "return this." + f.name + ";\n}";
        }
    }

    public void createToString() {
          /*toString**************************************************************************************************************************************************************/

        build += "\n\n@Override\n" +
                "public String toString() {\n" +
                " String builder = TextTools.REG_SEPARATOR +\n" +
                "\"" + tablename + "\" +\n";

        for (FieldBuilder f : list) {
            build += "TextTools.OBJECT_SEPARATOR_CHAR +\n" +

                    "TextTools.nc(" + f.name + ") +\n";
        }

        build += "TextTools.OBJECT_SEPARATOR_CHAR +\n" +
                "TextTools.RETORNO_CARRO_SALTO_LINEA;\n" +
                "return builder.replace(\"null\", \"\");\n}\n";
    }


    public void createParcelable() {
        /*descripcion de contenido*/
        build += "\n@Override\n" +
                "public int describeContents() {\n" +
                "return 0;\n}\n\n";

        /*Escritura en parcela*/
        build += "\n@Override\n" +
                "public void writeToParcel(Parcel dest, int flags) {\n";

        for (FieldBuilder f : list) {
            build += "dest.write";
            switch (f.type) {
                case "BigDecimal":
                case "String":
                    build += "String(TextTools.nc(" + f.name + "));\n";
                    break;
                case "long":
                case "Long":
                    build += "Long(MathTools.notNaN(" + f.name + "));\n";
                    break;
                case "int":
                case "Integer":
                    build += "Int(MathTools.notNaN(" + f.name + "));\n";
                    break;
                case "double":
                case "Double":
                    build += "Double(MathTools.notNaN(" + f.name + "));\n";
                    break;
                case "float":
                case "Float":
                    build += "Float(MathTools.notNaN(" + f.name + "));\n";
                    break;
                case "Boolean":
                case "boolean":
                    build += "Byte((byte) (BooleanTools.nc(" + f.name + ") ? 1 : 0));\n";
                    break;
                case "Date":
                    build += "Long(" + f.name + ".getTime());\n";
                    break;
                case "GregorianCalendar":
                    build += "Long(" + f.name + ".getTimeInMillis());\n";
                    break;
                default:
                    build += "null";
                    break;
            }
        }
        build += "\n}\n\n";

        /*Lectura de parcela*/
        build += "protected " + className + "(Parcel in) {\n";
        for (FieldBuilder f : list) {
            build += f.name + " = ";
            switch (f.type) {
                case "String":
                    build += "in.readString();\n";
                    break;
                case "BigDecimal":
                    build += "new BigDecimal(in.readString());\n";
                    break;
                case "long":
                case "Long":
                    build += "in.readLong();\n";
                    break;
                case "int":
                case "Integer":
                    build += "in.readInt();\n";
                    break;
                case "double":
                case "Double":
                    build += "in.readDouble();\n";
                    break;
                case "float":
                case "Float":
                    build += "in.readFloat();\n";
                    break;
                case "Boolean":
                case "boolean":
                    build += "in.readByte() != 0;\n";
                    build += "false";
                    break;
                case "Date":
                    build += "new Date();\n" +
                            f.name + ".setTime(in.readLong());\n";
                    break;
                case "GregorianCalendar":
                    build += "new GregorianCalendar();\n" +
                            f.name + ".setTimeInMillis(in.readLong());\n";
                    break;
                default:
                    build += "null";
                    break;
            }
        }
        build += "\n}\n\n";
        /*Creator*/

        build += "public static final Parcelable.Creator<" + className + "> CREATOR = new Parcelable.Creator<" + className + ">() {\n" +
                "@Override\n" +
                "public " + className + " createFromParcel(Parcel in) {\n" +
                "return new " + className + "(in);\n" +
                "}\n" +
                "@Override\n" +
                "public " + className + "[] newArray ( int size){\n" +
                "return new " + className + "[size];\n" +
                "}\n};\n";
    }

    private void close() {
        /*Cierre de clase  [EN]  Class closure***************************************************************************************************************************/
        build += "\n}";
    }

    public void clear() {
        build = "";
    }


    /**
     * in -> name,type,index
     */
    public static class FieldBuilder {
        public String name;
        public String type;
    }

}
