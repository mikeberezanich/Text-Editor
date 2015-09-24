import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This is a simple text editor created for HW 1 in CMPSC 221 Section 901 at The Pennsylvania State University
 */

/**
 * @author michaelberezanich@gmail.com
 *
 */
public class textEditor extends JFrame{
	
	public textEditor(){
		super("Mike's Text Editor");
		final JTextArea area = new JTextArea();
		getContentPane().add(area, BorderLayout.CENTER);
		setSize(640,480);
		final UndoManager undoManager = new UndoManager();
		
		area.getDocument().addUndoableEditListener(
		        new UndoableEditListener() {
		          public void undoableEditHappened(UndoableEditEvent e) {
		            undoManager.addEdit(e.getEdit());
		          }
		        });
		
		JMenuBar bar = new JMenuBar();
		
		//Creating each menu and menu option
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		JMenu edit = new JMenu("Edit");
		
		JMenuItem fresh = new JMenuItem("New");
		fresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				JFrame frame = new textEditor();
			}
		});
		
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(textEditor.this);
				File file = fc.getSelectedFile();
				String filename = file.getPath();
				String line = null;
				
				try {
		            FileReader fileReader = new FileReader(filename);
		            BufferedReader bufferedReader = new BufferedReader(fileReader);

		            while((line = bufferedReader.readLine()) != null) {
		            	area.setText(line);
		            }   

		            bufferedReader.close();         
		        }
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                filename + "'");                
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + filename + "'");
			}
		}});
		
		JMenuItem save = new JMenuItem("Save As");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(textEditor.this);
				String filename = fc.getSelectedFile().getPath();
				
				try{
					FileWriter fileWriter = new FileWriter(filename);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					bufferedWriter.write(area.getText());
					bufferedWriter.close();
				}
				catch(IOException ex) {
		            System.out.println(
		                "Error writing to file '"
		                + filename + "'");
				
			}
		}});
		
		JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
		cut.setText("Cut");
		
		JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
		copy.setText("Copy");
		
		JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
		paste.setText("Paste");
		
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				try {
			          undoManager.undo();
			        } catch (CannotRedoException cre) {
			          cre.printStackTrace();
			        }
			}
		});
		
		//For some reason the console displays an exception even though the redo function works perfectly
		JMenuItem redo = new JMenuItem("Redo"); 
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				try {
			          undoManager.redo();
			        } catch (CannotRedoException cre) {
			          cre.printStackTrace();
			        }
			}
		});
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				JOptionPane.showMessageDialog(null, "This is a simple text editor created for HW 1 in" +
						"\nCMPSC 221 Section 901 at The Pennsylvania State University" +
						"\nAuthor: Mike Berezanich");
			}
		});
		
		//Adding each item to the menus
		file.add(fresh);
		file.add(open);
		file.add(save);
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(undo);
		edit.add(redo);
		help.add(about);
		
		//Adding the menus to the menu bar
		bar.add(file);
		bar.add(edit);
		bar.add(help);
		
		setJMenuBar(bar);
		
		setVisible(true);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new textEditor();

	}

}
