# JetLinks sdk

服务端SDK包,用于统一定义JetLinks服务端开发时需要访问平台的一些通用功能API,
如设备管理,消息通知等功能.

## AI 任务目标接口

`TaskTarget` 仅描述任务目标的 `value`、`text` 和 `description`。
已移除废弃方法 `getAiOutputInstance()`、`getSimpleAiOutputInstance()` 和 `getAiOutputMetadata()`。
该调整属于接口不兼容变更，仍调用旧方法的消费者需同步适配并重新编译。
