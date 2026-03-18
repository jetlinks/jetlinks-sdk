package org.jetlinks.sdk.server.media;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

@Schema(title = "读取截图文件")
public class DownloadSnapFileCommand extends AbstractCommand<Flux<ByteBuf>, DownloadSnapFileCommand> {

}
