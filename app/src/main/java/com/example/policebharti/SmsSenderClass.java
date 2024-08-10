package com.example.policebharti;

import android.telephony.SmsManager;

public class SmsSenderClass {
    public void sendmassage(String mob,String msg){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(mob,null,msg,null,null);

    }
}
