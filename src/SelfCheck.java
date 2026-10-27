package ru.edu.pr01;

import static ru.edu.pr01.OrderCalculator.*;

public class SelfCheck {
    private static int failed = 0;

    public static void main(String[] args) {
        check("типовое значение 12;2490.50;5 -> корректно", isValid(12, 2490.50, 5));
        check("итог типового значения = 34070.04", eq(calculateTotal(12, 2490.50, 5, VAT_RATE), 34070.04));
        check("quantity = MIN_QUANTITY допустимо", isValid(MIN_QUANTITY, 100, 5));
        check("quantity = 0 недопустимо", !isValid(0, 100, 5));
        check("quantity = MAX_QUANTITY допустимо", isValid(MAX_QUANTITY, 100, 5));
        check("quantity = MAX_QUANTITY + 1 недопустимо", !isValid(MAX_QUANTITY + 1, 100, 5));
        check("price = 0 недопустимо", !isValid(1, 0, 5));
        check("price = 0.01 допустимо", isValid(1, 0.01, 5));
        check("price = MAX_UNIT_PRICE допустимо", isValid(1, MAX_UNIT_PRICE, 5));
        check("price = MAX_UNIT_PRICE + 0.01 недопустимо", !isValid(1, MAX_UNIT_PRICE + 0.01, 5));
        check("price = -20 недопустимо", !isValid(5, -20, 5));
        check("discount = 0 допустимо", isValid(1, 100, 0));
        check("discount = MAX_DISCOUNT (15) допустимо", isValid(1, 100, MAX_DISCOUNT));
        check("discount = MAX_DISCOUNT + 1 (16) недопустимо", !isValid(1, 100, MAX_DISCOUNT + 1));
        check("discount = -1 недопустимо", !isValid(1, 100, -1));
        check("NaN в цене недопустим", !isValid(1, Double.NaN, 5));
        check("скидка применяется как процент: 1000, 10% -> 900", eq(applyDiscount(1000, 10), 900));
        check("НДС считается после скидки: 900, 20% -> 180", eq(calculateVat(900, 20), 180));
        System.out.println(failed == 0 ? "ALL PASS" : "FAILED: " + failed);
        if (failed != 0) System.exit(1);
    }

    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS " : "FAIL ") + name);
        if (!ok) failed++;
    }

    private static boolean eq(double a, double b) {
        return Math.abs(a - b) < 0.005;
    }
}
