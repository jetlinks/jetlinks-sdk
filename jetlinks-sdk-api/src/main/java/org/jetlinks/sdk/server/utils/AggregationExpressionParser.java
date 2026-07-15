package org.jetlinks.sdk.server.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * 聚合查询表达式解析器.
 * <pre>
 *     avg(temp) as avgTemp
 *     max(properties.humidity) maxHumidity
 *     count(this.properties['alarm']) -> alarmCount
 * </pre>
 *
 * @author zhouhao
 * @since 1.1.1
 */
public final class AggregationExpressionParser {

    private static final String THIS_PROPERTIES_PREFIX = "this.properties.";

    private static final String PROPERTIES_PREFIX = "properties.";

    private static final String THIS_PROPERTIES_BRACKET_PREFIX = "this.properties[";

    private static final String PROPERTIES_BRACKET_PREFIX = "properties[";

    private AggregationExpressionParser() {

    }

    public static <T> List<T> parse(String expression, AggregationMapper<T> mapper) {
        Objects.requireNonNull(mapper, "aggregation mapper can not be null");
        if (!StringUtils.hasText(expression)) {
            return Collections.emptyList();
        }
        List<String> expressions = split(expression);
        List<T> aggregations = new ArrayList<>(expressions.size());
        for (String item : expressions) {
            aggregations.add(parseColumn(item, mapper));
        }
        return aggregations;
    }

    public static <T> T parseColumn(String expression, AggregationMapper<T> mapper) {
        Objects.requireNonNull(mapper, "aggregation mapper can not be null");
        if (!StringUtils.hasText(expression)) {
            throw new IllegalArgumentException("aggregation expression can not be blank");
        }
        String normalized = expression.trim();
        int start = normalized.indexOf('(');
        if (start <= 0) {
            throw new IllegalArgumentException("invalid aggregation expression: " + expression);
        }
        int end = findClosingParenthesis(normalized, start);
        String agg = normalized.substring(0, start).trim();
        String property = normalizeProperty(normalized.substring(start + 1, end));
        if (!StringUtils.hasText(agg) || !StringUtils.hasText(property)) {
            throw new IllegalArgumentException("invalid aggregation expression: " + expression);
        }
        String alias = normalizeAlias(normalized.substring(end + 1).trim());

        return mapper.apply(agg.toLowerCase(Locale.ROOT), property, alias);
    }

    static List<String> split(String expression) {
        List<String> expressions = new ArrayList<>();
        int depth = 0;
        int start = 0;
        char inQuote = 0;
        for (int i = 0, len = expression.length(); i < len; i++) {
            char ch = expression.charAt(i);
            if (inQuote != 0) {
                if (ch == inQuote && !isEscaped(expression, i)) {
                    inQuote = 0;
                }
                continue;
            }
            if (ch == '\'' || ch == '"' || ch == '`') {
                inQuote = ch;
                continue;
            }
            if (ch == '(') {
                depth++;
                continue;
            }
            if (ch == ')') {
                depth--;
                continue;
            }
            if (depth == 0 && isSeparator(ch)) {
                addExpression(expressions, expression.substring(start, i));
                start = i + 1;
            }
        }
        addExpression(expressions, expression.substring(start));
        return expressions;
    }

    private static void addExpression(List<String> expressions, String expression) {
        if (StringUtils.hasText(expression)) {
            expressions.add(expression.trim());
        }
    }

    private static boolean isSeparator(char ch) {
        return ch == ',' || ch == ';' || ch == '\n' || ch == '\r';
    }

    private static int findClosingParenthesis(String expression, int start) {
        int depth = 0;
        char inQuote = 0;
        for (int i = start, len = expression.length(); i < len; i++) {
            char ch = expression.charAt(i);
            if (inQuote != 0) {
                if (ch == inQuote && !isEscaped(expression, i)) {
                    inQuote = 0;
                }
                continue;
            }
            if (ch == '\'' || ch == '"' || ch == '`') {
                inQuote = ch;
                continue;
            }
            if (ch == '(') {
                depth++;
                continue;
            }
            if (ch == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("invalid aggregation expression: " + expression);
    }

    private static boolean isEscaped(String expression, int index) {
        return index > 0 && expression.charAt(index - 1) == '\\';
    }

    private static String normalizeAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        String normalized = alias.trim();
        String lower = normalized.toLowerCase(Locale.ROOT);
        if (lower.startsWith("as ") || lower.startsWith("as\t")) {
            normalized = normalized.substring(2).trim();
        } else if (normalized.startsWith("->") || normalized.startsWith("=>")) {
            normalized = normalized.substring(2).trim();
        } else if (normalized.startsWith(":")) {
            normalized = normalized.substring(1).trim();
        }
        return stripQuote(normalized);
    }

    private static String normalizeProperty(String property) {
        String normalized = stripQuote(property.trim());
        if (normalized.startsWith(THIS_PROPERTIES_PREFIX)) {
            return normalized.substring(THIS_PROPERTIES_PREFIX.length());
        }
        if (normalized.startsWith(PROPERTIES_PREFIX)) {
            return normalized.substring(PROPERTIES_PREFIX.length());
        }
        if (normalized.startsWith(THIS_PROPERTIES_BRACKET_PREFIX) && normalized.endsWith("]")) {
            return stripQuote(normalized.substring(THIS_PROPERTIES_BRACKET_PREFIX.length(), normalized.length() - 1).trim());
        }
        if (normalized.startsWith(PROPERTIES_BRACKET_PREFIX) && normalized.endsWith("]")) {
            return stripQuote(normalized.substring(PROPERTIES_BRACKET_PREFIX.length(), normalized.length() - 1).trim());
        }
        return normalized;
    }

    private static String stripQuote(String value) {
        if (!StringUtils.hasText(value) || value.length() < 2) {
            return value;
        }
        char first = value.charAt(0);
        char last = value.charAt(value.length() - 1);
        if ((first == '"' && last == '"')
            || (first == '\'' && last == '\'')
            || (first == '`' && last == '`')) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    @FunctionalInterface
    public interface AggregationMapper<T> {

        T apply(String agg, String property, String alias);
    }
}
