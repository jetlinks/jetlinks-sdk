package org.jetlinks.sdk.server.ai.cv.data;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeatureTest {

    @Test
    void shouldSerializeAndDeserialize() throws Exception {
        Feature source = Feature.of("glasses");
        source.setConfidence(0.93F);
        source.setOthers(Map.of("providerId", "provider-1", "color", "black"));

        byte[] bytes;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            objectOutput.writeObject(source);
            bytes = output.toByteArray();
        }

        Feature target;
        try (ObjectInputStream objectInput = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            target = (Feature) objectInput.readObject();
        }

        assertEquals("glasses", target.getValue());
        assertEquals(0.93F, target.getConfidence());
        assertEquals(Map.of("providerId", "provider-1", "color", "black"), target.getOthers());
    }
}
