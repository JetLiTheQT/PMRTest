import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging
from datetime import datetime

# Change source for the adminsdk, youll find it in app/src/ within the github repo
cred = credentials.Certificate("C:\\Users\\ekkac\\AndroidStudioProjects\\PMRTest\\app\\src\\pmr-test-cd8d2-firebase-adminsdk-fo4jd-05259879c0.json") 
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

#Jet's Emulator
device_token = "dEopJtYDS1apTu3wAQ_BOY:APA91bFkR7rvI5VVELN-g3HuVVzeTI5DQq8Z8Axwm70CxUWwjY6m338J6VYOtqTLDug86JqwiDGoV5gH3CyV707B3G90Kubv7QLyJXTRANNUatAOutUyIYbbAi9_SgYE31F1jpczTH53"
#Jet's Phone
# device_token = "dVIDmjV4Qqi9xTTxDfGSVp:APA91bGrh33TtKXRV_k6otUeuKk1ew-3LdayQJ_6y4ZsLv_geZNMNGXsC_Jd9mHk9NPdv9LMuWvsYIwz83aaeXZYILr_CdQl5Dh-NG8fVe99iU0NB8VsEQpVTvevAjAKhGSJKE9AIRLt"
message = "Patient has fallen"
#Tested, only works on LAN to update UI..

send_push_notification(device_token, message)
