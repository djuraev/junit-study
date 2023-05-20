package io.persona.unit.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class PostProcessingExtension implements TestInstancePostProcessor {
    //

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        //
        var fields = testInstance.getClass().getDeclaredFields();
        for (var f: fields) {
            System.out.println(f.getName());
        }
    }
}
