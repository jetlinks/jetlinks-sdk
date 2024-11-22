package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.base.PackageInfo;

import java.io.InputStream;

public interface JavaCodeParser {

    /**
     * 基于文件流，解析java文件
     *
     * @param inputStream java文件输入流
     * @return PackageInfo
     */
    PackageInfo parse(InputStream inputStream);
}
