package pl.gumyns.retrofit_progress;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple object pool for interceptor and converter factory.
 */
public final class ProgressListenerPool {

   ConcurrentHashMap<String, ProgressListener> pool = new ConcurrentHashMap<>(4);

   synchronized public String add(ProgressListener listener) {
      String key = UUID.randomUUID().toString();
      pool.put(key, listener);
      return key;
   }

   synchronized public ProgressListener getListener(String progressId) {
      ProgressListener listener = pool.get(progressId);
      pool.remove(progressId);
      return listener;
   }
}
