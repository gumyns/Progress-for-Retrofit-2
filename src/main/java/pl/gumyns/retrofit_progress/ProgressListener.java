package pl.gumyns.retrofit_progress;

public interface ProgressListener {

   void update(long bytesRead, long contentLength);
}
