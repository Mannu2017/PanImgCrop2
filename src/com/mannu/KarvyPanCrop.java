package com.mannu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.swing.FilePane;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JRadioButton;

public class KarvyPanCrop extends JFrame {
	double w,h;
	int dat;
	JLabel selectedAreaPanel,signa,re;
	String pdfpath;
	JTable table;
	JButton save; //next,pri,
	JTextField acknam;
	String fnam;
	JLabel imgnam;
	JComboBox croptype;
	JRadioButton imgradio,sigradio;
	DefaultTableModel model;
	
	
	DataUtility utility=new DataUtility();
	List<String> filepath;
	/**
	 * @wbp.nonvisual location=972,259
	 */
	
    public KarvyPanCrop() {
        super("Karvy Pan Image Crop");
        
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowOpened(WindowEvent e) {
        		try {
        			String baseUrl = FilenameUtils.getPath("\\\\srv-kdms-file2\\images");
        			File fname=new File(baseUrl);
        			Runtime rt = Runtime.getRuntime();
        			if(!fname.exists()) {
        				rt.exec(new String[] {"cmd.exe","/c","net use \\\\srv-kdms-file2\\images /user:logicalaccess@karvy.com India@123"});
        			}
        			
        			} catch (Exception e2) {
        				e2.printStackTrace();
        			}
        		imgradio.setSelected(true);
        	}
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);
        DrawRectanglePanel dr=new DrawRectanglePanel();
       
       Toolkit tk=Toolkit.getDefaultToolkit();
       w=tk.getDefaultToolkit().getScreenSize().getWidth();
       h=tk.getDefaultToolkit().getScreenSize().getHeight();
       JScrollPane imgscr=new JScrollPane();
       imgscr.setSize((int)w-550, (int)h-65);
       getContentPane().add(imgscr);
       imgscr.setViewportView(dr);
       
       JLabel scut=new JLabel("Shortcut Keys");
		scut.setBounds((int)w-200, 400, 200, 30);
		scut.setForeground(Color.BLUE);
		scut.setFont(new Font("Serif", Font.BOLD, 14));
		getContentPane().add(scut);
		
//		JLabel ex=new JLabel("Browse: Alt+B");
//		ex.setBounds((int)w-200, 440, 200, 30);
//		ex.setFont(new Font("Arial", Font.BOLD, 12));
//		getContentPane().add(ex);
		
//		JLabel nx=new JLabel("Next: Alt+N");
//		nx.setBounds((int)w-200, 460, 200, 30);
//		nx.setFont(new Font("Arial", Font.BOLD, 12));
//		getContentPane().add(nx);
		
//		JLabel pr=new JLabel("Previous: Alt+P");
//		pr.setBounds((int)w-200, 480, 200, 30);
//		pr.setFont(new Font("Arial", Font.BOLD, 12));
//		getContentPane().add(pr);
			
		JLabel sv=new JLabel("Submit: Alt+S");
		sv.setBounds((int)w-200, 500, 200, 30);
		sv.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(sv);
		
		JLabel iq=new JLabel("Image Crop: Alt+Q");
		iq.setBounds((int)w-200, 520, 200, 30);
		iq.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(iq);
		
		JLabel sq=new JLabel("Signature Crop: Alt+A");
		sq.setBounds((int)w-200, 540, 200, 30);
		sq.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(sq);
       
		JScrollPane fnPane=new JScrollPane();
		fnPane.setBounds((int)w-540, 400, 300, 300);
		getContentPane().add(fnPane);
       
		table=new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dat = table.getSelectedRow();
				//System.out.println("AA: "+model.getValueAt(dat, 0));
				dr.setIcon(new ImageIcon((String) model.getValueAt(dat, 0)));
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"File Name"
				}
			));
		fnPane.setViewportView(table);
		
		
