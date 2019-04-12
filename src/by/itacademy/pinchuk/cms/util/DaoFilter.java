package by.itacademy.pinchuk.cms.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to build filters, orderings and limits for SQL queries in DAO layer.
 * Each EntityDao class have number of constants that contains
 * relation name of table column in query.
 * <p>
 * NOTE: if you wish to use this Helper, pay your attention to
 * build SELECT query in your DAO classes ending with "WHERE TRUE "
 * statement.
 * <p>
 * EXAMPLE:
 * <p>
 * String filter = DaoFilter.builder()
 * .addCondition(CommentDao.FILTER_BY_CONTENT_ID, DaoFilter.MatchType.EQ, "1")
 * .addLogic(DaoFilter.Logic.AND)
 * .addCondition(CommentDao.FILTER_BY_STATUS, DaoFilter.MatchType.EQ, CommentStatus.PUBLISHED.name())
 * .build();
 * <p>
 * RESULT FILTER QUERY: "AND c.content_id = 1 AND c.status = PUBLISHED"
 */

@UtilityClass
public class DaoFilter {

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<String> conditions = new ArrayList<>();
        private List<String> ordering = new ArrayList<>();
        @Getter
        @Setter
        @Accessors(chain = true)
        private int limit;

        public Builder addCondition(String with, MatchType how, String what) {
            conditions.add(String.join(" ", with, how.getValue(), wrapWithQuotes(what)));
            return this;
        }

        public Builder addLogic(Logic logic) {
            conditions.add(logic.getValue());
            return this;
        }

        public Builder addOrdering(String by, Ordering order) {
            ordering.add(by + " " + order.getValue());
            return this;
        }

        public Builder addOrdering(String by) {
            ordering.add(by + " " + Ordering.ASC.getValue());
            return this;
        }

        public String build() {
            String conditionsString = (conditions.size() > 0)
                    ? "AND " + String.join(" ", conditions)
                    : "";
            String orderingString = (ordering.size() > 0)
                    ? "ORDER BY " + String.join(", ", ordering)
                    : "";
            String limitString = (limit > 0)
                    ? "LIMIT " + limit
                    : "";
            return String.join(" ", conditionsString, orderingString, limitString);
        }

        private String wrapWithQuotes(String value) {
            return String.format("'%s'", value);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum MatchType {

        EQ("="),
        NOT_EQ("<>"),
        GATHER(">"),
        LESS("<");

        private String value;
    }

    @Getter
    @AllArgsConstructor
    public enum Logic {
        AND(" AND "),
        OR(" OR ");

        private String value;
    }

    @Getter
    @AllArgsConstructor
    public enum Ordering {
        ASC(" ASC "),
        DESC(" DESC ");

        private String value;
    }
}
