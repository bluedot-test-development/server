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
                .account(userData.getAccount())
                .password(userData.getPassword())
                .phoneNumber("010404031414")
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

        UserApiRequest userApiRequest = request.getData();

        String enumString = UserStatus.REGISTERED.getDescription();

        Optional<User> findUser = baseRepository.findById(userApiRequest.getId());

        return findUser.map(user -> {
            user.setId(userApiRequest.getId());
            user.setStatus(enumString);
            return user;
        }).map(user -> baseRepository.save(user))
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("에러"));
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
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return userApiResponse;
    }

    public Header<UserApiResponse> response(User user) {

        UserStatus enumString = UserStatus.REGISTERED;

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .status(enumString)
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .account(user.getAccount())
                .build();


        return Header.OK(userApiResponse);
    }
}
