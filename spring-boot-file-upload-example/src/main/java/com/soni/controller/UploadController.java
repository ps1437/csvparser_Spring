/*
 * 
 */
package com.soni.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * The Class UploadController.
 */
@Controller
public class UploadController {

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@GetMapping("/")
	public String index() {
		return "upload";
	}

	/**
	 * Single file uploddad.
	 *
	 * @param file
	 *            the file
	 * @param redirectAttributes
	 *            the redirect attributes
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@PostMapping("/upload")
	public String singleFileUploddad(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws IOException {
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()), ',', '"', 1);
		List<String[]> allRows = reader.readAll();
		for (String[] row : allRows) {
			System.out.println(Arrays.toString(row));
		}
		redirectAttributes.addAttribute("message", "Added Sucessfully..!!");
		return "redirect:/uploadStatus";
	}

	/**
	 * Mapto bean.
	 *
	 * @param file
	 *            the file
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String maptoBean(@RequestParam("file") MultipartFile file) throws IOException {

		CsvToBean<Employee> csv = new CsvToBean();
		CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()), ',', '"', 1);

		List list = csv.parse(setColumMapping(), reader);

		for (Object object : list) {
			Employee employee = (Employee) object;
			System.out.println(employee);
		}
		return null;
	}

	/**
	 * Sets the colum mapping.
	 *
	 * @return the column position mapping strategy
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ColumnPositionMappingStrategy setColumMapping() {
		ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
		strategy.setType(Employee.class);
		String[] columns = new String[] { "id", "firstName", "lastName", "country", "age" };
		strategy.setColumnMapping(columns);
		return strategy;
	}

	/**
	 * Upload status.
	 *
	 * @return the string
	 */
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

}