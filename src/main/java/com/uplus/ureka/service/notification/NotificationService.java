package com.uplus.ureka.service.notification;

import com.uplus.ureka.domain.notification.Notification;
import com.uplus.ureka.dto.notification.NotificationRequestDTO;
import com.uplus.ureka.dto.notification.NotificationResponseDTO;
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

    public NotificationResponseDTO createNotification(NotificationRequestDTO notificationRequestDTO) {
        Long postId = notificationRequestDTO.getPostId();
        if (!checkPostExists(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }

        if ("댓글".equals(notificationRequestDTO.getType()) && !checkCommentExists(postId)) {
            throw new CustomExceptions("해당 댓글이 존재하지 않습니다.");
        }

        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        switch (notificationRequestDTO.getType()) {
            case 신청:
                notificationMapper.createNotificationForRequest(notification);
                break;
            case 승인:
                notificationMapper.createNotificationForApproval(notification);
                break;
            case 거절:
                notificationMapper.createNotificationForRejection(notification);
                break;
            case 댓글:
                notificationMapper.createNotificationForComment(notification);
                break;
            default:
                throw new CustomExceptions("잘못된 알림 타입입니다.");
        }
        // ✅ 저장된 `notification`을 기반으로 `NotificationResponseDTO` 생성하여 반환
        return new NotificationResponseDTO(
                notification.getId(),              // ✅ DB에서 자동 생성된 ID
                notification.getUserId(),
                notification.getPostId(),
                notification.getPostUserId(),
                notification.getParticipantId(),
                notification.getType(),
                false,                             // ✅ 기본값: 읽지 않음
                notification.getCreatedAt()        // ✅ 생성 시간 추가
        );

    }
    /*
    // ✅ 알림 생성 (신청)
    public void createNotificationForRequest(NotificationRequestDTO notificationRequestDTO) {
        Long postId = notificationRequestDTO.getPostId();
        if (!checkPostExists(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }

        Notification notification = new Notification();
        notification.setUserId(notificationRequestDTO.getUserId());
        notification.setPostId(notificationRequestDTO.getPostId());
        notification.setPostUserId(notificationRequestDTO.getPostUserId());
        notification.setParticipantId(notificationRequestDTO.getParticipantId());
        notification.setType(notificationRequestDTO.getType());

        notificationMapper.createNotificationForRequest(notification);
//        NotificationResponseDTO responseDTO = notificationMapper.
    }

    // ✅ 알림 생성 (승인)
    public void createNotificationForApproval(NotificationRequestDTO notificationRequestDTO) {
        Long postId = notificationRequestDTO.getPostId();
        if (!checkPostExists(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }

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
        Long postId = notificationRequestDTO.getPostId();
        if (!checkPostExists(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }

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
        Long postId = notificationRequestDTO.getPostId();
        if (!checkPostExists(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }
        if (!checkCommentExists(postId)) {
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
*/

    // ✅ 사용자 ID로 알림 목록 조회
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationMapper.getNotifications(userId);
    }

    // ✅ 읽은 알림 삭제
    public void deleteNotifications(Long userId) {
        notificationMapper.deleteNotifications(userId);
    }

    // ✅ 알림 상태 업데이트
    public void markNotificationAsRead(Long notificationId, Long userId) {
        notificationMapper.updateNotification(notificationId, userId);
    }
}