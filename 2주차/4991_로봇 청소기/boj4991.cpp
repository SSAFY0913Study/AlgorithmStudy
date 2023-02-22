#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <limits.h>
#include <string.h>
#include <queue>
using namespace std;

char map[21][21];
bool visited[21][21];
int dx[4] = { 0 , 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };

vector<pair<int, int>> dirtyRoom;
pair<int, int > posRobbot; //로봇의 위치
int col, low, minLen = INT_MAX, dirtyCnt = 0;

void cleanRoomBFS(int startLow, int startCol, int clearCnt, int sumLen) {	//clearCnt는 청소한 칸의 수, sumLen은 총 이동거리
	bool curChecked[21][21];

	for (int i = 0; i < low; i++) {
		for (int j = 0; j < col; j++) {
			curChecked[i][j] = false;	//curChecked 초기값 세팅
		}
	}

	if (minLen <= sumLen) return;	// 앞서 구한 결과값보다 현재의 sumLen이 같거나 크다면 리턴
	if (clearCnt == dirtyCnt) {// 모든 칸을 다 청소한 경우
		minLen = min(sumLen, minLen);
		return;
	}

	curChecked[startLow][startCol] = true;

	queue<pair<int, int>> q;

	q.push(make_pair(startLow, startCol));

	int l, c, nx, ny, size, move = 0;
	while (!q.empty()) {
		size = q.size();

		for (int k = 0; k < size; k++) {
			l = q.front().first;
			c = q.front().second;

			q.pop();

			for (int i = 0; i < 4; i++) {
				nx = l + dx[i];
				ny = c + dy[i];

				if (nx >= 0 && nx < low && ny >= 0 && ny < col && !curChecked[nx][ny] && map[nx][ny] != 'x') {
					if (map[nx][ny] == '*') {	// 다음 칸이 더러운 칸이면
						map[nx][ny] = '.';	// 빈칸으로 만들어준다
						cleanRoomBFS(nx, ny, clearCnt + 1, sumLen + move + 1);	// 현재의 위치에서 다시 재귀를 호출한다.
						map[nx][ny] = '*';	//원복
					}
					curChecked[nx][ny] = true;
					q.push(make_pair(nx, ny));
				}
			}
		}
		move++;
	}
}

bool canAllVisit() {	// 모든 더러운 칸을 방문할 수 있는지 검사하는 함수, BFS로 탐색
	int visitDirty = 0;	// 더러운 칸을 방문하나 횟수
	queue<pair<int, int>> q;
	visited[posRobbot.first][posRobbot.second] = true;

	q.push(make_pair(posRobbot.first, posRobbot.second));

	while (!q.empty()) {
		int curX = q.front().first;
		int curY = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int nx = curX + dx[i];
			int ny = curY + dy[i];

			if (nx >= 0 && nx < low && ny >= 0 && ny < col && !visited[nx][ny] && map[nx][ny] != 'x') {
				if (map[nx][ny] == '*') visitDirty++;	// 더러운 칸이면 증가시킴
				if (visitDirty == dirtyCnt) return true;	// 모든 더러운 칸을 방문했으면 true 리턴
				visited[nx][ny] = true;
				q.push(make_pair(nx, ny));
			}
		}
	}
	return false;	// 방문할 수 없는 더러운 칸이 있는 경우 false 리턴
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	string input;
	while (true) {
		minLen = INT_MAX;
		dirtyCnt = 0;

		cin >> col >> low;
		if (col == 0 && low == 0) break;

		for (int i = 0; i < low; i++) {
			cin >> input;
			for (int j = 0; j < col; j++) {
				map[i][j] = input[j];
				if (map[i][j] == '*') dirtyCnt++;
				else if (map[i][j] == 'o') {
					posRobbot.first = i;	//로봇의 시작 low
					posRobbot.second = j;	//로봇의 시작 col
				}
			}
		}

		memset(visited, false, sizeof visited);
		if (!canAllVisit()) {	//방문할 수 없는 더러운 칸이 있으면 -1출력
			cout << -1 << '\n';
		}
		else {
			cleanRoomBFS(posRobbot.first, posRobbot.second, 0, 0);
			cout << minLen << '\n';
		}
	}

	return 0;
}