package develop.bluedot.server.service;

import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.enumclass.UserStatus;
import develop.bluedot.server.network.Header;
import develop.bluedot.server.network.Pagination;
import develop.bluedot.server.network.request.UserApiRequest;
import develop.bluedot.server.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends BaseService<UserApiRequest,UserApiResponse,User> {

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

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userData = request.getData();

        User newUser = User.builder()
                .email(userData.getAccount())
                .password(userData.getPassword())
                .build();

        User returnData = baseRepository.save(newUser);

        return response(returnData);
    }

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

    public UserApiResponse responseForPageable(User user){
        return null;
    }

    public Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();


        return Header.OK(userApiResponse);
    }
}
