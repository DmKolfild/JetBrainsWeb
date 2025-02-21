package org.example.habrtest.pages;

import org.example.habrtest.AllureLogger;
import org.openqa.selenium.By;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;

//page url = https://www.jetbrains.com/clion/download/
public class CLionDownloadPage {
    private final AllureLogger LOG = new AllureLogger(LoggerFactory.getLogger(CLionDownloadPage.class));

    private static final By DOWNLOAD_BUTTON = By.xpath("//a[contains(text(),'Download')]");

    private static final By EXE_BUTTON = By.xpath("//button[@data-test='dropdown-trigger']/span[contains(text(), '.exe')]");

    private static final By WINDOWS_BUTTON = By.xpath("//div[contains(text(), 'Windows')]/..");

    private static final By ZIP_BUTTON = By.xpath("//span[contains(text(), '.zip')]");

    private static final By INSTRUCTION_BUTTON = By.xpath("//div[@class='wt-css-content-switcher__block']//span[contains(text(), 'Installation instructions')]");

    private static final By INSTRUCTION_HEADER_TEXT = By.xpath("//div[contains(text(), 'Installation instructions')]");

    public Boolean checkIfDownloadButtonIsClickable() {
        LOG.info("Проверка активности кнопки загрузки");
        return $(DOWNLOAD_BUTTON).isEnabled();
    }

    public void clickExe() {
        LOG.info("Клик по выпадающему списку .exe");
        $(EXE_BUTTON).click();
    }

    public void clickWindowsButtonIfNotSelected() {
        LOG.info("Клик по кнопке Windows, если кнопка неактивна");
        Boolean windowsButtonIsSelected = checkWindowsButton();
        if (!windowsButtonIsSelected)
            $(WINDOWS_BUTTON).click();
    }

    public Boolean checkWindowsButton() {
        LOG.info("Проверка, что кнопка Windows выбрана");
        $(WINDOWS_BUTTON).shouldHave(attribute("data-test"));
        String attributeDataTestOfWindowsButton = $(WINDOWS_BUTTON).getAttribute("data-test");
        return attributeDataTestOfWindowsButton.equals("tab tab-selected");
    }

    public Boolean checkIfZipButtonIsClickable() {
        LOG.info("Проверка активности кнопки загрузки в формате .zip");
        return $(ZIP_BUTTON).isEnabled();
    }

    public void clickInstruction() {
        LOG.info("Клик по кнопке с инструкцией по установке");
        $(INSTRUCTION_BUTTON).click();
    }

    public Boolean checkIfHeaderInstructionIsDisplayed() {
        LOG.info("Проверка, что заголовок окна инструкции по установке виден");
        return $(INSTRUCTION_HEADER_TEXT).isDisplayed();
    }
}
