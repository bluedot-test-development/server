package develop.bluedot.server.entity.repository;

import develop.bluedot.server.entity.DotVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DotVideoRepository extends JpaRepository<DotVideo,Long> {
}
