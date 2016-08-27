package com.jyothigas.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.FileUploader;

@Controller
public class KYCDocsController {

	@Autowired
	FileUploader uploader;

	@RequestMapping(value = Constant.KYC_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody Object uploadDocument(@RequestParam("custId") Integer custId,
			@RequestParam("file") MultipartFile file) {
		AppResponse appResponse = new AppResponse();
		if (!file.isEmpty()) {
			try {
				uploader.uploadFile(custId, file, Constant.KYC);
				appResponse.setStatus("OK");
				appResponse.setMessage("Success");
				return appResponse;
			} catch (Exception e) {
				appResponse.setStatus("Error");
				appResponse.setMessage("Please try after sometime");
				e.printStackTrace();
				return appResponse;
			}
		} else {
			appResponse.setStatus("Error");
			appResponse.setMessage("Empty File");
			return appResponse;
		}
	}

	@RequestMapping(value = Constant.KYC_DOWNLOAD, method = RequestMethod.POST)
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("custId") int custId) {
		try {
			File file = uploader.downloadFile(custId, Constant.KYC);
			if (null != file) {
				if (file.getName().indexOf("jpg") > 0) {
					response.setContentType("image/jpg");
				} else {
					response.setContentType("application/pdf");
				}
				response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
				try {
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				String errorMessage = "Sorry. The file you are looking for does not exist";
				System.out.println(errorMessage);
				OutputStream outputStream;
				try {
					outputStream = response.getOutputStream();
					outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = Constant.GET_DOCUMENT_NAME, method = RequestMethod.POST)
	public @ResponseBody Object getDocumentDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("custId") int custId) {
		AppResponse appResponse = new AppResponse();
		try {
			String fileName = uploader.getDocumentDetails(custId, Constant.KYC);
			if (null != fileName) {
				appResponse.setStatus("OK");
				appResponse.setResult(fileName);
			} else {
				String errorMessage = "Sorry. No KYC Document available";
				System.out.println(errorMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appResponse;
	}
}
