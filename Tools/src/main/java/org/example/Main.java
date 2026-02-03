package org.example;

import fileOperate.pdf.PdfAnalyzer;
import fileOperate.text.TextWriter;

import java.io.IOException;
import java.util.List;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) throws IOException {

        List<String> s = PdfAnalyzer.extractTextFromPdf("D:\\common\\download\\类型标准.pdf");
        TextWriter.cycleTextToOutput(s,"类型标准");
    }
}