package game;


import java.awt.Color;
import java.awt.Font; //��Ʈ�� ���� ����Ʈ��
import java.awt.event.ActionEvent; //�׼ǰ�ü�� ���õ� ����Ʈ��
import java.awt.event.ActionListener; //�׼Ǹ����ʿ� ���� ����Ʈ��
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton; //JButton ����� ���� ����Ʈ
import javax.swing.JFrame; //JFrame ����� ���� ����Ʈ
import javax.swing.JLabel; //JLabel ����� ���� ����Ʈ
import javax.swing.JOptionPane;
import javax.swing.JPanel; //JPanel ����� ���� ����Ʈ
import javax.swing.JTextField; //JTextField ����� ���� ����Ʈ
 
public class Guessing extends JFrame{ 
	
	// DB Ŀ�ؼ�
	private static String userid;
	static int guessingscore;

	private static String driver = "org.gjt.mm.mysql.Driver";
	private static String url = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";
	private static String user = "useruser";
	private static String pwd = "123useruser";

	public static void setgussingscore(int try_count) {
		guessingscore=100-try_count*10;
		guessingUpadate();
	}
	
	private static void guessingUpadate() {
		Connection con = null; // ����
		PreparedStatement pstmtUpdate = null; // ���

		try {
			con = guessingConnect();
			String sqlUpdate = "update Game_Score set gameScore=? where gameID=? and UserID=?";
			
			String strUserID = userid;
			System.out.println(strUserID);//test
			pstmtUpdate = con.prepareStatement(sqlUpdate);
			pstmtUpdate.setInt(1, guessingscore);
			pstmtUpdate.setInt(2, 04);
			pstmtUpdate.setString(3, strUserID);

			int r = pstmtUpdate.executeUpdate(); // ���� -> ����
			if (r > 0) {
				System.out.println("���� ���߱� ���� SCORE �߰� ����");
				
			} else {
				System.out.println("����");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Connection guessingConnect() {
		Connection con = null;

		try {
			Class.forName(driver); // 1. ����̹� �ε�
			con = DriverManager.getConnection(url, user, pwd); // 2. ����̹� ����
		}
		catch (Exception e) {
			e.printStackTrace();
			}
			return con;
			}
	




	
/* Guessing Ŭ���� - JFrame Ŭ������ ��� �޾ұ⿡ �� Guessing Ŭ���� ���� �ִ� ��� Ŭ���� ������� JFrame Ŭ���� ������� ����Ҽ� �ִ�. */
    JPanel main_panel; // ���ڸ��߱� ������ ������Ʈ���� �ϳ��� �гο��� �ٷ�� ���� ���� �г� ����
    JLabel hint_label, try_label; // ��Ʈ ��, �õ�Ƚ�� ��
    JLabel result_label, clear_label;
    JButton bt, clear_bt; // OK��ư, �ʱ�ȭ ��ư
    JTextField input_field; // �� �Է� �ؽ�Ʈ�ʵ�
    
    int random_value = (int)(Math.random() * 100 + 1); // �������� ������ 1~100 ���� ����
    int try_count = 0, game_count = 1; // �õ�Ƚ�� ī��Ʈ ����, ���� Ƚ���� ���� ī��Ʈ ����
    int user_number; // ����ڰ� �ؽ�Ʈ �ʵ忡 ���� �Է�����?�� ���� �������ִ� ����
    
    public Guessing(String id)
    {
    	userid=id;
        setTitle("���� ���߱� ����"); //Ÿ��Ʋ�� ����
        setSize(500,450); // ������ ����� 320,250 �ȼ��� ����
        this.setLocationRelativeTo(null);
        setUndecorated(true); //��ܹ� ���ֱ�
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //������ ���� ��� �� X ��ư�� ����� ����
        
        main_panel = new JPanel(); // ���� �г� ������Ʈ ����
        main_panel.setLayout(null);
 /* ���� �г��� ���̾ƿ��� null �����Ͽ� �����гο��� �ٷ�� ������Ʈ���� ��ǥ���� ������ ���� ���� */
        
        bt = new JButton("OK"); // ��ư ������Ʈ ����
        bt.setBackground(Color.ORANGE);
        clear_bt = new JButton("�ʱ�ȭ"); // �ʱ�ȭ ��ư ������Ʈ ����
        
        hint_label = new JLabel("��Ʈ: "); // ��Ʈ �� ������Ʈ ����
        hint_label.setFont(new Font("���� ���", Font.PLAIN, 25));
        try_label = new JLabel("�õ�Ƚ��: 0��"); // �õ�Ƚ�� �� ������Ʈ ����
        try_label.setFont(new Font("���� ���", Font.BOLD, 30)); // �õ�Ƚ���� ��Ʈ�� �۾�����, ũ�� ����
        result_label = new JLabel("�����Դϴ�");
        result_label.setFont(new Font("���� ���", Font.BOLD, 60));
        //clear_label = new JLabel("�ʱ�ȭ ��ư�� �����ּ���");
        //clear_label.setFont(new Font("���� ���", Font.PLAIN, 15));
        
        input_field = new JTextField(30); // �� �Է� �ؽ�Ʈ�ʵ� ������Ʈ ������ �ʵ� ũ�� 30����
        input_field.setFont(new Font("Dialog", Font.BOLD, 30));
        input_field.setHorizontalAlignment(JTextField.CENTER);
        
        try_label.setBounds(15,15,200,30); //�õ� Ƚ�� ��ġ ����
        hint_label.setBounds(90,150,500,30); //��Ʈ �� ��ġ ����
        input_field.setBounds(90,190,300,50); //�� �Է� �ؽ�Ʈ�ʵ� ��ġ ����
        bt.setBounds(165,250,150,40); //��ư ��ġ ����
        
        main_panel.add(result_label); 
        //main_panel.add(clear_label);
        main_panel.add(try_label); // ���� �гο� �õ�Ƚ��(������Ʈ) �߰�
        main_panel.add(hint_label); // ���� �гο� ��Ʈ(������Ʈ) �߰�
        main_panel.add(input_field); // ���� �гο� �� �Է� �ؽ�Ʈ�ʵ�(������Ʈ) �߰�
        main_panel.add(bt); // ���� �гο� OK��ư(������Ʈ) �߰�
        //main_panel.add(clear_bt);
        
        add(main_panel); // �����ӿ� ���� �г� �߰� - �� �������� ���� �гγ��� �ִ� ��� ������Ʈ ���� ��� ��ų�� �ִ�.
        
        bt.addActionListener(new MyActionListener()); // OK��ư�� ��������  MyActionListener���� �׼� ó��
        //clear_bt.addActionListener(new MyActionListener()); 
        // �ʱ�ȭ ��ư�� ��������  MyActionListener���� �׼� ó��
        
        System.out.println(game_count + "��° ���ӿ��� ������ ������: " + random_value); //�̸� ������ ������ Ȯ��
        
        setVisible(true); //�������� ���⸦ ������ ���� - �������� ���� �ִ�.
        /*
        while(true) 
        {
            if(clear_label.getText().equals("�ʱ�ȭ ��ư�� �����ּ���")) {
                clear_label.setText("");
            } else clear_label.setText("�ʱ�ȭ ��ư�� �����ּ���");
            
            try 
            {
                Thread.sleep(600);
            } catch(Exception e1) {
                System.exit(0);
            }
        }*/
    }
    
    class MyActionListener implements ActionListener
    { 
    	/* MyActionListener Ŭ���� - ActionListener �������̽� ���� , GuessingŬ���� ���� �� �ϳ��� Ŭ���� �̱� ������ �̷��� ���� Ŭ���� ��� �ϴµ� �������δ� �ٱ� GuessingŬ������ ������� �Բ� ���� �ϸ� ���� �ִٴ� ���̴�. */
        public void actionPerformed(ActionEvent e) 
        {
        	/* ActionListener �������̽� ������ �߱� ������ actionPerformed �޼ҵ� ���� , �̺�Ʈ�Ű������� e�� ���� */
            if(e.getSource() == bt) 
            { //��ư�� ���������� �� �ڵ� ����
                if( input_field.getText().equals(Integer.toString(random_value) )) { 
                	/* ����ڰ� �Է��� ���� �������� ��ġ ������ �� �ڵ� ���� */
                    result_label.setVisible(true);
                    //clear_label.setVisible(true);
                    try_label.setVisible(false);
                    hint_label.setVisible(false);
                    input_field.setVisible(false);
                    bt.setVisible(false);
                    Guessing.setgussingscore(try_count);//�������� 
                    System.out.println("����: "+ guessingscore);
                    result_label.setBounds(85,100,500,100);
                    
                    JOptionPane.showMessageDialog(null, "GAME OVER, your score "+guessingscore);
                    dispose();
                    //clear_label.setBounds(155,190,300,100);    
                    //clear_bt.setBounds(165,255,150,50); //�ʱ�ȭ ��ư ��ġ ����        
                } else { //����ڰ� �Է��� ���� �������� ��ġ ���� �ʾ�?��
                    user_number = Integer.parseInt(input_field.getText());
/* user_number ������ ����ڰ� �Է��� ���� ������(int)���� ���� - ���Ŀ� ���� ������ �񱳸� ���� ���� */
                    if(user_number < random_value) { // ����� �Է� ���� ������ ���� Ŭ����� ����
                        try_label.setText("�õ�Ƚ��: " + Integer.toString(++try_count) + "��"); //�õ�Ƚ�� ���������� ����
                        hint_label.setText("��Ʈ: �����ϴ�  ��");
/* ��Ʈ ���� ����� �Է� �� ���� �������� ���� ���̱� ������ ���ٴ� ������ ��Ʈ ��߿� �߰� */
                    } else if(user_number > random_value) { // ����� �Է� ���� ������ ���� ��������� ����
                        try_label.setText("�õ�Ƚ��: " + Integer.toString(++try_count) + "��"); //�õ�Ƚ�� ���������� ����
                        hint_label.setText("��Ʈ: �����ϴ� ��"); 
/* ��Ʈ ���� ����� �Է� �� ���� ��������  ���� ���̱� ������ ���ٴ´� ������ ��Ʈ ���� �߰� */
                    }
                }
            } 
            /*
            else if (e.getSource() == clear_bt)
            { // �ʱ�ȭ ��ư�� ������ ���� �� �ڵ� ó��
                result_label.setVisible(true);
                clear_label.setVisible(true);
                try_label.setVisible(true);
                hint_label.setVisible(true);
                input_field.setVisible(true);
                bt.setVisible(true);
                
                result_label.setBounds(60,53,0,0);
                clear_label.setBounds(57,142,0,0);
                clear_bt.setBounds(165,230,0,0);
                
                
                
                try_count = 0; // �ʱ�ȭ ��ư�� �������Ƿ� �õ�Ƚ�� �� 0���� �ʱ�ȭ ���ش�.
                try_label.setText("�õ�Ƚ��: " + try_count + "��"); // �õ�Ƚ���� ���
                hint_label.setText("��Ʈ: "); // ��Ʈ �ؽ�Ʈ �ʱ�ȭ
                input_field.setText("");
                random_value = (int)(Math.random() * 100 + 1); 
                // �ʱ�ȭ ��ư�̹Ƿ� ���ο� �������� ������ 1~100 ���� ������ 
                System.out.println((++game_count) + "��° ���ӿ��� ������ ������: " + random_value); 
                //�ʱ�ȭ�� ������ ������ Ȯ�� , game_count - ����Ƚ�� ������ ���� 
            }
            */
        }
        
    }
    /*
   
    public static void main(String[] args) { //���� �޼ҵ� ����
        new Guessing(userid); // GameŬ������ ������ �������� Game()�� �ڵ������� ������ ó�� �ϰ� �Ǵ¿���.
    }*/
}


