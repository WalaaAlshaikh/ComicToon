![Image of Tuwaiq](https://camo.githubusercontent.com/37ca472e2afb74974a0314d89af8f470422a79582bed0d188f9927777230195d/68747470733a2f2f6c61756e63682e73612f6173736574732f696d616765732f6c6f676f732f7475776169712d61636164656d792d6c6f676f2e737667)
# Capstone 2
Tuwaiq Academy Second Project.
NEARMe Android Application
## Overview:
![Image of app logo](https://g.top4top.io/p_2195pgt901.png)


This project represents an android application **ComicToon**, which helps the comic readers display the famouse classic comics, espicaillay 1990s onces
## Technologies used:
This application was built using the following technologies:
### For Designing the logo of the app:
* Canva

* Adobe Photoshop
### For Designing the pages of the app"
* Figma 

* Adobe Photoshop
### For Programming the app:
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) for improving code quality.
* Android Architecture Components:Room,LiveData,ViewModel and Data binding.
* Room DataBase.
* RecyclerViews & Adapters.
* Required Libraries
* Comicvine
* Video View
* Firebase

## Wireframes and User stories:

![Wireframe](https://i.top4top.io/p_2195qdtzk1.png)

link to Figma [Figma Wireframe](https://www.figma.com/file/ImjWEZmgzFlljOfWqHUWb9/ComicToon?node-id=0%3A1)


- As a user,I want to see the pictures in my location so that I can see the places near me.

- As a user, I would like to be able to choose any location on the map so that I can the picture  in that location.

- As a user I want to see the details of each image so that I can see additional information about it (such as title and date taken)  .

- As a user I want to share images so that I can sent it to other people.

-------------------------------------------------------------------------
## Installation:
Follow the steps below to get started with the project's development environment:
1. Install Android Studio from [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQjw5oiMBhDtARIsAJi0qk2WOPjxp2Wij5sgO3bAK6Rp18zrs4Y0L5S6W89Fk7OClhAiVuNr1mgaAsT-EALw_wcB&gclsrc=aw.ds)
2. Clone this repository:
 ```kotlin 
 $ git clone https://github.com/WalaaAlshaikh/NEARMe.git
 ```
3. Navigate to the project directory:
 ```kotlin 
 $ cd NEARMe
 ```
 4. List of the depencenceies used in the project:
   * for navigation fragments
 ```kotlin
    dependencies {
    implementation "androidx.navigation:navigation-fragment-ktx:2.3.5"
    implementation "androidx.navigation:navigation-ui-ktx:2.3.5"
    }
``` 

   * for notification:
```kotlin
    val core_version = "1.6.0"
    dependencies {
    implementation("androidx.core:core-ktx:$core_version")
    }
```
    
   * for ViewModel
```kotlin
    dependencies {
   implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-rc01"
   implementation "androidx.fragment:fragment-ktx:1.3.6"
    }
```
   * for live data
```kotlin
    dependencies { 
   implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-rc01"
   }
```
   * for Room Database
```kotlin
    dependencies { 
   id 'kotlin-kapt'

   implementation "androidx.room:room-runtime:2.3.0"
   implementation "androidx.room:room-ktx:2.3.0"
   kapt "androidx.room:room-compiler:2.3.0"
   }
```
   * for coroutines
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
   }
```


* for map
```kotlin
    dependencies { 
   // for current location
    implementation 'com.google.android.gms:play-services-location:18.0.0'
    //for enabling map
    implementation 'com.google.android.gms:play-services-maps:18.0.0'
   }
```


 You are ready to develop!
 -----------------------------------------------------------------
 
## Development Process and Problem-solving Strategy:
Firstly, Our developer team brainstormed some ideas related to the requirement of to the app and then took a general idea of the design and the mechanisim of some popular apps from app store
Secondly, we designed a logo according to the purpose of the app and gave it name.
Thirdly, we designed the screens each of them suitable for a specific action using the Figma and Photoshop,after that we statred programming the app using the android studio by dividing the project into several tasks that each member of the team work on it: at first, we installed the required libraries and dependencies and the required api from [FlikrApi](https://www.flickr.com/services/api/).
For many problems that we faced,we needed first to decide the nature of the error (if it's syntax, runtime or logical), and then find the solution accordingly.Such solutions that can be disovered when debugging the error, using (Log.d)to specifty the location of the error, searching for similar cases online in [stackoverflow](https://stackoverflow.com/) and asking for the help of the experts.
## Unsolved Problems which would be fixed in future iterations:
* seeting the favourite image in a separate list which can potentially fixed by using a certain api from  [FlikrApi](https://www.flickr.com/services/api/) and do the authentication .
* some bugs regarding maps such as the marker would not disappear when moving to another place .
* some minor issues regarding the enhancment of the design to make the user expereince more dynamic.

## My favorite functions work:
* sharing images
it is useful when you want to send a certain image to any app.
```kotlin

            val image:Bitmap?= getBitmapFromView(binding.imageItem)
            val share= Intent(Intent.ACTION_SEND)
            share.type="image/*"
            share.putExtra(Intent.EXTRA_STREAM,getImageUri(requireActivity(),image!!))
            startActivity(Intent.createChooser(share, "Share Via:"))

        }

    }

    /// those two functions for sharing the pic

    private fun getBitmapFromView(view: ImageView):Bitmap?{
        val bitmap= Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val paint=Canvas(bitmap)
        view.draw(paint)
        return bitmap

    }
    private fun getImageUri(inContext:Context, inImage:Bitmap): Uri?{
        val byte=ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG,100,byte)
        val path= MediaStore.Images.Media.insertImage(inContext.contentResolver,inImage,"Title",null)
        return Uri.parse(path)

    }
```            
* using Bundle
It is useful when you want to pass a specific data from one fragment to another.
 ```kotlin
 
 // in first fragment
var bundle= bundleOf("Lat" to  imageViewModel.lat,"Long" to imageViewModel.long)
                }
  /// in second fragment
  if(arguments!= null)
        {
            imageViewModel.lat=requireArguments().getDouble("Lat")
            imageViewModel.long= requireArguments().getDouble("Long")
        }
```
