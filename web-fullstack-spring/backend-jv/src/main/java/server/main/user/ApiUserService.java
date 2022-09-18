package server.main.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import server.main.user.ApiUser;
import server.main.user.ApiUserRepository;

import java.util.Optional;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;


@Service("apiUserService")
public class ApiUserService {

    @Autowired
    private ApiUserRepository apiUserRepository;

    public ApiUserService() {}

    public String login(String username, String password) {
        Optional<ApiUser> apiuser = apiUserRepository.login(username, password);

        if (apiuser.isPresent()) {
            String token = UUID.randomUUID().toString();
            ApiUser apiuserx = apiuser.get();
            apiuserx.setToken(token);
            apiUserRepository.save(apiuserx);
        }
        return "";
    }

    public Optional<User> findByToken(String token) {
        Optional<ApiUser> apiuser = apiUserRepository.findByToken(token);
        if (apiuser.isPresent()) {
            ApiUser apiuserx = apiuser.get();
            User user = new User(apiuserx.getUsername(), apiuserx.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public ApiUser findById (Long id) {
        Optional<ApiUser> apiuser = apiUserRepository.findById(id);
        return apiuser.orElse(null);
    }

    public ApiUser findByUsername(String name) {
        Optional<ApiUser> apiuser = apiUserRepository.findByUsername(name);
        return  apiuser.orElse(null);
    }


    public List<ApiUser> debug() {
        List<ApiUser> output = new ArrayList<>();
        for (ApiUser user : apiUserRepository.findAll()) {
            output.add(user);
        }
        return output;
    }

}