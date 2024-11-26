package org.jetlinks.sdk.generator.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
        cu.getPackageDeclaration()
          .ifPresent(packageDeclaration -> importsMap.put("classPackage", packageDeclaration.getNameAsString()));

        Optional<TypeDeclaration<?>> typeOptional = cu.getTypes().getFirst();
        if (typeOptional.isPresent()) {
            TypeDeclaration<?> typeDeclaration = typeOptional.get();
            if (typeDeclaration.isClassOrInterfaceDeclaration()) {
                ClassOrInterfaceDeclaration classType = typeDeclaration.asClassOrInterfaceDeclaration();
                return doParse(classType, importsMap);
            }
        }
        return ClassInfo.of();
    }

    /**
     * 解析类定义为类描述信息
     *
     * @param clazz      类定义
     * @param importsMap 导包Map
     * @return ClassInfo
     */
    private ClassInfo doParse(ClassOrInterfaceDeclaration clazz, Map<String, String> importsMap) {

        String classPackage = importsMap.get("classPackage");
        String className = clazz.getNameAsString();
        if (StringUtils.isNotBlank(classPackage)) {
            classPackage = String.join(".", classPackage, className);
        }

        //构建类信息
        ClassInfo classInfo = ClassInfo.of(className, classPackage);

        //获取类上注解信息
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(clazz.getAnnotations(), importsMap);

        //获取父类信息
        clazz.getExtendedTypes()
             .getFirst()
             .ifPresent(superType -> classInfo
                     .withSuperClass(TypeUtils.handleClassOrInterface(superType, importsMap)));

        // 获取接口信息
        List<ClassInfo> interfaceInfo = TypeUtils.handleClassOrInterface(clazz.getImplementedTypes(), importsMap);

        // 获取字段、内部类、方法信息
        List<FieldInfo> fieldInfos = null;
        List<MethodInfo> methodInfos = null;
        if (CollectionUtils.isNotEmpty(clazz.getMembers())) {
            fieldInfos = new ArrayList<>();
            methodInfos = new ArrayList<>();
            for (BodyDeclaration<?> member : clazz.getMembers()) {
                if (member.isFieldDeclaration()) {
                    fieldInfos.add(MembersUtils.handleFieldMember(member, importsMap));
                } else if (member.isMethodDeclaration()) {
                    methodInfos.add(MembersUtils.handleMethodMember(member, importsMap));
                } else if (member.isClassOrInterfaceDeclaration()) {
                    // 解析内部类
                    ClassInfo innerClass = doParse(member.asClassOrInterfaceDeclaration(), importsMap);
                    fieldInfos.add(FieldInfo.copyFrom(innerClass));
                }
            }
        }

        //获取类的访问修饰符
        List<Modifiers> modifiers = TypeUtils.handleModifier(clazz.getModifiers());

        //填充类各部分信息
        return classInfo
                .withAnnotations(annotationInfos)
                .withFields(fieldInfos)
                .withInterfaces(interfaceInfo)
                .withMethods(methodInfos)
                .withModifiers(modifiers);
    }
}



