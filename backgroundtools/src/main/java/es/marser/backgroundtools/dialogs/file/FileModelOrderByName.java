package es.marser.backgroundtools.dialogs.file;

import java.util.Comparator;
import java.util.List;

import es.marser.backgroundtools.dialogs.model.FileModel;

/**
 * @author sergio
 *         Created by sergio on 29/10/17.
 *         Comparador de objectos de archivo
 *         <p>
 *         [EN]  File Object Comparer
 *
 *         @see java.util.Collections#sort(List, Comparator)
 */

public class FileModelOrderByName implements Comparator<FileModel>{
        @Override
        public int compare(FileModel o1, FileModel o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
