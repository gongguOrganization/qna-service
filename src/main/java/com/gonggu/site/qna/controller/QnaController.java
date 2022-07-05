package com.gonggu.site.qna.controller;

import com.gonggu.site.qna.model.QnaDto;
import com.gonggu.site.qna.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("qna")
@Slf4j
public class QnaController {
    @Autowired
    private QnaService qnaService;
    @Autowired
    QnaDto qnaDto;

    @GetMapping("")
    public List<QnaDto> selectQna() {
        return qnaService.getQnaAll();
    }
    @PostMapping("")
    public QnaDto insertQna(
            @RequestBody QnaDto qnaDto) {
        log.info(String.valueOf(qnaDto));

        QnaDto result = null;
        try {
            qnaDto.setBoardId(qnaDto.getBoardId());
            qnaDto.setQuestionId(qnaDto.getQuestionId());
            qnaDto.setQuestion(qnaDto.getQuestion());
            log.info(String.valueOf(qnaDto));

            result = qnaService.insertQna(qnaDto);
            log.info(String.valueOf(result));
        }
        catch (Exception e){
            System.err.println("[ERROR] " + e.getMessage());
        }

        return result;
    }
    @PutMapping("")
    public List<QnaDto> updateQna(@RequestBody QnaDto bodyQnaDto) {
        log.info(String.valueOf(qnaDto));

        QnaDto result = null;
        try {
            QnaDto qnaDto = qnaService.getAnswerOne(bodyQnaDto.getId());
            qnaDto.setQuestion(bodyQnaDto.getQuestion());
            qnaDto.setQuestionDate(LocalDateTime.now());

            result = qnaService.updateQna(qnaDto);
            log.info(String.valueOf(result));
        }
        catch (Exception e){
            System.err.println("[ERROR] " + e.getMessage());
        }

        return qnaService.getQnaAll();
    }

    @DeleteMapping("/{id}")
    public void deleteQna(@PathVariable String id) {
        log.info("Delete id : ",String.valueOf(Integer.valueOf(id)));
        qnaService.deleteQna(Integer.valueOf(id));
    }

    @PostMapping("/answer")
    public QnaDto insertAnswer(
            @RequestBody QnaDto qnaDto) {
        System.out.println(qnaDto.getAnswer());
        int BOARD_ID = 1;
        int ANSWER_ID = 1;
        int QUESTION_ID = 1;
        QnaDto result = null;
        try {
            qnaDto.setBoardId(BOARD_ID);
            qnaDto.setAnswerId(ANSWER_ID);
            qnaDto.setQuestionId(QUESTION_ID);
            qnaDto.setAnswer(qnaDto.getAnswer());
            log.info(String.valueOf(qnaDto));

            result = qnaService.insertAnswer(qnaDto);
            log.info(String.valueOf(result));
        }
        catch (Exception e){
            System.err.println("[ERROR] " + e.getMessage());
        }

        return result;
    }
    @PutMapping("/answer")
    public QnaDto updateAnswer(@RequestBody QnaDto qnaDto) {
        log.info("Update : ",String.valueOf(qnaDto));

        return qnaService.updateAnswer(qnaDto.getId(), qnaDto.getAnswer());
    }
    @PutMapping("/answer/{id}")
    public QnaDto deleteAnswer(@PathVariable int id) {
        log.info("Delete id : ",Integer.valueOf(id));
        String remove_answer = "이 글은 작성자에 의해 삭제 되었습니다.";
        return qnaService.updateAnswer(id,remove_answer);
    }
}
