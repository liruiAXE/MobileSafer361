package org.qiaoer.mobilesafer361.welcome.moudle.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.qiaoer.mobilesafer361.HomeActivity;
import org.qiaoer.mobilesafer361.R;
import org.qiaoer.mobilesafer361.welcome.moudle.entity.VersionEntity;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiaoer on 16/5/13.
 */
public class VersionUpdateUtils {
    private static final int MESSAGE_NET_ERROR = 101;
    private static final int MESSAGE_IO_ERROR = 102;
    private static final int MESSAGE_JSON_ERROR = 103;
    private static final int MESSAGE_SHOW_DIALOG = 104;
    protected static final int MESSAGE_ENTERHOME = 105;

    private String mVersion;    //本地版本号
    private Activity mContext;
    private VersionEntity mVersionEntity;
    private ProgressDialog mProgressDialog;

    public VersionUpdateUtils(String version, Activity activity) {
        this.mVersion = version;
        this.mContext = activity;
    }

    /**
     * 用于更新UI
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NET_ERROR:
                    Toast.makeText(mContext, "IO异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_IO_ERROR:
                    Toast.makeText(mContext, "IO异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_JSON_ERROR:
                    Toast.makeText(mContext, "JSON解析异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_SHOW_DIALOG:
                    showUpdateDialog(mVersionEntity);
                    break;
                case MESSAGE_ENTERHOME:
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                    mContext.finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取服务器版本号
     */
    public void getCloudVersion() {
        HttpClient httpClient = new DefaultHttpClient();
        //连接超时
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
        //请求超时
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5000);
        HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/updateinfo.html");
        try {
            Log.d("getCloudVersion", "0");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Log.d("getCloudVersion", "001");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {//请求和响应都成功了
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.d("getCloudVersion", "1");
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                Log.d("getCloudVersion", "2");
                //创建JSONObject对象
                JSONObject jsonObject = new JSONObject(result);
                Log.d("getCloudVersion", "3");
                mVersionEntity = new VersionEntity();
                mVersionEntity.versioncode = jsonObject.getString("code");
                mVersionEntity.description = jsonObject.getString("des");
                mVersionEntity.apkurl = jsonObject.getString("apkurl");

                Log.d("mVersionEntity", String.valueOf(mVersionEntity));
                if (!mVersion.equals(mVersionEntity.versioncode)) {//版本号不一致,显示升级对话框
                    mHandler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
                } else {//版本号一致,直接进入主界面
                    mHandler.sendEmptyMessage(MESSAGE_ENTERHOME);
                }
            }
        } catch (ClientProtocolException e) {
            mHandler.sendEmptyMessage(MESSAGE_NET_ERROR);
            e.printStackTrace();
        } catch (IOException e) {
            mHandler.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        } catch (JSONException e) {
            mHandler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * 弹出更新提示对话框
     *
     * @param versionEntity
     */
    private void showUpdateDialog(VersionEntity versionEntity) {
        //创建dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本:" + versionEntity.versioncode);//设置标题
        //根据服务器返回描述,设置升级描述信息
        builder.setMessage(versionEntity.description);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initProgressDialog();
                downloadNewApk(mVersionEntity.apkurl);
            }
        });

        builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();
            }
        });
        builder.show();

    }

    /**
     * 下载新版本
     *
     * @param apkurl
     */
    private void downloadNewApk(String apkurl) {
        DownLoadUtils downLoadUtils = new DownLoadUtils();
        String targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "mobilesafer3612.0.apk";
        downLoadUtils.downapk(apkurl, targetPath, new DownLoadUtils.MyCallBack() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                mProgressDialog.dismiss();
                MyUtils.installAPK(mContext);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                mProgressDialog.setMessage("下载失败");
                mProgressDialog.dismiss();
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                enterHome();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                mProgressDialog.setMessage("正在下载...");
                mProgressDialog.setMax((int) total);
                mProgressDialog.setProgress((int) current);
            }
        });
    }


    /**
     * 初始化进度条对话框
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("准备下载...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }


    /**
     * 进入主页
     */
    private void enterHome() {
        mHandler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME, 2000);
    }


}
