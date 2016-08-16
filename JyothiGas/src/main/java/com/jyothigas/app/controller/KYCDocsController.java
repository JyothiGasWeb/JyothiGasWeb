package com.jyothigas.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.service.KYCService;

@Controller
public class KYCDocsController {

	@Autowired
	KYCService kycService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
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

	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("custId") int custId) {

		// Authorized user will download the file
		File file = new File("");
		if (file.exists()) {
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
