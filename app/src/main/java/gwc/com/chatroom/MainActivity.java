package gwc.com.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Eternity-Myth on 2018/1/10.
 */
public class MainActivity extends AppCompatActivity {
    EditText name;
    static EditText serverip;
    Button enter;
    Button contact;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        name = (EditText) findViewById(R.id.name);
        enter = (Button) findViewById(R.id.enter);
        contact = (Button) findViewById(R.id.contact);
        serverip = (EditText) findViewById(R.id.serverip);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入框是否为空
                check(name.getText().toString().trim(), serverip.getText().toString().trim());
            }
        });
        //点击跳转到发送短信页面
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    //实现判断输入框是否为空的函数
    public void check(String name, String serverip) {
        if (name.equals("") || serverip.equals(""))//判断输入的name是否为空
        {
            //若为空，用Toast出现提示
            Toast.makeText(MainActivity.this, "Error!Empty Name or IP!", Toast.LENGTH_SHORT).show();
        } else {
            Intent chatroom = new Intent(MainActivity.this, ChatRoom.class);//用Intent实现Activity的跳转
            //putExtra方法向其他Activity传参数
            chatroom.putExtra("name", name);
            chatroom.putExtra("serverip", serverip);
            startActivity(chatroom);//调用startActivity方法跳转
        }
    }
    //实现跳转到发送信息页面的函数
    private void sendSMS() {
        Uri smsToUri = Uri.parse("smsto:13590629980");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }
}

