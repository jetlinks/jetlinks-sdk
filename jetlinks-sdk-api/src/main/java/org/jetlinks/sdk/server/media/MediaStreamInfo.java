package org.jetlinks.sdk.server.media;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
public class MediaStreamInfo extends MediaInfo{

    //rtp推流时的ssrc值
    @Nullable
    private String ssrc;
}
