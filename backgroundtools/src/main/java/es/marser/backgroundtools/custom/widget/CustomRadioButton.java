package es.marser.backgroundtools.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import es.marser.backgroundtools.R;

/**
 * @author sergio
 *         Created by Sergio on 07/08/2017.
 *         Componente personalizado para creacción de radio button con imagenes vectoriales
 *         <p>
 *         [EN]  Custom component for radio button creation with vector images
 */

public class CustomRadioButton extends AppCompatRadioButton {
    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomRadioButton);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CustomRadioButton_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.CustomRadioButton_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomRadioButton_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CustomRadioButton_drawableTopCompat);
                setButtonDrawable(attributeArray.getDrawable(R.styleable.CustomRadioButton_buttonCompat));
            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CustomRadioButton_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CustomRadioButton_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CustomRadioButton_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CustomRadioButton_drawableTopCompat, -1);
                final int buttonId = attributeArray.getResourceId(R.styleable.CustomRadioButton_buttonCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
                if (buttonId != -1)
                    setButtonDrawable(VectorDrawableCompat.create(context.getResources(), buttonId, null));
            }

            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            attributeArray.recycle();
        }
    }
}