package org.jetlinks.sdk.server.ai;

import org.jetlinks.sdk.server.ai.cv.ImageData;

import java.util.function.Supplier;


public class SimpleImageGenericAiOutput extends SimpleGenericAiOutput<ImageData, SimpleImageGenericAiOutput> {

    public Supplier<ImageData> newFileDataSupplier() {
        return ImageData::new;
    }


}
