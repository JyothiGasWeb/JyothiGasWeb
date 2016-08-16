package com.jyothigas.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import com.jyothigas.app.entity.KYCDocumentEntity;
import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.service.KYCService;
import com.jyothigas.utils.Constant;

@Controller
public class KYCDocsController {

	@Autowired
	KYCService kycService;

	@RequestMapping(value = Constant.KYC_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody Object uploadDocument(@RequestParam("custId") String custId,
			@RequestParam("file") MultipartFile file) {
		AppResponse appResponse = new AppResponse();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "KYCDocuments" + File.separator + custId);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + "KYC_" + custId + ".pdf");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				kycService.saveDocument(file.getName(), serverFile.getAbsolutePath(), custId);
				appResponse.setStatus("OK");
				appResponse.setMessage("Success");
				return appResponse;
			} catch (Exception e) {
				appResponse.setStatus("Error");
				appResponse.setMessage("Please try after sometime");
				appResponse.setHttpErrorCode(405);
				appResponse.setOauth2ErrorCode("invalid_token");
				e.printStackTrace();
				return appResponse;
			}
		} else {
			appResponse.setStatus("Error");
			appResponse.setMessage("Empty File");
			return appResponse;
		}
	}

	@RequestMapping(value =Constant.KYC_DOWNLOAD, method = RequestMethod.POST)
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("custId") int custId) {

		KYCDocumentEntity doc = kycService.getDocument(custId);
		if (null != doc) {
			File file = new File(doc.getLocation());
			if (file.exists()) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + doc.getDocumentName() + ".pdf");
				try {
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
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

			return;
		}
	}
}
