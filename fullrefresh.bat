@echo off
cls


echo ===================================================
echo Step 1/3 - Cleaning cache
echo ===================================================
echo.

call gradlew.bat cleanCache


echo.
echo ===================================================
echo Step 2/3 - Refreshing
echo ===================================================
echo.

call gradlew.bat setupDecompWorkspace --refresh-dependencies


echo.
echo ===================================================
echo Step 3/3 - Updating Eclipse
echo ===================================================
echo.

call gradlew.bat eclipse