package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.ws.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.List;

@RequiredArgsConstructor
public abstract class MoneyFlowController<T extends MoneyFlow> extends AbstractCrudController<T> {

    protected final MoneyFlowRepo<T> moneyFlowRepo;
    private Class<T> clazz;

    @Override
    public T create(Authentication auth, T t) {
        t.setUser(getUser(auth));
        return moneyFlowRepo.save(t);
    }

    @Override
    public List<T> readAll(Authentication auth) {
        return moneyFlowRepo.findAllByUser(getUser(auth));
    }

    @Override
    public T read(Authentication auth, Long id) {
        return moneyFlowRepo.findByIdAndUser(id, getUser(auth)).orElseThrow(() -> new NotFoundException(id, null));
    }

    @Override
    public T update(Authentication auth, Long id, T t) {
        throw new NotFoundException(null, null);
    }

    @Override
    public void delete(Authentication auth, Long id) {
        moneyFlowRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
