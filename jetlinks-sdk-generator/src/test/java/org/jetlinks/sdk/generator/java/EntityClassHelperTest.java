package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import org.jetlinks.sdk.generator.java.info.BaseColumnInfo;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.info.RdbColumnInfo;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;
import org.jetlinks.sdk.generator.java.info.base.FieldInfo;
import org.jetlinks.sdk.generator.java.info.base.PackageInfo;
import org.jetlinks.sdk.generator.java.rdb.RdbEntityClassHelper;
import org.jetlinks.sdk.generator.java.rdb.RdbEntityJavaGenerator;
import org.junit.jupiter.api.Test;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassHelperTest {

    private List<BaseColumnInfo> createColumnInfos() {
        List<BaseColumnInfo> columnInfos = new ArrayList<>();

        {
            RdbColumnInfo columnInfo = new RdbColumnInfo();
            columnInfo.setTypeClass("String");
            RdbColumnInfo.ColumnSpec columnSpec = new RdbColumnInfo.ColumnSpec();
            columnSpec.setNullable(true);
            columnSpec.setLength("255");


            RdbColumnInfo.ColumnTypeSpec columnTypeSpec = new RdbColumnInfo.ColumnTypeSpec();
            columnTypeSpec.setJavaType("String");
            columnTypeSpec.setJdbcType(JDBCType.LONGNVARCHAR);


            columnInfo.setColumnSpec(columnSpec);
            columnInfo.setColumnTypeSpec(columnTypeSpec);
            columnInfo.setDefaultValue("enabled");
            columnInfo.setId("test");
            columnInfo.setName("测试");

            columnInfos.add(columnInfo);
        }

        {
            RdbColumnInfo columnInfo = new RdbColumnInfo();
            columnInfo.setTypeClass("String");
            RdbColumnInfo.ColumnSpec columnSpec = new RdbColumnInfo.ColumnSpec();
            columnSpec.setNullable(true);
            columnSpec.setLength("255");


            RdbColumnInfo.ColumnTypeSpec columnTypeSpec = new RdbColumnInfo.ColumnTypeSpec();
            columnTypeSpec.setJavaType("String");
            columnTypeSpec.setJdbcType(JDBCType.LONGNVARCHAR);


            columnInfo.setColumnSpec(columnSpec);
            columnInfo.setColumnTypeSpec(columnTypeSpec);
            columnInfo.setDefaultValue("enabled");
            columnInfo.setId("testColumn");
            columnInfo.setName("测试第二个字段");

            columnInfos.add(columnInfo);
        }

        return columnInfos;
    }

    private EntityInfo createEntityInfo() {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setClassPackage("org.jetlinks.test");
        entityInfo.setClassSimpleName("Test");
        entityInfo.setTableName("s_student");
        entityInfo.setName("测试");
        entityInfo.setEnabledEntityEvent(true);
        entityInfo.setRecordModifier(true);

        return entityInfo;
    }

    @Test
    void testGenerate() {
        EntityClassHelper helper = new RdbEntityClassHelper();
        List<BaseColumnInfo> columnInfos = createColumnInfos();
        EntityInfo entityInfo = createEntityInfo();
        helper.initClass(entityInfo);
        columnInfos.forEach(helper::addColumn);
        PackageInfo entityPackage = new PackageInfo(entityInfo.getClassPackage(),
                                                    Collections.singletonList(helper.getEntityClassInfo()));
        for (ClassInfo aClass : entityPackage.getClasses()) {
            RdbEntityJavaGenerator javaGenerator = RdbEntityJavaGenerator.create(entityPackage.getName(), aClass.getName());
            aClass
                    .getInterfaces()
                    .forEach(item -> javaGenerator
                            .implement(item.getName())
                            .addImport(item.getPackagePath()));
            aClass
                    .getAnnotations()
                    .forEach(item -> javaGenerator
                            .addClassAnnotation(item.getAnnotationExpr())
                            .addImport(item.getPackagePath()));
            

            Type[] genericsArray = aClass
                    .getSuperClass()
                    .getGenerics()
                    .stream()
                    .map(TypeParameter::new)
                    .toArray(Type[]::new);

            javaGenerator.extendsClass(aClass.getSuperClass()
                                             .getName(),
                                       genericsArray)
                         .addImport(aClass.getSuperClass()
                                          .getPackagePath());
            for (FieldInfo field : aClass.getFields()) {

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
            String generate = javaGenerator.generate();
            System.out.println(generate);
        }
    }
}
