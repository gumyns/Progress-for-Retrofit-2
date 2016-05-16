package pl.gumyns.retrofit_progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Request body implementation with progress listener
 */
final class ProgressRequestBody extends RequestBody {

   private final RequestBody requestBody;

   private final ProgressListener progressListener;

   public ProgressRequestBody(RequestBody responseBody, ProgressListener progressListener) {
      this.requestBody = responseBody;
      this.progressListener = progressListener;
   }

   @Override
   public MediaType contentType() {
      return requestBody.contentType();
   }

   @Override
   public void writeTo(BufferedSink sink) throws IOException {
      final long contentLength = contentLength();
      BufferedSink progressSink = Okio.buffer(new ForwardingSink(sink) {
         private long totalBytesWritten = 0L;

         @Override
         public void write(Buffer source, long bytesWritten) throws IOException {
            totalBytesWritten += bytesWritten;
            progressListener.update(totalBytesWritten, contentLength);
            super.write(source, bytesWritten);
         }
      });
      requestBody.writeTo(progressSink);
      progressSink.flush();
   }
}
