package org.jetlinks.sdk.generator.java;

import com.alibaba.fastjson.JSON;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 测试时在 test/resources目录下创建 TestCodeParse.java文件
 */
@Disabled
public class JavaCodeParserTest {

    @Test
    void test() throws IOException {
        JavaCodeParser parser = new DefaultJavaCodeParser();
        File file = new File("src/test/resources/TestCodeParse.java");
        InputStream inputStream = Files.newInputStream(file.toPath());
        ClassInfo parse = parser.parse(inputStream);
        System.out.println("parser：" + JSON.toJSONString(parse));
    }
}
