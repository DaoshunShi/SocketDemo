package File.Dialog;

import java.awt.Button;  
import java.awt.Dialog;  
import java.awt.Frame;  
import java.awt.TextField;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
  
public class MyDialog extends Dialog{  
  
    private TextField tf=new TextField(10);  
    private String str=null;  
    public MyDialog(Frame owner, String title, boolean modal)  
    {  
        super(owner, title, modal);  
        setBounds(0,0,200,150);  
        Button b1=new Button("Ӧ��");  
        Button b2=new Button("ȷ��");  
        add(tf,"North");  
        add(b1,"Center");  
        add(b2,"East");  
        if(this.isModal()==true)  
        {  
            b1.setEnabled(false);  
        }  
        b1.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent e)  
            {  
                ((testDialog)MyDialog.this.getOwner()).setInfo(tf.getText());  
            }  
        });  
        //ȷ����ť�Ĺ���  
        b2.addActionListener(new ActionListener()  
        {  
            public void actionPerformed(ActionEvent e)  
            {  
                if(isModal()==true)  
                {  
                    str=new String(tf.getText());  
                }  
                else  
                {  
                  ((testDialog)MyDialog.this.getOwner()).setInfo(tf.getText());  
                }  
                dispose();  
            }  
        });  
    }  
    public String getInfo()  
    {  
        return str;  
    }  
    public void setInfo(String strInfo)  
    {  
        tf.setText(strInfo);  
    }  
}  
