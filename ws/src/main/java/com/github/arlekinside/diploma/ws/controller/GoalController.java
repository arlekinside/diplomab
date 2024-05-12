package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.Goal;
import com.github.arlekinside.diploma.data.repo.GoalRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.arlekinside.diploma.ws.Utils.nvl;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController extends AbstractCrudController<Goal>{

    private final GoalRepo goalRepo;

    @Override
    public Goal create(Authentication auth, @RequestBody Goal goal) {
        goal.setUser(getUser(auth));
        return goalRepo.save(goal);
    }

    @Override
    public List<Goal> readAll(Authentication auth) {
        return goalRepo.findAllByUser(getUser(auth));
    }

    @Override
    public Goal read(Authentication auth, @PathVariable Long id) {
        return goalRepo.findByIdAndUser(id, getUser(auth))
                .orElseThrow(() -> new NotFoundException(id, Goal.class));
    }

    @Override
    public Goal update(Authentication auth, @PathVariable Long id, @RequestBody Goal goal) {
        var dbGoal = read(auth, id);
        var name = nvl(goal.getName(), dbGoal.getName());
        var price = nvl(goal.getPrice(), dbGoal.getPrice());
        var incomePercent = nvl(goal.getIncomePercent(), dbGoal.getIncomePercent());
        var recurringIncomePercent = nvl(goal.getRecurringIncomePercent(), dbGoal.getRecurringIncomePercent());

        dbGoal.setName(name);
        dbGoal.setPrice(price);
        dbGoal.setIncomePercent(incomePercent);
        dbGoal.setRecurringIncomePercent(recurringIncomePercent);

        return goalRepo.save(goal);
    }

    @Override
    public void delete(Authentication auth, @PathVariable Long id) {
        goalRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
