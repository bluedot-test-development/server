package develop.bluedot.server.controller;

import develop.bluedot.server.ifs.CrudInterface;
import develop.bluedot.server.network.Header;
import develop.bluedot.server.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<Req,Res,Entity> implements CrudInterface<Req,Res> {

    @Autowired
    protected BaseService<Req,Res,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header read(@PathVariable(name="id") Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable(name="id") Long id) {
        return baseService.delete(id);
    }
}
