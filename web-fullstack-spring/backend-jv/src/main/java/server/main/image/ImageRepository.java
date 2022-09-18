package server.main.image;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ImageRepository extends CrudRepository<Image,Long> {

    //---- Returns a list of images owned by a particular user
    @Query(value ="SELECT i FROM Image i where i.owner=?1")
    public List<Image> findByUserID(Long userID);

    @Query(value="SELECT i FROM Image i where i.id=?1 and i.owner=?2")
    public List<Image> findByImageID(Long imageID, Long userID);
}