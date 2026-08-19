package com.tienda.zely.util;

import com.tienda.zely.dto.product.ProductResponseDto;
import com.tienda.zely.exception.ConflictException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public final class ProductExcelHandler {

    private static final String[] EXPORT_HEADERS = {
            "codigoProducto", "nombre", "codigoTipoProducto", "precio"
    };

    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    private ProductExcelHandler() {
    }

    public static void writeProducts(OutputStream outputStream, List<ProductResponseDto> products) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("productos");
            Row headerRow = sheet.createRow(0);
            for (int column = 0; column < EXPORT_HEADERS.length; column++) {
                headerRow.createCell(column).setCellValue(EXPORT_HEADERS[column]);
            }

            int rowIndex = 1;
            for (ProductResponseDto product : products) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getCodigoProducto());
                row.createCell(1).setCellValue(product.getNombre());
                row.createCell(2).setCellValue(product.getCodigoTipoProducto());
                row.createCell(3).setCellValue(product.getPrecio());
            }

            for (int column = 0; column < EXPORT_HEADERS.length; column++) {
                sheet.autoSizeColumn(column);
            }

            workbook.write(outputStream);
        }
    }

    public static List<String[]> readDataRows(MultipartFile file) {
        validateExcelFile(file);

        List<String[]> rows = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                throw new ConflictException("El archivo Excel no contiene hojas");
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isEmptyRow(row)) {
                    continue;
                }
                rows.add(readRowValues(row));
            }
        } catch (IOException e) {
            throw new ConflictException("No se pudo procesar el archivo Excel");
        }

        if (rows.isEmpty()) {
            throw new ConflictException("El archivo Excel no contiene productos para procesar");
        }
        return rows;
    }

    private static void validateExcelFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ConflictException("El archivo Excel esta vacio o no fue enviado");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
            throw new ConflictException("El archivo debe tener formato Excel (.xlsx)");
        }
    }

    private static boolean isEmptyRow(Row row) {
        short firstCell = row.getFirstCellNum();
        short lastCell = row.getLastCellNum();
        if (firstCell < 0) {
            return true;
        }
        for (int cellIndex = firstCell; cellIndex < lastCell; cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            if (cell != null && !DATA_FORMATTER.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String[] readRowValues(Row row) {
        short lastCell = row.getLastCellNum();
        int columnCount = Math.max(lastCell, 0);
        String[] values = new String[columnCount];
        for (int cellIndex = 0; cellIndex < columnCount; cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            values[cellIndex] = cell == null ? "" : DATA_FORMATTER.formatCellValue(cell).trim();
        }
        return values;
    }
}
