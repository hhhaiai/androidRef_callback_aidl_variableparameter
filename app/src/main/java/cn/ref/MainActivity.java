package cn.ref;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import cn.ref.model.ErDogModel;
import cn.ref.model.ICallback;
import cn.ref.model.RefCus;
import cn.ref.rl.Mhandler;

public class MainActivity extends Activity {

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
            case R.id.buttonC:
                Logs.i("----------正常调用-------");
                oHo();
                break;
            case R.id.buttonD:
                Logs.i("----------反射调用-------");
                try {
                    refOho();
                } catch (Throwable e) {
                    Logs.e(Log.getStackTraceString(e));
                }
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

//            ICallback temp=new ICallback() {
//                @Override
//                public void sayOver(String text) {
//                    Logs.i("非空构造测试2--sayOver:" + text);
//                }
//            };
//
//            nos.sayHello("非空构造测试2",temp );

            // refCus.newInstance() ==等价于===>  RefCus fc= new RefCus();
            //  refCus.getDeclaredMethod("sayHello" ==等价于===>  fc.sayhello 反射发现 ---可以理解成安全机制，有才能调用
            // invoke===>  fc.sayhello("xxx,new ICallback() {.....})  ---- 真正调用
            Method sayHello = refCus.getDeclaredMethod("sayHello", new Class[]{String.class, callback});
            if ( sayHello!=null){
                sayHello.invoke(refCus.newInstance(), new Object[]{text, mObj});
            }
        } catch (Exception e) {
            Logs.e(Log.getStackTraceString(e));
        }
    }
/***************************************************************************************/
    /**
     * 正常对象的调用
     */
    private void oHo() {

        /////
        ErDogModel mode = new ErDogModel();
        mode.setAge(10);
        mode.setName("Qing");
        // 获取
        Logs.i("空构造获取测试:"
                + "\r\nname(已设置):" + mode.getName()
                + "\r\nage(已设置):" + mode.getAge()
                + "\r\nsax(未设置):" + mode.getSex()
        );
        mode.sayHello("空构造测试", new ICallback() {
            @Override
            public void sayOver(String text) {
                Logs.i("空构造测试--sayOver:" + text);
            }
        });
        //--> mode.clear();
        ////////////
        ErDogModel nos = new ErDogModel("-------Changsha-----");
        nos.setAge(66);
        Logs.i("非空构造获取测试:"
                + "\r\nname(已设置):" + nos.getName()
                + "\r\nage(已设置):" + nos.getAge()
                + "\r\nsax(未设置):" + nos.getSex()
        );
        nos.sayHello("非空构造测试", new ICallback() {
            @Override
            public void sayOver(String text) {
                Logs.i("非空构造测试--sayOver:" + text);
            }
        });

        ICallback temp=new ICallback() {
            @Override
            public void sayOver(String text) {
                Logs.i("非空构造测试2--sayOver:" + text);
            }
        };

        nos.sayHello("非空构造测试2",temp );
        ////////////////////////
    }

    /**
     * 发射调用
     */
    private void refOho() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        //
//        Class egMode = Class.forName(ErDogModel.class.getName());
//        // 构造函数
//      Object model=  egMode.newInstance();
//      Logs.i(model.toString());

//        ////////////////////////////////获取系列///////////////////////
////        Method[] ms = egMode.getDeclaredMethods();
//        Method[] ms = egMode.getMethods();
//        for (int i = 0; i < ms.length; i++) {
//            Method m = ms[i];
//            Logs.i("方法" + m.toString());
//        }



        ///
//        Field[] fs = egMode.getDeclaredFields();
//        for (int i = 0; i < fs.length; i++) {
//            Field f = fs[i];
//            Logs.i("变量:" + f.toString());
//            Logs.i("----isAccessible------>"+ f.isAccessible());
//            Logs.i("----getModifiers------>"+ f.getModifiers());
//            Logs.i("----isSynthetic------>"+ f.isSynthetic());
//            Logs.i("----isEnumConstant------>"+ f.isEnumConstant());
//        }
//
//        Field sexF = egMode.getDeclaredField("SEX");
//        if (sexF != null) {
//            Logs.i("----isAccessible------>"+ sexF.isAccessible());
//            Logs.i("----getModifiers------>"+ sexF.getModifiers());
////            sexF.setAccessible(true);
////            //  Logs.i("sex:" + sexF);
////            Object o = sexF.get(null);
////            if (o != null) {
////                Logs.i("sex value:" + o);
////            }
//        }


        Class egMode = Class.forName(ErDogModel.class.getName());
        // 构造函数
        Object model=  egMode.newInstance();

        Object proxys= Proxy.newProxyInstance(getClassLoader(),new Class[]{Class.forName("cn.ref.model.IKissBaby")},new Mhandler());
        Method m =  egMode.getMethod("ikk",new Class[]{String.class,Class.forName("cn.ref.model.IKissBaby")});
       Logs.i("proxys:"+proxys);
       Logs.i("m:"+m);



    }

    // support android and java
    private static void setFinalFieldReadable(Field field, int modify) throws NoSuchFieldException, IllegalAccessException {
        if (Modifier.isFinal(modify)) {
            Field modifiersField = getField("modifiers");
            if (modifiersField == null) {
                modifiersField = getField("accessFlags");
            }
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modify & ~Modifier.FINAL);
        }
    }

    private static Field getField(String accessFlags) {
        try {
            return Field.class.getDeclaredField(accessFlags);
        } catch (Throwable e) {
        }
        return null;
    }


}