#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <limits.h>
using namespace std;

int map[51][51];
int changedMap[51][51];
int N, M, result = INT_MAX, cntZero = 0;
vector<pair<int, int>> virusArr;	// 전체 바이러스 좌표배열
pair<int, int> selected[11];	// 선택한 바이러스 좌표들의 배열
queue<pair<int, int>> q;
int dx[4] = { 0, 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };

void simul() {
	for (int i = 0; i < M; i++) {
		q.push(selected[i]);	// 큐에 선택된 원소들 push
		changedMap[selected[i].first][selected[i].second] = -1;	//활성바이러스는 -1로 설정
	}

	int curX; // 큐 front의 low값
	int curY; // 큐 front의 col값
	int nx;	// 다음 low값
	int ny;	// 다음 col값
	int cntErase = 0;

	while (!q.empty()) {
		curX = q.front().first;
		curY = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			nx = curX + dx[i];
			ny = curY + dy[i];

			if (nx >= 0 && nx < N && ny >= 0 && ny < N) {// 범위 체크
				if (changedMap[nx][ny] == 0 || changedMap[nx][ny] == -2) {	// 0은 빈칸이고 -2는 비활성 바이러스
					if (changedMap[nx][ny] == 0) cntErase++;	// 대상이 빈칸이었을 경우 바이러스가 퍼지며 삭제한 빈칸의 개수를 1증가시킨다.
					if (changedMap[curX][curY] == -1)	changedMap[nx][ny] = 1;	//현재의 위치가 활성바이러스, 즉 시작점이었다면 다음 이동할 위치에 시간을 1로 세팅
					else changedMap[nx][ny] = changedMap[curX][curY] + 1; //아니라면 현재 시간에 + 1;
					q.push(make_pair(nx, ny));	// 큐에 활성화된 바이러스위 위치를 넣는다.
					if (cntErase == cntZero) {	//빈칸을 다 지웠을 경우
						result = min(result, changedMap[nx][ny]);
						return;	//BFS이기 때문에 처음 나온 결과값이 가장 짧은 시간이므로 return
					}
				}
			}
		}
	}
}

void chooseVirus(int cnt, int start) {
	if (cnt == M) {	// M개의 바이러스를 조합의 방식으로 선택했을 때
		while (!q.empty()) q.pop();	// 다음 작업을 위해 큐에 남아있는 원소들 삭제
		simul();
		for (int i = 0; i < N; i++) {	// 변경했던 맵을 원래대로 복원한다.
			for (int j = 0; j < N; j++) {
				changedMap[i][j] = map[i][j];
			}
		}
		return;
	}
	for (int i = start; i < virusArr.size(); i++) {
		selected[cnt] = virusArr[i];
		chooseVirus(cnt + 1, i + 1);
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> M;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> map[i][j];
			if (map[i][j] == 2) {	// 바이러스면 -2로 맵에 저장한다.
				virusArr.push_back(make_pair(i, j));	// 전체 바이러스의 위치를 저장하는 배열에 바이러스의 위치를 추가한다.
				map[i][j] = -2;
			}
			else if (map[i][j] == 0) cntZero++;	// 바이러스가 없는 빈칸의 개수를 증가시킨다.
			changedMap[i][j] = map[i][j];
		}
	}

	if (cntZero == 0) {
		cout << 0;
		return 0;
	}
	chooseVirus(0, 0);

	if (result != INT_MAX) cout << result;
	else cout << -1;

	return 0;
}