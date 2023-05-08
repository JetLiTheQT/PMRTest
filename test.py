import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging
from datetime import datetime

# Change source for the adminsdk, youll find it in app/src/ within the github repo
cred = credentials.Certificate("[DIRECTORY\\LOCATION\\FOR\\YOUR\\FIRE-ADMINSDK.JSON]") 
firebase_admin.initialize_app(cred)

def send_push_notification(token, message):
    timestamp = datetime.now().strftime("%Y-%m-%d at %I:%M%p")
    data = {
        'message': message,
    }

    message = messaging.Message(
        data=data,
        notification=messaging.Notification(
            title='Fall Alert',
            body=f"Patient has fallen at {timestamp}"
        ),
        token=token
    )

    response = messaging.send(message)
    print(f'Successfully sent message: {response}')

device_token = "[FCM TOKEN]"
#FCM Token is retrievable through the app after un-commenting out the Retrieve_token() function in the LogScreen page. You should find it output in Android Studio's Log. Input the device token above.
message = "Patient has fallen"
#Tested, only works on LAN to update UI..

send_push_notification(device_token, message)
