package com.ems.ManagerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ems.ChartBean.ChartBean;
import com.ems.ManagerBean.ManagerBean;
import com.ems.ManagerService.ManagerService;

@CrossOrigin
@RestController
public class ManagerController {
	
	@Autowired
	private ManagerService mService;
	
	
	@RequestMapping(value = "/manager/addManager",  method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addManager(@RequestBody ManagerBean managerBean){
		try {
		long mId = mService.addManager(managerBean);
		return new ResponseEntity<String>("Manager added successfully with id "+mId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to add manager", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/manager/addManagerImageById/{managerId}",  method = RequestMethod.POST,
			 produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addManagerImageById(@RequestParam("managerImage") MultipartFile managerImage, @PathVariable("managerId") long managerId){
		try {
		long mId = mService.addManagerImageById(managerId, managerImage);
		return new ResponseEntity<String>("Manager Image added successfully with id:"+mId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to add manager Image", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/manager/getManagerById",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ManagerBean> getManagerById(@RequestParam("managerId") long managerId){
		try {
		ManagerBean managerBean = mService.getManagerById(managerId);
		managerBean.setManagerImage(null);
		return new ResponseEntity<ManagerBean>(managerBean, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<ManagerBean>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/manager/getManagerImageById",  method = RequestMethod.GET)
	public ResponseEntity<Resource> getManagerImageById(@RequestParam("managerId") long managerId){
		try {
		ManagerBean mbean = mService.getManagerById(managerId);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(mbean.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mbean.getFileName() + "\"")
				.body(new ByteArrayResource(mbean.getManagerImage()));
		}
		catch(Exception e){
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value = "/manager/getManagerByUserId",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ManagerBean> getManagerByUserId(@RequestParam("userId") long userId){
		try {
		ManagerBean managerBean = mService.getManagerByUserId(userId);
		managerBean.setManagerImage(null);
		return new ResponseEntity<ManagerBean>(managerBean, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<ManagerBean>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/manager/getChartDetails/{managerId}",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChartBean> getChartDetails(@PathVariable("managerId") long managerId){
		try {
		ChartBean chartBean = mService.getChartDetails(managerId);
		return new ResponseEntity<ChartBean>(chartBean, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<ChartBean>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
