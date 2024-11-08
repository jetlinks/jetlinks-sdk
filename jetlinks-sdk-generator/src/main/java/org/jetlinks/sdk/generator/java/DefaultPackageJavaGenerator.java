package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import org.jetlinks.sdk.generator.java.info.GenerateResult;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;
import org.jetlinks.sdk.generator.java.info.base.FieldInfo;
import org.jetlinks.sdk.generator.java.info.base.PackageInfo;
import org.jetlinks.sdk.generator.java.rdb.RdbEntityJavaGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPackageJavaGenerator implements PackageJavaGenerator {

    @Override
    public List<GenerateResult> generate(PackageInfo packageInfo) {
        List<GenerateResult> generateResults = new ArrayList<>();
        for (ClassInfo classInfo : packageInfo.getClasses()) {
            RdbEntityJavaGenerator javaGenerator = RdbEntityJavaGenerator.create(packageInfo.getName(), classInfo.getName());
            classInfo
                    .getInterfaces()
                    .forEach(item -> javaGenerator
                            .implement(item.getName())
                            .addImport(item.getPackagePath()));
            classInfo
                    .getAnnotations()
                    .forEach(item -> javaGenerator
                            .addClassAnnotation(item.getAnnotationExpr())
                            .addImport(item.getPackagePath()));

            Type[] genericsArray = classInfo
                    .getSuperClass()
                    .getGenerics()
                    .stream()
                    .map(TypeParameter::new)
                    .toArray(Type[]::new);

            javaGenerator.extendsClass(classInfo.getSuperClass()
                                                .getName(),
                                       genericsArray)
                         .addImport(classInfo.getSuperClass()
                                             .getPackagePath());
            for (FieldInfo field : classInfo.getFields()) {

                List<AnnotationExpr> annotations = field
                        .getAnnotations()
                        .stream()
                        .peek(annotationInfo -> javaGenerator.addImport(annotationInfo.getPackagePath()))
                        .map(AnnotationInfo::getAnnotationExpr)
                        .collect(Collectors.toList());
                javaGenerator.addFieldWithAnnotation(field.getTypeClass(),
                                                     field.getId(),
                                                     annotations,
                                                     field.getModifiers().toArray(new Modifier.Keyword[0]));
            }
            GenerateResult generateResult = GenerateResult.of(packageInfo.getName(),
                                                              classInfo.getName(),
                                                              javaGenerator.generate());
            generateResults.add(generateResult);
        }
        return generateResults;
    }
}
