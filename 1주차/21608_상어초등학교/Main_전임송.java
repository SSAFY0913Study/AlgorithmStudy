package algorithm.boj;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//1. 모든 칸을 중 좋아하는 학생이 가장 많은 칸들을 찾는다. 배열에 좋아하는 학생 수 저장, max 값 저장
//2. max값을 가진 칸에서 비어있는 칸이 가장 많은 칸들을 찾는다. 동서남북 체크  1, 2 동시 카운트
//3. 이 칸들중 행 번호가 가장 작고, 열 번호가 가장 작은 칸에 학생을 배치한다
class Position {
	int r;
	int c;
	
	public Position(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

class Student {
	int number;
	Position pos;
	Set<Integer> friends;
	
	public Student(int number, Position pos, Set<Integer> friends) {
		this.number = number;
		this.pos = pos;
		this.friends = friends;
	}
}

public class Main_21608_상어초등학교 {
	static final int NUMBER_OF_FRIENDS = 4;
	static final int[] SCOREBOARD = {0, 1, 10, 100, 1000};
	
	static int[] dr = {0, 0, -1, 1};
	static int[] dc = {-1, 1, 0, 0};
	static int N;
	
	public static Position getBestPostion(int[][] classroom, Set<Integer> friends) {
		int bestR = -1;
		int bestC = -1;
		int maxFriends = 0;
		int maxSpace = 0;
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) { //모든 교실 칸 서치
				int thisFriends = 0;
				int thisSpace = 0;
				if (classroom[r][c] > 0) {
					continue;
				}
				for (int i = 0; i < 4; i++) {
					int checkR = r + dr[i];
					int checkC = c + dc[i];
					if (checkR < 0 || checkR >= N || checkC < 0 || checkC >= N) {
						continue;
					}
					if (classroom[checkR][checkC] == 0) { //빈자리
						thisSpace++;
						continue;
					}
					if (friends.contains(classroom[checkR][checkC])) { //사람이 존재하는 자리
						thisFriends++;
					}
				}
				if (thisFriends == 0 && thisSpace == 0 && bestR < 0 && bestC < 0) { //초기화???
					bestR = r;
					bestC = c;
				}
				if (thisFriends > maxFriends) {
					bestR = r;
					bestC = c;
					maxFriends = thisFriends;
					maxSpace = thisSpace;
					continue;
				}
				if (thisFriends == maxFriends && thisSpace > maxSpace) {
					bestR = r;
					bestC = c;
					maxSpace = thisSpace;
					continue;
				}
			}
		}
		return new Position(bestR, bestC);
	}
	
	public static int getScore(int[][] classroom, List<Student> students) {
		int score = 0;
		for (Student student : students) {
			int count = 0;
			for (int i = 0; i < 4; i++) {
				int checkR = student.pos.r + dr[i];
				int checkC = student.pos.c + dc[i];
				if (checkR < 0 || checkR >= N || checkC < 0 || checkC >= N) {
					continue;
				}
				if (student.friends.contains(classroom[checkR][checkC])) {
					count++;
				}
			}
			score += SCOREBOARD[count];
		}
		return score;
	}
	
	public static void printRoom(int[][] room) {
		for (int i = 0; i < room.length; i++) {
			System.out.println(Arrays.toString(room[i]));
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		N = Integer.parseInt(br.readLine());
		int numberOfStudents = N * N;
		int[][] classroom = new int[N][N];
		List<Student> students = new ArrayList<>(N);  
		for (int i = 0; i < numberOfStudents; i++) {
			Set<Integer> friends = new HashSet<>(NUMBER_OF_FRIENDS);
			String[] input = br.readLine().split(" ");
			int studentNumber = Integer.parseInt(input[0]);
			for (int j = 1; j <= NUMBER_OF_FRIENDS; j++) {
				friends.add(Integer.parseInt(input[j]));
			}
			Position bestPostion = getBestPostion(classroom, friends);
			classroom[bestPostion.r][bestPostion.c] = studentNumber;
			students.add(new Student(studentNumber, bestPostion, friends));
		}
		bw.append(Integer.toString(getScore(classroom, students)));
		
		bw.flush();
		br.close();
		bw.close();
	}	
}