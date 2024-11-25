package org.jetlinks.sdk.generator.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parent extends Artifact {


    public void merge(Parent project) {
        super.merge(project);
    }


}
