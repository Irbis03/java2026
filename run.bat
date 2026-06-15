@echo off
:: Настройка кодировки UTF-8 в консоли, чтобы корректно отображался русский текст
chcp 65001 > nul

:: --- НАСТРОЙКИ ---
set APP_NAME=hw1
set MAIN_CLASS=mfti.java.hw1.Main
set SRC_DIR=src
set TEST_DIR=test
set LIB_DIR=lib
set OUT_DIR=out
set OUT_TEST_DIR=out_test
set JAR_DIR=dist
set JAR_NAME=%APP_NAME%.jar

:: Путь к JUnit консольному запускчику (проверьте, чтобы имя файла в либ совпадало)
set JUNIT_JAR=%LIB_DIR%\junit-platform-console-standalone-6.1.0-RC1.jar

echo ============================================
echo      СТАРТ СБОРКИ, ТЕСТИРОВАНИЯ И ЗАПУСКА
echo ============================================

echo === Старт автоматической сборки и запуска ===

:: 1. Очистка предыдущих результатов сборки
echo [1/5] Очистка папок сборки...
if exist %OUT_DIR% rmdir /s /q %OUT_DIR%
if exist %OUT_TEST_DIR% rmdir /s /q %OUT_TEST_DIR%
if exist %JAR_DIR% rmdir /s /q %JAR_DIR%

mkdir %OUT_DIR%
mkdir %OUT_TEST_DIR%
mkdir %JAR_DIR%

:: 2. КОМПИЛЯЦИЯ ОСНОВНОГО КОДА
echo [2/5] Компиляция основного кода приложения...
dir /s /b %SRC_DIR%\*.java > sources.txt
javac -encoding UTF-8 -d %OUT_DIR% @sources.txt
if %errorlevel% neq 0 goto :error
del sources.txt

:: 3. КОМПИЛЯЦИЯ ТЕСТОВ
echo [3/5] Компиляция JUnit тестов...
dir /s /b %TEST_DIR%\*.java > test_sources.txt
javac -encoding UTF-8 -d %OUT_TEST_DIR% -cp "%OUT_DIR%;%LIB_DIR%\*" @test_sources.txt
if %errorlevel% neq 0 goto :error
del test_sources.txt

:: 4. ЗАПУСК ТЕСТОВ С ПОМОЩЬЮ JUNIT CONSOLE LAUNCHER
echo [4/5] Запуск JUnit тестов...
echo --------------------------------------------------
java -jar "%JUNIT_JAR%" -cp "%OUT_DIR%;%OUT_TEST_DIR%" --scan-classpath
if %errorlevel% neq 0 (
    echo.
    echo 🔴 [ОШИБКА] Тесты провалены! Сборка JAR-архива отменена.
    goto :error
)

:: 5. СОЗДАНИЕ JAR-АРХИВА
echo [5/5] Сборка приложения в JAR...
jar cfe %JAR_DIR%\%JAR_NAME% %MAIN_CLASS% -C %OUT_DIR% .
if %errorlevel% neq 0 goto :error

echo.
echo ============================================
echo СБОРКА УСПЕШНО ЗАВЕРШЕНА!
echo Путь к файлу: %JAR_DIR%\%JAR_NAME%
echo ============================================
echo.
echo Запуск собранного приложения...
echo --------------------------------------------------
java -jar %JAR_DIR%\%JAR_NAME%
echo --------------------------------------------------
pause
exit /b 0

:: Блок обработки ошибок
:error
echo.
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !! ОШИБКА: Сборка остановлена.          !!
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
if exist sources.txt del sources.txt
if exist test_sources.txt del test_sources.txt
pause
exit /b 1