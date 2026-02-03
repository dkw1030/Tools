package fileOperate.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfAnalyzer {

    /**
     * 从PDF文件中提取文本内容
     * @param filePath PDF文件路径
     * @return 提取的文本内容，按每块最多10000字符分割成列表
     * @throws IOException 文件读取异常
     */
    public static List<String> extractTextFromPdf(String filePath) throws IOException {
        File file = validateInputs(filePath);
        
        try (PDDocument document = Loader.loadPDF(file)) {
            validateDocument(document);
            
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String fullText = pdfStripper.getText(document);
            // 将完整文本按每块最多10000字符分割成列表
            return splitTextIntoChunks(fullText, 10000);
        }
    }
    
    /**
     * 将文本按指定大小分割成块
     * @param text 要分割的文本
     * @param chunkSize 每块的最大字符数
     * @return 分割后的文本块列表
     */
    private static List<String> splitTextIntoChunks(String text, int chunkSize) {
        if (text == null || text.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        
        java.util.List<String> chunks = new java.util.ArrayList<>();
        
        for (int i = 0; i < text.length(); i += chunkSize) {
            int end = Math.min(i + chunkSize, text.length());
            String chunk = text.substring(i, end);
            chunks.add(chunk);
        }
        
        return chunks;
    }
    
    /**
     * 验证输入参数和文件状态
     * @param filePath 待验证的文件路径
     * @return 验证通过的文件对象
     * @throws IOException 文件相关的异常
     */
    private static File validateInputs(String filePath) throws IOException {
        // 验证输入参数
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("PDF文件路径不能为空");
        }
        
        // 检查文件扩展名
        if (!filePath.toLowerCase().endsWith(".pdf")) {
            throw new IOException("文件不是PDF格式: " + filePath);
        }
        
        File file = new File(filePath);
        
        // 检查文件是否存在
        if (!file.exists()) {
            throw new IOException("PDF文件不存在: " + filePath);
        }
        
        // 检查是否是文件（而不是目录）
        if (!file.isFile()) {
            throw new IOException("指定路径不是一个有效的文件: " + filePath);
        }
        
        // 检查文件是否可读
        if (!file.canRead()) {
            throw new IOException("无法读取PDF文件: " + filePath);
        }
        
        return file;
    }
    
    /**
     * 验证PDF文档有效性
     * @param document PDF文档对象
     * @throws IOException 文档无效异常
     */
    private static void validateDocument(PDDocument document) throws IOException {
        if (document.getNumberOfPages() <= 0) {
            throw new IOException("PDF文档为空或损坏");
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "example.pdf"; // 替换为实际的PDF文件路径
            List<String> text = extractTextFromPdf(filePath);
            if (text.isEmpty()) {
                System.out.println("PDF文件中未找到任何文本内容。");
            } else {
                System.out.println("提取的文本内容：\n" + text);
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("处理PDF文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}