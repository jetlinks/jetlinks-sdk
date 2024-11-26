package org.jetlinks.sdk.generator.java;

import com.alibaba.fastjson.JSON;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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

    @Test
    void testInnerClass() {
        String innerClass = "public class TestCodeParse  {\n" +
                "    public static class Test {\n" +
                "        @Schema(description = \"创建时间\", accessMode = Schema.AccessMode.READ_ONLY)\n" +
                "        private Long createTime;\n" +
                "\n" +
                "        @Schema(description = \"修改人ID\")\n" +
                "        private String modifierId;\n" +
                "\n" +
                "        @Schema(description = \"更新时间\")\n" +
                "        private Long modifyTime;\n" +
                "\n" +
                "        private Boolean isBooeal;\n" +
                "    }\n" +
                "}";
        JavaCodeParser parser = new DefaultJavaCodeParser();
        InputStream inputStream = new ByteArrayInputStream(innerClass.getBytes());
        ClassInfo parse = parser.parse(inputStream);
        System.out.println("parser：" + JSON.toJSONString(parse));
    }
}
