package com.datafireball;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Readfdt {
	public static void main(String[] args) throws IOException {
		System.out.println("Program Starting:");

		Path path = Paths.get("/tmp/simplecodec");
		Directory dir = FSDirectory.open(path);
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//		config.setCodec(new SimpleTextCodec());
//		config.setUseCompoundFile(false);

		IndexWriter writer = new IndexWriter(dir, config);

		writer.addDocument(Arrays.asList(new TextField("title", "bin title of my first document", Store.NO),
				new TextField("content", "The content of the first document", Store.YES)));

		writer.addDocument(Arrays.asList(new TextField("title", "wang title of the second document", Store.YES),
				new TextField("content", "The content of the second document", Store.NO)));

		writer.addDocument(Arrays.asList(new TextField("title", "glenna title of the third document", Store.YES),
				new TextField("content", "The content of the third document", Store.YES)));
		
		writer.addDocument(Arrays.asList(new TextField("title", "The title of the fourth document", Store.NO),
				new TextField("content", "The content of the fourth document", Store.NO)));

		writer.close();
		System.out.println("Program Ending:");
	}
}