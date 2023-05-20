package io.persona.unit.service;

import org.junit.jupiter.engine.discovery.DiscoverySelectorResolver;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class TestLauncher {
    //
    public static void main(String[] args) {
        var launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
                .selectors(DiscoverySelectors.selectPackage("io.persona.test"))
                .build();

        launcher.execute(request);
    }
}
