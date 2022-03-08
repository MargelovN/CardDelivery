package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDelivery{
    String generateDate(int days, String datePattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(datePattern));
    }
    @Test
    void shouldOrderForCardDelivery() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Маргелов Никита");
        $("[data-test-id=phone] input").setValue("+79990001122");
        $("[data-test-id=agreement]").click();
        $$(".button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }
}
