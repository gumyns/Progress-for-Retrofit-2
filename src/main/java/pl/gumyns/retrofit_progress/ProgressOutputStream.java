package pl.gumyns.retrofit_progress;

import java.io.IOException;
import java.io.OutputStream;

public class ProgressOutputStream extends OutputStream{

  private final ProgressListener progressListener;
  private final OutputStream out;
  private final long total;
  private long totalWritten;

  public ProgressOutputStream(ProgressListener progressListener, OutputStream out, long total) {
    this.progressListener = progressListener;
    this.out = out;
    this.total = total;
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    this.out.write(b, off, len);
    if (this.total < 0) {
      this.progressListener.update(-1, -1);
      return;
    }
    if (len < b.length) {
      this.totalWritten += len;
    } else {
      this.totalWritten += b.length;
    }
    this.progressListener.update(this.totalWritten, this.total);
  }

  @Override
  public void write(int b) throws IOException {
    this.out.write(b);
    if (this.total < 0) {
      this.progressListener.update(-1, -1);
      return;
    }
    this.totalWritten++;
    this.progressListener.update(this.totalWritten, this.total);
  }

  @Override
  public void close() throws IOException {
    if (this.out != null) {
      this.out.close();
    }
  }

  @Override
  public void flush() throws IOException {
    if (this.out != null) {
      this.out.flush();
    }
  }
}
