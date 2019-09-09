package com.coretronic.ccpclient.CCPUtils.Download;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Jones Lin on 2019-08-19.
 */
public class SilentInstall {

    public static boolean startInstall(String apkPath) {
        String TAG = SilentInstall.class.getSimpleName();
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        BufferedReader successStream = null;
        Process process = null;
        try {
            // 申请 su 權限
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());

            // 標準安裝：pm install 命令
            String command = "pm install -r " + apkPath + "\n";

            // 安裝至system/priv-app，成為系統app
//            dataOutputStream.writeBytes("mount -o rw,remount -t auto /system"+ "\n");
//            dataOutputStream.flush();
//            dataOutputStream.writeBytes("chmod 777 /system/priv-app" + "\n");
//            dataOutputStream.flush();
//            String command = "mv " + apkPath + " /system/priv-app" + "\n";

            dataOutputStream.write(command.getBytes(Charset.forName("UTF-8")));
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorMsg = new StringBuilder();
            String line;
            while ((line = errorStream.readLine()) != null) {
                errorMsg.append(line);
            }
            Log.d(TAG, "silent install error message: " + errorMsg);
            StringBuilder successMsg = new StringBuilder();
            successStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 讀取命令執行結果
            while ((line = successStream.readLine()) != null) {
                successMsg.append(line);
            }
            Log.d(TAG, "silent install success message: " + successMsg);
            // 如果執行結果中包含 Failure 字樣就認為是操作失敗，否則就認為安裝成功
            if (!(errorMsg.toString().contains("Failure") || successMsg.toString().contains("Failure"))) {
                result = true;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
        } finally {
            try {
                if (process != null) {
                    process.destroy();
                }
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
                if (successStream != null) {
                    successStream.close();
                }
            } catch (Exception e) {
                // ignored
            }
        }
        return result;
    }
}
