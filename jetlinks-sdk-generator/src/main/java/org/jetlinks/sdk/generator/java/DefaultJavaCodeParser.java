package org.jetlinks.sdk.generator.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.generator.java.base.FieldInfo;
import org.jetlinks.sdk.generator.java.base.MethodInfo;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;
import org.jetlinks.sdk.generator.java.utils.AnnotationExpressionUtils;
import org.jetlinks.sdk.generator.java.utils.MembersUtils;
import org.jetlinks.sdk.generator.java.utils.TypeUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultJavaCodeParser implements JavaCodeParser {

    @Override
    public ClassInfo parse(InputStream inputStream) {
        CompilationUnit cu = StaticJavaParser.parse(inputStream);

        Map<String, String> importsMap = TypeUtils.handleImports(cu.getImports());

        Optional<TypeDeclaration<?>> typeOptional = cu.getTypes().getFirst();
        if (typeOptional.isPresent()) {
            TypeDeclaration<?> typeDeclaration = typeOptional.get();
            if (typeDeclaration.isClassOrInterfaceDeclaration()) {
                ClassOrInterfaceDeclaration classType = typeDeclaration.asClassOrInterfaceDeclaration();

                //构建类信息
                ClassInfo classInfo = ClassInfo.of(classType.getNameAsString());
                cu.getPackageDeclaration()
                  .ifPresent(packageDeclaration -> classInfo
                          .setClassPackage(packageDeclaration.getNameAsString()));


                //获取类上注解信息
                List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(classType.getAnnotations(), importsMap);

                //获取父类信息
                classType
                        .getExtendedTypes()
                        .getFirst()
                        .ifPresent(superType -> classInfo
                                .withSuperClass(TypeUtils.handleClassOrInterface(superType, importsMap)));

                // 获取接口信息
                List<ClassInfo> interfaceInfo = TypeUtils.handleClassOrInterface(classType.getImplementedTypes(), importsMap);

                // 获取字段和方法信息
                List<FieldInfo> fieldInfos = null;
                List<MethodInfo> methodInfos = null;
                if (CollectionUtils.isNotEmpty(classType.getMembers())) {
                    fieldInfos = new ArrayList<>();
                    methodInfos = new ArrayList<>();
                    for (BodyDeclaration<?> member : classType.getMembers()) {
                        if (member.isFieldDeclaration()) {
                            fieldInfos.add(MembersUtils.handleFieldMember(member, importsMap));
                        } else if (member.isMethodDeclaration()) {
                            methodInfos.add(MembersUtils.handleMethodMember(member, importsMap));
                        }
                    }
                }

                //获取类的访问修饰符
                List<Modifiers> modifiers = TypeUtils.handleModifier(classType.getModifiers());

                //填充类各部分信息
                return classInfo
                        .withAnnotations(annotationInfos)
                        .withFields(fieldInfos)
                        .withInterfaces(interfaceInfo)
                        .withMethods(methodInfos)
                        .withModifiers(modifiers);
            }
        }
        return ClassInfo.of();
    }


}
