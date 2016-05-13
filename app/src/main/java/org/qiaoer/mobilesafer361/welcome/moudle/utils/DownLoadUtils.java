package org.qiaoer.mobilesafer361.welcome.moudle.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by qiaoer on 16/5/13.
 */
public class DownLoadUtils {
    /**
     * 下载APK的方法
     *
     * @param url        要下载文件的路径
     * @param targetFile 下载文件的本地路径
     * @param myCallback 监听文件的下载状态
     */
    public void downapk(String url, String targetFile, final MyCallBack myCallback) {
        //创建HttpUtils对象
        HttpUtils httpUtils = new HttpUtils();
        //调用HttpUtils下载的方法下载指定文件
        httpUtils.download(url, targetFile, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                myCallback.onSuccess(responseInfo);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                myCallback.onFailure(e, s);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                myCallback.onLoading(total, current, isUploading);
            }
        });
    }

    /**
     * 接口,用于监听下载状态的接口
     */
    interface MyCallBack {
        //下载成功时调用
        void onSuccess(ResponseInfo<File> responseInfo);

        //下载失败时调用
        void onFailure(HttpException e, String s);

        //下载中调用
        void onLoading(long total, long current, boolean isUploading);
    }
}
