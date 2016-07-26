package com.example.upproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.upproject.ui.main.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by cjs on 2016/3/7.
 */
public class ConnectServerWithSocket extends Thread {
    private String sendtoserver = null;

    private String replay = null;

    private Handler mhandler;
    // cjs creat this to test git

    public void setStr(String str) {
        this.sendtoserver = str;
    }

    public String getReplayfromserver() {
        if(replay!=null&&replay.length()!=0)
        {
            replay = replay.substring(4);
            return replay;
        }
        return null;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("192.168.0.101", 3490);
            //得到读写流
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                //得到输入流
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //内容的发送
                String content = sendtoserver;
                pw.write(content);
                pw.flush();
                socket.shutdownOutput();
                //接受服务器响应
                String replayfromserver = null;
                while (!((replayfromserver = br.readLine()) == null)) {
                    replay = replay+replayfromserver;
                //关闭流
                br.close();
                is.close();
                pw.close();
                os.close();
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        super.run();
    }


}
