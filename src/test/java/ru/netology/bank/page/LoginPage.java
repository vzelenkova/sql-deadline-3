package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.bank.data.DataHelper;

import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }

    public void verifyPageVisible() {
        loginField.shouldBe(visible);
    }

    private void enterCredentials(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public AuthorizationCodePage validLogin(DataHelper.AuthInfo authInfo) {
        enterCredentials(authInfo.getLogin(), authInfo.getPassword());
        return new AuthorizationCodePage();
    }

    public void login(DataHelper.AuthInfo authInfo) {
        enterCredentials(authInfo.getLogin(), authInfo.getPassword());
        verifyErrorNotificationVisibility();
    }

    public void clearForm() {
        loginField.doubleClick().sendKeys(Keys.BACK_SPACE);
        passwordField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }
}
