package File.Dialog;


import java.awt.*;  
import java.awt.event.*;  

public class testDialog extends Frame {  
    
  private TextField tf=new TextField(10);  
    
  public testDialog()  
  {  
      Button b1=new Button("��ģ̬�Ի���");  
      Button b2=new Button("�򿪷�ģ̬�Ի���");  
      //���ӵ�������  
      add(tf,"North");  
      add(b1,"Center");  
      add(b2,"East");  
      //��ģ̬�Ի���  
      b1.addActionListener(new ActionListener()  
      {  
          public void actionPerformed(ActionEvent e)  
          {  
              /**              
               * ������new testdialog(),������������ 
               * Ҳ������this��thisָ���ǵ��÷����Ķ��󣬼���new ActionListener()�� 
               */  
              MyDialog dlg=new MyDialog(testDialog.this,"ģʽ�Ի���",true);  
              dlg.setInfo(tf.getText());  
              dlg.setVisible(true);  
              tf.setText(dlg.getInfo());  
                
          }  
      }  
      );  
      //�򿪷�ģ̬�Ի���  
      b2.addActionListener(new ActionListener()  
      {  
          public void actionPerformed(ActionEvent e)  
          {  
              /**              
               * ������new testdialog(),������������ 
               * Ҳ������this��thisָ���ǵ��÷����Ķ��󣬼���new ActionListener()�� 
               */  
              MyDialog dlg=new MyDialog(testDialog.this,"ģʽ�Ի���",false);  
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