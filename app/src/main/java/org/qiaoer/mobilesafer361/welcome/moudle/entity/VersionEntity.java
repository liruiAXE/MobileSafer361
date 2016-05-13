package org.qiaoer.mobilesafer361.welcome.moudle.entity;

/**
 * Created by qiaoer on 16/5/13.
 */
public class VersionEntity {
    //服务器版本号
    public String versioncode;
    //版本描述
    public String description;
    //apk下载地址
    public String apkurl;

    @Override
    public String toString() {
        return "VersionEntity{" +
                "versioncode='" + versioncode + '\'' +
                ", description='" + description + '\'' +
                ", apkurl='" + apkurl + '\'' +
                '}';
    }
}
