package com.org.api.vietin.mapper;

import com.org.api.vietin.model.dataset.AuthSessionDataset;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * AuthSessionMapper
 *
 * @author khal
 * @since 2020/11/14
 */
@Mapper
public interface AuthSessionMapper {

    /**
     * Select session authentication information
     * @param userId
     * @param sessionId
     * @return AuthSessionDataset
     */
    @Select("SELECT " +
            "   vias.user_id AS userId, " +
            "   vias.session_id AS sessionId, " +
            "   vias.expiration_time AS expirationTime, " +
            "   vias.auth_key AS authKey, " +
            "   vias.timeout_flg AS timeoutFlg, " +
            "   vias.login_time AS loginTime, " +
            "   vias.logout_time AS logoutTime " +
            "FROM " +
            "   vi_auth_session vias " +
            "INNER JOIN " +
            "   vi_user viu " +
            "   ON " +
            "       vias.user_id = viu.id " +
            "       AND vias.auth_key = viu.password " +
            "WHERE " +
            "   vias.user_id = #{userId} " +
            "   AND vias.session_id = #{sessionId} " +
            "   AND vias.timeout_flg != '1'; ")
    AuthSessionDataset selAuthSessionInfo(@Param("userId") String userId,
                                          @Param("sessionId") String sessionId);

    /**
     * Insert authentication session information
     * @param authSessionDataset
     * @return Integer
     */
    @Insert("INSERT INTO vi_auth_session ( " +
            "   user_id, " +
            "   session_id, " +
            "   expiration_time, " +
            "   auth_key, " +
            "   timeout_flg, " +
            "   login_time, " +
            "   logout_time " +
            ") VALUES ( " +
            "   #{userId}, " +
            "   #{sessionId}, " +
            "   #{expirationTime}, " +
            "   #{authKey}, " +
            "   #{timeoutFlg}, " +
            "   #{loginTime}, " +
            "   #{logoutTime} " +
            "); ")
    Integer insAuthSessionInfo(AuthSessionDataset authSessionDataset);

    /**
     * Expire all session by user id
     * @param userId
     * @return Integer
     */
    @Insert("UPDATE " +
            "   vi_auth_session " +
            "SET " +
            "   timeout_flg = '1', " +
            "   logout_time = #{logoutTime} " +
            "WHERE " +
            "   user_id = #{userId}; ")
    Integer updExpirationAllSession(@Param("userId") String userId,
                                    @Param("logoutTime") String logoutTime);

    /**
     * Expire session by user id and session id
     * @param userId
     * @param sessionId
     * @param logoutTime
     * @param timeoutFlg
     * @return Integer
     */
    @Insert("UPDATE " +
            "   vi_auth_session " +
            "SET " +
            "   timeout_flg = #{timeoutFlg}, " +
            "   logout_time = #{logoutTime} " +
            "WHERE " +
            "   user_id = #{userId} " +
            "   AND session_id = #{sessionId}; ")
    Integer updExpirationSession(@Param("userId") String userId,
                                 @Param("sessionId") String sessionId,
                                 @Param("logoutTime") String logoutTime,
                                 @Param("timeoutFlg") String timeoutFlg);

}
