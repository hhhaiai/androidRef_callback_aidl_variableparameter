package cn.ref;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.ref.model.ICallback;
import cn.ref.model.RefCus;
import cn.ref.rl.Mhandler;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnA:
                Logs.i("============即将展示不回调普通调用==========");
                new RefCus().sayHello(" 手动调用接口 ", new ICallback() {
                    
                    @Override
                    public void sayOver(String text) {
                        System.out.println();
                        Logs.i("接收到回调: [" + text + "]");
                    }
                    
                });
                break;
            case R.id.btnB:
                Logs.i("============即将展示反射调用================");
                ref();
                break;
            default:
                break;
        }
    }
    
    
    private void ref() {
        try {
            String text = "反射调用一下...";
            Class<?> refCus = Class.forName("cn.ref.model.RefCus");
            Class<?> callback = Class.forName("cn.ref.model.ICallback");
            final Mhandler mh = new Mhandler();
            Object mObj = Proxy.newProxyInstance(getClassLoader(), new Class[]{callback}, mh);
            Method sayHello = refCus.getDeclaredMethod("sayHello", new Class[]{String.class, callback});
            sayHello.invoke(refCus.newInstance(), new Object[]{text, mObj});
        } catch (Exception e) {
            Logs.e(Log.getStackTraceString(e));
        }
        
    }
}