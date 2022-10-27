package cn.ref.model;

import cn.ref.utils.Logs;

/**
 * @Copyright © 2020 analysys Inc. All rights reserved.
 * @Description: 反射模型类
 * @Version: 1.0
 * @Create: Aug 6, 2020 3:11:16 PM
 * @author: sanbo
 */
public class ErDogModel {

    /*****************私有变量区*****************/
    private String mName = "默认";
    private static int age = 0;
    private static final String SEX = "none";

    /*****************公有变量区*****************/
    public String PUB_KEY_A = "a";
    public static int PUB_STATIC_B = -1;
    public static final String VERSION = "v1.0.1";

    /*****************构造区*****************/
    public ErDogModel() {
        Logs.i("ErDogModel() 构造函数");
    }

    public ErDogModel(String name) {
        Logs.i("ErDogModel(" + name + ") 构造函数");
    }

    /*****************部分方法区*****************/
    //私有变量
    public String getName() {
        return mName;
    }

    public void setName(String tempName) {
        this.mName = tempName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int tempAge) {
        this.age = tempAge;
    }

    public String getSex() {
        return SEX;
    }

    //公有变量
    private String getA() {
        return PUB_KEY_A;
    }

    private void setA(String tempA) {
        this.PUB_KEY_A = tempA;
    }

    private static void setB(int tempB) {
        PUB_STATIC_B = tempB;
    }

    private static int getB() {
        return PUB_STATIC_B;
    }

    private static final String getVersion() {
        return VERSION;
    }

    /*****************接口回调区*****************/
    public void sayHello(String text, ICallback callback) {
        Logs.i("sayHello :[" + text + "] , will callback...callback：" + callback);
        if (callback != null) {
            callback.sayOver(text);
        }
    }

    public void ikk(String text, IKissBaby callback) {
        if (callback != null) {
            callback.kissEye("kiss eys=====" + text);
            callback.kissFace("kill face=====" + text);
        }
    }

    @Override
    public String toString() {
        return "ErDogModel{" +
                "mName='" + mName + '\'' +
                ", PUB_KEY_A='" + PUB_KEY_A + '\'' +
                '}';
    }
}
