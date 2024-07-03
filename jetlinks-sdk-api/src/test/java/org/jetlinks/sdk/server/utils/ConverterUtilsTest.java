package org.jetlinks.sdk.server.utils;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class ConverterUtilsTest {

    @Test
    void testBase64(){

        byte[] data = new byte[1024];
        ThreadLocalRandom.current().nextBytes(data);

        ByteBuf buf = ConverterUtils.convertNettyBuffer(Base64.getEncoder().encodeToString(data));

        assertArrayEquals(data, buf.array());

    }

    @Test
    void testDataBase64(){
        byte[] data = new byte[1024];
        String base64  = Base64.getEncoder().encodeToString(data);

        ByteBuf buf =  ConverterUtils.convertNettyBuffer("data:image/png;base64,"+base64);

        assertArrayEquals(data, buf.array());

    }

}