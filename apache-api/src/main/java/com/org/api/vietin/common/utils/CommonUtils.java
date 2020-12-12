package com.org.api.vietin.common.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * CommonUtils
 *
 * @author khal
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
}
