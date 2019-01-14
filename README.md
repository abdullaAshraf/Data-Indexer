# Data-Indexer
Android app to keep and manage a full index of all the user data of various categories on different hard disks or devices.
# Project Status
This project is currently in development. Users can add games using text fields and image load and crop for cover, or in case of internet connection type the game title and the data will be filled automatically using IGDB API. also users can add disks or devices to indicate where every game in located.
support for movies, songs , books , programs and other will be added soon.
## Screen shots
![alt text](https://i.imgur.com/RFR2EXc.png?2)
![alt text](https://i.imgur.com/YaIyjqd.png?2)
![alt text](https://i.imgur.com/s9fOEPN.png?2)
![alt text](https://i.imgur.com/6EpTd0v.jpg?2)
![alt text](https://i.imgur.com/sG77pVS.png)
![alt text](https://i.imgur.com/7j75igk.png)
# Reflection
This is a personal project to practise using new technologies learned like `Kotlin` and `MVVM`.

I got the idea to develop this Data Indexer app because for people like me with many external hard disks and multiple devices it is hard to keep track of what you have and where is it, also including adtional data about the game or the movie make it far easier to pick what you want and save a lot of googling time.

# Implementation
The idea of the app is to allow users to add data items (games, movies, songs, etc..) and store it using `Room`, to add item the user has to fill some fields using `AlertDialog` , `DatePickerDialog` and `Android-Image-Cropper`,When the user start typing the game title the app will suggest relative game titles by sending a request using `Volley` to IGDB API, once a suggestion is selected the app can fill most of the data for the user including loading the image using `Glide`.
The data is saved in a Room data base.At the `ListGameFragment` Using `LiveData` and `Room` i will listen for changes in the database and submit the latest list from the database to the `RecyclerView`using `ListAdapter`.The whole application is written in `Kotlin` 1.3.




