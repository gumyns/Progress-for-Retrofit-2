package pl.gumyns.retrofit_progress;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import pl.gumyns.retrofit_progress.annotation.DownloadProgress;
import pl.gumyns.retrofit_progress.annotation.UploadProgress;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Converter factory that generates uuid for all used progress listeners.
 */
public final class ProgressConverterFactory extends Converter.Factory {

   private final ProgressListenerPool pool;

   public ProgressConverterFactory(ProgressListenerPool pool) {
      this.pool = pool;
   }

   @Override
   public Converter<ProgressListener, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
      for (Annotation annotation : annotations) {
         if (annotation instanceof DownloadProgress || annotation instanceof UploadProgress) {
            return new Converter<ProgressListener, String>() {
               @Override
               public String convert(ProgressListener value) throws IOException {
                  return pool.add(value);
               }
            };
         }
      }
      return null;
   }
}
