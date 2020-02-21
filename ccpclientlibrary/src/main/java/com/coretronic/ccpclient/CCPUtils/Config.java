package com.coretronic.ccpclient.CCPUtils;
/**
 * Created by Jones Lin on 2019-08-08.
 */
public class Config {
    public static String ccpservicePackageName = "com.coretronic.ccpservice";
    public static String shadowPackageName = "com.coretronic.shadow";
    public static String apkDownloadSavePath = "/download/";
    public static String ccpserviceStartAction = "coretronic.intent.action.iot.service.START_BY_SHADOW";
    public static String REQUIRE_CCPSERVICE_VERSION = "1.7";
    public static String ccpserviceApkDownloadPath = "https://ftp.coretronic.com/dl/coretronicnote/ccpservice/ccpservice1.7.apk";
    public static String shadowStartAction = "coretronic.intent.action.shadow.start";
    public static String shadowApkDownloadPath = "https://ftp.coretronic.com/dl/coretronicnote/shadow/shadow.apk";
    public static Long ccpserviceApkDownloadRetryMillisecond = 6 * 1000L;
    public static boolean isBindService = false;
}
