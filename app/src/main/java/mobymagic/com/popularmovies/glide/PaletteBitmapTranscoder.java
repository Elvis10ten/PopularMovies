package mobymagic.com.popularmovies.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import timber.log.Timber;

public class PaletteBitmapTranscoder implements ResourceTranscoder<Bitmap, PaletteBitmap> {

    private static final String LOG_TAG = "PaletteBitmapTranscoder";

    private final BitmapPool bitmapPool;

    public PaletteBitmapTranscoder(@NonNull Context context) {
        Timber.d(LOG_TAG, "Initializing PaletteBitmapTranscoder");
        this.bitmapPool = Glide.get(context).getBitmapPool();
    }

    @Override public Resource<PaletteBitmap> transcode(Resource<Bitmap> toTranscode) {
        Timber.d(LOG_TAG, "Transcoding bitmap");
        Bitmap bitmap = toTranscode.get();

        Palette palette = new Palette.Builder(bitmap).clearFilters().maximumColorCount(24).generate();
        PaletteBitmap result = new PaletteBitmap(bitmap, palette);
        return new PaletteBitmapResource(result, bitmapPool);
    }

    @Override public String getId() {
        return PaletteBitmapTranscoder.class.getName();
    }

}