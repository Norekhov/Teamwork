package pro.sky.star.recommendations.repository;

import java.util.List;

public class SqlUtil {

    public static void validateProductType(String productType) {
        if (!productType.matches("DEBIT|CREDIT|INVEST|SAVING")) {
            throw new IllegalArgumentException("Неправильное название типа продукта");
        }
    }

    public static void validateSumOrCount(String sumOrCount) {
        if (!sumOrCount.matches("SUM|COUNT")) {
            throw new IllegalArgumentException("Неправильный аргумент для суммы или количества");
        }
    }

    public static void validateOperator(String operator) {
        if (!operator.matches(">=|<=|>|<|=")) {
            throw new IllegalArgumentException("Неправильный оператор сравнения: " + operator);
        }
    }

    public static void validateComparisonArgs(List<String> args) {
        if (args.size() < 4) {
            throw new IllegalArgumentException("Недостаточно аргументов для сравнения");
        }

        validateProductType(args.get(0));

        if (!args.get(1).matches("WITHDRAW|DEPOSIT")) {
            throw new IllegalArgumentException("Неправильное название типа транзакции");
        }

        try {
            Integer.parseInt(args.get(3));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа: " + args.get(3), e);
        }
    }
}
