package com.org.api.vietin.common.id;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * GenIDComponent
 *
 * @author khal
 * @since 2020/11/14
 */
@Component
public class GenIDComponentImp implements GenIDComponent {

    private final GenIDMapper mapper;

    public GenIDComponentImp (GenIDMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Generate ID
     *
     * @param length
     * @param tableName
     * @return String
     */
    public String getId(final int length, final String tableName) {
        if (length <= 0)
            return "";

        int max = (int) Math.pow(10, length) - 1;

        Random random = new Random();
        String id;
        do {
            id = String.valueOf(random.nextInt(max + 1));

            String prefix = "";
            for (int i = 0; i < (length - id.length()); i ++) {
                prefix += "0";
            }

            id = prefix + id;
        } while(mapper.selId(tableName, id) != 0);

        return id;
    }

}
