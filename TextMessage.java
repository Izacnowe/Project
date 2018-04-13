package com.paradise.malariastressfighter.Health;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paradise.malariastressfighter.R;
import com.paradise.malariastressfighter.Health.TextMessage.*;

public class TextMessage extends AppCompatActivity {
EditText textMessage;
    EditText phoneText;
Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_message);
        textMessage =(EditText)findViewById(R.id.textMessa);
        phoneText =(EditText)findViewById(R.id.phone_text);
        sendBtn =(Button) findViewById(R.id.message_btn);

sendBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String phoneNumber= phoneText.getText().toString();
        String textMes= textMessage.getText().toString();

        if (phoneNumber.length()>0 && textMes.length()>0){
            sendMessage(phoneNumber,textMes);
        }
        else {
            Toast.makeText(getBaseContext(), "Please enter message", Toast.LENGTH_LONG).show();
        }
    }
});

    }
    private void sendMessage(String phoneNumber,String textMes){

        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textMes, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
        }catch ( Exception e){
            Toast.makeText(getApplicationContext(), "SMS not sent. Please try Again!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
