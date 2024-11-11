package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.AnnotationProperty;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JavaGeneratorTest {


    @Test
    void test() {
        String java = JavaGenerator
            .create("org.jetlinks.test.Test")
            .implement(ResolvableType.forClass(Serializable.class))
            .comments("测试类",
                      "@author zhouhao")
            .addImport(List.class)
            .addMethod("setName", method -> {

                method.setType("Test");

                method
                    .addParameter(String.class, "name")
                    .createBody()
                    .addStatement("this.name=name;")
                    .addStatement("return this;")
                ;
            }).generate();

        assertNotNull(java);
        System.out.println(java);

    }

    @Test
    void testAnnotation() {
        ClassInfo classInfo = createClassInfo();
        String generate = JavaGenerator.create(String.join(".", classInfo.getClassPackage(), classInfo.getName()))
                                       .addClassAnnotation(classInfo)
                                       .generate();
        System.out.println(generate);
    }

    private ClassInfo createClassInfo() {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setName("Test");
        classInfo.setClassPackage("org.jetlinks.test");

        ClassInfo annotationClass1 = new ClassInfo();
        annotationClass1.setClassPackage("javax.validation.constraints");
        AnnotationInfo annotationInfo1 = new AnnotationInfo();
        annotationInfo1.setName("Size");
        annotationInfo1.setType(annotationClass1);

        List<AnnotationProperty> properties1 = new ArrayList<>();

        {
            ClassInfo propertyClass = new ClassInfo();
            propertyClass.setName("Integer");
            AnnotationProperty annotationProperty = new AnnotationProperty();
            annotationProperty.setName("max");
            annotationProperty.setType(propertyClass);
            annotationProperty.setDefaultValue("10");
            properties1.add(annotationProperty);
        }

        {
            ClassInfo propertyClass = new ClassInfo();
            propertyClass.setName("Integer");
            AnnotationProperty annotationProperty = new AnnotationProperty();
            annotationProperty.setName("min");
            annotationProperty.setType(propertyClass);
            annotationProperty.setDefaultValue("2");
            properties1.add(annotationProperty);
        }

        annotationInfo1.setProperties(properties1);

        ClassInfo annotationClass2 = new ClassInfo();
        annotationClass2.setClassPackage("javax.validation.constraints");
        AnnotationInfo annotationInfo2 = new AnnotationInfo();
        annotationInfo2.setName("Max");
        annotationInfo2.setType(annotationClass2);

        List<AnnotationProperty> properties2 = new ArrayList<>();
        {
            ClassInfo propertyClass = new ClassInfo();
            propertyClass.setName("Integer");
            AnnotationProperty annotationProperty = new AnnotationProperty();

            annotationProperty.setType(propertyClass);
            annotationProperty.setDefaultValue("10");
            properties2.add(annotationProperty);
        }
        annotationInfo2.setProperties(properties2);
        classInfo.setAnnotations(Arrays.asList(annotationInfo1, annotationInfo2));
        return classInfo;
    }
}