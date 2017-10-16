package es.marser.backgroundtools.custom;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author sergio
 *         Created by Sergio on 02/06/2017.
 *         Mascara de entrada para cajas de texto
 *         <p>
 *         [EN]  Input Mask for Text Boxes
 */

@SuppressWarnings("unused")
public class MaskWatcher implements TextWatcher {
    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    public static MaskWatcher buildCpf() {
        return new MaskWatcher("###.###.###-##");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }


    @Override
    public void afterTextChanged(Editable s) {
        if (isRunning || isDeleting) {
            return;
        }
        isRunning = true;

        int editableLength = s.length();
        if (editableLength < mask.length()) {
            if (mask.charAt(editableLength) != '#') {
                s.append(mask.charAt(editableLength));
            } else if (mask.charAt(editableLength - 1) != '#') {
                s.insert(editableLength - 1, mask, editableLength - 1, editableLength);
            }
        } else {
            s.replace(mask.length(), editableLength, "");
        }

        isRunning = false;
    }
}