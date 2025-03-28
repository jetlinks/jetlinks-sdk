package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Disabled
public class JavaCodeGeneratorTest {

    @Test
    void testClass() {
        String classStr = "package org.jetlinks.pro.devtools.init;\n" +
                "\n" +
                "import io.swagger.v3.oas.annotations.media.Schema;\n" +
                "import lombok.Getter;\n" +
                "import lombok.Setter;\n" +
                "import org.jetlinks.sdk.generator.java.base.FieldInfo;\n" +
                "import org.springframework.boot.context.properties.ConfigurationProperties;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "@Getter\n" +
                "@Setter\n" +
                "@ConfigurationProperties(prefix = \"jetlinks.devtools.nexus\")\n" +
                "public class Test {\n" +
                "\n" +
                "    /**\n" +
                "     * 用户名\n" +
                "     */\n" +
                "    private String userName;\n" +
                "\n" +
                "    @Schema(description = \"mapParam参数\")\n" +
                "    private Map<String, Object> mapParam;\n" +
                "\n" +
                "    @Schema(description = \"fieldInfoParam参数\")\n" +
                "    private FieldInfo fieldInfoParam;\n" +
                "\n" +
                "    @Schema(description = \"booleanParam参数\")\n" +
                "    private Boolean booleanParam;\n" +
                "\n" +
                "    @Schema(description = \"integerParam参数\")\n" +
                "    private Integer integerParam;\n" +
                "\n" +
                "    @Schema(description = \"longParam参数\")\n" +
                "    private Long longParam;\n" +
                "\n" +
                "    @Schema(description = \"floatParam参数\")\n" +
                "    private Float floatParam;\n" +
                "\n" +
                "    @Schema(description = \"doubleParam参数\")\n" +
                "    private Double doubleParam;\n" +
                "\n" +
                "    @Schema(description = \"listStringParam参数\")\n" +
                "    private List<String> listStringParam;\n" +
                "\n" +
                "    @Schema(description = \"fieldInfoListParam参数\")\n" +
                "    private List<FieldInfo> fieldInfoListParam;\n" +
                "\n" +
                "}\n";
        JavaCodeParser parser = new DefaultJavaCodeParser();
        InputStream inputStream = new ByteArrayInputStream(classStr.getBytes());
        ClassInfo parse = parser.parse(inputStream);

        Assertions.assertNotNull(parse.getName());

        Assertions.assertDoesNotThrow(() -> JavaCodeGenerator
                .create(parse.getClassPackage(), parse.getName())
                .generate(parse));

        String generate = JavaCodeGenerator
                .create(parse.getClassPackage(), parse.getName())
                .generate(parse);

        System.out.println(generate);

    }

    @Test
    void testEnum() {
        String enumStr = "package org.jetlinks.pro.devtools.enums;\n" +
                "\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import org.hswebframework.web.dict.EnumDict;\n" +
                "\n" +
                "@Getter\n" +
                "@AllArgsConstructor\n" +
                "public enum ModuleType implements EnumDict<String> {\n" +
                "\n" +
                "    codeGenerate(\"代码生成器模板\"),\n" +
                "    frontendCodeGenerate(\"前端代码生成器模板\"),\n" +
                "    frontend(\"前端源码\"),\n" +
                "    backendMvn(\"后端maven\"),\n" +
                "    backend(\"后端源码\");\n" +
                "\n" +
                "\n" +
                "    private final String text;\n" +
                "\n" +
                "    @Override\n" +
                "    public String getValue() {\n" +
                "        return name();\n" +
                "    }\n" +
                "}\n";

        JavaCodeParser parser = new DefaultJavaCodeParser();
        InputStream inputStream = new ByteArrayInputStream(enumStr.getBytes());
        ClassInfo parse = parser.parse(inputStream);
        Assertions.assertNotNull(parse.getName());

        Assertions.assertDoesNotThrow(() -> JavaCodeGenerator
                .createEnum(parse.getClassPackage(), parse.getName())
                .generate(parse));

        String generate = JavaCodeGenerator
                .createEnum(parse.getClassPackage(), parse.getName())
                .generate(parse);

        System.out.println(generate);
    }

}
