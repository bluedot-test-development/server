package develop.bluedot.server.service;

import develop.bluedot.server.entity.DotVideo;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.repository.DotVideoRepository;
import develop.bluedot.server.entity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DotVideoService {

    @Autowired
    private DotVideoRepository dotVideoRepository;

    @Autowired
    private UserRepository userRepository;

    public void create(String videoUrl, Long userId) {

        Optional<User> findUser = userRepository.findById(userId);

        findUser.ifPresent(user -> {
            DotVideo newDotVideo = DotVideo.builder()
                    .link(videoUrl)
                    .user(user)
                    .build();
            dotVideoRepository.save(newDotVideo);
        });
    }

    public Optional<Object> getAllDotVideo(Long id) {

        Optional<User> findUser = userRepository.findById(id);

        Optional<Object> dotVideos = findUser.map(user -> {
            return user.getDotVideoList();
        });

        return dotVideos;

    }
}
