#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int N, M, K, result = 0;
int map[21][21];
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
bool visited[21][21];

class Dice {
public:
	int top;
	int bottom;
	int front;
	int back;
	int left;
	int right;

	Dice(int t, int b, int f, int back, int l, int r)
		:top(t), bottom(b), front(f), back(back), left(l), right(r) {}
};

void turnEast(Dice& d);
void turnWest(Dice& d);
void turnSouth(Dice& d);
void turnNorth(Dice& d);
void simul();

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M >> K;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];
		}
	}

	simul();

	cout << result;

	return 0;
}
int BFS(int row, int col) {
	queue<pair<int, int>> q;
	q.push(make_pair(row, col));

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			visited[i][j] = false;
		}
	}
	visited[row][col] = true;

	int cnt = 1;
	int score = map[row][col];

	while (!q.empty()) {
		int curRow = q.front().first;
		int curCol = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int nx = curRow + dx[i];
			int ny = curCol + dy[i];

			if (nx >= 0 && nx < N && ny >= 0 && ny < M && map[nx][ny] == score && !visited[nx][ny]) {
				visited[nx][ny] = true;
				cnt++;
				q.push(make_pair(nx, ny));
			}
		}
	}

	return score * cnt;
}

void simul() {
	Dice dice = Dice(1, 6, 2, 5, 4, 3);	//�ֻ����� ó�� ����
	int cnt = 0;
	int dir = 0;	// ���� ������ ����, ���� 0, ����: 1, ����: 2, ����, 3
	int row = 0;
	int col = 0;
	int sum = 0;	//��� sum

	while (cnt < K) {

		if (row + dx[dir] < 0 || row + dx[dir] >= N || col + dy[dir] < 0 || col + dy[dir] >= M) {	// �ֻ����� ���� �������� ������ �� ������ ���� �ٲٱ�
			if (dir < 2) dir += 2;	//�ֻ����� ���� �ٲپ��ֱ�
			else dir -= 2;	//�ֻ����� ���� �ٲپ��ֱ�
		}

		if (dir == 0) turnEast(dice);
		else if (dir == 1) turnSouth(dice);
		else if (dir == 2) turnWest(dice);
		else turnNorth(dice);

		row += dx[dir];	//������ġ �ٲٱ�
		col += dy[dir];// ������ġ �ٲٱ�

		sum += BFS(row, col);

		if (dice.bottom > map[row][col]) dir = (dir + 1) % 4;	// ������ �ð�������� 90�� ȸ��
		else if (dice.bottom < map[row][col]) {	//������ �ݽð�������� 90�� ȸ��
			if (dir == 0) dir = 3;
			else dir -= 1;
		}

		cnt++;
	}

	result = sum; //����� ����
}


void turnEast(Dice& d) {
	int top = d.top;
	int bottom = d.bottom;
	int front = d.front;
	int back = d.back;
	int left = d.left;
	int right = d.right;

	d.top = left;
	d.bottom = right;
	d.left = bottom;
	d.right = top;
}

void turnWest(Dice& d) {
	int top = d.top;
	int bottom = d.bottom;
	int front = d.front;
	int back = d.back;
	int left = d.left;
	int right = d.right;

	d.top = right;
	d.bottom = left;
	d.left = top;
	d.right = bottom;
}

void turnNorth(Dice& d) {
	int top = d.top;
	int bottom = d.bottom;
	int front = d.front;
	int back = d.back;
	int left = d.left;
	int right = d.right;

	d.top = back;
	d.bottom = front;
	d.front = top;
	d.back = bottom;
}

void turnSouth(Dice& d) {
	int top = d.top;
	int bottom = d.bottom;
	int front = d.front;
	int back = d.back;
	int left = d.left;
	int right = d.right;

	d.top = front;
	d.bottom = back;
	d.front = bottom;
	d.back = top;
}

