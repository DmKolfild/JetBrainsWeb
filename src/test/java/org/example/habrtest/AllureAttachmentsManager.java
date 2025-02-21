package org.example.habrtest;

import io.qameta.allure.Attachment;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.screenshot;

public class AllureAttachmentsManager {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] screenshotForAllure() {
        return Objects.requireNonNull(screenshot("test")).getBytes();
    }
}
