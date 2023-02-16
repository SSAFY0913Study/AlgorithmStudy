import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static List<Student> students;
    static int[][] seats;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int size;

    public static void main(String[] args) throws IOException {
        size = Integer.parseInt(br.readLine());
        students = new ArrayList<>();
        seats = new int[size][size];
        addStudents();
        setPositions();
        System.out.println(sumScore());
    }

    private static void addStudents() throws IOException {
        for (int i = 0; i < size * size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int studentNumber = Integer.parseInt(st.nextToken());
            Student student = new Student(studentNumber);

            for (int j = 0; j < 4; j++) {
                student.addFriend(Integer.parseInt(st.nextToken()));
            }

            students.add(student);
        }
    }

    private static void setPositions() {
        for (Student student : students) {
            setPosition(student);
        }
    }

    private static void setPosition(Student student) {
        Queue<Position> positions = new PriorityQueue<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                int friendCnt = 0;
                int emptyCnt = 0;

                // 자리 비어있는 것만 진행
                if (seats[x][y] != 0) {
                    continue;
                }

                // 4 방향 체크
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (0 <= nx && nx < size && 0 <= ny && ny < size) {
                        // 주변 빈자리 개수 체크
                        if (seats[nx][ny] == 0) {
                            emptyCnt++;
                            continue;
                        }

                        // 좋아하는 학생 인접 개수 체크
                        if (student.isFriend(seats[nx][ny])) {
                            friendCnt++;
                        }
                    }
                }

                Position position = new Position(x, y, friendCnt, emptyCnt);
                positions.add(position);
            }
        }

        Position position = positions.poll();
        seats[position.x][position.y] = student.number;
    }

    private static int sumScore() {
        Collections.sort(students);
        int sum = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int friendCnt = 0;

                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (0 <= nx && nx < size && 0 <= ny && ny < size) {
                        // 좋아하는 학생 인접 개수 체크
                        if (students.get(seats[x][y] - 1).isFriend(seats[nx][ny])) {
                            friendCnt++;
                        }
                    }
                }

                if (friendCnt != 0) {
                    sum += Math.pow(10, friendCnt - 1);
                }
            }
        }
        return sum;
    }
}

class Student implements Comparable<Student> {
    int number;
    List<Integer> friends = new ArrayList<>();

    public Student(int number) {
        this.number = number;
    }

    public void addFriend(int number) {
        friends.add(number);
    }

    public boolean isFriend(int number) {
        return friends.contains(number);
    }

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", friends=" + friends +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.number - o.number;
    }
}

class Position implements Comparable<Position> {
    int x;
    int y;
    int friendCnt;
    int emptyCnt;

    public Position(int x, int y, int friendCnt, int emptyCnt) {
        this.x = x;
        this.y = y;
        this.friendCnt = friendCnt;
        this.emptyCnt = emptyCnt;
    }

    // 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
    // 인접한 칸 중에서 비어있는 칸이 가장 많은 칸으로 자리를 정한다.
    // 행의 번호가 가장 작은 칸으로, 그러한 칸도 여러 개이면 열의 번호가 가장 작은 칸으로 자리를 정한다.
    @Override
    public int compareTo(Position o) {
        if (this.friendCnt != o.friendCnt) {
            return o.friendCnt - this.friendCnt;
        }

        if (this.emptyCnt != o.emptyCnt) {
            return o.emptyCnt - this.emptyCnt;
        }

        if (this.x != o.x) {
            return this.x - o.x;
        }

        return this.y - o.y;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", friendCnt=" + friendCnt + ", emptyCnt=" + emptyCnt + "]";
    }
}
