package com.kh.sample01.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class MyFileUploadUtil {

	// 파일명얻기
	public static String uploadFile(String uploadPath, String originalFilename, byte[] fileData) throws Exception {
		// 어디에 저장해야 하는지 얻음
		String datePath = calcPath(uploadPath);
		// uuid 고유한 값 uuid_~.png
		UUID uuid = UUID.randomUUID(); // 중복되지 않는 고유한 값(뭐이상한 숫자랑 영어 조합으로 나옴)
		// D:/upload/2021/6/30/uuid_파일이름.png
		String filePath = datePath + "/" + uuid + "_" + originalFilename;
		System.out.println("filePath:" + filePath); // 실제 저장될 파일명
		File target = new File(filePath);
		// 파일 복사?
		FileCopyUtils.copy(fileData, target);
		// -- 원본업로드 -- 
		// 썸네일 이미지 만들기
		boolean isImage = isImage(filePath);
		if (isImage) {
			filePath = makeThumbnail(filePath);
		}
		return filePath;
	};
	
	// 파일경로
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		
		String dateString = year + "/" + month + "/" + date; // 2021/6/30 폴더 생성(폴더 사진 갯수 제한있어서 각 날마다 만들어줌)
		String datePath = uploadPath + "/" + dateString;
		// -> D:/upload/2021/6/30
		System.out.println("datePath:" + datePath);
		
		File f = new File(datePath);
		// 폴더만들기(여러개)
		if (!f.exists()) {
			f.mkdirs();
		}
		
		return datePath;
	}
	
	// 파일의 확장명 얻기
	private static String getExtName(String fileName) {
		// ~.png
		int dotIndex = fileName.lastIndexOf(".");
		String ext = fileName.substring(dotIndex + 1);
		// 사용자의 업로드 파일이 소문자건, 대문자건 무조건 대문자로 보내서 대문자만 비교
		return ext.toUpperCase();
	}
	
	
	
	// 이미지 파일인지 여부
	public static boolean isImage(String fileName) {
		String ext = getExtName(fileName);
		// 확장자명이 이미지일경우 true리턴해줌
		if (ext.equals("JPG") || ext.equals("GIF") || ext.equals("PNG")) {
			return true;
		}
		return false;
	}
	
	// 썸네일 이미지 생성
	public static String makeThumbnail(String filePath) {
		// D://upload/2021/6/30/uuid_noname.png
		// D://upload/2021/6/30/sm_uuid_noname.png -> 이 파일에 작은이미지(썸네일)
		int slashIndex = filePath.lastIndexOf("/");
		String front = filePath.substring(0, slashIndex + 1);	// -> D:/upload/2021/6/30
		String rear = filePath.substring(slashIndex + 1); // -> uuid_noname.png
		// 썸네일 파일 이미지 이름(기존이름 + sm_ )
		String thumbnailPath = front + "sm_" + rear;
		
		File orgFile = new File(filePath);
		File thumbFile = new File(thumbnailPath);
		
		try {
			// 이미 업로드 된 원본 파일을 메모리에 로딩
			BufferedImage srcImage = ImageIO.read(orgFile);
			// (원본이미지, 비율 맞추기, 높이에 맞추기)
			BufferedImage destImage = Scalr.resize(srcImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
			ImageIO.write(destImage, getExtName(rear), thumbFile);
			// 썸네일 이미지를 해당파일의 확장자 형식을 썸네일 파일로 저장
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thumbnailPath;
	}
	
	private static void deleteWhile(File f) {
		while (true) {
			if (f.exists()) {
				boolean b = f.delete();
				if (b) break;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// 첨부파일 삭제
	public static void deleteFile(String fileName) throws Exception {
		File f = new File(fileName);
		deleteWhile(f);
		
		if (isImage(fileName)) {
			String[] names = fileName.split("sm_");
			String orgFile = names[0] + names[1];
			System.out.println("orgFile:" + orgFile);
			File f2 = new File(orgFile);
			deleteWhile(f2);
		}
	}
	
	
}
