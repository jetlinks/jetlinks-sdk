package org.jetlinks.sdk.server.file;

import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

/**
 * 预存储文件信息命令，用于在文件内容保存前就存储相关信息.适用于异步上传文件,优先获取文件id进行处理的场景
 *
 * @see org.jetlinks.sdk.server.SdkServices#fileService
 * @since 1.0.1
 */
public class PrepareUploadFileInfoCommand extends AbstractCommand<Mono<FileInfo>, PrepareUploadFileInfoCommand> {
    /**
     * 获取文件信息
     *
     * @return 文件信息
     */
    public PrepareUploadRequest getRequest() {
        return FastBeanCopier.copy(readable().get("request"), new PrepareUploadRequest());
    }

    /**
     * 设置文件信息
     *
     * @param request 文件内容长度
     * @return this
     */
    public PrepareUploadFileInfoCommand withRequest(PrepareUploadRequest request) {
        return with("request", request);
    }

}