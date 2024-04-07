package com.study.board.controller;


import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/write") //localhost:8080/board/write
    public String boardWriteForm() {
        return "boardWrite";
    }

    @PostMapping("/writepro")
    public String boardWritePro(Board board, Model model,@RequestParam(name="file", required = false) MultipartFile file) throws Exception {
        //System.out.println(board.getTitle());
        boardService.write(board, file);

        model.addAttribute("message","게시물이 등록되었습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }

    @GetMapping("/list")
    public String boardList(Model model) { //Model은 데이터를 담아서 뷰로 전달하는 역할
        model.addAttribute("list", boardService.list());
        return "boardList";
    }

    @GetMapping("/view/{id}")
    public String boardView(Model model, @PathVariable(name = "id") Integer id){
        model.addAttribute("board", boardService.view(id));
        return "boardView";
    }

    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable(name = "id") Integer id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/update/{id}")
    public String boardUpdate(Model model, @PathVariable(name = "id") Integer id) {
        model.addAttribute("board", boardService.view(id));
        return "boardUpdate";
    }

    @PostMapping("/updateBoard/{id}")
    public String boardUpdatePro(Board board, @PathVariable(name = "id") Integer id, Model model,@RequestParam(name="file", required = false) MultipartFile file) throws Exception {
        Board updateBoard = boardService.view(id);
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());

        boardService.write(updateBoard, file); //수정된 내용으로 다시 저장

        model.addAttribute("message","게시물이 수정되었습니다.");
        model.addAttribute("searchUrl","/board/view/"+id);

        return "message";
    }
}
