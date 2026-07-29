package org.jetlinks.sdk.server.ai.cv.data;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FeatureVectorTest {

    @Test
    void shouldSerializeAndDeserialize() throws Exception {
        FeatureVector source = FeatureVector.of("face-arcface-v2", 0.12F, -0.36F, 0.58F);
        source.setQualityScore(0.91F);

        byte[] bytes;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            objectOutput.writeObject(source);
            bytes = output.toByteArray();
        }

        FeatureVector target;
        try (ObjectInputStream objectInput = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            target = (FeatureVector) objectInput.readObject();
        }

        assertEquals("face-arcface-v2", target.getProfile());
        assertEquals(0.91F, target.getQualityScore());
        assertArrayEquals(new float[]{0.12F, -0.36F, 0.58F}, target.getValues());
        assertFalse(target.toString().contains("0.12"));
    }
}
