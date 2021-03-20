import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StringUnpackingApplication {
    /**
     * Написать на Java программу распаковывания строки.
     * На вход поступает строка вида число[строка], на выход - строка, содержащая повторяющиеся подстроки.
     * <p>
     * Пример:
     * Вход: 3[xyz]4[xy]z
     * Выход: xyzxyzxyzxyxyxyxyz
     * <p>
     * Ограничения:
     * - одно повторение может содержать другое. Например: 2[3[x]y]  = xxxyxxxy
     * - допустимые символы на вход: латинские буквы, числа и скобки []
     * - числа означают только число повторений
     * - скобки только для обозначения повторяющихся подстрок
     * - входная строка всегда валидна.
     * <p>
     * Дополнительное задание:
     * Проверить входную строку на валидность.
     */

    public static void main(String[] args) {


//        String input = "a2[b3[c]d]4[efg]5[hi]j";

        String input = getInput();

        String result = unpackingString(getReversedMultipliersList(input), input);
        System.out.println(result);
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Enter the packed string! Examples: 3[xyz]4[xy]z 2[3[x]y]");
        } while (!validateInput(input = scanner.nextLine()));
        return input;
    }

    private static boolean validateInput(String input) {
        String brackets = input.replaceAll("[0-9a-zA-Z]", "");
        int bracketsLength = brackets.length();

        if (bracketsLength == 0) {
            return false;
        } else {
            while (true) {
                brackets = brackets.replaceAll("(\\[\\])", "");

                if (brackets.length() == 0) {
                    break;
                } else if (brackets.length() == bracketsLength) {
                    return false;
                } else {
                    bracketsLength = brackets.length();
                }
            }
        }
        return true;
    }

    private static List<Integer> getReversedMultipliersList(String input) {
        return Arrays.stream(
                input.replaceAll("[^0-9]", " ").trim().split("\\s+"))
                .map(Integer::parseInt).sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    private static String unpackingString(List<Integer> multipliers, String input) {
        for (int multiplier : multipliers) {

            String multiplierString = Integer.toString(multiplier);
            int multiplierIndex = input.lastIndexOf(multiplierString);

            String start = input.substring(0, multiplierIndex);
            String temp = input.substring(multiplierIndex + multiplierString.length() + 1);

            int closeIndex = temp.indexOf(']');
            String repeat = temp.substring(0, closeIndex).repeat(multiplier);
            String end = temp.substring(closeIndex + 1);

            input = start.concat(repeat).concat(end);
        }
        return input;
    }
}
