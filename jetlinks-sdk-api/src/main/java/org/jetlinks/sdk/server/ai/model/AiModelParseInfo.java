package org.jetlinks.sdk.server.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.AiDomain;

@Setter
@Getter
public class AiModelParseInfo {

    @NotBlank
    @Schema(description = "名称")
    private String name;

    @NotBlank
    @Schema(description = "文件地址")
    private String fileUrl;

    @Schema(description = "模型提供商")
    private String provider;

    @NotBlank
    @Schema(description = "驱动ID")
    private String driverId;

    @NotNull
    @Schema(description = "适用领域")
    private AiDomain domain;

    @Schema(description = "说明")
    private String description;

    @NotBlank
    @Schema(description = "AI命令(任务目标/模型类别)")
    private String taskTarget;

    @Schema(description = "AI命令(任务目标/模型类别)名称")
    private String taskTargetName;

    public AiModelParseInfo withFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public AiModelParseInfo withDomain(AiDomain domain) {
        this.domain = domain;
        return this;
    }
}
