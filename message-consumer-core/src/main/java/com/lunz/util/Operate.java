package com.lunz.util;

/**
 * @author Liuruixia
 * @Description:
 * @date 2019/10/08
 */
public final class Operate {
    public static enum OperationType {
        /**
         * read the current state of a record, most typically during snapshots.
         */
        READ("r"),
        /**
         * created
         */
        CREATE("c"),
        /**
         * updated
         */
        UPDATE("u"),
        /**
         * deleted
         */
        DELETE("d");
        private final String code;

        private OperationType(String code) {
            this.code = code;
        }

        public static OperationType forCode(String code) {
            for (OperationType op : OperationType.values()) {
                if (op.code().equalsIgnoreCase(code)) {
                    return op;
                }
            }
            return null;
        }

        public String code() {
            return code;
        }
    }
}

