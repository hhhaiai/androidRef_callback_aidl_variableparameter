package cn.ref.model;

/**
 * @Copyright © 2020 analysys Inc. All rights reserved.
 * @Description: 带反射接口
 * @Version: 1.0
 * @Create: Aug 6, 2020 3:11:31 PM
 * @author: sanbo
 */
public interface IKissBaby {
    public void kissEye(String hi);

    public void kissFace(String hi);
}


//class a{
//    private a(){
//
//        Proxy.newProxyInstance(ClassLoader.getSystemClassLoader()
//                ,new Class[]{IKissBaby.class}
//                ,new InvocationHandler(){
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });
//        IKissBaby K=   new IKissBaby(){
//
//            @Override
//            public void kissEye(String hi) {
//
//            }
//
//            @Override
//            public void kissFace(String hi) {
//
//            }
//        };
//    }
//}