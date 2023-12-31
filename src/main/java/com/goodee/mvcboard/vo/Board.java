package com.goodee.mvcboard.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board {
	private int boardNo;
	private String localName;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private String createdate;
	private String updatedate;
	
	// table속성 x , 입력폼 속성 > BoardForm.class(DTO), Board.clss(도메인) 분리해서 사용가능
	private List<MultipartFile> multipartFile;
}
