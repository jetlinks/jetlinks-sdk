package org.jetlinks.sdk.server.ai;

/**
 * AI 任务目标
 *
 * @see InternalCVTaskTarget
 * @see AiCommand
 * @since 1.0.1
 */
public interface TaskTarget {

    String getValue();

    String getText();

    String getDescription();

}
