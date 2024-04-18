package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void testCreateUser() {
		// remember to change this based on the admin role id (1)
		Role roleAdmin = testEntityManager.find(Role.class, 9);  
		//
		User userAdmin = new User("root@user.com", "123456", "root", "user");
		userAdmin.addRole(roleAdmin);
		User savedUser = userRepository.save(userAdmin);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		User userSuper = new User("super@user.com","123456","super","user");
		//change this based on the role id sales(2) editor(3)
		Role sales= new Role(10);
		Role editor = new Role(11);
		//
		userSuper.addRole(editor);
		userSuper.addRole(sales);
		User savedUser = userRepository.save(userSuper);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = userRepository.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User foundUser = userRepository.findById(1).get();
		System.out.println(foundUser);
		assertThat(foundUser).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User foundUser = userRepository.findById(1).get();
		foundUser.setEnabled(true);
		foundUser.setEmail("root@user.com");
		userRepository.save(foundUser);
	}
	
	@Test
	public void testUpdateUSerRole() {
		User foundUser = userRepository.findById(2).get();
		Role editor = new Role(10);
		Role shipping = new Role(12);
		foundUser.getRoles().remove(editor);
		foundUser.addRole(shipping);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId =2;
		userRepository.deleteById(userId);
		userRepository.findById(userId);
	}
}
