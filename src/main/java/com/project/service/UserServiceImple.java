package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repository.UserDao;

@Service
public class UserServiceImple implements UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private Emailservice emailservice ;
    // private JavaMailSender javaMailSender;

	@Override
public User insertUser(User user) {
    String emailText = "Dear " + user.getName() + ",\n\n" +
            "You are Succesfully Registered at EcoCycle\n" +
            "Your login details are as below: \n\n" +
            "UserId:"+user.getEmail()+"\n\n" +
			"password:"+user.getPassword()+"\n\n"+
			
            "Best regards,\n" +
            "EcoCycle";

    emailservice.sendEmail(user.getEmail(), "Registration Successful", emailText);

    return userDao.save(user);

	}

	@Override
	public List<User> getUserByEmailAndPassword(String email,String password) {
		return userDao.findByEmailAndPassword(email,password);
	}
	
	@Override
	public List<User> findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public List<User> findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}
	
	@Override
	public List<User> getAllUsers() {
		
		return this.userDao.findAll();
	}

	@Override
	public List<User> forgetPassword(String email,String securityQues, String securityAns) {
		return userDao.findByEmailAndSecurityQuesAndSecurityAns(email,securityQues, securityAns);
	}

	@Override
	public void deleteUser(String email) {
		User user =  userDao.getById(email);
		userDao.delete(user);	
	}

	@Override
	public User updatePassword(User user) {
		User u = userDao.getById(user.getEmail());
		u.setPassword(user.getPassword());
		 String emailText = "Dear " + user.getName() + ",\n\n" +
            "Your updated Password for Ecocycle\n" +
            "Your New Login Creditionals are as follow: \n\n" +
            "UserId:"+user.getEmail()+"\n\n" +
			"password:"+user.getPassword()+"\n\n"+
			
            "Best regards,\n" +
            "EcoCycle";

    emailservice.sendEmail(user.getEmail(), "Password Update", emailText);
   
		return userDao.save(u);
	}

	
	
	
}
