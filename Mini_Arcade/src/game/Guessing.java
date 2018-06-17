package game;


import java.awt.Color;
import java.awt.Font; //폰트에 관한 임포트문
import java.awt.event.ActionEvent; //액션개체와 관련된 임포트문
import java.awt.event.ActionListener; //액션리스너에 대한 임포트문
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton; //JButton 사용을 위한 임포트
import javax.swing.JFrame; //JFrame 사용을 위한 임포트
import javax.swing.JLabel; //JLabel 사용을 위한 임포트
import javax.swing.JOptionPane;
import javax.swing.JPanel; //JPanel 사용을 위한 임포트
import javax.swing.JTextField; //JTextField 사용을 위한 임포트
 
public class Guessing extends JFrame{ 
	
	// DB 커넥션
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
		Connection con = null; // 연결
		PreparedStatement pstmtUpdate = null; // 명령

		try {
			con = guessingConnect();
			String sqlUpdate = "update Game_Score set gameScore=? where gameID=? and UserID=?";
			
			String strUserID = userid;
			System.out.println(strUserID);//test
			pstmtUpdate = con.prepareStatement(sqlUpdate);
			pstmtUpdate.setInt(1, guessingscore);
			pstmtUpdate.setInt(2, 04);
			pstmtUpdate.setString(3, strUserID);

			int r = pstmtUpdate.executeUpdate(); // 실행 -> 저장
			if (r > 0) {
				System.out.println("숫자 맞추기 게임 SCORE 추가 성공");
				
			} else {
				System.out.println("실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Connection guessingConnect() {
		Connection con = null;

		try {
			Class.forName(driver); // 1. 드라이버 로딩
			con = DriverManager.getConnection(url, user, pwd); // 2. 드라이버 연결
		}
		catch (Exception e) {
			e.printStackTrace();
			}
			return con;
			}
	




	
/* Guessing 클래스 - JFrame 클래스를 상속 받았기에 현 Guessing 클래스 내에 있는 모든 클래스 멤버들은 JFrame 클래의 멤버들을 사용할수 있다. */
    JPanel main_panel; // 숫자맞추기 게임의 컴포넌트들을 하나의 패널에서 다루기 위해 메인 패널 지정
    JLabel hint_label, try_label; // 힌트 라벨, 시도횟수 라벨
    JLabel result_label, clear_label;
    JButton bt, clear_bt; // OK버튼, 초기화 버튼
    JTextField input_field; // 값 입력 텍스트필드
    
    int random_value = (int)(Math.random() * 100 + 1); // 난수값의 범위를 1~100 으로 지정
    int try_count = 0, game_count = 1; // 시도횟수 카운트 변수, 게임 횟수에 따른 카운트 변수
    int user_number; // 사용자가 텍스트 필드에 수를 입력했을?의 값을 저장해주는 변수
    
    public Guessing(String id)
    {
    	userid=id;
        setTitle("숫자 맞추기 게임"); //타이틀명 지정
        setSize(500,450); // 프레임 사이즈를 320,250 픽셀로 지정
        this.setLocationRelativeTo(null);
        setUndecorated(true); //상단바 없애기
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //프레임 우측 상단 에 X 버튼의 기능을 정의
        
        main_panel = new JPanel(); // 메인 패널 오브젝트 생성
        main_panel.setLayout(null);
 /* 메인 패널의 레이아웃을 null 지정하여 메인패널에서 다루는 컴포넌트들의 좌표값을 개발자 직접 지정 */
        
        bt = new JButton("OK"); // 버튼 오브젝트 생성
        bt.setBackground(Color.ORANGE);
        clear_bt = new JButton("초기화"); // 초기화 버튼 오브젝트 생성
        
        hint_label = new JLabel("힌트: "); // 힌트 라벨 오브젝트 생성
        hint_label.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
        try_label = new JLabel("시도횟수: 0번"); // 시도횟수 라벨 오브젝트 생성
        try_label.setFont(new Font("맑은 고딕", Font.BOLD, 30)); // 시도횟수의 폰트및 글씨굵기, 크기 지정
        result_label = new JLabel("정답입니다");
        result_label.setFont(new Font("맑은 고딕", Font.BOLD, 60));
        //clear_label = new JLabel("초기화 버튼을 눌러주세요");
        //clear_label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        
        input_field = new JTextField(30); // 값 입력 텍스트필드 오브젝트 생성및 필드 크기 30지정
        input_field.setFont(new Font("Dialog", Font.BOLD, 30));
        input_field.setHorizontalAlignment(JTextField.CENTER);
        
        try_label.setBounds(15,15,200,30); //시도 횟수 위치 지정
        hint_label.setBounds(90,150,500,30); //힌트 라벨 위치 지정
        input_field.setBounds(90,190,300,50); //값 입력 텍스트필드 위치 지정
        bt.setBounds(165,250,150,40); //버튼 위치 지정
        
        main_panel.add(result_label); 
        //main_panel.add(clear_label);
        main_panel.add(try_label); // 메인 패널에 시도횟수(컴포넌트) 추가
        main_panel.add(hint_label); // 메인 패널에 힌트(컴포넌트) 추가
        main_panel.add(input_field); // 메인 패널에 값 입력 텍스트필드(컴포넌트) 추가
        main_panel.add(bt); // 메인 패널에 OK버튼(컴포넌트) 추가
        //main_panel.add(clear_bt);
        
        add(main_panel); // 프레임에 메인 패널 추가 - 이 과정으로 메인 패널내에 있는 모든 컴포넌트 들을 출력 시킬수 있다.
        
        bt.addActionListener(new MyActionListener()); // OK버튼을 눌렀을때  MyActionListener에서 액션 처리
        //clear_bt.addActionListener(new MyActionListener()); 
        // 초기화 버튼을 눌렀을때  MyActionListener에서 액션 처리
        
        System.out.println(game_count + "번째 게임에서 정해진 난수값: " + random_value); //미리 정해진 난수값 확인
        
        setVisible(true); //프레임의 보기를 참으로 설정 - 프레임을 볼수 있다.
        /*
        while(true) 
        {
            if(clear_label.getText().equals("초기화 버튼을 눌러주세요")) {
                clear_label.setText("");
            } else clear_label.setText("초기화 버튼을 눌러주세요");
            
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
    	/* MyActionListener 클래스 - ActionListener 인터페이스 구현 , Guessing클래스 내에 또 하나의 클래스 이기 때문에 이런걸 내부 클래스 라고 하는데 장점으로는 바깥 Guessing클래스의 멤버들을 함께 공유 하며 쓸수 있다는 것이다. */
        public void actionPerformed(ActionEvent e) 
        {
        	/* ActionListener 인터페이스 구현을 했기 때문에 actionPerformed 메소드 정의 , 이벤트매개변수로 e로 지정 */
            if(e.getSource() == bt) 
            { //버튼이 눌렀을때에 밑 코드 동작
                if( input_field.getText().equals(Integer.toString(random_value) )) { 
                	/* 사용자가 입력한 값과 난수값이 일치 했을때 밑 코드 실행 */
                    result_label.setVisible(true);
                    //clear_label.setVisible(true);
                    try_label.setVisible(false);
                    hint_label.setVisible(false);
                    input_field.setVisible(false);
                    bt.setVisible(false);
                    Guessing.setgussingscore(try_count);//점수저장 
                    System.out.println("점수: "+ guessingscore);
                    result_label.setBounds(85,100,500,100);
                    
                    JOptionPane.showMessageDialog(null, "GAME OVER, your score "+guessingscore);
                    dispose();
                    //clear_label.setBounds(155,190,300,100);    
                    //clear_bt.setBounds(165,255,150,50); //초기화 버튼 위치 지정        
                } else { //사용자가 입력한 값과 난수값이 일치 하지 않았?때
                    user_number = Integer.parseInt(input_field.getText());
/* user_number 변수에 사용자가 입력한 값을 정수형(int)으로 저장 - 추후에 난수 값과의 비교를 위해 사용됨 */
                    if(user_number < random_value) { // 사용자 입력 값이 난수값 보다 클때라는 조건
                        try_label.setText("시도횟수: " + Integer.toString(++try_count) + "번"); //시도횟수 전위형으로 증감
                        hint_label.setText("힌트: 높습니다  ▲");
/* 힌트 라벨이 사용자 입력 수 보다 난수값이 높을 때이기 때문에 높다는 문구를 힌트 라발에 추가 */
                    } else if(user_number > random_value) { // 사용자 입력 값이 난수값 보다 낮을때라는 조건
                        try_label.setText("시도횟수: " + Integer.toString(++try_count) + "번"); //시도횟수 전위형으로 증감
                        hint_label.setText("힌트: 낮습니다 ▼"); 
/* 힌트 라벨이 사용자 입력 수 보다 난수값이  낮을 때이기 때문에 낮다는는 문구를 힌트 라벨이 추가 */
                    }
                }
            } 
            /*
            else if (e.getSource() == clear_bt)
            { // 초기화 버튼을 눌렀을 때에 밑 코드 처리
                result_label.setVisible(true);
                clear_label.setVisible(true);
                try_label.setVisible(true);
                hint_label.setVisible(true);
                input_field.setVisible(true);
                bt.setVisible(true);
                
                result_label.setBounds(60,53,0,0);
                clear_label.setBounds(57,142,0,0);
                clear_bt.setBounds(165,230,0,0);
                
                
                
                try_count = 0; // 초기화 버튼을 눌렀으므로 시도횟수 를 0으로 초기화 해준다.
                try_label.setText("시도횟수: " + try_count + "번"); // 시도횟수를 출력
                hint_label.setText("힌트: "); // 힌트 텍스트 초기화
                input_field.setText("");
                random_value = (int)(Math.random() * 100 + 1); 
                // 초기화 버튼이므로 새로운 난수값의 범위를 1~100 으로 재지정 
                System.out.println((++game_count) + "번째 게임에서 정해진 난수값: " + random_value); 
                //초기화로 정해진 난수값 확인 , game_count - 게임횟수 전위형 증감 
            }
            */
        }
        
    }
    /*
   
    public static void main(String[] args) { //메인 메소드 정의
        new Guessing(userid); // Game클래스의 디폴드 생성자인 Game()를 자동적으로 위에서 처리 하게 되는원리.
    }*/
}


