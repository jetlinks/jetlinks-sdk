package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Schema(title = "图像比对结果")
public class ImageComparisonResult extends AiCommandResult<ImageComparisonResult> {


}
