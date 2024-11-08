package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.info.GenerateResult;
import org.jetlinks.sdk.generator.java.info.base.PackageInfo;

import java.util.List;

/**
 * 通过类的描述信息生成java类
 */
public interface PackageInfoJavaGenerator {

    /**
     * 通过描述信息生成java类数据
     *
     * @param packageInfo 类的描述信息
     * @return List<GenerateResult>
     */
    List<GenerateResult> generate(PackageInfo packageInfo);

}
