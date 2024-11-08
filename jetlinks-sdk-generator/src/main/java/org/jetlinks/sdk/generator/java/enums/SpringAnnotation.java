package org.jetlinks.sdk.generator.java.enums;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;

import static org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant.SERVICE;

/**
 * Spring相关注解
 */
@Getter
@AllArgsConstructor
public enum SpringAnnotation implements EnumDict<String> {
    service("@Service注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new MarkerAnnotationExpr(new Name(SERVICE));
        }
    };

    private final String text;

    /**
     * 创建注解
     *
     * @param value 注解配置值
     * @return AnnotationExpr
     */
    public abstract AnnotationExpr createAnnotation(Object value);


    @Override
    public String getValue() {
        return name();
    }
}
