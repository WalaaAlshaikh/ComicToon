![Image of Tuwaiq](https://camo.githubusercontent.com/37ca472e2afb74974a0314d89af8f470422a79582bed0d188f9927777230195d/68747470733a2f2f6c61756e63682e73612f6173736574732f696d616765732f6c6f676f732f7475776169712d61636164656d792d6c6f676f2e737667)
# FINAL CAPSTONE
Tuwaiq Academy final Project.
ComicToon Android Application using Kotlin
## Overview:
![Image of app logo](https://a.top4top.io/p_2205wfx7r1.png)


This project represents an android application **ComicToon** ,that allows user to display classic comics especially in th 1990s , mark them and provide addtional information for each specific .
This application was built using the following technologies:
### For Designing the logo of the app:
* Canva

* Adobe Photoshop
### For Designing the pages of the app"
* Figma 

* Adobe Photoshop
### For Programming the app:
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) for improving code quality.
* Android Architecture Components:MVVM,Navigation, Livedata.
* RecyclerViews & Adapters.
* [Postman API Platform](https://www.postman.com/downloads/)
* Required Libraries
* [Firebase](https://firebase.google.com)
* API from [ComicVine](https://comicvine.gamespot.com/api/)

## Wireframes and User stories:

![Wireframe](https://i.top4top.io/p_2195qdtzk1.png)

link to Figma [Figma Wireframe](https://www.figma.com/file/ImjWEZmgzFlljOfWqHUWb9/ComicToon?node-id=0%3A1)


- As a user,I want to display the comics so that I get to know classic comics

- As a user I want to see the details of each comic so that I can see additional information about it (such as description of the comic, publisher , publish date ..etc)  .





- As a user I want to mark my favourite comic so that I can get back to them easily.
 
- As a user I would like to be able to add/ edit my personal review for my favourite comic so that I write down my personal opinion and thoughts.

- As a user I would like to be able to delete the comics that I marked so that I can remove the one that no longer see it a favourite.

- As a user I would like to be able to to play videos realted to comics so that I can enjoy watching various comic videos.


-------------------------------------------------------------------------
## Demo of the Application:
https://user-images.githubusercontent.com/91417065/151183556-c3c0a3ce-0acd-482f-a596-1835d922e0a8.mp4





## Installation:
Follow the steps below to get started with the project's development environment:
1. Install Android Studio from [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQjw5oiMBhDtARIsAJi0qk2WOPjxp2Wij5sgO3bAK6Rp18zrs4Y0L5S6W89Fk7OClhAiVuNr1mgaAsT-EALw_wcB&gclsrc=aw.ds)
2. Clone this repository:
 ```kotlin 
 $ git clone https://github.com/WalaaAlshaikh/ComicToon.git
 ```
3. Navigate to the project directory:
 ```kotlin 
 $ cd ComicToon
 ```
 4. List of the depencenceies used in the project:
   * for [navigation fragments](https://developer.android.com/guide/navigation/navigation-getting-started)
 ```kotlin
    dependencies {
    implementation "androidx.navigation:navigation-fragment-ktx:2.3.5"
    implementation "androidx.navigation:navigation-ui-ktx:2.3.5"
    }
``` 

   * for [notification](https://developer.android.com/training/notify-user/build-notification):
```kotlin
    val core_version = "1.6.0"
    dependencies {
    implementation("androidx.core:core-ktx:$core_version")
    
    implementation 'com.google.firebase:firebase-messaging-ktx:23.0.0'
    
    }
```
    
   * for [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
```kotlin
    dependencies {
   implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-rc01"
   implementation "androidx.fragment:fragment-ktx:1.3.6"
    }
```
   * for [live data](https://developer.android.com/topic/libraries/architecture/livedata)
```kotlin
    dependencies { 
   implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-rc01"
   }
```
   * for [Firebase Database](https://firebase.google.com/)
```kotlin
    dependencies { 
    implementation 'com.google.firebase:firebase-auth-ktx:21.0.1'
    implementation 'com.google.firebase:firebase-firestore-ktx:24.0.0'
    implementation 'com.google.firebase:firebase-storage-ktx:20.0.0'
    implementation 'com.google.firebase:firebase-database-ktx:20.0.3'
    implementation 'com.google.firebase:firebase-messaging-ktx:23.0.0'
    implementation 'com.firebaseui:firebase-ui-firestore:8.0.0'
   }
```
   * for [coroutines](https://developer.android.com/kotlin/coroutines)
```kotlin
    dependencies { 
   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
   }
```

* for Images
```kotlin
    dependencies { 
   implementation 'com.squareup.picasso:picasso:2.71828'
   implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    // for making pic circular
    implementation 'de.hdodenhof:circleimageview:3.1.0'
   }
```


* for animation
```kotlin
    dependencies {
    implementation 'com.facebook.shimmer:shimmer:0.5.0'
    
```

* for language (Localization)
```kotlin
    dependencies {
    implementation 'com.akexorcist:localization:1.2.11'
    
```


 You are ready to develop!
 -----------------------------------------------------------------
 
## Development Process and Problem-solving Strategy:
Firstly, I brainstormed some ideas related to the requirement of the app and then took a general idea of the design and the mechanisim of some popular apps from app store
Secondly, I designed a logo according to the purpose of the app and gave it name.
Thirdly, I designed the screens each of them suitable for a specific action using the Figma and Photoshop,after that I statred programming the app using the android studio by dividing the project into several tasks to work on it: at first, I installed the required libraries and dependencies and the required api from [ComicVine](https://comicvine.gamespot.com/api/).
As for the obstacles that I faced,first I needed to decide the nature of the error (if it's syntax, runtime or logical), and then find the solution accordingly.Such solutions that can be disovered when debugging the error, using (Log.d)to specifty the location of the error, searching for similar cases online in [stackoverflow](https://stackoverflow.com/) and asking for the help of the experts. After managing to finish all the requirements of the projects, I started adding more features to the app such as :
* Videos
* switching to eng\ar 
* internet connection handling
* and more.
## Unsolved Problems which would be fixed in future iterations:
* The issue of playing\puasing videos in VideoView, which can potentially be solved by adding some functions to add the action bar for videos.
* Some conflects in ui regarding Localization (when switching from English into Arabic).
* some minor issues regarding the enhancment of the design to make the user expereince more dynamic.

## My favorite functions work:
* video View
to display video url (.mp4)
 
 ```kotlin
// in class.kt file
var video:VideoView=view.findViewById(R.id.videoView)
 var mediaControls: MediaController? = null
        if (mediaControls == null) {
           //  creating an object of media controller class
            mediaControls = MediaController(context)

            // set the anchor view for the video view
            mediaControls.setAnchorView(video)
         video.setMediaController(mediaControls)
        video.setVideoURI(
              Uri.parse(item.highUrl))
           video.requestFocus()
           video.start()
        }
        
 // in xml file
 <VideoView
  //...//
            />
```            
* using Meta (facebook) library for animation
 an Android library that provides an easy way to add a shimmer effect to any view in your Android app. It is useful as an unobtrusive loading indicator that was originally developed for Facebook Home.

 ```kotlin
 
 // Gradle dependency on Shimmer for Android
dependencies {
  implementation 'com.facebook.shimmer:shimmer:0.5.0'
}

in xml
<com.facebook.shimmer.ShimmerFrameLayout
     android:id="@+id/shimmer_view_container"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
>
     ...(your complex view here)...
</com.facebook.shimmer.ShimmerFrameLayout>
 

```
