#!/bin/bash

# Настройка кодировки терминала (для корректного вывода русского языка)
export LANG=ru_RU.UTF-8

# НАСТРОЙКА ПАПОК И ПЕРЕМЕННЫХ
SRC_DIR="src/main"
TEST_DIR="src/test"
LIB_DIR="lib"
OUT_DIR="out"
OUT_TEST_DIR="out_test"
ARTIFACT_DIR="artifacts"
JAR_NAME="hw1.jar"
MAIN_CLASS="mfti.java.hw1.Main" # Замените на ваш главный класс

# Функция для обработки ошибок
error_exit() {
    echo -e "\n\e[31m🔴 [ОШИБКА] Сборка аварийно остановлена на этапе: $1\e[0m"
    exit 1
}

# 1. ОЧИСТКА СТАРЫХ СБОРОК
echo "[1/5] Очистка папок сборки..."
rm -rf "$OUT_DIR" "$OUT_TEST_DIR" "$ARTIFACT_DIR"
mkdir -p "$OUT_DIR" "$OUT_TEST_DIR" "$ARTIFACT_DIR"

# 2. КОМПИЛЯЦИЯ ОСНОВНОГО КОДА
echo "[2/5] Компиляция основного кода..."
# Находим все файлы .java в src и передаем их в javac
find "$SRC_DIR" -name "*.java" > sources.txt
javac -encoding UTF-8 -d "$OUT_DIR" @sources.txt || error_exit "Компиляция основного кода"
rm sources.txt

# 3. КОМПИЛЯЦИЯ ТЕСТОВ
echo "[3/5] Компиляция JUnit тестов..."
find "$TEST_DIR" -name "*.java" > test_sources.txt

# В Linux разделителем в classpath является ДВОЕТОЧИЕ (:), а не точка с запятой (;)
# Также для подключения всех jar-ников используется синтаксис "$LIB_DIR/*"
javac -encoding UTF-8 -d "$OUT_TEST_DIR" -cp "$OUT_DIR:$LIB_DIR/*" @test_sources.txt || error_exit "Компиляция тестов"
rm test_sources.txt

# 4. ЗАПУСК ТЕСТОВ
echo "[4/5] Запуск JUnit тестов..."
echo "--------------------------------------------------"

java -jar $LIB_DIR/junit-platform-console-standalone-6.1.0-RC1.jar execute --scan-class-path="$OUT_TEST_DIR"

# Проверяем статус выполнения тестов
if [ $? -ne 0 ]; then
    echo -e "\n\e[31m🔴 [ОШИБКА] Тесты провалены! Сборка JAR-архива отменена.\e[0m"
    exit 1
fi
echo "--------------------------------------------------"
echo -e "\e[32m🟢 Все тесты успешно пройдены!\e[0m"

# 5. СБОРКА JAR АРХИВА
echo "[5/5] Упаковка приложения в JAR..."
jar cfe "$ARTIFACT_DIR/$JAR_NAME" "$MAIN_CLASS" -C "$OUT_DIR" . || error_exit "Создание JAR"

echo -e "\n\e[32m🎉 СБОРКА УСПЕШНО ЗАВЕРШЕНА!\e[0m"
echo "Готовый файл: $ARTIFACT_DIR/$JAR_NAME"

# 6 Запуск проекта
echo "Запуск jar-архива: $ARTIFACT_DIR/$JAR_NAME"
java -jar $ARTIFACT_DIR/$JAR_NAME