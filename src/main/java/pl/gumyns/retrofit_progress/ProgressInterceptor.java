package pl.gumyns.retrofit_progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import pl.gumyns.retrofit_progress.annotation.DownloadProgress;
import pl.gumyns.retrofit_progress.annotation.UploadProgress;

/**
 * Okhttp3 interceptor.
 * Gets listener from object pool.
 * Removes additional helper headers.
 * Rewrites both Request and Response to support progress listeners.
 */
public final class ProgressInterceptor implements Interceptor {

   private final ProgressListenerPool pool;

   public ProgressInterceptor(ProgressListenerPool pool) {
      this.pool = pool;
   }

   @Override
   public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      if (request.header(UploadProgress.HEADER) != null) {
         // get temporary header
         String progressId = request.header(UploadProgress.HEADER);
         // remove temporary header and rewrite body
         return chain.proceed(request.newBuilder()
            .headers(request.headers().newBuilder().removeAll(UploadProgress.HEADER).build())
            .method(request.method(), new ProgressRequestBody(request.body(), pool.getListener(progressId)))
            .build());
      } else if (request.header(DownloadProgress.HEADER) != null) {
         // get temporary header
         String progressId = request.header(DownloadProgress.HEADER);
         // remove temporary header and proceed
         Response response = chain.proceed(request.newBuilder()
            .headers(request.headers().newBuilder().removeAll(DownloadProgress.HEADER).build())
            .build());
         // rewrite response
         return response.newBuilder()
            .body(new ProgressResponseBody(response.body(), pool.getListener(progressId)))
            .build();
      }
      return chain.proceed(request);
   }
}
