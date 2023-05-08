# PMRTest Android App


This is an Android app written in Kotlin that receives push notifications from a Python script. The app uses Firebase Cloud Messaging and the Realtime Database to receive and display push notifications.

## Table of Contents

- [Requirements](#requirements)
- [Firebase Console Setup](#firebase-console-setup)
- [Installation](#installation)
- [Usage](#usage)

## Requirements

You will need the following to use this code:

1. Android Studio installed on your computer
2. Your own `google-services.json` and `[project_name]-firebase-adminsdk.json` files, which you can download from the Firebase console

# Firebase Console Setup

Follow these steps to create a new project on the Firebase console, get the FCM private token, and download the necessary files:

## Create a new project and download `google-services.json`

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Sign in with your Google account if you haven't already.
3. Click on "Add project."
4. Enter a project name and follow the on-screen instructions to set up your project. You can leave the other settings as their defaults.
5. Once the project is created, you'll be redirected to the project dashboard.
6. From the left-side menu, click on the gear icon (⚙️) and select "Project settings."
7. In the "General" tab, scroll down to find the "Your apps" section.
8. Click on the Android icon to add an Android app to your project.
9. Fill in the "Android package name" and "App nickname" fields. The package name is `com.example.PMRTest`.
10. Click "Register app."
11. Download the `google-services.json` file by clicking on the "Download google-services.json" button. This file will be used in your Android project.
12. Click "Next" until you reach the "Firebase Console" screen. You can close this window since you already have the `google-services.json` file.

## Get the FCM private token (Server key)

1. In the left-side menu, click on "Cloud Messaging."
2. In the "Project credentials" section, you will find your FCM private token, also called the "Server key." Copy this key and keep it safe, as you'll need it to send push notifications from your Python script.

## Download `[project_name]-firebase-adminsdk.json`

1. In the left-side menu of your Firebase Console, click on "Realtime Database."
2. Click on "Create database" and follow the on-screen instructions to set up the database.
3. In the left-side menu, click on the gear icon (⚙️) and select "Project settings."
4. Go to the "Service accounts" tab.
5. Click on "Generate new private key" and then confirm by clicking on "Generate key."
6. A JSON file will be downloaded to your computer. This is your `[project_name]-firebase-adminsdk.json` file, which you'll use in your Python script for authentication purposes. Keep this file safe and secure, as it contains sensitive information.

Now you have both the `google-services.json` and `[project_name]-firebase-adminsdk.json` files required for your Android app and Python script.


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
