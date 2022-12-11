package it.unipi.dii.lsmd.service;

import it.unipi.dii.lsmd.repository.UserRepository;
import it.unipi.dii.lsmd.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceLocator {
        public static UserServiceImpl getUserService(){
            return new UserServiceImpl() ;
        }

}
