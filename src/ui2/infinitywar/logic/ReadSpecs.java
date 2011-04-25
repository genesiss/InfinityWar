package ui2.infinitywar.logic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class ReadSpecs {
	public static Warrior[] readFile(String file) {
		
		FileInputStream fstream;
		DataInputStream in;
		BufferedReader br=null;
		Warrior[] warriors = null;
		try {
			fstream = new FileInputStream(file);
			 in = new DataInputStream(fstream);
			 br = new BufferedReader(new InputStreamReader(in));
			 String strLine;
			 int numOfWarriors = Integer.parseInt(br.readLine());
			 warriors = new Warrior[numOfWarriors];
			 int i = -1;
			 while ((strLine = br.readLine()) != null)   {
				 if(strLine.contains("----")) {
					 i++;
					 warriors[i] = new Warrior();
				 }
				 else if(strLine.contains("Attributes")) {
					 String attrLine;
					 int numOfAttr = Integer.parseInt(strLine.trim().split(":")[1]);
					 for(int j = 0; j < numOfAttr; j++) {
						 attrLine = br.readLine();
						 String[] line = attrLine.trim().substring(1).split("=");
						 warriors[i].attributes.put(line[0], Integer.parseInt(line[1]));
					 }
					 
				 }
				 else if (strLine.contains("Action")) {
					 String actLine;
					 String[] in_names = null;
					 int[] in_deltas = null;
					 String[] out_names = null;
					 int[] out_deltas = null;
					 int numOfAttr = Integer.parseInt(strLine.trim().split(":")[1]);
					 for(int h = 0; h < numOfAttr; h++) {
						 actLine = br.readLine();
						 if(actLine.contains("in_att")) {
							 String[] line = actLine.trim().substring(1).split(":");
							 in_names = new String[Integer.parseInt(line[1])];
							 in_deltas = new int[Integer.parseInt(line[1])];
							 for(int j=0; j < Integer.parseInt(line[1]); j++) {
								 String att = br.readLine();
								 String[] line2 = att.trim().substring(1).split("=");
								 in_names[j] = line2[0];
								 in_deltas[j] = Integer.parseInt(line2[1]);
							 }
						 }
						 else if(actLine.contains("out_att")) {
							 String[] line = actLine.trim().substring(1).split(":");
							 out_names = new String[Integer.parseInt(line[1])];
							 out_deltas = new int[Integer.parseInt(line[1])];
							 for(int j=0; j < Integer.parseInt(line[1]); j++) {
								 String att = br.readLine();
								 String[] line2 = att.trim().substring(1).split("=");
								 out_names[j] = line2[0];
								 out_deltas[j] = Integer.parseInt(line2[1]);
							 }
						 }
						 
						 
					 }
					 
					 Action a = new Action(in_names, in_deltas, out_names, out_deltas);
					 warriors[i].actions.add(a);
				 }
				 else {
					 warriors[i].name = strLine;
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
		
		
		
		
		return warriors;
		
	}
}
