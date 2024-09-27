package org.jetlinks.sdk.server.ai;

import org.jetlinks.core.metadata.PropertyMetadata;

import java.io.Externalizable;
import java.util.List;
import java.util.Map;

/**
 * @author gyl
 * @since 2.3
 */
public interface AiOutput extends Externalizable {

    /**
     * 获取数据ID
     */
    String getOutputId();

    /**
     * 提供数据格式
     */
    List<PropertyMetadata> getMetadata();

    /**
     * 提供持久化数据
     */
    List<Map<String, Object>> getDataMap();

    /**
     * 提供持久化数据格式
     */
    List<PropertyMetadata> getDataMapMetadata();

    /**
     * 提供规则处理格式
     */
    List<Map<String, Object>> getRuleMap();

    /**
     * 提供规则处理数据格式
     */
    List<PropertyMetadata> getRuleMapMetadata();


}
