package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.info.GenerateResult;
import org.jetlinks.sdk.generator.java.info.base.PackageInfo;

import java.util.List;

public interface PackageJavaGenerator {

    List<GenerateResult> generate(PackageInfo packageInfo);

}
