package develop.bluedot.server.ifs;


import develop.bluedot.server.network.Header;

public interface CrudInterface<Req,Res> {

    Header<Res> create(Header<Req> request);

    Header read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);

//    Header authenticate(String email, String password);
}