package cn.ref.cases;

import java.util.Arrays;

import cn.ref.MainActivity;
import cn.ref.utils.Logs;

public class Variablle {
    public static void run() {


        Logs.i("=========================================测试-方案======================================");
        /**
         * 两种可变参数，汇总可执行对中可变参数
         */
        ar(Variablle.class, MainActivity.class);
        ar(Variablle.class.getName(), MainActivity.class.getName());
        Logs.i("=========================================测试=方案======================================");
        ttr(Variablle.class, MainActivity.class);
        ttr(Variablle.class.getName(), MainActivity.class.getName());
        ttr(Variablle.class.getName(), MainActivity.class.getName(), Variablle.class, MainActivity.class);
    }

    private static void ar(String... names) {
        Logs.d("测试2 ----------------------ar\r\n接受参数String:" + Arrays.asList(names));
        cr(names, null);
    }



    private static void ar(Class... classes) {
        Logs.d("测试1 ----------------------ar\r\n接受参数Class:" + Arrays.asList(classes));
        cr(null, classes);
    }

    /**
     * 汇总指定类型的可变参数
     * @param names
     * @param classes
     */
    private static void cr(String[] names, Class[] classes) {

        Logs.i("多种可变参数的接收。。cr 接受两种参数。"
                + "\r\n\t\t\tClass:" + (classes == null ? "" : Arrays.asList(classes))
                + "\r\n\t\t\tString:" + (names == null ? "" : Arrays.asList(names))
        );
    }

    /**
     * 接收多种可变参数
     * @param objects
     */
    private static void ttr(Object... objects) {
        Logs.i("终极可变参数: " + (objects == null ? "" : Arrays.asList(objects)));
    }
}
