package org.jetlinks.sdk.server.media;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

/**
 * ZLMedia转码配置请求类
 * 用于配置视频流的转码参数、AI处理参数、协议转换等
 */
@Schema(name = SetupTranscodeCommand.ID, title = "ZLMedia 转码命令")
public class SetupTranscodeCommand extends AbstractCommand<Mono<JSONObject>, SetupTranscodeCommand> {

    public static final String ID = "setupTranscode";

    @Override
    public String getCommandId() {
        return ID;
    }

    public SetupTranscodeCommand withApp(String app) {
        return with("app", app);
    }

    // ============ 流参数 ============

    /**
     * 流ID
     */
    public SetupTranscodeCommand withStreamId(String streamId) {
        return with("stream", streamId);
    }

    /**
     * 转码名，作为转码后流的stream_id的后缀
     */
    public SetupTranscodeCommand withName(String name) {
        return with("name", name);
    }

    /**
     * 添加或删除转码，1为添加，0为删除
     */
    public SetupTranscodeCommand withAdd(Integer add) {
        return with("add", add);
    }

    // ============ 视频转码参数 ============

    /**
     * 视频转码的codec,支持H264/H265/JPEG/copy
     */
    public SetupTranscodeCommand withVideoCodec(String videoCodec) {
        return with("video_codec", videoCodec);
    }

    /**
     * 转码后视频的比特率
     */
    public SetupTranscodeCommand withVideoBitrate(Integer videoBitrate) {
        return with("video_bitrate", videoBitrate);
    }

    /**
     * 转码视频宽高拉伸比例，取值范围0.1~10
     */
    public SetupTranscodeCommand withVideoScale(Double videoScale) {
        return with("video_scale", videoScale);
    }

    // ============ 音频转码参数 ============

    /**
     * 音频转码codec，支持mpeg4-generic/PCMA/PCMU/opus/copy
     * 默认: mpeg4-generic
     */
    public SetupTranscodeCommand withAudioCodec(String audioCodec) {
        return with("audio_codec", audioCodec);
    }

    /**
     * 转码后音频比特率
     * 默认: 32000
     */
    public SetupTranscodeCommand withAudioBitrate(Integer audioBitrate) {
        return with("audio_bitrate", audioBitrate);
    }

    /**
     * 转码后音频通道数
     */
    public SetupTranscodeCommand withAudioChannel(Integer audioChannel) {
        return with("audio_channel", audioChannel);
    }

    /**
     * 转码后音频采样率
     * 默认: 48000
     */
    public SetupTranscodeCommand withAudioSamplerate(Integer audioSamplerate) {
        return with("audio_samplerate", audioSamplerate);
    }

    // ============ 协议转换参数 ============

    /**
     * 是否转hls
     */
    public SetupTranscodeCommand withEnableHls(Integer enableHls) {
        return with("enable_hls", enableHls);
    }

    /**
     * 是否mp4录制
     */
    public SetupTranscodeCommand withEnableMp4(Integer enableMp4) {
        return with("enable_mp4", enableMp4);
    }

    /**
     * 是否转协议为rtsp/webrtc
     */
    public SetupTranscodeCommand withEnableRtsp(Integer enableRtsp) {
        return with("enable_rtsp", enableRtsp);
    }

    /**
     * 是否转协议为rtmp/flv
     */
    public SetupTranscodeCommand withEnableRtmp(Integer enableRtmp) {
        return with("enable_rtmp", enableRtmp);
    }

    /**
     * 是否转协议为http-ts/ws-ts
     */
    public SetupTranscodeCommand withEnableTs(Integer enableTs) {
        return with("enable_ts", enableTs);
    }

    /**
     * 是否转协议为http-fmp4/ws-fmp4
     */
    public SetupTranscodeCommand withEnableFmp4(Integer enableFmp4) {
        return with("enable_fmp4", enableFmp4);
    }

    /**
     * 转协议是否开启音频
     */
    public SetupTranscodeCommand withEnableAudio(Integer enableAudio) {
        return with("enable_audio", enableAudio);
    }

    /**
     * 转协议无音频时，是否添加静音aac音频
     */
    public SetupTranscodeCommand withAddMuteAudio(Integer addMuteAudio) {
        return with("add_mute_audio", addMuteAudio);
    }

    // ============ 录制参数 ============

    /**
     * mp4录制保存根目录，置空使用默认目录
     */
    public SetupTranscodeCommand withMp4SavePath(String mp4SavePath) {
        return with("mp4_save_path", mp4SavePath);
    }

    /**
     * mp4录制切片大小，单位秒
     */
    public SetupTranscodeCommand withMp4MaxSecond(Integer mp4MaxSecond) {
        return with("mp4_max_second", mp4MaxSecond);
    }

    /**
     * hls保存根目录，置空使用默认目录
     */
    public SetupTranscodeCommand withHlsSavePath(String hlsSavePath) {
        return with("hls_save_path", hlsSavePath);
    }

    // ============ 其他转码参数 ============

    /**
     * 是否重新计算时间戳
     */
    public SetupTranscodeCommand withModifyStamp(Integer modifyStamp) {
        return with("modify_stamp", modifyStamp);
    }

    /**
     * avfilter滤镜参数
     * 例如: "drawtext=fontfile=/System/Library/Fonts/Supplemental/Songti.ttc:text='hellow 录制':x=10:y=10:fontsize=24:fontcolor=yellow:shadowy=1"
     */
    public SetupTranscodeCommand withFilter(String filter) {
        return with("filter", filter);
    }

