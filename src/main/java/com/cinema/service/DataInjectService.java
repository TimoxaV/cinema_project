package com.cinema.service;

import com.cinema.model.Role;
import com.cinema.model.User;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInjectService {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public DataInjectService(UserService userService,
                             RoleService roleService,
                             ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    private void injectData() {
        Role roleAdmin = Role.of("ADMIN");
        Role roleUser = Role.of("USER");
        roleService.add(roleAdmin);
        roleService.add(roleUser);
        User adminUser = new User();
        adminUser.setEmail("admin@i.ua");
        adminUser.setPassword("admin");
        adminUser.setRoles(Set.of(roleAdmin, roleUser));
        userService.add(adminUser);
        shoppingCartService.registerNewShoppingCart(adminUser);
        User user = new User();
        user.setEmail("user@i.ua");
        user.setPassword("111");
        user.setRoles(Set.of(roleUser));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
    }
}
