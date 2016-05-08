# Progress-for-Retrofit-2
Provides simple progress listener implementation for Retrofit 2

Basic Usage
```java
public interface WS {

   @GET("/sth")
   Observable<Object> getSomething(@DownloadProgress @Header(DownloadProgress.HEADER) ProgressListener listener);

   @POST("/sth")
   Observable<Object> setSomething(@Body String veryLongString, @UploadProgress @Header(UploadProgress.HEADER) ProgressListener listener);
}
```