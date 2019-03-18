# SHImageSearch
Search image with REST API with typed text and
show received image URLS via RecyclerView

**Environment**  
OS: Android  
Min SDK: 4.0.3 (API 15)  
Target (Compile) SDK: 9.0 (API 28)  
Language: Kotlin  
Libs: Glide4.9.0, Gson2.5.0, Retrofit2.5.0, OkHttp3.12.0, androidx2.0.0  
REST API: Kakao Developers  
https://developers.kakao.com/docs/restapi/search#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EA%B2%80%EC%83%89  

**Technical Specification**
1. Start searching when typed text is not changed for 1 second  
2. Search when it triggered by using REST API  
3. Results(JSON) will be loaded in recycler view with images also pageable  
4. If an error occurred or there are no result, show UI to user  
5. Progress UI is required for loading  
6. Set image view size against device width and keep the aspect ratio of images  
7. Orientation (Optional)  

**Restrictions**
1. Java  
2. OkHttp3/Retrofit2/Jackson/Gson/Glide4  
3. MVP or MVVM  

**Preferred**
1. Kotlin  
2. Clean Architecture  
3. Android Architecture or Android Data Binding  
4. Rx2  
5. Dagger2  
