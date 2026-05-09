package org.jetlinks.sdk.server.utils;

import org.jetlinks.sdk.server.device.DevicePropertyAggregation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AggregationExpressionParserTest {

    @Test
    void testParseExpressions() {
        List<DevicePropertyAggregation> aggregations = AggregationExpressionParser.parse("""
            avg(temp) as avgTemp,
            max(properties.humidity) maxHumidity;
            count(this.properties['alarm']) -> alarmCount
            """);

        assertEquals(3, aggregations.size());

        assertEquals("avg", aggregations.get(0).getAgg());
        assertEquals("temp", aggregations.get(0).getProperty());
        assertEquals("avgTemp", aggregations.get(0).getAlias());

        assertEquals("max", aggregations.get(1).getAgg());
        assertEquals("humidity", aggregations.get(1).getProperty());
        assertEquals("maxHumidity", aggregations.get(1).getAlias());

        assertEquals("count", aggregations.get(2).getAgg());
        assertEquals("alarm", aggregations.get(2).getProperty());
        assertEquals("alarmCount", aggregations.get(2).getAlias());
    }

    @Test
    void testParseExpressionWithoutAlias() {
        DevicePropertyAggregation aggregation = AggregationExpressionParser.parseColumn("SUM(`temp`)");

        assertEquals("sum", aggregation.getAgg());
        assertEquals("temp", aggregation.getProperty());
        assertNull(aggregation.getAlias());
    }
}
