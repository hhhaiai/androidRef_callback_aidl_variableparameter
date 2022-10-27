package cn.ref;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import cn.ref.cases.AidlCases;
import cn.ref.cases.MyCases;
import cn.ref.cases.Variablle;
import cn.ref.utils.Logs;

public class MainActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        HiddenApiBypass.unseal(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnA:
                    Logs.i("============即将展示不回调普通调用==========");
                    MyCases.generalCallBack();
                    break;
                case R.id.btnB:
                    Logs.i("============即将展示反射调用================");
                    MyCases.ref();
                    break;
                case R.id.buttonC:
                    Logs.i("----------正常调用-------");
                    MyCases.oHo();
                    break;
                case R.id.buttonD:
                    Logs.i("----------反射调用-------");
                    MyCases.refOho();
                    break;
                case R.id.buttonE:
                    Logs.i("----------反射调用aidl-------");
                    AidlCases.run(MainActivity.this);
                    break;

                case R.id.buttonF:
                    Logs.i("---------可变参数测试------");
                    Variablle.run();
                    break;
                default:
                    break;
            }
        } catch (Throwable e) {
            Logs.e(e);
        }
    }


}