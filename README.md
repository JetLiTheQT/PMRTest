# PMRTest Android App


This is an Android app written in Kotlin that receives push notifications from a Python script. The app uses Firebase Cloud Messaging and the Realtime Database to receive and display push notifications.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Requirements

In order to use this code, you will need to download your own google-services.json and [project_name]-firebase-adminsdk.json files from the Firebase console.

## Installation

+ Clone the repository:
  +   git clone https://github.com/JetLiTheQT/PMRTest.git
+ Open the project in Android Studio
+ Add your google-services.json and [project_name]-firebase-adminsdk.json files to the app directory
+ Build and run the app on your device

## Usage

+ Open the app on your device
+ Send a push notification using the Python script included in the python directory:
  + Change the source for the adminsdk by replacing [DIRECTORY LOCATION FOR YOUR FIRE-ADMINSDK JSON] with the directory location for your [project_name]-firebase-adminsdk.json file. You'll find it in app/src/ within the GitHub repo.
  + Retrieve your FCM token by uncommenting the Retrieve_token() function in the LogScreen page of the app. You can find your FCM token in Android Studio's log. Copy the FCM token and replace [FCM TOKEN] with it in the test.py file.
+ The push notification should be received and displayed in the app
