package com.datafireball;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrJTest {
	public static void main(String[] args) throws SolrServerException, IOException {
		
		int[] data = {10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000};	
		for (int i = 0; i < data.length; i++) {
			int batchSize = data[i];
			System.out.println(batchSize);
			System.out.println(Benchmark(batchSize));
			System.out.println("-------------");
		}
	}

	public static int Benchmark(int batchSize) throws SolrServerException, IOException {
		long startTime = System.currentTimeMillis();
		String zkHostString = "localhost:9983";
		SolrClient solr = new CloudSolrClient(zkHostString);
		String mycollection = "gettingstarted";
		java.util.Date date = new java.util.Date();

		int totalSize = 1000000;
		int numField = 10;
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		// truncate existing collection
		solr.deleteByQuery(mycollection, "*:*");
		solr.commit(mycollection);

		for (int i = 0; i < totalSize; i++) {
			// when the batch is full, add to solr and clear batch
			if ((i + 1) % batchSize == 0) {
				solr.add(mycollection, docs);
				docs.clear();
				// System.out.println("Batch:" + i/batchSize);
			}
			SolrInputDocument mydoc = new SolrInputDocument();
			String myid = "myweirdid" + Integer.toString(i) + "_" + Long.toString(date.getTime());
			mydoc.addField("id", myid);
			for (int j = 0; j < numField; j++) {
				mydoc.addField("col" + Integer.toString(j), "col" + Integer.toString(j) + "_value" + myid);
			}
			docs.add(mydoc);
		}
		solr.commit(mycollection);

		long estimatedTime = System.currentTimeMillis() - startTime;
//		System.out.println(estimatedTime);
//
//		SolrQuery myquery = new SolrQuery();
//		myquery.set("q", "*:*");
//		QueryResponse myresponse = solr.query(mycollection, myquery);
//		SolrDocumentList mylist = myresponse.getResults();
		return (int) estimatedTime;
	}

}
