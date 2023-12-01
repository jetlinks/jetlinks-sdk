package org.jetlinks.sdk.server.handler;

import lombok.Getter;
import org.hswebframework.web.crud.events.*;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;

import java.util.function.Function;

/**
 * @author gyl
 * @since 1.0
 */
@Getter
public enum EntitySubscribeType {

    prepareCreate("subscribePrepareCreateEvent", EntityPrepareCreateEvent.class, "订阅创建预处理事件", "实体类创建预处理事件，可在这个阶段修改实体类属性值"),
    beforeCreate("subscribeBeforeCreateEvent", EntityBeforeCreateEvent.class, "订阅创建前事件", "实体类创建前事件，可用于校验参数等操作"),
    create("subscribeCreateEvent", EntityCreatedEvent.class, "订阅创建事件", "实体类创建事件，可用于记录日志等操作"),

    prepareModify("subscribePrepareModifyEvent", EntityPrepareModifyEvent.class, "订阅修改预处理事件", "实体类修改预处理事件，可在这个阶段修改实体类属性值", EntitySubscribeType::getModifyOutPut),
    beforeModify("subscribeBeforeModifyEvent", EntityBeforeModifyEvent.class, "订阅修改前事件", "实体类修改事件，可用于校验参数等操作", EntitySubscribeType::getModifyOutPut),
    modify("subscribeModifyEvent", EntityCreatedEvent.class, "订阅修改事件", "实体类修改事件，可用于记录日志等操作", EntitySubscribeType::getModifyOutPut),

    prepareSaved("subscribePrepareSavedEEvent", EntityPrepareSaveEvent.class, "订阅保存预处理事件", "实体类保存预处理事件，可在这个阶段修改实体类属性值"),
    beforeSaved("subscribeBeforeSavedEvent", EntityBeforeSaveEvent.class, "订阅保存前事件", "实体类保存事件，可用于校验参数等操作"),
    saved("subscribeSavedEvent", EntitySavedEvent.class, "订阅保存事件", "实体类保存事件，可用于记录日志等操作"),

    beforeDelete("subscribeBeforeDeleteEvent", EntityBeforeDeleteEvent.class, "订阅删除前事件", "实体类删除事件，可用于校验参数等操作"),
    delete("subscribeDeleteEvent", EntityDeletedEvent.class, "订阅删除事件", "实体类删除事件，可用于记录日志等操作"),

    ;
    private final String id;
    private final Class<?> eventClass;
    private final String name;
    private final String description;
    private final Function<DataType, DataType> output;

    EntitySubscribeType(String id, Class<?> eventClass, String name, String description) {
        this.id = id;
        this.eventClass = eventClass;
        this.name = name;
        this.description = description;
        this.output = EntitySubscribeType::getOutPut;
    }

    EntitySubscribeType(String id, Class<?> eventClass, String name, String description, Function<DataType, DataType> output) {
        this.id = id;
        this.eventClass = eventClass;
        this.name = name;
        this.description = description;
        this.output = output;
    }

    public static DataType getOutPut(DataType entityDataType) {
        ObjectType event = new ObjectType();
        event.addProperty("entity", "实体列表", new ArrayType().elementType(entityDataType));
        return event;
    }

    public static DataType getModifyOutPut(DataType entityDataType) {
        ObjectType event = new ObjectType();
        event.addProperty("before", "变更前实体", new ArrayType().elementType(entityDataType));
        event.addProperty("after", "变更后实体", new ArrayType().elementType(entityDataType));
        return event;
    }


}
