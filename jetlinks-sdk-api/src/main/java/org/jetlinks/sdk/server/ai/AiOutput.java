package org.jetlinks.sdk.server.ai;

import com.alibaba.fastjson.JSONObject;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.HeaderSupport;
import org.jetlinks.core.metadata.Jsonable;
import org.jetlinks.sdk.server.file.FileData;

import java.io.Externalizable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI相关命令的输出结果
 *
 * @author gyl
 * @author zhouhao
 * @see GenericAiOutput
 * @see AiCommand
 * @since 1.0.1
 */
public interface AiOutput<Self extends AiOutput<Self>> extends HeaderSupport<Self>, Externalizable, Jsonable {
    /**
     * 数据源ID
     *有关图像处理中用拉流id作为数据源ID
     */
    String getSourceId();


    /**
     * 获取数据ID
     */
    String getId();

    /**
     * 是否成功
     *
     * @return 是否成功
     */
    boolean isSuccess();

    /**
     * 错误码
     *
     * @return 错误码
     */
    String getErrorCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getErrorMessage();

    /**
     * 获取数据UTC时间戳,毫秒.
     *
     * @return 数据时间戳
     */
    long getTimestamp();

    /**
     * 获取AI输出的文件信息
     *
     * @return 文件列表
     */
    default List<? extends FileData> files() {
        return Collections.emptyList();
    }

    default JSONObject toJson() {
        return new JSONObject(toLightWeighMap());
    }

    /**
     * 获取为轻量数据，作用于统一存储等
     */
    default Map<String, Object> toLightWeighMap() {
        return FastBeanCopier.copy(this, new HashMap<>());
    }

    /**
     * 获取为平铺数据，作用于规则订阅等
     */
    default List<Map<String, Object>> flat() {
        return Collections.singletonList(toLightWeighMap());
    }

    /**
     * 获取为轻量平铺数据，作用于业务存储等
     */
    default List<Map<String, Object>> lightWeighFlat() {
        return flat();
    }


    /**
     * 释放资源
     */
    default void release() {
        List<? extends FileData> files = files();
        if (files != null) {
            for (FileData file : files) {
                file.release();
            }
        }

    }


}
