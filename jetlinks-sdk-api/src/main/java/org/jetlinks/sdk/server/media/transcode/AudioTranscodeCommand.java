package org.jetlinks.sdk.server.media.transcode;

public class AudioTranscodeCommand extends TranscodeCommand<AudioTranscodeCommand> {

    public String getCodec() {
        return getOrNull("codec", String.class);
    }

    public AudioTranscodeCommand setCodec(String codec) {
        return with("codec", codec);
    }


}
