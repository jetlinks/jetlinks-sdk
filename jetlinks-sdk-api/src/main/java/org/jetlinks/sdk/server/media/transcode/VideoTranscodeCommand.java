package org.jetlinks.sdk.server.media.transcode;

public class VideoTranscodeCommand extends TranscodeCommand<VideoTranscodeCommand> {

    /**
     * @see AudioCodecs
     */
    public String getAudioCodec() {
        return getOrNull("audioCodec", String.class);
    }

    /**
     * @see AudioCodecs
     */
    public VideoTranscodeCommand setAudioCodec(String codec) {
        return with("audioCodec", codec);
    }

    /**
     * @see VideoCodecs
     */
    public String getVideoCodec() {
        return getOrNull("videoCodec", String.class);
    }

    /**
     * @see VideoCodecs
     */
    public VideoTranscodeCommand setVideoCodec(String codec) {
        return with("videoCodec", codec);
    }


}