//		next=new JButton("Next");
//		next.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				String lno=imgnam.getText().substring(imgnam.getText().length()-1);
//				int nn=Integer.parseInt(lno)+1;
//				String nexnam="C:\\pan\\"+fnam+nn+".png";
//				File nexfil=new File(nexnam);
//				if (!nexfil.exists()) {
//					JOptionPane.showMessageDialog(null, "This is Last Page");
//				} else if (nexfil.exists()) {
//					dr.setIcon(new ImageIcon(nexnam));
//					imgnam.setText(fnam+nn);
//				}
//			}
//		});
//		next.setBounds((int)w-340, 350, 90, 30);
//		next.setMnemonic(KeyEvent.VK_N);
//		getContentPane().add(next);
		
//		pri=new JButton("Previous");
//		pri.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//			}
//		});
//		
//		pri.setBounds((int)w-440, 350, 90, 30);
//		pri.setMnemonic(KeyEvent.VK_P);
//		getContentPane().add(pri);
		
		save=new JButton("Submit");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model=(DefaultTableModel) table.getModel();
				model.setRowCount(0);
				Object[] row = new Object[1];
				if(acknam.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Ack No");
				} else if (acknam.getText().length()!=15) {
					JOptionPane.showMessageDialog(null, "Invalid Ack No");
				} else {
					dr.setIcon(null);
					signa.setIcon(null);
					selectedAreaPanel.setIcon(null);
					filepath=utility.getAllFiles(acknam.getText());
					for(String file:filepath) {
						row[0]=file;
						model.addRow(row);
					}
					table.getSelectionModel().setSelectionInterval(1, 0);
//					model.
				dr.setIcon(new ImageIcon((String) model.getValueAt(1, 0)));
					
				}
			}		
		});
		save.setBounds((int)w-240, 350, 90, 30);
		save.setMnemonic(KeyEvent.VK_S);
		getContentPane().add(save);
		
		JLabel acnam=new JLabel("ACK No: ");
		acnam.setBounds((int)w-500, 280, 80, 20);
		getContentPane().add(acnam);
		
		acknam=new JTextField();
		acknam.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (acknam.getText().length() == 20)) {
		          event.consume();
		        }
			}
		});
		acknam.setBounds((int)w-440, 280, 180, 20);
		getContentPane().add(acknam);
		
		selectedAreaPanel = new JLabel();
		selectedAreaPanel.setBounds((int)w-540, 20, 200, 212);
		getContentPane().add(selectedAreaPanel);
		
		imgscr=new JScrollPane();
		imgscr.setSize((int)w-550, (int)h-65);
		getContentPane().add(imgscr);
		
		signa = new JLabel("");
		signa.setBounds((int)w-330, 20, 222, 51);
		getContentPane().add(signa);
		
		re = new JLabel("");
		re.setBounds((int)w-540, 243, 390, 20);
		getContentPane().add(re);
		
		imgnam = new JLabel();
		imgnam.setFont(new Font("Arial", Font.BOLD, 12));
		imgnam.setBounds((int)w-200, 581, 200, 30);
		getContentPane().add(imgnam);
		
//		JLabel lblCroptype = new JLabel("CropType:");
//		lblCroptype.setBounds(866, 249, 65, 14);
//		getContentPane().add(lblCroptype);
	
		imgradio = new JRadioButton("Image Crop");
		imgradio.setMnemonic(KeyEvent.VK_Q);
		imgradio.setBounds(866, 249, 100, 14);
		getContentPane().add(imgradio);
		
		sigradio = new JRadioButton("Signature Crop");
		sigradio.setMnemonic(KeyEvent.VK_A);
		sigradio.setBounds(966, 249, 200, 14);
		getContentPane().add(sigradio);
		
		croptype = new JComboBox();
		croptype.setModel(new DefaultComboBoxModel(new String[] {"Photo Crop", "Signature Crop"}));
		croptype.setBounds(941, 146, 185, 20);
	//	getContentPane().add(croptype);
		setVisible(true);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(imgradio);
		bg.add(sigradio);
    }
    