    /**
     * 是否启用硬件解码器，默认启用
     */
    public SetupTranscodeCommand withHwDecoder(Integer hwDecoder) {
        return with("hw_decoder", hwDecoder);
    }

    /**
     * 是否启用硬件编码器，默认启用
     */
    public SetupTranscodeCommand withHwEncoder(Integer hwEncoder) {
        return with("hw_encoder", hwEncoder);
    }

    /**
     * 解码线程数，默认2个，最多16个，音频强制为1个
     */
    public SetupTranscodeCommand withDecoderThreads(Integer decoderThreads) {
        return with("decoder_threads", decoderThreads);
    }

    /**
     * 编码线程数，默认4个，最多16个，音频强制为1个
     */
    public SetupTranscodeCommand withEncoderThreads(Integer encoderThreads) {
        return with("encoder_threads", encoderThreads);
    }

    /**
     * 是否强制转码，如果目标codec与原始一致，默认不转码
     */
    public SetupTranscodeCommand withForce(Integer force) {
        return with("force", force);
    }

    /**
     * 视频解码器列表，以逗号分割，前面的优先级高
     * 例如: "openh264"
     */
    public SetupTranscodeCommand withDecoderList(String decoderList) {
        return with("decoder_list", decoderList);
    }

    /**
     * 视频编码器列表，以逗号分割，前面的优先级高
     * 例如: "hevc_videotoolbox,libx265"
     */
    public SetupTranscodeCommand withEncoderList(String encoderList) {
        return with("encoder_list", encoderList);
    }

    // ============ AI处理参数 ============

    /**
     * 模型文件路径，多个模型以逗号分割
     * 例如: "yolov8s.plan,yolov8s.plan"
     */
    public SetupTranscodeCommand withModel(String model) {
        return with("model", model);
    }

    /**
     * 抽帧间隔时间，为0则每帧都处理，单位毫秒
     */
    public SetupTranscodeCommand withIntervalMs(Integer interval) {
        return with("interval_ms", interval);
    }

    /**
     * 非极大值抑制阈值,用于去除重叠度高的重复框
     */
    public SetupTranscodeCommand withNmsThresh(Double nmsThresh) {
        return with("nmsThresh", nmsThresh);
    }

    /**
     * 置信度阈值，过滤掉置信度低于此值的候选框
     */
    public SetupTranscodeCommand withConfThresh(Double confThresh) {
        return with("confThresh", confThresh);
    }

    /**
     * ai处理后是否绘制文字和框且再编码，默认开启
     */
    public SetupTranscodeCommand withPaint(Integer paint) {
        return with("paint", paint);
    }

    /**
     * ai处理后是否绘制目标类型文字
     */
    public SetupTranscodeCommand withPaintTxt(Integer paintTxt) {
        return with("paint_txt", paintTxt);
    }

    /**
     * 截图后，是否在截图上绘制目标类型文字或目标框
     */
    public SetupTranscodeCommand withDrawSnap(Integer drawSnap) {
        return with("draw_snap", drawSnap);
    }

    // ============ MQTT参数 ============

    /**
     * mqtt写入间隔，单位秒
     */
    public SetupTranscodeCommand withMqttInterval(Integer mqttInterval) {
        return with("mqtt_publish_interval", mqttInterval);
    }

    /**
     * 写入mqtt地址
     */
    public SetupTranscodeCommand withMqttHost(String mqttHost) {
        return with("mqtt_host", mqttHost);
    }

    /**
     * 写入mqtt端口
     */
    public SetupTranscodeCommand withMqttPort(int mqttPort) {
        return with("mqtt_port", mqttPort);
    }

    /**
     * 写入mqtt主题
     */
    public SetupTranscodeCommand withMqttTopic(String mqttTopic) {
        return with("mqtt_publish_topic", mqttTopic);
    }

    /**
     * 写入mqtt Client id
     */
    public SetupTranscodeCommand withMqttClient(String mqttClient) {
        return with("mqtt_client", mqttClient);
    }

    /**
     * 写入mqtt用户名
     */
    public SetupTranscodeCommand withMqttUser(String mqttUser) {
        return with("mqtt_user", mqttUser);
    }

    /**
     * 写入mqtt用户密码
     */
    public SetupTranscodeCommand withMqttSecret(String mqttSecret) {
        return with("mqtt_password", mqttSecret);
    }

    /**
     * 订阅mqtt主题，透传给python插件用
     */
    public SetupTranscodeCommand withMqttTopicSub(String mqttTopicSub) {
        return with("mqtt_topic_sub", mqttTopicSub);
    }

    // ============ 其他AI参数 ============

    /**
     * 识别结果是否写入sei帧(protobuf序列化)
     */
    public SetupTranscodeCommand withEnableSei(Integer enableSei) {
        return with("enable_sei", enableSei);
    }

    /**
     * 该模型识别结果是否启用目标跟踪算法，多个模型开关通过逗号分割
     */
    public SetupTranscodeCommand withEnableTrack(Integer enableTrack) {
        return with("enable_track", enableTrack);
    }

    /**
     * 该模型识别结果是否包含obb旋转信息
     */
    public SetupTranscodeCommand withEnableObb(Integer enableObb) {
        return with("enable_obb", enableObb);
    }

    /**
     * 中文字体文件，绘制中文时需要
     */
    public SetupTranscodeCommand withFont(String font) {
        return with("font", font);
    }

    /**
     * python插件名(python源码无后缀名)
     */
    public SetupTranscodeCommand withPythonPlugin(String pythonPlugin) {
        return with("python_plugin", pythonPlugin);
    }


}
