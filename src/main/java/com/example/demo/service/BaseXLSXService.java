package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class BaseXLSXService {

    protected static final short PINK = IndexedColors.PINK.getIndex();
    protected static final short BLACK = IndexedColors.BLACK.getIndex();
    protected static final short BLUE = IndexedColors.BLUE.getIndex();

    protected static final boolean BOLD = true;
    protected static final boolean NORMAL = false;

    protected static final BorderStyle THICK = BorderStyle.THICK;
    protected static final BorderStyle THIN = BorderStyle.THIN;
    protected static final BorderStyle NONE = BorderStyle.NONE;

    protected CellStyle actualStyle = null;

    protected Map<String, CellStyle> styles = new HashMap<>();

    protected void setActiveStyle(String key) {
        if (styles.containsKey(key)) {
            actualStyle = styles.get(key);
        }
    }

    protected void initFont(Workbook wb, short color, boolean status, String styleKey) {
        if (styles.containsKey(styleKey)) {
            Font font = wb.createFont();
            font.setColor(color);
            font.setBold(status);
            styles.get(styleKey).setFont(font);
        }
    }

    protected void initStyle(Workbook wb, BorderStyle thickness, short borderColor, String key) {
        if (!styles.containsKey(key)) {
            CellStyle style = wb.createCellStyle();
            borderThickness(style, thickness, borderColor);
            styles.put(key, style);
        }
    }

    private void borderThickness(CellStyle style, BorderStyle thickness, short color) {
        style.setBorderBottom(thickness);
        style.setBorderTop(thickness);
        style.setBorderLeft(thickness);
        style.setBorderRight(thickness);
        style.setTopBorderColor(color);
        style.setBottomBorderColor(color);
        style.setLeftBorderColor(color);
        style.setRightBorderColor(color);
    }

    protected Cell createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        if (actualStyle != null) {
            cell.setCellStyle(actualStyle);
        }
        return cell;
    }

    /**
     * Joli formatting 🤩 (ici count est le nombre de colonne crée on l'utilise pour
     * resize toute les colonnes)
     **/
    protected void autoResizeSheet(Sheet sheet) {
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    protected void clearAllStyles() {
        styles = new HashMap<>();
        actualStyle = null;
    }
}
