package server.main.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import server.main.user.ApiUser;

@Repository
public interface ApiUserRepository extends CrudRepository<ApiUser, Long> {
    @Query(value = "SELECT u FROM ApiUser u where u.username = ?1 and u.password = ?2 ")
    Optional<ApiUser> login(String username,String password);

    Optional<ApiUser> findByToken(String token);
    Optional<ApiUser> findByUsername(String username);
}