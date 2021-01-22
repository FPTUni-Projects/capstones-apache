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

    @Select("SELECT " +
            "   car.id, " +
            "   car.name, " +
            "   car.file_name AS fileName, " +
            "   car.status " +
            "FROM " +
            "   ca_rule car " +
            "ORDER BY id")
    List<RuleDataset> selRules();

    @Select("SELECT " +
            "   car.id, " +
            "   car.name, " +
            "   car.file_name AS fileName, " +
            "   car.status " +
            "FROM " +
            "   ca_rule car " +
            "WHERE " +
            "   car.status = '0';")
    List<RuleDataset> selEnableRules();

    @Insert("INSERT INTO ca_rule ( " +
            "   id, " +
            "   name, " +
            "   file_name, " +
            "   status " +
            ") VALUES ( " +
            "   #{id}, " +
            "   #{name}, " +
            "   #{fileName}, " +
            "   #{status} " +
            ")")
    Integer insRule(RuleDataset ruleDataset);

}
