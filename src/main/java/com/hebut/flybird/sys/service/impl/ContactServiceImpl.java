package com.hebut.flybird.sys.service.impl;

import com.hebut.flybird.sys.entity.Contact;
import com.hebut.flybird.sys.entity.User;
import com.hebut.flybird.sys.repository.ContactRepository;
import com.hebut.flybird.sys.repository.UserRepository;
import com.hebut.flybird.sys.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJL on 2017/5/7.
 */
@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public void save(Contact contact) {
        contactRepository.save(contact);
    }

}
