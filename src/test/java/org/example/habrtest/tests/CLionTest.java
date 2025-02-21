package org.example.habrtest.tests;

import org.example.habrtest.pages.CLionDownloadPage;
import org.example.habrtest.pages.CLionPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CLionTest extends BaseTest {
    private CLionPage cLionPage;
    private CLionDownloadPage cLionDownloadPage;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        open("https://www.jetbrains.com/clion/");
        getWebDriver().manage().window().maximize();
        cLionPage = page();
        cLionDownloadPage = page();
        cLionPage.clickAcceptAllFromCookiesForm();
    }

    @Test
    @DisplayName("Активна кнопка скачивания на главной странице CLion")
    public void downloadButtonCheckMainPage() {
        cLionPage.checkIfDownloadButtonIsClickable();
    }

    @Test
    @DisplayName("Активна кнопка скачивания на странице загрузки CLion")
    public void downloadButtonCheckDownloadPage() {
        cLionPage.clickDownloadButton();
        assertTrue(cLionDownloadPage.checkIfDownloadButtonIsClickable(), "Кнопка скачивания не активна");
    }

    @Test
    @DisplayName("Активна кнопка скачивания файла в формате zip")
    public void checkButtonInZipFormat() {
        cLionPage.clickDownloadButton();
        cLionDownloadPage.clickWindowsButtonIfNotSelected();
        cLionDownloadPage.clickExe();
        assertTrue(cLionDownloadPage.checkIfZipButtonIsClickable(), "Кнопка скачивания не активна");
    }

    @Test
    @DisplayName("Активна кнопка 'what's new'")
    public void whatIsNewButtonCheck() {
        // assertTrue(cLionPage.checkIfWhatIsNewButtonClickable(), "Кнопка 'what's new' не активна");
        cLionPage.checkIfWhatIsNewButtonClickable();
    }

    @Disabled("Видеоролик убран со страницы https://www.jetbrains.com/clion/")
    @Test
    @DisplayName("Воспроизведение видео соответсвующее программе CLion")
    public void playerCheck() {
        String titleVideo = cLionPage.getNameOfVideo();
        assertEquals("CLion Quick Tour", titleVideo, "Воспроизводится другое видео");
    }

    @ParameterizedTest(name = "#{index} - активность кнопки №{0}")
    @CsvSource({"0", "1", "2", "3"})
    @DisplayName("Активны кнопки из блока Follow Us")
    public void followButtonsCheck(int numberButton) {
        Boolean StatusOfOneButton = cLionPage.checkIfFollowButtonsAreClickable(numberButton);
        assertTrue(StatusOfOneButton, String.format("Кнопка №%d неактивна", numberButton));
    }

    @Disabled("Поле с email убрано со страницы https://www.jetbrains.com/clion/")
    @Test
    @EnabledIfEnvironmentVariable(named = "DO_WE_HAVE_TIME", matches = "YES")
    @DisplayName("Возможность повторного ввода валидного email спустя 2 минуты")
    public void enterTwoTimesValidEmailWithWaiting() throws InterruptedException {
        String email = "qwer@google.com";
        cLionPage.enterEmail(email);
        TimeUnit.MINUTES.sleep(2);
        refresh();
        String answerAfterEnteringEmail = cLionPage.getAnswerAfterEnteringValidEmail(email);
        assertEquals("Thanks for your request!", answerAfterEnteringEmail, "Ooopps, something is wrong (I didn't get the right answer)");
    }

    @Disabled("Поле с email убрано со страницы https://www.jetbrains.com/clion/")
    @Test
    @DisplayName("Возможность повторного ввода валидного email спустя 2 минуты")
    public void enterTwoTimesValidEmailWithoutWaiting() {
        String email = "qwer@google.com";
        cLionPage.enterEmail(email);
        clearBrowserCookies();
        refresh();
        String answerAfterEnteringEmail = cLionPage.getAnswerAfterEnteringValidEmail(email);
        assertEquals("Thanks for your request!", answerAfterEnteringEmail, "Ooopps, something is wrong (I didn't get the right answer)");
    }

    @Disabled("Поле с email убрано со страницы https://www.jetbrains.com/clion/")
    @Test
    @DisplayName("Ввод пустого email")
    public void enterEmptyEmail() {
        String warningAnswer = cLionPage.getWarningAfterEnteringInvalidEmail("");
        assertEquals("This field is required.", warningAnswer, "Текст сообщения некорректен");
    }

    @Disabled("Поле с email убрано со страницы https://www.jetbrains.com/clion/")
    // Тесты на ввод невалидного email с разными положениями символа @
    // Другие проверки email не стал перечислять, чтобы не захломлять. Указал только три в качестве примера.
    @ParameterizedTest(name = "#{index} - проверка на НЕ валидность email {0}")
    @CsvSource({"sldkfj", "slkf@", "qwer@lsakjf"})
    @DisplayName("Ввод невалидного email")
    public void enterInvalidEmail(String email) {
        String warningAnswer = cLionPage.getWarningAfterEnteringInvalidEmail(email);
        assertEquals("Please enter a valid email address.", warningAnswer, "Текст сообщения некорректен");
    }

    @Test
    @DisplayName("Смена языка страницы на русский")
    public void changeLanguageOfPage() {
        String language = "Русский";
        cLionPage.changeLanguage(language);
        String urlPage = webdriver().driver().getCurrentFrameUrl();
        assertEquals("https://www.jetbrains.com/ru-ru/clion/", urlPage, "Открыта страница не с русским языком");
    }

    @Disabled("Раздел убран")
    @Test
    @DisplayName("В разделе 'Code analysis on the fly' есть три фото")
    public void checkScreenshotFromCodeAnalysisSection() {
        int countOfScreenshot = cLionPage.getCountOfScreenshotsInCodeAnalysisSection();
        assertEquals(3, countOfScreenshot, "Колиечество скриншотов для в разделе 'Code analysis on the fly' не равно 3");
    }

    @Test
    @DisplayName("Отображение заголовка в окне с инструкций по установке")
    public void checkIfInstructionHeaderTextIsDisplayedForWindows() {
        cLionPage.clickDownloadButton();
        cLionDownloadPage.clickWindowsButtonIfNotSelected();
        cLionDownloadPage.clickInstruction();
        assertTrue(cLionDownloadPage.checkIfHeaderInstructionIsDisplayed(), "Заголовок не отображается");
    }
}
