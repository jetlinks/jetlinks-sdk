package org.jetlinks.sdk.server.ai.embedding;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@AllArgsConstructor
@NoArgsConstructor
public class Vector implements Externalizable {

    private double[] data;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(data.length);
        for (double v : data) {
            out.writeDouble(v);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        data = new double[len];
        for (int i = 0; i < len; i++) {
            data[i] = in.readDouble();
        }
    }
}
