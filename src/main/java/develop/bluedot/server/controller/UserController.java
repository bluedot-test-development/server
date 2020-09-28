package develop.bluedot.server.controller;

import develop.bluedot.server.entity.User;
import develop.bluedot.server.network.Header;
import develop.bluedot.server.network.request.UserApiRequest;
import develop.bluedot.server.network.response.PostApiResponse;
import develop.bluedot.server.network.response.UserApiResponse;
import develop.bluedot.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends CrudController<UserApiRequest, UserApiResponse, User>
{
    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public Header<List<UserApiResponse>> search(
            @PageableDefault(sort="id",direction = Sort.Direction.ASC,size=10)
                    Pageable pageable){
//        return userService.search(pageable);
        return null;
    }

    @GetMapping("/artist")
    public List<UserApiResponse> getArtist(){
        return userService.getArtist();
    }

    @GetMapping("genre")
    public List<PostApiResponse> getGenrePost(){
        return userService.getGenrePost();
    }

    @GetMapping("post")
    public List<PostApiResponse> getAllPost(){
        return userService.getAllPost();
    }

}
