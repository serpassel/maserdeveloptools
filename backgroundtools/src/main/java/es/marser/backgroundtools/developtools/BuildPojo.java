package es.marser.backgroundtools.developtools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

import es.marser.backgroundtools.systemtools.FilePathUtil;
import es.marser.tools.DateTools;
import es.marser.tools.SystemColor;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 21/10/17.
 *         Herramienta para mapeo de pojos
 *         <p>
 *         [EN]  Tool for mapping pojos
 */

@SuppressWarnings({"SameParameterValue", "StringConcatenationInLoop", "WeakerAccess", "CanBeFinal"})
public class BuildPojo {

    public static final int OBJECT = 0;
    public static final int ITEM = 1;

    private String tablename;
    private String className;
    private FieldBuilder[] list;
    private String build;

    /**
     * El:
     * <code>String tablename = "P";
     * <p>
     * String nameclass = "Prueba";
     * <p>
     * BuildPojo.FieldBuilder[] list = {
     * BuildPojo.newfb("field1", String.class),
     * BuildPojo.newfb("date1", Date.class),
     * BuildPojo.newfb("num1", BigDecimal.class),
     * BuildPojo.newfb("num2", int.class),
     * BuildPojo.newfb("flag1", boolean.class),
     * BuildPojo.newfb("obj1", Object.class)
     * };
     * <p>
     * BuildPojo buildPojo = new BuildPojo(tablename,nameclass,list);
     * buildPojo.print(true);
     * </code>
     *
     * @param tablename Nombre de la tabla [EN]  Name of table
     * @param className Nombre de la clase a crear [EN]  Name of the class to create
     * @param list      lista de campos [EN]  field list
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
        fieldBuilder.longType = type.getName();
        fieldBuilder.cls = type;
        return fieldBuilder;
    }

    private void building(boolean bindable, int type) {
        building(bindable, type, (tablename != null || type == ITEM));
    }

    private void building(boolean bindable, int type, boolean mappeable) {
        createimported(bindable, mappeable, type);
//*********************************************************************************************************/
        openHeader();

//*********************************************************************************************************/
        if (mappeable && type == OBJECT) {
            createTableName();
        }
//*********************************************************************************************************/
        openClass(bindable);
//*********************************************************************************************************/
        declareVariables(mappeable, type);
//*********************************************************************************************************/
        createConstructor();
//*********************************************************************************************************/
        createSettersAndGetters(bindable, mappeable);
//*********************************************************************************************************/
        if (mappeable) {
            createToString(type);
        }
//*********************************************************************************************************/
        createParcelable();
//*********************************************************************************************************/
        close();
    }

    public String print(boolean bindable) {
        return print(bindable, OBJECT);
    }

    public String print(boolean bindable, int type) {
        clear();

        building(bindable, type);

        if (bindable) {
            System.out.println(SystemColor.ANSI_CYAN + build);
        } else {
            System.out.println(SystemColor.ANSI_YELLOW + build);
        }

        return build;
    }

    public void createimported(boolean bindable, boolean mappeable, int type) {
        build += "import android.os.Parcel;\n" +
                "import android.os.Parcelable;\n" +
                "\n";

        if (mappeable) {
            build += "import es.marser.annotation.DbColumn;\n" +
                    "import es.marser.annotation.DbPrimaryKey;\n";
            if(type == OBJECT){
                build += "import es.marser.annotation.DbTable;\n";
            }
        }

        for (FieldBuilder f : list) {
            String text = "import " + f.longType + ";\n";

            if (!build.contains(text) && !f.type.equals(f.longType)) {
                build += text;
            }

            String text2;
            switch (f.type) {
                case "BigDecimal":
                case "String":
                    text2 = "import es.marser.tools.TextTools;\n";
                    if (!build.contains(text2)) {
                        build += text2;
                    }
                    break;
                case "long":
                case "Long":
                case "int":
                case "Integer":
                case "double":
                case "Double":
                case "float":
                case "Float":
                    text2 = "import es.marser.tools.MathTools;\n";
                    if (!build.contains(text2)) {
                        build += text2;
                    }
                    break;
                case "Boolean":
                case "boolean":
                    text2 = "import es.marser.tools.BooleanTools;\n";
                    if (!build.contains(text2)) {
                        build += text2;
                    }
                    break;
            }
        }
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
        build += "@DbTable(name = \"" + tablename + "\")";
    }


    /**
     * Cabecera de la clase JavaDoc
     */
    public void openHeader() {
        build += "/**\n" +
                " * @author sergio\n" +
                " *         Created by sergio on " + DateTools.formatShortDate(new GregorianCalendar()) + "\n" +
                " *         Objeto Modelo\n" +
                " *         <p>\n" +
                " *         [EN]  Object Model \n" +
                " */\n";

        build += "@SuppressWarnings(\"unused\")\n";
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
    public void declareVariables(boolean mappeable, int type) {
/*Declaración de variables********************************************************************************************************************************************/
        /*Clave principal [EN]  Primary Key*/
        if (mappeable && type == ITEM) {
            build += "\n@DbPrimaryKey\n" +
                    "private String key;\n";
        }

        build += "\n";
        /*Campos [EN]  Fields */
        for (int i = 0; i < list.length; ++i) {
            FieldBuilder f = list[i];
            if (mappeable) {
                build += "@DbColumn(col_name = \"" + f.name + "\", indexorder = " + (i + 1) + ")\n";
            }
            build += "private " + f.type + " " + f.name + ";\n";
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

    public void createSettersAndGetters(boolean bindable, boolean mappeable) {
        if (mappeable) {
        /*Key*/
            FieldBuilder fieldBuilder = new FieldBuilder();
            fieldBuilder.name = "key";
            fieldBuilder.type = "String";
            createSetterAndGetter(bindable, fieldBuilder);
        }

        /*Setter & Getter****************************************************************************************************************************************************/
        for (FieldBuilder f : list) {
            createSetterAndGetter(bindable, f);
        }
    }

    private void createSetterAndGetter(boolean bindable, FieldBuilder f) {
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


    public void createToString(int type) {
          /*toString**************************************************************************************************************************************************************/

        build += "\n\n@Override\n" +
                "public String toString() {\n" +
                " String builder = ";

        if (type == OBJECT) {

            build += "TextTools.REG_SEPARATOR +\n" +
                    "\"" + tablename + "\" +\n";
        }

        for (FieldBuilder f : list) {
            if (type == OBJECT) {
                build += "TextTools.OBJECT_SEPARATOR_CHAR +\n";
            } else {
                build += "TextTools.ITEM_SEPARATOR_CHAR +\n";
            }
            build += "TextTools.nc(" + f.name + ") +\n";
        }

        if (type == OBJECT) {
            build += "TextTools.OBJECT_SEPARATOR_CHAR +\n" +
                    "TextTools.RETORNO_CARRO_SALTO_LINEA;\n" +
                    "return builder.replace(\"null\", \"\");\n}\n";
        } else {
            build += "TextTools.ITEM_SEPARATOR_CHAR;\n" +
                    "return builder;\n}\n";
        }
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
                    build += "Parcelable(" + f.name + ", flags);\n";
                    break;
            }
        }
        build += "\n}\n\n";

        /*Lectura de parcela*/
        build += "@SuppressWarnings(\"WeakerAccess\")\n";
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
                    //build += "false";
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
                    build += "in.readParcelable(" + f.type + ".class.getClassLoader());\n";
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
     * @param classpath "/home/sergio/Dropbox/BusiDay/BusiDay/app"
     * @param text      Texto de creación
     * @param packagen2 es.marser.busiday
     * @param path      "async"
     * @param name      BuildPojo
     */
    @SuppressWarnings("unused")
    public String writeClass(String classpathi, String packagen2, String text, String path, String name) {
        String classpath = classpathi + "/src/main/java/" + packagen2.replace(".", "/");
        String filePath = classpath + "/" + path + "/" + name + ".java";
        String packagen = "package " + packagen2 + "." + path.replace("/", ".") + ";\n\n";
        String importBR = "import " + packagen2 + ".BR;\n";

        File file = FilePathUtil.AutoRenameFile(new File(filePath));

        FileWriter fichero = null;
        PrintWriter pw;
        try {
            fichero = new FileWriter(filePath);
            pw = new PrintWriter(fichero);

            pw.print(packagen);
            pw.print(importBR);

            pw.print(text);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return filePath;
    }

    /**
     * in -> name,type,index
     */
    public static class FieldBuilder {
        public String name;
        public String type;
        public String longType;
        @SuppressWarnings("unused")
        public Class cls;
    }

}
