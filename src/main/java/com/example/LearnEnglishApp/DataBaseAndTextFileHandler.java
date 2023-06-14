package com.example.LearnEnglishApp;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseAndTextFileHandler extends DatabaseHandler {

    private static TypeHandler typeHandler = TypeHandler.DatabaseTypeHandler;

    public boolean needTextFileMode() {
        boolean needTextFileMode;
        if (getTypeHandler() == TypeHandler.TextFileTypeHandler && isReconnectionQuestionAsked())
            needTextFileMode = true;
        else needTextFileMode = !(reconnection());
        if (needTextFileMode) typeHandler = TypeHandler.TextFileTypeHandler;
        return needTextFileMode;
    }

    public static TypeHandler getTypeHandler() {
        return typeHandler;
    }

    @Override
    public ArrayList<String> getArrayListWords(String column) throws SQLException, IOException {
        ArrayList<String> list = new ArrayList<>();
        if (needTextFileMode()) {

            //Получаем таблицу
            String table = Const.getTableOfColumn(column);

            //Получаем имя файла
            Path textFilePatch = Const.getTextFilePatch(table);

            //Получаем список колонок таблицы
            ArrayList<String> columns = Const.getColumnsOfTable(table);

            //получаем данные из файла
            FileInputStream fileInputStream = new FileInputStream(textFilePatch.toString());
            Workbook workbook = new HSSFWorkbook();
            for (int i = 0; i < columns.size(); i++) {

                String columnCell = workbook.getSheetAt(0).getRow(0).getCell(i).getStringCellValue();
                if (columnCell.equals(column)) {
                    boolean isFirst = true;
                    for (Row row : workbook.getSheetAt(0)) {
                        if (isFirst) {
                            isFirst = false;
                            continue;
                        }
                        String cell = workbook.getSheetAt(0).getRow(0).getCell(i).getStringCellValue();
                        if (cell.equals("")) break;
                        list.add(cell);
                    }
                }
            }

            fileInputStream.close();
        } else return super.getArrayListWords(column);
        return list;
    }

    @Override
    public boolean reconnection() {
        boolean result = super.reconnection();
        if (!result && isReconnectionQuestionAsked()) typeHandler = TypeHandler.TextFileTypeHandler;
        return result;
    }

    public void exportDatabaseTableToExcel(String table) throws SQLException, IOException {

        Path excelFilePath = Const.getTextFilePatch(table);
        boolean isFile = Files.exists(excelFilePath);
        if (!needTextFileMode() || !isFile) {
            //Создаем первый лист файла
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Reviews");


        //Делаем запрос к БД
        ResultSet resultSet;
        String stringSQL = "SELECT * FROM " + table;
        resultSet = getResulSet(stringSQL);

        //Создаем файл Excel
        writeHeaderLine(sheet, table);

        if (!needTextFileMode()) {
            writeDataLines(resultSet, workbook, sheet, table);
        }

        //Записываем файл
        FileOutputStream outputStream = new FileOutputStream(excelFilePath.toString());
        workbook.write(outputStream);
        workbook.close();
        }
    }

    private void writeHeaderLine(HSSFSheet sheet, String table) {

        Row headerRow = sheet.createRow(0);
        Cell headerCell = null;
        ArrayList<String> columns = Const.getColumnsOfTable(table);

        for (int i = 0; i < columns.size(); i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columns.get(i));
        }
    }

    private void writeDataLines(ResultSet result, HSSFWorkbook workbook, HSSFSheet sheet, String table) throws SQLException {
        ArrayList<String> columns = Const.getColumnsOfTable(table);
        //Начинаем со второй строки (rowCount=1), т.к. в первой строке названия колонок
        int rowCount = 1;

        while (result.next()) {
            Row row = sheet.createRow(rowCount++);
            Cell cell = null;

            for (int i = 0; i < columns.size(); i++) {
                String resultColumn = result.getString(columns.get(i));
                cell = row.createCell(i);
                cell.setCellValue(resultColumn);
            }
        }
    }
}
