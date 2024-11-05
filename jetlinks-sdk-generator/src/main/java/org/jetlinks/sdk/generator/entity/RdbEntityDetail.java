package org.jetlinks.sdk.generator.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gyl
 * @since 1.0.1
 */
@Getter
@Setter
public class RdbEntityDetail extends RdbEntity {

    private List<RdbColumn> columns;
}
