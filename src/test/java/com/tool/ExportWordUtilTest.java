package com.tool;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 17:02
 */
public class ExportWordUtilTest {
    @Before
    public void init(){
        //初始化report文件路径
        Constants.reportModuleFilePath = "E:\\excell\\输出\\report_module.docx";
        Constants.reportOutFilePath = "E:\\excell\\输出\\report.docx";
    }

    @Test
    public void exportEordTest()throws Exception {
       // ExportWordUtil.exportEordTest();

    }
}