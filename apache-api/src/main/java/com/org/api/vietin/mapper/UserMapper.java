package com.org.api.vietin.mapper;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import com.org.api.vietin.model.dataset.UserServerDataset;
import com.org.api.vietin.service.UserService;
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
            "   status = #{status} " +
            "WHERE " +
            "   id = #{id} ")
    Integer updStatus(@Param("id") String id,
                      @Param("status") String status);

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
            "   cau.id AS userId, " +
            "   casi.id AS serverId, " +
            "   cau.full_name AS fullName, " +
            "   cau.username, " +
            "   cau.phone_number AS phoneNumber, " +
            "   casi.server_name AS serverName, " +
            "   casi.server_alias AS serverAlias," +
            "   cau.status AS userStatus, " +
            "   casi.status AS serverStatus " +
            "FROM " +
            "   ca_user cau " +
            "INNER JOIN " +
            "   ca_server_info casi " +
            "   ON cau.id = casi.user_id "+
            "WHERE " +
            "   cau.role_id != '0' ")
    List<UserServerDataset> selAllUser();

    /**
     * Select all user
     * @return UserDataset
     */
    @Select("SELECT " +
            "   cau.id AS userId, " +
            "   casi.id AS serverId, " +
            "   cau.full_name AS fullName, " +
            "   cau.username, " +
            "   cau.phone_number AS phoneNumber, " +
            "   casi.server_name AS serverName, " +
            "   casi.server_alias AS serverAlias," +
            "   cau.status AS userStatus, " +
            "   casi.status AS serverStatus " +
            "FROM " +
            "   ca_user cau " +
            "INNER JOIN " +
            "   ca_server_info casi " +
            "   ON cau.id = casi.user_id "+
            "WHERE " +
            "   cau.role_id != '0' " +
            "   AND cau.status = '0' ")
    List<UserServerDataset> selAllActiveUser();


    /**
     * Select user information by user's id
     * @param id
     * @return UserDataset
     */
    @Select("SELECT " +
            "   cau.id AS userId, " +
            "   casi.id AS serverId, " +
            "   cau.full_name AS fullName, " +
            "   cau.username, " +
            "   cau.phone_number AS phoneNumber, " +
            "   casi.server_name AS serverName, " +
            "   casi.server_alias AS serverAlias," +
            "   cau.status AS userStatus, " +
            "   casi.status AS serverStatus " +
            "FROM " +
            "   ca_user cau " +
            "INNER JOIN " +
            "   ca_server_info casi " +
            "   ON cau.id = casi.user_id "+
            "WHERE " +
            "   cau.id = #{id} ")
    UserServerDataset selUserInfo(@Param("id") String id);

    /**
     * Select user information by user's id
     * @param id
     * @return UserDataset
     */
    @Select("SELECT " +
            "   id, " +
            "   full_name AS fullName, " +
            "   username, " +
            "   phone_number AS phoneNumber, " +
            "   status AS status " +
            "FROM " +
            "   ca_user " +
            "WHERE " +
            "   id = #{id} ")
    UserDataset selUserInfoComm(@Param("id") String id);

    /**
     * Insert user information
     * @param userServerDataset
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
            "   #{userId}, " +
            "   #{username}, " +
            "   #{password}, " +
            "   #{fullName}, " +
            "   #{phoneNumber}, " +
            "   #{roleId}, " +
            "   #{userStatus} " +
            ")")
    Integer insUser(UserServerDataset userServerDataset);

    @Select("SELECT COUNT(1) FROM ca_user WHERE username = #{username}")
    Integer selExistUsername(@Param("username") String username);

}
