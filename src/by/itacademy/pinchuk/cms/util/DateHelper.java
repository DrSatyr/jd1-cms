package by.itacademy.pinchuk.cms.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@UtilityClass
public class DateHelper {

    public static LocalDate convertToLocalDate(java.sql.Date date) {
        return Objects.nonNull(date) ? date.toLocalDate() : null;
    }

    public static LocalDateTime convertToLocalDateTime(java.sql.Timestamp timestamp) {
        return Objects.nonNull(timestamp) ? timestamp.toLocalDateTime() : null;
    }

    public static java.sql.Date convertToSqlDate(LocalDate date) {
        return Objects.nonNull(date) ? java.sql.Date.valueOf(date) : null;
    }

    public static java.sql.Timestamp convertToSqlTimestamp(LocalDateTime dateTime) {
        return Objects.nonNull(dateTime) ? java.sql.Timestamp.valueOf(dateTime) : null;
    }
}
