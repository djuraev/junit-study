package io.persona.unit.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalExtension implements BeforeAllCallback, AfterTestExecutionCallback {
    //

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        //
        System.out.println("Before All Callback");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        //
        System.out.println("After Test Execution Callback");
    }
}
