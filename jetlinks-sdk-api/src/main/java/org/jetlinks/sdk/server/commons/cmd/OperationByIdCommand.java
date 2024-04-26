package org.jetlinks.sdk.server.commons.cmd;


import org.jetlinks.core.command.AbstractConvertCommand;

/**
 *
 * @author zhouhao
 * @since 2.1
 */
public abstract class OperationByIdCommand<T, Self extends OperationByIdCommand<T, Self>> extends AbstractConvertCommand<T, Self> {

    public String getId() {
        return (String) readable().get("id");
    }

    public Self withId(String id) {
        writable().put("id", id);
        return castSelf();
    }

}
