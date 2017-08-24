package com.example.administrator.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        iniViews();
    }

    private void iniViews() {
        btn = (Button) findViewById(R.id.btn_call);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
            }
        });
    }

    private void permission(){

        ArrayList<String> list=new ArrayList<>();
        for (int i = 0; i <Permission.PHONE.length ; i++) {
            list.add(Permission.PHONE[i]);
        }
        for (int j= 0; j <Permission.CAMERA.length ; j++) {
            list.add(Permission.CAMERA[j]);
        }
        requestRunPermisssion(list.toArray(new String[list.size()]), new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                Toast.makeText(MainActivity.this, "所有权限都授权了，可以搞事情了", Toast.LENGTH_SHORT).show();
                //我们可以执行打电话的逻辑
                call();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
//                for(String permission : deniedPermission){
//                    Toast.makeText(MainActivity.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
//                }
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("需要开启权限才能使用此功能")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //引导用户到设置中去进行设置
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getPackageName(), null));
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        });
    }

    public void call(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri uri = Uri.parse("tel:" + "10086");
            intent.setData(uri);
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

}
