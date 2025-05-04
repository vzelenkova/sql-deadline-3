package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bank.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;

public class AuthorizationCodePage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public AuthorizationCodePage() {
    }

    public void verifyPageVisible() {
        codeField.shouldBe(visible);
    }

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }

    private void enterCodeAndSubmit(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public void validVerify(DataHelper.VerificationCode code) {
        enterCodeAndSubmit(code.getCode());
        new PersonalAccountPage();
    }

    public void invalidVerify(DataHelper.VerificationCode code) {
        enterCodeAndSubmit(code.getCode());
        verifyErrorNotificationVisibility();
    }
}
