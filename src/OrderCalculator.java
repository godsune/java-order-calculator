package ru.edu.pr01;

import java.util.Locale;
import java.util.Scanner;


public class OrderCalculator {
    public static final double VAT_RATE = 20.0;
    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 10_000;
    public static final double MAX_UNIT_PRICE = 5_000_000.0;
    public static final double MIN_DISCOUNT = 0.0;
    // вариант 2: максимальная скидка 15% 
    public static final double MAX_DISCOUNT = 15.0;

    public static boolean isValid(int quantity, double unitPrice, double discountPercent) {
        return errorMessage(quantity, unitPrice, discountPercent) == null;
    }

    // null или сообщение об ошибке
    public static String errorMessage(int quantity, double unitPrice, double discountPercent) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            return "Количество должно быть от " + MIN_QUANTITY + " до " + MAX_QUANTITY;
        }
        if (Double.isNaN(unitPrice) || unitPrice <= 0 || unitPrice > MAX_UNIT_PRICE) {
            return "Цена должна быть больше 0 и не больше " + (long) MAX_UNIT_PRICE + " рублей";
        }
        if (Double.isNaN(discountPercent) || discountPercent < MIN_DISCOUNT || discountPercent > MAX_DISCOUNT) {
            return "Скидка должна быть от " + (int) MIN_DISCOUNT + " до " + (int) MAX_DISCOUNT + " %";
        }
        return null;
    }

    // базовая сумма = количество * цена
    public static double calculateBase(int quantity, double unitPrice) {
        return (double) quantity * unitPrice;
    }

    // сумма после скидки
    public static double applyDiscount(double base, double discountPercent) {
        return base - base * discountPercent / 100.0;
    }

    // начисление НДС
    public static double calculateVat(double discounted, double vatPercent) {
        return discounted * vatPercent / 100.0;
    }

    // Итог = (база - скидка) + НДС
    public static double calculateTotal(int quantity, double unitPrice, double discountPercent, double vatPercent) {
        double base = calculateBase(quantity, unitPrice);
        double discounted = applyDiscount(base, discountPercent);
        return discounted + calculateVat(discounted, vatPercent);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        try {
            System.out.print("Количество: ");
            int quantity = readInt(in);
            System.out.print("Цена за единицу (руб., через точку): ");
            double price = readDouble(in);
            System.out.print("Скидка, %: ");
            double discount = readDouble(in);

            String error = errorMessage(quantity, price, discount);
            if (error != null) {
                System.out.println("Ошибка: " + error + ". Расчёт не выполнен.");
                return;
            }
            double base = calculateBase(quantity, price);
            double discounted = applyDiscount(base, discount);
            double vat = calculateVat(discounted, VAT_RATE);
            System.out.printf(Locale.US, "Базовая сумма: %.2f руб.%n", base);
            System.out.printf(Locale.US, "После скидки: %.2f руб.%n", discounted);
            System.out.printf(Locale.US, "НДС (%.0f%%): %.2f руб.%n", VAT_RATE, vat);
            System.out.printf(Locale.US, "Итого: %.2f руб.%n", discounted + vat);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage() + ". Расчёт не выполнен.");
        }
    }

    private static int readInt(Scanner in) {
        if (!in.hasNext()) throw new IllegalArgumentException("значение не введено");
        if (!in.hasNextInt()) throw new IllegalArgumentException("«" + in.next() + "» не является целым числом");
        return in.nextInt();
    }

    private static double readDouble(Scanner in) {
        if (!in.hasNext()) throw new IllegalArgumentException("значение не введено");
        if (!in.hasNextDouble()) throw new IllegalArgumentException("«" + in.next() + "» не является числом");
        return in.nextDouble();
    }
}
