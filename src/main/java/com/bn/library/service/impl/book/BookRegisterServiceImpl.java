package com.bn.library.service.impl.book;

import com.bn.library.exception.NotExistException;
import com.bn.library.model.BookRegister;
import com.bn.library.repository.BookRegisterRepository;
import com.bn.library.service.BookRegisterService;
import org.springframework.stereotype.Service;

@Service
public class BookRegisterServiceImpl implements BookRegisterService {
    private final BookRegisterRepository bookRegisterRepository;

    public BookRegisterServiceImpl(BookRegisterRepository bookRegisterRepository) {
        this.bookRegisterRepository = bookRegisterRepository;
    }

    @Override
    public BookRegister getBookRegisterById(int id) {
        return bookRegisterRepository.findById(id).orElseThrow(NotExistException::new);
    }
}
