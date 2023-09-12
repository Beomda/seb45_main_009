package com.mainproject.server.liked.service;

import com.mainproject.server.exception.BusinessLogicException;
import com.mainproject.server.exception.ExceptionCode;
import com.mainproject.server.feed.enitiy.Feed;
import com.mainproject.server.feed.repository.FeedRepository;
import com.mainproject.server.feed.service.FeedService;
import com.mainproject.server.liked.entity.Liked;
import com.mainproject.server.liked.repository.LikedRepository;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository likedRepository;
    private final FeedRepository feedRepository;

    @Transactional
    // 피드에 좋아요 추가하기
    public boolean toggleLikeFeed(Long feedId, User user) {
        Optional<Feed> feed = feedRepository.findById(feedId);

        if (feed.isPresent()) {
            for (Liked liked : feed.get().getLikedList()) {
                if (liked.getUser().getUserId().equals(user.getUserId())) {
                    feed.get().getLikedList().remove(liked);
                    liked.disconnectFeed();
                    likedRepository.delete(liked);
                    return false;
                }
            }
            likedRepository.save(Liked.builder()
                    .user(user)
                    .feed(feed.get())
                    .build());
            return true;
        } else {
            throw new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND);
        }
    }

    // 피드에 좋아요를 누른 사용자 목록 가져오기
    public List<Long> getLikedUsers(Long feedId) {
        Optional<Feed> feedOptional = feedRepository.findById(feedId);

        if (feedOptional.isPresent()) {
            Feed feed = feedOptional.get();
            return feed.getLikedList()
                    .stream()
                    .map(liked -> liked.getUser().getUserId())
                    .collect(Collectors.toList());
        } else {
            throw new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND);
        }
    }



}
