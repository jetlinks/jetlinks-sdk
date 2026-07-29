package org.jetlinks.sdk.server.ai.cv.data;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentityTest {

    @Test
    void shouldSerializeAndDeserialize() throws Exception {
        Identity source = Identity.of("employee-face-library", "person-10001");
        source.setName("张三");
        source.setScore(0.93F);
        source.setOthers(Map.of("group", "employee", "providerId", "provider-1"));

        byte[] bytes;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             ObjectOutputStream objectOutput = new ObjectOutputStream(output)) {
            objectOutput.writeObject(source);
            bytes = output.toByteArray();
        }

        Identity target;
        try (ObjectInputStream objectInput = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            target = (Identity) objectInput.readObject();
        }

        assertEquals("employee-face-library", target.getProfile());
        assertEquals("person-10001", target.getId());
        assertEquals("张三", target.getName());
        assertEquals(0.93F, target.getScore());
        assertEquals(Map.of("group", "employee", "providerId", "provider-1"), target.getOthers());
    }
}
