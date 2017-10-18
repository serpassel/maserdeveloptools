package es.marser.examples;


import java.math.BigDecimal;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbPrimaryKey;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 06/03/2017.
 *         4| { TIPO \ COMENTARIO { # ID_BIM } \ UNIDADES \ LONGITUD \ LATITUD \ ALTURA \ }
 *         1| { TIPO
 *         2\ COMENTARIO { # ID_BIM }
 *         3\ UNIDADES
 *         4\ LONGITUD
 *         5\ LATITUD
 *         6\ ALTURA \ }
 */

@SuppressWarnings("unused")
public class ExampleItem {
    @DbPrimaryKey
    private String key;

    @DbColumn(col_name = "tipo", indexorder = 1)
    private Integer tipo;
    @DbColumn(col_name = "comentario", indexorder = 2)
    private String comentario;
    @DbColumn(col_name = "unidades", indexorder = 3)
    private Double unidades;
    @DbColumn(col_name = "longitud", indexorder = 4)
    private BigDecimal longitud;
    @DbColumn(col_name = "latitud", indexorder = 5)
    private BigDecimal latitud;
    @DbColumn(col_name = "altura", indexorder = 6)
    private BigDecimal altura;


    public ExampleItem() {
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getUnidades() {
        return unidades;
    }

    public void setUnidades(Double unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        String builder = String.valueOf(MathTools.notNaN(tipo)) +
                TextTools.ITEM_SEPARATOR_CHAR +
                getComentario() +
                TextTools.ITEM_SEPARATOR_CHAR +
                getUnidades() +
                TextTools.ITEM_SEPARATOR_CHAR +
                getLongitud() +
                TextTools.ITEM_SEPARATOR_CHAR +
                getLatitud() +
                TextTools.ITEM_SEPARATOR_CHAR +
                getAltura() +
                TextTools.ITEM_SEPARATOR_CHAR;
        return builder.replace("null", "");
    }
}
