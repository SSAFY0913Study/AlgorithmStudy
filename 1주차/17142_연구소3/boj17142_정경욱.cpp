#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <limits.h>
using namespace std;

int map[51][51];
int changedMap[51][51];
int N, M, result = INT_MAX, cntZero = 0;
vector<pair<int, int>> virusArr;	// ��ü ���̷��� ��ǥ�迭
pair<int, int> selected[11];	// ������ ���̷��� ��ǥ���� �迭
queue<pair<int, int>> q;
int dx[4] = { 0, 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };

void simul() {
	for (int i = 0; i < M; i++) {
		q.push(selected[i]);	// ť�� ���õ� ���ҵ� push
		changedMap[selected[i].first][selected[i].second] = -1;	//Ȱ�����̷����� -1�� ����
	}

	int curX; // ť front�� low��
	int curY; // ť front�� col��
	int nx;	// ���� low��
	int ny;	// ���� col��
	int cntErase = 0;

	while (!q.empty()) {
		curX = q.front().first;
		curY = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			nx = curX + dx[i];
			ny = curY + dy[i];

			if (nx >= 0 && nx < N && ny >= 0 && ny < N) {// ���� üũ
				if (changedMap[nx][ny] == 0 || changedMap[nx][ny] == -2) {	// 0�� ��ĭ�̰� -2�� ��Ȱ�� ���̷���
					if (changedMap[nx][ny] == 0) cntErase++;	// ����� ��ĭ�̾��� ��� ���̷����� ������ ������ ��ĭ�� ������ 1������Ų��.
					if (changedMap[curX][curY] == -1)	changedMap[nx][ny] = 1;	//������ ��ġ�� Ȱ�����̷���, �� �������̾��ٸ� ���� �̵��� ��ġ�� �ð��� 1�� ����
					else changedMap[nx][ny] = changedMap[curX][curY] + 1; //�ƴ϶�� ���� �ð��� + 1;
					q.push(make_pair(nx, ny));	// ť�� Ȱ��ȭ�� ���̷����� ��ġ�� �ִ´�.
					if (cntErase == cntZero) {	//��ĭ�� �� ������ ���
						result = min(result, changedMap[nx][ny]);
						return;	//BFS�̱� ������ ó�� ���� ������� ���� ª�� �ð��̹Ƿ� return
					}
				}
			}
		}
	}
}

void chooseVirus(int cnt, int start) {
	if (cnt == M) {	// M���� ���̷����� ������ ������� �������� ��
		while (!q.empty()) q.pop();	// ���� �۾��� ���� ť�� �����ִ� ���ҵ� ����
		simul();
		for (int i = 0; i < N; i++) {	// �����ߴ� ���� ������� �����Ѵ�.
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
			if (map[i][j] == 2) {	// ���̷����� -2�� �ʿ� �����Ѵ�.
				virusArr.push_back(make_pair(i, j));	// ��ü ���̷����� ��ġ�� �����ϴ� �迭�� ���̷����� ��ġ�� �߰��Ѵ�.
				map[i][j] = -2;
			}
			else if (map[i][j] == 0) cntZero++;	// ���̷����� ���� ��ĭ�� ������ ������Ų��.
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