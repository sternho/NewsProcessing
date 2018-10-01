package com.nlp.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.Word2VecDao;
import com.nlp.db.vo.Word2Vec;
import com.nlp.manager.Word2VecMgr;

@Controller
public class Word2VecController {

	@RequestMapping(value = "/word2vec_train", method = RequestMethod.POST)
	public String train(@RequestParam("file") MultipartFile[] files, Model model) throws IOException {
		if (files.length > 0) {
			List<InputStream> in = new ArrayList<InputStream>();
			for (MultipartFile file : files)
				in.add(file.getInputStream());

			Word2VecMgr.getInstance().train(in.toArray(new InputStream[0]));

			model.addAttribute("status", "Upload success!");
		}

		return "train_upload";
	}

	@RequestMapping(value = "/word2vec", method = RequestMethod.GET)
	public String train(Model model) {
		model.addAttribute("name", "abc");
		return "train_upload";
	}

	@RequestMapping(value = "/set_key", method = RequestMethod.GET)
	public ModelAndView keyWords(@RequestParam(value="key", required=false) String key) throws ClassNotFoundException, SQLException {
		if(key!=null) {
			Word2Vec w2v = new Word2Vec();
			w2v.setKey(key);
			getW2vDao().insertOrUpdate(w2v);
		}
		
		List<Word2Vec> list = getW2vDao().selectAll();
		ModelAndView model = new ModelAndView();
    	model.addObject("keys", list);
    	model.setViewName("w2v_key");
    	return model;
	}

	public Word2VecDao getW2vDao() throws ClassNotFoundException {
		return (Word2VecDao) DatabaseFactory.getInstance().getDAO("Word2VecDao");
	}
	
}
