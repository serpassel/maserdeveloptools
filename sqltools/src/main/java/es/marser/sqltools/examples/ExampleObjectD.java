package es.marser.sqltools.examples;

import java.math.BigDecimal;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 02/03/2017.
 *         <p>
 *         <p>
 *         En este registro figuran las mediciones (cantidades), en que interviene un concepto de un
 *         presupuesto en la descomposición de otro de mayor rango.
 *         En el intercambio de archivos de presupuestos,
 *         deberá figurar siempre este registro, exista o no desglose de mediciones.
 *         Cuando se trate de intercambiar una relación de registros ~M
 *         que recogen un listado de mediciones no estructurado, no es necesario la disposición de un
 *         CODIGO raíz ni los registros ~D complementarios.
 *         El operador indicará en estos casos cual es el destino de la medición.
 *         <p>
 *         ~M
 *         1| [ CODIGO_PADRE \ ] CODIGO_HIJO
 *         2| { POSICION \ }
 *         3| MEDICION_TOTAL
 *         4| { TIPO \ COMENTARIO { # ID_BIM } \ UNIDADES \ LONGITUD \ LATITUD \ ALTURA \ }
 *         5| [ ETIQUETA ] |
 *         CODIGO_PADRE: CODIGO del concepto padre o concepto descompuesto del presupuesto.
 *         CODIGO_HIJO: CODIGO del concepto hijo o concepto de la línea de descomposición.
 *         <p>
 *         ~M | [ CODIGO_PADRE \ ] CODIGO_HIJO | { POSICION \ } | 1 | | [ ETIQUETA ] |
 */

@SuppressWarnings("unused")
@DbTable(name = "M")
public class ExampleObjectD {

    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "codigo", indexorder = 1)
    private String codigo;
    @DbColumn(col_name = "posicion", indexorder = 2)
    private String posicion;
    @DbColumn(col_name = "medicion", indexorder = 3)
    private BigDecimal medicion;
    @DbColumn(col_name = "lines", indexorder = 4)
    private String lines;
    @DbColumn(col_name = "noreadable", readabled = false)
    private String noreadable;

    public ExampleObjectD() {
        this.key = DateTools.getTimeStamp();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
        this.key = codigo;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public BigDecimal getMedicion() {
        return medicion;
    }

    public void setMedicion(BigDecimal medicion) {
        this.medicion = medicion;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }


    @Override
    public String toString() {
        String builder = TextTools.REG_SEPARATOR +
                "M" +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(codigo).replace(TextTools.ITEM_SEPARATOR_SPLIT, TextTools.ITEM_SEPARATOR) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(posicion) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                getMedicion() +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.nc(lines) +
                TextTools.OBJECT_SEPARATOR_CHAR +
                TextTools.RETORNO_CARRO_SALTO_LINEA;

        return builder.replace("null", "");
    }

}
