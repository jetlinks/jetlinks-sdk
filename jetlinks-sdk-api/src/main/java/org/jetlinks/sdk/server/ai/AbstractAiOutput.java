package org.jetlinks.sdk.server.ai;

import lombok.Getter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.hswebframework.web.id.IDGenerator;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.utils.MetadataUtils;
import org.jetlinks.sdk.server.ai.cv.AiCommandResult;
import org.springframework.core.ResolvableType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gyl
 * @since 2.3
 */
public abstract class AbstractAiOutput<SELF extends AiCommandResult<SELF>> extends GenericHeaderSupport<SELF> implements AiOutput {


    @Getter
    private String outputId = IDGenerator.RANDOM.generate();

    /**
     * 自动获取注解定义的属性信息
     * <pre>{@code @Schema(title = "属性名")}</pre>
     */
    @Override
    public List<PropertyMetadata> getMetadata() {
        return getPropertyMetadata(getClass());
    }

    @Override
    public List<Map<String, Object>> getDataMap() {
        Map<String, Object> copy = FastBeanCopier.copy(this, new HashMap<>());
        return Collections.singletonList(copy);
    }

    @Override
    public List<PropertyMetadata> getDataMapMetadata() {
        return getMetadata();
    }

    @Override
    public List<Map<String, Object>> getRuleMap() {
        return getDataMap();
    }

    @Override
    public List<PropertyMetadata> getRuleMapMetadata() {
        return getMetadata();
    }


    protected static List<PropertyMetadata> getPropertyMetadata(Class<?> aclass) {
        DataType dataType = MetadataUtils.parseType(ResolvableType.forType(aclass));
        if (dataType instanceof ObjectType) {
            return ((ObjectType) dataType).getProperties();
        }
        return Collections.emptyList();
    }

}
