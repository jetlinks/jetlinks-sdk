package org.jetlinks.sdk.generator.function;

import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.generator.GenerateClass;
import org.jetlinks.sdk.generator.java.JavaGenerator;

/**
 * 方法定义代码生成器，可以生成对应接口类及实现类
 *
 * @author zhouhao
 * @since 1.0.1
 */
public interface FunctionApiJavaGenerator extends JavaGenerator {

    static FunctionApiJavaGenerator create(String className) {
        // TODO: 2024/11/4 创建默认实现
        return null;
    }

    /**
     * 添加方法
     *
     * @param metadata  方法定义
     * @param commandId 命令id,为空不注册为命令
     * @return
     */
    FunctionApiJavaGenerator addMethod(FunctionMetadata metadata, String commandId);

    GenerateClass generatesImpl(String packageName);

}
