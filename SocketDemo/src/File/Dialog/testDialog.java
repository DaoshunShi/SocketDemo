package File.Dialog;


import java.awt.*;  
import java.awt.event.*;  

public class testDialog extends Frame {  
    
  private TextField tf=new TextField(10);  
    
  public testDialog()  
  {  
      Button b1=new Button("打开模态对话框！");  
      Button b2=new Button("打开非模态对话框！");  
      //增加到容器上  
      add(tf,"North");  
      add(b1,"Center");  
      add(b2,"East");  
      //打开模态对话框  
      b1.addActionListener(new ActionListener()  
      {  
          public void actionPerformed(ActionEvent e)  
          {  
              /**              
               * 不能用new testdialog(),导致两个对象 
               * 也不能用this，this指的是调用方法的对象，即【new ActionListener()】 
               */  
              MyDialog dlg=new MyDialog(testDialog.this,"模式对话框！",true);  
              dlg.setInfo(tf.getText());  
              dlg.setVisible(true);  
              tf.setText(dlg.getInfo());  
                
          }  
      }  
      );  
      //打开非模态对话框  
      b2.addActionListener(new ActionListener()  
      {  
          public void actionPerformed(ActionEvent e)  
          {  
              /**              
               * 不能用new testdialog(),导致两个对象 
               * 也不能用this，this指的是调用方法的对象，即【new ActionListener()】 
               */  
              MyDialog dlg=new MyDialog(testDialog.this,"模式对话框！",false);  
              dlg.setInfo(tf.getText());  
              dlg.setVisible(true);  
              //tf.setText(dlg.getinfo());  
                
          }  
      }  
      );  
      addWindowListener(new WindowAdapter()  
      {  
          public void windowClosing(WindowEvent e)  
          {  
              dispose();  
              System.exit(0);  
    
          }  
            
      });  
  }  
  public void setInfo(String info)  
  {  
      tf.setText(info);  
  }  
  public static void main(String[] args) {  
      // TODO Auto-generated method stub  

      testDialog dw=new testDialog();  
      dw.setSize(400, 300);  
      dw.setTitle("DrawLine");  
      dw.setVisible(true);  
        
        
  }  

}  