package mobymagic.com.popularmovies.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import java.lang.ref.WeakReference;

import timber.log.Timber;

public class BlurUtils {

    private static final String LOG_TAG = "BlurUtils";

    public static final float DEFAULT_BITMAP_SCALE = 0.2f;
    public static final float DEFAULT_BLUR_RADIUS = 25.0f;

    /**
     * Blurs the given bitmap using the provided scale and radius
     * @param context Any context
     * @param bitmap The bitmap to blur
     * @param bitmapScale The value to use to scale the bitmap
     * @param blurRadius The blur radius
     * @return A blurred bitmap on success, null on failure
     */
    @WorkerThread
    public static @Nullable Bitmap blur(@NonNull Context context, @NonNull Bitmap bitmap,
                                        float bitmapScale, float blurRadius) {
        Timber.d(LOG_TAG, "Blurring bitmap with radius: ", blurRadius);
        int width = Math.round(bitmap.getWidth() * bitmapScale);
        int height = Math.round(bitmap.getHeight() * bitmapScale);

        try {
            Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            theIntrinsic.setRadius(blurRadius);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            //recycle the original bitmap
            //bitmap.recycle(); Don't think its a nice idea making the parameter 'bitmap' unusable
            //After finishing everything, we cleanup the Renderscript.
            rs.destroy();

            Timber.d("Blur successful");
            return outputBitmap;
        } catch (OutOfMemoryError e) {
            Timber.e(e);
        }
        //Getting to this point means an error occurred
        return null;
    }

    @MainThread
    public static void blurAsync(@NonNull Context context,
                                 @NonNull Bitmap bitmap,
                                 float bitmapScale,
                                 float blurRadius,
                                 @NonNull BlurTaskCallback blurTaskCallback) {
        new BlurTask(context, blurTaskCallback, bitmapScale, blurRadius)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, bitmap);
    }

    private static class BlurTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private Context mAppContext;
        private WeakReference<BlurTaskCallback> mBlurTaskCallbackRef;
        private float bitmapScale;
        private float blurRadius;

        BlurTask(@NonNull Context context,
                 @NonNull BlurTaskCallback blurTaskCallback,
                 float bitmapScale,
                 float blurRadius) {
            mAppContext = context;
            mBlurTaskCallbackRef = new WeakReference<>(blurTaskCallback);

            this.bitmapScale = bitmapScale;
            this.blurRadius = blurRadius;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            return BlurUtils.blur(mAppContext, params[0], bitmapScale, blurRadius);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            BlurTaskCallback blurTaskCallback = mBlurTaskCallbackRef.get();
            if(blurTaskCallback != null && blurTaskCallback.isActive()) {
                if(bitmap == null) {
                    blurTaskCallback.onBlurError();
                } else {
                    blurTaskCallback.onBlurSuccess(bitmap);
                }
            }
        }
    }

    public interface BlurTaskCallback {

        boolean isActive();
        void onBlurError();
        void onBlurSuccess(@NonNull Bitmap bitmap);

    }

}
