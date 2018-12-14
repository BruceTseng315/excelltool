package com.tool;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 16:36
 */
public class ExportWordUtil {
   public static void exportEordTest(WordStatistics wordStatistics)throws Exception{


       //读取word 模板源文件
       FileInputStream fileInputStream = new FileInputStream(Constants.reportModuleFilePath);

        // POIFSFileSystem pfs = new POIFSFileSystem(fileInputStream);
       XWPFDocument document = new XWPFDocument(fileInputStream);

        //获取所有表格
       List<XWPFTable> tables = document.getTables();
        //这里简单取第一个表格
       XWPFTable table = tables.get(0);

       List<XWPFTableRow> rows = table.getRows();
       //表格的插入行有两种方式，这里使用addNewRowBetween，因为这样会保留表格的样式，就像我们在word文档的表格中插入行一样。注意这里不要使用insertNewTableRow方法插入新行，这样插入的新行没有样式，很难看
       System.out.println(table.getRows().size());
       //模板的那一行
       XWPFTableRow tmpRow = rows.get(2);
       List<XWPFTableCell> tmpCells = null;
       List<XWPFTableCell> cells = null;
       XWPFTableCell tmpCell = null;
       tmpCells = tmpRow.getTableCells();

       for(int i=0;i<3;i++) {
           XWPFTableRow row = table.createRow();

           // 获取模板的行高 设置为新一行的行高
           row.setHeight(tmpRow.getHeight());
           cells = row.getTableCells();
           System.out.println("rows cess num:"+cells.size());
           System.out.println(table.getRows().size());

           if (row == null) {
               System.out.println("row2 is null");
           }
           String cellText = null;
           String cellTextKey = null;
           for (int j = 0; j < cells.size(); j++) {
               tmpCell = tmpCells.get(j);
               XWPFTableCell cell = cells.get(j);
               cellText = tmpCell.getText();
              // if (!StringUtils.isEmpty(cellText)) {
                   //转换为mapkey对应的字段
                  // cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");

                   setCellText(tmpCell,cell,String.valueOf(j));

               //}

           }

           mergeCellsHorizontal(table,0,1,3);
           mergeCellsHorizontal(table,0,5,7);
           mergeCellsHorizontal(table,0,9,11);
           mergeCellsHorizontal(table,0,13,16);
       }
       //删除模板行
       table.removeRow(2);
       fileInputStream.close();

        //写到目标文件
       OutputStream output = new FileOutputStream(Constants.reportOutFilePath);

       document.write(output);

       output.close();
   }

