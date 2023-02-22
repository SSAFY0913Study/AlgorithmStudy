#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <queue>
#include <limits.h>
using namespace std;

int N, L, R, day = 0;
int map[50][50];
bool visited[50][50];
int dx[4] = { 0, 1, 0, -1 };
int dy[4] = { 1, 0, -1, 0 };
vector<pair<int, int>> selected;	// ���漱�� ���� ������� ��ǥ �� ����

int personSum = 0;	// ���漱�� ���� ������� �α� ����

void clearVisited();
void DFS(int low, int col);
bool IsMove();
void simul();

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> L >> R;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> map[i][j];
		}
	}

	simul();

	cout << day;

	return 0;
}

/*
* �Լ� �̸�: simul
* �Լ� ����: 1. IsMove() �Լ��� true�� ��ȯ�� ���� day�� ������Ű�� �ùķ��̼� ����
*			 2. clearVisited() �Լ��� ȣ���Ͽ� �湮�ߴ� ������� ���¸� �ٽ� false�� �ٲ��ش�.
*/
void simul() {

	while (IsMove()) {	// �α��̵��� �߻��� �� �ִ� ���� ����
		clearVisited();
		day++; //��¥�� ����
	}
}

/*
* �Լ� �̸�: IsMove
* ��ȯ�� : bool Ÿ���� ��, �α� �̵��� ���� �� ��� true�� �����ϰ� �α� �̵��� ������� ���� ��� false�� �����Ѵ�.
* �Լ� ����: ���漱�� �������� ��� �α� �̵��� �����Ѵ�.
*/
bool IsMove() {
	bool pn = false;	// �α� �̵��� ����Ǿ����� ����

	for (int i = 0; i < N; i++) {	// ��ü�� ��(2���� �迭)�� Ž���Ѵ�.
		for (int j = 0; j < N; j++) {
			personSum = 0;	//���� ��ġ�� ����� ���漱�� ����Ǿ� �ִ� �α��� ����
			selected.clear();	// ������ �����ִ� ���� �迭 �����

			if (visited[i][j]) continue;	// ���� ���� �̹� �湮�߾��ٸ� ���� ����

			personSum = map[i][j];	//	���� ��ġ ������ �α��� �ʱⰪ���� ����
			visited[i][j] = true; // ���� ��ġ ���� �湮ó��
			selected.push_back(make_pair(i, j));	// ���� ��ġ ���� ���ù迭�� �߰�
			
			DFS(i, j);	//���� ��ġ�� ���󿡼� ���漱�� �����ִ� ���� Ž���Ѵ�.

			if (selected.size() == 1) continue;	// ���漱�� �����ִ� ���� ���ٸ�, �� ���ù迭���� ���� �ڱ� �ڽŸ� �����Ƿ� �α� �̵��� ������� ���� ���̹Ƿ� continue

			pn = true;	// ���ù迭�� 2���̻��� ���� �ִٸ� ���漱�� �����ִٴ� ���̹Ƿ� �α��̵��� �����ϰ� �α� �̵� ���࿩�θ� true�� ����
			
			for (int k = 0; k < selected.size(); k++) {// ���ù迭�� �ִ� ������� ���� �������ش�.
				int low = selected[k].first;
				int col = selected[k].second;

				map[low][col] = personSum / selected.size();	// ���漱�� ���� ������� �α� �̵� �����Ŵ
			}
		}
	}

	return pn;	// �α� �̵� ���� ���θ� ��ȯ
}

/*
* �Լ��̸�: DFS
* �Լ� ����: ���� �켱 Ž���� ���� �α� �̵��� ������ ������ ������� ��� ã�� �湮ó���Ѵ�.
*/
void DFS(int low, int col) {
	int nx;
	int ny;
	for (int i = 0; i < 4; i++) {
		nx = low + dx[i];	// ���� ��
		ny = col + dy[i];	// ���� ��

		if (nx >= 0 && nx < N && ny >= 0 && ny < N) {// ���� ���� �˻�
			if (!visited[nx][ny] && abs(map[nx][ny] - map[low][col]) >= L && abs(map[nx][ny] - map[low][col]) <= R) {	// �α� �̵� ���� ���� �˻�
				visited[nx][ny] = true;	// �湮ó��
				selected.push_back(make_pair(nx, ny));	// ���� �迭(������ ���� �迭)�� ��ġ �߰�
				personSum += map[nx][ny]; // ���漱�� �����ִ� ������ ������� �� �α����� ����
				DFS(nx, ny);
			}
		}
	}
}

void clearVisited() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			visited[i][j] = false;
		}
	}
}