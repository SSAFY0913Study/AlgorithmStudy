#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <math.h>
#include <limits.h>
using namespace std;

int map[501][501];
bool check[501][501];
int dx[] = { -2, -2, -1, -1, 1, 1, 2, 2 };	//나이트가 이동할 수 있는 low좌표 배열
int dy[] = { -1, 1, -2, 2, -2, 2, -1, 1 };	//나이트가 이동할 수 있는 col좌표 배열
pair<int, int> knightPos;	// 나이트의 위치를 저장할 변수
vector<pair<int, int>> enemyPosArr;	// 적들의 위치를 저장할 배열

int n, m, low, col, killCnt = 0;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> n >> m;

	cin >> low >> col;
	knightPos = make_pair(low, col);	//나이트의 위치 입력받기

	for (int i = 0; i < m; i++) {
		cin >> low >> col;
		enemyPosArr.push_back(make_pair(low, col));	// 적의 위치를 배열에 저장한다.
		map[low][col] = -1;	// 적이 위치한 곳을 map에서 -1로 지정한다.
	}
	
	// BFS탐색을 하며 적의 위치에 도달하는데 얼마의 시간이 걸리는지 기록한다.
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
				if (map[nx][ny] == -1) killCnt++;	//다음 위치가 적의 위치라면 적을 죽인 횟수 + 1;
				map[nx][ny] = map[curLow][curCol] + 1;	// 거리 증가 + 1
				if (killCnt == m) {	//적을 모두 죽였을 경우 출력하고 종료
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