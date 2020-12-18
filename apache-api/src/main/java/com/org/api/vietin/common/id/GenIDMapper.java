package com.org.api.vietin.common.id;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * IDHelperMapper
 *
 *
 * @since 2020/11/14
 */
@Mapper
public interface GenIDMapper {

    @Select("SELECT " +
            "   COUNT(1) " +
            "FROM " +
            "   ${tableName} " +
            "WHERE " +
            "   id = #{id}")
    int selId(@Param("tableName") String tableName, @Param("id") String id);

}
