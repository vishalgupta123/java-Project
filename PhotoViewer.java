package photoviewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class PhotoViewer extends JFrame {
	
	JFrame jf,jfFullScreen;
	
	JPanel panel,panelLeft,panelImage,panelControl;
	JSplitPane splitPaneV,splitPaneH;
	
	JList list;
	DefaultListModel model;
	
	JLabel j1;
	ImageIcon img;
	
	JFileChooser Filechooser,Folderchooser;
	
	JButton btnNext,btnPrev,btnStart,btnStop,btnFullScreen;
	
	JMenuBar mb;//Making Reference Variable
	JMenu file ,edit,settings,help;//Making Reference Variable
	JMenuItem open,openFolder,exit,cut,copy,paste;//Making Reference Variable
	
	int listCount;
	
	Timer timer;
	
	public PhotoViewer(){
		setFrameWindow();//method call
		createMenu();// method call
		initComp();
		
	}//Constructor
	final void setFrameWindow() {
		 jf= this;// current object reference is assigned into this
		
		setSize(800, 600);//to set the size of window
		setVisible(true);//to show window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
		setLocationRelativeTo(null);//to set windows to center
		setTitle("Photo Viewer");
		
		panel=new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		panelLeft =new JPanel();
		panelLeft.setLayout(new BorderLayout());
		
		panelImage=new JPanel();
		panelImage.setLayout(new BorderLayout());
		panelImage.setBackground(Color.BLACK);//it will set the image Background  Black
		
		panelControl=new JPanel();
		panelControl.setLayout(new FlowLayout());
		
		splitPaneV=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneV.setDividerLocation(650);//It will Set the divider location 
		splitPaneV.setLeftComponent(panelImage);//It will Show the Image
		splitPaneV.setRightComponent(panelControl);//It will Control the Image View
		
		splitPaneH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneH.setDividerLocation(250);//It will Set the Divider Location
		splitPaneH.setLeftComponent(panelLeft);//It will show the Image List
		splitPaneH.setRightComponent(splitPaneV);//It will add the SplitPaneV
		panel.add(splitPaneH,BorderLayout.CENTER);
	}//setting the window frame
	final void createMenu() {
		mb=new JMenuBar();
		setJMenuBar(mb);//adding MenuBar
		
		file=new JMenu("File");
		mb.add(file);//adding Menu to the MenuBar
		
		edit=new JMenu("Edit");
		mb.add(edit);//adding Menu to the MenuBar
		
		settings=new JMenu("Settings");
		mb.add(settings);//adding Menu to the MenuBar
		
		help=new JMenu("Help");
		mb.add(help);//adding Menu to the MenuBar
		
		open=new JMenuItem("Open");
		file.add(open);//adding MenuItem to the File Menu
		
		openFolder=new JMenuItem("Open Folder");
		file.add(openFolder);//adding MenuItem to the File Menu
		
		file.addSeparator();
		
		exit=new JMenuItem("Exit");
		file.add(exit);//adding MenuItem to the File Menu
		
		cut=new JMenuItem("Cut");
		edit.add(cut);//adding MenuItem to the Edit Menu
		
		copy=new JMenuItem("Copy");
		edit.add(copy);//adding MenuItem to the Edit Menu
		
		paste=new JMenuItem("Paste");
		edit.add(paste);//adding MenuItem to the Edit Menu
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Filechooser=new JFileChooser();
				Filechooser.setCurrentDirectory(new java.io.File("."));
				Filechooser.setDialogTitle("Select Image File");
				
				if(Filechooser.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION) {
					String path = Filechooser.getSelectedFile().toString();//Taking the Path of the Image
					//System.out.println(Filechooser.getSelectedFile().toString());
					img = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(400,300, Image.SCALE_DEFAULT));
					j1.setIcon(img);		//Showing the Image on the Screen
				}
			}
		});
		
		openFolder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Folderchooser=new JFileChooser();
				Folderchooser.setCurrentDirectory(new java.io.File("."));
				Folderchooser.setDialogTitle("Select Image File");
				Folderchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if(Folderchooser.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION) {
					File folder=new File(Folderchooser.getSelectedFile().toString());
					File[] listOfFiles = folder.listFiles();
					model.removeAllElements();
					for(File file : listOfFiles) {
						if(file.isFile())
						{
							model.addElement(file.getPath());
						}
					}
					listCount = listOfFiles.length;
					list.setSelectedIndex(0);
				}
			}
		});
		
	}
	final void initComp() {
		model =new DefaultListModel();
		list=new JList(model);
		panelLeft.add(list,BorderLayout.CENTER);
		
		j1=new JLabel();
		j1.setHorizontalAlignment(JLabel.CENTER);
		j1.setVerticalAlignment(JLabel.CENTER);// it will make the image in the center
		panelImage.add(j1,BorderLayout.CENTER);//it will make the image in the center
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				img = new ImageIcon(new ImageIcon(list.getSelectedValue().toString()).getImage().getScaledInstance(600,500, Image.SCALE_DEFAULT));
				j1.setIcon(img);		//Showing the Image on the Screen
			}
		});
		
		btnNext=new JButton("Next");
		panelControl.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()<listCount-1) {
					list.setSelectedIndex(list.getSelectedIndex()+1);
				}
			}
		});
		
		btnPrev=new JButton("Previous");
		panelControl.add(btnPrev);
		btnPrev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()>0) {
					list.setSelectedIndex(list.getSelectedIndex()-1);
				}
			}
		});
		
		btnStart=new JButton("Start");
		panelControl.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			}
		});
		
		timer =new Timer(3000,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()<listCount-1) {
					list.setSelectedIndex(list.getSelectedIndex()+1);
				}
				else {
					list.setSelectedIndex(0);
				}
			}
		});
		
		btnStop=new JButton("Stop");
		panelControl.add(btnStop);
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
			}
		});//btnStop code same as btnStart
		
		btnFullScreen=new JButton("Full Screen");
		panelControl.add(btnFullScreen);
		btnFullScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jfFullScreen=new JFrame();
				jfFullScreen.setLayout(new BorderLayout());
				jfFullScreen.setUndecorated(true);
				jfFullScreen.setVisible(true);
				jfFullScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);

				JLabel j1full=new JLabel();
				jfFullScreen.add(j1full,BorderLayout.CENTER);
				img = new ImageIcon(new ImageIcon(list.getSelectedValue().toString()).getImage().getScaledInstance(jfFullScreen.getWidth(),jfFullScreen.getHeight(), Image.SCALE_DEFAULT));
				j1full.setIcon(img);
			}
		});
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new PhotoViewer();//Constructor call
			}
		});
		
		
	}

}
