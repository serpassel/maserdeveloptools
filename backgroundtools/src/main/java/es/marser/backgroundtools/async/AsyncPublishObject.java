package es.marser.backgroundtools.async;

/**
 * @author sergio
 *         Created by sergio on 15/10/17.
 *         Objeto generico de publicación de resultados en tareas asíncronas
 *         <p>
 *         [EN]  Generic object of publication of results in asynchronous tasks
 */

@SuppressWarnings("unused")
public class AsyncPublishObject<T> {
    private T record;/*Objecto procesado [EN]  Processed object*/
    private Throwable e;/*Error de generación [EN]  Generation error*/

    public AsyncPublishObject() {
        this(null, null);
    }

    public AsyncPublishObject(T record) {
        this(record, null);
    }

    public AsyncPublishObject(Throwable e) {
        this(null, e);
    }

    public AsyncPublishObject(T record, Throwable e) {
        this.record = record;
        this.e = e;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
