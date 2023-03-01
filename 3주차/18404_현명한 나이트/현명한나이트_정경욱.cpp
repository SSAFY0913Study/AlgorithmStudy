#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <math.h>
#include <limits.h>
using namespace std;

int map[501][501];
bool check[501][501];
int dx[] = { -2, -2, -1, -1, 1, 1, 2, 2 };	//����Ʈ�� �̵��� �� �ִ� low��ǥ �迭
int dy[] = { -1, 1, -2, 2, -2, 2, -1, 1 };	//����Ʈ�� �̵��� �� �ִ� col��ǥ �迭
pair<int, int> knightPos;	// ����Ʈ�� ��ġ�� ������ ����
vector<pair<int, int>> enemyPosArr;	// ������ ��ġ�� ������ �迭

int n, m, low, col, killCnt = 0;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> n >> m;

	cin >> low >> col;
	knightPos = make_pair(low, col);	//����Ʈ�� ��ġ �Է¹ޱ�

	for (int i = 0; i < m; i++) {
		cin >> low >> col;
		enemyPosArr.push_back(make_pair(low, col));	// ���� ��ġ�� �迭�� �����Ѵ�.
		map[low][col] = -1;	// ���� ��ġ�� ���� map���� -1�� �����Ѵ�.
	}
	
	// BFSŽ���� �ϸ� ���� ��ġ�� �����ϴµ� ���� �ð��� �ɸ����� ����Ѵ�.
	queue<pair<int, int>> q;
	check[knightPos.first][knightPos.second] = true;
	q.push(knightPos);

	while (!q.empty()) {
		int curLow = q.front().first;
		int curCol = q.front().second;
		q.pop();

		for (int i = 0; i < 8; i++) {
			int nx = curLow + dx[i];
			int ny = curCol + dy[i];

			if (nx >= 1 && nx <= n && ny >= 1 && ny <= n && !check[nx][ny]) {
				check[nx][ny] = true;
				if (map[nx][ny] == -1) killCnt++;	//���� ��ġ�� ���� ��ġ��� ���� ���� Ƚ�� + 1;
				map[nx][ny] = map[curLow][curCol] + 1;	// �Ÿ� ���� + 1
				if (killCnt == m) {	//���� ��� �׿��� ��� ����ϰ� ����
					for (int j = 0; j < m; j++) {
						int enemyLow = enemyPosArr[j].first;
						int enemyCol = enemyPosArr[j].second;

						cout << map[enemyLow][enemyCol] << ' ';
					}
					return 0;
				}
				q.push(make_pair(nx, ny));
			}
		}
	}


	return 0;
}