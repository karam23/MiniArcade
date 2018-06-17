/*
 * Data Transfer Object 입니다. 여기는 DB와 연결된 데이터만 따로 빼 놓았습니다. 
 * 객체만 있다고 보시면 되어요. 필요없는 객체들은 주석처리 했습니다.
 * by 라운 
 */

package member;

public class MemberDTO {

	private String id;
	private String pwd;
	private String name;
	private String gender;
	private String email;
	private String score;
	//private int scoreRps, scoreNum, scoreSnake, score4;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	/*
	//RPS
	public int getScoreRps() {
		return scoreRps;
	}
	public void setScoreRps(int scoreRps) {
		this.scoreRps = scoreRps;
	}
	//Num
	public int getScoreNum() {
		return scoreNum;
	}
	public void setScoreNum(int scoreNum) {
		this.scoreNum = scoreNum;
	}
	//Snake
	public int getScoreSnake() {
		return scoreSnake;
	}
	public void setScoreSnake(int scoreSnake) {
		this.scoreSnake = scoreSnake;
	}

	public int getScore4() {
		return score4;
	}
	public void setScore4(int score4) {
		this.score4 = score4;
	}
	 */
	// DTO 객체 확인
	/*
	 * public String toString() { return "MemberDTO [id=" + id + ", pwd=" + pwd +
	 * ", name=" + name + ", gender=" + gender + ", email=" + email + ", score=" +
	 * score + "]"; }
	 */
}