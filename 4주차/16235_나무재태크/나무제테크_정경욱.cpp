#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
#include <queue>
using namespace std;

int N, M, K;
int territory[12][12];	//땅에 있는 양분의 값
vector<int> map[12][12];	//나무의 크기 배열을 갖고있는 벡터 2차원 배열
int addMap[12][12];	//추가할 양분의 값
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

vector<Tree> deathTree;	//죽은 나무의 배열

void spread(int row, int col) {//나무 번식하는 함수
	int nx, ny;

	for (int i = 0; i < 8; i++) {
		nx = row + dx[i];
		ny = col + dy[i];

		if (nx > 0 && ny > 0 && nx <= N && ny <= N) {
			map[nx][ny].push_back(1);	// 나무가 퍼지는 위치에 크기 1의 나무를 추가
		}
	}
}

void spring() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j].size() > 0) {// 그 칸에 나무가 있다면 정렬
				sort(map[i][j].begin(), map[i][j].end());

				for (int k = 0; k < map[i][j].size(); k++) {
					if (territory[i][j] >= map[i][j][k]) {	//나무에게 양분을 줄 수 있으면 양분을 준다.
						territory[i][j] -= map[i][j][k];
						map[i][j][k]++;
					}
					else {	//나무에게 양분을 줄 수 없는 경우
						for (int h = map[i][j].size() - 1; h >= k; h--) {
							deathTree.push_back(Tree(i, j, map[i][j][h]));	//죽은 나무 배열에 나무 추가
							map[i][j].pop_back();	//죽은 나무는 맵에서 제거
						}
					}
				}
			}
		}
	}
}

void summer() {
	int row, col, age;
	for (int i = 0; i < deathTree.size(); i++) {//봄에 죽은 나무 배열을 돌면서 양분 추가
		row = deathTree[i].row;
		col = deathTree[i].col;
		age = deathTree[i].age;

		territory[row][col] += (age / 2);	//양분 증가
	}

	deathTree.clear();//죽은 나무 배열을 비워준다.
}

void autumn() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j].size() > 0) {//나무가 있으면
				for (int k = 0; k < map[i][j].size(); k++) {
					if (map[i][j][k] % 5 == 0) {
						spread(i, j);	//조건에 맞으면 나무 번식 
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

int countTree() {//남아 있는 나무의 개수를 세는 함수
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