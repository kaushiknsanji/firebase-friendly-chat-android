# Firebase Android - Build Friendly Chat

**Friendly Chat** app built by following the instructions detailed in the Google Codelab **["Firebase Android Codelab - Build Friendly Chat"][Firebase_Build_Friendly_Chat_Codelab]**. Original code by Google for this codelab can be referred [here][Firebase_Build_Friendly_Chat_Repository].

## What one will learn

* Allow users to sign in and authenticate with Google Sign-In Provider.
* Synchronize messages (reading and sending) using Firebase Realtime Database and FirebaseUI Database. 
* Download images from Firebase Cloud Storage, for messages containing images.
* Store binary files of images in Firebase Cloud Storage, for messages sent with an image.

## Getting Started

* Android Studio 4.0 or higher with updated SDK and Gradle.
* Android device or emulator with Android 4.4+.

## Branches in this Repository

* **[build-starter](https://github.com/kaushiknsanji/firebase-friendly-chat-android/tree/build-starter)**
	* This is the Starter code for the [codelab][Firebase_Build_Friendly_Chat_Codelab].
	* In comparison to the original [build-starter][Firebase_Build_Friendly_Chat_Starter_Repository] repository, this repository contains some modifications and corrections-
		* Moved all dependency versions to project gradle for centralized management and updated the same to their latest versions.
		* Applied Lint corrections in layouts related to chosen sdk versions, and for some invalid attributes used.
		* Used Android ViewBinding.
		* Refactored certain code parts to utility methods and classes.
		* Rewritten [Main](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/build-starter/app/src/main/res/layout/activity_main.xml) and [Message Item](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/build-starter/app/src/main/res/layout/item_message.xml) layouts to use tools namespace in order to show list items.
		* Rewritten [Message Item](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/build-starter/app/src/main/res/layout/item_message.xml) layout to remove a redundant root `LinearLayout`, and to show the Messenger's name at the top rather than below the text/image message.
		* Configured Glide to be used via [AppGlideModule](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/build-starter/app/src/main/java/com/google/firebase/codelab/friendlychat/MyAppGlideModule.java).
		* Placed TODO statements appropriately.
* **[master](https://github.com/kaushiknsanji/firebase-friendly-chat-android/tree/master)**
	* This contains the Solution for the [codelab][Firebase_Build_Friendly_Chat_Codelab].
    * In comparison to the original [solution][Firebase_Build_Friendly_Chat_Solution_Repository] repository, this repository contains additional modifications and corrections-
		* Uses Glide through [AppGlideModule](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/master/app/src/main/java/com/google/firebase/codelab/friendlychat/MyAppGlideModule.java).
		* Changes to [MessageViewHolder](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/master/app/src/main/java/com/google/firebase/codelab/friendlychat/MessageViewHolder.java)-
			* Takes care of setting the Messenger's Name and Profile picture as well. 
			* Supports Text messages along with an Image.
		* Adds [List Item Spacing](https://github.com/kaushiknsanji/firebase-friendly-chat-android/blob/master/app/src/main/java/com/google/firebase/codelab/friendlychat/VerticalListItemSpacingDecoration.java) through RecyclerView ItemDecoration.
		* Monitors changes in the `Query` referenced, by passing `LifecycleOwner` instance to `FirebaseRecyclerOptions` instead of registering it manually in appropriate lifecycle callbacks.
		* While sending an Image message, any Text message entered by the user prior to launching the Gallery Intent, is also included so that a Message with Text and Image is sent together.
		* The Upload Task for uploading an Image is combined with a Continuation Task for retrieving the downloadable URI to the uploaded image, instead of nesting this task on Success of Upload Task.
		* Uses FirebaseUI-Storage for downloading an Image directly from a `StorageReference` via Glide, by configuring `GlideModule` to register `FirebaseImageLoader` as a component to handle `StorageReference`.
	

<!-- Reference Style Links are to be placed after this -->
[Firebase_Build_Friendly_Chat_Codelab]: https://codelabs.developers.google.com/codelabs/firebase-android/index.html
[Firebase_Build_Friendly_Chat_Repository]: https://github.com/firebase/codelab-friendlychat-android
[Firebase_Build_Friendly_Chat_Starter_Repository]: https://github.com/firebase/codelab-friendlychat-android/tree/master/build-android-start
[Firebase_Build_Friendly_Chat_Solution_Repository]: https://github.com/firebase/codelab-friendlychat-android/tree/master/build-android
