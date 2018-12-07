package main;

import java.io.File;

public class Proba
{
	/*
	public static void main(String[] args)
	{
		Proba p = new Proba();
		//p.start();
		p.start2();
	}
*/
	/**
	 * Construct a DOT graph in memory, convert it
	 * to image and store the image in the file system.
	 */
	
	/**
	 * Read the DOT source from a file,
	 * convert to image and store the image in the file system.
	 */
	public void start2()
	{
		//String dir = "/home/jabba/Dropbox/git.projects/laszlo.own/graphviz-java-api";     // Linux
		//String input = dir + "/sample/simple.dot";
		String input = "..\\Apptomata\\grafoPgraphviz.dot";    // Windows

		GraphViz gv = new GraphViz();
		gv.readSource(input);
		System.out.println(gv.getDotSource());

		String type = "png";
		//    String type = "dot";
		//    String type = "fig";    // open with xfig
		//    String type = "pdf";
		//    String type = "ps";
		//    String type = "svg";    // open with inkscape
		//    String type = "png";
		//      String type = "plain";
		
		
		String repesentationType= "dot";
		//		String repesentationType= "neato";
		//		String repesentationType= "fdp";
		//		String repesentationType= "sfdp";
		// 		String repesentationType= "twopi";
		//		String repesentationType= "circo";
		System.out.println("gera imagem");
		//File out = new File("/tmp/simple." + type);   // Linux
		File out = new File("..\\Apptomata\\grafoGerado." + type);   // Windows
		gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
	}
}
