# Carworkz-Android-Offline-Assignment

Kotlin, Room Database, Retrofit, Glide

User need to Sign in with Github.

After successful signin github users list will be downloaded from github's API and stored into the local database.

If user has not signed in then login page will appear else user will be directly redirected to users page where list of users will appear from database if exist and updated else users list will be downloaded and stored into local database and update the last updated date and then shown on the recyclerview. 

If data in the local database is older than 1 hour then it will get new data from API and update into local database as well along with updatedAt date.

# ScreenShots

![screenshot_2018-09-12-17-00-34](https://user-images.githubusercontent.com/1027655/45422858-2b7dd900-b6af-11e8-94bd-fc4655e65f41.png)
![screenshot_2018-09-12-17-01-52](https://user-images.githubusercontent.com/1027655/45422862-2e78c980-b6af-11e8-9450-65f0b37fc1fd.png)
![screenshot_2018-09-12-17-02-10](https://user-images.githubusercontent.com/1027655/45422867-3173ba00-b6af-11e8-992a-86865717e248.png)
