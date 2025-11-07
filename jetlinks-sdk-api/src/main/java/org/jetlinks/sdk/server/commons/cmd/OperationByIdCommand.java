package org.jetlinks.sdk.server.commons.cmd;


import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;

import java.util.List;
import java.util.function.Function;

/**
 * 根据ID进行相关操作命令
 *
 * @author zhouhao
 * @since 1.0
 */
public abstract class OperationByIdCommand<T, Self extends OperationByIdCommand<T, Self>> extends AbstractConvertCommand<T, Self> {

    //ID参数key
    public static final String PARAMETER_KEY_ID = "id";

    /**
     * 获取ID List
     *
     * @return List
     */
    @Schema(name = PARAMETER_KEY_ID, title = "ID")
    public List<Object> getIdList() {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY_ID));
    }

    /**
     * 获取ID List,自定义ID类型转换
     *
     * @param converter ID转换器
     * @param <ID>      ID类型
     * @return List
     */
    public <ID> List<ID> getIdList(Function<Object, ID> converter) {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY_ID), converter);
    }

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    public Self withId(String id) {
        writable().put(PARAMETER_KEY_ID, id);
        return castSelf();
    }

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    public Self withIdList(List<?> id) {
        return with(PARAMETER_KEY_ID, id);
    }

    /**
     * 获取单个字符类型ID
     *
     * @return ID
     */
    public String getId() {
        List<Object> list = getIdList();
        if (!list.isEmpty()) {
            return String.valueOf(list.get(0));
        }
        return null;
    }
}
