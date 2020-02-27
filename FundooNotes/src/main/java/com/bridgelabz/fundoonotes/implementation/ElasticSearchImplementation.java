package com.bridgelabz.fundoonotes.implementation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.controller.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.service.ElasticSearch;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImplementation implements ElasticSearch{
	
	@Autowired
	private ElasticSearchConfig config;

	@Autowired
	private ObjectMapper objectmapper;
	
	private String INDEX ="springboot";
	
	private String TYPE = "note_detail";
	
	
	@Override
	public String CreateNote(NoteInformation noteInformation) {
		Map<String, Object> notemapper = objectmapper.convertValue(noteInformation, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(noteInformation.getId())).source(notemapper);
		IndexResponse indexResponse = null;
		try {
			indexResponse = config.client().index(indexrequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return indexResponse.getResult().name();
		
		
		
	}


	@Override
	public String DeleteNote(NoteInformation info) {
		Map<String, Object> notemapper = objectmapper.convertValue(info, Map.class);
		System.out.println("es ======="+info.getId());
		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, String.valueOf(info.getId()));
		System.out.println(info.getId());
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = config.client().delete(deleterequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
		
		
	}


	@Override
	public List<NoteInformation> searchbytitle(String title) {
		
		System.out.println(title);
		SearchRequest searchrequest = new SearchRequest("springboot");
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		System.out.println(searchrequest);
		
		searchsource.query(QueryBuilders.matchQuery("title",title));
		searchrequest.source(searchsource);
		SearchResponse searchresponse = null;
		try {
			searchresponse = config.client().search(searchrequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getResult(searchresponse);
	}
	private List<NoteInformation> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits = searchresponse.getHits().getHits();
		List<NoteInformation> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectmapper.convertValue(hit.getSourceAsMap(), NoteInformation.class)));
		}
		return notes;
	}
}
