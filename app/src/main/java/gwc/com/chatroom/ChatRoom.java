package gwc.com.chatroom;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatRoom extends AppCompatActivity {
    //定义界面上的两个文本框
    EditText input;
    TextView show;
    //定义界面上的一个按钮
    Button send;
    Handler handler;
    //定义与服务器通信的子线程
    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        input = (EditText) findViewById(R.id.input);
        show = (TextView) findViewById(R.id.show);
        send = (Button) findViewById(R.id.send);
        Intent intent = getIntent();//获取Intent，目的是为了得到Intent传来的参数
        final String nameString = intent.getStringExtra("name");
        handler = new Handler() {
            public void handleMessage(Message msg) {
                //如果消息来自于子线程
                if (msg.what == 0x123) {
                    //将读取的内容追加显示在文本框中
                    show.append("\n"+msg.obj.toString());
                }
            }
        };
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //当用户单击Send按钮后，将用户输入的数据封装成Message然后发送给子线程的Handler
                    Message msg = new Message();
                    msg.what = 0x345;
                    msg.obj = nameString+":"+input.getText().toString();
                    clientThread.revHandler.sendMessage(msg);
                    //发送完毕后清空文本框
                    input.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
