# Progress-for-Retrofit-2
Provides simple progress listener implementation for Retrofit 2

Basic Usage:
```
compile 'pl.gumyns:retrofit_progress:1.0.0'
```
Create pool:
```java
ProgressListenerPool pool = new ProgressListenerPool();
```
Add interceptor and converter:
```java
Retrofit retrofit = new Retrofit.Builder()
    .client(new OkHttpClient.Builder().addInterceptor(new ProgressInterceptor(pool)).build())
    .addConverterFactory(new ProgressConverterFactory(pool))
    ...
    .build();
```
Sample Webservice interface:
```java
public interface WS {

   @GET("/sth")
   Observable<Object> getSomething(@DownloadProgress @Header(DownloadProgress.HEADER) ProgressListener listener);

   @POST("/sth")
   Observable<Object> setSomething(@Body String veryLongString, @UploadProgress @Header(UploadProgress.HEADER) ProgressListener listener);
}
```
