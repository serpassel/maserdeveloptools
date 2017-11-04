package es.marser.backgroundtools.systemtools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

import es.marser.tools.TextTools;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author sergio
 *         Created by Sergio on 21/06/2017.
 *         Operaciones con imagenes
 *         <p>
 *         [EN]  Operations with images
 */

@SuppressWarnings("unused")
public class ImageTools{

    /**
     * Redimensionar imagen
     * <p>
     * [EN]  Resize image
     *
     * @param mBitmap   Imagen de tipo {@link Bitmap}
     * @param newWidth  Ancho nuevo [EN]  New width
     * @param newHeigth Alto nuevo [EN]  New height
     * @return Imagen redimenacionada de tipo {@link Bitmap}
     */
    public static Bitmap resizeImage(Bitmap mBitmap, float newWidth, float newHeigth) {

        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = (newWidth) / width;
        float scaleHeight = (newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    /**
     * Redimensionar la imagen ajustando su ancho sin perder la proporciones
     * <p>
     * [EN]  Resize the image by adjusting its width without losing proportions
     *
     * @param mBitmap  imagen en {@link Bitmap}
     * @param newWidth nuevo ancho [EN]  new width
     * @return Imagen redimenacionada de tipo {@link Bitmap}
     */
    public static Bitmap resizeImageWidth(Bitmap mBitmap, float newWidth) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scalew = (newWidth) / width;
        float newHeight = scalew * height;
        return resizeImage(mBitmap, newWidth, newHeight);
    }

    /**
     * Redimensionar la imagen ajustando su alto sin perder la proporciones
     * <p>
     * [EN]  Resize the image by adjusting its height without losing proportions
     *
     * @param mBitmap  imagen en {@link Bitmap}
     * @param newWidth nuevo ancho [EN]  new width
     * @return Imagen redimenacionada de tipo {@link Bitmap}
     */
    public static Bitmap resizeImageHeight(Bitmap mBitmap, float newHeight) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleh = (newHeight) / height;
        float newWidth = scaleh * width;
        return resizeImage(mBitmap, newWidth, newHeight);
    }

    /**
     * Ajustar según el valor máximo de una de sus dimensiones sin perder la proporciones
     * <p>
     * [EN]  Adjust according to the maximum value of one of its dimensions without losing the proportions
     *
     * @param mBitmap imagen en {@link Bitmap}
     * @param newSize nueva medida [EN]  new measure
     * @return Imagen redimenacionada de tipo {@link Bitmap}
     */
    public static Bitmap resizeMaxSize(Bitmap mBitmap, float newSize) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int max = width - height;
        if (max > 0) {
            return resizeImageWidth(mBitmap, newSize);
        } else {
            return resizeImageHeight(mBitmap, newSize);
        }
    }

    /**
     * Convertir un texto en {@link Bitmap}
     * <p>
     * [EN]  Convert a text in {@link Bitmap}
     *
     * @param text      Texto [EN]  Text
     * @param textSize  Tamaño del texto [EN]  Text size
     * @param textColor color del texto [EN]  text color
     * @return Covierte texto en {@link Bitmap} [EN]  Convert text in {@link Bitmap}
     */
    public static Bitmap textAsBitmap(Context context, String text, float textSize, int textColor) {

        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto_Black.ttf"));
        paint.setTextAlign(Paint.Align.LEFT);

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    /**
     * Crear una imagen circular
     * <p>
     * [EN]  Create a circular image
     *
     * @param bmp    imagen en {@link Bitmap} [EN]  image in {@link Bitmap}
     * @param radius radio de la imagen [EN]  radio of the image
     * @return Imagen circular [EN]  Circular image
     * @throws NullPointerException      Posibles nulos  [EN]  Possible nulls
     * @throws IndexOutOfBoundsException Tamaños de radio no válidos [EN]  Invalid radio sizes
     */
    public static Bitmap croppedBitmap(Bitmap bmp, int radius) throws NullPointerException, IndexOutOfBoundsException {
        Bitmap sbmp;

        int h = bmp.getHeight();
        int w = bmp.getWidth();

        if (h <= 0 || w <= 0 || radius <= 0) {
            //return bmp;
            throw new IndexOutOfBoundsException("La medidas son inferiores al valor admitido");
        }

        if (w != radius || h != radius) {
            float smallest = Math.min(w, h);
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (w / factor),
                    (int) (h / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));

        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
                radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    /**
     * Enlaza una imagen remota en un {@link ImageView}
     * <p>
     * [EN]  Link a remote image in a {@link ImageView}
     *
     * @param imageView    Objeto sobre el que se enlaza la imagen [EN]  Object on which the image is linked
     * @param uri          origen externo de la iagen [EN]  external origin of the iagen
     * @param defaultImage Imagen por defecto en caso de error [EN]  Default image in case of error
     * @param crop         verdadero si se quiere una imagen circular [EN]  true if you want a circular image
     */
    public static void setImagefromUri(ImageView imageView, String uri, int defaultImage, boolean crop) {
        if (imageView == null) {
            return;
        }
        new LoadImageAsyncTask(imageView, defaultImage, crop).execute(uri);

    }

    /**
     * Devuelve la medida mínima de una vista
     * <p>
     * [EN]  Returns the minimum size of a view
     *
     * @param view vista [EN]  vista
     * @return valor de la medida mínima [EN]  value of the minimum measure
     */
    public static int minSize(View view) {
        int h = view.getHeight();
        int w = view.getWidth();
        return Math.min(h, w);
    }

    /**
     * Cargar una página web en un webview
     * <p>
     * [EN]  Upload a web page in a webview
     *
     * @param view        Objeto {@link WebView} [EN]  Objeto {@link WebView}
     * @param url         Enlace de la web [EN]  Link to the web
     * @param imageView   Imagen a sustituir por el webview [EN]  Image to be replaced by the webview
     * @param progressBar barra de progreso de carga [EN]  load progress bar
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebPage(WebView view, String url, final ImageView imageView, final ProgressBar progressBar) {
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        view.setHorizontalScrollBarEnabled(true);
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (imageView != null) {
                    imageView.setVisibility(View.GONE);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                view.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (imageView != null) {
                    imageView.setVisibility(View.GONE);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                view.setVisibility(View.VISIBLE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                view.setVisibility(View.GONE);
            }
        });

        view.loadUrl(url);
    }

    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("unused")
    public static class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        Integer defaultImage;
        boolean crop;

        public LoadImageAsyncTask(ImageView bmImage, Integer defaultImage, boolean crop) {
            this.bmImage = bmImage;
            if (defaultImage != null && defaultImage > -1) {
                this.defaultImage = defaultImage;
            }
            bmImage.setImageResource(this.defaultImage);
            this.crop = crop;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            if (TextTools.isEmpty(urls[0]) || !urls[0].startsWith("http")) {
                return null;
            }

            try {
                InputStream in = new java.net.URL(urldisplay).openConnection().getInputStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                // Log.e(MainCRUD.TAG, "ERROR CARGA DE IMAGEN " + e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //Comprobamos si tiene que ser redonda
            if (result == null) {
                return;
            }
            if (crop) {
                try {
                    result = croppedBitmap(result, minSize(bmImage));
                    bmImage.setImageBitmap(result);
                } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                }
            }
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Oyente de carga [EN]  Load listener
     * Devuelve el bitmap de la imagen
     * <p>
     * [EN]  Returns the bitmap of the image
     */
    @SuppressWarnings("unused")
    public interface BitmapFormUriListener {
        void onBitmapFromUri(Bitmap bitmap);
    }
}
