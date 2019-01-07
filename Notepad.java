package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {

	private JFrame frame;
	private JTextArea textArea;
	private JMenuBar menu;
	private JMenu file;
	private Font selectedFont = new Font("Consolas",Font.BOLD,16);
	private JScrollPane scroll;
	private JMenuItem fileClear,fileOpen,fileSave;
	private JFileChooser fileChooser;
	
	public Notepad() {
		this.frame = new JFrame("Notepad");
		this.frame.setFont(selectedFont);
		//ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/4.png"));
		//this.frame.getIconImages().add(img.getImage());
		//this.frame.setIconImage(img.getImage());
		this.frame.setSize(new Dimension(700,700));
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		
		this.setTextArea();
				
		this.frame.add(textArea,BorderLayout.CENTER);
		
		this.file = new JMenu("File");
		this.file.setFont(selectedFont);
		this.file.addSeparator();
		
		//Setting up menu options
		this.fileClear = this.createMenuItem(file, "Clear");
		this.fileClear.setFont(selectedFont);
		this.fileOpen =this.createMenuItem(file, "Open");
		this.fileOpen.setFont(selectedFont);
		this.fileSave= this.createMenuItem(file, "Save");
		this.fileSave.setFont(selectedFont);
		this.fileChooser = new JFileChooser();
		
		//Linking a menu bar
		this.menu = new JMenuBar();
		this.menu.setFont(selectedFont);
		this.menu.add(file);
		this.frame.add(menu,BorderLayout.NORTH);
		this.frame.add(fileChooser);

		//Adding a scroll option to the textArea
		this.scroll = new JScrollPane(this.textArea);
		this.scroll.setAutoscrolls(true);
		this.frame.add(this.scroll);
		
		// Adding a keyListener for the frame
		this.frame.addKeyListener(new innerListener());
		this.frame.setFocusable(true);
		this.frame.setVisible(true);
	}
	
	//Nested class for listener implementation
	class innerListener implements ActionListener,KeyListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem src = (JMenuItem)e.getSource();
			if(src.equals(fileClear)) {
				//A set of actions for creating a new file
				textArea.setText("");
			}
			else if(src.equals(fileSave)) {
				//A set of actions for saving a file
				if(JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(frame)) {
					File innerFile;
					
					// Make sure file name ends with txt
					if(!fileChooser.getSelectedFile().getName().endsWith(".txt")) {
						 innerFile = new File(fileChooser.getSelectedFile() + ".txt");
					}
					else {
						 innerFile = fileChooser.getSelectedFile();
					}
				
					//Write all text from textArea to the created file
					try (PrintWriter fileWriter = new PrintWriter(innerFile)){
						fileWriter.print(textArea.getText());
						fileWriter.flush();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						System.exit(-1);
					}
					
				}
				
			}else {
				//A set of actions for opening an existing file
				
				if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)) {
					File file = fileChooser.getSelectedFile();
					try {
						Scanner chosenFileReader = new Scanner(file);
						
						textArea.setText("");
						while(chosenFileReader.hasNext()) {
							String eachLine = chosenFileReader.nextLine();
							textArea.append(eachLine + "\n");
						}
						
						chosenFileReader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
						System.exit(-1);
					}
					
				}
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	//Setting up the textArea here for some clarity
	private void setTextArea() {
		this.textArea = new JTextArea();
		this.textArea.setFont(this.selectedFont);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setBackground(Color.BLACK);
		this.textArea.setForeground(Color.WHITE		);
		this.textArea.setSelectedTextColor(Color.YELLOW);
		this.textArea.setSelectionColor(Color.GRAY);
	}
	//Creating menu items here for some clarity
	public JMenuItem createMenuItem(JMenu menu,String title) {
			JMenuItem item = new JMenuItem(title);
			item.addActionListener(new innerListener());
			menu.add(item);
		return item;
	}
	
	public static void main(String[] args) {
		new Notepad();
	}
}
