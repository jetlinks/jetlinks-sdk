package org.jetlinks.sdk.generator.java.rdb;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.DefaultJavaGenerator;

import java.util.List;

public class DefaultRdbEntityJavaGenerator extends DefaultJavaGenerator implements RdbEntityJavaGenerator {

    DefaultRdbEntityJavaGenerator(String packageName, String className) {
        super(String.join(".", packageName, className));
    }

    @Override
    public RdbEntityJavaGenerator extendsClass(String clazz, Type... types) {
        ClassOrInterfaceType genericParentType = new ClassOrInterfaceType(clazz);
        genericParentType.setTypeArguments(types);
        this.clazz.addExtendedType(genericParentType);
        return this;
    }

    @Override
    public RdbEntityJavaGenerator addFieldWithAnnotation(String type, String name, List<AnnotationExpr> annotations, Modifier.Keyword... modifiers) {
        FieldDeclaration fieldDeclaration = clazz.addField(type, name, modifiers);
        annotations.forEach(fieldDeclaration::addAnnotation);
        return this;
    }

    @Override
    public RdbEntityJavaGenerator addClassAnnotation(AnnotationExpr annotation) {
        clazz.addAnnotation(annotation);
        return this;
    }
}
