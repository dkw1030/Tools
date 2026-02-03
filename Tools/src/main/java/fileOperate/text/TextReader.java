package fileOperate.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本文件读取器
 * 提供读取文本文件并按块处理的功能
 */
public class TextReader {

    /**
     * 读取文本文件并将其处理为List<String>，每1000行为一块内容
     * @param filePath 要读取的文件路径
     * @return 包含文件内容块的列表，每个元素包含最多1000行的内容
     * @throws IOException 文件操作异常
     */
    public static List<String> readFileInChunks(String filePath) throws IOException {
        return readFileInChunks(filePath, 1000);
    }
    
    /**
     * 读取文本文件并将其处理为List<String>，允许自定义每块的行数
     * @param filePath 要读取的文件路径
     * @param linesPerChunk 每块的行数
     * @return 包含文件内容块的列表，每个元素包含指定行数的内容
     * @throws IOException 文件操作异常
     */
    public static List<String> readFileInChunks(String filePath, int linesPerChunk) throws IOException {
        List<String> chunks = new ArrayList<>();
        StringBuilder chunk = new StringBuilder();
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chunk.append(line);
                chunk.append("\n");
                lineCount++;

                // 当达到指定行数时，将当前块添加到列表中并重置
                if (lineCount >= linesPerChunk) {
                    chunks.add(chunk.toString());
                    chunk.setLength(0); // 清空StringBuilder
                    lineCount = 0;
                }
            }

            // 添加最后不足指定行数的部分
            if (chunk.length() > 0) {
                chunks.add(chunk.toString());
            }
        }

        return chunks;
    }
}