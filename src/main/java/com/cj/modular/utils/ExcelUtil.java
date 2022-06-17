package com.cj.modular.utils;

import com.cj.modular.entity.Movies;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Excel数据
 * @author Caoj
 * @date 8/30/2021 11:16
 */
@Slf4j
public class ExcelUtil {
    private XSSFSheet sheet;
    //定义Excel的文件路径
    private String filePath = "D:\\files\\豆瓣\\movie_details.xlsx";
    private String sheetName = "movie_details";

    public ExcelUtil(String filePath, String sheetName) {
        if (StringUtils.isEmpty(filePath)) {
            filePath = this.filePath;
        }
        if (StringUtils.isEmpty(sheetName)) {
            sheetName = this.sheetName;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            sheet = sheets.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据行和列获取单元格数据
     * @param row 行元素
     * @param column 列元素
     * @return java.lang.String
     * @author Caoj created at 8/30/2021 11:21
     */
    public String getExcelDataByIndex(int row, int column) {
        XSSFRow row1 = sheet.getRow(row);
        return row1.getCell(column).toString();
    }

    public List<Movies> readExcelData() throws ParseException {
        int rows = sheet.getPhysicalNumberOfRows();
        List<Movies> movies = new ArrayList<>();
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
//            int column = row.getPhysicalNumberOfCells();
            Movies movie = new Movies();
            movie.setName(row.getCell(1).getStringCellValue());
            movie.setYear(Integer.parseInt(row.getCell(2).getStringCellValue()));
            movie.setPoster(row.getCell(3).getStringCellValue());
            movie.setDirector(row.getCell(4).getStringCellValue());
//            row.getCell(6).setCellType(CellType.STRING);
            movie.setScriptWriter(row.getCell(5).getStringCellValue());
            movie.setCast(row.getCell(6).getStringCellValue());
            movie.setType(row.getCell(7).getStringCellValue());
            movie.setLocation(row.getCell(8).getStringCellValue());
            movie.setDuration(row.getCell(9).getStringCellValue());
            movie.setDouban(Float.parseFloat(row.getCell(10).getStringCellValue()));
            movies.add(movie);
//            for (int j = 0; j < column; j++) {
//                XSSFCell cell = row.getCell(j);
//                if (cell.getCellType() == CellType.STRING) {
//                } else if (cell.getCellType() == CellType.NUMERIC) {
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    } else {
//                        cell.setCellType(CellType.STRING);
//                    }
//                } else if (cell.getCellType() == CellType.BLANK) {
//                } else if (cell.getCellType() == CellType.FORMULA) {
//                    cell.setCellType(CellType.STRING);
//                }
//            }
        }
        return movies;
    }
}
