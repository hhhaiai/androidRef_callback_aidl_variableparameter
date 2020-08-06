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
                generalCallBack();
                break;
            case R.id.btnB:
                Logs.i("============即将展示反射调用================");
                ref();
                break;
            default:
                break;
        }
    }
    
    /**
     * 常规回调函数
     */
    private void generalCallBack() {
        new RefCus().sayHello(" 手动调用接口 ", new ICallback() {
            
            //该方法反射时，处理写在Mhandler中
            @Override
            public void sayOver(String text) {
                System.out.println();
                Logs.i("接收到回调: [" + text + "]");
            }
            
        });
    }
    
    
    /**
     * 反射实现下回调函数
     */
    private void ref() {
        try {
            String text = "反射调用一下...";
            Class<?> refCus = Class.forName("cn.ref.model.RefCus");
            Class<?> callback = Class.forName("cn.ref.model.ICallback");
            final Mhandler mh = new Mhandler();
            /**
             * newProxyInstance( ClassLoader loader, Class<?>[] interfaces,  InvocationHandler h) ：
             * ClassLoader loader：它是类加载器类型,Class对象就可以获取到ClassLoader对象；
             * Class[] interfaces：指定newProxyInstance()方法返回的对象要实现哪些接口，可以指定多个接口;
             * InvocationHandler h： 回调函数的处理器，如返回值为空，则处理器中返回null即可
             *
             */
            Object mObj = Proxy.newProxyInstance(getClassLoader(), new Class[]{callback}, mh);
            Method sayHello = refCus.getDeclaredMethod("sayHello", new Class[]{String.class, callback});
            sayHello.invoke(refCus.newInstance(), new Object[]{text, mObj});
        } catch (Exception e) {
            Logs.e(Log.getStackTraceString(e));
        }
        
    }
}