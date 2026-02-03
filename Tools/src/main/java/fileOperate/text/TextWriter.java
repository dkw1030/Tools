package fileOperate.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 文本循环输出器
 * 将多段文本循环输出到指定的文本文件中
 */
public class TextWriter {
    
    private static final String OUTPUT_DIR = "D:\\\\Code\\\\OutPutFiles";
    
    /**
     * 将多段文本循环输出到文本文件中
     * @param texts 要输出的文本列表
     * @param fileName 输出文件名（不需要包含.txt后缀）
     * @throws IOException 文件操作异常
     */
    private static void cycleTextToOutputCreate(List<String> texts, String fileName) throws IOException {
        // 确保输出目录存在
        createOutputDirectory();
        
        // 创建输出文件路径，自动添加.txt后缀
        String outputPath = OUTPUT_DIR + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // 循环写入所有文本
            for (int i = 0; i < texts.size(); i++) {
                String text = texts.get(i);
                writer.newLine();
                writer.write(text);
                writer.newLine();
                writer.newLine(); // 添加空行作为分隔
            }
        }
    }
    
    /**
     * 将多段文本循环输出到文本文件中（追加模式）
     * @param texts 要输出的文本列表
     * @param fileName 输出文件名（不需要包含.txt后缀）
     * @throws IOException 文件操作异常
     */
    private static void cycleTextToOutputAppend(List<String> texts, String fileName) throws IOException {
        // 确保输出目录存在
        createOutputDirectory();
        
        // 创建输出文件路径，自动添加.txt后缀
        String outputPath = OUTPUT_DIR + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            // 循环写入所有文本
            for (int i = 0; i < texts.size(); i++) {
                String text = texts.get(i);
                writer.newLine();
                writer.write(text);
                writer.newLine();
                writer.newLine(); // 添加空行作为分隔
            }
        }
    }
    
    /**
     * 确保输出目录存在，如果不存在则创建
     * @throws IOException 目录创建失败异常
     */
    private static void createOutputDirectory() throws IOException {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            if (!created) {
                throw new IOException("无法创建输出目录: " + OUTPUT_DIR);
            }
        } else if (!outputDir.isDirectory()) {
            throw new IOException("输出路径已存在但不是一个目录: " + OUTPUT_DIR);
        }
    }
    
    /**
     * 将多段文本循环输出到文本文件中（智能模式）
     * 如果文件不存在则创建新文件，如果文件存在则追加内容
     * @param texts 要输出的文本列表
     * @param fileName 输出文件名（不需要包含.txt后缀）
     * @throws IOException 文件操作异常
     */
    public static void cycleTextToOutput(List<String> texts, String fileName) throws IOException {
        // 确保输出目录存在
        createOutputDirectory();
        
        // 创建输出文件路径，自动添加.txt后缀
        String outputPath = OUTPUT_DIR + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);
        
        // 检查文件是否存在，如果不存在则使用cycleTextToOutput，否则使用cycleTextToOutputAppend
        if (!outputFile.exists()) {
            cycleTextToOutputCreate(texts, fileName);
        } else {
            cycleTextToOutputAppend(texts, fileName);
        }
    }

    /**
     * 主方法，用于测试功能
     */
    public static void main(String[] args) {
        try {
            // 示例用法
            java.util.List<String> texts = java.util.Arrays.asList(
                "第一段示例文本",
                "第二段示例文本",
                "第三段示例文本",
                "第四段示例文本"
            );

            cycleTextToOutput(texts, "sample_output");
            System.out.println("文本已成功输出到 " + OUTPUT_DIR + File.separator + "sample_output.txt");

        } catch (IOException e) {
            System.err.println("处理文本时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}