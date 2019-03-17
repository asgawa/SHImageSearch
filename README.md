# SHImageSearch
Image search automatically

**Environment**  
OS: Android  
Min SDK: 4.0.3 (API 15)  
Target (Compile) SDK: 9.0 (API 28)  
Language: Kotlin  
External API: Kakao Developers (https://developers.kakao.com)  

**Technical Specification**
1. Observe user input $editTextUserInput and search when it does not change for 1 second
2. Search when it has triggered
3. Search result will be shown in $recyclerViewImageResults
4. If an error occurred or there are no result, show UI to user
5. Progress UI is required for long time works

9. Orientation
