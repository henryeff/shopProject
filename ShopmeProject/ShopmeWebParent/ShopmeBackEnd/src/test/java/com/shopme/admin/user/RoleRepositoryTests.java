package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Root Access Role");
		Role savedRole = roleRepository.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSales = new Role("Sales", "Manage price, customer, shipping, orders and reports");
		Role roleEditor = new Role("Editor", "Manage categories, brands, products, articles, menus");
		Role roleShipper = new Role("Shipping", "View products, view orders, update status");
		Role roleAssistant = new Role("Assistant", "Manage questions and review");
		roleRepository.saveAll(List.of(roleSales, roleEditor, roleShipper, roleAssistant));
		
	}
}
