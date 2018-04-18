package com.dub.spring.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dub.spring.exceptions.SharewoodException;
import com.dub.spring.model.Photo;



/**
 * @author Dominique Ubersfeld
 */
public interface SharewoodServices {
	
	List<Photo> getSharewoodPhotosMy() throws SharewoodException;
	
	List<Photo> getSharewoodSharedPhotos() throws SharewoodException;
	
	Photo getSharewoodPhoto(long id) throws SharewoodException;
	
	void deletePhoto(long id) throws SharewoodException;
			
	long createPhoto(MultipartFile uploadedFileRef, String title, boolean shared) 
			throws SharewoodException, IOException;
	
	void updatePhoto(Photo photo) 
			throws SharewoodException, IOException;

	InputStream loadSharewoodPhoto(String id) throws SharewoodException;
}
