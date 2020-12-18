package com.org.api.vietin.common.utils;

import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * CommonUtils
 *
 *
 * @since 2020/06/13
 */
public class CommonUtils {

    /**
     * Convert string
     * Ex: This is my code -> this-is-my-code
     *
     * @param str
     * @return
     */
    public static String convertStr(String str) {
        // TODO: Uncheck vietnamese unicode
        String result = "";
        if (!StringUtils.isEmpty(str) && str.contains(" ")) {
            String[] strArr = str.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
            result = Arrays.asList(strArr).stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("-"));
        }

        return result;
    }

    /**
     * Check if it is a static resources
     *
     * @param uri
     */
    public static boolean isStaticResouces(String uri) {
        return uri.startsWith("/dist/")
                || uri.endsWith(".js")
                || uri.endsWith(".css")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg")
                || uri.endsWith(".svg")
                || uri.endsWith(".ico")
                || uri.endsWith(".woff")
                || uri.endsWith(".woff2")
                || uri.endsWith(".ttf")
                || uri.endsWith(".map")
                || uri.endsWith(".webmanifest")
                || uri.endsWith("manifest.json")
                || uri.endsWith("favicon.ico");
    }

    /**
     * Save multipart file into physical file
     *
     * @param dir
     * @param fileName
     * @param multipartFile
     */
    public static void saveOnce(String dir, String fileName, MultipartFile multipartFile) {
        if (!StringUtils.hasLength(dir) || multipartFile == null)
            return;

        OutputStream fos = null;
        OutputStream stream = null;
        try {
            File folder = new File(dir);
            if (folder == null | !folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }

            File file = new File(dir + File.separator + fileName);
            fos = new FileOutputStream(file);
            stream = new BufferedOutputStream(fos);
            stream.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read multiple file type is image as base64 url
     *
     * @param dir
     * @return List<String>
     */
    public static String readFileAsBase64Url(String dir) {
        String base64Url = "";

        File file = new File(dir);
        if (file != null && file.exists() && file.isFile()) {
            String filePath = file.getAbsolutePath();
            byte[] byteArr = Base64.getEncoder().encode(readFileAsByte(filePath));
            String ext = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
            String mimeType = getMimeTypeByExtension(ext);
            String base64Encode = new String(byteArr);

            base64Url = "data:" + mimeType + ";base64," + base64Encode;
        }

        return base64Url;
    }

    /**
     * Read file to byte array
     *
     * @param fileDir
     * @return byte[]
     */
    public static byte[] readFileAsByte(String fileDir) {
        byte[] bytes = null;
        InputStream inputStream = null;

        File file = new File(fileDir);
        if (file.exists()) {
            try {
                bytes = new byte[(int) file.length()];
                inputStream = new FileInputStream(file);
                inputStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return bytes;
    }

    private static final String EXT_PDF = "pdf";
    private static final String EXT_DOC = "doc";
    private static final String EXT_DOCX = "docx";
    private static final String EXT_XLSX = "xlsx";
    private static final String EXT_XLSM = "xlsm";
    private static final String EXT_XLS = "xls";
    private static final String EXT_TXT = "txt";

    private static final String MIME_TYPE_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String MIME_TYPE_DOC = "application/msword";
    private static final String MIME_TYPE_PDF = "application/dpf";
    private static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String MIME_TYPE_XLSM = "application/vnd.ms-excel.sheet.macroenabled.12";
    private static final String MIME_TYPE_XLS = "application/vnd.ms-excel";
    private static final String MIME_TYPE_TXT = "text/plain";
    private static final String MIME_TYPE_OCTET_STREAM = "application/octet-stream";

    /**
     * Get mime type of file by extension
     *
     * @param extension
     * @return mimeType
     */
    public static String getMimeTypeByExtension(String extension) {
        String mimeType;
        switch (extension) {
            case EXT_DOC: mimeType = MIME_TYPE_DOC; break;
            case EXT_DOCX: mimeType = MIME_TYPE_DOCX; break;
            case EXT_PDF: mimeType = MIME_TYPE_PDF; break;
            case EXT_XLS: mimeType = MIME_TYPE_XLS; break;
            case EXT_XLSX: mimeType = MIME_TYPE_XLSX; break;
            case EXT_XLSM: mimeType = MIME_TYPE_XLSM; break;
            case EXT_TXT: mimeType = MIME_TYPE_TXT; break;
            default: mimeType = MIME_TYPE_OCTET_STREAM; break;
        }

        return mimeType;
    }
}
