package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;

import javax.annotation.Nullable;

/**
 * 文件数据信息
 *
 * @author zhouhao
 * @since 2.3
 */
public interface FileData {

    /**
     * 文件名
     *
     * @return 文件名
     */
    String name();

    /**
     * 文件内容
     *
     * @return 文件内容
     */
    @Nullable
    ByteBuf body();

    /**
     * 设置文件访问地址,通常用于设置文件上传后的访问地址
     *
     * @param url 文件地址
     */
    void setUrl(String url);

    /**
     * @return 获取文件访问地址
     */
    String getUrl();

    /**
     * 释放文件资源
     */
    void release();
}
