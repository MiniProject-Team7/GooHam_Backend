package com.uplus.ureka.service.notification;

import com.uplus.ureka.domain.notification.Notification;
import com.uplus.ureka.dto.notification.NotificationRequestDTO;
import com.uplus.ureka.exception.CustomExceptions;
import com.uplus.ureka.repository.notification.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationMapper notificationMapper;

    // ✅ 게시글 존재 여부 체크
    public boolean checkPostExists(Long postId) {
        return notificationMapper.checkPostExists(postId);
    }

    // ✅ 댓글 존재 여부 확인
    public boolean checkCommentExists(Long postId) {
        return notificationMapper.checkCommentExists(postId);
    }

    // ✅ 알림 생성 (신청)
    public void createNotificationForRequest(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        notificationMapper.createNotificationForRequest(notification);
    }

    // ✅ 알림 생성 (승인)
    public void createNotificationForApproval(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        notificationMapper.createNotificationForApproval(notification);
    }

    // ✅ 알림 생성 (거절)
    public void createNotificationForRejection(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        notificationMapper.createNotificationForRejection(notification);
    }

    // ✅ 알림 생성 (댓글)
    public void createNotificationForComment(NotificationRequestDTO notificationRequestDTO) {
        if (!checkPostExists(notificationRequestDTO.getPostId())) {
            //TODO: 예외처리 수정
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }
        if (!checkCommentExists(notificationRequestDTO.getPostId())) {
            //TODO: 예외처리 수정
            throw new CustomExceptions("해당 댓글이 존재하지 않습니다.");
        }

        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        notificationMapper.createNotificationForComment(notification);
    }

    // 사용자 ID로 알림 목록 조회
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationMapper.getNotifications(userId);
    }

    // ✅ 읽은 알림 삭제
    public void deleteNotifications(Long userId) {
        notificationMapper.deleteNotifications(userId);
    }

    // 알림 상태 업데이트
    public void markNotificationAsRead(Long notificationId, Long userId) {
        notificationMapper.updateNotification(notificationId, userId);
    }
}