package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.sdk.server.device.DevicePropertyAggregation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryPropertyAggCommandTest {

    @Test
    void testGetColumnsFromExpression() {
        QueryPropertyAggCommand command = new QueryPropertyAggCommand()
            .withColumnsExpression("avg(temp) as avgTemp,max(properties.humidity) as maxHumidity");

        List<DevicePropertyAggregation> columns = command.getColumns();

        assertEquals(2, columns.size());
        assertEquals("avg", columns.get(0).getAgg());
        assertEquals("temp", columns.get(0).getProperty());
        assertEquals("avgTemp", columns.get(0).getAlias());
        assertEquals("max", columns.get(1).getAgg());
        assertEquals("humidity", columns.get(1).getProperty());
        assertEquals("maxHumidity", columns.get(1).getAlias());
    }

    @Test
    void testGetColumnsFromJsonString() {
        QueryPropertyAggCommand command = new QueryPropertyAggCommand()
            .with("columns", "[{\"property\":\"temp\",\"alias\":\"avgTemp\",\"agg\":\"avg\"}]");

        List<DevicePropertyAggregation> columns = command.getColumns();

        assertEquals(1, columns.size());
        assertEquals("temp", columns.get(0).getProperty());
        assertEquals("avgTemp", columns.get(0).getAlias());
        assertEquals("avg", columns.get(0).getAgg());
    }
}
