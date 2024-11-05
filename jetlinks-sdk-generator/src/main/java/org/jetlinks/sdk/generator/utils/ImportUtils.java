package org.jetlinks.sdk.generator.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.springframework.core.ResolvableType;

/**
 * @author gyl
 * @since 2.3
 */
public class ImportUtils {

    // TODO: 2024/11/5 处理使用处携带包名问题

    public static void importClass(CompilationUnit cu, ResolvableType type) {
        if (type != null) {
            cu.addImport(type.toClass());
            // 递归调用以获取所有泛型依赖
            for (ResolvableType generic : type.getGenerics()) {
                importClass(cu, generic);
            }
        }
    }

    public static void importClass(CompilationUnit cu, String type) {
        // TODO: 2024/11/5  处理泛型
    }

    public static void importClass(CompilationUnit cu, FieldDeclaration type) {
        // TODO: 2024/11/5
    }

    public static void importClass(CompilationUnit cu, MethodDeclaration type) {
        // TODO: 2024/11/5
    }


}
