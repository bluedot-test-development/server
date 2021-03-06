package develop.bluedot.server.service;

import develop.bluedot.server.application.EmailExistedException;
import develop.bluedot.server.application.EmailNotExistedException;
import develop.bluedot.server.entity.Post;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.repository.PostRepository;
import develop.bluedot.server.entity.repository.UserRepository;
import develop.bluedot.server.network.Header;
import develop.bluedot.server.network.request.UserApiRequest;
import develop.bluedot.server.network.response.PostApiResponse;
import develop.bluedot.server.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends BaseService<UserApiRequest,UserApiResponse,User> {

    @Autowired
    private PostRepository postRepository;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 아이디 생성
     */
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userData = request.getData();

        //존재하는 이메일 예외처리
        Optional<User> existed = userRepository.findByEmail(userData.getEmail());
        if(existed.isPresent()){
            throw new EmailExistedException(userData.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(userData.getPassword());

        User newUser = User.builder()
                .email(userData.getEmail())
                .password(encodedPassword)
                .genre(userData.getGenre())
                .name(userData.getName())
                .build();

        User returnData = baseRepository.save(newUser);

        return response(returnData);
    }

    /**
     * 페이징처리
    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> returnData = users.stream().map(user->
                responseForPageable(user)
        ).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .currentPage(users.getNumber())
                .totalElements(users.getTotalElements())
                .currentElements(users.getNumberOfElements())
                .build();

        return Header.OK(returnData,pagination);
    }
     */

    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> findUser = baseRepository.findById(id);

        return findUser.map(user ->
                response(user)
        ).orElseGet(() -> Header.ERROR("에러"));

    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {

        Optional<User> findUser = baseRepository.findById(id);

        return findUser.map(user->{
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("에러"));

    }

    public List<UserApiResponse> getArtist(){

        List<UserApiResponse> userApiResponsesList = new ArrayList<>();

        List<User> findUsers= baseRepository.findAll();

        List<User> artistUser = findUsers.stream().filter(user->user.getIsArtist()==1).collect(Collectors.toList());

        artistUser.stream().forEach(user->{
            UserApiResponse userApiResponse = UserApiResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .genre(user.getGenre())
                    .img(user.getImg())
                    .build();
            userApiResponsesList.add(userApiResponse);
        });

        return userApiResponsesList;
    }

    public List<PostApiResponse> getGenrePost(){

        List<PostApiResponse> postList = new ArrayList<>();
        List<Post> AllPosts = postRepository.findAll();

        AllPosts.forEach(post->{
            Optional<User> findUser = baseRepository.findById(post.getUser().getId());
            Optional<User> filteredUser = findUser.filter(user-> user.getGenre()==1);

            filteredUser.ifPresent(u->{
                Optional<Post> findPost = postRepository.findByUserId(u.getId());
                findPost.ifPresent(item->{
                    PostApiResponse postApiResponse = PostApiResponse.builder()
                            .userName(item.getUser().getName())
                            .userGenre(item.getUser().getGenre())
                            .userImg(item.getUser().getImg())
                            .description(item.getDescription())
                            .img(item.getImg())
                            .title(item.getTitle())
                            .build();
                    postList.add(postApiResponse);
                });
            });
        });
        return postList;
    }

    public List<PostApiResponse> getAllPost(){
        List<Post> postList = postRepository.findAll();
        List<PostApiResponse> postApiResponseList = new ArrayList<>();

        postList.forEach(item->{
            PostApiResponse postApiResponse = PostApiResponse.builder()
                    .userName(item.getUser().getName())
                    .userGenre(item.getUser().getGenre())
                    .userImg(item.getUser().getImg())
                    .title(item.getTitle())
                    .img(item.getImg())
                    .description(item.getDescription())
                    .build();
            postApiResponseList.add(postApiResponse);
        });
        return postApiResponseList;
    }

    public User getUserProfile(){
        Optional<User> findUser = userRepository.findById(14L);

        if(findUser.isPresent()){
            return findUser.get();
        }else{
            return null;
        }

        // findUser.orElse()

//        return findUser.map(user->{
//            UserApiResponse userApiResponse = UserApiResponse.builder()
//                    .id(user.getId())
//                    .email(user.getEmail())
//                    .name(user.getName())
//                    .genre(user.getGenre())
//                    .followedCount(user.getFollowedCount())
//                    .followingCount(user.getFollowingCount())
//                    .isArtist(user.getIsArtist())
//                    .img(user.getImg())
//                    .bannerImg(user.getBannerImg())
//                    .build();
//            return userApiResponse;
//        });
    }

    public Optional<Object> getBannerImg(){
        Optional<User> findUser = userRepository.findById(14L);

        return findUser.map(user->{
                    UserApiResponse userApiResponse = UserApiResponse.builder()
                            .bannerImg(user.getBannerImg())
                            .build();
                    return userApiResponse;
                }
        );
    }


    //    인증
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));
        //TODO : 패스워드 예외처리
//        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new PasswordWrongException();
//        }
        return user;
    }

    public UserApiResponse responseForPageable(User user){
        return null;
    }

    public Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
//                .userId(user.getUserId())
                .build();


        return Header.OK(userApiResponse);
    }
}
