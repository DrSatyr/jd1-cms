package by.itacademy.pinchuk.cms.formatter;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static by.itacademy.pinchuk.cms.util.ReqParamUtils.EMPTY;
import static by.itacademy.pinchuk.cms.util.ReqParamUtils.isSet;

@UtilityClass
public class DateFormat {

    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public String format(LocalDate localDate) {
        return Objects.nonNull(localDate)
                ? localDate.format(FORMATTER)
                : EMPTY;
    }

    public LocalDate format(String value) {
        return  isSet(value)
                ? LocalDate.parse(value, FORMATTER)
                : null;
    }
}
