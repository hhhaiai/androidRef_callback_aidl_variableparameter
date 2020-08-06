package cn.ref.rl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.ref.Logs;

public class Mhandler implements InvocationHandler {
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//        Logs.i(method.toString());
        if ("sayOver".equals(method.getName())) {
            Logs.i("回调方法： " + method.getName() + ", Mhandler 接收到回调: [" + objects[0].toString() + "]");
            return null;
        }
        return method.invoke(o, objects);
    }
}
