package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //게시물 작성
    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, filename);

        file.transferTo(saveFile);

        board.setFilename(filename);
        board.setFilepath("/files/" + filename);

        boardRepository.save(board);
    }


    //게시물 목록
    public Page<Board> list(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


    //특정 게시물 상세보기
    public Board view(Integer id) {
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void delete(Integer id) {
        Board board = boardRepository.findById(id).get();
        boardRepository.delete(board);
        //boardRepository.deleteById(id);
    }

    public Page<Board> searchKeyword(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }


}
