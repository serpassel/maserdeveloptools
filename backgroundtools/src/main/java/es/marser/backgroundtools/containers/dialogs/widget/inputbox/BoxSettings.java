package es.marser.backgroundtools.containers.dialogs.widget.inputbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;

import es.marser.backgroundtools.BR;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 8/11/17.
 *         Modelo para configuración de {@link android.support.design.widget.TextInputEditText}
 *         y {@link android.support.design.widget.TextInputEditText}
 *         <p>
 *         [EN]  Model for configuration of {@link android.support.design.widget.TextInputEditText}
 *         and {@link android.support.design.widget.TextInputEditText}
 */

@SuppressWarnings("unused")
public class BoxSettings extends BaseObservable implements Parcelable {

    public static int textEmailAddress = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
    public static int textPassword = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    public static int textMultiLine = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
    public static int number = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
    public static int numberDecimal = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL;

    private int inputType;
    private int lines;
    private String body;


    private String errorText;

    private int passwordCount;
    private int counterCount;
    private String hint;

    //CONSTRUCTORS_______________________________________________________________________________________________
    public BoxSettings() {
        this(InputType.TYPE_CLASS_TEXT,
                1,
                null,
                0,
                0,
                "Escribir el texto"
        );
    }

    public BoxSettings(String hint) {
        this(1, hint);
    }

    public BoxSettings(int lines, String hint) {
        this(lines > 1 ? textMultiLine : InputType.TYPE_CLASS_TEXT,
                lines,
                null,
                0,
                0,
                hint
        );
    }

    public BoxSettings(int passwordCount) {
        this(textPassword,
                1,
                null,
                passwordCount,
                0,
                "Contraseña"
        );
    }

    public BoxSettings(int inputType, int lines, String errorText, int passwordCount, int counterCount, String hint) {
        this.inputType = inputType;
        this.lines = lines;
        this.errorText = TextTools.nc(errorText);
        this.passwordCount = passwordCount;
        this.counterCount = counterCount;
        this.hint = TextTools.nc(hint);
        this.body = "";
    }

    private void notifyAllBR() {
        notifyPropertyChanged(BR.inputType);
        notifyPropertyChanged(BR.passwordCount);
        notifyPropertyChanged(BR.counterCount);
    }

    //EDIT TEXT_________________________________________________________________________
    public BoxSettings setInputType(int inputType) {
        this.inputType = inputType;
        notifyPropertyChanged(BR.inputType);
        return this;
    }

    @Bindable
    public int getInputType() {
        return this.inputType;
    }

    public BoxSettings setLines(int lines) {
        this.lines = lines;
        notifyPropertyChanged(BR.lines);
        setInputType(
                lines > 1 ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        : InputType.TYPE_CLASS_TEXT
        );
        return this;
    }

    @Bindable
    public int getLines() {
        return this.lines;
    }

    @Bindable
    public String getBody() {
        return this.body;
    }

    public BoxSettings setBody(String body) {
        if (counterCount > 0 && body.length() > counterCount) {
            return this;
        }

        this.body = body;
        notifyPropertyChanged(BR.body);

        return this;
    }

    //INPUT LAYOUT_______________________________________________________________________
    public BoxSettings setErrorText(String errorText) {
        this.errorText = errorText;
        notifyPropertyChanged(BR.errorText);
        return this;
    }

    @Bindable
    public String getErrorText() {
        return this.errorText;
    }


    /*Validate password*/
    public BoxSettings setPasswordCount(int passwordCount) {
        this.passwordCount = passwordCount;
        notifyPropertyChanged(BR.passwordCount);

        if (this.passwordCount < 0) {
            setInputType(textPassword);
        }

        return this;
    }

    @Bindable
    public int getPasswordCount() {
        return this.passwordCount;
    }

    /**
     * Valida si la contraseña introducida contiene un mínimo de caracteres
     * <p>
     * [EN]  Valid if the entered password contains a minimum of characters
     *
     * @return verdadero si la contraseña cumple con la longitud [EN]  true if the password meets the length
     */
    public boolean validatePassword() {
        if (passwordCount < 1) {
            setErrorText("");
            return true;
        }
        boolean out = TextTools.validatePassword(body, passwordCount);
        if (!out) {
            setErrorText("La contraseña debe contener al menos " + passwordCount + " caracteres");
        } else {
            setErrorText("");
        }
        return out;
    }


    /**
     * Comprueba que la cadena de texto tiene una formación de dirección de correo electrónico
     * <p>
     * [EN]  Verify that the text string has an email address formation
     *
     * @return verdadero si no es cuadro de emial o si se cumple el patrón de correo electrónico
     * [EN]  true if it is not emial box or if the email pattern is met
     */
    public boolean validateMail() {
        boolean out = inputType != (textEmailAddress)
                || TextTools.validateMail(this.body);

        if (!out) {
            setErrorText("Malformación de la dirección de correo");
        } else {
            setErrorText("");
        }
        return out;
    }

    /**
     * Valida el valor del texto
     * <p>
     * [EN]  Validate the value of the text
     *
     * @return Verdadero si se cumple el patrón [EN]  True if the pattern is met
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean validate() {
        if (inputType == (textEmailAddress)) {
            return validateMail();
        }

        if (inputType == (textPassword)) {
            return validatePassword();
        }

        return true;
    }

    /*Contador*/
    public BoxSettings setCounterCount(int counterCount) {
        this.counterCount = counterCount;
        notifyPropertyChanged(BR.counterCount);
        return this;
    }

    @Bindable
    public int getCounterCount() {
        return this.counterCount;
    }

    public BoxSettings setHint(String hint) {
        this.hint = hint;
        notifyPropertyChanged(BR.hint);
        return this;
    }

    @Bindable
    public String getHint() {
        return this.hint;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(MathTools.notNaN(inputType));
        dest.writeInt(MathTools.notNaN(lines));
        dest.writeString(TextTools.nc(errorText));
        dest.writeInt(MathTools.notNaN(passwordCount));
        dest.writeInt(MathTools.notNaN(counterCount));
        dest.writeString(TextTools.nc(hint));
        dest.writeString(TextTools.nc(body));

    }

    protected BoxSettings(Parcel in) {
        inputType = in.readInt();
        lines = in.readInt();
        errorText = in.readString();
        passwordCount = in.readInt();
        counterCount = in.readInt();
        hint = in.readString();
        body = in.readString();
    }

    public static final Parcelable.Creator<BoxSettings> CREATOR = new Parcelable.Creator<BoxSettings>() {
        @Override
        public BoxSettings createFromParcel(Parcel in) {
            return new BoxSettings(in);
        }

        @Override
        public BoxSettings[] newArray(int size) {
            return new BoxSettings[size];
        }
    };

}
