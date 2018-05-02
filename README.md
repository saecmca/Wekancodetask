# Wekancodetask

In API, don't send raw value for password. Encrypt password value to SHA-256 hash value

Login Success:
After logging in store the data in local session
Allow the user to logout and login again (clear the local session)
Create a dot with the name from the user (apart from name hardcode other values as described in the sample request params)
On clicking the create dot button if there is active internet connection call the api and show the success response
If there is not active internet connection store it in local db and then make a background task service(Preferred  the bg service works in Oreo - Jobs service) to call the api once the internet is active and show a local push notification stating the dot is created
Your view should contain (Edit Text - for entering dot name, Button - to trigger create dot action, Logout button - To logging out of the app)

![ScreenShot](https://raw.github.com/saecmca/Wekancodetask/master/photo_2018-05-02_17-07-11.jpg)
![ScreenShot](https://raw.github.com/saecmca/Wekancodetask/master/photo_2018-05-02_17-06-58.jpg)

