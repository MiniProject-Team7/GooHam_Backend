package com.uplus.ureka.repository.notification;

import com.uplus.ureka.domain.notification.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    // 게시글 존재 확인
    @Select("SELECT EXISTS (SELECT 1 FROM POSTS WHERE id = #{postId})")
    boolean checkPostExists(@Param("postId") Long postId);

    // 댓글 존재 여부 확인
    @Select("SELECT EXISTS (SELECT 1 FROM COMMENTS WHERE id = #{postId})")
    boolean checkCommentExists(@Param("postId") Long postId);

    // 신청 알림 발송
    @Insert("INSERT INTO NOTIFICATIONS (user_id, post_id, post_user_id, participant_id, type, created_at) " +
            "VALUES (#{userId}, #{postId}, #{postUserId}, #{participantId}, #{type}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createNotificationForRequest(Notification notification);

    // 댓글 알림 발송
    @Insert("INSERT INTO NOTIFICATIONS (user_id, post_id, post_user_id, participant_id, type, created_at) " +
            "VALUES (#{userId}, #{postId}, #{postUserId}, #{participantId}, #{type}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createNotificationForComment(Notification notification);

    // 승인 알림 발송
    @Insert("INSERT INTO NOTIFICATIONS (user_id, post_id, post_user_id, participant_id, type, created_at) " +
            "VALUES (#{userId}, #{postId}, #{postUserId}, #{participantId}, '승인', NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createNotificationForApproval(Notification notification);

    // 거절 알림 발송
    @Insert("INSERT INTO NOTIFICATIONS (user_id, post_id, post_user_id, participant_id, type, created_at) " +
            "VALUES (#{userId}, #{postId}, #{postUserId}, #{participantId}, #{type}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createNotificationForRejection(Notification notification);


    // 읽음 처리
    @Update("UPDATE NOTIFICATIONS SET is_read = true WHERE id = #{id} AND user_id = #{userId}")
    void updateNotification(@Param("id") Long id, @Param("userId") Long userId);

    // 읽은 알림 제거
    @Delete("DELETE FROM NOTIFICATIONS WHERE is_read = true AND user_id = #{userId}")
    void deleteNotifications(@Param("userId") Long userId);

    // 알림 조회 쿼리
    @Select("<script>" +
            "SELECT id, user_id AS userId, post_id AS postId, post_user_id AS postUserId, participant_id AS participantId, type, is_read AS isRead, created_at AS createdAt " +
            "FROM NOTIFICATIONS WHERE user_id = #{userId} " +
            "ORDER BY created_at DESC" +
            "</script>")
    List<Notification> getNotifications(@Param("userId") Long userId);

    // 기한이 지난 스케줄 조회
    @Select("SELECT ID FROM NOTIFICATIONS WHERE POST_ID IN (SELECT ID FROM POSTS WHERE SCHEDULE_END < NOW() -  INTERVAL 7 DAY)")
    List<Long> findPastNotifications();

    // 기한이 지난 참여 내역 삭제
    @Delete("<script>" +
            "DELETE FROM NOTIFICATIONS " +
            "WHERE ID IN " +
            "<foreach item='id' collection='pastNotificationIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deleteOldNotifications(@Param("pastNotificationIds") List<Long> pastNotificationIds);

}
