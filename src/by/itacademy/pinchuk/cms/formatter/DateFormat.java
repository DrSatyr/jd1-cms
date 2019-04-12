package by.itacademy.pinchuk.cms.formatter;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static by.itacademy.pinchuk.cms.util.RequestHelper.EMPTY_STRING;
import static by.itacademy.pinchuk.cms.util.RequestHelper.isSetParam;

@UtilityClass
public class DateFormat {

    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public String format(LocalDate localDate) {
        return Objects.nonNull(localDate)
                ? localDate.format(FORMATTER)
                : EMPTY_STRING;
    }

    public LocalDate format(String value) {
        return  isSetParam(value)
                ? LocalDate.parse(value, FORMATTER)
                : null;
    }
}
