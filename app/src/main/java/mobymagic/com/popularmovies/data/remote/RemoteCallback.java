package mobymagic.com.popularmovies.data.remote;

import android.support.annotation.Nullable;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RemoteCallback<T> implements Callback<T> {

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                onSuccess(response.body());
                break;
            default:
                onFailed(response.code(), response.message());
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        onFailed(t);
    }

    public abstract void onSuccess(@Nullable T response);

    public abstract void onFailed(int responseCode, @Nullable String message);

    public abstract void onFailed(Throwable throwable);
}