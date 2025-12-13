package com.penote.penote.service;

import com.penote.penote.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   public boolean distinct (String userPassword, User user) {
       if (userPassword == null)
           return false;

       if (!user.getUserPassword().equals(userPassword))
           return false;

       else return true;
   }
}
