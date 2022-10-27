package cn.ref.rl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.ref.utils.Logs;

/**
 * @Copyright © 2020 sanbo Inc. All rights reserved.
 * @Description: 回调函数处理器
 * @Version: 1.0
 * @Create: 2020/8/6 17:38
 * @author: sanbo
 */
public class Mhandler implements InvocationHandler {
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//        Logs.i(method.toString());
        String methodName = method.getName();
        if ("sayOver".equals(methodName)) {
            // 此处处理的方法是<code> public void sayOver(String text);</code>
            Logs.i("回调方法： " + method.getName() + ", Mhandler 接收到回调: [" + objects[0].toString() + "]");
            // 返回值是void,直接返回即可，如还反射调用，则会递归至爆栈
            return null;
        } else if ("kissEye".equals(methodName)) {

        } else if ("kissFace".equals(methodName)) {

        }
        return method.invoke(o, objects);
    }
}
