# DECISIONS

## Решение 1
Метод `OrderCalculator.errorMessage(...)` проверяет количество, цену и скидку в фиксированном порядке и возвращает
сообщение либо `null`. `isValid(...)` передает ему, поэтому логика и текст ошибки не расходятся.
В `main` расчет запускается только после успешной проверки, а пустой или текстовый ввод проверяется
до чтения значения `hasNextInt()` / `hasNextDouble()`.

## Решение 2
Границы `MIN_QUANTITY`, `MAX_QUANTITY`, `MAX_UNIT_PRICE`, `MIN_DISCOUNT`, `MAX_DISCOUNT` и ставка НДС `VAT_RATE`
заданы константами `OrderCalculator`. Вариант 2 (максимальная скидка 15%) — это одна строка: `MAX_DISCOUNT = 15.0`.
Каждая формула `calculateBase`, `applyDiscount`, `calculateVat` записана один раз, `calculateTotal` собирает их
в порядке скидка -> НДС.