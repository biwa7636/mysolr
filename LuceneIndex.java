package com.datafireball;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

public class LuceneIndex {
	private static Directory directory;

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		// open
		Path path = Paths.get("/tmp/myindex/index");
		directory = new SimpleFSDirectory(path);
		IndexWriter writer = getWriter();
		
		// index
		int documentCount = 10000000;
		List<String> fieldNames = Arrays.asList("id", "manu");

		FieldType myFieldType = new FieldType();
		myFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		myFieldType.setOmitNorms(true);
		myFieldType.setStored(true);
		myFieldType.setTokenized(true);
		myFieldType.freeze();
		
		for (int i = 0; i < documentCount; i++) {
			Document doc = new Document();
			for (int j = 0; j < fieldNames.size(); j++) {
				doc.add(new Field(fieldNames.get(j), fieldNames.get(j) + Integer.toString(i), myFieldType));
			}
			writer.addDocument(doc);
		}
		// close
		writer.close();
		System.out.println("Finished Indexing");
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println(estimatedTime);
	}

	private static IndexWriter getWriter() throws IOException {
		return new IndexWriter(directory, new IndexWriterConfig(new WhitespaceAnalyzer()));
	}
	
	
}
