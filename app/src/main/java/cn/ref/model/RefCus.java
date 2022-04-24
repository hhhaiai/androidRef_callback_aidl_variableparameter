package cn.ref.model;


import cn.ref.Logs;

/**
 * @Copyright © 2020 analysys Inc. All rights reserved.
 * @Description: 反射模型类
 * @Version: 1.0
 * @Create: Aug 6, 2020 3:11:16 PM
 * @author: sanbo
 */
public class RefCus {

    public RefCus() {
        Logs.i("RefCus 构造函数");
    }

    public void sayHello(String text, ICallback callback) {
        Logs.i("sayHello :[" + text + "] , will callback...");
        if (callback != null) {
            callback.sayOver(text);
        }
    }
}
