# StringUnpackingApplication

- Приложение для распаковки строк: 2[a]3[b2[c]] -> aabccbccbcc.
- Исполняемый файл -> jars/StringUnpackingApplication.jar
- Варианты запуска:
    1. java -jar StringUnpackingApplication.jar 2[3[x]y]
    2. java -jar StringUnpackingApplication.jar
- В 1 варианте, если строка валидна — выводит результат, иначе срабатывает 2 вариант — ручной ввод из консоли.