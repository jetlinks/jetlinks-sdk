package org.jetlinks.sdk.generator.java;

import com.alibaba.fastjson.JSON;
import org.jetlinks.sdk.generator.java.info.*;
import org.jetlinks.sdk.generator.java.info.base.PackageInfo;
import org.jetlinks.sdk.generator.java.rdb.RdbEntityClassHelper;
import org.junit.jupiter.api.Test;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityClassHelperTest {

    private List<ColumnInfo> createColumnInfos() {
        List<ColumnInfo> columnInfos = new ArrayList<>();

        {
            RdbColumnInfo columnInfo = new RdbColumnInfo();
            columnInfo.setTypeClass("String");
            RdbColumnInfo.ColumnSpec columnSpec = new RdbColumnInfo.ColumnSpec();
            columnSpec.setNullable(true);
            columnSpec.setLength("255");


            RdbColumnInfo.ColumnTypeSpec columnTypeSpec = new RdbColumnInfo.ColumnTypeSpec();
            columnTypeSpec.setJavaType("String");
            columnTypeSpec.setJdbcType(JDBCType.LONGNVARCHAR);

            ColumnInfo.SizeSpec sizeSpec = new ColumnInfo.SizeSpec();
            sizeSpec.setMax("20");
            sizeSpec.setMin("5");

            columnInfo.setSizeSpec(sizeSpec);
            columnInfo.setColumnSpec(columnSpec);
            columnInfo.setColumnTypeSpec(columnTypeSpec);
            columnInfo.setDefaultValue("enabled");
            columnInfo.setId("test");
            columnInfo.setName("测试");
            columnInfo.setNotnull(true);

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
            columnInfo.setMax("10");
            columnInfo.setMin("1");

            columnInfos.add(columnInfo);
        }

        return columnInfos;
    }

    private EntityInfo createEntityInfo() {
        RdbEntityInfo entityInfo = new RdbEntityInfo();
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
        List<ColumnInfo> columnInfos = createColumnInfos();
        EntityInfo entityInfo = createEntityInfo();
        helper.initClass(entityInfo);
        columnInfos.forEach(helper::addColumn);
        PackageInfo entityPackage = new PackageInfo(entityInfo.getClassPackage(),
                                                    Collections.singletonList(helper.getEntityClassInfo()));

        PackageJavaGenerator javaGenerator = new DefaultPackageJavaGenerator();
        List<GenerateResult> generate = javaGenerator.generate(entityPackage);
        System.out.println("generate：" + JSON.toJSONString(generate));

    }
}
