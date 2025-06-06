package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.utils.file.FileUtils;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.validator.ValidatorUtils;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 *
 * @author zhangji 2025/6/3
 * @since 2.10
 */
@Getter
@Setter
public class UploadFileInfo {

    @Schema(description = "支持类型")
    private FileType type;

    @Schema(description = "传递方式")
    @NotNull
    private TransferMethod transferMethod;

    @Schema(description = "文件地址，传递方式为文件地址时使用")
    private String url;

    @Schema(description = "文件ID，传递方式为上传文件时使用")
    private String uploadFileId;

    @Schema(description = "文件后缀")
    private String extension;

    public void validate() {
        ValidatorUtils.tryValidate(this);
        if (transferMethod == TransferMethod.remote_url && !StringUtils.hasText(url)) {
            throw new ValidationException("url cannot be blank");
        }
        if (transferMethod == TransferMethod.local_file && !StringUtils.hasText(uploadFileId)) {
            throw new ValidationException("uploadFileId cannot be blank");
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (type == null) {
            type = FileType.of(extension);
        }
        map.put("type", type.getValue());
        map.put("transfer_method", transferMethod.getValue());
        if (StringUtils.hasText(url)) {
            map.put("url", url);
        }
        if (StringUtils.hasText(uploadFileId)) {
            map.put("upload_file_id", uploadFileId);
        }
        return map;
    }

    @Getter
    @AllArgsConstructor
    public enum FileType implements EnumDict<String> {
        document("文本", Arrays.asList("TXT", "MD", "MARKDOWN", "PDF", "HTML", "XLSX", "XLS", "DOCX", "CSV", "EML", "MSG", "PPTX", "PPT", "XML", "EPUB")),
        image("图片", Arrays.asList("JPG", "JPEG", "PNG", "GIF", "WEBP", "SVG")),
        audio("音频", Arrays.asList("MP3", "M4A", "WAV", "WEBM", "AMR")),
        video("视频", Arrays.asList("MP4", "MOV", "MPEG", "MPGA")),
        custom("自定义", Collections.emptyList());

        private final String text;

        //类型对应的文件后缀
        private final List<String> suffix;

        public String getValue() {
            return name();
        }

        public static FileType of(String extension) {
            if (extension == null) {
                return custom;
            }
            return Arrays
                .stream(values())
                .filter(type -> type.getSuffix().contains(extension.toUpperCase()))
                .findAny()
                .orElse(custom);
        }

        public static FileType ofFileName(String fileName) {
            return of(FileUtils.getSuffix(fileName));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum TransferMethod implements EnumDict<String> {
        remote_url("文件地址"),
        local_file("上传文件");

        private final String text;

        public String getValue() {
            return name();
        }
    }

}