    /**
     *  复制模板行的属性
     * @param tmpCell
     * @param cell
     * @param text
     * @throws Exception
     */
    private static void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell,String text) throws Exception {

        CTTc cttc2 = tmpCell.getCTTc();
        CTTcPr ctPr2 = cttc2.getTcPr();
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        if (ctPr2.getTcW() != null) {
            ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
        }
        if (ctPr2.getVAlign() != null) {
            ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
        }
        if (cttc2.getPList().size() > 0) {
            CTP ctp = cttc2.getPList().get(0);
            if (ctp.getPPr() != null) {
                if (ctp.getPPr().getJc() != null) {
                    cttc.getPList().get(0).addNewPPr().addNewJc()
                            .setVal(ctp.getPPr().getJc().getVal());
                }
            }
        }
        if (ctPr2.getTcBorders() != null) {
            ctPr.setTcBorders(ctPr2.getTcBorders());
        }

        XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
        XWPFParagraph cellP = cell.getParagraphs().get(0);
        XWPFRun tmpR = null;
        if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {
            tmpR = tmpP.getRuns().get(0);
        }
        XWPFRun cellR = cellP.createRun();
        cellR.setText(text);
        // 复制字体信息
        if (tmpR != null) {
            if(!cellR.isBold()){
                cellR.setBold(tmpR.isBold());
            }
            cellR.setItalic(tmpR.isItalic());
            cellR.setUnderline(tmpR.getUnderline());
            cellR.setColor(tmpR.getColor());
            cellR.setTextPosition(tmpR.getTextPosition());
            if (tmpR.getFontSize() != -1) {
                cellR.setFontSize(tmpR.getFontSize());
            }
            if (tmpR.getFontFamily() != null) {
                cellR.setFontFamily(tmpR.getFontFamily());
            }
            if (tmpR.getCTR() != null) {
                if (tmpR.getCTR().isSetRPr()) {
                    CTRPr tmpRPr = tmpR.getCTR().getRPr();
                    if (tmpRPr.isSetRFonts()) {
                        CTFonts tmpFonts = tmpRPr.getRFonts();
                        CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR
                                .getCTR().getRPr() : cellR.getCTR().addNewRPr();
                        CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr
                                .getRFonts() : cellRPr.addNewRFonts();
                        cellFonts.setAscii(tmpFonts.getAscii());
                        cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
                        cellFonts.setCs(tmpFonts.getCs());
                        cellFonts.setCstheme(tmpFonts.getCstheme());
                        cellFonts.setEastAsia(tmpFonts.getEastAsia());
                        cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
                        cellFonts.setHAnsi(tmpFonts.getHAnsi());
                        cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
                    }
                }
            }

        }
        // 复制段落信息
        cellP.setAlignment(tmpP.getAlignment());
        cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
        cellP.setBorderBetween(tmpP.getBorderBetween());
        cellP.setBorderBottom(tmpP.getBorderBottom());
        cellP.setBorderLeft(tmpP.getBorderLeft());
        cellP.setBorderRight(tmpP.getBorderRight());
        cellP.setBorderTop(tmpP.getBorderTop());
        cellP.setPageBreak(tmpP.isPageBreak());
        if (tmpP.getCTP() != null) {
            if (tmpP.getCTP().getPPr() != null) {
                CTPPr tmpPPr = tmpP.getCTP().getPPr();
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP
                        .getCTP().getPPr() : cellP.getCTP().addNewPPr();
                // 复制段落间距信息
                CTSpacing tmpSpacing = tmpPPr.getSpacing();
                if (tmpSpacing != null) {
                    CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr
                            .getSpacing() : cellPPr.addNewSpacing();
                    if (tmpSpacing.getAfter() != null) {
                        cellSpacing.setAfter(tmpSpacing.getAfter());
                    }
                    if (tmpSpacing.getAfterAutospacing() != null) {
                        cellSpacing.setAfterAutospacing(tmpSpacing
                                .getAfterAutospacing());
                    }
                    if (tmpSpacing.getAfterLines() != null) {
                        cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
                    }
                    if (tmpSpacing.getBefore() != null) {
                        cellSpacing.setBefore(tmpSpacing.getBefore());
                    }
                    if (tmpSpacing.getBeforeAutospacing() != null) {
                        cellSpacing.setBeforeAutospacing(tmpSpacing
                                .getBeforeAutospacing());
                    }
                    if (tmpSpacing.getBeforeLines() != null) {
                        cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
                    }
                    if (tmpSpacing.getLine() != null) {
                        cellSpacing.setLine(tmpSpacing.getLine());
                    }
                    if (tmpSpacing.getLineRule() != null) {
                        cellSpacing.setLineRule(tmpSpacing.getLineRule());
                    }
                }
                // 复制段落缩进信息
                CTInd tmpInd = tmpPPr.getInd();
                if (tmpInd != null) {
                    CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd()
                            : cellPPr.addNewInd();
                    if (tmpInd.getFirstLine() != null) {
                        cellInd.setFirstLine(tmpInd.getFirstLine());
                    }
                    if (tmpInd.getFirstLineChars() != null) {
                        cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
                    }
                    if (tmpInd.getHanging() != null) {
                        cellInd.setHanging(tmpInd.getHanging());
                    }
                    if (tmpInd.getHangingChars() != null) {
                        cellInd.setHangingChars(tmpInd.getHangingChars());
                    }
                    if (tmpInd.getLeft() != null) {
                        cellInd.setLeft(tmpInd.getLeft());
                    }
                    if (tmpInd.getLeftChars() != null) {
                        cellInd.setLeftChars(tmpInd.getLeftChars());
                    }
                    if (tmpInd.getRight() != null) {
                        cellInd.setRight(tmpInd.getRight());
                    }
                    if (tmpInd.getRightChars() != null) {
                        cellInd.setRightChars(tmpInd.getRightChars());
                    }
                }
            }
        }
    }


// word跨列合并单元格

    public static  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {

        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {

            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);

            if ( cellIndex == fromCell ) {

                // The first merged cell is set with RESTART merge value

                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

            } else {

                // Cells which join (merge) the first one, are set with CONTINUE

                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

            }

        }

    }

    // word跨行并单元格

    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {

        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {

            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);

            if ( rowIndex == fromRow ) {

                // The first merged cell is set with RESTART merge value

                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);

            } else {

                // Cells which join (merge) the first one, are set with CONTINUE

                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);

            }

        }

    }

}
