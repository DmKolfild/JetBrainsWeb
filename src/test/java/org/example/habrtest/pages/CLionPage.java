package org.example.habrtest.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.habrtest.AllureLogger;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.switchTo;

//page url = https://www.jetbrains.com/clion/
public class CLionPage {
    private final AllureLogger LOG = new AllureLogger(LoggerFactory.getLogger(CLionPage.class));

    @FindBy(xpath = "//a[contains(text(),'Download CLion')]")
    private SelenideElement downloadButton;

    @FindBy(css = "#js-menu-second-desktop a[href='/clion/whatsnew/']")
    private SelenideElement whatIsNewButton;

    @FindBy(css = "button[data-test='youtube-player-button']")
    private SelenideElement imgVideoButton;

    @FindBy(css = "iframe[title='CLion Quick Tour']")
    private SelenideElement videoIframe;

    @FindBy(css = "#player a[href^='https://www.youtube.com/watch']")
    private SelenideElement videoTitle;

    @FindBy(css = "[data-test='social-footer-menu-item'] a")
    private List<SelenideElement> followButtons;

    @FindBy(css = "[name='Email']")
    private SelenideElement emailInput;

    @FindBy(xpath = "//button[contains(text(), 'Submit')]")
    private SelenideElement emailSubmit;

    @FindBy(css = ".social-footer p:last-child")
    private SelenideElement messageAfterEnteringValidEmail;

    @FindBy(css = "[data-test='error-message']")
    private SelenideElement messageAfterEnteringInvalidEmail;

    @FindBy(css = "header [data-test='language-picker']")
    private SelenideElement languageButtonInTheHeader;

    @FindBy(css = "footer [data-test='language-picker']")
    private SelenideElement languageButtonInTheFooter;

    @FindBy(css = "div > span[data-test='list-item'] > span")
    private List<SelenideElement> listOfLanguages;

    @FindBy(css = "h2 + div img[alt='Code Analysis screenshot']")
    private List<SelenideElement> ScreenshotsInCodeAnalysisSection;

    @FindBy(css = "[data-jetbrains-cookies-banner-action='ACCEPT_ALL']")
    private SelenideElement acceptAllFromCookiesForm;

    public Integer getCountOfScreenshotsInCodeAnalysisSection() {
        return ScreenshotsInCodeAnalysisSection.size();
    }

    public void clickAcceptAllFromCookiesForm() {
        if (acceptAllFromCookiesForm.isDisplayed()) {
            LOG.info("Кликнуть 'Принять куки'");
            acceptAllFromCookiesForm.click();
        } else {
            LOG.info("Окно с куками не отобразилось");
        }
    }

    public void checkIfDownloadButtonIsClickable() {
        LOG.info("Проверка активности кнопки загрузки");
        downloadButton.shouldBe(enabled);
    }

    public void clickDownloadButton() {
        LOG.info("Переход на страницу загрузки");
        downloadButton.click();
    }

    public void checkIfWhatIsNewButtonClickable() {
        LOG.info("Проверка активности кнопки 'what's new'");
        whatIsNewButton.shouldBe(enabled);
    }

    public void switchOnIframe() {
        LOG.info("Переключение на frame с видео");
        imgVideoButton.click();
        switchTo().frame(videoTitle);
    }

    public String getNameOfVideo() {
        switchOnIframe();
        LOG.infoWithScreenshot("Получение названия видео");
        return videoTitle.getText();
    }

    public Boolean checkIfFollowButtonsAreClickable(int num) {
        LOG.info("Получение активности кнопки из блока Follow Us");
        return followButtons.get(num).isEnabled();
    }

    public void enterEmail(String email) {
        LOG.info("Ввода email");
        emailInput.setValue(email);
        emailSubmit.click();
    }

    public String getAnswerAfterEnteringValidEmail(String email) {
        LOG.info("Получение сообщения при вводе валидного email");
        enterEmail(email);
        return messageAfterEnteringValidEmail.getText();
    }

    public String getWarningAfterEnteringInvalidEmail(String email) {
        LOG.info("Получение сообщения при вводе невалидного email");
        enterEmail(email);
        return messageAfterEnteringInvalidEmail.getText();
    }

    public void changeLanguage(String language) {
        LOG.infoWithScreenshot("Смена языка страницы на указанный");
        languageButtonInTheHeader.shouldBe(visible).click();
        for (SelenideElement i : listOfLanguages) {
            if (i.getText().equals(language)) {
                i.click();
                break;
            }
        }
    }
}
