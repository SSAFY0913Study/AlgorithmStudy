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
pair<int, int > posRobbot; //�κ��� ��ġ
int col, low, minLen = INT_MAX, dirtyCnt = 0;

void cleanRoomBFS(int startLow, int startCol, int clearCnt, int sumLen) {	//clearCnt�� û���� ĭ�� ��, sumLen�� �� �̵��Ÿ�
	bool curChecked[21][21];

	for (int i = 0; i < low; i++) {
		for (int j = 0; j < col; j++) {
			curChecked[i][j] = false;	//curChecked �ʱⰪ ����
		}
	}

	if (minLen <= sumLen) return;	// �ռ� ���� ��������� ������ sumLen�� ���ų� ũ�ٸ� ����
	if (clearCnt == dirtyCnt) {// ��� ĭ�� �� û���� ���
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
					if (map[nx][ny] == '*') {	// ���� ĭ�� ������ ĭ�̸�
						map[nx][ny] = '.';	// ��ĭ���� ������ش�
						cleanRoomBFS(nx, ny, clearCnt + 1, sumLen + move + 1);	// ������ ��ġ���� �ٽ� ��͸� ȣ���Ѵ�.
						map[nx][ny] = '*';	//����
					}
					curChecked[nx][ny] = true;
					q.push(make_pair(nx, ny));
				}
			}
		}
		move++;
	}
}

bool canAllVisit() {	// ��� ������ ĭ�� �湮�� �� �ִ��� �˻��ϴ� �Լ�, BFS�� Ž��
	int visitDirty = 0;	// ������ ĭ�� �湮�ϳ� Ƚ��
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
				if (map[nx][ny] == '*') visitDirty++;	// ������ ĭ�̸� ������Ŵ
				if (visitDirty == dirtyCnt) return true;	// ��� ������ ĭ�� �湮������ true ����
				visited[nx][ny] = true;
				q.push(make_pair(nx, ny));
			}
		}
	}
	return false;	// �湮�� �� ���� ������ ĭ�� �ִ� ��� false ����
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
					posRobbot.first = i;	//�κ��� ���� low
					posRobbot.second = j;	//�κ��� ���� col
				}
			}
		}

		memset(visited, false, sizeof visited);
		if (!canAllVisit()) {	//�湮�� �� ���� ������ ĭ�� ������ -1���
			cout << -1 << '\n';
		}
		else {
			cleanRoomBFS(posRobbot.first, posRobbot.second, 0, 0);
			cout << minLen << '\n';
		}
	}

	return 0;
}