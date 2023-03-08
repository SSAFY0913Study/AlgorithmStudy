#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
#include <queue>
using namespace std;

int N, M, K;
int territory[12][12];	//���� �ִ� ����� ��
vector<int> map[12][12];	//������ ũ�� �迭�� �����ִ� ���� 2���� �迭
int addMap[12][12];	//�߰��� ����� ��
int dx[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
int dy[] = { -1, 0, 1, -1, 1, -1, 0, 1 };

class Tree {
public:
	int row;
	int col;
	int age;

	Tree(int row, int col, int age)
		:row(row), col(col), age(age) {}
};

vector<Tree> deathTree;	//���� ������ �迭

void spread(int row, int col) {//���� �����ϴ� �Լ�
	int nx, ny;

	for (int i = 0; i < 8; i++) {
		nx = row + dx[i];
		ny = col + dy[i];

		if (nx > 0 && ny > 0 && nx <= N && ny <= N) {
			map[nx][ny].push_back(1);	// ������ ������ ��ġ�� ũ�� 1�� ������ �߰�
		}
	}
}

void spring() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j].size() > 0) {// �� ĭ�� ������ �ִٸ� ����
				sort(map[i][j].begin(), map[i][j].end());

				for (int k = 0; k < map[i][j].size(); k++) {
					if (territory[i][j] >= map[i][j][k]) {	//�������� ����� �� �� ������ ����� �ش�.
						territory[i][j] -= map[i][j][k];
						map[i][j][k]++;
					}
					else {	//�������� ����� �� �� ���� ���
						for (int h = map[i][j].size() - 1; h >= k; h--) {
							deathTree.push_back(Tree(i, j, map[i][j][h]));	//���� ���� �迭�� ���� �߰�
							map[i][j].pop_back();	//���� ������ �ʿ��� ����
						}
					}
				}
			}
		}
	}
}

void summer() {
	int row, col, age;
	for (int i = 0; i < deathTree.size(); i++) {//���� ���� ���� �迭�� ���鼭 ��� �߰�
		row = deathTree[i].row;
		col = deathTree[i].col;
		age = deathTree[i].age;

		territory[row][col] += (age / 2);	//��� ����
	}

	deathTree.clear();//���� ���� �迭�� ����ش�.
}

void autumn() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j].size() > 0) {//������ ������
				for (int k = 0; k < map[i][j].size(); k++) {
					if (map[i][j][k] % 5 == 0) {
						spread(i, j);	//���ǿ� ������ ���� ���� 
					}
				}
			}
		}
	}
}

void winter() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			territory[i][j] += addMap[i][j];
		}
	}
}

void simul() {
	int year = 0;

	while (year < K) {
		spring();
		summer();
		autumn();
		winter();
		year++;
	}
}

int countTree() {//���� �ִ� ������ ������ ���� �Լ�
	int sum = 0;
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			sum += map[i][j].size();
		}
	}
	return sum;
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M >> K;

	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			cin >> addMap[i][j];
			territory[i][j] = 5;
		}
	}

	int x, y, z;
	for (int i = 0; i < M; i++) {
		cin >> x >> y >> z;
		map[x][y].push_back(z);
	}

	simul();
	cout << countTree();

	return 0;
}