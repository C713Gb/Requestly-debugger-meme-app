
# Using Requestly Android SDK

## Step 1: Setting up Requestly
Login to the Requestly dashboard https://app.requestly.io/

## Step 2: Setting up the Android dependencies
Add the following dependencies in the *app/build.gradle* file
```
dependencies {
    debugImplementation "io.requestly:requestly-android:2.0.0"
    releaseImplementation "io.requestly:requestly-android-noop:2.0.0"
    debugImplementation "io.requestly:requestly-android-okhttp:2.0.0"
    releaseImplementation "io.requestly:requestly-android-okhttp-noop:2.0.0"
}
```
## Step 3: Initializing the Requestly SDK
Initialize the Requestly SDK in your *Application* class *onCreate* method.
```
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Requestly SDK like this
        Requestly.Builder(this, "<your-sdk-key>")
            .build()
    }
}
```
You can fetch your SDK key by creating a new app in Requestly.

## Step 4: Configurating your Retrofit Builder and OkHttpClient
To configure the Interceptor, you need to initialize the RQCollector and then add rqInterceptor as the last interceptor on okHttpClient.
```
val collector = RQCollector(context=appContext)
val rqInterceptor = RQInterceptor.Builder(appContext)
    .collector(collector)
    .build()
    
val client = OkHttpClient.Builder()
    .addInterceptor(rqInterceptor)
    .build()
```
Now, add the client to the Retrofit Builder.
```
Retrofit.Builder()
    .baseUrl(APIUtils.API_BASE_URI)
    .client(okHttpClient) // okHttpClient with RQInterceptor
    .build();
```

## Step 5: Connect your mobile device to Requestly
After successfully running the app, you will be able to view the device id provided by Requestly in the status bar. 
Connect your Requestly browser or web application by entering the device id, after which you will be able to intercept all the network calls and add rules to them.
In case you are facing any issues connecting the device, please refer to their official documentation.

Reference to Requestly links:

- [Requestly Official website](https://requestly.io/)
- [Requestly Github Page](https://github.com/requestly/requestly)
- [Requestly Android SDK documentation](https://docs.requestly.io/mobile-interceptor/android-sdk)