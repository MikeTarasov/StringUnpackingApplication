import java.util.*;
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
    private static List<Integer> multipliers = new ArrayList<>();

    public static void main(String[] args) {
        runMe(args);
    }

    public static void runMe(String[] args) {
        String input;
        if (args.length == 0) {
            input = manualInput();
        } else {
            input = args[0];
            if (isInputInvalid(input)) {
                System.out.println("Invalid argument!");
                input = manualInput();
            }
        }

        String result = unpackingString(input);
        System.out.println(result);
    }

    private static String manualInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Enter a valid packed string! Example: 3[xyz]4[xy]z2[3[x]y]");
            input = scanner.nextLine();

            if (isInputInvalid(input)) {
                System.out.println("Wrong input!");
            } else {
                break;
            }
        }
        return input;
    }

    private static boolean isInputInvalid(String input) {
        String brackets = input.replaceAll("[0-9a-zA-Z]", "");
        int bracketsLength = brackets.length();

        if (bracketsLength == 0) {
            return true;
        } else {
            while (true) {
                brackets = brackets.replaceAll("(\\[\\])", "");

                if (brackets.length() == 0) {
                    break;
                } else if (brackets.length() == bracketsLength) {
                    return true;
                }
                bracketsLength = brackets.length();
            }
        }

        multipliers = getReversedMultipliersList(input);
        if (multipliers.isEmpty()) {
            return true;
        }

        char[] inputChars = input.toCharArray();
        for (int i = 0; i < inputChars.length; i++) {
            if (inputChars[i] == '[' && (i == 0 || inputChars[i - 1] < '0' || inputChars[i - 1] > '9')) {
                return true;
            } else if (inputChars[i] >= '0' && inputChars[i] <= '9' &&
                    (inputChars[i + 1] != '[' || (inputChars[i + 1] < '0' && inputChars[i + 1] > '9'))) {
                return true;
            }
        }

        return false;
    }

    private static List<Integer> getReversedMultipliersList(String input) {
        return Arrays.stream(
                input.replaceAll("[^0-9]", " ").trim().split("\\s+"))
                .map(Integer::parseInt).sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    private static String unpackingString(String input) {
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