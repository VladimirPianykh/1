import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class User implements Serializable{
	public String name,password;
	public int tries;
	public LocalDateTime lock;
	public User(String name,String password){
		this.name=name;this.password=password;
	}
	public static HashMap<String,User>userMap=new HashMap<String,User>();
	public void login(String pwd){
		if(lock!=null){
			if(lock.until(LocalDateTime.now(),ChronoUnit.MINUTES)>4)return;
			else lock=null;
		}
		if(pwd==password){
			//TODO: login
		}else if(tries>2)lock=LocalDateTime.now();
	}
	@SuppressWarnings("unchecked")
	public static void save(){
		try{
			FileInputStream fIS=new FileInputStream("C:/Users/user/AppData/Local/"/*TODO: write a name*/);
			ObjectInputStream oIS=new ObjectInputStream(fIS);
			User.userMap=(HashMap<String,User>)oIS.readObject();
			fIS.close();oIS.close();
		}catch(Exception ex){ex.printStackTrace();}
	}
}

class Main{
	public static void main(String[]args) throws Exception{
		Dimension d=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getSize();
		JFrame f=new JFrame();
		f.setSize(d);
		f.setUndecorated(true);
		f.setExtendedState(2);
		f.setBackground(Color.GRAY);
		f.setLayout(null);
		int s=f.getWidth()/40;
		Font font=new Font(Font.DIALOG_INPUT,Font.PLAIN,s);
		JTextField t1=new JTextField();JTextField t2=new JTextField();
		JLabel l1=new JLabel("name");JLabel l2=new JLabel("password");
		FontMetrics fm=t1.getFontMetrics(font);
		t1.setBounds(f.getWidth()/2+s,f.getHeight()/2-s*1,f.getWidth()/10,s*2);
		t2.setBounds(f.getWidth()/2+s,f.getHeight()/2+s*3,f.getWidth()/10,s*2);
		l1.setBounds(f.getWidth()/2-(s+fm.stringWidth("name")),f.getHeight()/2-s*1,s+fm.stringWidth("name"),s*2);
		l2.setBounds(f.getWidth()/2-(s+fm.stringWidth("password")),f.getHeight()/2+s*3,s+fm.stringWidth("password"),s*2);
		JCheckBox c=new JCheckBox();
		JButton b=new JButton(new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				if(c.isSelected()){User.userMap.put(t1.getText(),new User(t1.getText(),t2.getText()));}
				else{User.userMap.get(t1.getText()).login(t2.getText());}
			}
		});
		t1.setFont(font);t2.setFont(font);l1.setFont(font);l2.setFont(font);
		b.setBounds(f.getWidth()/2-f.getWidth()/5,f.getHeight()-s*6,f.getWidth()*2/5,s*5);
		f.add(t1);f.add(t2);f.add(l1);f.add(l2);f.add(b);
		b.setText("enter");
		b.setFont(font);
		f.setVisible(true);
	}
}
