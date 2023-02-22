#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int R, C, N;
char input;

int map[202][202];
int dx[4] = { 0,1,0,-1 };
int dy[4] = { 1,0,-1,0 };

void putBoom();
void checkBoom();


void simul() {
	int curtime = 1;

	while (curtime < N) {
		putBoom();
		checkBoom();
		curtime++;
	}
}

void putBoom() {	// ��ź ��ġ�Լ�
	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			if (map[i][j] == -1) map[i][j] = 4; //��ĭ�� ���� ��ź�� ��ġ, 4�� ������ ������ checkedBoom()�Լ����� �ٷ� ���ҽ����ֱ� ������. 
		}
	}
}

void checkBoom() {
	vector<pair<int, int>> curBoom;	//�̹� �Ͽ� ������ ��ź�� ��ǥ�� �����ϴ� �迭

	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			if (map[i][j] > 0) map[i][j] --;	//0���� ū���̸� ���� 1�����ش�
			if (map[i][j] == 0) {	// 0�̵Ǿ����� �̹��Ͽ� ������.
				map[i][j] = -1;	// ������ ���� ��ĭ���� ����
				curBoom.push_back(make_pair(i, j));	// �迭�� �߰�
			}
		}
	}

	for (int i = 0; i < curBoom.size(); i++) {// �迭�� ���� ���鼭 �ֺ��� ��ĭ���� �����.
		for (int j = 0; j < 4; j++) {
			map[curBoom[i].first + dx[j]][curBoom[i].second + dy[j]] = -1;
		}
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);


	cin >> R >> C >> N;

	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			cin >> input;
			if (input == '.') map[i][j] = -1;
			else map[i][j] = 3;
		}
	}

	checkBoom();	//1����
	if (N >= 2) simul();

	for (int i = 1; i <= R; i++) {//���
		for (int j = 1; j <= C; j++) {
			if (map[i][j] == -1) cout << '.';
			else cout << 'O';
		}
		cout << '\n';
	}

	return 0;
}