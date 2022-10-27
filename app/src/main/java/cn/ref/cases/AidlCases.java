package cn.ref.cases;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import cn.ref.utils.Logs;

public class AidlCases {

    public static void run(Activity act) {
//        // 如在hook前调用，会出现异常---hook失败
//        test(act, "hook之前");
        ref(act);
        test(act, "hook之后");
    }

    private static void ref(Activity act) {
        try {
            //下面这一段的意思其实就是ServiceManager.getService("clipboard")
            //只不过ServiceManager这个类是@hide的
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            Logs.d("getService:" + getService);

            //取得ServiceManager里的原始的clipboard binder对象
            //一般来说这是一个Binder代理对象
            IBinder rawBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);

            Logs.d("rawBinder:" + rawBinder);

            //Hook掉这个Binder代理的queryLocalInterface 方法
            //然后在queryLocalInterface返回一个IInterface对象，hook掉我们感兴趣的方法即可
            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                    new Class<?>[]{IBinder.class},
                    new IClipboardHookBinderHandler(rawBinder));
            Logs.i("===>hookedBinder:" + hookedBinder);

            //放回ServiceManager中，替换掉原有的
            Field cacheField = serviceManager.getDeclaredField("sCache");
            Logs.d("cacheField:" + cacheField);
            cacheField.setAccessible(true);
            @SuppressWarnings({"unchecked"})
            Map<String, IBinder> cache = (Map<String, IBinder>) cacheField.get(null);
            Logs.d("cache:" + cache);
            cache.put(Context.CLIPBOARD_SERVICE, hookedBinder);
        } catch (Throwable e) {
            Logs.e(e);
        }
    }

    /**
     * 由于ServiceManager里面的sCache里面存储的 IBinder类型基本上都是BinderProxy, 因此, ServiceManager的使用者调用getService之后不会直接使用这个map,
     * 而是先将他使用asInterface转成需要的接口, asInterface函数的代码告诉我们, 它会先使用这个BinderPRoxy查询本进程是否有Binder对象
     * 如果有就使用本地的, 这里恰好就是一个hook点,我们让所有的查询都返回一个"本地Binder"对象, 当然,这是一个假象, 我们给它返回的Binder对象自然是符合要求的(要么是本地Binder,要么是Binder代理)
     * 只不过,我们对需要hook的API做了处理
     * <p/>
     * 这个类仅仅Hook掉这个关键的 queryLocalInterface 方法
     * @author weishu
     * @date 16/2/15
     */
    public static class IClipboardHookBinderHandler implements InvocationHandler {
        // 绝大部分情况下,这是一个BinderProxy对象
        // 只有当Service和我们在同一个进程的时候才是Binder本地对象
        // 这个基本不可能
        IBinder base;

        Class<?> stub;

        Class<?> iinterface;

        public IClipboardHookBinderHandler(IBinder base) {
            this.base = base;
            try {
                this.stub = Class.forName("android.content.IClipboard$Stub");
                this.iinterface = Class.forName("android.content.IClipboard");
            } catch (Throwable e) {
                Logs.e(e);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Logs.i("method:" + method);
            if ("queryLocalInterface".equals(method.getName())) {

                Logs.d("hook queryLocalInterface");

                // 这里直接返回真正被Hook掉的Service接口
                // 这里的 queryLocalInterface 就不是原本的意思了
                // 我们肯定不会真的返回一个本地接口, 因为我们接管了 asInterface方法的作用
                // 因此必须是一个完整的 asInterface 过的 IInterface对象, 既要处理本地对象,也要处理代理对象
                // 这只是一个Hook点而已, 它原始的含义已经被我们重定义了; 因为我们会永远确保这个方法不返回null
                // 让 IClipboard.Stub.asInterface 永远走到if语句的else分支里面
                return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),

                        // asInterface 的时候会检测是否是特定类型的接口然后进行强制转换
                        // 因此这里的动态代理生成的类型信息的类型必须是正确的

                        // 这里面Hook的是一个BinderProxy对象(Binder代理) (代理Binder的queryLocalInterface正常情况下是返回null)
                        // 因此, 正常情况下 在asInterface里面会由于BinderProxy的queryLocalInterface返回null导致系统创建一个匿名的代理对象, 这样我们就无法控制了
                        // 所以我们要伪造一个对象, 瞒过这个if检测, 使得系统把这个queryLocalInterface返回的对象透传给asInterface的返回值;
                        // 检测有两个要求, 其一: 非空, 其二, IXXInterface类型。
                        // 所以, 其实返回的对象不需要是Binder对象, 我们把它当作普通的对象Hook掉就ok(拦截这个对象里面对于IXXInterface相关方法的调用)
                        // tks  jeremyhe_cn@qq.com
                        new Class[]{this.iinterface},
                        new BinderHookHandler(base, stub));
            }

            Logs.d("method:" + method.getName());
            return method.invoke(base, args);
        }
    }

    public static class BinderHookHandler implements InvocationHandler {

        private static final String TAG = "BinderHookHandler";

        // 原始的Service对象 (IInterface)
        Object base;

        public BinderHookHandler(IBinder base, Class<?> stubClass) {
            try {
                Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
                // IClipboard.Stub.asInterface(base);
                this.base = asInterfaceMethod.invoke(null, base);
            } catch (Exception e) {
                throw new RuntimeException("hooked failed!");
            }
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 把剪切版的内容替换为 "you are hooked"
            if ("getPrimaryClip".equals(method.getName())) {
                Logs.d("hook getPrimaryClip");
                return ClipData.newPlainText(null, "you are hooked");
            }

            // 欺骗系统,使之认为剪切版上一直有内容
            if ("hasPrimaryClip".equals(method.getName())) {
                return true;
            }

            return method.invoke(base, args);
        }
    }



    private static String test(Activity act, String info) {

        //获取剪切板服务
        ClipboardManager cm = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
        //设置剪切板内容
        cm.setPrimaryClip(ClipData.newPlainText("key", "[" + info + "]set from code~"));
        //获取剪切板数据对象
        ClipData cd = cm.getPrimaryClip();
        String msg = cd.getItemAt(0).getText().toString();
        Logs.d("[" + info + "] msg:" + msg);
        return msg;
    }
}