private class DrawRectanglePanel extends JLabel implements MouseListener,
            MouseMotionListener {
         int x1;
         int y1;
         int x2;
         int y2;
         int a2;
         int k2;
        public DrawRectanglePanel() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
       
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.drawRect(x1, y1, x2, y2);   
        }
       public void mousePressed(MouseEvent e) {
    	   if(getIcon()==null) {
    		   JOptionPane.showMessageDialog(null, "Please Select PDF File");
    	   }
    	   if (acknam.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please Enter ACK Number");
		} else {
			x1 = e.getX();
            y1 = e.getY();
            x2 = 0;
            y2 = 0;
            a2 = 0;
            k2 = 0;
		} 
        }

        public void mouseReleased(MouseEvent e) {
        	try {
        		if (acknam.getText().isEmpty()) {
        			JOptionPane.showMessageDialog(null, "Please Enter ACK Number");
        		} else {
        			if (imgradio.isSelected()) {
        				File pstr=new File("C:\\karvy\\Photo\\");
					if (!pstr.exists()) {
						pstr.mkdirs();
					}
					
						File ph=new File(pstr+"\\"+acknam.getText()+"_Photo.jpg");
						if (ph.exists()) {
							int photoresult=JOptionPane.showConfirmDialog(null, "Image Already Exists \nDo You want to Replace");
							if(photoresult == JOptionPane.YES_OPTION){
								System.out.println("Mannu Image Crop");
								String crimg=getIcon().toString();
								BufferedImage scr=ImageIO.read(new File(crimg));
								BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
								ImageIO.write(phocro, "jpg", ph);
								ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Photo.jpg");
								Image image=icon.getImage();
								Image image2=image.getScaledInstance(selectedAreaPanel.getWidth(), selectedAreaPanel.getHeight(), Image.SCALE_SMOOTH);
								selectedAreaPanel.setIcon(new ImageIcon(image2));
								BufferedImage img = null;
					            BufferedImage tempJPG = null;
					            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
					            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
					            tempJPG = resizeImage(img, 204, 204, type);
					            
					            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
					            BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
								FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
								JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
								JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
								jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
					            jpegEncodeParam.setXDensity(200);
					            jpegEncodeParam.setYDensity(200);
					            jpegEncoder.encode(im, jpegEncodeParam);
					            fos.close();
					            File phosiz=new File(pstr+"\\"+acknam.getText()+"_Photo.jpg");
					            double bytes=phosiz.length();
					            double filsiz=(bytes / 	1024);
					            if(filsiz>12) {
					            	JOptionPane.showMessageDialog(null, "File Size More Then 12 k.b.");
					            }
							}
						} else {
							System.out.println("Mannu Image Crop-2");
							String crimg=getIcon().toString();
							BufferedImage scr=ImageIO.read(new File(crimg));
							BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
							ImageIO.write(phocro, "jpg", ph);
							ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Photo.jpg");
							Image image=icon.getImage();
							Image image2=image.getScaledInstance(selectedAreaPanel.getWidth(), selectedAreaPanel.getHeight(), Image.SCALE_SMOOTH);
							selectedAreaPanel.setIcon(new ImageIcon(image2));
							BufferedImage img = null;
				            BufferedImage tempJPG = null;
				            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
				            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
				            tempJPG = resizeImage(img, 204, 204, type);
				            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
				            
				            BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
							FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
							JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
							JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
							jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
				            jpegEncodeParam.setXDensity(200);
				            jpegEncodeParam.setYDensity(200);
				            jpegEncoder.encode(im, jpegEncodeParam);
				            fos.close();
				            File phosiz=new File(pstr+"\\"+acknam.getText()+"_Photo.jpg");
				            double bytes=phosiz.length();
				            double filsiz=(bytes / 	1024);
				            if(filsiz>12) {
				            	JOptionPane.showMessageDialog(null, "File Size More Then 12 k.b.");
				            }
						}
        			} else if(sigradio.isSelected()) {
        				File pstr=new File("C:\\karvy\\Sig\\");
					if (!pstr.exists()) {
						pstr.mkdirs();
					}
						File si=new File(pstr+"\\"+acknam.getText()+"_Sig.jpg");
						if (si.exists()) {
							int sigresult=JOptionPane.showConfirmDialog(null, "Image Already Exists \nDo You want to Replace");
							if(sigresult == JOptionPane.YES_OPTION){
								String crimg=getIcon().toString();
								BufferedImage scr=ImageIO.read(new File(crimg));
								BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
								ImageIO.write(phocro, "jpg", si);
								ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Sig.jpg");
								Image image=icon.getImage();
								Image image2=image.getScaledInstance(signa.getWidth(), signa.getHeight(), Image.SCALE_SMOOTH);
								signa.setIcon(new ImageIcon(image2));
								
								 BufferedImage img = null;
						            BufferedImage tempJPG = null;
						            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
						            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
						            tempJPG = resizeImage(img, 333, 137,type);
						            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
								
								BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
								FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
								JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
								JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
								jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
					            jpegEncodeParam.setXDensity(200);
					            jpegEncodeParam.setYDensity(200);
					            //jpegEncodeParam.setQuality(1.0f, true);
					            jpegEncoder.encode(im, jpegEncodeParam);
					            fos.close();
					            File phosiz=new File(pstr+"\\"+acknam.getText()+"_Sig.jpg");
					            double bytes=phosiz.length();
					            double filsiz=(bytes / 	1024);
					            if(filsiz>12) {
					            	JOptionPane.showMessageDialog(null, "File Size More Then 12 k.b.");
					            }
							}
						} else {
							String crimg=getIcon().toString();
							BufferedImage scr=ImageIO.read(new File(crimg));
							BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
							ImageIO.write(phocro, "jpg", si);
							ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Sig.jpg");
							Image image=icon.getImage();
							Image image2=image.getScaledInstance(signa.getWidth(), signa.getHeight(), Image.SCALE_SMOOTH);
							signa.setIcon(new ImageIcon(image2));
							
							 BufferedImage img = null;
					            BufferedImage tempJPG = null;
					            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
					            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
					            tempJPG = resizeImage(img, 333, 137,type);
					            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
							
							BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
							FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
							JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
							JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
							jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
				            jpegEncodeParam.setXDensity(200);
				            jpegEncodeParam.setYDensity(200);
				            //jpegEncodeParam.setQuality(1.0f, true);
				            jpegEncoder.encode(im, jpegEncodeParam);
				            fos.close();
				            File phosiz=new File(pstr+"\\"+acknam.getText()+"_Sig.jpg");
				            double bytes=phosiz.length();
				            double filsiz=(bytes / 	1024);
				            if(filsiz>12) {
				            	JOptionPane.showMessageDialog(null, "File Size More Then 12 k.b.");
				            }
						}
				}
			}
        	} catch (Exception e2) {
				System.out.println(e2);
			}
        }

        public BufferedImage resizeImage(final Image image, int i, int j, int type) {
        	BufferedImage resizedImage = new BufferedImage(i, j, type);
        	Graphics2D g = resizedImage.createGraphics();
        	g.drawImage(image, 0, 0, i, j, null);
        	g.dispose();
        	g.setComposite(AlphaComposite.Src);
        	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        			g.setRenderingHint(RenderingHints.KEY_RENDERING,
        			RenderingHints.VALUE_RENDER_QUALITY);
        			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        			RenderingHints.VALUE_ANTIALIAS_ON);
        	
        	return resizedImage;
		}
        
		public void mouseDragged(MouseEvent e) {
            x2 = e.getX() - x1;
            y2 = e.getY() - y1;
            a2=240+x1;
            k2=240+y1;
            
            repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    }

    public static void main(String[] args) {
        new KarvyPanCrop();
    }
};
