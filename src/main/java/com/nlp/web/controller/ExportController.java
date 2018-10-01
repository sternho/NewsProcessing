package com.nlp.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExportController {

	@RequestMapping("/export_csv")
	public String toPage() throws IOException {
		return "export_csv";
//		response.sendRedirect("export_csv");
	}

}
