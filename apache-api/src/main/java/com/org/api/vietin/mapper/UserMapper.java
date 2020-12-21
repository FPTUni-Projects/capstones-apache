package com.org.api.vietin.mapper;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper
 *
 *
 * @since 2020/11/14
 */
@Mapper
public interface UserMapper {

    @Update("UPDATE " +
            "   ca_user " +
            "SET " +
            "   password = #{newPassword} " +
            "WHERE " +
            "   id = #{id} " +
            "   AND password = #{oldPassword}")
    Integer updPassword(UserPasswordDataset userPasswordDataset);

    @Update("UPDATE " +
            "   ca_user " +
            "SET " +
            "   status = '1' " +
            "WHERE " +
            "   id = #{id} ")
    Integer updStatus(@Param("id") String id);

    /**
     * Select user information for authentication by username and password
     * @param username
     * @param password
     * @return UserDataset
     */
    @Select("SELECT " +
            "   id, " +
            "   username, " +
            "   password," +
            "   role_id AS roleId " +
            "FROM " +
            "   ca_user " +
            "WHERE " +
            "   username = #{username} " +
            "   AND password = #{password}" +
            "   AND status = '0'; ")
    UserDataset selUserInfoForAuth(@Param("username") String username,
                                   @Param("password") String password);

    /**
     * Select all user
     * @return UserDataset
     */
    @Select("SELECT " +
            "   id, " +
            "   full_name AS fullName, " +
            "   username, " +
            "   phone_number AS phoneNumber " +
            "FROM " +
            "   ca_user " +
            "WHERE " +
            "   role_id != '0' " +
            "   AND status = '0'; ")
    List<UserDataset> selAllUser();

    /**
     * Select user information by user's id
     * @param id
     * @return UserDataset
     */
    @Select("SELECT " +
            "   id, " +
            "   username, " +
            "   full_name AS fullName, " +
            "   phone_number AS phoneNumber, " +
            "   role_id AS roleId, " +
            "   status " +
            "FROM " +
            "   ca_user " +
            "WHERE " +
            "   id = #{id} ")
    UserDataset selUserInfo(@Param("id") String id);

    /**
     * Insert user information
     * @param userDataset
     * @return Integer
     */
    @Insert("INSERT INTO ca_user( " +
            "   id, " +
            "   username, " +
            "   password, " +
            "   full_name, " +
            "   phone_number, " +
            "   role_id, " +
            "   status " +
            ") VALUES ( " +
            "   #{id}, " +
            "   #{username}, " +
            "   #{password}, " +
            "   #{fullName}, " +
            "   #{phoneNumber}, " +
            "   #{roleId}, " +
            "   #{status} " +
            ")")
    Integer insUser(UserDataset userDataset);

}
