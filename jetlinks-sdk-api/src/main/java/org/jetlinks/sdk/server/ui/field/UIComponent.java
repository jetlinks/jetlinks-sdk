package org.jetlinks.sdk.server.ui.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.sdk.server.ui.field.annotation.field.Select;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;

public interface UIComponent {
    /**
     * 文本框
     */
    String INPUT = "input";

    /**
     * 长文本框
     */
    String TEXTAREA = "textarea";

    /**
     * 数字输入框
     */
    String INPUT_NUMBER = "inputNumber";

    /**
     * 密码框
     */
    String PASSWORD = "password";

    /**
     * 开关
     */
    String SWITCH = "switch";

    /**
     * 单选框
     */
    String RADIO = "radio";

    /**
     * 复选框
     */
    String CHECKBOX = "checkbox";

    /**
     * 时间选择
     */
    String TIME = "time";

    /**
     * 日期选择
     */
    String DATE = "date";

    /**
     * 时间范围
     */
    String TIME_RANGE = "timeRange";

    /**
     * 日期范围
     */
    String RANGE_PICKER = "rangePicker";

    /**
     * 树选择
     */
    String TREE_SELECT = "treeSelect";

    /**
     * 文件上传
     */
    String FILE_UPLOAD = "fileUpload";

    /**
     * 图片上传
     */
    String IMAGE_UPLOAD = "imageUpload";

    /**
     * 树形控件
     */
    String TREE = "tree";

    /**
     * 下拉选择
     */
    String SELECT = "select";


}
