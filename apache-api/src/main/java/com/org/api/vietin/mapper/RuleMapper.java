package com.org.api.vietin.mapper;

import com.org.api.vietin.model.dataset.RuleDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * RuleMapper
 *
 *
 * @since 2020/11/14
 */
@Mapper
public interface RuleMapper {

    @Update("UPDATE " +
            "   ca_rule " +
            "SET " +
            "   status = #{status} " +
            "WHERE " +
            "   id = #{id} ")
    Integer updRule(@Param("id") String id,
                    @Param("status") String status);

    @Delete("DELETE FROM " +
            "   ca_rule " +
            "WHERE " +
            "   id = #{id} ")
    Integer delRule(@Param("id") String id);

    @Select("<script>" +
            "SELECT " +
            "   car.id, " +
            "   car.name, " +
            "   car.file_name AS fileName, " +
            "   car.description AS description, " +
            "   car.status," +
            "   car.user_id AS userId, " +
            "   cau.full_name AS publisher " +
            "FROM " +
            "   ca_rule car " +
            "LEFT JOIN " +
            "   ca_user cau " +
            "   ON car.user_id = cau.id " +
            "WHERE " +
            "   1 = 1" +
            "<if test='roleId != \"0\"'> " +
            "   AND car.user_id = #{userId}" +
            "</if>" +
            "</script>")
    List<RuleDataset> selRules(@Param("userId") String userId,
                               @Param("roleId") String roleId);

    @Insert("INSERT INTO ca_rule ( " +
            "   id, " +
            "   name, " +
            "   file_name, " +
            "   description, " +
            "   user_id, " +
            "   status " +
            ") VALUES ( " +
            "   #{id}, " +
            "   #{name}, " +
            "   #{fileName}, " +
            "   #{description}, " +
            "   #{userId}, " +
            "   #{status} " +
            ")")
    Integer insRule(RuleDataset ruleDataset);

}
