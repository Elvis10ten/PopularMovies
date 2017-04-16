package mobymagic.com.popularmovies.data.remote;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.utils.RequestUtility;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by etiennelawlor on 12/5/16.
 */

public class AuthorizedNetworkInterceptor implements Interceptor {

    // region Member Variables
    private Context context;
    // endregion

    // region Constructors
    public AuthorizedNetworkInterceptor(Context context) {
        this.context = context;
    }
    // endregion

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain != null) {
            Request originalRequest = chain.request();

            Map<String, String> queryParamsMap = new HashMap<>();
            queryParamsMap.put("api_key", context.getString(R.string.api_key));
            queryParamsMap.put("language", "en-US");
            Request modifiedRequest = RequestUtility.addQueryParams(originalRequest, queryParamsMap);

            return chain.proceed(modifiedRequest);
        }

        return null;
    }
}
