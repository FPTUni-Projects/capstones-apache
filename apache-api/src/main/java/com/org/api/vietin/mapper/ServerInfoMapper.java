package com.org.api.vietin.mapper;

import com.org.api.vietin.model.dataset.UserServerDataset;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * ServerInfoMapper
 *
 * @since 2021/01/22
 */
@Mapper
public interface ServerInfoMapper {

    @Insert("INSERT INTO ca_server_info (" +
            "   id, " +
            "   user_id, " +
            "   server_name, " +
            "   server_alias, " +
            "   status " +
            ") VALUES ( " +
            "   #{serverId}, " +
            "   #{userId}, " +
            "   #{serverName}, " +
            "   #{serverAlias}, " +
            "   #{serverStatus} " +
            ")")
    Integer insServerInfo(UserServerDataset userServerDataset);

    @Select("SELECT COUNT(1) FROM ca_server_info WHERE server_name = #{serverName}")
    Integer selExistServerName(@Param("serverName") String serverName);

    @Select("SELECT COUNT(1) FROM ca_server_info WHERE server_alias = #{serverAlias}")
    Integer selExistServerAlias(@Param("serverAlias") String serverAlias);

}
