package com.tool;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 16:36
 */
public class ExportWordUtil {
   public static void exportEordTest(Map<String,AreaStatistics> areaStatisticsMap)throws Exception{


       //读取word 模板源文件
       FileInputStream fileInputStream = new FileInputStream(Constants.reportModuleFilePath);

        // POIFSFileSystem pfs = new POIFSFileSystem(fileInputStream);
       XWPFDocument document = new XWPFDocument(fileInputStream);


        //获取所有表格
       List<XWPFTable> tables = document.getTables();
        //这里简单取第一个表格
       XWPFTable table = tables.get(0);

       List<XWPFTableRow> rows = table.getRows();
       //模板的那一行
       XWPFTableRow tmpRow = rows.get(2);
       List<XWPFTableCell> tmpCells = null;
       List<XWPFTableCell> cells = null;
       XWPFTableCell tmpCell = null;
       tmpCells = tmpRow.getTableCells();

       Iterator<Map.Entry<String,AreaStatistics>> iterator = areaStatisticsMap.entrySet().iterator();

       int[] total = new int[17];//保存最后一行的合计

       while(iterator.hasNext()) {
           Map.Entry<String,AreaStatistics> entry = iterator.next();
           String area = entry.getKey();
           XWPFTableRow row = table.createRow();
           AreaStatistics areaStatistics = entry.getValue();

           // 获取模板的行高 设置为新一行的行高
           row.setHeight(tmpRow.getHeight());
           cells = row.getTableCells();
           System.out.println("rows cess num:"+cells.size());
           System.out.println(table.getRows().size());


           String cellText = null;
           String cellTextKey = null;
           XWPFTableCell cell = null;

           tmpCell = tmpCells.get(0);
           cell = cells.get(0);
           setCellText(tmpCell,cell,area);

           tmpCell = tmpCells.get(1);
           cell = cells.get(1);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getAddCountOfOutside()));
           total[0] += areaStatistics.getAddCountOfOutside();

           tmpCell = tmpCells.get(2);
           cell = cells.get(2);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getAddCountOfInside()));
           total[1] += areaStatistics.getAddCountOfInside();

           tmpCell = tmpCells.get(3);
           cell = cells.get(3);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getAddCountOfInside()+areaStatistics.getAddCountOfOutside()));
           total[2] += (areaStatistics.getAddCountOfInside()+areaStatistics.getAddCountOfOutside());

           tmpCell = tmpCells.get(5);
           cell = cells.get(5);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getSaveCountOfOutside()));
           total[4] += areaStatistics.getSaveCountOfOutside();

           tmpCell = tmpCells.get(6);
           cell = cells.get(6);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getSaveCountOfInside()));
           total[5] += areaStatistics.getSaveCountOfInside();

           tmpCell = tmpCells.get(7);
           cell = cells.get(7);
           setCellText(tmpCell,cell,String.valueOf(areaStatistics.getSaveCountOfOutside()+areaStatistics.getSaveCountOfInside()));
           total[6] += (areaStatistics.getSaveCountOfOutside()+areaStatistics.getSaveCountOfInside());



       }
       //添加总计行
       XWPFTableRow row = table.createRow();
       XWPFTableCell cell = null;
       tmpCells = row.getTableCells();
       cell = tmpCells.get(0);
       cell.setText("合计");
       for(int i=0;i<total.length;i++){
           cell = tmpCells.get(i+1);
           cell.setText(String.valueOf(total[i]));
       }

       //替换模板标记文本
       Map<String,String> map = new HashMap<>();
       map.put("${1}",String.valueOf(total[2]));
       map.put("${2}",String.valueOf(total[3]));
       map.put("${3}",String.valueOf(total[6]));
       map.put("${4}",String.valueOf(total[7]));
       map.put("${5}",String.valueOf(total[10]));
       map.put("${6}",String.valueOf(total[8]));
       map.put("${7}",String.valueOf(total[9]));
       map.put("${8}",String.valueOf(total[11]));
       map.put("${9}",String.valueOf(total[14]));
       map.put("${10}",String.valueOf(total[15]));
       System.out.println(map);
      List<XWPFParagraph> paragraphs =  document.getParagraphs();
      for(XWPFParagraph paragraph:paragraphs){
         //replaceInPara(paragraph,map);
          List<XWPFRun> runs = paragraph.getRuns();
          for (int i = 0; i < runs.size(); i++) {
              String oneparaString = runs.get(i).getText(
                      runs.get(i).getTextPosition());
              for (Map.Entry<String, String> entry : map
                      .entrySet()) {
                  oneparaString = oneparaString.replace(
                          entry.getKey(), entry.getValue());
              }
              runs.get(i).setText(oneparaString, 0);
          }

      }

       //合并单元格，必须最后做
       mergeCellsHorizontal(table,0,1,3);
       mergeCellsHorizontal(table,0,5,7);
       mergeCellsHorizontal(table,0,9,11);
       mergeCellsHorizontal(table,0,13,16);
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

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    public static void replaceInPara(XWPFParagraph para, Map<String, String> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();

            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if(runText == null || runText.length() <2) continue;
                System.out.println("------>>>>>>>>>" + runText);
                if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {
                    start = i;
                }
                if ((start != -1)) {
                    str += runText;
                }
                if ('}' == runText.charAt(runText.length() - 1)) {
                    if (start != -1) {
                        end = i;
                        break;
                    }
                }
            }
            System.out.println("start--->"+start);
            System.out.println("end--->"+end);

            System.out.println("str---->>>" + str);

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
                System.out.println("remove i="+i);
            }

            for (String key : params.keySet()) {
                if (str.equals(key)) {
                    para.createRun().setText((String) params.get(key));
                    break;
                }
            }


        }
    }
    /**	 * 正则匹配字符串	 * @param paragraphText	 * @return	 */
    public static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }




}
