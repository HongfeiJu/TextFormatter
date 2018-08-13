import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class Formatter {
	static String inputPath;
	static String outputPath;
	static int setLineLength=80;
	static int processedWordNum=0;
	static int linesNum=0;
	static int blankLineNum=0;
	static double wordsPerLine=0;
	static double lineLength=0;
	static double addedSpaceNum=0;	
	static String line;	
	static LinkedList<String> words=new LinkedList<String>();
	public static JFrame frame;
	public static JTextField textField;
	static boolean doubleSpacing=false;
	static boolean rightJustification=false;
	static boolean fullJustification=false;
	
	
	public static void textFormatter() throws Exception {
		//read words from the original file and store them in a linked list				
		//create the file chooser for selecting original files
		JButton open=new JButton();
		JFileChooser selectDialog= new JFileChooser();		
		selectDialog.setDialogTitle("select original text file");
		boolean reshow=true;
		
		
		while(reshow) {
			reshow=false;
			if(selectDialog.showOpenDialog(open)==JFileChooser.APPROVE_OPTION){
				//get the path of original file
				inputPath=selectDialog.getSelectedFile().getAbsolutePath();
				//check whether the file type is text. if it is, stop the loop.
				Path path=Paths.get(inputPath);
				String fileType=Files.probeContentType(path);				
				if (fileType.equals("text/plain")) {
					if(readWordsFromText()) {
						setFormat();
						//SetFormat window = new SetFormat();
						//window.frame.setVisible(true);	
					}
													
				}
				else{	
					JOptionPane.showMessageDialog(null, "error: only .txt file is accepted");
					reshow=true;
				}				
			}
		}
		
		
		//System.out.println("Formatting completed");//just for test
	}
	
	public static boolean readWordsFromText() throws Exception{
		//read the words and store them in the linked list
		File originalFile=new File(inputPath);				
		Scanner read=new Scanner(new FileReader(originalFile));		
		while(read.hasNextLine()) {
			line=read.nextLine();
			if(line.matches("^\\s*$")) blankLineNum++;
			else {
				String[] lineWords=line.split("\\s+");
				for (int i=0;i<lineWords.length;i++) {					
					words.add(lineWords[i]);
					processedWordNum++;
					}			
			}			
		}			
		read.close();
		return true;
	}
		/**
		 * Initialize the contents of the frame.
		 * 
		 */
	
	public static void setFormat() {
		frame = new JFrame();
		frame.setBounds(100, 100, 656, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLineLength = new JLabel("set line length [20 to 100]");
		lblLineLength.setBounds(42, 100, 326, 33);
		frame.getContentPane().add(lblLineLength);
		
		textField = new JTextField();
		textField.setBounds(329, 97, 113, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("set");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(Integer.parseInt(textField.getText())<20||Integer.parseInt(textField.getText())>100) {
						JOptionPane.showMessageDialog(null, "error: line length out of range");
					}else {
						setLineLength=Integer.parseInt(textField.getText());
						btnNewButton.setBackground(Color.red);
					}
					//frame.setVisible(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(467, 97, 107, 39);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSingleSpacing = new JButton("single spacing");
		btnSingleSpacing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSingleSpacing.setBackground(Color.RED);
			}
		});
		btnSingleSpacing.setBounds(42, 150, 225, 41);
		frame.getContentPane().add(btnSingleSpacing);
		
		JButton btnNewButton_1 = new JButton("double spacing");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnNewButton_1.setBackground(Color.RED);
					doubleSpacing=true;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(42, 221, 225, 41);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblLengthSetAnd = new JLabel("options for text formatter");
		lblLengthSetAnd.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblLengthSetAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblLengthSetAnd.setBounds(89, 28, 439, 33);
		frame.getContentPane().add(lblLengthSetAnd);
		
		JButton btnLeftJustification = new JButton("left justification");
		btnLeftJustification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnLeftJustification.setBackground(Color.RED);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLeftJustification.setBounds(349, 150, 225, 41);
		frame.getContentPane().add(btnLeftJustification);
		
		JButton btnRightJustification = new JButton("right justification");
		btnRightJustification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					btnRightJustification.setBackground(Color.red);
					rightJustification=true;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRightJustification.setBounds(349, 221, 225, 41);
		frame.getContentPane().add(btnRightJustification);
		
		JButton btnFullJustification = new JButton("full justification");
		btnFullJustification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					btnFullJustification.setBackground(Color.red);
					fullJustification=true;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnFullJustification.setBounds(349, 290, 225, 41);
		frame.getContentPane().add(btnFullJustification);
		
		JButton btnFormat = new JButton("Format");
		btnFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					writeWords();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnFormat.setBounds(42, 290, 225, 41);
		frame.getContentPane().add(btnFormat);
		
		frame.setVisible(true);
	}	
	
	public static void writeWords() throws Exception{
		
		//format and save text
		//build the dialog for selecting formatted file path
		JFileChooser saveDialog=new JFileChooser();
		//saveDialog.setCurrentDirectory(new File("c:/users/hongfei/desktop"));
		saveDialog.setDialogTitle("save formatted text file");
		
		boolean reshow=true;
		while(reshow) {
			reshow=false;
			if(saveDialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
				//get the path of formatted file
				outputPath=saveDialog.getSelectedFile().getAbsolutePath();				
				//check whether the path is the same as the original file path. If they are different, stop the loop
				if (inputPath.equals(outputPath)) {
					JOptionPane.showMessageDialog(null, "error: avoid selecting original file");
					reshow=false;
					continue;				
				}		
				//get the words from the linked list and write it based on the required format
				if (fullJustification) {
					leftJustificationFormat();						
					fullJustificationFormat();
					processInfo();
					showInformation();
				}else {
					leftJustificationFormat();							
					if (rightJustification) rightJustificationFormat();
					processInfo();
					showInformation();
				}						
				
				
				}
			}
	}

	
	public static void leftJustificationFormat() throws Exception{
		File f2=new File(outputPath);			
		FileWriter write=new FileWriter(f2, true);
		write.write("");				
		int wordNum=0;			
		int characterNum=0;
		boolean firstWord=true;
		boolean veryFirstWord=true;
		String word="";
		
		while(wordNum<processedWordNum) {
			linesNum++;
			characterNum=0;
			firstWord=true;
			while(wordNum<processedWordNum) {
				if (veryFirstWord) {
					word=words.get(wordNum).toString();
					veryFirstWord=false;					
				}
				if (!firstWord) word=words.get(wordNum).toString();			
				
				if (characterNum+word.length()+1>setLineLength&&word.length()>=setLineLength) {
					write.write("\r\n");
					if (doubleSpacing) write.write("\r\n");
					linesNum++;
					write.write(word);
					wordNum++;
					word=words.get(wordNum).toString();
					break;
				}
				if(characterNum+word.length()+1>setLineLength&&word.length()<setLineLength) {
					break;
				}
				if (firstWord) {	
					characterNum+=word.length();
					write.write(word);					
					firstWord=false;
				}else {					
					characterNum+=(word.length()+1);
					write.write(" "+word);
					addedSpaceNum+=1;
					
				}				
				wordNum++;					
			}
			write.write("\r\n");	
			if (doubleSpacing) write.write("\r\n");
		}		
		write.close();
	}	

	
	public static void rightJustificationFormat() throws Exception{
		LinkedList<String> tempWords=new LinkedList<String>();
		File fO=new File(outputPath);
		Scanner readO=new Scanner(new FileReader(fO));
		int totalLength=0;
		while(readO.hasNextLine()) {
			tempWords.add(readO.nextLine());			
		}
		fO.delete();
		
		File rJFile=new File(outputPath);
		FileWriter rJWriter=new FileWriter(rJFile,false);
		rJWriter.write("");
		int lineNum=0;
		int blanksNum=0;
		while(!tempWords.isEmpty()) {
			String s=tempWords.get(lineNum);
			tempWords.remove(lineNum);
			//System.out.println(s);
			blanksNum=setLineLength-s.length();
			if (blanksNum>0) {
				for (int i=0;i<blanksNum;i++) {
					rJWriter.write(" ");
					addedSpaceNum+=1;
				}
				
			}
			rJWriter.write(s);
			rJWriter.write("\r\n");			
		}
		rJWriter.close();
	}
	
	public static void fullJustificationFormat() throws Exception{
		LinkedList<String> tempWords=new LinkedList<String>();
		File fO=new File(outputPath);
		Scanner readO=new Scanner(new FileReader(fO));		
		while(readO.hasNextLine()) {
			tempWords.add(readO.nextLine());			
		}
		fO.delete();
		
		File rJFile=new File(outputPath);
		FileWriter rJWriter=new FileWriter(rJFile,false);
		rJWriter.write("");
		int lineNum=0;
		
		while(!tempWords.isEmpty()) {
			String s=tempWords.get(lineNum);			
			String[] words=s.split(" ");
			for (int m=0;m<words.length;m++) {
				System.out.print(words[m]);
			}
			System.out.println();
			
			int wordsNum=words.length;
			if (wordsNum==1) {
				rJWriter.write(s);
				rJWriter.write("\r\n");				
				tempWords.remove(lineNum);
				continue;
			}
			
			int charLength=0;
			for (int k=0;k<words.length;k++) {
				charLength+=words[k].length();
			}
			System.out.println("char length: "+charLength);
			System.out.println("word number: "+words.length);
			int baseBlankNum=(setLineLength-charLength)/(wordsNum-1);
			int extraBlankNum=setLineLength-s.length()-(wordsNum-1)*(baseBlankNum-1);
			int addedBlanks=setLineLength-s.length();
			System.out.println("addedBlanks: "+addedBlanks);
			addedSpaceNum+=addedBlanks;
			System.out.println(baseBlankNum);
			System.out.println(extraBlankNum);
			tempWords.remove(lineNum);	
			
			for (int i=0;i<wordsNum;i++) {	
				if (i!=0) {
					for (int j=0;j<baseBlankNum;j++) {
						rJWriter.write(" ");
						//if(baseBlankNum>1)addedSpaceNum++;
					}
					if (extraBlankNum>0) {
						rJWriter.write(" ");
						extraBlankNum--;
						//addedSpaceNum++;
						
					}
				}				
				rJWriter.write(words[i]);
			}
			rJWriter.write("\r\n");			
		}
		rJWriter.close();
	}
	
	public static void processInfo() throws Exception{
		wordsPerLine=(double) processedWordNum/(double) linesNum;
		//read the formatted file to get the information about length per line
		LinkedList<String> tempWords=new LinkedList<String>();
		File fO=new File(outputPath);
		Scanner readO=new Scanner(new FileReader(fO));
		int totalLength=0;
		while(readO.hasNextLine()) {
			String s=readO.nextLine();
			tempWords.add(s);
			totalLength+=s.length();
		}
		if (doubleSpacing) linesNum=linesNum*2-1;
		lineLength=(double) totalLength/(double) linesNum;
		 
	}
	
	public static void showInformation() {
		DecimalFormat numFormatter=new DecimalFormat("0.00");
		String info="formatting information: \n processed words: "
	+processedWordNum+"\n lines: "+linesNum+"\n removedBlankLines: "+blankLineNum
	+"\n average words per line: "+numFormatter.format(wordsPerLine)+"\n average line length: "+numFormatter.format(lineLength)
	+"\n Total added space: "+numFormatter.format(addedSpaceNum);
		JOptionPane.showMessageDialog(new JFrame(),info);
	}
	
	public static void main(String[] agrs) throws Exception{
		Formatter formatter=new Formatter();
		formatter.textFormatter();
		//formatter.showInformation();
		}
	}


