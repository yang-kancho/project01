package com.project.project00.service;

import com.project.project00.repository.DirectMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectMessageService {
    @Autowired
    DirectMessageRepository directMessageRepository;
}
